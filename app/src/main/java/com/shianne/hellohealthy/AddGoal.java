package com.shianne.hellohealthy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.sql.SQLException;


public class AddGoal extends ActionBarActivity {

    DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.goalLayout);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
    }

    protected void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void onClickAddGoal(View view){

        EditText goalDescET = (EditText) findViewById(R.id.goalDescEdit);
        String goalDesc = goalDescET.getText().toString();
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerAddGoal);
        String dateCompleted = db.getDateTime(datePicker);

        try {
            db.openDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }

        // Inserts new goal into table
        db.createGoal(goalDesc, dateCompleted);

        // Displays all the goals including the new one in the Goals List
        startActivity(new Intent(this, GoalsList.class));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_goal, menu);
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
