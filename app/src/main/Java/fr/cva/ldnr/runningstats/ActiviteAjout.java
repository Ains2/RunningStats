package fr.cva.ldnr.runningstats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

/**
 * Created by Nanwee on 19/04/2017.
 */

public class ActiviteAjout extends Menu {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout);
        TimePicker tp = (TimePicker) findViewById(R.id.hour_entry);
        tp.setIs24HourView(true);

        //Récupération du Spinner de la liste des distances
        Spinner list_dist = (Spinner) findViewById(R.id.list_dist);
        //Création de la liste des distances
        List list = new ArrayList();
        list.add("60");
        list.add("100");
        list.add("200");
        list.add("400");

        // Création de l'adaptater pour afficher le contenu
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                list
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list_dist.setAdapter(adapter);

        // Concerne les buttons radio et leurs actions :
        RadioButton radio_competition = (RadioButton) findViewById(R.id.competition);
        radio_competition.setChecked(true);
    }

    // Affichage ou non des champs selon le button
    public void hide(View v) {
        LinearLayout layout_name = (LinearLayout) findViewById(R.id.name);
        LinearLayout layout_ranking = (LinearLayout) findViewById(R.id.ranking);
        layout_name.setVisibility(LinearLayout.GONE);
        layout_ranking.setVisibility(LinearLayout.GONE);
    }

    public void see(View v) {
        LinearLayout layout_name = (LinearLayout) findViewById(R.id.name);
        LinearLayout layout_ranking = (LinearLayout) findViewById(R.id.ranking);
        layout_name.setVisibility(LinearLayout.VISIBLE);
        layout_ranking.setVisibility(LinearLayout.VISIBLE);
    }

    public void saveRun(View v) {
        DatePicker dp = (DatePicker) findViewById(R.id.date_entry);
        TimePicker tp = (TimePicker) findViewById(R.id.hour_entry);
        String date = dp.getYear() + "/";
        int month = dp.getMonth()+1;
        if (month < 10) date += "0";
        date += month + "/";
        if (dp.getDayOfMonth() < 10) date += "0";
        date += dp.getDayOfMonth() + " ";
      /*  if(tp.getHour()<10) date+="0";
        date+=tp.getHour()+":";
        if(tp.getMinute()<10) date+="0";
        date+=tp.getMinute();*/

        // Récupération de la valeur de la liste déroulante
        Spinner list_dist = (Spinner) findViewById(R.id.list_dist);
        String spin_result = list_dist.getSelectedItem().toString();
        int dist = Integer.parseInt(spin_result);

        //Récupération des valeurs
        EditText etd = (EditText) findViewById(R.id.time_entry);
        double temps = Double.parseDouble(etd.getText().toString());


        EditText name_entry = (EditText) findViewById(R.id.name_entry);
        String name = name_entry.getText().toString();

        // Dans le cas d'entrainement, pas de ranking
        int ranking = 0;
        try {
            EditText ranking_entry = (EditText) findViewById(R.id.ranking_entry);
            String ranking_convers = ranking_entry.getText().toString();
            if (!ranking_convers.equals(null)) {
                ranking = Integer.parseInt(ranking_convers);
            }
        } catch (Exception ex) {
        // Pas d'exception, le ranking reste à 0
        }

        int compet = 0;
        RadioButton radio_competition = (RadioButton) findViewById(R.id.competition);
        Boolean radio = radio_competition.isChecked();
        if (radio) {
            compet = 1;
        }
        GestionBDD gbdd = GestionBDD.getInstance(this);
        gbdd.insertSprint(dist, temps, date, compet, name, ranking);

    }


}
