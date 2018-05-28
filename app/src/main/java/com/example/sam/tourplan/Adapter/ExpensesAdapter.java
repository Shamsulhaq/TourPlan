package com.example.sam.tourplan.Adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sam.tourplan.Event.Summary.ExpensesModelClass;
import com.example.sam.tourplan.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 5/7/2017.
 */

public class ExpensesAdapter extends ArrayAdapter<ExpensesModelClass> {
    private Context context;
    private ArrayList<ExpensesModelClass> expensesall;

    public ExpensesAdapter(@NonNull Context context, ArrayList<ExpensesModelClass> expensesall) {
        super(context, R.layout.my_expenses_list_layout, expensesall);
        this.context = context;
        this.expensesall = expensesall;
    }

    class ViewHolder{
        TextView expenseswayTV,expensesamountTV;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.my_expenses_list_layout, parent, false);
            holder = new ViewHolder();

            holder.expenseswayTV = (TextView) convertView.findViewById(R.id.expenses_way);
            holder.expensesamountTV = (TextView) convertView.findViewById(R.id.expenses_Amount);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.expenseswayTV.setText("Expenses Titsle: " + expensesall.get(position).getExpensesFor());
        holder.expensesamountTV.setText("Amount: " + expensesall.get(position).getExpensesAmount()+" Tk");


        return convertView;
    }

}
