package com.shianne.moveshakedrop;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class AddWeight extends ActionBarActivity {

    DBAdapter db = new DBAdapter(this);
    private ListView drawerList;
    private ArrayAdapter<String> navAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String activityTitle;
    private Intent intent;
    EditText weightET;
    TextView weightValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.weightLayout);
        weightET = (EditText) findViewById(R.id.weightVal);
        weightValid = (TextView) findViewById(R.id.addWeightValidation);
        weightET.addTextChangedListener(watch);

        // When the screen is touched outside of the edit field, the keyboard will be hidden
        layout.setOnTouchListener(new View.OnTouchListener(){
            @Override
        public boolean onTouch(View v, MotionEvent event){
                hideKeyboard(v);
                return false;
            }
        });

        drawerList = (ListView) findViewById(R.id.navList);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        activityTitle = getTitle().toString();

        // Create the sliding navigation menu
        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    // Hide the soft keyboard
    protected void hideKeyboard(View view){

        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    TextWatcher watch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if(s.length() == 5){
                Toast.makeText(getApplicationContext(), "Maximum value reached!", Toast.LENGTH_LONG).show();
            }
        }
    };

    // Adds the new weight to the database and displays the Weight History
    public void onClickAddWeight(View view){

        weightET = (EditText) findViewById(R.id.weightVal);
        String weight = weightET.getText().toString();
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerAddWeight);
        String dateWeighed = db.getDateTime(datePicker);

        // If the user leaves the weight value empty or greater than 999.9 lbs, it will show an
        // error and redisplay the activity
        if(weight.equals("") || Float.parseFloat(weight) > 999.9) {
            recreate();
            Toast.makeText(getApplicationContext(), "Field must contain proper values, please!",
                    Toast.LENGTH_LONG).show();
        }else {
            try {
                db.openDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Inserts new weight into table
            db.createWeight(weight, dateWeighed);

            // Displays all the weight values including newest one to Weight History
            startActivity(new Intent(this, WeightHistory.class));
        }
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
                        intent = new Intent(AddWeight.this, AddGoal.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(AddWeight.this, GoalsList.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(AddWeight.this, CompletedGoalsList.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(AddWeight.this, AddWeight.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(AddWeight.this, WeightHistory.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(AddWeight.this, SelectItem.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(AddWeight.this, ItemsHistory.class);
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
        getMenuInflater().inflate(R.menu.menu_add_weight, menu);
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
