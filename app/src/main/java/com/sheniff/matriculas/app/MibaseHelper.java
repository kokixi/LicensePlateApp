package com.sheniff.matriculas.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sheniff.matriculas.app.MibaseContract.FeedEntry;


public class MibaseHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COLUMN_NOMBRE + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_DIRECCION + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NIF + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_MATRICULA + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_TIPO + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_MODELO + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_BASTIDOR + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EntradaMatriculas.db";

    public MibaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
