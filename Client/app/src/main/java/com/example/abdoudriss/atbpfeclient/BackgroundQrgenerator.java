package com.example.abdoudriss.atbpfeclient;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


/**
 * Created by Abdou Driss on 13/04/2017.
 */

public class BackgroundQrgenerator extends AsyncTask<Void, Void, Void> {
    final QRCodeWriter writer = new QRCodeWriter();
    static Bitmap bmp;
    static ImageView ab;

    public interface qrgenimp {

        void qrgenimp();

    }

    ProgressDialog dialog;
    QrGenerator bckqr = new QrGenerator();
    private qrgenimp listener;


    public BackgroundQrgenerator(QrGenerator activity) {
        dialog = new ProgressDialog(activity);
        listener = activity;
    }


    @Override
    protected void onPreExecute() {
        dialog.setMessage("getting your Qr code...");

        dialog.setTitle("Please wait...");
        dialog.setIcon(R.drawable.zz);
        dialog.show();
    }


    @Override
    protected void onPostExecute(Void result) {
        if (dialog.isShowing()) {
            dialog.dismiss();
            QrGenerator.abcd.setImageBitmap(BackgroundQrgenerator.bmp);

            listener.qrgenimp();
        }
    }


    @Override
    protected Void doInBackground(Void... params) {
        String qr = QrGenerator.qrcodeget;
        try {
            BitMatrix bitMatrix = writer.encode(qr, BarcodeFormat.QR_CODE, 512, 512);
            int width = 512;
            int height = 512;
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(x, y))
                        bmp.setPixel(x, y, Color.BLACK);
                    else
                        bmp.setPixel(x, y, Color.WHITE);
                }
//                QrGenerator.abcd.setImageBitmap(BackgroundQrgenerator.bmp);

            }

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
