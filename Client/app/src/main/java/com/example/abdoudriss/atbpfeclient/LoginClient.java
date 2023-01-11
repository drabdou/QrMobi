package com.example.abdoudriss.atbpfeclient;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginClient extends AppCompatActivity {
    Button btlogin, btinscri;
    String success = "Welcome";
    String reset = "go to reset";
    String errorwrong = "Wrong username or password";
    Intent versmenu;
    StringRequest rq;
    RequestQueue rqq;
    EditText edusername, edpassword;
    TextView txtforgot;
    static String username;
    static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_client);
        rqq = Volley.newRequestQueue(getApplicationContext());
        edusername = (EditText) findViewById(R.id.edusername);
        edpassword = (EditText) findViewById(R.id.edpassword);
        btinscri = (Button) findViewById(R.id.btinscription);
        btlogin = (Button) findViewById(R.id.btlogin);
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   FIELDS NOT FILLED
                if (edusername.equals("")) {
                    edpassword.setText("");
                    edusername.setText("");
                    edusername.setError("Enter your username");
                    edpassword.setError("Enter your password");
                } else {
                    rq = new StringRequest(Request.Method.POST, Constants.URL + "loginclt.php", new Response.Listener<String>() {

                        //REPONSE PHP
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(LoginCommercant.this, response.toString(), Toast.LENGTH_LONG).show();

                            if (response.toString().equals(errorwrong)) {
                                edpassword.setText("");
                                edusername.setText("");
                                edusername.setError("Enter your username");
                                edpassword.setError("Enter your password");
                                //Toast.makeText(LoginClient.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                            }
//
                            //NOT ACTIVATED

                            else if (response.toString().equals("not activated")) {
                                Toast.makeText(LoginClient.this, "activate your account by the code sent to your email", Toast.LENGTH_SHORT).show();
                                versmenu = new Intent(LoginClient.this, ConfirmationCode.class);
                                startActivity(versmenu);
                                LoginClient.this.finish();

                            }

                            //FORGOT

                            else if (response.toString().equals(reset)) {


                                Intent intent = new Intent();
                                intent.setClass(LoginClient.this, ModifPassword.class);
                                intent.putExtra("parametres", edusername.getText().toString());
                                startActivity(intent);
                            }

                            //SUCCESSFUL REGISTRAION
                            else if (response.toString().equals(success)) {
                                Toast.makeText(LoginClient.this, "welcome", Toast.LENGTH_SHORT).show();
                                username = edusername.getText().toString();
                                password = edpassword.getText().toString();
                                versmenu = new Intent(LoginClient.this, MenuClient.class);
                                startActivity(versmenu);


                            }

                            // CONNECTION DB ERROR

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginClient.this, "You must connect to db", Toast.LENGTH_LONG).show();

                        }
                    })
                            //Putting params
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
            }
        });
        btinscri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                versmenu = new Intent(LoginClient.this, InscriptionClient.class);
                startActivity(versmenu);
            }
        });
        txtforgot = (TextView) findViewById(R.id.forgotpass);
        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                versmenu = new Intent(LoginClient.this, ForgotAccount.class);
                startActivity(versmenu);

            }
        });
    }
}

