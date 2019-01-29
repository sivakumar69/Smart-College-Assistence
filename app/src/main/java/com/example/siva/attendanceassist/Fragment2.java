package com.example.siva.attendanceassist;

import android.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Siva on 01-Apr-16.
 */
public class Fragment2 extends Fragment {
    TextView tv1,stv1,tv2,stv2,tv3,stv3,tv4;
    Animate fx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        fx = new Animate();
        tv4 = (TextView)view.findViewById(R.id.textView6);
        tv4.setSelected(true);

        tv1 = (TextView)view.findViewById(R.id.t1);
        tv2 = (TextView)view.findViewById(R.id.t2);
        tv3 = (TextView)view.findViewById(R.id.t3);
        stv1 = (TextView)view.findViewById(R.id.s1);
        stv2 = (TextView)view.findViewById(R.id.s2);
        stv3 = (TextView)view.findViewById(R.id.s3);


        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stv1.isShown()) {
                    fx.slide_up(getActivity(), stv1);
                    stv1.setVisibility(View.GONE);
                } else {
                    stv1.setVisibility(View.VISIBLE);
                    fx.slide_down(getActivity(), stv1);
                }
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stv2.isShown()) {
                    fx.slide_up(getActivity(), stv2);
                    stv2.setVisibility(View.GONE);
                } else {
                    stv2.setVisibility(View.VISIBLE);
                    fx.slide_down(getActivity(), stv2);
                }
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stv3.isShown()) {
                    fx.slide_up(getActivity(), stv3);
                    stv3.setVisibility(View.GONE);
                } else {
                    stv3.setVisibility(View.VISIBLE);
                    fx.slide_down(getActivity(), stv3);
                }
            }
        });
        return view;
    }
}
