package com.example.siva.attendanceassist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Daily_updates extends AppCompatActivity implements View.OnClickListener{

    public final static String message_key = "com.example.siva.attendanceassist.message_key";
    private static final String tag = "MyCalendarActivity";
    GridCellAdapter GCA;
    Calendar cal=new GregorianCalendar();
    int today,cur_month,cur_year;
    final Context context = this;
    Database db_subs;
    public String sub_name="";
    public int radio_selected;
    public int DD,MM,YYY;
    SubjectStatus_Database db;
    private TextView currentMonth;
    int[] a={40};
    int[] b={40};
    int index = 0, index2 = 0;
    int pos = 0,pos2 = 0;
    private TextView selectedDayMonthYearButton;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private GridCellAdapter adapter;
    private Calendar _calendar;
    @SuppressLint("NewApi")
    private int month, year;
    @SuppressWarnings("unused")
    @SuppressLint({"NewApi", "NewApi", "NewApi", "NewApi"})
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_updates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        db = new SubjectStatus_Database(this);
        Calendar cal=new GregorianCalendar();
        int months=cal.get(Calendar.MONTH);
        months++;
        int years=cal.get(Calendar.YEAR);
        MM=months;
        YYY=years;
        db_subs = new Database(this);
        Intent intent = getIntent();
        sub_name = intent.getStringExtra(message_key);
        Toast.makeText(Daily_updates.this,sub_name,Toast.LENGTH_LONG).show();
        _calendar = Calendar.getInstance(Locale.getDefault());
        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
                + year);

        selectedDayMonthYearButton = (TextView) this
                .findViewById(R.id.selectedDayMonthYear);
        selectedDayMonthYearButton.setText("Selected: ");

        prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);

        currentMonth = (TextView) this.findViewById(R.id.currentMonth);
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));

        nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);

        calendarView = (GridView) this.findViewById(R.id.calendar);

// Initialised
        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

    }

    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(getApplicationContext(), R.id.calendar_day_gridcell, month, year);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate,
                _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == prevMonth) {
            if (month <= 1) {
                month = 12;
                year --;
            } else {
                month --;
            }
            Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: "
                    + month + " Year: " + year);
            setGridCellAdapterToDate(month, year);
        }
        if (v == nextMonth) {
            if (month > 11) {
                month



                        = 1;
                year++;
            } else {
                month++;
            }
            Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "
                    + month + " Year: " + year);
            setGridCellAdapterToDate(month, year);
        }

    }

    @Override
    public void onDestroy() {
        Log.d(tag, "Destroying View …");
        super.onDestroy();
    }

    // Inner Class
    public class GridCellAdapter extends BaseAdapter implements View.OnClickListener {
        private static final String tag = "GridCellAdapter";
        private final Context _context;

        private final List<String> list;
        private static final int DAY_OFFSET = 1;
        private final String[] weekdays = new String[]{"Sun", "Mon", "Tue",
                "Wed", "Thu", "Fri", "Sat"};
        private final String[] months = {"January", "February", "March",
                "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
        private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30,
                31, 30, 31};
        private int daysInMonth;
        private int currentDayOfMonth;
        private int currentWeekDay;
        private Button gridcell;
        private TextView num_events_per_day;
        private final HashMap<String, Integer> eventsPerMonthMap;
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
                "dd-MMM-yyyy");

        // Days in Current Month
        public GridCellAdapter(Context context, int textViewResourceId,
                               int month, int year) {
            super();
            this._context = context;
            this.list = new ArrayList<String>();
            Log.d(tag, "==> Passed in Date FOR Month: " + month + " "
                    + "Year: " + year);
            Calendar calendar = Calendar.getInstance();
            setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
            setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
            Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
            Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
            Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

// Print Month
            printMonth(month, year);

// Find Number of Events
            eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
        }

        private String getMonthAsString(int i) {
            return months[i];
        }

        private String getWeekDayAsString(int i) {
            return weekdays[i];
        }

        private int getNumberOfDaysOfMonth(int i) {
            return daysOfMonth[i];
        }

        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * Prints Month
         *
         * @param mm
         * @param yy
         */
        private void printMonth(int mm, int yy) {
            Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
            int trailingSpaces = 0;
            int daysInPrevMonth = 0;
            int prevMonth = 0;
            int prevYear = 0;
            int nextMonth = 0;
            int nextYear = 0;

            int currentMonth = mm - 1;
            String currentMonthName = getMonthAsString(currentMonth);
            daysInMonth = getNumberOfDaysOfMonth(currentMonth);

            Log.d(tag, "Current Month: " + " " + currentMonthName + " having "
                    + daysInMonth + " days.");

            GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
            Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

            if (currentMonth == 11) {
                prevMonth = currentMonth - 1;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 0;
                prevYear = yy;
                nextYear = yy + 1;
                Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"
                        + prevMonth + " NextMonth: " + nextMonth
                        + " NextYear: " + nextYear);
            } else if (currentMonth == 0) {
                prevMonth = 11;
                prevYear = yy - 1;
                nextYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 1;
                Log.d(tag, "**-> PrevYear: " + prevYear + " PrevMonth:"
                        + prevMonth + " NextMonth: " + nextMonth
                        + " NextYear: " + nextYear);
            } else {
                prevMonth = currentMonth - 1;
                nextMonth = currentMonth + 1;
                nextYear = yy;
                prevYear = yy;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                Log.d(tag, "***—> PrevYear: " + prevYear + " PrevMonth:"
                        + prevMonth + " NextMonth: " + nextMonth
                        + " NextYear: " + nextYear);
            }

            int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
            trailingSpaces = currentWeekDay;

            Log.d(tag, "Week Day:" + currentWeekDay + " is "
                    + getWeekDayAsString(currentWeekDay));
            Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
            Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

            if (cal.isLeapYear(cal.get(Calendar.YEAR)))
                if (mm == 2)
                    ++daysInMonth;
                else if (mm == 3)
                    ++daysInPrevMonth;

