package fr.cva.ldnr.runningstats.GestionDonnees;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GestionBDD extends SQLiteOpenHelper {
    private static GestionBDD instance;

    private GestionBDD(Context context) {super(context, "data", null, 1);}

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

    public boolean insertSprint(int dist, double tmp, String date, int compet, String nom, int classement){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("INSERT INTO sprint(dist, temps, dates, compet, nom, classement) VALUES (?,?,?,?,?,?)",
                    new Object[]{dist, tmp, date, compet, nom, classement});
            return true;
        }catch (Exception e){
            Log.e("GestionBDD", "erreur insert "+e.getMessage());
            return false;
        }
    }

    public Cursor selectSprint(String[]champs, String where, String[] args ,String groupby, String having, String orderby, String limit ){
        SQLiteDatabase db = getWritableDatabase();
        return db.query("sprint", champs, where, args, groupby, having, orderby, limit);
    }

    public void delete(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("sprint",null,null);
    }
}
