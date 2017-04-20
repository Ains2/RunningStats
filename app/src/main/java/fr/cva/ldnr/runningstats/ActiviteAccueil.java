package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Ains on 20/04/2017.
 */

public class ActiviteAccueil extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featuredId, MenuItem item){
        switch(item.getItemId()) {
            case R.id.menu_enregistrer:
                Intent intent = new Intent(this, ActiviteAjout.class);
                startActivity(intent);
                return  true;
            case R.id.menu_historique:
                Intent intent2 = new Intent(this, ActiviteHistorique.class);
                startActivity(intent2);
                return  true;
            case R.id.menu_statistiques:
                Intent intent3 = new Intent(this, ActiviteStats.class);
                startActivity(intent3);
                return  true;
            case R.id.menu_parametres:
                Intent intent4 = new Intent(this, ActiviteParam.class);
                startActivity(intent4);
                return  true;
            default:
                return false;
        }

    }










}
