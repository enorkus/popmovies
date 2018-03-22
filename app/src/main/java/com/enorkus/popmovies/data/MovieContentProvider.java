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

import static com.enorkus.popmovies.data.MovieContract.*;

public class MovieContentProvider extends ContentProvider {

    private static final int FAVORITE = 100;
    private static final int FAVORITE_BY_ID = 101;

    private MoviesDBHelper dbHelper;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, PATH_FAVORITE, FAVORITE);
        matcher.addURI(AUTHORITY, PATH_FAVORITE + "/#", FAVORITE_BY_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MoviesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);

        Cursor result;
        switch (match) {
            case FAVORITE:
                result = db.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case FAVORITE_BY_ID:
                String id = uri.getPathSegments().get(1);
                result = db.query(MovieEntry.TABLE_NAME, projection, "id=?", new String[]{id}, null, null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        result.setNotificationUri(getContext().getContentResolver(), uri);
        return result;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        Uri result;
        switch (match) {
            case FAVORITE:
                long id = db.insert(MovieEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    result = ContentUris.withAppendedId(MovieEntry.FAVORITE_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String whereClause, @Nullable String[] whereArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        int tasksDeleted;
        switch (match) {
            case FAVORITE_BY_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(MovieEntry.TABLE_NAME, "id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        if(tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
