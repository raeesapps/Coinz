package net.raeesaamir.coinz.game;

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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
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

import net.raeesaamir.coinz.DownloadFileTask;
import net.raeesaamir.coinz.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GameFragment extends Fragment implements OnMapReadyCallback, LocationEngineListener, PermissionsListener {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");
    private static final String FEATURE_COLLECTION_URL = "http://homepages.inf.ed.ac.uk/stg/coinz/";
    private static final String SHARED_PREFERENCES_KEY = "FeatureCollection_Shared_Prefs";

    public static class GeoJsonDownloadTask extends DownloadFileTask<FeatureCollection> {

        @Override
        public FeatureCollection readStream(String jSONDocument) {



            try {

                JSONObject jsonObject = new JSONObject(jSONDocument);

                String featureCollectionType = jsonObject.getString("type");
                String featureCollectionDateGenerated = jsonObject.getString("date-generated");
                String featureCollectionTimeGenerated = jsonObject.getString("time-generated");
                String featureCollectionApproximateTimeRemaining = jsonObject.getString("approximate-time-remaining");

                System.out.println("featureCollectionType"+featureCollectionType);
                System.out.println("featureCollectionDateGenerated"+featureCollectionDateGenerated);
                System.out.println("featureCollectionTimeGenerated"+featureCollectionTimeGenerated);
                System.out.println("featureCollectionApproxTimeRemaining"+featureCollectionApproximateTimeRemaining);

                JSONObject featureCollectionRatesJSONObject = jsonObject.getJSONObject("rates");

                ImmutableMap<Currency, Double> featureCollectionRates = new ImmutableMap.Builder<Currency, Double>()
                        .put(Currency.SHIL, featureCollectionRatesJSONObject.getDouble("SHIL"))
                        .put(Currency.DOLR, featureCollectionRatesJSONObject.getDouble("DOLR"))
                        .put(Currency.QUID, featureCollectionRatesJSONObject.getDouble("QUID"))
                        .put(Currency.PENY, featureCollectionRatesJSONObject.getDouble("PENY")).build();

                System.out.println("rates"+featureCollectionRates.toString());

                JSONArray featureCollectionFeaturesJSONArray = jsonObject.getJSONArray("features");

                System.out.println("LENGTH"+featureCollectionFeaturesJSONArray.length());

                ImmutableList.Builder<Feature> featureBuilder = new ImmutableList.Builder<>();
                for(int i = 0; i < featureCollectionFeaturesJSONArray.length(); i++) {

                    System.out.println("INDEX= "+i);

                    JSONObject featureJSONObject = featureCollectionFeaturesJSONArray.getJSONObject(i);

                    String featureType = featureJSONObject.getString("type");

                    JSONObject featurePropertiesJSONObject = featureJSONObject.getJSONObject("properties");
                    String featurePropertiesId = featurePropertiesJSONObject.getString("id");
                    String featurePropertiesValue = featurePropertiesJSONObject.getString("value");
                    Currency featurePropertiesCurrency = Currency.fromString(featurePropertiesJSONObject.getString("currency"));
                    String featurePropertiesMarkerSymbol = featurePropertiesJSONObject.getString("marker-symbol");
                    String featurePropertiesMarkerColor = featurePropertiesJSONObject.getString("marker-color");
                    Feature.Properties featureProperties = new Feature.Properties(featurePropertiesId, featurePropertiesValue, featurePropertiesCurrency, featurePropertiesMarkerSymbol, featurePropertiesMarkerColor);

                    System.out.println(featureProperties.toString());

                    JSONObject featureGeometryJSONObject = featureJSONObject.getJSONObject("geometry");
                    String featureGeometryType = featureJSONObject.getString("type");
                    JSONArray featureGeometryCoordinatesJSONArray = featureGeometryJSONObject.getJSONArray("coordinates");
                    ImmutableList<Double> featureGeometryCoordinates = new ImmutableList.Builder<Double>().add(featureGeometryCoordinatesJSONArray.getDouble(0))
                            .add(featureGeometryCoordinatesJSONArray.getDouble(1)).build();
                    Feature.Geometry featureGeometry = new Feature.Geometry(featureGeometryType, featureGeometryCoordinates);

                    System.out.println(featureGeometry.toString());

                    Feature feature = new Feature(featureType, featureProperties, featureGeometry);
                    featureBuilder.add(feature);

                    System.out.println(feature.toString());
                }

                ImmutableList<Feature> featureCollectionFeatures = featureBuilder.build();

                FeatureCollection featureCollection = new FeatureCollection(featureCollectionType, featureCollectionDateGenerated, featureCollectionTimeGenerated, featureCollectionApproximateTimeRemaining, featureCollectionRates, featureCollectionFeatures, jSONDocument);
                System.out.println(featureCollection.toString());
                return featureCollection;
            } catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private String tag = "GameFragment";
    private View view;

    private MapView mapView;
    private MapboxMap map;

    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originalLocation;

    private FeatureCollection featureCollection;

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

        long date = new Date().getTime();
        String dateFormatted = DATE_FORMATTER.format(date);

        String url = FEATURE_COLLECTION_URL + dateFormatted + "/coinzmap.geojson";
        try {

            SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

            if(preferences.contains(dateFormatted)) {

                System.out.println("jsondata exists");

                String jSONDocument = preferences.getString(dateFormatted, "");
                featureCollection = new GeoJsonDownloadTask().readStream(jSONDocument);
                System.out.println("jsondata"+featureCollection.getFeatures());
            } else {

                System.out.println("jsondata does not exist");

                featureCollection = new GeoJsonDownloadTask().execute(url).get();
                preferences.edit().putString(dateFormatted, featureCollection.getJsonData()).commit();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    private void configureMapView(@Nullable Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync((MapboxMap m) -> {
            Map<Marker, Feature> markerFeatureMap = Maps.newHashMap();

            for(Feature feature: featureCollection.getFeatures()) {

                Feature.Geometry geometry = feature.getGeometry();
                double x = geometry.getCoordinates().get(0);
                double y = geometry.getCoordinates().get(1);

                Feature.Properties properties = feature.getProperties();
                String markerColor = properties.getMarkerColor();
                String value = properties.getValue() + " " + properties.getCurrency().toString().toLowerCase();
                int resource = -1;
                switch(markerColor) {
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

                IconFactory iconFactory = IconFactory.getInstance(getActivity());
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resource);
                Icon icon = iconFactory.fromBitmap(Bitmap.createScaledBitmap(bitmap, 80, 150, false));

                Marker marker = m.addMarker(new MarkerOptions().setPosition(new LatLng(y,x)).setIcon(icon));
                markerFeatureMap.put(marker, feature);
            }

            m.setOnMarkerClickListener((@NonNull Marker marker) -> {
                Preconditions.checkArgument(markerFeatureMap.containsKey(marker));

                Feature feature = markerFeatureMap.get(marker);
                Feature.Properties properties = feature.getProperties();
                String value = properties.getValue() + " " + properties.getCurrency().toString().toLowerCase();

                Toast.makeText(getActivity(), value, Toast.LENGTH_SHORT).show();
                return true;
            });

        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
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

        if(mapboxMap == null) {
            Log.d(tag, "[onMapReady] mapBox is null");
        } else {
            map = mapboxMap;

            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);

            enableLocation();
        }

    }

    private void enableLocation() {
        if(PermissionsManager.areLocationPermissionsGranted(getContext())) {
            Log.d(tag, "Permissions are granted");
            initializeLocationEngine();
            initializeLocationLayer();

        } else {
            Log.d(tag, "Permissions are granted");
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    private void setCameraPosition(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private void initializeLocationEngine() {
        locationEngine = new LocationEngineProvider(getContext()).obtainBestLocationEngineAvailable();
        locationEngine.setInterval(5000);
        locationEngine.setFastestInterval(1000);
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if(lastLocation != null) {
            originalLocation = lastLocation;
            setCameraPosition(originalLocation);
        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }

    private void initializeLocationLayer() {
        if(mapView == null) {
            Log.d(tag, "map view is null");
        } else {
            locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
            locationLayerPlugin.setLocationLayerEnabled(true);
            locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
            locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
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

        if(location == null) {
            Log.d(tag, "[onLocationChanged] location is null");
        } else {
            Log.d(tag, "[onLocationChanged] location is not null");
            originalLocation = location;
            setCameraPosition(location);
        }

    }

    @Override
    public void onPermissionResult(boolean granted) {
        Log.d(tag, "[onPermissionResult] granted == " + granted);

        if(granted) {
            enableLocation();
        } else {

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
}
