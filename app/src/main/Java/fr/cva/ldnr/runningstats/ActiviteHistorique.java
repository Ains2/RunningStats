package fr.cva.ldnr.runningstats;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        Cursor cur_histo = gbdd.selectSprint(tab, null, new String[0], null, null, "dates DESC", "10");

        // on prendra les données des colonnes 1 et 2...
        String[] from = new String[]{"dates", "dist", "temps"};
        // ...pour les placer dans les TextView définis dans "row_item.xml"
        int[] to = new int[]{R.id.textViewCol1, R.id.textViewCol2, R.id.textViewCol3};

        // on extrait les données du curseur pour qu'elles soient utilisable par l'adapter
        List history = cursorToHistory(cur_histo);
        ListAdapter adapter = new SimpleAdapter(this, history, R.layout.row_item, from, to);
        // ...qui va remplir l'objet ListView
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);

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
    */


    }

    private List cursorToHistory(Cursor cursor) {
        List history = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            HashMap historyLine = new HashMap();
            historyLine.put("dates", cursor.getString(1));
            historyLine.put("dist", cursor.getInt(2));
            historyLine.put("temps", cursor.getFloat(3));
            history.add(historyLine);
        }
        return history;
    }

    public void gotoGraphique(View view) {
        Intent intent = new Intent(this, ActiviteGraph.class);
        startActivity(intent);
        finish();
    }
}