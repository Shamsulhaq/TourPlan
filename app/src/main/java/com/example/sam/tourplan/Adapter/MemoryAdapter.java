package com.example.sam.tourplan.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.tourplan.Event.Summary.Memory.MemoryModelClass;
import com.example.sam.tourplan.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by ASUS on 5/7/2017.
 */

public class MemoryAdapter extends ArrayAdapter<MemoryModelClass>{
    private Context context;
    private ArrayList<MemoryModelClass> memorys;
    private MemoryModelClass memory;

    public MemoryAdapter(@NonNull Context context, ArrayList<MemoryModelClass> memorys) {
        super(context, R.layout.my_memory_list_layout, memorys);
        this.context = context;
        this.memorys = memorys;
    }

    class ViewHolder{
        TextView titleTV;
        ImageView imageIV;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.my_memory_list_layout, parent, false);
            holder = new ViewHolder();

            holder.titleTV = (TextView) convertView.findViewById(R.id.memoryHints);
            holder.imageIV = (ImageView) convertView.findViewById(R.id.memoryPicture);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //try {
            memory = memorys.get(position);
            byte[] bytet =  memory.getMemoryImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(bytet);
            final Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            holder.titleTV.setText("" + memory.getMemoryText());
            holder.imageIV.setImageBitmap(theImage);

        /*} catch (Exception e) {

            Log.d("Test", "login: ---------- Un successful  "+e);

        }*/


        return convertView;
    }

}
