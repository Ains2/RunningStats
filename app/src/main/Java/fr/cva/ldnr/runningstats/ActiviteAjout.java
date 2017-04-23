package fr.cva.ldnr.runningstats;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

/**
 * Created by Nanwee on 19/04/2017.
 */

public class ActiviteAjout extends Menu {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout);
        /* Problème de compatibilité avec les versions < 23*/
        TimePicker tp = (TimePicker) findViewById(R.id.hour_entry);
        tp.setIs24HourView(true);

        //Récupération du Spinner de la liste des distances
        Spinner list_dist = (Spinner) findViewById(R.id.list_dist);
        // Création de l'adaptater pour afficher le contenu
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dist_sprint, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list_dist.setAdapter(adapter);
        //Affiche les boutons en fonction du type de course
        hideshow(list_dist);
    }

    // Affichage ou non des champs selon le button
    public void hideshow(View v) {
        RadioButton radio_competition = (RadioButton) findViewById(R.id.competition);
        if (radio_competition.isChecked()) {
            LinearLayout layout_name = (LinearLayout) findViewById(R.id.name);
            LinearLayout layout_ranking = (LinearLayout) findViewById(R.id.ranking);
            layout_name.setVisibility(LinearLayout.VISIBLE);
            layout_ranking.setVisibility(LinearLayout.VISIBLE);
        } else {
            LinearLayout layout_name = (LinearLayout) findViewById(R.id.name);
            LinearLayout layout_ranking = (LinearLayout) findViewById(R.id.ranking);
            layout_name.setVisibility(LinearLayout.GONE);
            layout_ranking.setVisibility(LinearLayout.GONE);
        }
    }

    public void saveRun(View v) {
        boolean complete = true;

        DatePicker dp = (DatePicker) findViewById(R.id.date_entry);
        // Problème de compatibilité avec les versions < 23
        TimePicker tp = (TimePicker) findViewById(R.id.hour_entry);
        String date = dp.getYear() + "/";
        int month = dp.getMonth() + 1;
        if (month < 10) date += "0";
        date += month + "/";
        if (dp.getDayOfMonth() < 10) date += "0";
        date += dp.getDayOfMonth() + " ";

        //Problème de compatibilité avec les versions < 23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (tp.getHour() < 10) date += "0";
            date += tp.getHour() + ":";
        } else {
            if (tp.getCurrentHour() < 10) date += "0";
            date += tp.getCurrentHour() + ":";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (tp.getMinute() < 10) date += "0";
            date += tp.getMinute();
        } else {
            if (tp.getCurrentMinute() < 10) date += "0";
            date += tp.getCurrentMinute();
        }

        // Récupération de la valeur de la liste déroulante
        Spinner list_dist = (Spinner) findViewById(R.id.list_dist);
        String spin_result = list_dist.getSelectedItem().toString();
        int dist = Integer.parseInt(spin_result);

        //Récupération des valeurs
        EditText etd = (EditText) findViewById(R.id.time_entry);
        double temps = 0;
        try {
            temps = Double.parseDouble(etd.getText().toString());
            if(temps<0){
                complete = false;
                Toast.makeText(this, getString(R.string.add_neg), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            complete = false;
            Log.e("Ajout", e.getMessage());
        }

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
        // Si compétition
        if (radio) {
            compet = 1;
            // Vérification des champs classement et nom
            if (ranking == 0 | name == "")
                complete = false;
        }
        if (complete) {
            GestionBDD gbdd = GestionBDD.getInstance(this);
            if (gbdd.insertSprint(dist, temps, date, compet, name, ranking)) {
                Button button_save = (Button) findViewById(R.id.save);
                button_save.setVisibility(LinearLayout.GONE);
                Button button_new_entry = (Button) findViewById(R.id.new_entry);
                button_new_entry.setVisibility(LinearLayout.VISIBLE);
                Toast.makeText(this, getString(R.string.add_ok), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getString(R.string.add_ko), Toast.LENGTH_LONG).show();
            }
        } else {
            if (compet == 0)
                Toast.makeText(this, getString(R.string.add_comp), Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, getString(R.string.add_all), Toast.LENGTH_LONG).show();
        }
    }

    public void gotoAjout(View view) {
        Intent intent = new Intent(this, ActiviteAjout.class);
        startActivity(intent);
        finish();
    }

}
