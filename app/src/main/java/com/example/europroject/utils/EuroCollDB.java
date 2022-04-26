package com.example.europroject.utils;

import android.provider.BaseColumns;

public class EuroCollDB {
    public static final int DB_VERSION= 1;
    public static final String DB_NAME= "euroColl.db";

    public static class CoinDB implements BaseColumns{

        public static final String TABLE_COINS="coins";
     //   public static final String COLUMN_ID_COIN= "_id";
        public static final String COLUMN_TAGLIO= "taglio";
        public static final String COLUMN_MATERIALE= "materiale";
        public static final String COLUMN_PAESE= "paese";
        public static final String COLUMN_ANNO= "anno";
        public static final String COLUMN_IMAGE= "image";
        public static final String COLUMN_REF_COLL= "coll_id";
    }


    public static class CollectionDB implements BaseColumns{

      //public static final String COLUMN_ID_COLLEZIONE= "_id";
        public static final String COLUMN_NOME= "nome";
        public static final String COLUMN_TIPO= "tipo";
        public static final String COLUMN_NOTE= "nota";
        public static final String TABLE_COLLEZIONE="collezione";

    }
}
