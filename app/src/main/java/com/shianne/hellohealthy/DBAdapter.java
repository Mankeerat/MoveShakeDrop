package com.shianne.hellohealthy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Shianne on 4/8/2015.
 */
public class DBAdapter{

    // Tag
    static final String TAG = "DBAdapter";

    // Database Version
    static final int DATABASE_VERSION = 1;

    // Database Name
    static final String DATABASE_NAME = "helloHealthyDB";

    // Table Names
    static final String TABLE_GOAL = "goals";
    static final String TABLE_WEIGHT = "weight";
    static final String TABLE_ITEM = "items";
    static final String TABLE_ENTRY = "entries";
    static final String TABLE_ITEM_ENTRY = "item_entries";

    // Common Column Names
    static final String KEY_ID = "_id";
    static final String KEY_CREATED_AT = "createdAt";

    // Goal Column Names
    static final String KEY_GOALDESC = "goalDesc";
    static final String KEY_DATECOMPLETED = "dateCompleted";

    // Weight Column Names
    static final String KEY_WEIGHT = "weight";
    static final String KEY_DATEWEIGHED = "dateWeighed";

    // Item Column Names
    static final String KEY_ITEM = "item";

    // Entry Column Names
    static final String KEY_ENTRY = "dateEntry";

    // Item_Entry Column Name
    static final String KEY_ITEM_ID = "itemId";
    static final String KEY_ENTRY_ID = "entryId";

    // Table Create Statements
    // Goal Create Statement
    static final String CREATE_TABLE_GOAL =
            "CREATE TABLE " + TABLE_GOAL + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_GOALDESC + " TEXT,"
            + KEY_DATECOMPLETED + " DATETIME,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // Weight Create Statement
    static final String CREATE_TABLE_WEIGHT =
            "CREATE TABLE " + TABLE_WEIGHT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_WEIGHT + " TEXT,"
            + KEY_DATEWEIGHED + " DATETIME" + ")";

    // Item Create Statement
    static final String CREATE_TABLE_ITEM =
            "CREATE TABLE " + TABLE_ITEM + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ITEM + " TEXT" + ")";

    // Entry Create Statement
    static final String CREATE_TABLE_ENTRY =
            "CREATE TABLE " + TABLE_ENTRY + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ENTRY + " TEXT" + ")";

    // Item_Entry Create Statement
    static final String CREATE_TABLE_ITEM_ENTRY =
            "CREATE TABLE " + TABLE_ITEM_ENTRY + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ENTRY_ID + " INTEGER,"
            + KEY_ITEM_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context context){
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){

            try{
                openDatabase();
                // Create tables
                Log.i(TAG, "Before creating tables");
                db.execSQL(CREATE_TABLE_GOAL);
                Log.i(TAG, "creating weight tables");
                db.execSQL(CREATE_TABLE_WEIGHT);
                Log.i(TAG, "creating item tables");
                db.execSQL(CREATE_TABLE_ITEM);
                Log.i(TAG, "creating entry tables");
                db.execSQL(CREATE_TABLE_ENTRY);
                Log.i(TAG, "creating item entry tables");
                db.execSQL(CREATE_TABLE_ITEM_ENTRY);
                Log.i(TAG, "After creating tables");
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int prevVersion, int newVersion){

            // Upgrade will drop old tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAL);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_ENTRY);

            // Create tables
            onCreate(db);
        }
    }

    // Open Database
    public DBAdapter openDatabase() throws SQLException{
        Log.i(TAG, "Before opening database");
        db = DBHelper.getWritableDatabase();
        Log.i(TAG, "After opening database");
        return this;
    }

    // Close Database
    public void closeDatabase(){
            DBHelper.close();
    }



    // -- GOALS
    // Insert
    public long createGoal(String goalDesc, String dateCompleted){

        ContentValues values = new ContentValues();

        values.put(KEY_GOALDESC, goalDesc);
        values.put(KEY_DATECOMPLETED, dateCompleted);
        values.put(KEY_CREATED_AT, getCurrentDateTime());
        return db.insert(TABLE_GOAL, null, values);
    }

    // Retrieve
    public Cursor getAllGoals(){

        return db.query(TABLE_GOAL, new String[]{KEY_ID, KEY_GOALDESC, KEY_DATECOMPLETED,
                KEY_CREATED_AT}, null, null, null, null, KEY_DATECOMPLETED);
    }

    // -- WEIGHT
    // Insert
    public long createWeight(String weight, String dateWeighed){

        ContentValues values = new ContentValues();

        values.put(KEY_WEIGHT, weight);
        values.put(KEY_DATEWEIGHED, dateWeighed);

        return db.insert(TABLE_WEIGHT, null, values);
    }

    // Retrieve
    public Cursor getAllWeight(){

        return db.query(TABLE_WEIGHT, new String[]{KEY_ID, KEY_WEIGHT, KEY_DATEWEIGHED}, null, null, null, null, KEY_DATEWEIGHED);
    }

    // -- ITEM
    // Insert
    public long createItem(String item){

        ContentValues values = new ContentValues();

        values.put(KEY_ITEM, item);

        return db.insert(TABLE_ITEM, null, values);
    }

    // Retrieve
    public Cursor getAllItems(){
        return db.query(TABLE_ITEM, new String[]{KEY_ID, KEY_ITEM}, null, null, null, null, null);
    }

    // -- ENTRY
    // Insert
    public long createEntry(String entry){

        ContentValues values = new ContentValues();

        values.put(KEY_ENTRY, entry);

        return db.insert(TABLE_ENTRY, null, values);
    }

    // Retrieve
    public Cursor getAllEntries(){
        return db.query(TABLE_ENTRY, new String[]{KEY_ID, KEY_ENTRY}, null, null, null, null, null);
    }

    // Retrieve all the items under a single entry
    public Cursor getEntry(String entry){

        String query = "SELECT * FROM "
                + TABLE_ENTRY + "en, "
                + TABLE_ITEM + "it, "
                + TABLE_ITEM_ENTRY + "ie"
                + " WHERE en." + KEY_ENTRY + " = '" + entry + "'"
                + " AND en." + KEY_ID + " = " + "ie." + KEY_ENTRY_ID
                + " AND it." + KEY_ID + " = " + "ie." + KEY_ITEM_ID;

        Cursor entryCursor = db.rawQuery(query, null);

        if(entryCursor != null){
            entryCursor.moveToFirst();
        }
        return entryCursor;
    }

    // -- ITEM_ENTRY
    // Insert
    public long createItemEntry(long itemId, long entryId){

        ContentValues values = new ContentValues();

        values.put(KEY_ENTRY_ID, entryId);
        values.put(KEY_ITEM_ID, itemId);
        values.put(KEY_CREATED_AT, getCurrentDateTime());

        return db.insert(TABLE_ITEM_ENTRY, null, values);
    }

    // Retrieve - might not be needed
    /*public Cursor getAllItemEntries(){
        return db.query(TABLE_ITEM_ENTRY, new String[]{KEY_ID, KEY_ENTRY_ID, KEY_ITEM_ID,
                KEY_CREATED_AT}, null, null, null, null, null);
    }*/

    // Gets today's date
    private String getCurrentDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MMM-dd HH:mm:ss", Locale.getDefault()
        );
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Takes the datepicker value and returns it as a string
    public String getDateTime(DatePicker datePicker){

        String day = String.valueOf(datePicker.getDayOfMonth());
        int m = datePicker.getMonth() + 1;
        final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String month = MONTHS[m];
        String year = String.valueOf(datePicker.getYear());


        return year + "-" + month + "-" + day;
    }

}
