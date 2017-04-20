package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.icu.text.DateTimePatternGenerator;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Date;
import java.text.ParseException;

import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

/**
 * Created by Nanwee on 19/04/2017.
 */

public class ActiviteAjout extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout);
    }

    public void saveRun(View v){
        DatePicker dp = (DatePicker) findViewById(R.id.date_entry);
        TimePicker tp = (TimePicker) findViewById(R.id.hour_entry);
        String sDate = dp.getYear()+"/"+dp.getMonth()+"/"+dp.getDayOfMonth()+" "+tp.getHour()+":"+tp.getMinute();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        try {
            Date date = sdf.parse(sDate);
            Log.i("Ajout", "date : "+sdf.format(date));
            GestionBDD gbdd = new GestionBDD(this);
            gbdd.insertSprint(100, 11.1, date, true,"test", 1);
        } catch (ParseException ex) {
            Log.i("Ajout", "Exception "+ex);
        }

    }
}
