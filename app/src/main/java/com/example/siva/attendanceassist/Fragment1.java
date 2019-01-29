package com.example.siva.attendanceassist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Siva on 11-Feb-16.
 */

public class Fragment1 extends Fragment {

    String[] subjects={"MATHS \nAbsent:  Total:  Percentage: ","PHYSICS \nAbsent:  Total:  Percentage: ","CHEMISTRY \nAbsent:  Total:  Percentage: "};
    Database a = new Database(getActivity());
    int upd=0;
    ArrayAdapter<String> arrayAdapter;
    int del;
    ArrayList<String> arrayList;
    ListView listView2;
    toset ts;
    Activity_change ac;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        listView2 = (ListView)view.findViewById(R.id.sub_list);
        registerForContextMenu(listView2);
        arrayList = new ArrayList<String>();
        ts.set("siva");
        for(String items : subjects)
        {
            arrayList.add(items.toString());
            upd++;
        }
        arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.subjects_layout,R.id.textView,arrayList);
        listView2.setAdapter(arrayAdapter);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ac.act_change(position);
            }
        });
        return view;
    }

    public void listitemsSelected()
    {
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                ac.act_change(position);
            }
        });
    }

    public void array_update(String ps)
    {
        String stri = String.valueOf(ps.charAt(0));
        int len = Integer.parseInt(stri);
        String[] str = new String[len];
        int l = ps.length();
        int index=0;
        String s="",num="";
        String res="";
        boolean f1=true,f2=true,f3=true,f4=true;
        for(int i=1;i<l;i++)
        {
            if(f1 && ps.charAt(i)!='/'&& ps.charAt(i)!='+'&& ps.charAt(i)!='*'&& ps.charAt(i)!='-')
            {
                s = s+ps.charAt(i);
            }
            if(ps.charAt(i)=='+')
            {
                f1=false;
                s.toUpperCase();
                res = " "+res+s+"\n"+" Absent: ";
                s="";
            }
            if(!f1 && f2&& ps.charAt(i)!='/'&& ps.charAt(i)!='+'&& ps.charAt(i)!='*'&& ps.charAt(i)!='-')
            {
                s = s+ps.charAt(i);
            }
            if(ps.charAt(i)=='-')
            {
                f2=false;
                res = res + s + " " + "Total: ";
                s="";
            }
            if(!f1 && !f2 && f3&& ps.charAt(i)!='/'&& ps.charAt(i)!='+'&& ps.charAt(i)!='*'&& ps.charAt(i)!='-')
            {
                s = s+ps.charAt(i);
            }
            if(ps.charAt(i)=='*')
            {
                f3=false;
                res = res + s + " " + "%: ";
                s="";
            }
            if(!f1 && !f2 && !f3 && f4&& ps.charAt(i)!='/'&& ps.charAt(i)!='+'&& ps.charAt(i)!='*'&& ps.charAt(i)!='-')
            {
                s = s+ps.charAt(i);
            }
            if(ps.charAt(i)=='/')
            {
                /*for(int b=0;b<5;b++)
                {
                    num = num + s.charAt(b);
                }
                s = num;*/
                res = res + s;
                str[index]=res;
                s="";
                res="";
                num="";
                index++;
                f1=true;f2=true;f3=true;f4=true;
            }
        }
        subjects=str;

    }

    public interface toset
    {
        public void set(String name);
    }

    public interface Activity_change
    {
        public void act_change(int position);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.id_delete:
                del = info.position;
                arrayList.remove(info.position);
                arrayAdapter.notifyDataSetChanged();
                ts.set(String.valueOf(del));
                upd--;
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            ts = (toset) activity;
            ac = (Activity_change) activity;
        }catch(Exception e){}
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
