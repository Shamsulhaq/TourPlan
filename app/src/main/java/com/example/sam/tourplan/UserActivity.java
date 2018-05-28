package com.example.sam.tourplan;

import android.content.DialogInterface;
import android.content.Intent;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sam.tourplan.DataBase.UserDatabase;
import com.example.sam.tourplan.Users.LogInfo;
import com.example.sam.tourplan.Users.UserModelClass;

public class UserActivity extends AppCompatActivity {
    private LogInfo logInfo;
    private UserDatabase database;
    private UserModelClass user;
    private int userID;
    private TextView userNameTV,userEmailTV,userPhoneTV,userPassTV,emergencyNumberTV,addressTV;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_user);
        inti();
        setText();


    }
    public void inti(){
        logInfo = new LogInfo(this);
        userID = logInfo.getStatus();
        database = new UserDatabase(this);
        user = database.getUser(userID);

        /*
        *      TextView Inti
        *
        * */
        userNameTV = (TextView) findViewById(R.id.usernameAU);
        userEmailTV =  (TextView) findViewById(R.id.useremailAU);
        userPhoneTV =  (TextView) findViewById(R.id.userphoneAU);
        userPassTV =  (TextView) findViewById(R.id.userpassAU);
        emergencyNumberTV =  (TextView) findViewById(R.id.useremergencyAU);
        addressTV =  (TextView) findViewById(R.id.useraddressAU);
    }
    /*
    *
    *     SET DATA
    *
    * */

    public void setText(){
        userNameTV.setText("User name: "+user.getUserName());
        userEmailTV.setText("User Email: "+user.getUserEmail());
        userPhoneTV.setText("User Phone: "+user.getUserPhone());
        userPassTV.setText("User Password: "+user.getUserPsss());
        emergencyNumberTV.setText("Emergency Number: "+user.getEmergencyContact());
        addressTV.setText("Address: "+user.getAddress());
    }
    public void logout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
        builder.setTitle("Log Out !");
        builder.setMessage("Are you sure you want to Log Out?");
        builder.setIcon(R.drawable.logout);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logInfo.addStatus(0);
                startActivity(new Intent(UserActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
        builder.setNegativeButton("No",null);
        builder.show();
    }

    public void backToHome(View view) {
        startActivity(new Intent(UserActivity.this,EventShowActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
