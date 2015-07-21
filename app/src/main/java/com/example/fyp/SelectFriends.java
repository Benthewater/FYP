package com.example.fyp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class SelectFriends extends Activity implements View.OnClickListener{

    ListView friendlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friends);

        friendlist = (ListView) findViewById(R.id.friendselection);
        friendlist.setChoiceMode(friendlist.CHOICE_MODE_MULTIPLE);
        friendlist.setTextFilterEnabled(true);
        LoadFriendList();

        Button CreateNewSession = (Button)findViewById(R.id.createsession);
        CreateNewSession.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_friends, menu);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.createsession:
                Toast.makeText(this, "Session successfully created!", Toast.LENGTH_SHORT).show();
                finish();
                break;

            default:
                break;
        }

    }

    private void LoadFriendList(){
        DBHelper db = new DBHelper(getApplicationContext());

        List<String> friend_list = db.getFriend();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, friend_list);
        friendlist.setAdapter(dataAdapter);
    }
}
