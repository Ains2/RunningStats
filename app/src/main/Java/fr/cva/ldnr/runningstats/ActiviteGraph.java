package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

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

public class ActiviteGraph extends fr.cva.ldnr.runningstats.Menu  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphique);
        LineChart chart = (LineChart) findViewById(R.id.chart);

        GestionBDD gbdd = GestionBDD.getInstance(this);
        /* correspond à la requete: "SELECT dates, temps FROM sprint WHERE dist = 100 ORDER BY dates DESC LIMIT 10"*/
        String[] tab = {"_id", "dates", "dist", "temps"};
        String[] args = {"100"};
        Cursor c = gbdd.selectSprint(tab, "dist=?", args, null, null, "dates ASC", null);

        List<Entry> entries = new ArrayList<>();
        int i=0;
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()) {
            // turn your data into Entry objects
            entries.add(new Entry(i, c.getFloat(3)));
            i++;
        }
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(R.style.button);
        dataSet.setValueTextColor(R.style.button); // styling, ...

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }

    public void seehisto (View view) {
        Intent intent = new Intent(this, ActiviteHistorique.class);
        startActivity(intent);
    }




}
