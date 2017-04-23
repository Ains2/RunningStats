package fr.cva.ldnr.runningstats;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;


import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

public class ActiviteAccueil extends fr.cva.ldnr.runningstats.Menu {

    @Override
    protected void onStart() {
        super.onStart();
        GestionBDD gbdd = GestionBDD.getInstance(this);
        try {
            String[] tab = {"_id", "dates", "dist", "temps", "compet", "nom", "classement"};
            Cursor c = gbdd.selectSprint(tab, null, new String[0], null, null, "dates DESC", "1");
            // Placement sur le résultat
            c.moveToFirst();
            // Récup des données
            String date = c.getString(1);
            int dist = c.getInt(2);
            Float temps = c.getFloat(3);
            int compet = c.getInt(4);
            String nom = c.getString(5);
            int classement = c.getInt(6);
            // Configuration pour affichage
            String txt = dist + " m / " + temps + "s";
            TextView last_run = (TextView) findViewById(R.id.last_run);
            last_run.setText(txt);
            TextView date_last_run = (TextView) findViewById(R.id.date);
            // Affichage selon le cas entrainement ou compétition
            if (!nom.equals("")) {
                date_last_run.setText("\"" + nom + "\"\n" + getString(R.string.ranking_entry) + " " + classement + "\n" + date);
            } else {
                date_last_run.setText(getString(R.string.training) + " : \n" + date);
            }
        } catch (Exception exception) {
            TextView titre = (TextView) findViewById(R.id.titre);
            titre.setText(getString(R.string.titre));
            TextView last_run = (TextView) findViewById(R.id.last_run);
            last_run.setText(getString(R.string.todo));
            TextView date_last_run = (TextView) findViewById(R.id.date);
            date_last_run.setText("");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
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
