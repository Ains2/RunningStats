package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Vince on 20/04/2017.
 */

public abstract class Menu extends Activity {

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general, menu);
        return true;
    }

    public boolean onMenuItemSelected(int featuredId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_accueil:
                Intent intent5 = new Intent(this, ActiviteAccueil.class);
                startActivity(intent5);
                finish();
                return true;
            case R.id.menu_enregistrer:
                Intent intent = new Intent(this, ActiviteAjout.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_historique:
                Intent intent2 = new Intent(this, ActiviteHistorique.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.menu_statistiques:
                Intent intent3 = new Intent(this, ActiviteStats.class);
                startActivity(intent3);
                finish();
                return true;
            case R.id.menu_parametres:
                Intent intent4 = new Intent(this, ActiviteParam.class);
                startActivity(intent4);
                finish();
                return true;
            default:
                return false;
        }
    }
}