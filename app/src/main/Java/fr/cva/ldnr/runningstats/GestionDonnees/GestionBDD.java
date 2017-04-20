package fr.cva.ldnr.runningstats.GestionDonnees;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nanwee on 19/04/2017.
 */

public class GestionBDD extends SQLiteOpenHelper {
    public GestionBDD(Context context) {
        super(context, context.getClass().getName(), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sprint(id INTEGER AUTO_INCREMENT PRIMARY KEY, dist INTEGER, temps DOUBLE, dates DATETIME, compet BOOLEAN, nom TEXT, classement INTEGER)");
        Log.i("BDD","Done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertSprint(int dist, double tmp, Date date, boolean compet, String nom, int classement){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO sprint(dist, temps, dates, compet, nom, classement) VALUES (?,?,?,?,?,?)",
                new Object[]{dist, tmp, date, compet, nom, classement});
        Log.i("GestionBDD",dist + " "+ tmp + " "+ date +" "+ compet + nom + classement);
    }

    public Cursor selectSprint(String[]champs, String where, String groupby, String having, String orderby, String limit ){
        SQLiteDatabase db = getReadableDatabase();
        String[] str = new String[0];
        //Cursor c = db.rawQuery(req,str)
        Cursor c = db.query("sprint", champs, where, str, groupby, having, orderby, limit);
        return c;
    }
}
