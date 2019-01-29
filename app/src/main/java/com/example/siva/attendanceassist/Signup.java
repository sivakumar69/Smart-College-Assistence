package com.example.siva.attendanceassist;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Siva on 14-Apr-16.
 */
public class Signup extends Fragment {

    EditText name,un,pwd,mobile;
    TextView untv,mobiletv;
    Button register_button;
    boolean register_flag;
    Forum_database fd3;
    int x=0;
    signupflags sf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.signup, container, false);

        name = (EditText) view.findViewById(R.id.editText4);
        un = (EditText) view.findViewById(R.id.editText5);
        pwd = (EditText) view.findViewById(R.id.editText7);
        mobile = (EditText) view.findViewById(R.id.editText6);
        untv = (TextView) view.findViewById(R.id.textView14);
        mobiletv = (TextView) view.findViewById(R.id.textView15);
        register_button = (Button) view.findViewById(R.id.button);
        fd3 = new Forum_database(getActivity());
        untv.setVisibility(view.GONE);
        mobiletv.setVisibility(view.GONE);

        pwd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (x < 1) {

                }
                return false;
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                String a, b, c, d;
                a = un.getText().toString();
                b = pwd.getText().toString();
                c = name.getText().toString();
                d = mobile.getText().toString();
                register_flag = fd3.check_validity(a);
                if (register_flag == true) {
                    flag = fd3.insert_credentials(a, b, c, d);
                } else {
                    //Toast.makeText(getActivity(),"Username already exsists",Toast.LENGTH_SHORT).show();
                    untv.setVisibility(view.VISIBLE);
                    untv.setText("Username already exsists");
                }
                if(flag == true)
                {
                    sf.hide("true");
                }

            }
        });

        return view;
    }

    public interface signupflags
    {
        public void hide(String f1);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            sf = (signupflags) activity;
        }catch(Exception e){}
    }
}
