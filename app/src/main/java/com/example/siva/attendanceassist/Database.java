package com.example.siva.attendanceassist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigDecimal;

/**
 * Created by Siva on 10-Feb-16.
 */
public class Database extends SQLiteOpenHelper {
    public static final String database_name = "attendenceassit2.db";
    public static final String table1 = "subjects_table";
    public static final String table2 = "user_table";
    String a="",b="",c="";

    public Database(Context context) {
        super(context, database_name, null, 6);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + table1 + "(subject_name text primary key,classes_attended integer,classes_happened integer)");
        db.execSQL("create table " + table2 + "(subject_name text primary key,date text,month text,year text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + table1);
        db.execSQL("drop table if exists " + table2);
        onCreate(db);

    }

    public int r_date(String sname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int d = 0;
        Cursor rd = db.rawQuery("select * from " + table2, null);
        while(rd.moveToNext())
        {
            if(rd.getString(0).equals(sname))
            {
                d = Integer.parseInt(rd.getString(1));
                return d;
            }
        }
        rd.close();
        return d;
    }

    public int r_month(String sname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int d = 0;
        Cursor rd = db.rawQuery("select * from " + table2, null);
        while(rd.moveToNext())
        {
            if(rd.getString(0).equals(sname))
            {
                d = Integer.parseInt(rd.getString(2));
                return d;
            }
        }
        rd.close();
        return d;

    }

    public int r_year(String sname)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int d = 0;
        Cursor rd = db.rawQuery("select * from " + table2, null);
        while(rd.moveToNext())
        {
            if(rd.getString(0).equals(sname))
            {
                d = Integer.parseInt(rd.getString(3));
                return d;
            }
        }
        rd.close();
        return d;

    }

    public boolean insert_user(String s1,String s2,String s3,String s4)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("subject_name", s1);
        cv.put("date", s2);
        cv.put("month", s3);
        cv.put("year", s4);
        long result = db.insert(table2, null, cv);
        if (result == -1)
            return false;
        else
            return true;
    }


    public boolean insert(String arg1, String arg2, String arg3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("subject_name", arg1);
        cv.put("classes_attended", arg2);
        cv.put("classes_happened", arg3);
        long result = db.insert(table1, null, cv);
        if (result == -1)
            return false;
        else
            return true;
    }

    public String send_data() {
        SQLiteDatabase db = this.getWritableDatabase();
        int d = 0;
        Cursor rd = db.rawQuery("select * from " + table1, null);
        while(rd.moveToNext())
        {
            d++;
        }
        rd.close();
        String k=String.valueOf(d);
        Cursor rs = db.rawQuery("select * from " + table1, null);
        while (rs.moveToNext()) {
            k=k+rs.getString(0).toString()+"+";
            k=k+rs.getString(1).toString()+"-";
            k=k+rs.getString(2).toString()+"*";
            double n = Integer.parseInt(rs.getString(2))-Integer.parseInt(rs.getString(1));
            double m = Integer.parseInt(rs.getString(2));
            double per = n/m;
            per = per*100;
            k=k+per+"/";
        }
        rs.close();
        return k;
    }

    public String return_percentage(String s_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        double per=0.0,n=0.0,m=0.0;
        String s = "",num="";
        Cursor rs = db.rawQuery("select * from " + table1, null);
        while (rs.moveToNext())
        {
            if(rs.getString(0).equals(s_name))
            {
                n = Integer.parseInt(rs.getString(2))-Integer.parseInt(rs.getString(1));
                m = Integer.parseInt(rs.getString(2));
                per = n/m;
                per = per*100;
            }
        }
        rs.close();
        return String.valueOf(per);
    }

    public String get_name(int pos)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int i=0;
        String s="";
        Cursor rs = db.rawQuery("select * from " + table1, null);
        while(rs.moveToNext())
        {
            if(i == pos)
            {
                s=rs.getString(0);
            }
            i++;
        }
        rs.close();
        return s;
    }

    public void set_strings(String sub)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        a = "";b="";c="";
        Cursor rs = db.rawQuery("select * from " + table1, null);
        while(rs.moveToNext())
        {
            if(rs.getString(0).equals(sub))
            {
                a = rs.getString(0);
                b = rs.getString(1);
                c = rs.getString(2);
            }
        }
        rs.close();
    }



    public boolean delete_item(int position)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String arg2 = get_name(position);
        int n = db.delete(table1, "subject_name = ?", new String[]{arg2});
        int m = db.delete(table2, "subject_name = ?", new String[]{arg2});
        if(n <= 0 || m <= 0)
            return false;
        else
            return true;
    }

    public boolean update(String sub_name,int n)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        set_strings(sub_name);
        if(n == 0)
        {
            ContentValues cv = new ContentValues();
            int l = Integer.parseInt(c);
            l++;
            cv.put("subject_name",a);
            cv.put("classes_attended", b);
            cv.put("classes_happened",String.valueOf(l));
            long result = db.update(table1,cv,"subject_name = ?",new String[] {a});
            if(result==-1)
                return false;
            else
                return true;
        }
        if(n == 1)
        {
            ContentValues cv = new ContentValues();
            int k = Integer.parseInt(b);
            int l = Integer.parseInt(c);
            l++;
            k++;
            cv.put("subject_name",a);
            cv.put("classes_attended", String.valueOf(k));
            cv.put("classes_happened",String.valueOf(l));
            long result = db.update(table1,cv,"subject_name = ?",new String[] {a});
            if(result==-1)
                return false;
            else
                return true;
        }
        if(n == 2)
        {
            ContentValues cv = new ContentValues();
            int l = Integer.parseInt(c);
            l = l + 2;
            cv.put("subject_name",a);
            cv.put("classes_attended", b);
            cv.put("classes_happened",String.valueOf(l));
            long result = db.update(table1,cv,"subject_name = ?",new String[] {a});
            if(result==-1)
                return false;
            else
                return true;
        }
        if(n == 3)
        {
            ContentValues cv = new ContentValues();
            int k = Integer.parseInt(b);
            int l = Integer.parseInt(c);
            l = l + 2;
            k = k + 2;
            cv.put("subject_name",a);
            cv.put("classes_attended", String.valueOf(k));
            cv.put("classes_happened",String.valueOf(l));
            long result = db.update(table1,cv,"subject_name = ?",new String[] {a});
            if(result==-1)
                return false;
            else
                return true;
        }

        return false;

    }

}
