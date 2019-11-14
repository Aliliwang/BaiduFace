package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<Menu> {
    private int resourceId;

    public  MenuAdapter(Context context, int textViewResourceId, List<Menu> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Menu menu = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);

            viewHolder = new ViewHolder();
            viewHolder.menuImage = (ImageView) view.findViewById(R.id.menu_image);
            viewHolder.menuName = (TextView) view.findViewById(R.id.menu_name);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder =(ViewHolder)view.getTag();
        }
        viewHolder.menuImage.setImageResource(menu.getImageid());
        viewHolder.menuName.setText(menu.getName());


        return  view;

    }
    class ViewHolder{
        ImageView menuImage;
        TextView menuName;

    }




}
