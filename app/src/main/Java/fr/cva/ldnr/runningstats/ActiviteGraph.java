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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

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
        // Création de l'adaptater pour afficher le contenu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dist_sprint, android.R.layout.simple_spinner_item);
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
            }
        });
    }

    private void showgraph(String dist) {
        GestionBDD gbdd = GestionBDD.getInstance(this);
        LineChart chart = (LineChart) findViewById(R.id.chart);
        try {
            String[] tab = {"_id", "dates", "dist", "temps"};
            String[] args = {dist};
            /* correspond à la requete: "SELECT dates, temps FROM sprint WHERE dist = 100 ORDER BY dates ASC"*/
            Cursor c = gbdd.selectSprint(tab, "dist=?", args, null, null, "dates ASC", null);

            List<Entry> entries_tmps = new ArrayList<>();
            List<Entry> entries_moy = new ArrayList<>();
            if (c.moveToFirst()) {
                int i = 0;
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    // turn your data into Entry objects
                    entries_tmps.add(new Entry(i, c.getFloat(3)));
                    entries_moy.add(new Entry(i, c.getInt(2)/c.getFloat(3)));
                    i++;
                }
                c.close();

                LineDataSet ds_tmps = new LineDataSet(entries_tmps, getString(R.string.graph_tmps,getString(R.string.time_unit)));
                LineDataSet ds_moy = new LineDataSet(entries_moy, getString(R.string.graph_moy, getString(R.string.speed_unit)));
                ds_tmps.setColor(Color.CYAN);
                ds_tmps.setCircleColor(Color.CYAN);
                ds_moy.setColor(Color.MAGENTA);
                ds_moy.setCircleColor(Color.MAGENTA);
                ds_tmps.setValueTextColor(Color.WHITE);
                ds_moy.setValueTextColor(Color.WHITE);
                List<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(ds_tmps);
                dataSets.add(ds_moy);

                LineData lineData = new LineData(dataSets);
                chart.setData(lineData);

                //param axes
                XAxis x = chart.getXAxis();
                x.setDrawLabels(false);
                x.setDrawAxisLine(false);
                x.setAxisMinimum(0);
                x.setGranularity(1);
                x.setTextColor(Color.WHITE);

                YAxis yl = chart.getAxisLeft();
                yl.setDrawLabels(true);
                yl.setAxisMinimum(0);
                yl.setGranularity(1);
                yl.setTextColor(Color.WHITE);

                YAxis yr = chart.getAxisRight();
                yr.setDrawLabels(false);
                yr.setDrawAxisLine(false);
                yr.setDrawGridLines(false);

                //param legende
                Legend leg = chart.getLegend();
                leg.setTextColor(Color.WHITE);

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
