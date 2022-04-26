
package com.example.europroject.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.europroject.utils.EuroCollDB;
import com.example.europroject.utils.MyApplication;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Coin_Dao_DB_imp implements CoinDAO{
    private SQLiteDatabase db;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {EuroCollDB.CoinDB._ID,EuroCollDB.CoinDB.COLUMN_TAGLIO,EuroCollDB.CoinDB.COLUMN_PAESE,EuroCollDB.CoinDB.COLUMN_MATERIALE, EuroCollDB.CoinDB.COLUMN_ANNO,EuroCollDB.CoinDB.COLUMN_IMAGE, EuroCollDB.CoinDB.COLUMN_REF_COLL};
    @Override
    public void open() {
        if(dbHelper==null) dbHelper =new MySQLiteHelper(MyApplication.getAppContext());
        db= dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public Coin insertCoin(Coin coin) {

        long insertId = db.insert(EuroCollDB.CoinDB.TABLE_COINS, null,coinToValues(coin));
        //read from db the inserted coin and return it
        Cursor cursor = db.query(EuroCollDB.CoinDB.TABLE_COINS,allColumns,EuroCollDB.CoinDB._ID+"=?", new String[] {""+insertId},null,null,null);
        cursor.moveToFirst();
        Coin c = cursorToCoin(cursor);
        cursor.close();
        return c;
    }

    @Override
    public void deleteCoin(Coin coin) {
        long id= coin.getId();
        db.delete(EuroCollDB.CoinDB.TABLE_COINS, EuroCollDB.CoinDB._ID+ "=?",new String[] {""+id});

    }

    @Override
    public List<Coin> getAllCoin(long idColl) {
        List<Coin> coins = new ArrayList<Coin>();
        Cursor cursor = db.query(EuroCollDB.CoinDB.TABLE_COINS,null,EuroCollDB.CoinDB.COLUMN_REF_COLL+"=?",new String[] {""+idColl},null,null,EuroCollDB.CoinDB.COLUMN_TAGLIO);
       // Cursor cursor = db.rawQuery("select * from " + EuroCollDB.CoinDB.TABLE_COINS + " where " + EuroCollDB.CoinDB.COLUMN_REF_COLL+"=?",new String[] {""+idColl});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Coin coin=cursorToCoin(cursor);
            coins.add(coin);
            cursor.moveToNext();

        }
        cursor.close();
        return coins;
    }


    @Override
    public List<Coin> getCoinsForCountry(String paese, long idColl) {
        List<Coin> coins = new ArrayList<Coin>();
        Cursor cursor = db.query(EuroCollDB.CoinDB.TABLE_COINS,null,EuroCollDB.CoinDB.COLUMN_PAESE + " = ? AND " + EuroCollDB.CoinDB.COLUMN_REF_COLL+"=?",new String[] {paese,""+idColl},null,null,EuroCollDB.CoinDB.COLUMN_TAGLIO);

        //Cursor cursor1 = db.rawQuery("select * from " + EuroCollDB.CoinDB.TABLE_COINS + " where " + EuroCollDB.CoinDB.COLUMN_PAESE + " = ? AND " + EuroCollDB.CoinDB.COLUMN_REF_COLL+"=?",new String[] {paese,""+idColl});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Coin coin=cursorToCoin(cursor);
            coins.add(coin);
            cursor.moveToNext();

        }
        cursor.close();
        return coins;
    }

    @Override
    public List<Coin> getCoinsForYear(int anno, long idColl) {
        List<Coin> coins = new ArrayList<Coin>();
        Cursor cursor = db.query(EuroCollDB.CoinDB.TABLE_COINS,null,EuroCollDB.CoinDB.COLUMN_ANNO + " = ? AND " + EuroCollDB.CoinDB.COLUMN_REF_COLL+"=?",new String[] {""+anno,""+idColl},null,null,EuroCollDB.CoinDB.COLUMN_TAGLIO);

      //  Cursor cursor = db.rawQuery("select * from " + EuroCollDB.CoinDB.TABLE_COINS + " where " + EuroCollDB.CoinDB.COLUMN_ANNO + " = ? AND " + EuroCollDB.CoinDB.COLUMN_REF_COLL+"=?",new String[] {""+anno,""+idColl});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Coin coin=cursorToCoin(cursor);
            coins.add(coin);
            cursor.moveToNext();

        }
        cursor.close();
        return coins;
    }

    @Override
    public List<Coin> getCoinsForPiece(float taglio,long idColl) {
        List<Coin> coins = new ArrayList<Coin>();
        Cursor cursor = db.rawQuery("select * from " + EuroCollDB.CoinDB.TABLE_COINS + " where " + EuroCollDB.CoinDB.COLUMN_TAGLIO + " = ? AND " + EuroCollDB.CoinDB.COLUMN_REF_COLL+"=?",new String[] {""+taglio,""+idColl});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Coin coin=cursorToCoin(cursor);
            coins.add(coin);
            cursor.moveToNext();

        }
        cursor.close();
        return coins;
    }

    @Override
    public int getCount(long idColl) {
        List<Coin> array=new ArrayList<Coin>();
        array.addAll(getAllCoin(idColl));
        array.size();
        return array.size();
    }


    public List<String> getCountrys(long idColl){
        List<String> paesi = new ArrayList<String>();

        Cursor cursor = db.query(EuroCollDB.CoinDB.TABLE_COINS,new String[]{EuroCollDB.CoinDB.COLUMN_PAESE},EuroCollDB.CoinDB.COLUMN_REF_COLL+"=?",new String[] {""+idColl},null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String p=cursor.getString(0);
            paesi.add(p);
            cursor.moveToNext();

        }
        cursor.close();
        Set<String> paesiUniciT = new HashSet<String>(paesi);
        List<String> paesiU = new ArrayList<>(paesiUniciT);

        return paesiU;
    }


    public List<Integer> getYears(long idColl){
        List<Integer> anni = new ArrayList<Integer>();
        Cursor cursor = db.query(EuroCollDB.CoinDB.TABLE_COINS,new String[]{EuroCollDB.CoinDB.COLUMN_ANNO},EuroCollDB.CoinDB.COLUMN_REF_COLL+"=?",new String[] {""+idColl},null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int p=cursor.getInt(0);
            anni.add(p);
            cursor.moveToNext();

        }
        cursor.close();

        Set<Integer> anniUniciT = new HashSet<Integer>(anni);
        List<Integer> anniU = new ArrayList<>(anniUniciT);
        return anniU;
    }

    // from object to db
    private ContentValues coinToValues(Coin coin){

        ContentValues values= new ContentValues();
        values.put(EuroCollDB.CoinDB.COLUMN_TAGLIO, coin.getTaglio());
        values.put(EuroCollDB.CoinDB.COLUMN_PAESE, coin.getPaese());
        values.put(EuroCollDB.CoinDB.COLUMN_ANNO, coin.getAnno());
        values.put(EuroCollDB.CoinDB.COLUMN_MATERIALE, coin.getMateriale());
       values.put(EuroCollDB.CoinDB.COLUMN_IMAGE, coin.getImageUri());
        values.put(EuroCollDB.CoinDB.COLUMN_REF_COLL, coin.getIdCollection());
        return values;
    }

    // from db to object
    private Coin cursorToCoin(Cursor cursor){
        long id = cursor.getLong(0);
        double taglio = cursor.getDouble(1);
        String materiale = cursor.getString(2);
        String  paese = cursor.getString(3);
        int anno = cursor.getInt(4);
        String imageUri =cursor.getString(5);
        long idColl = cursor.getLong(6);
        return new Coin(id, taglio,materiale,paese,anno,imageUri,idColl);
    }

}
