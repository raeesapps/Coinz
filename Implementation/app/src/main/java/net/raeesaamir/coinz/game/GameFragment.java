package net.raeesaamir.coinz.game;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;

import net.raeesaamir.coinz.R;
import net.raeesaamir.coinz.wallet.Wallet;
import net.raeesaamir.coinz.wallet.WalletType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * This fragment handles the rendering, updating and coin collection of the game.
 *
 * @author raeesaamir
 */
public class GameFragment extends Fragment implements OnMapReadyCallback, LocationEngineListener, PermissionsListener {

    /**
     * The date format used in the map's GeoJSON file and the player's wallet.
     */
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd", Locale.UK);

    /**
     * The unique identifier of the shared preferences object
     */
    private static final String SHARED_PREFERENCES_KEY = "FeatureCollection_Shared_Prefs";

    /**
     * The tag that appears in the debug statements
     */
    private final String tag = "GameFragment";

    /**
     * A one-to-one mapping from feature objects to marker objects. Features contain information about markers whereas the marker objects are the actual markers on the Mapbox map.
     */
    private final Map<Feature, Marker> featureMarkerMap = Maps.newHashMap();

    /**
     * The context of the android app.
     */
    private Context context;

    /**
     * The view object that is passed to onViewCreated.
     */
    private View view;

    /**
     * The object representing the authenticated user.
     */
    private FirebaseUser mUser;

    /**
     * The view holding the MapboxMap.
     */
    private MapView mapView;

    /**
     * The class representing the Mapbox map.
     */
    private MapboxMap map;

    /**
     * The location engine which handles player movement and location changes.
     */
    private LocationEngine locationEngine;

    /**
     * The player's location cached.
     */
    private Location originalLocation;

    /**
     * The features of today's map such as the exchange rate and the location of the markers.
     */
    private FeatureCollection featureCollection;

    /**
     * The loading dialog.
     */
    private ProgressDialog dialog;

    /**
     * Today's date formatted using the date formatter.
     */
    private String dateFormatted;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        configureMapView(savedInstanceState);

