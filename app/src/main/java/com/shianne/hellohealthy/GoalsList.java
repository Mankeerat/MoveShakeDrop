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


public class GoalsList extends ActionBarActivity {
/*

    private SimpleCursorAdapter SCAdapter;
    //CustomCursorAdapter CCAdapter;
    private Cursor c;
    private ListView listView;
    private DBAdapter db;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_list);
/*
        db = new DBAdapter(this);

        try{
            db.openDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }

        //if(db.getAllGoals().getCount() == 0)
        //db.insertTesterGoals();

        displayGoals();

        db.closeDatabase();*/
    }
/*

    private void displayGoals(){
        Log.i("DBAdapter", "in displayGoals");
        c = db.getAllGoals();
        Log.i("DBAdapter", "before views in displaygoals");
*/
/*
            listView = (ListView) findViewById(R.id.list_data);
            CCAdapter = new CustomCursorAdapter(this, c);
            listView.setAdapter(CCAdapter);*//*


            String[] cols = new String[]{
                    db.KEY_GOALDESC,
                    db.KEY_DATECOMPLETED
            };

            int[] to = new int[]{
                    R.id.goalDesc,
                    R.id.dateCompleted
            };

            SCAdapter = new SimpleCursorAdapter(
                    this, R.layout.activity_goal_list_single_row, c, cols, to, 0
            );
            listView = (ListView) findViewById(R.id.list_data);
            listView.setAdapter(SCAdapter);
        Log.i("DBAdapter", "after views");
    }
*/

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
