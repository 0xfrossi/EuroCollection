package com.example.europroject.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.europroject.utils.EuroCollDB;
import com.example.europroject.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class Collection_DAO_DB_imp implements CollectionDAO{

    private SQLiteDatabase db;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {EuroCollDB.CollectionDB._ID,EuroCollDB.CollectionDB.COLUMN_NOME,EuroCollDB.CollectionDB.COLUMN_TIPO,EuroCollDB.CollectionDB.COLUMN_NOTE};

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
    public Collection insertCollection(Collection coll) {
        long insertId = db.insert(EuroCollDB.CollectionDB.TABLE_COLLEZIONE, null,coinToValues(coll));
        //read from db the inserted coin and return it
        Cursor cursor = db.query(EuroCollDB.CollectionDB.TABLE_COLLEZIONE,allColumns,EuroCollDB.CollectionDB._ID+"=?", new String[] {""+insertId},null,null,null);
        cursor.moveToFirst();
        Collection c = cursorToCollection(cursor);
        cursor.close();
        return c;


    }

    @Override //also delete coins associated
    public void deleteCollection(Collection coll) {

        long id= coll.getId();
        db.delete(EuroCollDB.CollectionDB.TABLE_COLLEZIONE, EuroCollDB.CollectionDB._ID+ "=?",new String[] {""+id});

    }

    @Override
    public List<Collection> getAllCollection() {
        List<Collection> colls = new ArrayList<Collection>();
        Cursor cursor = db.query(EuroCollDB.CollectionDB.TABLE_COLLEZIONE,allColumns,null,null,null,null
                ,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Collection coll=cursorToCollection(cursor);
            colls.add(coll);
            cursor.moveToNext();

        }
        cursor.close();
        return colls;
    }

    @Override
    public void editNote(Collection coll, String newNote) {
        long id= coll.getId();
        ContentValues values = new ContentValues();
        values.put(EuroCollDB.CollectionDB.COLUMN_NOME, coll.getNome());
        values.put(EuroCollDB.CollectionDB.COLUMN_TIPO, coll.getTipo());
        values.put(EuroCollDB.CollectionDB.COLUMN_NOTE, newNote);

        db.update(EuroCollDB.CollectionDB.TABLE_COLLEZIONE, values, EuroCollDB.CollectionDB._ID + "=?", new String[] {""+id});
    }


    private ContentValues coinToValues(Collection coll){
        ContentValues values= new ContentValues();
        values.put(EuroCollDB.CollectionDB.COLUMN_NOME, coll.getNome());
        values.put(EuroCollDB.CollectionDB.COLUMN_TIPO, coll.getTipo());
        values.put(EuroCollDB.CollectionDB.COLUMN_NOTE, coll.getNota());

        return values;
    }

    private Collection cursorToCollection(Cursor cursor){
        long id = cursor.getLong(0);
        String nome = cursor.getString(1);
        String tipo = cursor.getString(2);
        String note = cursor.getString(3);
        return new Collection(id,nome, tipo,note);
    }



}
