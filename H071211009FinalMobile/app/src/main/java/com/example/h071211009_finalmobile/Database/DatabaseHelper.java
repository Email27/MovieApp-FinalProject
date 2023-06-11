package com.example.h071211009_finalmobile.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.h071211009_finalmobile.Model.Movie;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "movies";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_RELEASE_DATE = "release_date";
    private static final String COLUMN_OVERVIEW = "overview";
    private static final String COLUMN_POSTER_URL = "poster_url";
    private static final String COLUMN_BACKDROP_URL = "backdrop_url";
    private static final String COLUMN_VOTE_AVERAGE = "vote_average";
    private static final String COLUMN_GENRE_IDS = "genre_ids";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_RELEASE_DATE + " TEXT, " +
                COLUMN_OVERVIEW + " TEXT, " +
                COLUMN_POSTER_URL + " TEXT, " +
                COLUMN_BACKDROP_URL + " TEXT, " +
                COLUMN_VOTE_AVERAGE + " REAL, " +
                COLUMN_GENRE_IDS + " INTEGER" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public long insertMovie(Movie movie) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(COLUMN_OVERVIEW, movie.getOverview());
        values.put(COLUMN_POSTER_URL, movie.getPosterPath());
        values.put(COLUMN_BACKDROP_URL, movie.getBackdropUrl());
        values.put(COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(COLUMN_GENRE_IDS, movie.getId());

        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor getAllMovies() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                COLUMN_ID,
                COLUMN_TITLE,
                COLUMN_RELEASE_DATE,
                COLUMN_OVERVIEW,
                COLUMN_POSTER_URL,
                COLUMN_BACKDROP_URL,
                COLUMN_VOTE_AVERAGE,
                COLUMN_GENRE_IDS
        };
        return db.query(TABLE_NAME, projection, null, null, null, null, null);
    }

    public int updateMovie(Movie movie) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(COLUMN_OVERVIEW, movie.getOverview());
        values.put(COLUMN_POSTER_URL, movie.getPosterPath());
        values.put(COLUMN_BACKDROP_URL, movie.getBackdropUrl());
        values.put(COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(COLUMN_GENRE_IDS, movie.getId());

        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(movie.getId())};

        return db.update(TABLE_NAME, values, selection, selectionArgs);
    }

    public int deleteMovie(String title) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = COLUMN_TITLE + "=?";
        String[] selectionArgs = {title};

        return db.delete(TABLE_NAME, selection, selectionArgs);
    }

    public boolean isMovieInFavorites(String movieTitle) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_TITLE + " = ?";
        String[] selectionArgs = {movieTitle};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean isMovieInFavorites = cursor != null && cursor.getCount() > 0;
        cursor.close();
        return isMovieInFavorites;
    }
}