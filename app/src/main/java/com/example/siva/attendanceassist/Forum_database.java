package com.example.siva.attendanceassist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Siva on 09-Apr-16.
 */
public class Forum_database extends SQLiteOpenHelper {

    public static final String database_name = "forum4.db";
    public static final String table1 = "qa_table";
    public static final String table2 = "login_table";
    public static final String table3 = "details_table";
    public static final String table4 = "questions_table";


    public Forum_database(Context context) {
        super(context, database_name, null, 6);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + table1 + "(question text,answer text,user_name text)");
        db.execSQL("create table " + table2 + "(status text,user text)");
        db.execSQL("create table " + table3 + "(user_name text primary key,password text,name text,mobile text)");
        db.execSQL("create table " + table4 + "(question text primary key,user text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + table1);
        db.execSQL("drop table if exists " + table2);
        db.execSQL("drop table if exists " + table3);
        onCreate(db);

    }

    public boolean get_status()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag = false;
        Cursor rs = db.rawQuery("select * from " + table2, null);
        while(rs.moveToNext())
        {
            if(rs.getString(0).equals("yes"))
                return true;
        }

        return flag;
    }

    public boolean update_status(String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String name = get_username();
        ContentValues cv = new ContentValues();
        cv.put("status", "yes");
        cv.put("user", user);
        long result = db.insert(table2, null, cv);
        if (result == -1)
            return false;
        else
            return true;
    }


    public String get_username() {
        SQLiteDatabase db = this.getWritableDatabase();
        String name = "";
        Cursor rs = db.rawQuery("select * from " + table2, null);
        while (rs.moveToNext()) {
            if (rs.getString(0).equals("yes"))
                name = rs.getString(1);
        }
        return name;
    }

    public boolean check_credentials(String un,String pwd)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag = false;
        Cursor rs = db.rawQuery("select * from " + table3, null);
        while (rs.moveToNext()) {
            if (rs.getString(0).equals(un) && rs.getString(1).equals(pwd)) {
                return true;
            }

        }

        return flag;
    }

    public boolean delete_status(String user)
    {
        boolean flag = false;
        SQLiteDatabase db = this.getWritableDatabase();
        int n = db.delete(table2, "user = ?", new String[]{user});
        if (n>0)
            return true;
        return flag;
    }


    public boolean check_validity(String un)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean flag = true;
        Cursor rs = db.rawQuery("select * from " + table3, null);
        while (rs.moveToNext()) {
            if (rs.getString(0).equals(un)) {
                flag = false;
                return flag;
            }

        }

        return flag;
    }

    public boolean insert_credentials(String un,String pwd,String name,String mobile)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("user_name", un);
        cv.put("password", pwd);
        cv.put("name", name);
        cv.put("mobile", mobile);
        long result = db.insert(table3, null, cv);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean post_question(String question)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String name = get_username();
        ContentValues cv = new ContentValues();
        cv.put("question", question);
        cv.put("user", name);
        long result = db.insert(table4, null, cv);
        if (result == -1)
            return false;
        else
            return true;
    }

    public String get_answer(String question)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String name = get_username();
        String answer = "";
        Cursor rs = db.rawQuery("select * from " + table1, null);
        while(rs.moveToNext())
        {
            if(rs.getString(0).equals(question) && !rs.getString(1).equals(""))
            {
                answer = answer + rs.getString(2) + ": " +  rs.getString(1)+"\n";
            }
        }
        return answer;
    }

    public boolean answer(String qestion,String answer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String name = get_username();
        ContentValues cv = new ContentValues();
        cv.put("question", qestion);
        cv.put("answer",answer);
        cv.put("user_name", name);
        long result = db.insert(table1, null, cv);
        if (result == -1)
            return false;
        else
            return true;

    }


    public String get_titles()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String titles = "";
        Cursor rs = db.rawQuery("select * from " + table4, null);
        while(rs.moveToNext())
        {

                titles = titles + rs.getString(1)+":"+rs.getString(0)+"/";
        }

        titles = titles + "*";
        return titles;
    }



}
