package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract class Menu extends Activity {

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general, menu);
        return true;
    }

    public boolean onMenuItemSelected(int featuredId, MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_accueil:
                if(this.getLocalClassName().equals("ActiviteAccueil")){
                    return true;
                }
                finish();
                return true;
            case R.id.menu_enregistrer:
                intent = new Intent(this, ActiviteAjout.class);
                break;
            case R.id.menu_historique:
                intent = new Intent(this, ActiviteHistorique.class);
                break;
            case R.id.menu_statistiques:
                intent = new Intent(this, ActiviteStats.class);
                break;
            case R.id.menu_parametres:
                intent = new Intent(this, ActiviteParam.class);
                break;
            default:
                return false;
        }
        Log.i("Menu",this.getLocalClassName());
        startActivity(intent);
        if(!this.getLocalClassName().equals("ActiviteAccueil")){
            finish();
        }
        return true;
    }
}