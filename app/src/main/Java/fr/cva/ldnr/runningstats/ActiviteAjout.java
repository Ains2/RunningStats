package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.icu.text.DateTimePatternGenerator;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Date;
import java.text.ParseException;

import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

/**
 * Created by Nanwee on 19/04/2017.
 */

public class ActiviteAjout extends Menu {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout);
        TimePicker tp = (TimePicker)findViewById(R.id.hour_entry);
        tp.setIs24HourView(true);
    }

    public void saveRun(View v){
        DatePicker dp = (DatePicker) findViewById(R.id.date_entry);
        TimePicker tp = (TimePicker) findViewById(R.id.hour_entry);
        String date = dp.getYear()+"/";
        if(dp.getMonth()<10) date+="0";
        date+=dp.getMonth()+"/";
        if(dp.getDayOfMonth()<10) date+="0";
        date+=dp.getDayOfMonth()+" ";
        if(tp.getHour()<10) date+="0";
        date+=tp.getHour()+":";
        if(tp.getMinute()<10) date+="0";
        date+=tp.getMinute();
        EditText etd = (EditText) findViewById(R.id.length_entry);
        int dist = Integer.parseInt(etd.getText().toString());
        etd = (EditText) findViewById(R.id.time_entry);
        double temps = Double.parseDouble(etd.getText().toString());
        GestionBDD gbdd = GestionBDD.getInstance(this);
        gbdd.insertSprint(dist, temps, date, 1,"test", 1);

    }
}
