package com.example.abdoudriss.atbpfeclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuClient extends AppCompatActivity {
    Button btqr, bthistorique, btmapcom, btmodif, btdepenses;
    Intent it;
    String username = LoginClient.username;
    String password = LoginClient.password;
    private String m_Text = "";
    StringRequest rq;
    RequestQueue rqq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_client);
        btqr = (Button) findViewById(R.id.btqr);
        btmodif = (Button) findViewById(R.id.resetpass);
        bthistorique = (Button) findViewById(R.id.btHistorique);
        btmapcom = (Button) findViewById(R.id.btmapcommercant);
        btdepenses = (Button) findViewById(R.id.depense);
        final DateFormat dateFormat = new SimpleDateFormat("MM");
        final Date date = new Date();
        rqq = Volley.newRequestQueue(getApplicationContext());
        btqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(MenuClient.this);
                builder.setTitle("Type your password");

// Set up the input
                final EditText input = new EditText(MenuClient.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);
                builder.setIcon(R.drawable.zz);


// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        if (m_Text.equals(password)) {
                            it = new Intent(MenuClient.this, QrGenerator.class);
                            startActivity(it);
                        } else {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(MenuClient.this);
                            builder1.setIcon(R.drawable.caution);
                            builder1.setTitle("ERROR");
                            builder1.setMessage("   Wrong password");
                            AlertDialog alert1 = builder1.create();
                            alert1.show();

                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


            }
        });
        btmodif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                it = new Intent(MenuClient.this, ResetPass.class);
                startActivity(it);

            }
        });
        btmapcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                it = new Intent(MenuClient.this, MapsActivity.class);
                startActivity(it);

            }
        });
        btdepenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MenuClient.this, dateFormat.format(date), Toast.LENGTH_SHORT).show();
                rq = new StringRequest(Request.Method.POST, Constants.URL + "depenses.php", new Response.Listener<String>() {

                    //REPONSE PHP
                    @Override
                    public void onResponse(String response) {
                        final AlertDialog.Builder builder3 = new AlertDialog.Builder(MenuClient.this);
                        builder3.setTitle("Somme dépensée ce mois : ");
                        if (response.toString().isEmpty())
                            builder3.setMessage("    0 DT");
                        else
                            builder3.setMessage("         " + response.toString() + " DT");
                        builder3.setIcon(R.drawable.zz);
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
                        Toast.makeText(MenuClient.this, "You must connect to db", Toast.LENGTH_LONG).show();

                    }
                })
                        //Putting params
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("username", username);
                        params.put("month", dateFormat.format(date).toString());
                        return params;
                    }
                };

                rqq.add(rq);


            }


        });
    bthistorique.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            it=new Intent(MenuClient.this, ListPayments.class);
            startActivity(it);


        }
    });

    }
}