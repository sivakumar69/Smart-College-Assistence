package com.example.siva.attendanceassist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Siva on 29-Feb-16.
 */
public class SubjectStatus_Database extends SQLiteOpenHelper {

    public static final String database_name = "attendenceassit3.db";
    public static final String table2 = "calinfo_present";
    public static final String table3 = "calinfo_absent";
    public static final String table4 = "calinfo_2attended";
    public static final String table5 = "calinfo_2absent";

    public SubjectStatus_Database(Context context) {
        super(context, database_name, null, 6);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + table2 + "(subject_name text,date integer,month integer,year integer)");
        db.execSQL("create table " + table3 + "(subject_name text,date integer,month integer,year integer)");
        db.execSQL("create table " + table4 + "(subject_name text,date integer,month integer,year integer)");
        db.execSQL("create table " + table5 + "(subject_name text,date integer,month integer,year integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + table2);
        db.execSQL("drop table if exists " + table3);
        db.execSQL("drop table if exists " + table4);
        db.execSQL("drop table if exists " + table5);
        onCreate(db);

    }

    public boolean insert_data(int n, String arg1, String arg2, String arg3, String arg4) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (n == 0) {
            ContentValues cv = new ContentValues();
            cv.put("subject_name", arg1);
            cv.put("date", arg2);
            cv.put("month", arg3);
            cv.put("year", arg4);
            long result = db.insert(table2, null, cv);
            if (result == -1)
                return false;
            else
                return true;
        }
        if (n == 1) {
            ContentValues cv = new ContentValues();
            cv.put("subject_name", arg1);
            cv.put("date", arg2);
            cv.put("month", arg3);
            cv.put("year", arg4);
            long result = db.insert(table3, null, cv);
            if (result == -1)
                return false;
            else
                return true;

        }
        if (n == 2) {
            ContentValues cv = new ContentValues();
            cv.put("subject_name", arg1);
            cv.put("date", arg2);
            cv.put("month", arg3);
            cv.put("year", arg4);
            long result = db.insert(table4, null, cv);
            if (result == -1)
                return false;
            else
                return true;

        }
        if (n == 3) {
            ContentValues cv = new ContentValues();
            cv.put("subject_name", arg1);
            cv.put("date", arg2);
            cv.put("month", arg3);
            cv.put("year", arg4);
            long result = db.insert(table5, null, cv);
            if (result == -1)
                return false;
            else
                return true;

        }
        return false;
    }

    public boolean delete_dates(String sub_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int n = db.delete(table2, "subject_name = ?", new String[]{sub_name});
        int m = db.delete(table3, "subject_name = ?", new String[]{sub_name});
        int o = db.delete(table4, "subject_name = ?", new String[]{sub_name});
        int p = db.delete(table5, "subject_name = ?", new String[]{sub_name});
        if (n < 0 || m < 0 || o < 0 || p < 0)
            return false;
        else
            return true;
    }

    public String return_present(String sub, int n, String month,String year) {
        SQLiteDatabase db = this.getWritableDatabase();
        String s = "";
        if (n == 0) {
            Cursor rs = db.rawQuery("select * from " + table2, null);
            while (rs.moveToNext()) {
                if (rs.getString(0).equals(sub) && rs.getString(2).equals(month) &&  rs.getString(3).equals(year)) {
                    s = s + rs.getString(1);
                    s = s + "-";
                }
            }
            rs.close();
        }
        if (n == 1) {
            Cursor rp = db.rawQuery("select * from " + table3, null);
            while (rp.moveToNext()) {
                if (rp.getString(0).equals(sub) && rp.getString(2).equals(month) &&  rp.getString(3).equals(year)) {
                    s = s + rp.getString(1);
                    s = s + "-";
                }
            }
            rp.close();
        }
        if (n == 2) {
            Cursor rq = db.rawQuery("select * from " + table4, null);
            while (rq.moveToNext()) {
                if (rq.getString(0).equals(sub) && rq.getString(2).equals(month) &&  rq.getString(3).equals(year)) {
                    s = s + rq.getString(1);
                    s = s + "-";
                }
            }
            rq.close();
        }
        if (n == 3) {
            Cursor ra = db.rawQuery("select * from " + table5, null);
            while (ra.moveToNext()) {
                if (ra.getString(0).equals(sub) && ra.getString(2).equals(month) &&  ra.getString(3).equals(year)) {
                    s = s + ra.getString(1);
                    s = s + "-";
                }
            }
            ra.close();
        }

        return s;
    }
}
