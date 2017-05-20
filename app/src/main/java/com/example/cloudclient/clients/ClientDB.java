package com.example.cloudclient.clients;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.os.Build.VERSION_CODES.M;


/**
 * Created by jo on 2017/3/2.
 */
public class ClientDB extends SQLiteOpenHelper {
    private static ClientDB instance = null;
    public static ClientDB getInstance(Context ctx){
        if(instance == null){
            instance = new ClientDB(ctx,"Client",null,1);
        }
        return instance;
    }
    private ClientDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Client "+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, "+"name VARCHAR(32), "+"phone VARCHAR(16), "+"vehicle VARCHAR(16), " +
                "info VARCHAR(64), "+"plate VARCHAR(7), "+"milage INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
