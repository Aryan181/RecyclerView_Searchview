package com.example.recyclerview;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.tilequery.MapboxTilequery;
//import com.mapbox.api.tilequery.MapboxTilequery;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.example.recyclerview.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import android.graphics.Color;
import android.graphics.PointF;

import android.util.Log;
import android.widget.Toast;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, MapboxMap.OnMapClickListener {

    private MapView mapView;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;

    ArrayList<Coordinate> latlong = new ArrayList<Coordinate>();

    private static final String RESULT_GEOJSON_SOURCE_ID = "RESULT_GEOJSON_SOURCE_ID";
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_map_view);

        mapView = findViewById(R.id.mapView);
        // mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }


    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        MapViewActivity.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                enableLocationComponent(style, mapboxMap);
                style.addSource(new GeoJsonSource("source-id"));

                style.addLayer(new FillLayer("layer-id", "source-id").withProperties(
                        PropertyFactory.fillColor(Color.parseColor("#8A8ACB"))
                ));

                mapboxMap.addOnMapClickListener(MapViewActivity.this);
                Toast.makeText(MapViewActivity.this,
                        getString(R.string.click_on_map_instruction), Toast.LENGTH_SHORT).show();

            }
        });




    }

    @Override
    public boolean onMapClick(@NonNull LatLng point ) {

        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                final PointF finalPoint = mapboxMap.getProjection().toScreenLocation(point);





                Coordinate current_point = new Coordinate(""+point.getLatitude(),""+point.getLongitude());






               // makeTilequeryApiCall(point);






                latlong.add(current_point);

                Toast.makeText(MapViewActivity.this, "Chosen location added for removal", Toast.LENGTH_SHORT).show();


                Log.e(TAG, "Here's the current list =>");




                for(int i = 0;i<latlong.size();i++) {
                    Log.e(TAG, "" + latlong.get(i).getX()+" " + latlong.get(i).getY());

                }


                List<Feature> features = mapboxMap.queryRenderedFeatures(finalPoint, "building");
                if (features.size() > 0) {

                    GeoJsonSource selectedBuildingSource = style.getSourceAs("source-id");
                    if (selectedBuildingSource != null) {

                        selectedBuildingSource.setGeoJson(FeatureCollection.fromFeatures(features));
                    }
                }
            }
        });
        return true;





    }



    private void makeTilequeryApiCall(@NonNull LatLng point) {
        MapboxTilequery tilequery = MapboxTilequery.builder()
                .accessToken(getString(R.string.mapbox_access_token))
                .tilesetIds("mapbox.mapbox-streets-v7")
                .query(Point.fromLngLat(point.getLongitude(), point.getLatitude()))
                .radius(50)
                .limit(10)
                .geometry("polygon")
                .dedupe(true)
                .layers("building")
                .build();


        tilequery.enqueueCall(new Callback<FeatureCollection>() {


                                 @Override
                                  public void onResponse(Call<FeatureCollection> call, Response<FeatureCollection> response) {
                                      if (response.body() != null) {
                                          FeatureCollection responseFeatureCollection = response.body();
                                          // tilequeryResponseTextView.setText(responseFeatureCollection.toJson());
                                          Log.e(TAG, "" + responseFeatureCollection.toJson());
                                      }
                                  }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable t) {

            }

        });
}


                    /*mapboxMap.getStyle(new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
                            GeoJsonSource resultSource = style.getSourceAs(RESULT_GEOJSON_SOURCE_ID);
                             if (resultSource != null && responseFeatureCollection.features() != null) {
                                List<Feature> featureList = responseFeatureCollection.features();


                                if (featureList.isEmpty()) {
                                    //Toast.makeText(TilequeryActivity.this,
                                            //getString(R.string.no_tilequery_response_features_toast), Toast.LENGTH_SHORT).show();
                                } else {
                                    resultSource.setGeoJson(FeatureCollection.fromFeatures(featureList));
                                    Log.e(TAG, "Here's the list compiled till now ");
                                    Log.e(TAG, featureList.toString());

                                }
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FeatureCollection> call, Throwable throwable) {
                Timber.d("Request failed: %s", throwable.getMessage());
                //Toast.makeText(TilequeryActivity.this, R.string.api_error, Toast.LENGTH_SHORT).show();
            }
        });


            }

*/





    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(Style style, MapboxMap mapboxMap) {

        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Activate with a built LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(LocationComponentActivationOptions.builder(this, style).build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);

        } else {

            permissionsManager = new PermissionsManager(this);

            permissionsManager.requestLocationPermissions(this);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }





    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {

    }
}