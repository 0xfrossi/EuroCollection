package com.example.europroject.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.europroject.utils.EuroCollDB;

public class MySQLiteHelper extends SQLiteOpenHelper {



    private static final String DB_CREATE_COLLEZIONE= "create table " + EuroCollDB.CollectionDB.TABLE_COLLEZIONE +"("+ EuroCollDB.CollectionDB._ID+ " integer primary key autoincrement,"+
            EuroCollDB.CollectionDB.COLUMN_NOME + " text not null," +
            EuroCollDB.CollectionDB.COLUMN_TIPO + " text not null," +
            EuroCollDB.CollectionDB.COLUMN_NOTE + " text);";


    private static final String DB_CREATE_COINS= "create table " + EuroCollDB.CoinDB.TABLE_COINS +"("+ EuroCollDB.CoinDB._ID+ " integer primary key autoincrement,"+
            EuroCollDB.CoinDB.COLUMN_TAGLIO + " float not null,"
            + EuroCollDB.CoinDB.COLUMN_MATERIALE + " text not null," +
            EuroCollDB.CoinDB.COLUMN_PAESE + " text not null,"+
            EuroCollDB.CoinDB.COLUMN_ANNO + " integer not null," +
            EuroCollDB.CoinDB.COLUMN_IMAGE +" text not null," +
            EuroCollDB.CoinDB.COLUMN_REF_COLL+ " integer not null"+
            ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();
        db.execSQL( DB_CREATE_COLLEZIONE);
       // onUpgrade(this,1,2);
        db.execSQL( DB_CREATE_COINS);
            db.setTransactionSuccessful();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName()," Upgrading db from version "+ oldVersion + "to "+newVersion+", which will destroy all old data");
       try{

        db.beginTransaction();
        db.execSQL("DROP TABLE IF EXIST "+ EuroCollDB.CollectionDB.TABLE_COLLEZIONE);
        db.execSQL("DROP TABLE IF EXIST "+ EuroCollDB.CoinDB.TABLE_COINS);
        db.setTransactionSuccessful();
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           db.endTransaction();
       }
        onCreate(db);
    }

    public MySQLiteHelper(Context context){
        super(context, EuroCollDB.DB_NAME,null,EuroCollDB.DB_VERSION);
    }

}
