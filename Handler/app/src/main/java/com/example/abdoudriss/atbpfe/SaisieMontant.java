package com.example.abdoudriss.atbpfe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.google.zxing.Result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SaisieMontant extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    static String montant;
    private Button btscanner;
    private EditText edsaisie;
    Intent versqr;
    StringRequest rq;
    RequestQueue rqq;
    private ZXingScannerView mScannerView;
    String usrname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_montant);
        btscanner = (Button) findViewById(R.id.btsaisie);
        edsaisie = (EditText) findViewById(R.id.editsaisiemnt);
        final Bundle param = this.getIntent().getExtras();
        usrname = (String) param.get("parametres");
        rqq = Volley.newRequestQueue(getApplicationContext());
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);


            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
            } else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }


    public void QrScanner(View view) {

        if (edsaisie.getText().toString().equals("")) {
            edsaisie.setSelected(true);
            edsaisie.setError("you must enter an amount ");
        } else {
            montant = edsaisie.getText().toString();
            mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
            setContentView(mScannerView);
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();         // Start camera
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //   mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(final Result rawResult) {

        // Do something with the result here

//        Log.e("handler", rawResult.getText()); // Prints scan results
//        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
//
//        rq = new StringRequest(Constants.URL + "qrcoderead.php", new Response.Listener<String>() {
//
//            //REPONSE PHP
//            @Override
//            public void onResponse(String response) {
//
//                if (rawResult.getText().equals(response.toString()))
//                    Toast.makeText(SaisieMontant.this, "fuck yeah", Toast.LENGTH_SHORT).show();
//// show the scanner result into dialog box.
//                AlertDialog.Builder builder = new AlertDialog.Builder(SaisieMontant.this);
//                builder.setTitle("Scan Result");
//                builder.setMessage(rawResult.getText());
//                AlertDialog alert1 = builder.create();
//                alert1.show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SaisieMontant.this, "You must connect to db", Toast.LENGTH_LONG).show();
//
//            }
//
//
//        });
//
//
//       rqq.add(rq);


        // If you would like to resume scanning, call this method below:
        // mScannerView.resumeCameraPreview(this);

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)
        final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        final Date date = new Date();
        //Toast.makeText(SaisieMontatnt.this.this, dateFormat.format(date).toString(), Toast.LENGTH_SHORT).show();
        rq = new StringRequest(Request.Method.POST, Constants.URL + "qrreadcomm.php", new Response.Listener<String>() {

            //REPONSE PHP
            @Override
            public void onResponse(String response) {
                Log.d("aahhhh",response.toString());
                if (response.toString().equals("ok")) {

// show the scanner result into dialog box.
                    AlertDialog.Builder builder = new AlertDialog.Builder(SaisieMontant.this);
                    builder.setTitle("Scan finished");
                    builder.setMessage("Successful payement");
                    builder.setIcon(R.drawable.okok);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            versqr = new Intent(SaisieMontant.this, Menucommercant.class);
                            startActivity(versqr);
                            SaisieMontant.this.finish();

                        }
                    });

                    AlertDialog alert1 = builder.create();
                    alert1.show();
                } else if (response.toString().equals("solde insuffisant")) {

// show the scanner result into dialog box.
                    AlertDialog.Builder builder = new AlertDialog.Builder(SaisieMontant.this);
                    builder.setTitle("Scan finished");
                    builder.setMessage("Insufficient balance");
                    builder.setIcon(R.drawable.stop);
                    builder.setPositiveButton("retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mScannerView.resumeCameraPreview(SaisieMontant.this);

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            versqr = new Intent(SaisieMontant.this, Menucommercant.class);
                            startActivity(versqr);
                            SaisieMontant.this.finish();
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SaisieMontant.this);
                    builder.setTitle("CAUTION");
                    builder.setMessage("User not verified");
                    builder.setIcon(R.drawable.stop);

                    builder.setPositiveButton("retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mScannerView.resumeCameraPreview(SaisieMontant.this);

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            versqr = new Intent(SaisieMontant.this, Menucommercant.class);
                            startActivity(versqr);
                            SaisieMontant.this.finish();
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert1 = builder.create();
                    alert1.show();

                }

            }
            // CONNECTION DB ERROR


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SaisieMontant.this, "You must connect to db", Toast.LENGTH_LONG).show();

            }
        })
                // EVERYTHING WORKS JUST FINE
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("qrcode", rawResult.getText().toString());
                params.put("solde", montant);
                params.put("usrname", LoginCommercant.usrname);
                params.put("datep", dateFormat.format(date).toString());
                return params;
            }
        };

        rqq.add(rq);


    }

}
