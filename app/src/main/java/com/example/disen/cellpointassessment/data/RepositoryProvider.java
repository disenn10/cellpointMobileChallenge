package com.example.disen.cellpointassessment.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by disen on 8/1/2018.
 */

public class RepositoryProvider extends ContentProvider {
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    final int repository = 100;
    final int repository_id = 101;
    SQLiteDatabase sqLiteDatabase;
    RepositoryHelper myDbHelper;
    @Override
    public boolean onCreate() {
        uriMatcher.addURI(RepositoryContract.repositoryAuthority,RepositoryContract.path,repository);
        uriMatcher.addURI(RepositoryContract.repositoryAuthority,RepositoryContract.path+"/#",repository_id);
        myDbHelper = new RepositoryHelper(getContext(),null);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        int path = uriMatcher.match(uri);
        Cursor cursor;
        sqLiteDatabase = myDbHelper.getReadableDatabase();
        switch (path){
            case repository:
                cursor = sqLiteDatabase.query(RepositoryContract.RepositoryEntry.db_name, strings,s,strings1,null,null,null);
                break;
            case repository_id:
                s = RepositoryContract.RepositoryEntry.ColumnID + "=?";
                strings1 = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(RepositoryContract.RepositoryEntry.db_name, strings,s,strings1,null,null,null);
                break;
            default:
                throw new IllegalArgumentException("Can't peform uri request");
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        long ID;
        int path = uriMatcher.match(uri);
        switch (path){
            case repository:
                ID = sqLiteDatabase.insert(RepositoryContract.RepositoryEntry.db_name,null,contentValues);
                break;
            default:
                throw new IllegalArgumentException("Cannot insert values");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,ID);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int id;
        int path = uriMatcher.match(uri);
        sqLiteDatabase = myDbHelper.getWritableDatabase();
        switch (path){
            case repository:
                id = sqLiteDatabase.delete(RepositoryContract.RepositoryEntry.db_name,s,strings);
                break;
            case repository_id:
                s = RepositoryContract.RepositoryEntry.ColumnID = "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                id = sqLiteDatabase.delete(RepositoryContract.RepositoryEntry.ColumnID, s,strings);
                break;
            default:
                throw new IllegalArgumentException("could not delete record");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