// Trailing Month days
            for (int i = 0; i < trailingSpaces; i++) {
                Log.d(tag,
                        "PREV MONTH:= "
                                + prevMonth
                                + " => "
                                + getMonthAsString(prevMonth)
                                + " "
                                + String.valueOf((daysInPrevMonth
                                - trailingSpaces + DAY_OFFSET)
                                + i));
                list.add(String
                        .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                                + i)
                        + "-GREY"
                        + "-"
                        + getMonthAsString(prevMonth)
                        + "-"
                        + prevYear);
            }


            /*String temp  = db.return_present(sub_name,0,String.valueOf(MM),String.valueOf(YYY));
            String temp2 = db.return_present(sub_name,1,String.valueOf(MM),String.valueOf(YYY));
            temp = temp + db.return_present(sub_name,2,String.valueOf(MM),String.valueOf(YYY));
            temp2 = temp2 + db.return_present(sub_name,3,String.valueOf(MM),String.valueOf(YYY));*/
            String temp  = db.return_present(sub_name,0,String.valueOf(currentMonth+1),String.valueOf(2016));
            String temp2 = db.return_present(sub_name,1,String.valueOf(currentMonth+1),String.valueOf(2016));
            temp = temp + db.return_present(sub_name,2,String.valueOf(currentMonth+1),String.valueOf(2016));
            temp2 = temp2 + db.return_present(sub_name,3,String.valueOf(currentMonth+1),String.valueOf(2016));
            //Toast.makeText(Daily_updates.this,String.valueOf(MM)+" "+String.valueOf(YYY),Toast.LENGTH_SHORT).show();
            //Toast.makeText(Daily_updates.this,temp2,Toast.LENGTH_SHORT).show();
            set_dateArray(temp);
            set_dateArray2(temp2);
            Arrays.sort(a);//present dates
            Arrays.sort(b);//absent dates
            //Toast.makeText(Daily_updates.this,Arrays.toString(a),Toast.LENGTH_SHORT).show();
            //Toast.makeText(Daily_updates.this,Arrays.toString(b),Toast.LENGTH_SHORT).show();
            boolean flag=true,flag2=true;


            for (int i = 1; i <= daysInMonth; i++) {
                if(flag == true)
                {
                    if(i==1)
                    {
                        index = 0;
                        index2 = 0;
                    }
                    pos=a[index];
                    pos2=b[index2];
                    Log.d(currentMonthName, String.valueOf(i) + " "
                            + getMonthAsString(currentMonth) + " " + yy);
                    if (i == getCurrentDayOfMonth()) {
                        list.add(String.valueOf(i) + "-BLUE" + "-"
                                + getMonthAsString(currentMonth) + "-" + yy);
                    }
                    else
                    {
                        if(i == pos)
                        {
                            index = index + 1;
                            flag2 = false;
                            if(a[index] == a[index-1])
                            {
                                index++;
                            }
                            list.add(String.valueOf(i) + "-NAVY" + "-"
                                    + getMonthAsString(currentMonth) + "-" + yy);
                        }
                        if(i == pos2 && flag2)
                        {
                            index2 = index2 + 1;
                            flag2 = false;
                            if(b[index2] == b[index2-1])
                            {
                                index2++;
                            }
                            list.add(String.valueOf(i) + "-RED" + "-"
                                    + getMonthAsString(currentMonth) + "-" + yy);
                        }
                        if(flag2)
                        {
                            list.add(String.valueOf(i) + "-WHITE" + "-"
                                    + getMonthAsString(currentMonth) + "-" + yy);
                        }
                    }
                }
                else
                {
                    Log.d(currentMonthName, String.valueOf(i) + " "
                            + getMonthAsString(currentMonth) + " " + yy);
                    if (i == getCurrentDayOfMonth()) {
                        list.add(String.valueOf(i) + "-BLUE" + "-"
                                + getMonthAsString(currentMonth) + "-" + yy);
                    }
                    else
                    {
                        list.add(String.valueOf(i) + "-WHITE" + "-"
                                + getMonthAsString(currentMonth) + "-" + yy);
                    }
                }
                flag2  = true;
            }


