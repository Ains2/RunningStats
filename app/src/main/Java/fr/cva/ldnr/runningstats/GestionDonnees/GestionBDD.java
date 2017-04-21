package fr.cva.ldnr.runningstats.GestionDonnees;

import android.content.ContentValues;
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
    private static GestionBDD instance;

    private GestionBDD(Context context) {
        super(context, context.getClass().getName(), null, 1);
    }

    public static GestionBDD getInstance(Context context){
        if (instance==null){
            instance = new GestionBDD(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE sprint(_id INTEGER AUTO_INCREMENT PRIMARY KEY, dist INTEGER, temps REAL, dates TEXT, compet INTEGER, nom TEXT, classement INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertSprint(int dist, double tmp, String date, int compet, String nom, int classement){
        SQLiteDatabase db = getWritableDatabase();
        /*ContentValues values = new ContentValues();
        values.put("dist",dist);
        values.put("temps",tmp);
        values.put("dates",date);
        values.put("compet",compet);
        values.put("nom",nom);
        values.put("classement",classement);
        db.insert("sprint", null, values);*/
        db.execSQL("INSERT INTO sprint(dist, temps, dates, compet, nom, classement) VALUES (?,?,?,?,?,?)",
                new Object[]{dist, tmp, date, compet, nom, classement});
        //Log.i("GestionBDD",dist + " "+ tmp + " "+ date +" "+ compet + nom + classement);
        //db.close();
        /*db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT dates, dist, temps FROM sprint ORDER BY dates DESC LIMIT 10",//"SELECT COUNT(*) FROM sprint",
                new String[0]);
        Log.i("h",c.getColumnCount()+" "+c.getCount());
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            date = c.getString(0);
            dist = c.getInt(1);
            double temps= c.getDouble(2);
            Log.i("hh",date+" "+ dist + " " + temps);

        }*/
        /*c.moveToFirst();
        int nb = c.getInt(0);
        Log.i("GestionBDD", "nb lignes "+nb);
        c.close();*/
    }

    public Cursor selectSprint(String[]champs, String where, String[] args ,String groupby, String having, String orderby, String limit ){
        SQLiteDatabase db = getWritableDatabase();
        String[] str = new String[0];
        //Cursor c = db.rawQuery(req,str)
        /*Cursor c = db.rawQuery("SELECT COUNT(*) FROM sprint",
                new String[0]);
        c.moveToFirst();
        int nb = c.getInt(0);
        Log.i("GestionBDD", "nb lignes "+nb);*/

        Cursor c = db.query("sprint", champs, where, args, groupby, having, orderby, limit);
        return c;
    }

    public void delete()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("sprint",null,null);
    }
}
