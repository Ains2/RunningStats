package fr.cva.ldnr.runningstats;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

/**
 * Created by Nanwee on 19/04/2017.
 */

public class ActiviteHistorique extends fr.cva.ldnr.runningstats.Menu {

    private Cursor cur_histo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique);

        GestionBDD gbdd = GestionBDD.getInstance(this);
        /* correspond à la requete: "SELECT dates, dist, temps FROM sprint ORDER BY dates DESC LIMIT 10"*/
        String[] tab = {"_id", "dates", "dist", "temps"};
        cur_histo = gbdd.selectSprint(tab, null, new String[0], null, null, "dates DESC", "10");

        // on prendra les données des colonnes 1 et 2...
        String[] from = new String[]{"dates", "dist", "temps"};

        // ...pour les placer dans les TextView définis dans "row_item.xml"
        int[] to = new int[]{R.id.textViewCol1, R.id.textViewCol2, R.id.textViewCol3};

        //try {
        // création de l'objet SimpleCursorAdapter...
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_item, cur_histo, from, to, 0);
        // ...qui va remplir l'objet ListView
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);

        //} finally {
        //c.close();



        /*
        // Gestion des clics sur les lignes
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                // faites ici ce que vous voulez
                Log.i("mydebug","clic sur id:"+id);

            }
        };

    // Utilisation avec notre listview
        lv.setOnItemClickListener(itemClickListener);
    }*/


    }

    @Override
    protected void onDestroy() {
        cur_histo.close();
        super.onDestroy();
    }

    public void gotoGraphique (View view) {
        Intent intent = new Intent(this, ActiviteGraph.class);
        startActivity(intent);
    }
}