package com.example.sam.tourplan.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sam.tourplan.Event.EventModelClass;
import com.example.sam.tourplan.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 5/7/2017.
 */

public class EventAdapter extends ArrayAdapter<EventModelClass> {
    private ArrayList<EventModelClass> events;
    private Context context;


    public EventAdapter(@NonNull Context context,ArrayList<EventModelClass> events) {
        super(context, R.layout.event_list_layout,events);
        this.context = context;
        this.events = events;
    }
    class ViewHolder {
        TextView destination,startDate,endDate,budGet;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView==null){

            convertView = inflater.inflate(R.layout.event_list_layout,parent,false);
            holder = new ViewHolder();


            holder.destination= (TextView) convertView.findViewById(R.id.event_des);
            holder.startDate= (TextView) convertView.findViewById(R.id.event_start);
            holder.endDate= (TextView) convertView.findViewById(R.id.eventend);
            holder.budGet= (TextView) convertView.findViewById(R.id.budget);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.destination.setText("Destination: " + events.get(position).getDestination());
        holder.startDate.setText("Start: " + events.get(position).getStartDate());
        holder.endDate.setText("End: " + events.get(position).getEndDate());
        holder.budGet.setText("Budget Amount: " + events.get(position).getBudget()+" Tk");

        return convertView;
    }
    }

