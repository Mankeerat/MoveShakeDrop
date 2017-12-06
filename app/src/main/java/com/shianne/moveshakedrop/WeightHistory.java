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

import java.sql.SQLException;


public class WeightHistory extends ActionBarActivity {

    DBAdapter db = new DBAdapter(this);
    Cursor c;
    SimpleCursorAdapter SCAdapter;
    private ListView drawerList;
    private ArrayAdapter<String> navAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String activityTitle;
    private Intent intent;
    ListView listView;

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

        drawerList = (ListView) findViewById(R.id.navList);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        activityTitle = getTitle().toString();

        // Creates the sliding navigation menu
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    // Displays all weight values using the simple cursor adapter
    private void displayAllWeight(){

        listView = (ListView) findViewById(R.id.weightList);
        String[] from = new String[]{db.KEY_WEIGHT, db.KEY_DATEWEIGHED};
        int[] to = new int[]{R.id.weight, R.id.dateWeighed};
        SCAdapter = new SimpleCursorAdapter(this, R.layout.activity_weight_history_single_row, c, from, to, 0);
        // Inserts the single rows into the ListView section of Weight History
        listView.setAdapter(SCAdapter);
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
                        intent = new Intent(WeightHistory.this, AddGoal.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(WeightHistory.this, GoalsList.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(WeightHistory.this, CompletedGoalsList.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(WeightHistory.this, AddWeight.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(WeightHistory.this, WeightHistory.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(WeightHistory.this, SelectItem.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(WeightHistory.this, ItemsHistory.class);
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

        return super.onOptionsItemSelected(item) || drawerToggle.onOptionsItemSelected(item);
    }
}
