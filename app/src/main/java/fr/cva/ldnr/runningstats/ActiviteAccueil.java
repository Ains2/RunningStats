package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

/**
 * Created by Ains on 20/04/2017.
 */
import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

public class ActiviteAccueil extends fr.cva.ldnr.runningstats.Menu {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        GestionBDD gbdd = GestionBDD.getInstance(this);


        String[] tab = {"_id", "dates", "dist", "temps"};
        Cursor c = gbdd.selectSprint(tab, null, new String[0], null, null, "dates DESC", "1");
        // Placement sur le résultat
        c.moveToFirst();
        String date = c.getString(1);
        int dist = c.getInt(2);
        int temps = c.getInt(3);
        String txt = dist + " m / " + temps + "s";
        TextView last_run = (TextView) findViewById(R.id.last_run);
        last_run.setText(txt);
        TextView date_last_run = (TextView) findViewById(R.id.date);
        date_last_run.setText("Entrainement : " +date);
// Ajouter le nom quand sera fait dans ajout

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
