package com.example.abdoudriss.atbpfeclient;

/**
 * Created by Abdou Driss on 18/05/2017.
 */

public class paiement {
    int id_paiement;
    Double montant;
    String date;

    public int getId_paiement() {
        return id_paiement;
    }

    public void setId_paiement(int id_paiement) {
        this.id_paiement = id_paiement;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public paiement(int id_paiement, Double montant, String date) {
        this.id_paiement = id_paiement;
        this.montant = montant;
        this.date = date;
    }

    public paiement() {
    }
}
