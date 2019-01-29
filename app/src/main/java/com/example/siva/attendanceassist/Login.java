package com.example.siva.attendanceassist;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Siva on 14-Apr-16.
 */
public class Login extends Fragment {

    EditText un,pwd;
    Forum_database fd2;
    boolean login_flag;
    fragchange fc;
    loginflags lf;
    TextView tv1,tv2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);

        fd2 = new Forum_database(getActivity());
        un = (EditText)view.findViewById(R.id.editText2);
        pwd = (EditText)view.findViewById(R.id.editText3);
        tv1 = (TextView)view.findViewById(R.id.log);
        tv2 = (TextView)view.findViewById(R.id.register);


        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = un.getText().toString();
                String b = pwd.getText().toString();
                login_flag = fd2.check_credentials(a,b);
                if(login_flag == true)
                {
                    lf.visibility("true",a);
                }
                else
                    Toast.makeText(getActivity(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc.set("true");
            }
        });

        return view;
    }

    public interface fragchange
    {
        public void set(String f2);
    }

    public interface loginflags
    {
        public void visibility(String f1,String f2);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            lf = (loginflags) activity;
            fc = (fragchange) activity;
        }catch(Exception e){}
    }
}
