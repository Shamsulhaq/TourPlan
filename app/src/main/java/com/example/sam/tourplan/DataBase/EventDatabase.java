package com.example.sam.tourplan.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.sam.tourplan.Event.EventModelClass;

import java.util.ArrayList;

/**
 * Created by ASUS on 5/6/2017.
 */

public class EventDatabase {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private EventModelClass event;

    public EventDatabase(Context context) {
        dbHelper = new DBHelper(context);

    }
    private void Open(){

        database = dbHelper.getWritableDatabase();
    }
    private void Close(){
        database.close();
    }

    /*
*     private int userId;
private int eventId;
private String destination;
private String startDate;
private String endDate;
private int budget;
*/

    public boolean createEvent(EventModelClass event){

        this.Open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.ECOL_U_ID,event.getUserId());
        contentValues.put(dbHelper.ECOL_DESTINATION,event.getDestination());
        contentValues.put(dbHelper.ECOL_STARTDATE,event.getStartDate());
        contentValues.put(dbHelper.ECOL_ENDDATE,event.getEndDate());
        contentValues.put(dbHelper.ECOL_BUDGET,event.getBudget());

        long id = database.insert(dbHelper.TABLE_EVENT,null,contentValues);

        this.Close();
        if(id > 0){
            return true;
        }else {
            return false;
        }

    }

    public ArrayList<EventModelClass> getAllEvent(int userID){

        this.Open();

        /*Cursor cursor = database.rawQuery("Select *from "+dbHelper.TABLE_EVENT
                        +" where "+dbHelper.COL_EVENT_USER_ID +" = ?"
                ,new String[]{String.valueOf(userID)});*/
        Cursor cursor = database.query(dbHelper.TABLE_EVENT,null,
                dbHelper.ECOL_U_ID+" = "+userID,null,null,null,null);

        ArrayList<EventModelClass> events = new ArrayList<>();
        EventModelClass event ;
        cursor.moveToFirst();
        if(cursor.getCount() != 0 & cursor.getCount() >0 )
        {
            for (int i = 0 ; cursor.getCount() > i ; i++)
            {
                int id =  cursor.getInt(cursor.getColumnIndex(dbHelper.ECOL_ID));
                int userId = cursor.getInt(cursor.getColumnIndex(dbHelper.ECOL_U_ID));
                String destination = cursor.getString(cursor.getColumnIndex(dbHelper.ECOL_DESTINATION));
                String startDate = cursor.getString(cursor.getColumnIndex(dbHelper.ECOL_STARTDATE));
                String endDate = cursor.getString(cursor.getColumnIndex(dbHelper.ECOL_ENDDATE));
                int budget = cursor.getInt(cursor.getColumnIndex(dbHelper.ECOL_BUDGET));
                event = new EventModelClass(id,userId,destination,startDate,endDate,budget);
                events.add(event);
                cursor.moveToNext();
        }

        }
        cursor.close();
        this.Close();
        return events;


    }

    public EventModelClass getEvent(int eventID){

        this.Open();


        Cursor cursor = database.query(dbHelper.TABLE_EVENT,null,
                dbHelper.ECOL_ID+" = "+eventID,null,null,null,null);

        EventModelClass event =new EventModelClass();
        cursor.moveToFirst();
        if(cursor.getCount() != 0 & cursor.getCount() >0 )
        {
            for (int i = 0 ; cursor.getCount() > i ; i++)
            {
                int id =  cursor.getInt(cursor.getColumnIndex(dbHelper.ECOL_ID));
                int userId = cursor.getInt(cursor.getColumnIndex(dbHelper.ECOL_U_ID));
                String destination = cursor.getString(cursor.getColumnIndex(dbHelper.ECOL_DESTINATION));
                String startDate = cursor.getString(cursor.getColumnIndex(dbHelper.ECOL_STARTDATE));
                String endDate = cursor.getString(cursor.getColumnIndex(dbHelper.ECOL_ENDDATE));
                int budget = cursor.getInt(cursor.getColumnIndex(dbHelper.ECOL_BUDGET));
                event = new EventModelClass(id,userId,destination,startDate,endDate,budget);
                cursor.moveToNext();
            }

        }
        cursor.close();
        this.Close();
        return event;


    }
    public boolean deleteEvent(int eventId){

        this.Open();
        long status = database.delete(dbHelper.TABLE_EVENT,dbHelper.ECOL_ID+" = ?",new String []{Integer.toString(eventId)});
        this.Close();
        if (status > 0){
            return true;
        }else {
            return false;
        }

    }


    public boolean upDateEvent(EventModelClass event, int evenid){
        this.Open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.ECOL_DESTINATION,event.getDestination());
        contentValues.put(dbHelper.ECOL_STARTDATE,event.getStartDate());
        contentValues.put(dbHelper.ECOL_ENDDATE,event.getEndDate());
        contentValues.put(dbHelper.ECOL_BUDGET,event.getBudget());

        long id = database.update(dbHelper.TABLE_EVENT,contentValues,dbHelper.ECOL_ID+" = ? "
                ,new String[] {String.valueOf(evenid)});

        this.Close();
        if(id > 0){
            return true;
        }else {
            return false;
        }

    }

}
