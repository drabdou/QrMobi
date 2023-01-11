package com.example.abdoudriss.atbpfeclient;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private commercant[] cmp;
    private ArrayList<String> values;
    private String lang, lat;
    private GoogleMap mMap;
    private LatLng a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        values = new ArrayList<>();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Ion.with(MapsActivity.this).load(Constants.URL + "test.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e == null) {
                            // Toast.makeText(MenuClient.this, "OK", Toast.LENGTH_SHORT).show();
                            Log.d("result", result.toString());

                            Gson gson = new Gson();
                            cmp = gson.fromJson(result.toString(), commercant[].class);

                            for (int i = 0; i < cmp.length; i++) {

                                lat = (cmp[i].getLatitude().toString());
                                lang = cmp[i].getLangitude().toString();
                                Toast.makeText(MapsActivity.this, lang, Toast.LENGTH_SHORT).show();

                                a = new LatLng(Double.parseDouble(lang), Double.parseDouble(lat));
                                mMap.addMarker(new MarkerOptions().position(a).title(cmp[i].getWork()));


                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(a));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(a,10));

                            Log.d("ahawa", cmp[0].getLatitude().toString());


                        } else {

                            Toast.makeText(MapsActivity.this, "err cnx", Toast.LENGTH_SHORT).show();


                        }
                    }
                });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera




    }


}
