package com.example.fyp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class FirstPage extends Activity implements OnClickListener {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    Button log_login, log_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

//        mainTextView = (TextView) findViewById(R.id.main_textview);
//        mainTextView.setText("Set in Java!");
        log_login = (Button) findViewById(R.id.log_login);
        log_login.setOnClickListener(this);
//
        log_register = (Button) findViewById(R.id.log_register);
        log_register.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_page, menu);
        return true;
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.log_login:
                Intent logging_in = new Intent(this, Homepage.class);
                EditText enteredusername = (EditText)findViewById(R.id.log_username);
                String username = enteredusername.getText().toString();
                logging_in.putExtra(EXTRA_MESSAGE, username);
                startActivity(logging_in);
                break;

            case R.id.log_register:
                Intent register_page = new Intent(this, Register.class);
                startActivity(register_page);
                break;

            default:
                break;
        }
    }
}
