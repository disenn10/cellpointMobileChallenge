package com.example.disen.cellpointassessment.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by disen on 7/31/2018.
 */

public class RepositoryHelper extends SQLiteOpenHelper {
    static String db_name = RepositoryContract.RepositoryEntry.db_name;
    static int version = 2;
    public RepositoryHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, db_name, factory, version);
    }

    public static String CreateEntries = "CREATE TABLE " + RepositoryContract.RepositoryEntry.db_name+"("
            + RepositoryContract.RepositoryEntry.ColumnID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RepositoryContract.RepositoryEntry.ColumnRepository_name+ " TEXT, "
            + RepositoryContract.RepositoryEntry.ColumnCreated+ " TEXT, "
            + RepositoryContract.RepositoryEntry.ColumnDesc+ " TEXT, "
            + RepositoryContract.RepositoryEntry.ColumnUpdated+ " TEXT) ";
    public static String deleteTable = "DROP TABLE IF EXISTS "+ db_name;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CreateEntries);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(deleteTable);
        onCreate(sqLiteDatabase);
    }
}
