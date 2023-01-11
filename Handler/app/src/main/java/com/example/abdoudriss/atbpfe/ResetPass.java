package com.example.abdoudriss.atbpfe;

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

public class ResetPass extends AppCompatActivity {
    StringRequest rq;
    RequestQueue rqq;
    EditText edpnvass, edreppass, edoldpass;
    String errtooshort = "Password too short", errnotmatch = "the passwords entered does not match";
    String success = "updated successfully";
    Button btnresetpass;
    String usrname = LoginCommercant.usrname;
    String password = LoginCommercant.password;
    Intent et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        rqq = Volley.newRequestQueue(getApplicationContext());
        edoldpass = (EditText) findViewById(R.id.oldpass);
        edpnvass = (EditText) findViewById(R.id.resetnewpass);
        edreppass = (EditText) findViewById(R.id.ResetNewpasswordRepeat);
        btnresetpass = (Button) findViewById(R.id.newpasswordReset);
//        final Bundle param = this.getIntent().getExtras();
//        usrname = (String) param.get("parametres");
        btnresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edpnvass.toString().contains("'") || edreppass.toString().contains("'")) {
                    edpnvass.setText("");
                    edreppass.setText("");
                    Toast.makeText(ResetPass.this, "avoid the: ' ", Toast.LENGTH_SHORT).show();
                } else if (!(edoldpass.getText().toString().equals(password))) {
                    // Toast.makeText(ResetPass.this,password, Toast.LENGTH_SHORT).show();
                    edoldpass.setError("Wrong password");


                } else if (edoldpass.getText().toString().equals(edpnvass.getText().toString())) {
                    edpnvass.setText("");
                    edreppass.setText("");
                    edpnvass.setError("you must enter a new password");
                } else {
                    rq = new StringRequest(Request.Method.POST, Constants.URL + "updatepassword.php", new Response.Listener<String>() {

                        //REPONSE PHP
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(ResetPass.this, response.toString(), Toast.LENGTH_LONG).show();


                            if (response.toString().equals(errtooshort) || (response.toString().equals(errnotmatch))) {
                                edpnvass.setText("");
                                edreppass.setText("");
                                edpnvass.setError("Password must contain 6 caracters at least");
                                edreppass.setError("Passwords entered must match each others");
                            } else if (response.toString().equals(success)) {

                                Intent it = new Intent(ResetPass.this, Menucommercant.class);
                                startActivity(it);
                                ResetPass.this.finish();

                            }

                        }


                        // CONNECTION DB ERROR


                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ResetPass.this, "You must connect to db", Toast.LENGTH_LONG).show();

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
