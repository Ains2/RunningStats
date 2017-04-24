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

public class ActiviteHistorique extends fr.cva.ldnr.runningstats.Menu {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique);

        //requête bdd
        GestionBDD gbdd = GestionBDD.getInstance(this);
        String[] tab = {"_id", "dates", "dist", "temps","nom","classement"};
        Cursor cur_histo = gbdd.selectSprint(tab, null, new String[0], null, null, "dates DESC", "10");

        // Sélection des données prélevées
        String[] from = new String[]{"dates", "dist", "temps", "nom","classement"};
        // Placement des données dans les TextView définis dans "row_item.xml"
        int[] to = new int[]{R.id.textViewCol1, R.id.textViewCol2, R.id.textViewCol3, R.id.textViewCol4, R.id.textViewCol5};

        // on extrait les données du curseur pour qu'elles soient utilisable par l'adapter
        final List history = cursorToHistory(cur_histo);
        cur_histo.close();
        ListAdapter adapter = new SimpleAdapter(this, history, R.layout.row_item, from, to);
        // On remplit l'objet ListView
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);
    }

    //Transformation du Curseur en liste associative
    private List cursorToHistory(Cursor cursor) {
        List history = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            HashMap historyLine = new HashMap();
            historyLine.put("dates", cursor.getString(1));
            historyLine.put("dist", cursor.getInt(2));
            historyLine.put("temps", cursor.getFloat(3));
            historyLine.put("nom", cursor.getString(4));

            //pour ne rien afficher pour les entrainements
            if (cursor.getInt(5) == 0)
                historyLine.put("classement", "");
            else
                historyLine.put("classement", cursor.getInt(5));
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