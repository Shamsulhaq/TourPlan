package com.example.sam.tourplan;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.tourplan.Adapter.ExpensesAdapter;
import com.example.sam.tourplan.DataBase.EventDatabase;
import com.example.sam.tourplan.DataBase.ExpensesDatabase;
import com.example.sam.tourplan.Event.EventModelClass;
import com.example.sam.tourplan.Event.Summary.ExpensesModelClass;

import java.util.ArrayList;

public class ShowExpensesActivity extends AppCompatActivity {
    private int eventID;
    private ArrayList<ExpensesModelClass> expensesall;
    private ExpensesDatabase database;
    private EventDatabase eventDatabase;
    private ListView mylist;
    private ExpensesAdapter adapter;
    private EventModelClass event;
    private TextView budgetTv,totalExpensesTv,SummeryTv;
    private int expenseID;
    private int total;
    private ExpensesModelClass expenses;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses);
        setTitle("Expenses Details");

        inti();
        setAction();
        totalExpenses();
        summery();
        //fbAction();


    }

    private void setAction(){
        mylist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                expenseID = expensesall.get(i).getExpensesId();

                AlertDialog.Builder builder = new AlertDialog.Builder(ShowExpensesActivity.this);
                builder.setTitle("Delete Event !");
                builder.setMessage("Are you sure you want to Delete?");
                builder.setIcon(R.drawable.folder);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean status = database.deleteExpenses(expenseID);
                        if (status){
                            Toast.makeText(ShowExpensesActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ShowExpensesActivity.this,ShowExpensesActivity.class)
                                    .putExtra("rowID",eventID)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }else {
                            Toast.makeText(ShowExpensesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("No",null);
                builder.show();
                return false;
            }
        });
    }
    private void totalExpenses(){
         total = 0;
        for (int i= 0 ; expensesall.size() > i ;i++){
            int amount = expensesall.get(i).getExpensesAmount();
            total += amount;
            totalExpensesTv.setText("Total Expenses : " +total + " Tk");
        }
    }
    private void summery(){

        int summery =(event.getBudget() - total);
        if (summery < 0)
        {
            SummeryTv.setText("Summery : Your Expenses " + summery+ " Tk are more than Budget" );
        }else {
            SummeryTv.setText("Summery : Now You Have " + summery+ " Tk" );
        }


    }
   /* private void fbAction(){
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View layout = LayoutInflater.from(ShowExpensesActivity.this).inflate(R.layout.add_expenses_layout,null);
                AlertDialog.Builder dialog =new AlertDialog.Builder(ShowExpensesActivity.this);
                dialog.setView(layout);
                final TextInputEditText expenseForET,expensesAmountET;
                Button cancleBtn,createBtn;
                cancleBtn = (Button) layout.findViewById(R.id.cancleBTN);
                createBtn = (Button) layout.findViewById(R.id.addBTN);
                expenseForET = (TextInputEditText) layout.findViewById(R.id.expensestitle);
                expensesAmountET = (TextInputEditText) layout.findViewById(R.id.amountofexpenses);
                cancleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        startActivity(new Intent(ShowExpensesActivity.this,ShowExpensesActivity.class).putExtra("rowID",eventID));

                    }
                });
                createBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String expensesFor = expenseForET.getText().toString();
                        String expensesAmount = expensesAmountET.getText().toString();
                        if (expensesFor.isEmpty()){
                            expenseForET.setError("Enter Expenses Titale");
                        }else if (expensesAmount.isEmpty()){
                            expensesAmountET.setError("Enter Expenses Amount");
                        }else {

                            expenses = new ExpensesModelClass(eventID,expensesFor,Integer.parseInt(expensesAmount));
                            boolean status = database.createExpenses(expenses);
                            if (status){
                                Toast.makeText(ShowExpensesActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ShowExpensesActivity.this,ShowExpensesActivity.class).putExtra("rowID",eventID));

                            }else {
                                Toast.makeText(ShowExpensesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
                dialog.show();
            }
        });
    }*/
    private void inti(){
       // fb = (FloatingActionButton) findViewById(R.id.fabexpenses);
        eventID = getIntent().getIntExtra("rowID",0);
        database = new ExpensesDatabase(this);
        expensesall = database.getAllExpenses(eventID);
        adapter = new ExpensesAdapter(this,expensesall);
        mylist = (ListView) findViewById(R.id.myExpensesList);
        mylist.setAdapter(adapter);
        eventDatabase = new EventDatabase(this);
        event = eventDatabase.getEvent(eventID);
        budgetTv = (TextView) findViewById(R.id.budgetETEx);
        totalExpensesTv = (TextView) findViewById(R.id.totalexpensesETEx);
        SummeryTv = (TextView) findViewById(R.id.summeryETEx);
        budgetTv.setText("Budget Amount : "+event.getBudget()+ " Tk");
        TextView nodataTV = (TextView) findViewById(R.id.nodatExp);
        if (expensesall.isEmpty()){
            nodataTV.setText("NO Data Found!");
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.expenses_menu,menu);
        return true;

    }
    public void gotoHome(MenuItem item) {
        startActivity(new Intent(ShowExpensesActivity.this,EventDetailsActivity.class).putExtra("rowID",eventID));
    }

    public void fbAction(MenuItem item) {
        View layout = LayoutInflater.from(ShowExpensesActivity.this).inflate(R.layout.add_expenses_layout,null);
        AlertDialog.Builder dialog =new AlertDialog.Builder(ShowExpensesActivity.this);
        dialog.setView(layout);
        final TextInputEditText expenseForET,expensesAmountET;
        Button cancleBtn,createBtn;
        cancleBtn = (Button) layout.findViewById(R.id.cancleBTN);
        createBtn = (Button) layout.findViewById(R.id.addBTN);
        expenseForET = (TextInputEditText) layout.findViewById(R.id.expensestitle);
        expensesAmountET = (TextInputEditText) layout.findViewById(R.id.amountofexpenses);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ShowExpensesActivity.this,ShowExpensesActivity.class).putExtra("rowID",eventID));

            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expensesFor = expenseForET.getText().toString();
                String expensesAmount = expensesAmountET.getText().toString();
                if (expensesFor.isEmpty()){
                    expenseForET.setError("Enter Expenses Titale");
                }else if (expensesAmount.isEmpty()){
                    expensesAmountET.setError("Enter Expenses Amount");
                }else {

                    expenses = new ExpensesModelClass(eventID,expensesFor,Integer.parseInt(expensesAmount));
                    boolean status = database.createExpenses(expenses);
                    if (status){
                        Toast.makeText(ShowExpensesActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ShowExpensesActivity.this,ShowExpensesActivity.class).putExtra("rowID",eventID));

                    }else {
                        Toast.makeText(ShowExpensesActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        dialog.show();
    }

}