// Leading Month days
            for (int i = 0; i < list.size() % 7; i++) {
                Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
                list.add(String.valueOf(i + 1) + "-GREY" + "-"
                        + getMonthAsString(nextMonth) + "-" + nextYear);
            }
        }

        public void set_dateArray(String subs)
        {
            int len = subs.length();
            int d=0;
            for(int m=0;m<subs.length();m++)
            {
                if(subs.charAt(m) == '-')
                    d++;
            }
            int [] x = new int[d+1];
            int p=0;
            String s="";
            for(int i=0;i<len;i++) {
                if (subs.charAt(i) != '-') {
                    s = s + subs.charAt(i);
                }
                if (subs.charAt(i) == '-') {
                    x[p] = Integer.parseInt(s);
                    p++;
                    s = "";

                }

                if(i == len-1)
                    x[p]=80;
            }
            a = x;
        }

        public void set_dateArray2(String subs)
        {
            int len = subs.length();
            int d=0;
            for(int m=0;m<subs.length();m++)
            {
                if(subs.charAt(m) == '-')
                    d++;
            }
            int [] x = new int[d+1];
            int p=0;
            String s="";
            for(int i=0;i<len;i++) {
                if (subs.charAt(i) != '-') {
                    s = s + subs.charAt(i);
                }
                if (subs.charAt(i) == '-') {
                    x[p] = Integer.parseInt(s);
                    p++;
                    s = "";

                }

                if(i == len-1)
                    x[p]=80;
            }
            b = x;
        }



        /**
         * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
         * ALL entries from a SQLite database for that month. Iterate over the
         * List of All entries, and get the dateCreated, which is converted into
         * day.
         *
         * @param year
         * @param month
         * @return
         */
        private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
                                                                    int month) {
            HashMap<String, Integer> map = new HashMap<String, Integer>();

            return map;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.screen_gridcell, parent, false);
            }

// Get a reference to the Day gridcell
            gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
            gridcell.setOnClickListener(this);

// ACCOUNT FOR SPACING

            Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
            String[] day_color = list.get(position).split("-");
            String theday = day_color[0];
            String themonth = day_color[2];
            String theyear = day_color[3];
            if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
                if (eventsPerMonthMap.containsKey(theday)) {
                    num_events_per_day = (TextView) row
                            .findViewById(R.id.num_events_per_day);
                    Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                    num_events_per_day.setText(numEvents.toString());
                }
            }

