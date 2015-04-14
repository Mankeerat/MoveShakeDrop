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

public class AddWeight extends ActionBarActivity {

    DBAdapter db = new DBAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);
    }

    public void onClickAddWeight(View view){

        EditText weightET = (EditText) findViewById(R.id.weightVal);
        String weight = weightET.getText().toString();
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerAddWeight);
        String dateWeighed = db.getDateTime(datePicker);

        try{
            db.openDatabase();
        }catch (SQLException e){
            e.printStackTrace();
        }

        // Inserts new weight into table
        db.createWeight(weight, dateWeighed);

        // Displays all the weight values including newest one to Weight History
        startActivity(new Intent(this, WeightHistory.class));
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

        return super.onOptionsItemSelected(item);
    }
}
