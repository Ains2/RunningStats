package fr.cva.ldnr.runningstats.GestionDonnees;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.execSQL("CREATE TABLE sprint(id INTEGER PRIMARY KEY, distance INTEGER, temps DOUBLE, date DATETIME, compet BOOLEAN, nom TEXT, classement INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertSprint(int dist, double tmp, Date date, boolean compet, String nom, int classement){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO sprint(distance, temps, date, compet, nom, classement) VALUES (?,?)",
                new Object[]{dist, tmp, date, compet, nom, classement});
    }

    public ArrayList selectSprint(String req){
        SQLiteDatabase db = getReadableDatabase();
        String[] str = new String[0];
        db.rawQuery(req,str);
        return null;
    }
}
