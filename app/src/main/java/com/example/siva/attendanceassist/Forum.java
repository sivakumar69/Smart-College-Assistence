package com.example.siva.attendanceassist;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Forum extends AppCompatActivity implements Login.loginflags,Login.fragchange,Signup.signupflags{

    Animate fx;
    TextView tv1,tv2,tv3,tv4,t1,t2,t3,t4,nex,prev;
    EditText et1,et2;
    String ttl1,ttl2,ttl3,ttl4,question="";
    Forum_database fd;
    boolean visibility = false,settitles = true;
    Button b1,b2,b3;
    int next=0;

    FrameLayout ll1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
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

        fd = new Forum_database(this);
        fx = new Animate();
        ll1 = (FrameLayout)findViewById(R.id.fragmentholder2);
        b1 = (Button)findViewById(R.id.post_btn);
        b2 = (Button)findViewById(R.id.ans_btn);
        b3 = (Button)findViewById(R.id.sign_btn);
        t1 = (TextView)findViewById(R.id.title1);
        t2 = (TextView)findViewById(R.id.title2);
        t3 = (TextView)findViewById(R.id.title3);
        t4 = (TextView)findViewById(R.id.title4);
        tv1 = (TextView)findViewById(R.id.sub_title1);
        tv1.setVisibility(View.GONE);
        tv2 = (TextView)findViewById(R.id.sub_title2);
        tv2.setVisibility(View.GONE);
        tv3 = (TextView)findViewById(R.id.sub_title3);
        tv3.setVisibility(View.GONE);
        tv4 = (TextView)findViewById(R.id.sub_title4);
        tv4.setVisibility(View.GONE);
        nex = (TextView)findViewById(R.id.next);
        prev = (TextView)findViewById(R.id.previous);
        set_buttons();
        answer();
        setTiles();
    }

    public void set_buttons()
    {
        if(fd.get_status())
        {
            b3.setText("Sign Out");
            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
        }
        else
        {
            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
        }
    }

    public void set(View v)
    {
        if(b3.getText().equals("Sign Out"))
        {
            b3.setText("Sign In");
            b1.setVisibility(View.GONE);
            b2.setVisibility(View.GONE);
            fd.delete_status(fd.get_username());
        }
        else
        {
            ll1.setVisibility(View.VISIBLE);
            FragmentManager fn = getFragmentManager();
            fn.beginTransaction().replace(R.id.fragmentholder2,new Login()).commit();
        }
    }



    public void answer()
    {
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog(1, question);

            }
        });
    }


    public void setTiles()
    {
        if(next == 0)
        {
            prev.setVisibility(View.GONE);
        }
        else
            prev.setVisibility(View.VISIBLE);
        String titles = fd.get_titles();
        int count=0;
        boolean flag = false;
        String parse="";
        for(int i=0;i<titles.length();i++)
        {
            if(titles.charAt(i) == '/')
            {
                if(count == next)
                {
                    ttl1 = parse;
                    parse="";
                    if(titles.charAt(i+1) == '*')
                    {
                        nex.setVisibility(View.GONE);
                        visibility = true;
                        t2.setVisibility(View.GONE);
                        t3.setVisibility(View.GONE);
                        t4.setVisibility(View.GONE);
                    }
                }
                if(count == next+1)
                {
                    ttl2 = parse;
                    parse="";
                    if(titles.charAt(i+1) == '*')
                    {
                        nex.setVisibility(View.GONE);
                        visibility = true;
                        t3.setVisibility(View.GONE);
                        t4.setVisibility(View.GONE);
                    }
                }
                if(count == next+2)
                {
                    ttl3 = parse;
                    parse="";
                    if(titles.charAt(i+1) == '*')
                    {
                        nex.setVisibility(View.GONE);
                        visibility = true;
                        t4.setVisibility(View.GONE);
                    }
                }
                if(count == next+3)
                {
                    ttl4 = parse;
                    parse="";
                    if(titles.charAt(i+1) == '*')
                    {
                        nex.setVisibility(View.GONE);
                        visibility = true;
                    }
                }
                count++;
                parse = "";
            }
            else
            {
                parse = parse + titles.charAt(i);
            }

            if(titles.charAt(i) == '*')
            {
                parse = "";
                t1.setText(ttl1);
                t2.setText(ttl2);
                t3.setText(ttl3);
                t4.setText(ttl4);
                ttl1  = "";
                ttl2  = "";
                ttl3  = "";
                ttl4  = "";
            }
        }
        if(titles.equals("*"))
        {
            t1.setVisibility(View.GONE);
            t2.setVisibility(View.GONE);
            t3.setVisibility(View.GONE);
            t4.setVisibility(View.GONE);
            nex.setVisibility(View.GONE);
            Toast.makeText(Forum.this, "SIGN IN AND POST QUESTIONS TO DISPLAY!", Toast.LENGTH_LONG).show();
            Toast.makeText(Forum.this, "SIGN IN AND POST QUESTIONS TO DISPLAY!", Toast.LENGTH_LONG).show();
        }
    }

    public void next(View v)
    {
       next = next + 4;
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        setTiles();
    }

    public void previous(View v)
    {
        if(visibility == true) {
            nex.setVisibility(View.VISIBLE);
            t1.setVisibility(View.VISIBLE);
            t2.setVisibility(View.VISIBLE);
            t3.setVisibility(View.VISIBLE);
            t4.setVisibility(View.VISIBLE);
            visibility = false;
        }
       if(next == 3)
           next = 0;
        else
           next = next - 4;

        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        setTiles();
    }

    public void post_question(View v)
    {
        dialog(0, "");
        //setTiles();
    }



    public void dialog(final int option,String qes)
    {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.prompt3, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setView(promptsView);

            et1 = (EditText)promptsView.findViewById(R.id.editText);

        if(option == 0)
        {

            alertDialogBuilder
                    .setTitle("GIVE YOUR REPLY AND SUBMIT")
                    .setCancelable(false)
                    .setPositiveButton("SUBMIT",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String s = String.valueOf(et1.getText());
                                    boolean f = fd.post_question(s);
                                    if (f == true) {
                                        Toast.makeText(Forum.this, "YOUR QUESTION WAS POSTED SUCCESFULLY!", Toast.LENGTH_SHORT).show();
                                        //setTiles();
                                        settitles = false;

                                    }
                                }
                            })
                    .setNegativeButton("CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }
        else if(!question.equals(""))
        {
            alertDialogBuilder
                    .setTitle(question)
                    .setCancelable(false)
                    .setPositiveButton("SUBMIT",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                        String s = String.valueOf(et1.getText());
                                        boolean f = fd.answer(question,s);
                                        if (f == true)
                                            Toast.makeText(Forum.this,"RECEIVED YOUR ANSWER!", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .setNegativeButton("CANCEL",
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

    }



    public String divide(String str)
    {
        boolean flag=false;
        String st="";
        for(int i=0;i<str.length();i++)
        {
            if(flag==true)
            {
                st = st+str.charAt(i);
            }
            if(str.charAt(i) == ':')
            {
                flag=true;
            }
        }

        return st;
    }


    public void toggle_contents(View v){
        if(v.getId() == R.id.title1) {
            String st = divide(t1.getText().toString());
            question=st;
            if (tv1.isShown()) {
                fx.slide_up(this, tv1);
                tv1.setVisibility(View.GONE);
            } else {
                tv1.setText(fd.get_answer(st));
                tv1.setVisibility(View.VISIBLE);
                fx.slide_down(this, tv1);
            }

        }

        if(v.getId() == R.id.title2) {
            String st = divide(t2.getText().toString());
            question=st;
            if (tv2.isShown()) {
                fx.slide_up(this, tv2);
                tv2.setVisibility(View.GONE);
            } else {
                tv2.setText(fd.get_answer(st));
                tv2.setVisibility(View.VISIBLE);
                fx.slide_down(this, tv2);
            }

        }

        if(v.getId() == R.id.title3) {
            String st = divide(t3.getText().toString());
            question=st;
            if (tv3.isShown()) {
                fx.slide_up(this, tv3);
                tv3.setVisibility(View.GONE);
            } else {
                tv3.setText(fd.get_answer(st));
                tv3.setVisibility(View.VISIBLE);
                fx.slide_down(this, tv3);
            }

        }

        if(v.getId() == R.id.title4) {
            String st = divide(t4.getText().toString());
            question=st;
            if (tv4.isShown()) {
                fx.slide_up(this, tv4);
                tv4.setVisibility(View.GONE);
            } else {
                tv4.setText(fd.get_answer(st));
                tv4.setVisibility(View.VISIBLE);
                fx.slide_down(this, tv4);
            }

        }

    }


    @Override
    public void set(String f2) {
        FragmentManager fn = getFragmentManager();
        fn.beginTransaction().replace(R.id.fragmentholder2, new Signup()).commit();

    }

    @Override
    public void visibility(String f1, String f2) {

        if(f1.equals("true"))
        {
            b3.setText("Sign Out");
            b1.setVisibility(View.VISIBLE);
            b2.setVisibility(View.VISIBLE);
            ll1.setVisibility(View.GONE);
            fd.update_status(f2);
        }

    }

    @Override
    public void hide(String f1) {

        if(f1.equals("true"))
        {
            FragmentManager fn = getFragmentManager();
            fn.beginTransaction().replace(R.id.fragmentholder2, new Login()).commit();
            Toast.makeText(Forum.this, "Login With Registered Credentials", Toast.LENGTH_SHORT).show();

        }
    }
}
