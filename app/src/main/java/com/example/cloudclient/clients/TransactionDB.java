package com.example.cloudclient.clients;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jo on 2017/3/25.
 */

public class TransactionDB extends SQLiteOpenHelper {
    private static TransactionDB instance = null;

    public static TransactionDB getInstance(Context ctx){
        if(instance == null){
            instance = new TransactionDB(ctx,"Transaction",null,1);
        }
        return instance;
    }
    public TransactionDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TClient "+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, "+"clientid INTEGER, "+"item VARCHAR(16), "+"totalmoney VARCHAR(16), " +
                "date INTEGER, "+"milage INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
