package com.example.abdoudriss.atbpfe;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Abdou Driss on 19/03/2017.
 */

public class backgroundForgot extends AsyncTask<Void, Void, Void> {


    public interface talla {
        void talla();
    }

    private talla listener;
    ProgressDialog dialog;
    ForgotAccount a = new ForgotAccount();


    public backgroundForgot(ForgotAccount activity) {
        dialog = new ProgressDialog(activity);
        listener = activity;

    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("getting your password, please wait.");

        dialog.setTitle("Sending...");
        dialog.setIcon(R.drawable.atb);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void result) {
        if (dialog.isShowing()) {

            dialog.dismiss();
            listener.talla();


        }
    }

    @Override
    protected Void doInBackground(Void... params) {


        Mail m = new Mail("arabtunisanbank@gmail.com", "arabtunisian");

        String[] toArr = {a.strforgot};
        m.set_to(toArr);
        m.set_from("arabtunisanbank@gmail.com");
        m.set_subject("This is an email sent from atb containing the password of your account qrmobi");
        m.set_body(a.strpassword);
        try {
            m.send();
        } catch (Exception e) {
            //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
            Log.e("MailApp", "Could not send email, check internet connection", e);
        }


        return null;
    }
}
