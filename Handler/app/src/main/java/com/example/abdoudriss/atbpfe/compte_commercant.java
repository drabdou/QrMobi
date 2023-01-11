package com.example.abdoudriss.atbpfe;

import java.math.BigInteger;

/**
 * Created by Abdou Driss on 25/02/2017.
 */

public class compte_commercant {
    // private int id_compte;
    // private String type;
    //private String nom;
    private BigInteger rib;


    public compte_commercant() {
    }

    public compte_commercant(BigInteger rib) {
        this.rib = rib;
    }

    public BigInteger getRib() {
        return rib;
    }

    public void setRib(BigInteger rib) {
        this.rib = rib;
    }
}
