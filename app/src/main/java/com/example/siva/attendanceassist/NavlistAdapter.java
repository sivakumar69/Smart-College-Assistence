package com.example.siva.attendanceassist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siva on 09-Jan-16.
 */
public class NavlistAdapter extends ArrayAdapter{

    List list = new ArrayList();

    public NavlistAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class DataHandeller
    {
        ImageView poster;
        TextView title;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        DataHandeller handeller;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_layout,parent,false);
            handeller = new DataHandeller();
            handeller.poster = (ImageView)row.findViewById(R.id.imageView);
            handeller.title = (TextView)row.findViewById(R.id.textView);
            row.setTag(handeller);
        }
        else
        {
            handeller = (DataHandeller)row.getTag();
        }
        DataProvider dataProvider;
        dataProvider = (DataProvider) this.getItem(position);
        handeller.poster.setImageResource(dataProvider.getPoster());
        handeller.title.setText(dataProvider.getTitle());
        return row;
    }


}