// Set the Day GridCell
            gridcell.setText(theday);
            gridcell.setTag(theday + "-" + themonth + "-" + theyear);
            Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
                    + theyear);

            if (day_color[1].equals("GREY")) {
                gridcell.setTextColor(getResources()
                        .getColor(R.color.lightgray));
            }
            if (day_color[1].equals("WHITE")) {
                gridcell.setTextColor(getResources().getColor(
                        R.color.colorAccent));
            }
            if (day_color[1].equals("BLUE")) {
                gridcell.setTextColor(getResources().getColor(R.color.blue));
            }
            if (day_color[1].equals("NAVY")) {
                gridcell.setTextColor(getResources().getColor(R.color.green));
                index=0;
            }
            if (day_color[1].equals("RED")) {
                gridcell.setTextColor(getResources().getColor(R.color.orrange));
            }
            return row;
        }

        @Override
        public void onClick(View view) {
            String date_month_year = (String) view.getTag();
            selectedDayMonthYearButton.setText("Selected: " + date_month_year);

            String k = date_month_year;
            set_dates(k);
            mark_in_calendar();

            Log.e("Selected date", date_month_year);
            try {
                Date parsedDate = dateFormatter.parse(date_month_year);
                Log.d(tag, "Parsed Date: " + parsedDate.toString());

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        public void set_dates(String str)
        {
            int len = str.length();
            boolean f1=true,f2=true,f3=true;
            String s="";
            int j=0;
            for(int i=0;i<len;i++)
            {
                if(f1 && str.charAt(i) != '-')
                {
                    s = s + str.charAt(i);
                }
                if(f1 && f2 && f3 && str.charAt(i) == '-')
                {
                    f1 = false;
                    DD = Integer.parseInt(s);

                    j=i;
                    s="";
                }
                if(!f1 && f2 && f3 && str.charAt(i) != '-')
                {
                    s = s + str.charAt(i);

                }
                if(!f1 && f2 && f3 && str.charAt(i) == '-' && j!=i)
                {
                    f2 = false;
                    calculate(s);
                    //MM = 3;
                    j=i;
                    s = "";
                }
                if(!f1 && !f2 && f3 && str.charAt(i) != '-')
                {
                    s = s + str.charAt(i);

                }
                if((!f1 && !f2 && f3 && str.charAt(i) == '-' && j!=i) || i==len-1)
                {
                    f3 = false;
                    YYY  = Integer.parseInt(s);

                    s = "";
                }
            }
        }

        public void calculate(String s)
        {
            switch(s)
            {
                case "January":
                    MM  = 1;
                    break;
                case "February":
                    MM  = 2;
                    break;
                case "March":
                    MM  = 3;
                    break;
                case "April":
                    MM  = 4;
                    break;
                case "May":
                    MM  = 5;
                    break;
                case "June":
                    MM  = 6;
                    break;
                case "July":
                    MM  = 7;
                    break;
                case "August":
                    MM  = 8;
                    break;
                case "September":
                    MM  = 9;
                    break;
                case "October":
                    MM  = 10;
                    break;
                case "November":
                    MM  = 11;;
                    break;
                case "December":
                    MM  = 12;
                    break;
            }
        }

        public int getCurrentDayOfMonth() {
            return currentDayOfMonth;
        }

        private void setCurrentDayOfMonth(int currentDayOfMonth) {
            this.currentDayOfMonth = currentDayOfMonth;
        }

        public void setCurrentWeekDay(int currentWeekDay) {
            this.currentWeekDay = currentWeekDay;
        }

        public int getCurrentWeekDay() {
            return currentWeekDay;
        }

    }

    public void mark_in_calendar()
    {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompt2, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        // set dialog message
        alertDialogBuilder
                .setTitle("CHOOSE AN OPTION BELOW")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                update_cal_color();
                               // Toast.makeText(Daily_updates.this,String.valueOf(MM)+" "+String.valueOf(YYY),Toast.LENGTH_LONG).show();
                                setGridCellAdapterToDate(MM, YYY);
                                notify_updated_subject(sub_name);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public void notify_updated_subject(String s_name)
    {
        String per = db_subs.return_percentage(sub_name);
        /*String num="";
                for(int b=0;b<5;b++)
                {
                    num = num + per.charAt(b);
                }*/
        Intent intent = new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(Daily_updates.this, 0, intent, 0);
        Notification noti = new Notification.Builder(Daily_updates.this)
                .setTicker("Ticker")
                .setContentTitle("SUCCESFUL UPDATE!")
                .setContentText("Current "+sub_name+" attendance % = "+per)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(pIntent).getNotification();
        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, noti);
    }

    public void update_cal_color()
    {
        Calendar cal=new GregorianCalendar();
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int month=cal.get(Calendar.MONTH);
        month++;
        int year=cal.get(Calendar.YEAR);
        switch(radio_selected)
        {
            case 0:
                boolean f=false;
                if((YYY >= db_subs.r_year(sub_name) && YYY <= year) && (( MM < month) || (MM == month && DD >= db_subs.r_date(sub_name) && DD <= day)))
                {
                    if(checking(DD))
                        f = db.insert_data(0, sub_name, String.valueOf(DD), String.valueOf(MM), String.valueOf(YYY));
                    else
                        Toast.makeText(Daily_updates.this,"SELECTED HAS ALREADY BEEN UPDATED!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Daily_updates.this,"INVALID DATE SELECTION",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Daily_updates.this,String.valueOf(f),Toast.LENGTH_SHORT).show();
                if(f == true)
                {
                    db_subs.update(sub_name,0);
                }
                break;
            case 1:
                boolean g=false;
                if(((YYY >= db_subs.r_year(sub_name) && YYY <= year) && (MM >= db_subs.r_month(sub_name) && MM <= month) && (DD >= db_subs.r_date(sub_name) && DD <= day)))
                {
                    if(checking(DD))
                        g = db.insert_data(1, sub_name, String.valueOf(DD), String.valueOf(MM), String.valueOf(YYY));
                    else
                        Toast.makeText(Daily_updates.this,"SELECTED HAS ALREADY BEEN UPDATED!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Daily_updates.this,"INVALID DATE SELECTION",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Daily_updates.this,String.valueOf(g),Toast.LENGTH_SHORT).show();
                if(g == true)
                {
                    db_subs.update(sub_name,1);
                }
                break;
            case 2:
                boolean h=false;
                if(((YYY >= db_subs.r_year(sub_name) && YYY <= year) && (MM >= db_subs.r_month(sub_name) && MM <= month) && (DD >= db_subs.r_date(sub_name) && DD <= day)))
                {
                    if(checking(DD))
                        h = db.insert_data(2, sub_name, String.valueOf(DD), String.valueOf(MM), String.valueOf(YYY));
                    else
                        Toast.makeText(Daily_updates.this,"SELECTED HAS ALREADY BEEN UPDATED!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Daily_updates.this,"INVALID DATE SELECTION",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Daily_updates.this,String.valueOf(h),Toast.LENGTH_SHORT).show();
                if(h == true)
                {
                    db_subs.update(sub_name,2);
                }
                break;
            case 3:
                boolean i=false;
                if(((YYY >= db_subs.r_year(sub_name) && YYY <= year) && (MM >= db_subs.r_month(sub_name) && MM <= month) && (DD >= db_subs.r_date(sub_name) && DD <= day)))
                {
                    if(checking(DD))
                        i = db.insert_data(3, sub_name, String.valueOf(DD), String.valueOf(MM), String.valueOf(YYY));
                    else
                        Toast.makeText(Daily_updates.this,"SELECTED HAS ALREADY BEEN UPDATED!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Daily_updates.this,"INVALID DATE SELECTION",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Daily_updates.this,String.valueOf(i),Toast.LENGTH_SHORT).show();
                if(i == true)
                {
                    db_subs.update(sub_name,3);
                }
                break;
        }
    }

    public boolean checking(int date)
    {
        boolean flag = true;
        for(int i=0;i<a.length;i++)
        {
           if(date == a[i])
               flag = false;
        }
        for(int i=0;i<b.length;i++)
        {
            if(date == b[i])
                flag = false;
        }
        return flag;
    }


    public void slected_radio_button(View v)
    {
        boolean checked = ((RadioButton)v).isChecked();
        switch(v.getId())
        {
            case R.id.present:
                radio_selected = 0;
                break;
            case R.id.absent:
                radio_selected = 1;
                break;
            case R.id.cancelled:
                radio_selected = 2;
                break;
            case R.id.twoclasses:
                radio_selected = 3;
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
