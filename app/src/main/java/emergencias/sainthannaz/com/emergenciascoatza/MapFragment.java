package emergencias.sainthannaz.com.emergenciascoatza;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import emergencias.sainthannaz.com.emergenciascoatza.app.MyApplication;
import emergencias.sainthannaz.com.emergenciascoatza.model.InnerTables;
import emergencias.sainthannaz.com.emergenciascoatza.model.SubNumbers;
import emergencias.sainthannaz.com.emergenciascoatza.tools.DatabaseHandler;
import emergencias.sainthannaz.com.emergenciascoatza.tools.PermissionUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    private LatLng mClickPos;
    private GoogleMap mMap;
    MapView mMapView;
    View view;

    DatabaseHandler db;
    List<SubNumbers> subNumbers;
    List<InnerTables> innerNumbers;
    String mapArray[];
    int resID;
    BitmapDescriptor bitmapDescriptor;
    private Marker previousMarker = null;
    private UiSettings mUiSettings;
    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOCATION_LAYER_PERMISSION_REQUEST_CODE = 2;
    private boolean mLocationPermissionDenied = false;
    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        setHasOptionsMenu(true);
        db = new DatabaseHandler(getActivity());
        subNumbers = db.getAllSubData();
        innerNumbers = db.getAllInnerTable();

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            requestLocationPermission(MY_LOCATION_PERMISSION_REQUEST_CODE);
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        populateMyMap();

        return view;
    }

    /**
     * Requests the fine location permission. If a rationale with an additional explanation should
     * be shown to the user, displays a dialog that triggers the request.
     */
    public void requestLocationPermission(int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Display a dialog with rationale.
            PermissionUtils.RationaleDialog
                    .newInstance(requestCode, false).show(
                    getFragmentManager(), "dialog");
        } else {
            // Location permission has not been granted yet, request it.
            PermissionUtils.requestPermission((AppCompatActivity) getContext(), requestCode,
                    Manifest.permission.ACCESS_FINE_LOCATION, false);
        }

    }

    public void populateMyMap(){
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap = mMap;
                mUiSettings = mMap.getUiSettings();
                System.out.println("Se cargara mapa!");

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                mUiSettings.setMapToolbarEnabled(true);
                mUiSettings.setZoomControlsEnabled(true);
                mUiSettings.setCompassEnabled(true);
                mMap.setMyLocationEnabled(true);
                //mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));

                // For dropping a marker at a point on the Map
                LatLng coatzacoalcos = new LatLng(18.13346, -94.44242);
                for (int i = 0; i < innerNumbers.size(); i++) {
                    //System.out.println("Checar: " + subNumbers.get(i).getCard_map());
                    String mapTitle = innerNumbers.get(i).getCard_title();
                    String mapDescription = innerNumbers.get(i).getCard_description();
                    String mapToLangLon = innerNumbers.get(i).getCard_map();
                    String mapCategory = innerNumbers.get(i).getCard_category();
                    int mapTagID = innerNumbers.get(i).getCard_unique_id();

                    System.out.println(mapTitle);
                    System.out.println(mapDescription);
                    System.out.println(String.valueOf(i) + " " + mapToLangLon);
                    String[] latlong = null;
                    latlong = mapToLangLon.split(",");
                    double latitude = Double.parseDouble(latlong[0]);
                    double longitude = Double.parseDouble(latlong[1]);

                    System.out.println("Lat: " + String.valueOf(latitude));
                    System.out.println("Long: " + String.valueOf(longitude));

                    Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.ic_water);
                    bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(img);
                    /*
                    if (mapCategory == "Salud") {

                        Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.ic_fire);
                        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(img);

                    } else {


                        Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.ic_water);
                        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(img);

                    }
                    */
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude, longitude))
                            .icon(bitmapDescriptor)
                            .title(mapTitle)
                            .snippet(mapDescription));
                }

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(coatzacoalcos).zoom(13).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String locTitle = marker.getTitle();
                        String locDescription = marker.getId();
                        System.out.println(locTitle);
                        if (previousMarker != null) {
                            previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        }
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        marker.showInfoWindow();
                        previousMarker = marker;

                        return true;
                    }
                });
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == MY_LOCATION_PERMISSION_REQUEST_CODE) {
            // Enable the My Location button if the permission has been granted.
            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                if(mMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
                    populateMyMap();

                    // add markers from database to the map
                }
                mUiSettings.setMyLocationButtonEnabled(true);


                //mMyLocationButtonCheckbox.setChecked(true);
            } else {
                mLocationPermissionDenied = true;
            }

        } else if (requestCode == LOCATION_LAYER_PERMISSION_REQUEST_CODE) {
            // Enable the My Location layer if the permission has been granted.
            if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                if(mMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
                    populateMyMap();

                    // add markers from database to the map
                }
                mMap.setMyLocationEnabled(true);

                //mMyLocationLayerCheckbox.setChecked(true);
            } else {
                mLocationPermissionDenied = true;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Tracking the screen view
        MyApplication.getInstance().trackScreenView("Map Fragment");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_map, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.map_satellite:
                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        mMap = mMap;
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    }
                });
                return true;
            case R.id.map_normal:
                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        mMap = mMap;
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    }
                });
                return true;
            case R.id.map_terrain:
                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        mMap = mMap;
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    }
                });
                return true;
            case R.id.map_hybrid:
                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        mMap = mMap;
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void focusInMap(){

    }
}