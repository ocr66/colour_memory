package com.accedo.colourmemory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by o.lopez.cienfuegos on 19/03/2016.
 */
public class HighscoreDatabase extends SQLiteOpenHelper {

    private String ID = "id";
    private String NAME = "name";
    private String RANK = "rank";
    private String SCORE = "score";
    private String TABLE_NAME = "highscores";

    public HighscoreDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                        ID + " INTEGER primary key autoincrement, " +
                        NAME + " VARCHAR(15), " +
                        SCORE + " INTEGER)"
            );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                        ID + " INTEGER primary key autoincrement, " +
                        NAME + " VARCHAR(15), " +
                        SCORE + " INTEGER)"
        );
    }


}
