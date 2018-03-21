package com.enorkus.popmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.enorkus.popmovies.database.MovieContract;
import com.enorkus.popmovies.database.MoviesDBHelper;

import static com.enorkus.popmovies.database.MovieContract.*;

public class MovieContentProvider extends ContentProvider {

    private static final int FAVORITE = 100;

    private MoviesDBHelper dbHelper;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, PATH_FAVORITE, FAVORITE);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MoviesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        Uri returnUri;
        try {
            switch (match) {
                case FAVORITE:
                    long id = db.insert(MovieEntry.TABLE_NAME, null, contentValues);
                    if(id > 0) {
                        returnUri = ContentUris.withAppendedId(MovieEntry.FAVORITE_URI, id);
                    } else {
                        throw new SQLException("Failed to insert row into " + uri);
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Unknown uri " + uri);
            }

            getContext().getContentResolver().notifyChange(uri, null);
        } finally {
            db.close();
        }
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
