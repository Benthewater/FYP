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


public class SelectFriends extends Activity implements View.OnClickListener{

    String[] friends= {
            "friend 1",
            "friend 2",
            "friend 3",
            "friend 4",
            "friend 5",
            "friend 6",
            "friend 7",
            "friend 8"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friends);

        ListView listview= (ListView) findViewById(R.id.friendselection);
        listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);

        listview.setTextFilterEnabled(true);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_checked, friends);

        listview.setAdapter(adapter);

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
}
