package com.enorkus.popmovies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.enorkus.popmovies.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import static com.enorkus.popmovies.database.MovieContract.*;

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

    public List<Movie> fetchAllFavoriteMovies() {
        SQLiteDatabase db = getReadableDatabase();
        List<Movie> movies = new ArrayList<>();
        Cursor cursor = db.query(MovieEntry.TABLE_NAME, null, null, null, null, null, null);
        try {
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_ID));
                String poster = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER));
                String title = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_TITLE));
                String overview = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW));
                String releaseDate = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE));
                String voteAverage = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_VOTE_AVERAGE));
                movies.add(new Movie(id, title, releaseDate, poster, voteAverage, overview));
            }

        } finally {
            cursor.close();
            db.close();
        }
        return movies;
    }
}
