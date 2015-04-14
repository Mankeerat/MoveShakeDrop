package com.shianne.hellohealthy;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.sql.SQLException;


public class WeightHistory extends ActionBarActivity {

    DBAdapter db = new DBAdapter(this);
    Cursor c;
    SimpleCursorAdapter SCAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_history);

        try{
            db.openDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }

        // Retrieve all the weight values from the database
        c = db.getAllWeight();

        // Display the weight values
        displayAllWeight();

        db.closeDatabase();
    }

    private void displayAllWeight(){

        ListView listView = (ListView) findViewById(R.id.weightList);
        String[] from = new String[]{db.KEY_WEIGHT, db.KEY_DATEWEIGHED};
        int[] to = new int[]{R.id.weight, R.id.dateWeighed};
        SCAdapter = new SimpleCursorAdapter(this, R.layout.activity_weight_history_single_row, c, from, to, 0);
        // Inserts the single rows into the ListView section of Weight History
        listView.setAdapter(SCAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weight_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
