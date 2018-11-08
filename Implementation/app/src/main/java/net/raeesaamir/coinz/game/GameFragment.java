package net.raeesaamir.coinz.game;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
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

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class GameFragment extends Fragment implements OnMapReadyCallback, LocationEngineListener, PermissionsListener {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");

    public static class GeoJsonDownloadTask extends DownloadFileTask<FeatureCollection> {

        @Override
        public FeatureCollection readStream(String jSONDocument) {

            try {

                JSONObject jsonObject = new JSONObject(jSONDocument);

                String featureCollectionType = jsonObject.getString("type");
                String featureCollectionDateGenerated = jsonObject.getString("date-generated");
                String featureCollectionTimeGenerated = jsonObject.getString("time-generated");
                String featureCollectionApproximateTimeRemaining = jsonObject.getString("approximate-time-remaining");

                JSONObject featureCollectionRatesJSONObject = jsonObject.getJSONObject("rates");

                ImmutableMap<Currency, Double> featureCollectionRates = new ImmutableMap.Builder<Currency, Double>()
                        .put(Currency.SHIL, featureCollectionRatesJSONObject.getDouble("SHIL"))
                        .put(Currency.DOLR, featureCollectionRatesJSONObject.getDouble("DOLR"))
                        .put(Currency.QUID, featureCollectionRatesJSONObject.getDouble("QUID"))
                        .put(Currency.PENY, featureCollectionRatesJSONObject.getDouble("PENY")).build();

                JSONArray featureCollectionFeaturesJSONArray = jsonObject.getJSONArray("features");
                ImmutableList.Builder<Feature> featureBuilder = new ImmutableList.Builder<>();
                for(int i = 0; i < featureCollectionFeaturesJSONArray.length(); i++) {
                    JSONObject featureJSONObject = featureCollectionFeaturesJSONArray.getJSONObject(i);

                    String featureType = featureJSONObject.getString("type");

                    JSONObject featurePropertiesJSONObject = featureJSONObject.getJSONObject("properties");
                    String featurePropertiesId = featurePropertiesJSONObject.getString("id");
                    String featurePropertiesValue = featurePropertiesJSONObject.getString("value");
                    Currency featurePropertiesCurrency = Currency.fromString(featurePropertiesJSONObject.getString("currency"));
                    String featurePropertiesMarkerSymbol = featurePropertiesJSONObject.getString("marker-symbol");
                    String featurePropertiesMarkerColor = featurePropertiesJSONObject.getString("markr-color");
                    Feature.Properties featureProperties = new Feature.Properties(featurePropertiesId, featurePropertiesValue, featurePropertiesCurrency, featurePropertiesMarkerSymbol, featurePropertiesMarkerColor);

                    JSONObject featureGeometryJSONObject = featureJSONObject.getJSONObject("geometry");
                    String featureGeometryType = featureJSONObject.getString("type");
                    JSONArray featureGeometryCoordinatesJSONArray = featureGeometryJSONObject.getJSONArray("coordinates");
                    ImmutableList<Double> featureGeometryCoordinates = new ImmutableList.Builder<Double>().add(featureGeometryCoordinatesJSONArray.getDouble(0))
                            .add(featureGeometryCoordinatesJSONArray.getDouble(1)).build();
                    Feature.Geometry featureGeometry = new Feature.Geometry(featureGeometryType, featureGeometryCoordinates);

                    Feature feature = new Feature(featureType, featureProperties, featureGeometry);
                    featureBuilder.add(feature);
                }

                ImmutableList<Feature> featureCollectionFeatures = featureBuilder.build();

                FeatureCollection featureCollection = new FeatureCollection(featureCollectionType, featureCollectionDateGenerated, featureCollectionTimeGenerated, featureCollectionApproximateTimeRemaining, featureCollectionRates, featureCollectionFeatures);
                return featureCollection;
            } catch(Exception e) {

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
    }

    private void configureMapView(@Nullable Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync((MapboxMap m) -> {

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
