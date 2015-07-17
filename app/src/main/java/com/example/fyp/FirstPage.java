package com.example.fyp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FirstPage extends Activity implements OnClickListener {

    private EditText user, pass;

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    private static final String LOGIN_URL = "http://www.benfyphangout.net63.net/webservices/login.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_POINT = "member_point";
    private static final String TAG_NAME = "member_name";

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        mydb = new DBHelper(this);

        user = (EditText) findViewById(R.id.log_username);
        pass = (EditText) findViewById(R.id.log_password);

        Button log_login = (Button) findViewById(R.id.log_login);
        log_login.setOnClickListener(this);

        Button log_register = (Button) findViewById(R.id.log_register);
        log_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.log_login:
                new LoginUser().execute();
                break;

            case R.id.log_register:
                Intent register_page = new Intent(this, Register.class);
                startActivity(register_page);
                break;

            default:
                break;
        }
    }

    class LoginUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FirstPage.this);
            pDialog.setMessage("Logging in...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            Intent logging_in;
            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                //full json response
                Log.d("Login attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Success!", json.toString());
                    logging_in = new Intent(FirstPage.this, Homepage.class);
                    EditText enteredusername = (EditText)findViewById(R.id.log_username);
                    username = enteredusername.getText().toString();
                    logging_in.putExtra(EXTRA_MESSAGE, username);
                    startActivity(logging_in);
                    finish();
                    mydb.StoreMemberInfo(json.getString(TAG_NAME), Integer.valueOf(json.getString(TAG_POINT).toString()));
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(FirstPage.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }
}
