package com.shianne.hellohealthy;


import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.SQLException;

public class GoalsList extends ActionBarActivity {

    DBAdapter db = new DBAdapter(this);
    Cursor c;
    SimpleCursorAdapter SCAdapter;
    int isCompleted = 0;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_list);

        try{
            Log.i("DBAdapter", "Goal list open db");
             db.openDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }
        Log.i("DBAdapter", "before retrieve goals");
        // Retrieve all the goals from the database
        c = db.getAllIncompletedGoals();
        Log.i("DBAdapter", "before listview");
        // Display the goals
        listView = (ListView) findViewById(R.id.list_data);
        String[] from = new String[]{db.KEY_GOALDESC, db.KEY_DATECOMPLETED}; // From database
        int[] to = new int[]{R.id.goalDesc, R.id.dateCompleted}; // To the view
        SCAdapter = new SimpleCursorAdapter(this,R.layout.activity_goal_list_single_row, c, from,
                to, 0);
        Log.i("DBAdapter", "before viewBinder");
        final SimpleCursorAdapter.ViewBinder viewBinder = new SimpleCursorAdapter.ViewBinder(){
            @Override
        public boolean setViewValue( final View view, final Cursor cursor, final int colIndex){

                return false;
            }
        };
        SCAdapter.setViewBinder(viewBinder);
        Log.i("DBAdapter", "end viewBinder section");
        // Inserts the single rows into the ListView section of Goals List
        listView.setAdapter(SCAdapter);


        //listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("DBAdapter", "before onitemclick");
                isCompleted = 1;

                db.updateGoal(id, isCompleted);
                Log.i("DBAdapter", "before recreate()");
                //recreate();
                startActivity(new Intent(getApplicationContext(), CompletedGoalsList.class));
                Toast.makeText(getBaseContext(), "Congratulations!! You completed a goal!",
                        Toast.LENGTH_LONG).show();
                //recreate or after click move to completed goals page
            }
        });

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
