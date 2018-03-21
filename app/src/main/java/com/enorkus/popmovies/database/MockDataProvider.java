package com.enorkus.popmovies.database;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MockDataProvider {

    public static void insertFakeData(SQLiteDatabase db) {
        if (db == null) {
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_ID, 10);
        cv.put(MovieEntry.COLUMN_POSTER, "poster1");
        cv.put(MovieEntry.COLUMN_TITLE, "poster1");
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, "poster1");
        cv.put(MovieEntry.COLUMN_VOTE_AVERAGE, "poster1");
        cv.put(MovieEntry.COLUMN_OVERVIEW, "poster1");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_ID, 20);
        cv.put(MovieEntry.COLUMN_POSTER, "poster2");
        cv.put(MovieEntry.COLUMN_TITLE, "poster2");
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, "poster2");
        cv.put(MovieEntry.COLUMN_VOTE_AVERAGE, "poster2");
        cv.put(MovieEntry.COLUMN_OVERVIEW, "poster2");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_ID, 30);
        cv.put(MovieEntry.COLUMN_POSTER, "poster3");
        cv.put(MovieEntry.COLUMN_TITLE, "poster3");
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, "poster3");
        cv.put(MovieEntry.COLUMN_VOTE_AVERAGE, "poster3");
        cv.put(MovieEntry.COLUMN_OVERVIEW, "poster3");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_ID, 40);
        cv.put(MovieEntry.COLUMN_POSTER, "poster4");
        cv.put(MovieEntry.COLUMN_TITLE, "poster4");
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, "poster4");
        cv.put(MovieEntry.COLUMN_VOTE_AVERAGE, "poster4");
        cv.put(MovieEntry.COLUMN_OVERVIEW, "poster4");
        list.add(cv);

        cv = new ContentValues();
        cv.put(MovieEntry.COLUMN_ID, 50);
        cv.put(MovieEntry.COLUMN_POSTER, "poster5");
        cv.put(MovieEntry.COLUMN_TITLE, "poster5");
        cv.put(MovieEntry.COLUMN_RELEASE_DATE, "poster5");
        cv.put(MovieEntry.COLUMN_VOTE_AVERAGE, "poster5");
        cv.put(MovieEntry.COLUMN_OVERVIEW, "poster5");
        list.add(cv);


        //insert all guests in one transaction
        try {
            db.beginTransaction();
            //clear the table first
            db.delete(MovieEntry.TABLE_NAME, null, null);
            //go through the list and add one by one
            for (ContentValues c : list) {
                db.insert(MovieEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            //too bad :(
        } finally {
            db.endTransaction();
        }
    }
}
