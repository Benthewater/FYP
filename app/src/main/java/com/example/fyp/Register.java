package com.example.fyp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Register extends Activity implements View.OnClickListener {

    private EditText user, pass;

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    private static final String LOGIN_URL = "http://www.benfyphangout.net63.net/webservices/register.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = (EditText) findViewById(R.id.reg_username);
        pass = (EditText) findViewById(R.id.reg_password);

        Button register = (Button) findViewById(R.id.reg_Register);
        register.setOnClickListener(this);

    }

        @Override
        public void onClick(View v) {

            new CreateUser().execute();
        }

        class CreateUser extends AsyncTask<String, String, String> {

            /**
             * Before starting background thread Show Progress Dialog
             * */
            boolean failure = false;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Register.this);
                pDialog.setMessage("Creating User...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {
                // TODO Auto-generated method stub
                // Check for success tag
                int success;
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
                        Log.d("User Created!", json.toString());
                        finish();
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
                    Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
                }

            }

        }

    }
