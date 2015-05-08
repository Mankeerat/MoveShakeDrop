package com.shianne.hellohealthy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.sql.SQLException;
import java.util.ArrayList;


public class SelectItem extends ActionBarActivity {

    DBAdapter db = new DBAdapter(this);
    Cursor c;
    SimpleCursorAdapter SCAdapter;
    private ListView drawerList;
    private ArrayAdapter<String> navAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String activityTitle;
    private Intent intent;
    ArrayList<Long> idArr = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);

        try{
            db.openDatabase();
        }catch(SQLException e){
            e.printStackTrace();
        }

        c = db.getAllItems();

        // This is done to add some items to the list and database on the first run
        if(c.getCount() == 0){
            initialItems();
            c = db.getAllItems();
        }
        displayAllItems();

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


    // After items are selected, this saves the entry to the database
    public void onClickToAddItems(View view){

       Button button = (Button) findViewById(R.id
       .selectItemButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = db.getCurrentDateTime();
                //SparseBooleanArray checked = listView.getCheckedItemPositions();
                ArrayList<String> list = new ArrayList<>();
                long itemId = 0;
                Intent intent = new Intent(getApplicationContext(), ItemList.class);

                try{
                    db.openDatabase();
                }catch(SQLException e){
                    e.printStackTrace();
                }

                // Creates the entry using the date
                long entryId = db.createEntry(date);
                //c = db.getAllItems();

                // Loops through to find the checked boxes
                for(int i = 0; i < listView.getChildCount(); i++){
                    View view = listView.getChildAt(i);
                    CheckedTextView ctv = (CheckedTextView) view.findViewById(R.id
                            .selectItemCheckedTextView);

                    // If an item is checked
                    if(ctv.isChecked()){
                        try {
                            // Retrieve the id for the checked item
                            itemId = db.getItem(ctv.getText().toString());

                            // Add the item to a list
                            list.add(ctv.getText().toString());
                        }catch(SQLException e){
                            e.printStackTrace();
                        }
                        // Create a item-entry record for each item connected to an entry
                        long itemEntryId = db.createItemEntry(itemId, entryId);
                    }
                }
                db.closeDatabase();
                // Add list of items to the intent
                intent.putStringArrayListExtra("selectedItems", list);
                // Starts the Item List activity to show the items just selected
                startActivity(intent);
            }
        });
    }

    // This displays the items in the ListView
    private void displayAllItems(){

        listView = (ListView) findViewById(R.id.selectItemListView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //listView.setTextFilterEnabled(true);
        String[] from = new String[]{db.KEY_ITEM};
        int[] to = new int[]{R.id.selectItemCheckedTextView};
        SCAdapter = new SimpleCursorAdapter(this, R.layout.activity_select_item_single_row, c, from,
                to, 0);
        // Inserts the single rows into the ListView section of Item Selection
        listView.setAdapter(SCAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = listView.getChildAt(position);
                CheckedTextView ctv = (CheckedTextView) v.findViewById(R.id.selectItemCheckedTextView);
                ctv.toggle();
            }
        });
    }

    // On the first run of the app, an items list is added to the database
    private void initialItems(){

        String[] itemListArr = getResources().getStringArray(R.array.selectItemsList);
        for (String anItemListArr : itemListArr) {
            long id = db.createItem(anItemListArr);
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
                        intent = new Intent(SelectItem.this, AddGoal.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(SelectItem.this, GoalsList.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(SelectItem.this, CompletedGoalsList.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(SelectItem.this, AddWeight.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(SelectItem.this, WeightHistory.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(SelectItem.this, SelectItem.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(SelectItem.this, ItemsHistory.class);
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
        getMenuInflater().inflate(R.menu.menu_select_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            /*case R.id.action_search:
                openSearch();
                return true;*/
            case R.id.action_new:
                // create a input box for adding new items to list
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View promptView = inflater.inflate(R.layout.activity_add_item_to_list, null);
                AlertDialog.Builder adb = new AlertDialog.Builder(SelectItem.this);

                adb.setTitle(R.string.title_activity_add_item_to_list)
                   .setMessage(R.string.addItemDesc)
                   .setView(promptView);
                final EditText input = (EditText) promptView.findViewById(R.id.addedItem);
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            db.openDatabase();
                        }catch(SQLException e){
                            e.printStackTrace();
                        }
                        db.createItem(input.getText().toString());
                        recreate();
                    }
                })
                   .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
            }

                   }).show();

                return true;
            default:
                return super.onOptionsItemSelected(item) || drawerToggle.onOptionsItemSelected(item);
        }
    }

}
