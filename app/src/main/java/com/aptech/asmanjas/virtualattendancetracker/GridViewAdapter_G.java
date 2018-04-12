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

public class GridViewAdapter_G  extends ArrayAdapter<Product> {
    public GridViewAdapter_G(@NonNull Context context, int resource, @NonNull List<Product> objects) {
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
            v=inflater.inflate(R.layout.gridlist,null);

        }
        Product product=getItem(position);
        ImageView img=(ImageView) v.findViewById(R.id.image_listView_g);
        TextView txtTitle=(TextView) v.findViewById(R.id.subject_name_list_view_G);
        TextView txtDescription=(TextView) v.findViewById(R.id.out_of_g);

        img.setImageResource(product.getImageid());
        txtTitle.setText(product.getTitle());
        txtDescription.setText(product.getDescription());


        return v;
    }
}
