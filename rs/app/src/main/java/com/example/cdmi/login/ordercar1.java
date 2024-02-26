package com.example.cdmi.login;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
public class ordercar1 extends BaseAdapter {
        Context mainActivity;
        ArrayList<String> name;
        ArrayList<String> city;
        //ArrayList<String> model;
        ArrayList<String> price;
        ArrayList<String> img;
        TextView textView;
    public ordercar1(Context mainActivity, ArrayList name, ArrayList city, ArrayList price, ArrayList img)
        {
            this.name = name;
            this.mainActivity = mainActivity;
            this.city = city;
            this.price = price;
            this.img = img;
        }
        @Override
        public int getCount () {
        return name.size();
    }
        @Override
        public Object getItem ( int position){
        return position;
    }
        @Override
        public long getItemId ( int position){
        return position;
    }
        @Override
        public View getView ( int position, View convertView, ViewGroup parent)
        {
            LayoutInflater layoutInflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fragment_ordercar1, parent, false);
            TextView t = convertView.findViewById(R.id.name);
            t.setText(name.get(position));
            TextView t2 = convertView.findViewById(R.id.model);
            t2.setText("City:" + city.get(position));
            TextView t3 = convertView.findViewById(R.id.price);
            t3.setText("Rate:" + price.get(position));
            String imgname = "https://rideclub.000webhostapp.com/jigu/cab_image/" + img.get(position);
            ImageView imageView = convertView.findViewById(R.id.imageView3);
            Picasso.get().load(imgname).into(imageView);
            // imageView.setImageResource(img[position]);
            return convertView;
        }
    }