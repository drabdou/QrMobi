package com.example.abdoudriss.atbpfeclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;

public class ModifPassword extends AppCompatActivity {

    StringRequest rq;
    RequestQueue rqq;
    EditText edpnvass, edreppass;
    String errtooshort = "Password too short", errnotmatch = "the passwords entered does not match";
    String success = "updated successfully";
    Button btnresetpass;
    String usrname = LoginClient.username;
    Intent et;
    private static final String TAG = "ModifPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_password);
        btnresetpass = (Button) findViewById(R.id.newpasswordReset);
        edpnvass = (EditText) findViewById(R.id.ModifNewpassword);
        edreppass = (EditText) findViewById(R.id.ModifNewpasswordRepeat);
        rqq = Volley.newRequestQueue(getApplicationContext());
        btnresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edpnvass.toString().contains("'") || edreppass.toString().contains("'")) {
                    edpnvass.setText("");
                    edreppass.setText("");
                    Toast.makeText(ModifPassword.this, "avoid the: ' ", Toast.LENGTH_SHORT).show();
                } else {
                    rq = new StringRequest(Request.Method.POST, Constants.URL + "updatepasswordclt.php", new Response.Listener<String>() {

                        //REPONSE PHP
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(ModifPassword.this, response.toString(), Toast.LENGTH_LONG).show();


                            if (response.toString().equals(errtooshort) || (response.toString().equals(errnotmatch))) {
                                edpnvass.setText("");
                                edreppass.setText("");
                                edpnvass.setError("Password must contain 6 caracters at least");
                                edreppass.setError("Passwords entered must match each others");
                            } else if (response.toString().equals(success)) {

                                Intent it = new Intent(ModifPassword.this, MenuClient.class);
                                startActivity(it);
                                ModifPassword.this.finish();

                            }

                        }


                        // CONNECTION DB ERROR


                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ModifPassword.this, "You must connect to db", Toast.LENGTH_LONG).show();

                        }
                    })
                            // EVERYTHING WORKS JUST FINE
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("resetpass", edpnvass.getText().toString());
                            params.put("resetpassrep", edreppass.getText().toString());
                            params.put("username", usrname);
                            return params;
                        }
                    };

                    rqq.add(rq);
                }

            }
        });
    }
}

