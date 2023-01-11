package com.example.abdoudriss.atbpfe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class InscriptionCommercant extends AppCompatActivity implements BackgroundInscription.tallainscri, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private Location mlocation;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 15000;  /* 15 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */
    //ERRORS
    private String errordosentexist = "he dosent exists in atbpfe";
    private String errorlastname = "Name too short, please enter your real name";
    private String erroremail = "Email not valid";
    private String errorusernameshort = "Username is too short";
    private String errorpasswordshort = "The password you entered is too short";
    private String errorrib = "Please enter your RIB  (20 digits)";
    private String errpasswordmatch = "the passwords entered does not match";
    private String errallinformations = "You must enter all informations please";
    private String erralreadyregistred = "It seems that you are already registred";
    //successful registration
    private String success = "he exists in atbpfe.Registered Successfully";
    static String confirmcode = "";
    static String emailto;
    private static final String TAG = "InscriptionCommercant";
    //FIELDS
    private EditText ed1, ed2, ed3, ed4, ed5, ed6, ed7;
    //BUTTONS
    private Button btok, btretour;
    //
    //INTENT
    protected GoogleApiClient mGoogleApiClient;
    Intent intentretour, intentmenu;
    //STRREQUESTS
    StringRequest rq;
    RequestQueue rqq;

    protected Location mLastLocation, mCurrentLocation;

    protected String mLatitude;
    protected String mLongitude;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_commercant);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        buildGoogleApiClient();
        Random rn = new Random();
        while (confirmcode.length() < 6) {
            int answer = rn.nextInt(10);
            confirmcode = confirmcode + answer;

        }

        Log.d(TAG, "code " + confirmcode);
        btok = (Button) findViewById(R.id.btlok);
        btretour = (Button) findViewById(R.id.btretour);

        ed1 = (EditText) findViewById(R.id.inscrinom);
        ed2 = (EditText) findViewById(R.id.inscriemail);
        ed3 = (EditText) findViewById(R.id.inscriusername);
        ed4 = (EditText) findViewById(R.id.inscripassword);
        ed5 = (EditText) findViewById(R.id.inscririb);
        ed6 = (EditText) findViewById(R.id.inscrinvpassword);
        ed7 = (EditText) findViewById(R.id.inscritype);


        rqq = Volley.newRequestQueue(getApplicationContext());
        Log.d("loc", String.valueOf(mLatitude));
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

            mLatitude = String.valueOf(location.getLatitude());
            mLongitude = String.valueOf(location.getLongitude());
            // Toast.makeText(this, "w hedhy 2"+mLatitude, Toast.LENGTH_SHORT).show();
        }

    }

    //SET ERROR
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //BUTTON ON CLICK LISTENER
                    ed7.setError("eg: restaurant,cafe,store");
                    btok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            Toast.makeText(InscriptionCommercant.this, "latitude" + mLatitude, Toast.LENGTH_SHORT).show();
                            //FIELDS NOT FILLED

                            if (ed1.getText().toString().isEmpty() || ed2.getText().toString().isEmpty() || ed3.getText().toString().isEmpty() || ed4.getText().toString().isEmpty() || ed5.getText().toString().isEmpty() || ed6.getText().toString().isEmpty() || ed7.getText().toString().isEmpty()) {
                                Toast.makeText(InscriptionCommercant.this, "you must provide all informations", Toast.LENGTH_SHORT).show();
                            }

                            // NOT YOUR LAST NAME
                            else if (ed1.getText().toString().length() < 2) {
                                ed1.setText("");
                                ed1.setError("Please enter your real name");
                            }
                            //ERROR FIRSTNAME SHORT

                            else if (ed2.getText().toString().length() < 4) {
                                ed2.setText("");
                                ed2.setError("Please enter a valid Email address");
                            } else if ((ed1.getText().toString().contains("'")) || (ed2.getText().toString().contains("'")) || (ed3.getText().toString().contains("'")) || (ed4.getText().toString().contains("'")) || (ed5.getText().toString().contains("'")) || (ed6.getText().toString().contains("'"))) {
                                Toast.makeText(InscriptionCommercant.this, "please avoid the this caracter: ' ", Toast.LENGTH_SHORT).show();
                            } else if (!((ed2.getText().toString().contains("@")) && (ed2.getText().toString().contains(".")))) {
                                Toast.makeText(InscriptionCommercant.this, "Please give a valid Email adress", Toast.LENGTH_SHORT).show();
                                ed2.setText("");
                                ed2.setError("Please give a valid email adress");
                            }  //PASSWORD IS TOO SHORT
                            else if (ed4.getText().toString().length() < 6) {
                                ed6.setText("");
                                ed4.setText("");
                                ed4.setError("password must contain at least 6 caracters");
                                ed6.setError("passwords entered must match each other");

                            }

                            //USERNAME IS TOO SHORT
                            else if (ed3.getText().length() < 4) {
                                ed3.setText("");
                                ed3.setError("Username must contain at least 4 caracters");
                            }

                            // RIB NOT 20
                            else if (ed5.getText().toString().length() < 20) {
                                ed5.setText("");
                                ed5.setError("RIB contains 20 digits");
                            }

                            // PASSWORDS  DO NOT MATCH
                            else if (!(ed4.getText().toString().equals(ed6.getText().toString()))) {
                                ed6.setText("");
                                ed4.setText("");
                                ed6.setError("passwords entered should match each other");
                                ed4.setError("passwords entered should match each other");
                            } else {
                                rq = new StringRequest(Request.Method.POST, Constants.URL + "insertcommwithverf.php", new Response.Listener<String>() {

                                    //REPONSE PHP
                                    @Override
                                    public void onResponse(String response) {
//                            if (!(response.toString().equals(success)))
                                        //Toast.makeText(InscriptionCommercant.this, response.toString(), Toast.LENGTH_SHORT).show();
                                       // Toast.makeText(InscriptionCommercant.this, response.toString(), Toast.LENGTH_SHORT).show();

                                        if (response.toString().equals("username duplicated")) {
                                            ed3.setError("Username already taken");
                                        } else if (response.toString().equals(errordosentexist)) {
                                            ed5.setError("Ce rib n'est pas valide");
                                            Toast.makeText(InscriptionCommercant.this, "This account dosen't exist", Toast.LENGTH_SHORT).show();
                                        }
                                        //ALREADY REGISTERED
                                        else if (response.toString().equals(erralreadyregistred)) {

                                            Toast.makeText(InscriptionCommercant.this, "It seems that you are already registered", Toast.LENGTH_SHORT).show();


                                        }


                                        //SUCCESSFUL REGISTRAION
                                        else if (response.toString().equals(success)) {

                                            emailto = ed2.getText().toString();

                                            /////////////////////////////////////////////
                                            BackgroundInscription bk = new BackgroundInscription(InscriptionCommercant.this);
                                            bk.execute();


//                            intentmenu=new Intent(InscriptionCommercant.this,ConfirmationCode.class);
//                            startActivity(intentmenu);

                                        }


                                    }

                                    // CONNECTION DB ERROR

                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(InscriptionCommercant.this, "You must connect to db", Toast.LENGTH_LONG).show();

                                    }
                                })
                                        // EVERYTHING WORKS JUST FINE
                                {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();

                                        params.put("nom", ed1.getText().toString());
                                        params.put("email", ed2.getText().toString());
                                        params.put("username", ed3.getText().toString());
                                        params.put("password", ed4.getText().toString());
                                        params.put("nvpassword", ed6.getText().toString());
                                        params.put("rib", ed5.getText().toString());
                                        params.put("code", confirmcode);
                                        params.put("latitude", mLatitude);
                                        params.put("langitude", mLongitude);
                                        params.put("work", ed7.getText().toString());
                                        return params;

                                    }
                                };
                                rqq.add(rq);


                            }


                        }


                    });
                    btretour.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intentretour = new Intent(InscriptionCommercant.this, LoginCommercant.class);
                            startActivity(intentretour);
                            InscriptionCommercant.this.finish();
                        }
                    });

                    Log.d("locationTest", String.valueOf(mLatitude) + String.valueOf(mLongitude));
                }

                // permission was granted, yay! Do the
                // contacts-related task you need to do.

                else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.


                }
            }
            return;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(InscriptionCommercant.this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {


//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//
//
//        if (mLastLocation != null) {
//            mLatitude = mLastLocation.getLatitude();
//            mLongitude = mLastLocation.getLongitude();
//            Toast.makeText(this, String.valueOf(mLatitude) + "L" + String.valueOf(mLongitude), Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(this, "no_location_detected", Toast.LENGTH_LONG).show();
//        }
//        Log.d("loc2", String.valueOf(mLatitude));
            mlocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


            if (mlocation != null) {
//                Toast.makeText(this, "ahaya"+mlocation.getLatitude(), Toast.LENGTH_SHORT).show();
            }

            startLocationUpdates();

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void tallainscri() {
        confirmcode = "";
        Toast.makeText(this, "An email with confirmation code was sent to you", Toast.LENGTH_LONG).show();
        Intent it1 = new Intent(InscriptionCommercant.this, ConfirmationCode.class);
        startActivity(it1);

        InscriptionCommercant.this.finish();

    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Enable Permissions", Toast.LENGTH_LONG).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);


    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }


    public void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi
                    .removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }
}
