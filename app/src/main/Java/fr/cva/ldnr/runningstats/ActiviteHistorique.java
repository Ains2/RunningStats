package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

/**
 * Created by Nanwee on 19/04/2017.
 */

public class ActiviteHistorique extends fr.cva.ldnr.runningstats.Menu {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique);

        GestionBDD gbdd = GestionBDD.getInstance(this);
        /* correspond à la requete: "SELECT dates, dist, temps FROM sprint ORDER BY dates DESC LIMIT 10"*/
        String[] tab = {"_id", "dates", "dist", "temps"};
        Cursor c = gbdd.selectSprint(tab, null, new String[0], null, null, "dates DESC", "10");

        // on prendra les données des colonnes 1 et 2...
        String[] from = new String[]{"dates", "dist", "temps"};

        // ...pour les placer dans les TextView définis dans "row_item.xml"
        int[] to = new int[]{R.id.textViewCol1, R.id.textViewCol2, R.id.textViewCol3};

        //try {
        // création de l'objet SimpleCursorAdapter...
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_item, c, from, to, 0);
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


    public void gotoGraphique (View view) {
        Intent intent = new Intent(this, ActiviteGraph.class);
        startActivity(intent);
    }
}