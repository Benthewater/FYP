package com.example.fyp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class Homepage extends Activity implements OnClickListener {

    String Mname;
    Button Create_New_Session, View_Promotions, View_Friendlist, Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        DBHelper mydb = new DBHelper(this);
        Cursor name = mydb.getName(1000);
        name.moveToFirst();
        while(!name.isAfterLast()){
            Mname = name.getString(0);
            name.moveToNext();
        }

        Intent intent = getIntent();
        String message = "Welcome, " + Mname;
        TextView welcometext = (TextView) findViewById(R.id.welcometext);
        welcometext.setText(message);

        Create_New_Session = (Button) findViewById(R.id.createnewsession);
        Create_New_Session.setOnClickListener(this);

        View_Promotions = (Button) findViewById(R.id.viewpromotions);
        View_Promotions.setOnClickListener(this);

        View_Friendlist = (Button) findViewById(R.id.viewfriendlist);
        View_Friendlist.setOnClickListener(this);

        Profile = (Button) findViewById(R.id.profile);
        Profile.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public void onClick(View V){
        switch(V.getId()){
            case R.id.createnewsession:
                Intent cns = new Intent(this, CreateNewSession.class);
                startActivity(cns);
                break;

            case R.id.viewpromotions:
                Intent vp = new Intent(this, ViewPromotions.class);
                startActivity(vp);
                break;

            case R.id.viewfriendlist:
                Intent vfl = new Intent(this, ViewFriendlist.class);
                startActivity(vfl);
                break;

            case R.id.profile:
                Intent pro = new Intent(this, Profile.class);
                startActivity(pro);
                break;

            default:
                break;
        }
    }
}
