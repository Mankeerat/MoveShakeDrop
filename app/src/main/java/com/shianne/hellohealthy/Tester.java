package com.shianne.hellohealthy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

// This file is temporary for easy access to each activity from the main page.
public class Tester extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);
    }

// Temporary links to all activities
    public void clickAddGoal(View view){
        Intent intent = new Intent(this, AddGoal.class);
        startActivity(intent);
    }
    public void clickAddItemtoList(View view){
        Intent intent = new Intent(this, AddItemToList.class);
        startActivity(intent);
    }
    public void clickGoalList(View view){
        Intent intent = new Intent(this, GoalsList.class);
        startActivity(intent);
    }
    public void clickItemList(View view){
        Intent intent = new Intent(this, ItemList.class);
        startActivity(intent);
    }
    public void clickAddWeight(View view){
        Intent intent = new Intent(this, AddWeight.class);
        startActivity(intent);
    }
    public void clickItemHistory(View view){
        Intent intent = new Intent(this, ItemsHistory.class);
        startActivity(intent);
    }
    public void clickSelectItem(View view){
        Intent intent = new Intent(this, SelectItem.class);
        startActivity(intent);
    }
    public void clickWeightHistory(View view){
        Intent intent = new Intent(this, WeightHistory.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tester, menu);
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
