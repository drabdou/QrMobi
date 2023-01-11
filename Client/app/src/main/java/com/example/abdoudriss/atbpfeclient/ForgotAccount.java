package com.example.abdoudriss.atbpfeclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

public class ForgotAccount extends AppCompatActivity implements backgroundForgot.talla {
    EditText forgotemail;
    String password;
    Button btrecover;
    StringRequest rq;
    RequestQueue rqq;
    static String strforgot;
    static String strpassword;
    ProgressDialog pr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_account);
        pr = new ProgressDialog(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        forgotemail = (EditText) findViewById(R.id.forgotemail);
        btrecover = (Button) findViewById(R.id.forgotrecover);
        rqq = Volley.newRequestQueue(getApplicationContext());
        btrecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
//            progress=new ProgressDialog(ForgotAccount.this);
//            progress.setMessage("Loading");
//            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progress.setIndeterminate(true);
//            progress.show();

                if ((forgotemail.getText().toString().contains("'")) || (!(forgotemail.getText().toString().contains("@"))) || (!forgotemail.getText().toString().contains("."))) {
                    forgotemail.setError("Invalid Email adress");
                } else {
                    strforgot = forgotemail.getText().toString();

                    rq = new StringRequest(Request.Method.POST, Constants.URL + "forgotclt.php", new Response.Listener<String>() {

                        //REPONSE PHP
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(ForgotAccount.this, response.toString(), Toast.LENGTH_LONG).show();


                            //SUCCESSFUL REGISTRAION
                            if (response.toString().contains("RBK2V5RT8")) {

                                String sub = response.toString().substring(0, (response.length() - 9));
                                strpassword = sub;

                                backgroundForgot task = new backgroundForgot(ForgotAccount.this);
                                task.execute();


                                // Toast.makeText(ForgotAccount.this, task.getStatus().toString(), Toast.LENGTH_SHORT).show();

                                //  Toast.makeText(ForgotAccount.this, "Email sent", Toast.LENGTH_SHORT).show();

//                        Intent versmenu = new Intent(ForgotAccount.this,LoginCommercant.class);
//                        startActivity(versmenu);
//                        ForgotAccount.this.finish();
                                // Toast.makeText(ForgotAccount.this, a, Toast.LENGTH_SHORT).show();
//    Mail m = new Mail("drissabdouu@gmail.com","26 754 522 A");
//
//                        String[] toArr = {strforgot};
//                        m.set_to(toArr);
//                        m.set_from("drissabdouu@gmail.com");
//                        m.set_subject("This is an email sent from atb containing the password of your account qrmobi");
//                        m.set_body(strpassword);
//
//                        try {
//
//
//                            // m.addAttachment("/sdcard/filelocation");
//
//                            if (m.send()) {
//                                Intent it1 = new Intent(ForgotAccount.this, LoginCommercant.class);
//                                startActivity(it1);
//                                ForgotAccount.this.finish();
//                                Toast.makeText(ForgotAccount.this, "An email containing your password was sent to you", Toast.LENGTH_LONG).show();
//
//                                strpassword = "";
//                                progress.dismiss();
//
//                            }
//                            else
//                                Toast.makeText(ForgotAccount.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                        }
//                        catch(Exception e) {
//                            //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
//                            Log.e("MailApp", "Could not send email, check internet connection", e);
//                     }


                            } else {

                                Toast.makeText(ForgotAccount.this, "The email adress is not valid", Toast.LENGTH_SHORT).show();
                                forgotemail.setText("");
                                forgotemail.setError("type email associated with your account");

                            }


                            // CONNECTION DB ERROR

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ForgotAccount.this, "Check out internet connection", Toast.LENGTH_LONG).show();

                        }
                    })
                            // EVERYTHING WORKS JUST FINE
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("email", forgotemail.getText().toString());
                            return params;
                        }
                    };

                    rqq.add(rq);
                }

            }

        });
    }

    @Override
    public void talla() {
        Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show();
        Intent it1 = new Intent(ForgotAccount.this, LoginClient.class);
        startActivity(it1);
        ForgotAccount.this.finish();

    }
}