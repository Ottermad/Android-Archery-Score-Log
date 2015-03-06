package me.attwoodthomas.archeryscorelog.database.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Member Variables
    public String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "archeryscorelog.mDatabaseHelper";
    private static final int DATABASE_VERSION = 3;
    public String[] mTables = {"TBL_SCORES", "TBL_ARROWS"};

    public DatabaseHelper (Context paramContext) {
        super(paramContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase paramSQLiteDatabase) {

        try {
            // Create Scores Table
            paramSQLiteDatabase.execSQL("CREATE TABLE " + mTables[0] + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATETIME TEXT, TOTAL INTEGER, DISTANCE INTEGER, LOCATION TEXT, STYLE TEXT, INDOOR_OR_OUTDOOR TEXT, NUMBER_OF_ARROWS INTEGER, DISTANCE_UNITS TEXT)");

            // Create Arrows Table
            paramSQLiteDatabase.execSQL("CREATE TABLE " + mTables[1] + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, VALUE INTEGER, SCOREID INTEGER,FOREIGN KEY (SCOREID) REFERENCES TBL_SCORES(ID))");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2) {
        for (String name : mTables) {
            paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + name);
        }




        onCreate(paramSQLiteDatabase);
    }

    public void insertScore (String datetime, int[] scores, int distance, String location, String style, String indoorOrOutdoor, int numberOfArrows, String units) {
        int total = 0;
        for (int score : scores) {
            total = total + score;
        }
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        String insertScoreString = "INSERT INTO " + mTables[0] + " (DATETIME, TOTAL, DISTANCE, LOCATION, STYLE, INDOOR_OR_OUTDOOR, NUMBER_OF_ARROWS, DISTANCE_UNITS) VALUES ('" + datetime +"', '" + total + "', '" + distance + "', '"+ location +"', '" + style +"', '" + indoorOrOutdoor + "', '" + numberOfArrows +"', '" + units + "');";
        localSQLiteDatabase.execSQL(insertScoreString);
        String[] args = {datetime, Integer.toString(total)};
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT ID FROM " + mTables[0] +" WHERE DATETIME = ? AND TOTAL = ?", args);
        localCursor.moveToFirst();
        int id = Integer.parseInt(localCursor.getString(0));
        for (int value: scores) {
            String insertArrowString = "INSERT INTO " + mTables[1] + " (SCOREID, VALUE) VALUES ('" + id +"', '" + value + "');";
            localSQLiteDatabase.execSQL(insertArrowString);
        }
        /*
        for (int i = 0; i < scores.length; i++) {
            int value = scores[i];
            String insertArrowString = "INSERT INTO " + mTables[1] + " (SCOREID, VALUE) VALUES ('" + id +"', '" + value + "');";
            localSQLiteDatabase.execSQL(insertArrowString);
        }*/
    }

    public ArrayList<HashMap<String, String>> getAllScores () {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM " + mTables[0] + " ORDER BY ID DESC", null);
        ArrayList<HashMap<String, String>> scores = new ArrayList<>();
        localCursor.moveToFirst();
        while (!localCursor.isAfterLast())
        {
            HashMap<String, String> score = new HashMap<>();
            score.put("DateTime", localCursor.getString(1));
            score.put("Total", localCursor.getString(2));
            scores.add(score);
            localCursor.moveToNext();
        }
        return scores;
    }

    public HashMap<String, String> getScore (String datetime, String total) {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        String[] args = {datetime, total};
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM " + mTables[0] +" WHERE DATETIME = ? AND TOTAL = ?", args);
        localCursor.moveToFirst();
        int id = Integer.parseInt(localCursor.getString(0));
        HashMap<String, String> score = new HashMap<>();
        score.put("DateTime", localCursor.getString(1));
        score.put("Total", localCursor.getString(2));
        score.put("Distance", localCursor.getString(3));
        score.put("Location", localCursor.getString(4));
        score.put("Style", localCursor.getString(5));
        score.put("IndoorOrOutdoor", localCursor.getString(6));
        score.put("NumberOfArrows", localCursor.getString(7));
        score.put("Units", localCursor.getString(8));
        String[] args2 = {Integer.toString(id)};
        localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM " + mTables[1] + " WHERE SCOREID = ?", args2);
        localCursor.moveToFirst();
        int c = 1;
        while (!localCursor.isAfterLast())
        {
            score.put("Arrow" + c, localCursor.getString(1));
            c++;
            localCursor.moveToNext();
        }
        return score;
    }

    public void deleteScore (String datetime, String total) {
        SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
        String[] args = {datetime, total};
        Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM " + mTables[0] +" WHERE DATETIME = ? AND TOTAL = ?", args);
        localCursor.moveToFirst();
        int id = Integer.parseInt(localCursor.getString(0));
        String deleteString = "DELETE FROM " + mTables[0] + " WHERE ID = " + id + ";";
        localSQLiteDatabase.execSQL(deleteString);
    }
}
