package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

/**
 * Created by Ains on 20/04/2017.
 */

public class ActiviteAccueil extends fr.cva.ldnr.runningstats.Menu {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        GestionBDD gbdd = GestionBDD.getInstance(this);
    }

    public void gotoAjout(View view) {
        Intent intent = new Intent(this, ActiviteAjout.class);
        startActivity(intent);
    }

    public void gotoHistorique(View view) {
        Intent intent = new Intent(this, ActiviteHistorique.class);
        startActivity(intent);
    }

}
