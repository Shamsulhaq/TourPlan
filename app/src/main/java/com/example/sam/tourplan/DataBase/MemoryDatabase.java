package com.example.sam.tourplan.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sam.tourplan.Event.Summary.ExpensesModelClass;
import com.example.sam.tourplan.Event.Summary.Memory.MemoryModelClass;

import java.util.ArrayList;

/**
 * Created by ASUS on 5/7/2017.
 */

public class MemoryDatabase {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private MemoryModelClass memory;

    public MemoryDatabase(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){

        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }
    public boolean createMemory(MemoryModelClass expenses){

        this.Open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.MCOL_E_ID,expenses.getEventId());
        contentValues.put(dbHelper.MCOL_TEXT,expenses.getMemoryText());
        contentValues.put(dbHelper.MCOL_IMAGE,expenses.getMemoryImage());


        long id = database.insert(dbHelper.TABLE_MEMORY,null,contentValues);

        this.Close();
        if(id > 0){
            return true;
        }else {
            return false;
        }

    }

    public ArrayList<MemoryModelClass> getAllMemory(int eventID){

        this.Open();


        Cursor cursor = database.query(dbHelper.TABLE_MEMORY,null,
                dbHelper.MCOL_E_ID+" = "+eventID,null,null,null,null);

        ArrayList<MemoryModelClass> memorys = new ArrayList<>();
        MemoryModelClass memory ;
        cursor.moveToFirst();
        if(cursor.getCount() != 0 & cursor.getCount() >0 )
        {
            for (int i = 0 ; cursor.getCount() > i ; i++)
            {
                int id =  cursor.getInt(cursor.getColumnIndex(dbHelper.MCOL_ID));
                int userId = cursor.getInt(cursor.getColumnIndex(dbHelper.MCOL_E_ID));
                String destination = cursor.getString(cursor.getColumnIndex(dbHelper.MCOL_TEXT));
                byte [] image = cursor.getBlob(cursor.getColumnIndex(dbHelper.MCOL_IMAGE));

                memory = new MemoryModelClass(id,userId,destination,image);
                memorys.add(memory);
                cursor.moveToNext();
            }

        }
        cursor.close();
        this.Close();
        return memorys;


    }

    public boolean deleteMemory(int memory){

        this.Open();
        long status = database.delete(dbHelper.TABLE_MEMORY,dbHelper.MCOL_ID+" = ?",new String []{Integer.toString(memory)});
        this.Close();
        if (status > 0){
            return true;
        }else {
            return false;
        }

    }
}
