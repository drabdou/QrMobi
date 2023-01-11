package com.example.abdoudriss.atbpfe;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abdou Driss on 18/05/2017.
 */

public class AdapterPaiement extends ArrayAdapter {

    public AdapterPaiement(Context context, ArrayList<paiement> paiements) {
        super(context, 0, paiements);
    }

    @Override
    public View getView(int position, View monView, ViewGroup parent) {
        // Get the data item for this position
        paiement paym = (paiement) getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (monView == null) {
            monView = LayoutInflater.from(getContext()).inflate(R.layout.item_paiement, parent, false);
        }

        // Lookup view for data population
        TextView tvMontant = (TextView) monView.findViewById(R.id.tvMontant);
        TextView tvDate = (TextView) monView.findViewById(R.id.tvdate);
        TextView tvnum = (TextView) monView.findViewById(R.id.tvnum);

        // Populate the data into the template view using the data object
        tvDate.setText("Date : "+paym.getDate());
        tvnum.setText("Numéro du paiement : "+paym.getId_paiement());
        tvMontant.setText("Montant : "+paym.getMontant());

        // Return the completed view to render on screen
        return monView;
    }
    }

