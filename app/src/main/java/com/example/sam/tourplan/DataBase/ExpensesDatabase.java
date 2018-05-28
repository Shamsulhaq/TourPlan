package com.example.sam.tourplan.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sam.tourplan.Event.EventModelClass;
import com.example.sam.tourplan.Event.Summary.ExpensesModelClass;

import java.util.ArrayList;

/**
 * Created by ASUS on 5/7/2017.
 */

public class ExpensesDatabase {

    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private ExpensesModelClass expenses;

    public ExpensesDatabase(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){

        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }
    public boolean createExpenses(ExpensesModelClass expenses){

        this.Open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.EXCOL_E_ID,expenses.getEventId());
        contentValues.put(dbHelper.EXCOL_EXPENSES_FOR,expenses.getExpensesFor());
        contentValues.put(dbHelper.EXCOL_EXPENSES_AMOUTN,expenses.getExpensesAmount());


        long id = database.insert(dbHelper.TABLE_EXPENS,null,contentValues);

        this.Close();
        if(id > 0){
            return true;
        }else {
            return false;
        }

    }

    public ArrayList<ExpensesModelClass> getAllExpenses(int eventID){

        this.Open();

        /*Cursor cursor = database.rawQuery("Select *from "+dbHelper.TABLE_EVENT
                        +" where "+dbHelper.COL_EVENT_USER_ID +" = ?"
                ,new String[]{String.valueOf(userID)});*/
        Cursor cursor = database.query(dbHelper.TABLE_EXPENS,null,
                dbHelper.EXCOL_E_ID+" = "+eventID,null,null,null,null);

        ArrayList<ExpensesModelClass> expenseses = new ArrayList<>();
        ExpensesModelClass expenses ;
        cursor.moveToFirst();
        if(cursor.getCount() != 0 & cursor.getCount() >0 )
        {
            for (int i = 0 ; cursor.getCount() > i ; i++)
            {
                int id =  cursor.getInt(cursor.getColumnIndex(dbHelper.EXCOL_ID));
                int userId = cursor.getInt(cursor.getColumnIndex(dbHelper.EXCOL_E_ID));
                String destination = cursor.getString(cursor.getColumnIndex(dbHelper.EXCOL_EXPENSES_FOR));
                int startDate = cursor.getInt(cursor.getColumnIndex(dbHelper.EXCOL_EXPENSES_AMOUTN));

                expenses = new ExpensesModelClass(id,userId,destination,startDate);
                expenseses.add(expenses);
                cursor.moveToNext();
            }

        }
        cursor.close();
        this.Close();
        return expenseses;


    }

    public boolean deleteExpenses(int expensesId){

        this.Open();
        long status = database.delete(dbHelper.TABLE_EXPENS,dbHelper.EXCOL_ID+" = ?",new String []{Integer.toString(expensesId)});
        this.Close();
        if (status > 0){
            return true;
        }else {
            return false;
        }

    }
}
