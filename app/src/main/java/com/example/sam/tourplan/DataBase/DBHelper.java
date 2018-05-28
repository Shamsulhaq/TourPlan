package com.example.sam.tourplan.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by ASUS on 5/1/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tourPlan";
    private static final int DATABASE_VERSION = 1;
    private Context context;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //---------------------- TABLE USER-----------------------
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


    public static final String TABLE_USER = "tblUser";
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "userName";
    public static final String COL_USEREMAIL = "userEmail";
    public static final String COL_USERPHONE = "userPhone";
    public static final String COL_USERPASS = "userPsss";
    public static final String COL_EMERGENCYCONTACT = "EmergencyContact";
    public static final String COL_ADDRESS = "address";

    private static final String userTable = "Create Table "+TABLE_USER+" ( "
            +COL_ID+" Integer primary key, "+COL_USERNAME+" Text, "
            +COL_USEREMAIL+" Text, "+COL_USERPHONE+" Text, "+COL_USERPASS
            +" Text, "+COL_EMERGENCYCONTACT+" Text, "+COL_ADDRESS+" Text);";



    //------------------------------------- Event Table-------------------------
    public static final String TABLE_EVENT = "tblEvent";
    public static final String ECOL_ID = "eventID";
    public static final String ECOL_U_ID = "userID";
    public static final String ECOL_DESTINATION = "destination";
    public static final String ECOL_STARTDATE = "startDate";
    public static final String ECOL_ENDDATE = "endDate";
    public static final String ECOL_BUDGET = "budget";


    private static final String eventTable = "Create Table "+TABLE_EVENT+" ( "
            +ECOL_ID+" Integer primary key, " +ECOL_U_ID +" Text, "
            +ECOL_DESTINATION +" Text, "+ECOL_STARTDATE+" Text, "
            +ECOL_ENDDATE+" Text, "+ECOL_BUDGET+" Text);";


    //--------------------------------------- Expenses Table ----------------------------------
    public static final String TABLE_EXPENS = "tblExpenses";
    public static final String EXCOL_ID = "expensID";
    public static final String EXCOL_E_ID = "eventID";
    public static final String EXCOL_EXPENSES_FOR = "expensesFor";
    public static final String EXCOL_EXPENSES_AMOUTN = "expensesAmount";

    private static final String expensesTable = "Create Table "+TABLE_EXPENS+" ( "
            +EXCOL_ID+" Integer primary key, " +EXCOL_E_ID +" Text, "
            +EXCOL_EXPENSES_FOR+" Text, "+EXCOL_EXPENSES_AMOUTN+" Text);";



    //---------------------------------------------- MEmory Table --------------------------------
    public static final String TABLE_MEMORY = "tblMemory";
    public static final String MCOL_ID = "mID";
    public static final String MCOL_E_ID = "eventID";
    public static final String MCOL_TEXT = "mText";
    public static final String MCOL_IMAGE= "mImage";

    private static final String memoryTable = "Create Table "+TABLE_MEMORY+" ( "
            +MCOL_ID+" Integer primary key, " +MCOL_E_ID +" Text, "
            +MCOL_TEXT+" Text, "+MCOL_IMAGE+" BLOB );";

    //--------------------------------------------------------------------------------------------------

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(userTable);
        sqLiteDatabase.execSQL(eventTable);
        sqLiteDatabase.execSQL(expensesTable);
        sqLiteDatabase.execSQL(memoryTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("Drop table if exist "+TABLE_USER);
        sqLiteDatabase.execSQL("Drop table if exist "+TABLE_EVENT);
        sqLiteDatabase.execSQL("Drop table if exist "+TABLE_EXPENS);
        sqLiteDatabase.execSQL("Drop table if exist "+TABLE_MEMORY);
        onCreate(sqLiteDatabase);

    }
}
