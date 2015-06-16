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


public class ViewFriendlist extends Activity implements View.OnClickListener {

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
        setContentView(R.layout.activity_view_friendlist);

        ListView Friendlist= (ListView) findViewById(R.id.friendlist);
        Friendlist.setChoiceMode(Friendlist.CHOICE_MODE_SINGLE);

        Friendlist.setTextFilterEnabled(true);

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_selectable_list_item, friends);

        Friendlist.setAdapter(adapter);

        Button AddFriend = (Button)findViewById(R.id.addfriend);
        AddFriend.setOnClickListener(this);

        Button Homepage = (Button) findViewById(R.id.homepage);
        Homepage.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_friendlist, menu);
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
        switch(v.getId()){
            case(R.id.addfriend):
                Intent add = new Intent(this, AddFriends.class);
                startActivity(add);
                break;
            case(R.id.homepage):
                finish();
                break;
            default:
                break;
        }
    }
}
