package com.example.sam.tourplan.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sam.tourplan.Users.UserModelClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 5/1/2017.
 */

public class UserDatabase {

    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private UserModelClass user;


    public UserDatabase(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){

        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }



    public boolean createUser(UserModelClass user){

        this.Open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.COL_USERNAME,user.getUserName());
        contentValues.put(dbHelper.COL_USEREMAIL,user.getUserEmail());
        contentValues.put(dbHelper.COL_USERPHONE,user.getUserPhone());
        contentValues.put(dbHelper.COL_USERPASS,user.getUserPsss());
        contentValues.put(dbHelper.COL_EMERGENCYCONTACT,user.getEmergencyContact());
        contentValues.put(dbHelper.COL_ADDRESS,user.getAddress());

        long id = database.insert(dbHelper.TABLE_USER,null,contentValues);

        this.Close();
        if(id > 0){
            return true;
        }else {
            return false;
        }

    }


    public int login(String username, String pass){

        this.Open();

        try{
/*
            Cursor cursor = database.rawQuery("Select " +
                            dbHelper.COL_USERNAME+","+dbHelper.COL_USERPASS+" from "+dbHelper.TABLE_USER
                            +" where "+dbHelper.COL_USERPASS +" = ? AND "+dbHelper.COL_USERPASS +" = ? "
                    ,new String[]{username,pass});*/
            Cursor cursor = database.rawQuery("Select " +
                            dbHelper.COL_ID+" from "+dbHelper.TABLE_USER
                            +" where "+dbHelper.COL_USERPASS +" = ? AND "+dbHelper.COL_USERPASS +" = ? "
                    ,new String[]{username,pass});

            int userId = 0;
            int id = cursor.getCount();
            cursor.moveToFirst();
            if (id != 0 & id > 0){
                for(int i = 0 ; cursor.getCount() > i ;i++){
                    userId = cursor.getInt(cursor.getColumnIndex(dbHelper.COL_ID));
                }
            }

            cursor.close();
            this.Close();
            if(id > 0){

                return userId ;
            }else {
                return 0;
            }


        }catch (Exception e){
            Log.d("Test", "login: ---------- Un successful  "+e);
            return 0;
        }


    }
    /*

         Data From Model Class

               private int id;
               private String userName;
               private String userEmail;
               private String userPhone;
               private String userPsss;
               private String EmergencyContact;
               private String address;

               */
    public UserModelClass getUser(int id) {

        this.Open();

        UserModelClass user= new UserModelClass();
        try {
            Cursor cursor = database.query(dbHelper.TABLE_USER, null, dbHelper.COL_ID + " = "+id, null, null, null, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0 & cursor.getCount() != 0) {
                for (int i = 0; cursor.getCount() > i; i++) {
                    user = new UserModelClass(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_ID)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.COL_USERNAME)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.COL_USEREMAIL)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.COL_USERPHONE)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.COL_USERPASS)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.COL_EMERGENCYCONTACT)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.COL_ADDRESS)));
                }
            }

            cursor.close();
            this.Close();
            int get = cursor.getCount();
            if (get > 0) {
                return user;
            } else {
                return user;
            }
        } catch (Exception e) {
            return user;

        }

    }

    public boolean checkUsername(String user){




            String select = dbHelper.COL_USERNAME + " = ? ";
            String[] strings = {user};

            UserModelClass userc;
            try {
                Cursor cursor = database.query(dbHelper.TABLE_USER, null, select, strings, null, null, null);
                cursor.moveToFirst();
                if (cursor.getCount() > 0 & cursor.getCount() != 0) {
                    for (int i = 0; cursor.getCount() > i; i++) {
                        userc = new UserModelClass(cursor.getInt(cursor.getColumnIndex(dbHelper.COL_ID)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.COL_USERNAME)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.COL_USEREMAIL)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.COL_USERPHONE)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.COL_USERPASS)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.COL_EMERGENCYCONTACT)),
                                cursor.getString(cursor.getColumnIndex(dbHelper.COL_ADDRESS)));
                    }
                }

                int get = cursor.getCount();
                if (get > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;

            }




    }
}
