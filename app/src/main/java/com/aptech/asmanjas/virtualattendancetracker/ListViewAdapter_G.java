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
 * Created by Asmanjas on 30-03-2018.
 */

public class ListViewAdapter_G  extends ArrayAdapter<Product> {
    public ListViewAdapter_G(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View v2=convertView;
        if(null==v2)
        {
            LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v2=inflater.inflate(R.layout.listitem_g,null);

        }

        Product product_g = getItem(position);
        ImageView img=(ImageView) v2.findViewById(R.id.image_listView_g);
        TextView txtTitle=(TextView) v2.findViewById(R.id.subject_name_list_view_G);
        TextView class_att = (TextView)v2.findViewById(R.id.classes_List_View_G);
        TextView total_cl = (TextView)v2.findViewById(R.id.total_classes_list_view_G);
        TextView out_of = (TextView)v2.findViewById(R.id.out_of_g);

        img.setImageResource(product_g.getImageid());
        txtTitle.setText(product_g.getTitle());
        int classes = product_g.getAttendance();
        int total_classes = product_g.getTotal_c();
        class_att.setText(String.valueOf(classes));
        out_of.setText(product_g.getDescription());
        total_cl.setText(String.valueOf(total_classes));


        return v2;
    }
}
