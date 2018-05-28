package com.example.sam.tourplan;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.tourplan.DataBase.EventDatabase;
import com.example.sam.tourplan.DataBase.ExpensesDatabase;
import com.example.sam.tourplan.DataBase.MemoryDatabase;
import com.example.sam.tourplan.Event.EventModelClass;
import com.example.sam.tourplan.Event.Summary.ExpensesModelClass;
import com.example.sam.tourplan.Event.Summary.Memory.MemoryModelClass;
import com.example.sam.tourplan.R;
import com.example.sam.tourplan.Users.LogInfo;

import java.io.ByteArrayOutputStream;

public class EventDetailsActivity extends AppCompatActivity {

    private TextView eventIdTV;
    private TextView eventDestinationTV;
    private TextView eventStartDateTV;
    private TextView eventEndDateTV;
    private TextView eventBudgetTV;
    private LogInfo logInfo;
    private EventDatabase database;
    private EventModelClass event;
    int eventID;
    private ExpensesDatabase expensesDatabase;
    private MemoryDatabase memoryDatabase;
    private ExpensesModelClass expenses;
    private MemoryModelClass memory ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        setTitle("Event Details");
        inti();
        setInfo();
    }

    private void inti(){
        eventIdTV = (TextView) findViewById(R.id.eventId);
        eventDestinationTV = (TextView) findViewById(R.id.eventDestinastion);
        eventStartDateTV = (TextView) findViewById(R.id.eventStartDate);
        eventEndDateTV = (TextView) findViewById(R.id.eventEndDate);
        eventBudgetTV = (TextView) findViewById(R.id.eventBudget);
        database =new EventDatabase(this);
        logInfo = new LogInfo(this);
        eventID = getIntent().getIntExtra("rowID",0);
        event = database.getEvent(eventID);
        expensesDatabase = new ExpensesDatabase(this);
        memoryDatabase = new MemoryDatabase(this);

    }

    private void setInfo(){
        eventIdTV.setText(""+event.getEventId());
        eventDestinationTV.setText(""+event.getDestination());
        eventStartDateTV.setText(""+event.getStartDate());
        eventEndDateTV.setText(""+event.getEndDate());
        eventBudgetTV.setText(""+event.getBudget()+" Tk");
    }

    public void editeEvent(View view) {
        View layout = LayoutInflater.from(EventDetailsActivity.this).inflate(R.layout.add_event_layout,null);
        AlertDialog.Builder dialog =new AlertDialog.Builder(EventDetailsActivity.this);
        dialog.setView(layout);
        final TextView titaleTV;
        final TextInputEditText destinationET,startDateET,endDateET,budgetAmountET;
        Button cancleBtn,createBtn;
        cancleBtn = (Button) layout.findViewById(R.id.cancleBTN);
        createBtn = (Button) layout.findViewById(R.id.addBTN);
        createBtn.setText("Upgrade");
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventDetailsActivity.this,EventDetailsActivity.class).putExtra("rowID",eventID));
            }
        });

        titaleTV = (TextView) layout.findViewById(R.id.titaleup);
        destinationET = (TextInputEditText) layout.findViewById(R.id.destinationET);
        startDateET = (TextInputEditText) layout.findViewById(R.id.startDateET);
        endDateET = (TextInputEditText) layout.findViewById(R.id.endDateET);
        budgetAmountET = (TextInputEditText) layout.findViewById(R.id.budgetET);

        titaleTV.setText("Upgrade Event");
        destinationET.setText(""+event.getDestination());
        startDateET.setText(""+event.getStartDate());
        endDateET.setText(""+event.getEndDate());



        budgetAmountET.setText(""+event.getBudget());


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destinationGet,startDateGet,endDateGt,budgetGet;
                destinationGet = destinationET.getText().toString();
                startDateGet = startDateET.getText().toString();
                endDateGt = endDateET.getText().toString();

                boolean status = checkValidDate(startDateGet,endDateGt);
                if (!status){
                    endDateGt = "";
                    Toast.makeText(EventDetailsActivity.this,"End Date Must Be After Start Date",Toast.LENGTH_SHORT).show();
                }
                budgetGet = budgetAmountET.getText().toString();
                if (destinationGet.isEmpty()){
                    destinationET.setError("Enter Destination");
                }else if (startDateGet.isEmpty()){
                    startDateET.setError("Choose Tour Start Date");
                }else if (endDateGt.isEmpty()){
                    endDateET.setError("Choose Tour Finish Date");
                }else if (budgetGet.isEmpty()){
                    budgetAmountET.setError("Enter Budget Amount");
                }else {

                    int budget = Integer.parseInt(budgetGet);
                    int userId = logInfo.getStatus();
                    event = new EventModelClass(userId,destinationGet,startDateGet,endDateGt,budget);
                    boolean id = database.upDateEvent(event,eventID);
                    if (id){
                        Toast.makeText(EventDetailsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EventDetailsActivity.this,EventDetailsActivity.class)
                                .putExtra("rowID",eventID)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }


                }
            }
        });

        dialog.show();

    }

    public boolean checkValidDate(String startDate,String endDate){
        String[] dateStart = startDate.split("-");
        String[] dateEnd = endDate.split("-");
        /*
        * date Format is  = 14 - 5 - 2017
        * so after split
        * dateStart[0] = date
        * dateStart[1] = month
        * dateStart[2] = year
        *
        * String.trim() => remove space from any string
        * example if a = " 10 "
        * then after trim() it will be a = "10";
        *
        * Note : Integer can,t parse space value so without space remove you will got exception
        * */

        if (Integer.parseInt(dateStart[2].trim()) > Integer.parseInt(dateEnd[2].trim())){
            return false;
        }else if (Integer.parseInt(dateStart[1].trim()) > Integer.parseInt(dateEnd[1].trim())){
            return false;
        }else if (Integer.parseInt(dateStart[0].trim()) > Integer.parseInt(dateEnd[0].trim())){
            return false;
        }else return true;
    }

    public void deleteEvent(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailsActivity.this);
        builder.setTitle("Delete Event !");
        builder.setMessage("Are you sure you want to Delete?");
        builder.setIcon(R.drawable.folder);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                boolean status = database.deleteEvent(eventID);
                if (status){
                    Toast.makeText(EventDetailsActivity.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EventDetailsActivity.this,EventShowActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }else {
                    Toast.makeText(EventDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("No",null);
        builder.show();


    }

    public void addExpenses(View view) {
        View layout = LayoutInflater.from(EventDetailsActivity.this).inflate(R.layout.add_expenses_layout,null);
        AlertDialog.Builder dialog =new AlertDialog.Builder(EventDetailsActivity.this);
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

                startActivity(new Intent(EventDetailsActivity.this,EventDetailsActivity.class).putExtra("rowID",eventID));

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
                    boolean status = expensesDatabase.createExpenses(expenses);
                    if (status){
                        Toast.makeText(EventDetailsActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EventDetailsActivity.this,ShowExpensesActivity.class).putExtra("rowID",eventID));

                    }else {
                        Toast.makeText(EventDetailsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        dialog.show();
    }

    public void showExpenses(View view) {
        startActivity(new Intent(EventDetailsActivity.this,ShowExpensesActivity.class).putExtra("rowID",eventID));
    }

    public void addMemory(View view) {
        startActivity(new Intent(EventDetailsActivity.this,AddMemoryActivity.class).putExtra("rowID",eventID));
    }

    public void showMemory(View view) {
        startActivity(new Intent(EventDetailsActivity.this,ShowMemoryActivity.class).putExtra("rowID",eventID));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.detail_menu,menu);
        return true;

    }

    public void logOut(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailsActivity.this);
        builder.setTitle("Log Out !");
        builder.setMessage("Are you sure you want to Log Out?");
        builder.setIcon(R.drawable.logout);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logInfo.addStatus(0);
                startActivity(new Intent(EventDetailsActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
        builder.setNegativeButton("No",null);
        builder.show();
    }


    public void gotoHome(MenuItem item) {
        startActivity(new Intent(EventDetailsActivity.this,EventShowActivity.class));
    }

}