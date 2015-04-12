package com.shianne.hellohealthy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.sql.SQLException;


public class AddGoal extends ActionBarActivity {
/*

    DBAdapter db = new DBAdapter(this);
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);




    }

    public void onClickAddGoal(View view){

      /*  EditText goalDescET = (EditText) findViewById(R.id.goalDesc);
        String goalDesc = goalDescET.getText().toString();
        DatePicker datePicker = (DatePicker) findViewById(R.id.dateCompleted);
        String day = String.valueOf(datePicker.getDayOfMonth());
        String month = String.valueOf(datePicker.getMonth() + 1);
        String year = String.valueOf(datePicker.getYear());

        String dateCompleted = year + "-" + month + "-" + day;

        try {
            db.openDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }

        // Inserts new goal into table
        db.createGoal(goalDesc, dateCompleted);

        startActivity(new Intent(this, GoalsList.class));
*/

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
