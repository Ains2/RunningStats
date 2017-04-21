package fr.cva.ldnr.runningstats;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.data.Entry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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

    public void parametres_export(View view) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            try {
                GestionBDD gbdd = GestionBDD.getInstance(this);
                String[] tab = {"*"};
                Cursor c = gbdd.selectSprint(tab, null, new String[0], null, null, "_id DESC", null);
                c.moveToFirst();

                File dir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS);

                dir.mkdirs();

                File f = new File(dir, "export.xml");
                FileWriter fw = new FileWriter(f, true);

                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                    // Log.i("GestionBDD", "_id=" + c.getInt(0) + " dist=" + c.getInt(1) + " temps=" + c.getDouble(2) + " dates=" + c.getString(3) + " compet=" + c.getInt(4) + " nom=" + c.getString(5) + " classement=" + c.getInt(6));
                    fw.write("_id=" + c.getInt(0) + " dist=" + c.getInt(1) + " temps=" + c.getDouble(2) + " dates=" + c.getString(3) + " compet=" + c.getInt(4) + " nom=" + c.getString(5) + " classement=" + c.getInt(6));
                }

                fw.close();
                c.close();

            } catch (IOException ex) {
                Log.e("param", "pb support");
            }
        }
    }
}