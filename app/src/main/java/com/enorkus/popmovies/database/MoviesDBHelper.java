package com.enorkus.popmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.enorkus.popmovies.entity.Movie;

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

    public void saveMovie(Movie movie) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues movieCV = new ContentValues();
        movieCV.put(MovieEntry.COLUMN_ID, movie.getId());
        movieCV.put(MovieEntry.COLUMN_POSTER, movie.getPoster());
        movieCV.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
        movieCV.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        movieCV.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        movieCV.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        db.insert(MovieEntry.TABLE_NAME, null, movieCV);
        db.close();
    }

    public void deleteMovie(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = MovieEntry.COLUMN_ID + "=?";
        String[] idArg = {String.valueOf(id)};
        db.delete(MovieEntry.TABLE_NAME, whereClause, idArg);
        db.close();
    }

    public boolean favoriteMovieExists(int id) {
        String[] idArg = {String.valueOf(id)};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MovieEntry.TABLE_NAME + " WHERE " + MovieEntry.COLUMN_ID + "=?", idArg);
        try {
            if(cursor.getCount() != 0) {
                return true;
            }
        } finally {
            cursor.close();
            db.close();
        }
        return false;
    }
}
