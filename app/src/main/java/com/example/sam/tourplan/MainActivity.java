package com.example.sam.tourplan;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sam.tourplan.DataBase.UserDatabase;
import com.example.sam.tourplan.Users.LogInfo;
import com.example.sam.tourplan.Users.UserModelClass;
import com.example.sam.tourplan.Weather.WeatherActivity;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private InputValidation inputValidation;
    private TextInputLayout userNameLy,userPassLy;
    private TextInputEditText userNameET,userPassET;
    private UserDatabase userDb;
    private UserModelClass user;
    private LogInfo logInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        if (logInfo.getStatus() != 0 & logInfo.getStatus() >0 ){
            startActivity(new Intent(MainActivity.this,EventShowActivity.class));

        }

    }


   private void intiView(){
       inputValidation =new InputValidation(this);
       userDb = new UserDatabase(this);
       logInfo = new LogInfo(this);
       userNameET = (TextInputEditText) findViewById(R.id.UserNameLET);
       userPassET = (TextInputEditText) findViewById(R.id.UserPassLET);
       //user = new UserModelClass();
       userNameLy = (TextInputLayout) findViewById(R.id.UserNameLLayout);
       userPassLy = (TextInputLayout) findViewById(R.id.UserPassLLayout);



   }













    public void signUp(View view) {

        View layout = LayoutInflater.from(this).inflate(R.layout.sign_up_layout,null);
         AlertDialog.Builder dialog =new AlertDialog.Builder(this);

        // layout
        dialog.setView(layout);
        final TextInputLayout userNameLy,userEmailLy,userPhoneLy,userPassLy,EmergencyLy,addressLy;
        final TextInputEditText userNameEt,userEmailEt,userPhoneEt,userPassEt,EmergencyEt,addressEt;
        Button cancleBtn,CreateBtn;
        cancleBtn = (Button) layout.findViewById(R.id.cancleBTN);
        CreateBtn = (Button) layout.findViewById(R.id.addBTN);
        //-------------------- Layout ___---------------------------
        userNameLy = (TextInputLayout) layout.findViewById(R.id.UsernameLayout);
        userEmailLy = (TextInputLayout) layout.findViewById(R.id.UseremailLayout);
        userPhoneLy = (TextInputLayout) layout.findViewById(R.id.UserphoneLayout);
        userPassLy = (TextInputLayout) layout.findViewById(R.id.UserpassLayout);

        EmergencyLy = (TextInputLayout) layout.findViewById(R.id.UseremergencyLayout);
        addressLy = (TextInputLayout) layout.findViewById(R.id.UseraddressLayout);
        //------------------------------ Edite Text -----------------------------
        userNameEt = (TextInputEditText) layout.findViewById(R.id.usernameET);
        userEmailEt = (TextInputEditText) layout.findViewById(R.id.useremailET);
        userPhoneEt = (TextInputEditText) layout.findViewById(R.id.userphoneET);
        userPassEt = (TextInputEditText) layout.findViewById(R.id.userpassET);

        EmergencyEt = (TextInputEditText) layout.findViewById(R.id.useremergencyET);
        addressEt = (TextInputEditText) layout.findViewById(R.id.useraddresET);

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        /*inputValidation.isInputEditTextFilled(userNameEt,userNameLy,"Enter Valid Name ");
        inputValidation.isInputEditTextEmail(userEmailEt,userEmailLy,"Enter Valid Email ");
        inputValidation.isInputEditTextFilled(userPhoneEt,userPhoneLy,"Enter Valid Name ");
        inputValidation.isInputEditTextFilled(userPassEt,userPassLy,"Enter Valid Name ");
        inputValidation.isInputEditTextMatches(userPassEt,userRPassEt,userRPassLy,"Password Not match ");
        inputValidation.isInputEditTextFilled(EmergencyEt,EmergencyLy,"Enter Valid Name ");
        inputValidation.isInputEditTextFilled(addressEt,addressLy,"Enter Valid Name ");*/


        userEmailEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValidation.isInputEditTextEmail(userEmailEt, userEmailLy, "Enter Valid Email Address");
            }
        });

        CreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name, Email, Phone, Password,Emergency,Address;
                Name = userNameEt.getText().toString();
                Email = userEmailEt.getText().toString();
                Phone = userPhoneEt.getText().toString();
                Password = userPassEt.getText().toString();
                Emergency = EmergencyEt.getText().toString();
                Address = addressEt.getText().toString();

                if (Name.isEmpty()){

                    userNameLy.setError("Enter USer Name");
                }else if (Email.isEmpty()){

                    userEmailLy.setError("Enter User Email");
                }
                else if (Phone.isEmpty()){
                    userPhoneLy.setError("Enter User Phone");
                }else if (Password.isEmpty()){
                    userPassLy.setError("Enter User Password");
                }else if (Emergency.isEmpty()){
                    EmergencyLy.setError("Enter User Emergency Number");
                }else if (Address.isEmpty()){
                    addressLy.setError("Enter User Address");
                }else {
                    boolean em = inputValidation.isInputEditTextEmail(userEmailEt,userEmailLy,"Enter Valid Email");
                    if (em){

                    user = new UserModelClass(Name, Email, Phone, Password, Emergency, Address);
                    boolean id = userDb.createUser(user);
                    if (id) {
                        //Snackbar.make(layout,"Successful",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                    }else {
                        Toast.makeText(MainActivity.this, "UnSuccessful", Toast.LENGTH_SHORT).show();

                    }}else {

                    }

                }
            }

        });


        dialog.show();
    }

    public void logIn(View view) {

        String username = userNameET.getText().toString().trim();
        String userpass = userPassET.getText().toString().trim();
        if (username.isEmpty()){
            userNameET.setError("Enter User Name");
        }else if (userpass.isEmpty()){
            userPassET.setError("Enter Password");
        }else {
                if (username != null && userpass != null ){

                    int id = userDb.login(username,userpass);
                    if (id > 0 & id != 0){
                        logInfo.addStatus(id);
                        /*Toast.makeText(this, "User Id = "+ String.valueOf(id), Toast.LENGTH_SHORT).show();*/
                        startActivity(new Intent(MainActivity.this,EventShowActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }else {Snackbar.make(view, "Wrong User Pass", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();}
                   /*Snackbar.make(view, "User Name: "+username +"\n User Password: "+userpass, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
            }else {
                Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void go(View view) {
        switch (view.getId()){
            case R.id.forcast:
                startActivity(new Intent(MainActivity.this, WeatherActivity.class));
                break;
        }

    }
}