        Gson gson = new Gson();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        long date = new Date().getTime();
        dateFormatted = DATE_FORMATTER.format(date);

        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        try {
            dialog = ProgressDialog.show(context, "",
                    "Loading. Please wait...", true);
            dialog.show();
            featureCollection = FeatureCollection.fromWebsite(preferences, gson, mAuth.getUid(), dateFormatted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Instantiates the map view and loads the map object in an asynchronous manner.
     *
     * @param savedInstanceState - The saved instance.
     */
    private void configureMapView(@Nullable Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();

        if (locationEngine != null) {

            try {
                locationEngine.requestLocationUpdates();
            } catch (SecurityException ignored) {
            }
            locationEngine.addLocationEngineListener(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();

        if (locationEngine != null) {
            locationEngine.removeLocationEngineListener(this);
            locationEngine.removeLocationUpdates();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {

        if (mapboxMap == null) {
            Log.d(tag, "[onMapReady] mapBox is null");
        } else {

            map = mapboxMap;

            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);

            Map<Marker, Feature> markerFeatureMap = Maps.newHashMap();

            for (Feature feature : featureCollection.getFeatures()) {

                if (feature == null) {
                    continue;
                }

                Geometry geometry = feature.getGeometry();
                double longitude = geometry.getCoordinates()[0];
                double latitude = geometry.getCoordinates()[1];

                Properties properties = feature.getProperties();
                String markerColor = properties.getMarkerColor();
                int resource = -1;
                switch (markerColor) {
                    case "#ff0000":
                        resource = R.drawable.purple_marker;
                        break;
                    case "#0000ff":
                        resource = R.drawable.blue_marker;
                        break;
                    case "#008000":
                        resource = R.drawable.green_marker;
                        break;
                    case "#ffdf00":
                        resource = R.drawable.yellow_marker;
                        break;
                    default:
                        break;

                }

                IconFactory iconFactory = IconFactory.getInstance(Objects.requireNonNull(getActivity()));
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resource);
                Icon icon = iconFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, 80, 150, false));

                Marker marker = map.addMarker(new MarkerOptions().setPosition(new LatLng(latitude, longitude)).setIcon(icon));
                markerFeatureMap.put(marker, feature);
                featureMarkerMap.put(feature, marker);
            }

            map.setOnMarkerClickListener((@NonNull Marker marker) -> {
                Preconditions.checkArgument(markerFeatureMap.containsKey(marker));

                Feature feature = markerFeatureMap.get(marker);
                Properties properties = feature.getProperties();
                String value = properties.getValue() + " " + properties.getCurrency().toLowerCase();

                Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
                return true;
            });

            enableLocation();
            dialog.dismiss();
        }

    }

    /**
     * Initializes the location engine and location layer if the permissions are granted otherwise it requests permission from the player.
     */
    private void enableLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(context)) {
            Log.d(tag, "Permissions are granted");
            initializeLocationEngine();
            initializeLocationLayer();

        } else {
            Log.d(tag, "Permissions are not granted");
            PermissionsManager permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    /**
     * Aligns the camera to the player's location.
     *
     * @param location - The location to align to.
     */
    private void setCameraPosition(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    /**
     * Initializes the location engine by setting the listener to this class and caches the player's location.
     */
    private void initializeLocationEngine() {
        locationEngine = new LocationEngineProvider(context).obtainBestLocationEngineAvailable();
        locationEngine.addLocationEngineListener(this);
        locationEngine.setInterval(5000);
        locationEngine.setFastestInterval(1000);
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            originalLocation = lastLocation;
            setCameraPosition(originalLocation);
        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }

    private void initializeLocationLayer() {
        if (mapView == null) {
            Log.d(tag, "map view is null");
        } else {
            LocationLayerPlugin locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
            locationLayerPlugin.setLocationLayerEnabled(true);
            locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
            locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
            Lifecycle lifecycle = getLifecycle();
            lifecycle.addObserver(locationLayerPlugin);
        }
    }

    @Override
    public void onConnected() {
        Log.d(tag, "[onConnected] requesting location update");
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Log.d(tag, "Permissions: " + permissionsToExplain.toString());
    }

    @Override
    public void onLocationChanged(Location location) {

        System.out.println("onLocationChanged");

        if (location == null) {
            Log.d(tag, "[onLocationChanged] location is null");
        } else {
            long dateNowNum = new Date().getTime();
            String dateNow = DATE_FORMATTER.format(dateNowNum);

            if (!dateNow.equals(dateFormatted)) {
                Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.fragment_container);

                getFragmentManager().beginTransaction()
                        .detach(this)
                        .attach(this)
                        .commit();
            }

            Wallet.loadWallet(mUser.getUid(), dateFormatted, (Wallet wallet) -> {
                System.out.println("[onLocationChanged] callback called!");
                Log.d(tag, "[onLocationChanged] location is not null");
                originalLocation = location;
                setCameraPosition(location);

                LocationChangedEvent locationChangedEvent = onPlayerChangesLocation(location);
                System.out.println("PLAYER AT MARKER: " + locationChangedEvent.atMarker);
                if (locationChangedEvent.atMarker) {
                    List<String> coins = wallet.getCoins();

                    locationChangedEvent.featureMap.forEach((indexOfFeature, feature) -> {

                        Marker marker = featureMarkerMap.get(feature);
                        map.removeMarker(marker);

                        Feature[] features = featureCollection.getFeatures();
                        Properties properties = features[indexOfFeature].getProperties();
                        String currency = properties.getCurrency();
                        String value = properties.getValue();

                        features[indexOfFeature] = null;
                        String dateFormatted = DATE_FORMATTER.format(new Date().getTime());
                        SharedPreferences preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
                        Gson gson = new Gson();
                        String jSONDocument = gson.toJson(featureCollection);
                        String key = mUser.getUid() + "/" + dateFormatted;
                        preferences.edit().putString(key, jSONDocument).commit();

                        if (coins.size() == 25) {
                            Wallet.loadWallet(mUser.getUid(), dateFormatted, WalletType.SPARE_CHANGE_WALLET, (Wallet spareChangeWallet) -> {
                                spareChangeWallet.addCoin(currency + " " + value);
                                System.out.println("[GameFragment]: " + currency + " " + value);
                                spareChangeWallet.getFuture();
                            });
                            Toast.makeText(getActivity(), "You have too many coins in your wallet! The coin has been put in your spare change wallet.", Toast.LENGTH_LONG).show();
                        } else {
                            wallet.addCoin(currency + " " + value);
                            System.out.println("[GameFragment]: " + currency + " " + value);
                            wallet.getFuture();
                        }
                    });

                }
            });
        }
    }

    /**
     * Called when the player moves somewhere else on the map. If there is no marker at the player's location.
     *
     * @param playerLocation - The player's location.
     * @return If there's a marker at the player's location then a LocationChangedEvent containing the Feature and the Feature's index will be returned; otherwise, an empty LocationChangedEvent will be returned.
     */
    private LocationChangedEvent onPlayerChangesLocation(Location playerLocation) {
        Map<Integer, Feature> featureMap = Maps.newHashMap();
        Feature[] features = featureCollection.getFeatures();
        LatLng playerLatLng = new LatLng(playerLocation.getLatitude(), playerLocation.getLongitude());
        for (int i = 0; i < features.length; i++) {

            Feature feature = features[i];

            if (feature == null) {
                continue;
            }

            Geometry geometry = feature.getGeometry();
            LatLng featureLatLng = new LatLng(geometry.getCoordinates()[1], geometry.getCoordinates()[0]);

            Log.i("[onPlayerChangesLocation] distance: ", Double.toString(featureLatLng.distanceTo(playerLatLng)));

            if (featureLatLng.distanceTo(playerLatLng) <= 25) {
                featureMap.put(i, feature);
            }
        }

        return new LocationChangedEvent(featureMap, featureMap.size() > 0);
    }

    @Override
    public void onPermissionResult(boolean granted) {
        Log.d(tag, "[onPermissionResult] granted == " + granted);

        if (granted) {
            enableLocation();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    /**
     * This event is called when the player moves somewhere else.
     *
     * @author raeesaamir
     */
    private static class LocationChangedEvent {

        /**
         * The information of the markers the player has walked to.
         */
        private final Map<Integer, Feature> featureMap;

        /**
         * If the player is at a marker on the map, this will be true.
         */
        private final boolean atMarker;

        /**
         * Constructs a new LocationChangedEvent instance.
         *
         * @param featureMap - The features of the markers the player has visited.
         * @param atMarker   - A boolean value of the player being at a marker on the map.
         */
        LocationChangedEvent(Map<Integer, Feature> featureMap, boolean atMarker) {
            this.featureMap = featureMap;
            this.atMarker = atMarker;
        }
    }
}
