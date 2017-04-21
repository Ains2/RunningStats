package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import fr.cva.ldnr.runningstats.GestionDonnees.GestionBDD;

/**
 * Created by Nanwee on 19/04/2017.
 */

public class ActiviteParam extends fr.cva.ldnr.runningstats.Menu {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametres);
    }

    public void erase(View view) {
        LinearLayout main_param = (LinearLayout) findViewById(R.id.settings);
        main_param.setVisibility(LinearLayout.GONE);

        LinearLayout sup_param = (LinearLayout) findViewById(R.id.sure);
        sup_param.setVisibility(LinearLayout.VISIBLE);
    }

    public void yes (View view) {
        GestionBDD gbdd = GestionBDD.getInstance(this);
        try {
            gbdd.delete();
            Toast.makeText(this,getString(R.string.sup_ok),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ActiviteAccueil.class);
            startActivity(intent);
        }
        catch (Exception ex){
        }
    }

    public void no (View view) {
        Intent intent = new Intent(this, ActiviteParam.class);
        startActivity(intent);
    }
}