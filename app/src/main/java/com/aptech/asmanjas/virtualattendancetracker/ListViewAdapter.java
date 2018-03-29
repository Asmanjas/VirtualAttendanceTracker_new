package com.aptech.asmanjas.virtualattendancetracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by m on 18-Mar-18.
 */

public class ListViewAdapter extends ArrayAdapter<Product> {
    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View v=convertView;
        if(null==v)
        {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.listitem,null);

        }
        Product product=getItem(position);
        ImageView img=(ImageView) v.findViewById(R.id.image_view_1);
        TextView txtTitle=(TextView) v.findViewById(R.id.texttitle);
        TextView class_att = (TextView)v.findViewById(R.id.class_attended);
        TextView total_cl = (TextView)v.findViewById(R.id.total_classes);
        TextView out_of = (TextView)v.findViewById(R.id.total_classes);

        img.setImageResource(product.getImageid());
        txtTitle.setText(product.getTitle());
        int classes = product.getAttendance();
        int total_classes = product.getTotal_c();
        class_att.setText(String.valueOf(classes));
        out_of.setText(product.getDescription());
        total_cl.setText(String.valueOf(total_classes));


        return v;
    }
}
