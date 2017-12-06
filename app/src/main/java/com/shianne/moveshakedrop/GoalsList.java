package com.shianne.moveshakedrop;


import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private ListView drawerList;
    private ArrayAdapter<String> navAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String activityTitle;
    private Intent intent;

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
        c = db.getAllIncompletedGoals();

        // Display the goals
        listView = (ListView) findViewById(R.id.list_data);
        String[] from = new String[]{db.KEY_GOALDESC, db.KEY_DATECOMPLETED}; // From database
        int[] to = new int[]{R.id.goalDesc, R.id.dateCompleted}; // To the view

        // Create a simple cursor adapter to display the goal list
        SCAdapter = new SimpleCursorAdapter(this, R.layout.activity_goal_list_single_row, c, from,
                to, 0);

        final SimpleCursorAdapter.ViewBinder viewBinder = new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(final View view, final Cursor cursor, final int colIndex) {

                return false;
            }
        };
        SCAdapter.setViewBinder(viewBinder);

        // Inserts the rows into the ListView section of Goals List
        listView.setAdapter(SCAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                isCompleted = 1;

                // Updates the goal and displays the completed goals list activity
                db.updateGoal(id, isCompleted);
                startActivity(new Intent(getApplicationContext(), CompletedGoalsList.class));
                Toast.makeText(getBaseContext(), "Congratulations!! You completed a goal!",
                        Toast.LENGTH_LONG).show();
                }
            });

        drawerList = (ListView) findViewById(R.id.navList);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        activityTitle = getTitle().toString();

        // Creates the sliding navigation menu
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    // Adds each item to the sliding menu
    private void addDrawerItems(){

        String[] listArr = getResources().getStringArray(R.array.navItems);
        navAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listArr);
        drawerList.setAdapter(navAdapter);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerList.setItemChecked(position, true);
                switch(position){
                    case 0:
                        intent = new Intent(GoalsList.this, AddGoal.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(GoalsList.this, GoalsList.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(GoalsList.this, CompletedGoalsList.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(GoalsList.this, AddWeight.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(GoalsList.this, WeightHistory.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(GoalsList.this, SelectItem.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(GoalsList.this, ItemsHistory.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    // Decides what to display when sliding menu is open or closed
    private void setupDrawer(){

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawerOpen,
                R.string.drawerClose){

            public void onDrawerOpened(View drawerView){

                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view){

                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(activityTitle);
                invalidateOptionsMenu();
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
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

        return super.onOptionsItemSelected(item) || drawerToggle.onOptionsItemSelected(item);
    }
}
