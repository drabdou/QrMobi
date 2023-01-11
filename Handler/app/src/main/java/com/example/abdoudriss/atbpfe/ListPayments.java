package com.example.abdoudriss.atbpfe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class ListPayments extends AppCompatActivity {
    ListView list;
    private paiement[] pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_payments);
        list = (ListView) findViewById(R.id.list);

//        JsonObject json = new JsonObject();
//        json.addProperty("username", LoginCommercant.usrname);
        final ArrayList<paiement> paiements = new ArrayList<>();
        final AdapterPaiement adapter = new AdapterPaiement(this, paiements);
        Ion.with(ListPayments.this).load(Constants.URL + "historique.php")
                .setBodyParameter("username", LoginCommercant.usrname)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e == null) {
                            // Toast.makeText(MenuClient.this, "OK", Toast.LENGTH_SHORT).show();
                            Log.d("result", result.toString());

                            Gson gson = new Gson();
                            pay = gson.fromJson(result.toString(), paiement[].class);

                            for (int i = 0; i < pay.length; i++) {

                                paiements.add(pay[i]);

                            }
                            list.setAdapter(adapter);
                            if (paiements.isEmpty())
                                 {
                                final AlertDialog.Builder builder3 = new AlertDialog.Builder(ListPayments.this);
                                builder3.setTitle("Non disponible");
                                     builder3.setMessage("Vous n'avez effectué aucune opération de recouvrement ");
                                builder3.setIcon(R.drawable.noaccess);
                                builder3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent it = new Intent(ListPayments.this,Menucommercant.class);
                                        startActivity(it);
                                        ListPayments.this.finish();
                                    }
                                });
                                     builder3.show();

                            }


                        } else {
                            Log.d("erreure", e.toString());
                            Toast.makeText(ListPayments.this, "err cnx", Toast.LENGTH_SHORT).show();


                        }
                        list.setAdapter(adapter);

                    }
                });
    }
}
