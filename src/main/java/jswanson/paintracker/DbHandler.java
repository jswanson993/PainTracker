package jswanson.paintracker;

/**
 * Created by jswan on 5/10/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Console;
import java.util.LinkedList;

public class DbHandler extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final  String DATABASE_NAME = "times.db ";
    public static final String TABLE_NAME = "times ";
    public static final String COLUMN_ID = "_id ";
    public static final String COLUMN_YEAR = "year ";
    public static final String COLUMN_MONTH = "month ";
    public static final String COLUMN_DATE = "date ";
    public static final String COLUMN_TIME = "time ";


    public DbHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     Creates the database  with the column names and types
     */
    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                COLUMN_YEAR + " INTEGER, " +
                COLUMN_MONTH + " INTEGER, " +
                COLUMN_DATE + " INTEGER, " +
                COLUMN_TIME + " TEXT)";

        db.execSQL(query);
    }

    /**
     Deletes the old table and makes a new one
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    /**
     calls onUpgrade and creates new version
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     Adds a new row to the database
     */
    public void addTime(int year, int month, int day, String time){
        ContentValues values = new ContentValues();
        //Adding each value to the ContentValues object
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_MONTH, month);
        values.put(COLUMN_DATE, day);
        values.put(COLUMN_TIME, time);

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_NAME, null, values);
            db.close();
        }catch(Exception e){
            Log.e("Exception: ", e.fillInStackTrace().toString());
        }
    }



    /**
     * Deletes a row from the database
     */
    public void removeTime(int year, int month, int day, String time){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM" + TABLE_NAME + "WHERE " + COLUMN_YEAR  + "=\"" + year + "\", " +
                COLUMN_MONTH + "=\"" + month + "\", " + COLUMN_DATE + "=\"" + day + "\", " + COLUMN_TIME + "=\"" + time + "\";");
    }

    /**
     * Gets the times from the database
     */
    public LinkedList<String> getTimes(int year, int month, int day){
        LinkedList<String> times = new LinkedList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + COLUMN_TIME + "FROM " + TABLE_NAME + "WHERE (" + COLUMN_YEAR  + "=" + year + " AND " +
                COLUMN_MONTH + "=" + month + " AND " + COLUMN_DATE + "= "+ day + ");";

        //points to location in results
        Cursor c;
        try {
             c = db.rawQuery(query, null);
        }catch(Exception e){
            return times;
        }
        //moves to the first row of the results
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("time")) != null){
                times.add(c.getString(c.getColumnIndex("time")));
                c.moveToNext();
            }
        }
        db.close();
        return times;
    }



}

