package com.example.abdoudriss.atbpfeclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class QrGenerator extends AppCompatActivity implements BackgroundQrgenerator.qrgenimp {
    static String qrcodeget;
    StringRequest rq, rq1, rq2, rq3;
    RequestQueue rqq, rqq1, rqq2, rqq3;
    static ImageView abcd;
    String alphabet = "vw+NXBxy456Oz01CD2lms3PWQRgJKLMefUVabcnA7EYZopFGHIqrhijSTd-(ktu89)";
    String qrcode = "";
    private int combien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generator);
        final QRCodeWriter writer = new QRCodeWriter();

        abcd = (ImageView) findViewById(R.id.abdcd);
        rqq = Volley.newRequestQueue(getApplicationContext());
        rqq1 = Volley.newRequestQueue(getApplicationContext());
        rqq2 = Volley.newRequestQueue(getApplicationContext());
        rqq3 = Volley.newRequestQueue(getApplicationContext());
        rq = new StringRequest(Request.Method.POST, Constants.URL + "qrcoderead.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                qrcodeget = response;
//                Toast.makeText(QrGenerator.this, response, Toast.LENGTH_SHORT).show();
                BackgroundQrgenerator bk = new BackgroundQrgenerator(QrGenerator.this);
                bk.execute();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QrGenerator.this, "connexion error", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", LoginClient.username);


                return params;
            }
        };
        rqq.add(rq);

        rq2 = new StringRequest(Request.Method.POST, Constants.URL + "countbefore.php", new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                combien = Integer.parseInt(response);
                Toast.makeText(QrGenerator.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QrGenerator.this, "connexion error", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", LoginClient.username);


                return params;
            }
        };
        rqq2.add(rq2);
//            //REPONSE PHP
//            @Override
//            public void onResponse(String response) {
//                qrcodeget = response;
////                try {
////                    BitMatrix bitMatrix = writer.encode(response, BarcodeFormat.QR_CODE, 512, 512);
////                    int width = 512;
////                    int height = 512;
////                    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
////                    for (int x = 0; x < width; x++) {
////                        for (int y = 0; y < height; y++) {
////                            if (bitMatrix.get(x, y))
////                                bmp.setPixel(x, y, Color.BLACK);
////                            else
////                                bmp.setPixel(x, y, Color.WHITE);
////                        }
////                    }
//                BackgroundQrgenerator bk = new BackgroundQrgenerator(QrGenerator.this);
//                bk.execute();
////                    abcd.setImageBitmap(BackgroundQrgenerator.bmp);
////                    try {
////                        Thread.sleep(5000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////
////                }
////                catch (WriterException e) {
////                    Log.e("QR ERROR", "" + e);
////
////                }

//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(QrGenerator.this, "You must connect to db", Toast.LENGTH_LONG).show();
//
//            }
//        });
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("qrcode", "33");
//            params.put("reponse", "3");
//
//            return params;
//        }
//        };
//
//
//        rqq.add(rq);


        //}
    }

    @Override
    public void qrgenimp() {
        // Toast.makeText(this, "all good", Toast.LENGTH_SHORT).show();
        Random rn = new Random();
        for (int i = 0; i < 32; i++) {
            qrcode = qrcode + alphabet.charAt(rn.nextInt(alphabet.length()));
        }
//        abcd.setImageBitmap(BackgroundQrgenerator.bmp);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                rq1 = new StringRequest(Request.Method.POST, Constants.URL + "qrcodeupdater.php", new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
//                Toast.makeText(QrGenerator.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(QrGenerator.this, "connexion error", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("qrcode", qrcode);
                        params.put("reponse", qrcodeget);

                        return params;
                    }
                };
                rqq1.add(rq1);

                //HEDHY EL AFTER
                rq3 = new StringRequest(Request.Method.POST, Constants.URL + "countbefore.php", new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        if (Integer.parseInt(response) > combien) {
                            final AlertDialog.Builder builder3 = new AlertDialog.Builder(QrGenerator.this);
                            builder3.setTitle("Successful payment");
                            builder3.setIcon(R.drawable.okok);
                            builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent it = new Intent(QrGenerator.this, MenuClient.class);
                                    startActivity(it);
                                    QrGenerator.this.finish();
                                }
                            });
                            builder3.show();
                            builder3.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    Intent it = new Intent(QrGenerator.this, MenuClient.class);
                                    startActivity(it);
                                    QrGenerator.this.finish();
                                }
                            });

                        } else {
                            final AlertDialog.Builder builder3 = new AlertDialog.Builder(QrGenerator.this);
                            builder3.setTitle("No payement was made");
                            builder3.setIcon(R.drawable.caution);
                            builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent it = new Intent(QrGenerator.this, MenuClient.class);
                                    startActivity(it);
                                    QrGenerator.this.finish();

                                }
                            });
                            builder3.show();


                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(QrGenerator.this, "connexion error", Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", LoginClient.username);


                        return params;
                    }
                };
                rqq3.add(rq3);

                // this code will be executed after 2 seconds


            }
        }, 12000);


    }


}
