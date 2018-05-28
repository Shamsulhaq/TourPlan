package com.example.sam.tourplan;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.tourplan.Adapter.EventAdapter;
import com.example.sam.tourplan.DataBase.EventDatabase;
import com.example.sam.tourplan.Event.EventModelClass;
import com.example.sam.tourplan.Users.LogInfo;
import com.example.sam.tourplan.Weather.WeatherActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EventShowActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FloatingActionButton fab;
    private int year;
    private int month;
    private int day;
    private Calendar calendar;
    private String date;
    private int userId ;
    private EventModelClass event;
    private LogInfo logInfo ;
    private EventDatabase database;
    private ArrayList<EventModelClass> events;
    private ListView mylist;
    private EventAdapter adapter ;
    private TextView nodatTV;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_event_activity_main);
        setTitle("All Events");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navi_menu();


        mylist = (ListView) findViewById(R.id.myEventlist);
        logInfo = new LogInfo(this);
        userId = logInfo.getStatus();
        nodatTV = (TextView) findViewById(R.id.nodatEvn);


        database = new EventDatabase(this);
        events = database.getAllEvent(userId);
        adapter = new EventAdapter(this,events);
        mylist.setAdapter(adapter);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int rowID = events.get(i).getEventId();
                startActivity(new Intent(EventShowActivity.this,EventDetailsActivity.class).putExtra("rowID",rowID));
            }
        });
        if (events.isEmpty()){
            nodatTV.setText("NO Data Fould!");
        }
        floatingAction();


        calendarInti();

    }

    public void floatingAction(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                View layout = LayoutInflater.from(EventShowActivity.this).inflate(R.layout.add_event_layout,null);
                AlertDialog.Builder dialog =new AlertDialog.Builder(EventShowActivity.this);
                dialog.setView(layout);
                final TextInputEditText destinationET,startDateET,endDateET,budgetAmountET;
                Button cancleBtn,CreateBtn;
                cancleBtn = (Button) layout.findViewById(R.id.cancleBTN);
                CreateBtn = (Button) layout.findViewById(R.id.addBTN);

                cancleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(EventShowActivity.this,EventShowActivity.class));
                    }
                });

                destinationET = (TextInputEditText) layout.findViewById(R.id.destinationET);
                startDateET = (TextInputEditText) layout.findViewById(R.id.startDateET);
                endDateET = (TextInputEditText) layout.findViewById(R.id.endDateET);
                budgetAmountET = (TextInputEditText) layout.findViewById(R.id.budgetET);
                startDateET.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog dpd =new DatePickerDialog(EventShowActivity.this,dateListener,year,month,(day+2));
                        dpd.show();


                    }
                    DatePickerDialog.OnDateSetListener dateListener= new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            startDateET.setText(dayOfMonth+"-"+(month+1)+"-"+year);




                        }
                    };

                });
                endDateET.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog dpd =new DatePickerDialog(EventShowActivity.this,dateListener,year,month,(day+2));
                        dpd.show();


                    }
                    DatePickerDialog.OnDateSetListener dateListener= new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                            endDateET.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                            boolean status = checkValidDate(startDateET.getText().toString(),endDateET.getText().toString());

                            if (!status){
                                endDateET.setText("");
                                Toast.makeText(EventShowActivity.this,"End Date Must Be After Start Date",Toast.LENGTH_SHORT).show();
                            }
                            Log.d("TEST", "onDateSet: ------- "+endDateET.getText().toString());

                        }
                    };
                });
                CreateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String destinationGet,startDateGet,endDateGt,budgetGet;
                        destinationGet = destinationET.getText().toString();
                        startDateGet = startDateET.getText().toString();
                        endDateGt = endDateET.getText().toString();
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
                            event = new EventModelClass(userId,destinationGet,startDateGet,endDateGt,budget);
                            boolean id = database.createEvent(event);
                            if (id){
                                Toast.makeText(EventShowActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EventShowActivity.this,EventShowActivity.class));
                            }


                        }
                    }
                });

                dialog.show();
            }
        });
    }

    public void calendarInti(){
        calendar=Calendar.getInstance(Locale.getDefault());
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;

    }

    public void logOut(MenuItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(EventShowActivity.this);
        builder.setTitle("Log Out !");
        builder.setMessage("Are you sure you want to Log Out?");
        builder.setIcon(R.drawable.logout);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logInfo.addStatus(0);
                startActivity(new Intent(EventShowActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
        builder.setNegativeButton("No",null);
        builder.show();


    }


    public void userInfo(MenuItem item) {
        startActivity(new Intent(EventShowActivity.this,UserActivity.class));

    }
    private void navi_menu(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(EventShowActivity.this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_forecast) {
            startActivity(new Intent(EventShowActivity.this,WeatherActivity.class));
        } else if (id == R.id.nav_nearbye) {
            //Toast.makeText(this, "Nearbye", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_user) {
            startActivity(new Intent(EventShowActivity.this,UserActivity.class));

        }else if (id == R.id.nav_About_Us) {
           // Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
}
