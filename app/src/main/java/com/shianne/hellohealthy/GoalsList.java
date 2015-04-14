package com.shianne.hellohealthy;


import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.sql.SQLException;

// NOTE: ADD CHECKBOX FOR ISCOMPLETED SECTION
public class GoalsList extends ActionBarActivity {

    DBAdapter db = new DBAdapter(this);
    Cursor c;
    SimpleCursorAdapter SCAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_list);

        try{
            db.openDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }

        // Retrieve all the goals from the database
        c = db.getAllGoals();

        // Display the goals
        displayAllGoals();

        db.closeDatabase();
    }

    private void displayAllGoals(){

        ListView listView = (ListView) findViewById(R.id.list_data);
        String[] from = new String[]{db.KEY_GOALDESC, db.KEY_DATECOMPLETED}; // From database
        int[] to = new int[]{R.id.goalDesc, R.id.dateCompleted}; // To the view
        SCAdapter = new SimpleCursorAdapter(this,R.layout.activity_goal_list_single_row, c, from,
                to, 0);
        // Inserts the single rows into the ListView section of Goals List
        listView.setAdapter(SCAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_goals_list, menu);
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
