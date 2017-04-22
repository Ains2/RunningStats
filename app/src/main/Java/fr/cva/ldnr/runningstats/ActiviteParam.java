package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;

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

    public void parametres_export(View view) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            try {
                GestionBDD gbdd = GestionBDD.getInstance(this);
                String[] tab = {"*"};
                Cursor c = gbdd.selectSprint(tab, null, new String[0], null, null, "_id DESC", null);
                c.moveToFirst();

                File dir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS);

                dir.mkdirs();

                File f = new File(dir, "export.csv");
                FileWriter fw = new FileWriter(f, true);

                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    // Log.i("GestionBDD", "_id=" + c.getInt(0) + " dist=" + c.getInt(1) + " temps=" + c.getDouble(2) + " dates=" + c.getString(3) + " compet=" + c.getInt(4) + " nom=" + c.getString(5) + " classement=" + c.getInt(6));
                    fw.write("_id=" + c.getInt(0) + " dist=" + c.getInt(1) + " temps=" + c.getDouble(2) + " dates=" + c.getString(3) + " compet=" + c.getInt(4) + " nom=" + c.getString(5) + " classement=" + c.getInt(6));
                }
                fw.close();
                c.close();

            } catch (Exception ex) {
                Log.e("param", "pb support"+ex.getMessage());
            }
        }
    }

}