//package com.example.abdoudriss.atbpfe;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//
//public class Liste_comptes extends AppCompatActivity {
//ListView lst;
// compte_commercant[] cmp;
//    ArrayList<BigInteger> values;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_liste_comptes);
//   lst=(ListView)findViewById(R.id.lista);
//        values=new ArrayList<>();
//        Ion.with(Liste_comptes.this).load("http://192.168.8.101:888/atbqrmobi/get.php")
//                .asJsonArray()
//                .setCallback(new FutureCallback<JsonArray>() {
//            @Override
//            public void onCompleted(Exception e, JsonArray result) {
//                if (e==null)
//                {
//                    Toast.makeText(Liste_comptes.this, "OK", Toast.LENGTH_SHORT).show();
//                    Log.d("result", result.toString());
//                    Gson gson = new Gson();
//                    cmp = gson.fromJson(result.toString(), compte_commercant[].class);
//                    for (int i=0; i<cmp.length; i++){
//                        values.add(( cmp[i].getRib()));
//                    }
//                    ArrayAdapter<BigInteger> adbp =new ArrayAdapter<BigInteger>(Liste_comptes.this,android.R.layout.simple_list_item_1,values);
//                    lst.setAdapter(adbp);
//                }
//                else {
//                    Log.e("result",e.getMessage());
//                    Toast.makeText(Liste_comptes.this, "err cnx", Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//

//        });
//    }
//}
