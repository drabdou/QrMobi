package com.example.abdoudriss.atbpfe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Menucommercant extends AppCompatActivity {
    private Button btpayer, btrevenu, bthisorique, btmodifierpin;
    Intent vers;
    String  usrname=LoginCommercant.usrname;
    StringRequest rq;
    RequestQueue rqq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menucommercant);
        btpayer = (Button) findViewById(R.id.btpayer);
        btmodifierpin = (Button) findViewById(R.id.btPin);
        btrevenu = (Button) findViewById(R.id.btRevenu);
        bthisorique=(Button)findViewById(R.id.btHistorique);
        final DateFormat dateFormat = new SimpleDateFormat("MM");
        final Date date = new Date();
        rqq = Volley.newRequestQueue(getApplicationContext());
        btpayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vers = new Intent(Menucommercant.this, SaisieMontant.class);
                vers.putExtra("parametres", usrname);
                startActivity(vers);


            }
        });


        btmodifierpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vers = new Intent(Menucommercant.this, ResetPass.class);
                startActivity(vers);
            }
        });
        btrevenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rq = new StringRequest(Request.Method.POST, Constants.URL + "revenu.php", new Response.Listener<String>() {

                    //REPONSE PHP
                    @Override
                    public void onResponse(String response) {
                        final AlertDialog.Builder builder3 = new AlertDialog.Builder(Menucommercant.this);
                        builder3.setTitle("Le revenu du mois : ");
                        if (response.toString().isEmpty())
                            builder3.setMessage("    0 DT");
                        else
                            builder3.setMessage("         " + response.toString() + " DT");
                        builder3.setIcon(R.drawable.atb);
                        builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder3.show();


                    }

                    // CONNECTION DB ERROR


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errrrrrr",error.toString());
                        Toast.makeText(Menucommercant.this, "You must connect to db", Toast.LENGTH_LONG).show();

                    }
                })
                        //Putting params
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("username", usrname);
                        params.put("month", dateFormat.format(date).toString());
                        return params;
                    }
                };

                rqq.add(rq);


            }
        });
        bthisorique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vers=new Intent(Menucommercant.this,ListPayments.class);
                startActivity(vers);
            }
        });


    }
}
