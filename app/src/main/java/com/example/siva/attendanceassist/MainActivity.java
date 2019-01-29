package com.example.siva.attendanceassist;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,Fragment1.toset,Fragment1.Activity_change,Login.loginflags,Login.fragchange,Signup.signupflags{
    final Context context = this;
    SubjectStatus_Database db2;
    public final static String message_key = "com.example.siva.attendanceassist.message_key";
    private Button button;
    private EditText result;
    Database db;
    ListView listView,listView2;
    String[] subjects;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
    int b;
    int [] a = new int[3];
    int [] poster = {R.drawable.home,R.drawable.attendance,R.drawable.books,R.drawable.icon_forum,R.drawable.question,R.drawable.touch};
    String [] titles = {"DASHBOARD","ATTENDANCE CHECK","STUDY MATERIALS","STUDENTS FORUM","NEED HELP","ABOUT US"};
    NavlistAdapter adapter;
    LinearLayout linearLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    private ListView navlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        fab.setVisibility(View.GONE);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        db = new Database(this);
        db2 = new SubjectStatus_Database(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.opendrawer,R.string.closedrawer);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        listView = (ListView) findViewById(R.id.list_view);
        int i=0;
        adapter = new NavlistAdapter(getApplicationContext(),R.layout.row_layout);
        listView.setAdapter(adapter);
        for(String title : titles)
        {
            DataProvider dataProvider = new DataProvider(poster[i],title);
            adapter.add(dataProvider);
            i++;
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        button = (Button) findViewById(R.id.action_settings);

        FragmentManager fn = getFragmentManager();
        fn.beginTransaction().replace(R.id.fragmentholder,new Fragment2()).commit();
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
    }


    public void checked(int a)
    {
        if(a == 0)
        {
            listView.setItemChecked(a,true);
            FragmentManager fn = getFragmentManager();
            fn.beginTransaction().replace(R.id.fragmentholder,new Fragment2()).commit();
        }
        if(a == 1)
        {
            listView.setItemChecked(a,true);
            FragmentManager fn = getFragmentManager();
            fn.beginTransaction().replace(R.id.fragmentholder,new Fragment1()).commit();
        }
        if(a == 2)
        {
            listView.setItemChecked(a,true);
            FragmentManager fn = getFragmentManager();
            fn.beginTransaction().replace(R.id.fragmentholder,new Fragment3()).commit();
        }
        if(a == 3)
        {

            /*FragmentManager fn = getFragmentManager();
            fn.beginTransaction().replace(R.id.fragmentholder,new Fragment4()).commit();*/
            Intent intent2 = new Intent("com.example.siva.attendanceassist.Forum");
            startActivity(intent2);

            Intent intent = new Intent("com.example.siva.attendanceassist.Splashscreen");
            startActivity(intent);
        }
    }

    public void userinput()
    {
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        final EditText userInput2 = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput2);
        final EditText userInput3 = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput3);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                Calendar cal=new GregorianCalendar();
                                int day=cal.get(Calendar.DAY_OF_MONTH);
                                int month=cal.get(Calendar.MONTH)+1;
                                int year=cal.get(Calendar.YEAR);
                                String a="",b="",c="",d="",m="",y="";
                                a = userInput.getText().toString();
                                b = userInput2.getText().toString();
                                c = userInput3.getText().toString();
                                d = String.valueOf(day);
                                m = String.valueOf(month);
                                y = String.valueOf(year);
                                boolean k=false,l=false;
                                if(!a.equals("")&&!b.equals("")&&!c.equals("")) {
                                    k = db.insert(a, b, c);
                                    l = db.insert_user(a,d,m,y);
                                }
                                if(k==true && l == true)
                                {
                                    FragmentManager fn = getFragmentManager();
                                    fn.beginTransaction().replace(R.id.fragmentholder,new Fragment1()).commit();
                                }
                                else
                                {
                                    Toast.makeText(MainActivity.this,"Ivalid Input!",Toast.LENGTH_SHORT).show();
                                }
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        actionBarDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            userinput();
            return true;
        }
        else if(id == android.R.id.home)
        {
            if(drawerLayout.isDrawerOpen(linearLayout))
            {
                drawerLayout.closeDrawer(linearLayout);
            }
            else
            {
                drawerLayout.openDrawer(linearLayout);
            }

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setSelected(true);
        switch(position)
        {
            case 0:
                checked(0);
                break;
            case 1:
                checked(1);
                break;
            case 2:
                checked(2);
                break;
            case 3:
                checked(3);
                break;
        }
        drawerLayout.closeDrawer(linearLayout);

    }

    @Override
    public void set(String name) {



        if(name.equals("siva"))
        {
                String ps = db.send_data();
                Fragment1 fragment1 = (Fragment1) getFragmentManager().findFragmentById(R.id.fragmentholder);
                fragment1.array_update(ps);
        }
        else
        {
            int ss = Integer.parseInt(name);
            if (ss < 100) {
                boolean flag = db.delete_item(ss);
                if(flag == true)
                {
                    String subject_name = db.get_name(ss);
                    boolean f = db2.delete_dates(subject_name);
                    //Toast.makeText(MainActivity.this,String.valueOf(f),Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void act_change(int position) {
        String p = db.get_name(position);
        Intent intent = new Intent("com.example.siva.attendanceassist.Daily_updates");
        intent.putExtra(message_key,p);
        startActivity(intent);
    }

    @Override
    public void visibility(String f1,String f2) {


    }

    @Override
    public void hide(String f1) {

    }
}


