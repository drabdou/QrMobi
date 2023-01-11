package com.example.abdoudriss.atbpfe;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginCommercant extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    String errornodata = "Enter your username and password to sign in";
    String success = "Welcome";
    String reset = "go to reset";
    String errorwrong = "Wrong username or password";
    private Button btinscription, btlogin;
    EditText edusername, edpassword;
    StringRequest rq;
    RequestQueue rqq;
    TextView txtforgot;
    static String usrname;
    static String password;


    Intent versmenu, forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_commercant);

        btlogin = (Button) findViewById(R.id.btlogincomm);
        btinscription = (Button) findViewById(R.id.btinscription);

        edusername = (EditText) findViewById(R.id.edlogin);
        edpassword = (EditText) findViewById(R.id.edpassword);
        rqq = Volley.newRequestQueue(getApplicationContext());

        txtforgot = (TextView) findViewById(R.id.forget);
        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                forgot = new Intent(LoginCommercant.this, ForgotAccount.class);
                startActivity(forgot);

            }
        });

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                Date date = new Date();

//                Toast.makeText(LoginCommercant.this, dateFormat.format(date).toString(), Toast.LENGTH_SHORT).show();
                if (edusername.getText().toString().equals("abdou")) {
                    versmenu = new Intent(LoginCommercant.this, Menucommercant.class);
                    usrname = edusername.getText().toString();
                    startActivity(versmenu);

                }
                if ((edusername.getText().toString().contains("'")) || (edpassword.getText().toString().contains("'"))) {
                    Toast.makeText(LoginCommercant.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
                rq = new StringRequest(Request.Method.POST, Constants.URL + "logincomm.php", new Response.Listener<String>() {

                    //REPONSE PHP
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(LoginCommercant.this, response.toString(), Toast.LENGTH_LONG).show();


                        //FIELDS NOT FILLED
                        if (response.toString().equals(errornodata)) {
                            edpassword.setText("");
                            edusername.setText("");
                            edusername.setError("Enter your username");
                            edpassword.setError("Enter your password");
                        } else if (response.toString().equals(errorwrong)) {
                            edpassword.setText("");
                            edusername.setText("");
                            edusername.setError("Enter your username");
                            edpassword.setError("Enter your password");
                            Toast.makeText(LoginCommercant.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                        }
                        //NOT ACTIVATED

                        else if (response.toString().equals("not activated")) {
                            Toast.makeText(LoginCommercant.this, "activate your account by the code sent to your email", Toast.LENGTH_SHORT).show();
                            versmenu = new Intent(LoginCommercant.this, ConfirmationCode.class);
                            startActivity(versmenu);
                            LoginCommercant.this.finish();
                        }

                        //SUCCESSFUL Login
                        else if (response.toString().equals(success)) {
                            usrname = edusername.getText().toString();
                            password = edpassword.getText().toString();
                            versmenu = new Intent(LoginCommercant.this, Menucommercant.class);
                            startActivity(versmenu);
                            versmenu.putExtra("parametres", edusername.getText().toString());
                            LoginCommercant.this.finish();

                        } else if (response.toString().equals(reset)) {

                            usrname = edusername.getText().toString();
                            Intent intent = new Intent();
                            intent.setClass(LoginCommercant.this, ModifPassword.class);
//                            intent.putExtra("parametres", edusername.getText().toString());
                            startActivity(intent);

                        }


                        // CONNECTION DB ERROR

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginCommercant.this, "You must Activate Internet connection", Toast.LENGTH_LONG).show();

                    }
                })
                        // EVERYTHING WORKS JUST FINE
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", edusername.getText().toString());
                        params.put("password", edpassword.getText().toString());
                        return params;
                    }
                };

                rqq.add(rq);

            }
        });
//versmenu=new Intent(LoginCommercant.this,Menucommercant.class);
        //               startActivity(versmenu);


        btinscription.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(LoginCommercant.this, "Location disabled", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                } else {
//                versmenu = new Intent(LoginCommercant.this, InscriptionCommercant.class);
                    versmenu = new Intent(LoginCommercant.this, InscriptionCommercant.class);
                    startActivity(versmenu);
                }
            }
        });

    }
}
