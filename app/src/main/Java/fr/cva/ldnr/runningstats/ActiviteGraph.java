package fr.cva.ldnr.runningstats;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

/**
 * Created by Nanwee on 19/04/2017.
 */

public class ActiviteGraph extends fr.cva.ldnr.runningstats.Menu {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphique);

        //Récupération du Spinner de la liste des distances
        Spinner list_dist = (Spinner) findViewById(R.id.selectdist);
        //Création de la liste des distances
        List<String> list = new ArrayList<>();
        list.add("60");
        list.add("100");
        list.add("200");
        list.add("400");

        // Création de l'adaptater pour afficher le contenu
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                list
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list_dist.setAdapter(adapter);
        list_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                String spin_result = (String) tv.getText();
                showgraph(spin_result);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("ActiviteGraph", parent.toString());
            }
        });
    }

    public void showgraph(String dist) {
        GestionBDD gbdd = GestionBDD.getInstance(this);
        LineChart chart = (LineChart) findViewById(R.id.chart);
        try {
            String[] tab = {"_id", "dates", "dist", "temps"};
            String[] args = {dist};
            Log.i("ActiviteGraph", "debut try");
            /* correspond à la requete: "SELECT dates, temps FROM sprint WHERE dist = 100 ORDER BY dates ASC"*/
            Cursor c = gbdd.selectSprint(tab, "dist=?", args, null, null, "dates ASC", null);
            Log.i("ActiviteGraph", c.toString());
            List<Entry> entries = new ArrayList<>();
            if (c.moveToFirst()) {
                int i = 0;
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    // turn your data into Entry objects
                    entries.add(new Entry(i, c.getFloat(3)));
                    i++;
                }
                c.close();

                LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
                dataSet.setColor(Color.BLUE);
                dataSet.setValueTextColor(Color.WHITE); // styling, ...

                LineData lineData = new LineData(dataSet);
                chart.setData(lineData);
                chart.invalidate(); // refresh
            }else{
                chart.setData(null);
                chart.setNoDataText(getString(R.string.no_data_text));
                chart.invalidate(); // refresh
            }
        } catch (Exception e) {
            Log.e("ActiviteGraph", e.getMessage());
        }
    }

    public void seehisto(View view) {
        Intent intent = new Intent(this, ActiviteHistorique.class);
        startActivity(intent);
    }
}
