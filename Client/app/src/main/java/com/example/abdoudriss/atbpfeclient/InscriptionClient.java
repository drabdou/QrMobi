package com.example.abdoudriss.atbpfeclient;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class InscriptionClient extends AppCompatActivity implements BackgroundInscription.tallainscri {
    private EditText ed1, ed2, ed3, ed4, ed5, ed6, ed7;
    private Button btok, btretour;
    private String success = "he exists in atbpfe.Registered Successfully";
    String alreadyReg = "It seems that you are already registred";
    String usernameExists = "username exists", emailexists = "email exists";
    private String errordosentexist = "he dosent exists in atbpfe";
    static String confirmcode = "";
    static String emailto;

    Intent intentretour;
    StringRequest rq;
    RequestQueue rqq;
    DatePickerDialog dpd;
    String alphabet = "vw+NXBxy456Oz01CD2lms3PWQRgJKLMefUVabcnA7EYZopFGHIqrhijSTd-(ktu89)";
    String qrcode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_client);
        btok = (Button) findViewById(R.id.btlok);
        btretour = (Button) findViewById(R.id.btretour);
        ed1 = (EditText) findViewById(R.id.inscriusername);
        ed2 = (EditText) findViewById(R.id.inscripassword);
        ed3 = (EditText) findViewById(R.id.inscrinvpassword);
        ed4 = (EditText) findViewById(R.id.inscrinumcarte);
        ed5 = (EditText) findViewById(R.id.inscridate);
        ed6 = (EditText) findViewById(R.id.inscricvv);
        ed7 = (EditText) findViewById(R.id.inscriemail);
        Random rn = new Random();
        for (int i = 0; i < 6; i++) {
            int answer = rn.nextInt(10);
            confirmcode = confirmcode + answer;
        }
        for (int i = 0; i < 32; i++) {
            qrcode = qrcode + alphabet.charAt(rn.nextInt(alphabet.length()));
            Log.d("QRcode", qrcode);
        }

        ed5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                if (ed5.isFocused()) {
                    dpd = new DatePickerDialog(InscriptionClient.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    // set day of month , month and year value in the edit text
                                    ed5.setText((monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    dpd.show();

                }
            }
        });


        rqq = Volley.newRequestQueue(getApplicationContext());

        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ed1.getText().toString().equals("")) || (ed2.getText().toString().equals("")) || (ed3.getText().toString().equals("")) || (ed4.getText().toString().equals("")) || (ed5.getText().toString().equals("")) || (ed6.getText().toString().equals("")) || (ed7.getText().toString().equals(""))) {
                    Toast.makeText(InscriptionClient.this, "Please fill in with all informations", Toast.LENGTH_LONG).show();


                } else if ((ed1.getText().toString().contains("'")) || (ed2.getText().toString().contains("'")) || (ed3.getText().toString().contains("'")) || (ed7.getText().toString().contains("'"))) {
                    Toast.makeText(InscriptionClient.this, "Please avoid this character : ' ", Toast.LENGTH_LONG).show();


                } else if (ed1.getText().toString().length() < 5) {
                    Toast.makeText(InscriptionClient.this, "Username is too short", Toast.LENGTH_SHORT).show();
                    ed1.setError("username must contain 5 caracters at least");
                } else if (!((ed7.getText().toString().contains(("@"))) && (ed7.getText().toString().contains(("."))))) {
                    Toast.makeText(InscriptionClient.this, "Email adress is not valid", Toast.LENGTH_LONG).show();
                    ed7.setText("");
                    ed7.setError("Please give a valid email adress");
                } else if (ed2.getText().toString().length() < 6) {
                    ed2.setText("");
                    ed3.setText("");
                    ed2.setError("password must contain 6 caracters at least");

                } else if (!(ed2.getText().toString().equals(ed3.getText().toString()))) {
                    ed2.setText("");
                    ed3.setText("");
                    ed2.setError("passwords must matches each others");
                    ed3.setError("passwords must matches each others");

                } else if (ed4.getText().toString().length() < 16) {
                    ed4.setText("");
                    ed4.setError("Please enter the 16 digits of your card");
                } else if (ed6.getText().toString().length() < 3) {
                    ed6.setError("cvv contains 3 digits");

                } else {

                    rq = new StringRequest(Request.Method.POST, Constants.URL + "insertcltverif.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(InscriptionClient.this, response.toString(), Toast.LENGTH_SHORT).show();
                            if (response.equals(usernameExists)) {
                                ed1.setText("");
                                ed1.setError("");
                                Toast.makeText(InscriptionClient.this, "Sorry,that username is already taken", Toast.LENGTH_LONG).show();
                            } else if (response.equals(alreadyReg)) {
                                ed4.setError("This card is already registered with another account");
                                Toast.makeText(InscriptionClient.this, "It seems that you are already registererd", Toast.LENGTH_LONG).show();

                            } else if (response.equals(emailexists)) {
                                ed7.setText("");
                                ed7.setError("Enter a valid email adress");
                                Toast.makeText(InscriptionClient.this, "Email already registered with another account", Toast.LENGTH_LONG).show();
                            } else if (response.equals(errordosentexist)) {
                                Toast.makeText(InscriptionClient.this, "This card dosen't exist", Toast.LENGTH_SHORT).show();
                                ed4.setError("Are you sure of the given digits ?");
                                ed5.setError("Are you sure of the given date ?");
                                ed6.setError("Are you sure of the given digits ?");

                            } else if (response.equals("dateval mouch kifkif")) {
                                ed5.setError("Are you sure of it ?");
                            } else if (response.equals("cvv mouch kifkif")) {
                                ed6.setError("Are you sure of it ?");
                            } else if (response.equals(success)) {
                                emailto = ed7.getText().toString();
                                BackgroundInscription bk = new BackgroundInscription(InscriptionClient.this);
                                bk.execute();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(InscriptionClient.this, "connexion error", Toast.LENGTH_SHORT).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username", ed1.getText().toString());
                            params.put("password", ed2.getText().toString());
                            params.put("qrcode", qrcode);
                            params.put("num_carte", ed4.getText().toString());
                            params.put("dateval", ed5.getText().toString());
                            params.put("cvv", ed6.getText().toString());
                            params.put("email", ed7.getText().toString());
                            params.put("code", confirmcode);

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
                intentretour = new Intent(InscriptionClient.this, LoginClient.class);
                startActivity(intentretour);
            }
        });
    }

    @Override
    public void tallainscri() {
        confirmcode = "";
        Toast.makeText(this, "An email with confirmation code was sent to you", Toast.LENGTH_LONG).show();
        Intent it1 = new Intent(InscriptionClient.this, ConfirmationCode.class);
        startActivity(it1);

        InscriptionClient.this.finish();
    }
}
