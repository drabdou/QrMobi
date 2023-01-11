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

public class ConfirmationCode extends AppCompatActivity {

    private EditText ed1;
    private Button bt1;
    StringRequest rq;
    RequestQueue rqq;
    Intent vermenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_code);
        ed1 = (EditText) findViewById(R.id.ConfirmationCodeEdittext);
        bt1 = (Button) findViewById(R.id.ButtonConfirmation);
        rqq = Volley.newRequestQueue(getApplicationContext());
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rq = new StringRequest(Request.Method.POST, Constants.URL + "confirmation.php", new Response.Listener<String>() {

                    //REPONSE PHP
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().equals("Successful registration")) {
                            Toast.makeText(ConfirmationCode.this, "Successful registration", Toast.LENGTH_LONG).show();

                            vermenu = new Intent(ConfirmationCode.this, LoginCommercant.class);
                            startActivity(vermenu);
                            ConfirmationCode.this.finish();

                        } else if (response.toString().equals("Wrong code")) {
                            Toast.makeText(ConfirmationCode.this, "Wrong code ", Toast.LENGTH_SHORT).show();
                            ed1.setText("");
                            ed1.setError("check for code in your email inbox");
                        }

                        // CONNECTION DB ERROR

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ConfirmationCode.this, "You must connect to db", Toast.LENGTH_LONG).show();

                    }
                })
                        // EVERYTHING WORKS JUST FINE
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("code", ed1.getText().toString());

                        return params;
                    }
                };

                rqq.add(rq);

            }
        });
    }
}
