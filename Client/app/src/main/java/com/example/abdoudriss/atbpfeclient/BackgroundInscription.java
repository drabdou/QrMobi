package com.example.abdoudriss.atbpfeclient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;


/**
 * Created by Abdou Driss on 21/03/2017.
 */

public class BackgroundInscription extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "BackgroundInscription";

    public interface tallainscri {

        void tallainscri();

    }

    ProgressDialog dialog;
    InscriptionClient ins = new InscriptionClient();
    private tallainscri listener;


    public BackgroundInscription(InscriptionClient activity) {
        dialog = new ProgressDialog(activity);
        listener = activity;
    }


    @Override
    protected void onPreExecute() {
        dialog.setMessage("sending confirmation code, please wait.");

        dialog.setTitle("Sending...");
        dialog.setIcon(R.drawable.zz);
        dialog.show();
    }


    @Override
    protected void onPostExecute(Void result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
            listener.tallainscri();
        }
    }


    @Override
    protected Void doInBackground(Void... params) {
        Mail m = new Mail("arabtunisanbank@gmail.com", "arabtunisian");

        String[] toArr = {ins.emailto};
        m.set_to(toArr);
        m.set_from("arabtunisianbank");
        m.set_subject("This is an email sent from atb containing a confirmation code of your email adress ");
        m.set_body(ins.confirmcode);

        try {
            m.send();

        } catch (Exception e) {
            //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
            Log.e("MailApp", "Could not send email, check internet connection", e);
        }
        return null;
    }


}
