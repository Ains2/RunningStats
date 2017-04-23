package fr.cva.ldnr.runningstats;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

public class ActiviteStats extends Menu {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        GestionBDD gbdd = GestionBDD.getInstance(this);
        //affichage stats globales
        String[] tab = {"sum(dist)", "sum(temps)"};
        Cursor c = gbdd.selectSprint(tab, null, null, null, null, null, null);
        c.moveToFirst();
        int i = c.getInt(0);
        float f = c.getFloat(1);
        c.close();
        TextView tv = (TextView) findViewById(R.id.distance_total);
        tv.setText(i+" "+getText(R.string.length_unit));
        tv = (TextView) findViewById(R.id.time_total);
        tv.setText(f+" "+getText(R.string.time_unit));
        tv = (TextView) findViewById(R.id.avg_speed);
        f = i/f;
        tv.setText(f+" "+getText(R.string.speed_unit));

        //Récupération du Spinner de la liste des distances
        Spinner list_dist = (Spinner) findViewById(R.id.selectdist);
        // Création de l'adaptater pour afficher le contenu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dist_sprint, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list_dist.setAdapter(adapter);
        list_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                String spin_result = (String) tv.getText();
                showStats(spin_result);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("ActiviteGraph", parent.toString());
            }
        });
    }

    private void showStats(String dist) {
        GestionBDD gbdd = GestionBDD.getInstance(this);
        //Total
        String[] tab = {"sum(dist)", "sum(temps)"};
        String[] args = {dist};
        TextView tv;
        Cursor c = gbdd.selectSprint(tab, "dist=?", args, null, null, null, null);
        if(c.moveToFirst()){
            int i = c.getInt(0);
            float f = c.getFloat(1);
            tv = (TextView) findViewById(R.id.distance_total_dist);
            tv.setText(i+" "+getText(R.string.length_unit));
            tv = (TextView) findViewById(R.id.time_total_dist);
            tv.setText(f+" "+getText(R.string.time_unit));
            tv = (TextView) findViewById(R.id.avg_speed_dist);
            f = i/f;
            tv.setText(f+" "+getText(R.string.speed_unit));
        }
        //Best
        tab = new String[]{"temps", "dates"};
        c = gbdd.selectSprint(tab, "dist=?", args, null, null, "temps ASC", "1");
        String tt, td;
        if(c.moveToFirst()){
            tt = c.getDouble(0)+" "+getString(R.string.time_unit);
            td = c.getString(1);
        }else {
            tt = td = "-";
        }
        tv = (TextView) findViewById(R.id.best_time_dist);
        tv.setText(tt);
        tv = (TextView) findViewById(R.id.the_best_dist);
        tv.setText(td);
        //Worst
        c = gbdd.selectSprint(tab, "dist=?", args, null, null, "temps DESC", "1");
        if(c.moveToFirst()){
            tt = c.getDouble(0)+" "+getString(R.string.time_unit);
            td = c.getString(1);
        }else {
            tt = td = "-";
        }
        tv = (TextView) findViewById(R.id.worst_time_dist);
        tv.setText(tt);
        tv = (TextView) findViewById(R.id.the_worst_dist);
        tv.setText(td);
        c.close();
    }
}