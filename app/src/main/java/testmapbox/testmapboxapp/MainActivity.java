package testmapbox.testmapboxapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.style.sources.Source;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private boolean markerSelected = false;
    private List<Point> routeCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Mapbox.getInstance(this,getString(R.string.MapBoxKey));

        setContentView(R.layout.activity_main);

        // Create a list to store our line coordinates.
        routeCoordinates = new ArrayList<Point>();
        routeCoordinates.add(Point.fromLngLat(-118.39439114221236, 33.397676454651766));
        routeCoordinates.add(Point.fromLngLat(-118.39421054012902, 33.39769799454838));
        routeCoordinates.add(Point.fromLngLat(-118.38955358640874, 33.3955978295119));
        routeCoordinates.add(Point.fromLngLat(-118.389041880506, 33.39578092284221));
        routeCoordinates.add(Point.fromLngLat(-118.38872797688494, 33.3957916930261));
        routeCoordinates.add(Point.fromLngLat(-118.38638874990086, 33.395737842093304));
        routeCoordinates.add(Point.fromLngLat(-118.38723155962309, 33.395027006653244));
        routeCoordinates.add(Point.fromLngLat(-118.38734766096238, 33.394441819579285));
        routeCoordinates.add(Point.fromLngLat(-118.38785936686516, 33.39403972556368));
        routeCoordinates.add(Point.fromLngLat(-118.3880743693453, 33.393616088784825));
        routeCoordinates.add(Point.fromLngLat(-118.38791956755958, 33.39331092541894));
        routeCoordinates.add(Point.fromLngLat(-118.38318091289693, 33.38941192035707));
        routeCoordinates.add(Point.fromLngLat(-118.38271650753981, 33.3896129783018));
        routeCoordinates.add(Point.fromLngLat(-118.38275090793661, 33.38902416443619));
        routeCoordinates.add(Point.fromLngLat(-118.38226930238106, 33.3889451769069));
        routeCoordinates.add(Point.fromLngLat(-118.37794345248027, 33.387810620840135));

        mapView = findViewById(R.id.mapView);

        mapView.getMapAsync(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
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
    public void onStop() {
        super.onStop();
        mapView.onStop();
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        List<Feature> markerCoordinates = new ArrayList<>();

        //Origin point
        markerCoordinates.add(Feature.fromGeometry(
                Point.fromLngLat(-118.39439114221236, 33.397676454651766)));

        //Destination point
        markerCoordinates.add(Feature.fromGeometry(
                Point.fromLngLat(-118.37091717142867, 33.39114243958559)));

        FeatureCollection featureCollection = FeatureCollection.fromFeatures(markerCoordinates);

        Source geoJsonSource = new GeoJsonSource("marker-source", featureCollection);
        mapboxMap.addSource(geoJsonSource);

        Bitmap icon = BitmapFactory.decodeResource(
                MainActivity.this.getResources(), R.drawable.android_source);

        // Add the marker image to map
        mapboxMap.addImage("my-marker-image", icon);

        SymbolLayer markers = new SymbolLayer("marker-layer", "marker-source")
                .withProperties(PropertyFactory.iconImage("my-marker-image"));
        mapboxMap.addLayer(markers);

        // Add the selected marker source and layer
        FeatureCollection emptySource = FeatureCollection.fromFeatures(new Feature[]{});
        Source selectedMarkerSource = new GeoJsonSource("selected-marker", emptySource);
        mapboxMap.addSource(selectedMarkerSource);

        SymbolLayer selectedMarker = new SymbolLayer("selected-marker-layer", "selected-marker")
                .withProperties(PropertyFactory.iconImage("my-marker-image"));
        mapboxMap.addLayer(selectedMarker);


        Drawable drawable = new BitmapDrawable(getResources(), fetchIcon());

        Icon planeIcon = IconFactory.getInstance(MainActivity.this).fromResource(R.drawable.red_plane_android);

        Icon downloadedIcon=IconFactory.getInstance(MainActivity.this).fromBitmap(fetchIcon());

        // Add the custom icon marker to the map
       Marker planeIconmarker= mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng( 33.387810620840135,-118.37794345248027))
                .icon(downloadedIcon));

        //Draw line
        // FeatureCollection so we can add the line to our map as a layer.
        LineString lineString = LineString.fromLngLats(routeCoordinates);
        FeatureCollection featureCollection1 =
                FeatureCollection.fromFeatures(new Feature[] {Feature.fromGeometry(lineString)});
        Source geoJsonSource1 = new GeoJsonSource("line-source", featureCollection1);
        mapboxMap.addSource(geoJsonSource1);
        LineLayer lineLayer = new LineLayer("linelayer", "line-source");

        lineLayer.setProperties(
                PropertyFactory.lineWidth(3f),
                PropertyFactory.lineColor(Color.parseColor("#e55e5e"))
        );
        mapboxMap.addLayer(lineLayer);

        LatLngBounds latLngBounds=new LatLngBounds.Builder().include(new LatLng(33.397676454651766,-118.39439114221236))
                .include(new LatLng(33.39114243958559,-118.37091717142867)).build();

        mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50), 5000);


    }

    private Bitmap fetchIcon() {

        URL url = null;
        try {
            url = new URL("https://res.cloudinary.com/dfxcoimwt/image/upload/a_107,e_trim,c_scale,w_100/v1503978294/RedPlane_yinoag.png");
        } catch (MalformedURLException e) {
            return  null;
        }
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            return  null;
        }
        return  bmp;
    }

    private double computeHeading(LatLng from, LatLng to) {
        double fromLat = Math.toRadians(from.getLatitude());
        double fromLng = Math.toRadians(from.getLongitude());
        double toLat = Math.toRadians(to.getLatitude());
        double toLng = Math.toRadians(to.getLongitude());
        double dLng = toLng - fromLng;
        double heading = Math.atan2(Math.sin(dLng) * Math.cos(toLat),
                Math.cos(fromLat) * Math.sin(toLat) - Math.sin(fromLat) * Math.cos(toLat) * Math.cos(dLng));
        return (Math.toDegrees(heading) >= -180 && Math.toDegrees(heading) < 180) ?
                Math.toDegrees(heading) : ((((Math.toDegrees(heading) + 180) % 360) + 360) % 360 + -180);
    }
}
