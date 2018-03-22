package com.enorkus.popmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.enorkus.popmovies.data.MovieContract.MovieEntry;

public class MoviesDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "movies.db";
    private static final int version = 3;

    public MoviesDBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_MOVIES_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" + MovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " + MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " + MovieEntry.COLUMN_POSTER + " TEXT NOT NULL, " + MovieEntry.COLUMN_VOTE_AVERAGE + " TEXT NOT NULL, "
                + MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL" + ");";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
