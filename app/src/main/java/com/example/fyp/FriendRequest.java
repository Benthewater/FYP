package com.example.fyp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FriendRequest extends Activity {

    ListView friendrequest;
    final Context context = this;
    String selectedrequest;
    int requestid, memberid;
    DBHelper db = new DBHelper(this);


    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL_ACCEPT = "http://www.benfyphangout.net63.net/webservices/acceptrequest.php";
    private static final String LOGIN_URL_REJECT = "http://www.benfyphangout.net63.net/webservices/rejectrequest.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        friendrequest = (ListView) findViewById(R.id.friendrequestlist);
        friendrequest.setChoiceMode(friendrequest.CHOICE_MODE_SINGLE);
        friendrequest.setTextFilterEnabled(true);
        loadfriendrequest();

        friendrequest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedrequest = friendrequest.getItemAtPosition(position).toString();
                requestid = db.getFriendRequestID(selectedrequest);
                memberid = db.getmemberID();
                dialogbox();
            }
        });

    }

    private void dialogbox() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_add_friends_pop_up);
        dialog.setTitle("Add Friend");

        TextView text = (TextView) dialog.findViewById(R.id.Pop_up);
        text.setText(selectedrequest);
        Button accept = (Button) dialog.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Accept().execute();
            }
        });
        Button decline = (Button) dialog.findViewById(R.id.decline);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Reject().execute();
            }
        });

        dialog.show();
    }

    private void loadfriendrequest(){
        DBHelper db = new DBHelper(getApplicationContext());
        List<String> friend_request_list = db.getFriendRequest();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, friend_request_list);
        friendrequest.setAdapter(dataAdapter);
    }

    class Accept extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FriendRequest.this);
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

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("member_id", String.valueOf(memberid)));
                params.add(new BasicNameValuePair("friend_id", String.valueOf(requestid)));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL_ACCEPT, "POST", params);

                //full json response
                Log.d("Login attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(FriendRequest.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }

    class Reject extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FriendRequest.this);
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

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("member_id", String.valueOf(memberid)));
                params.add(new BasicNameValuePair("friend_id", String.valueOf(requestid)));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL_REJECT, "POST", params);

                //full json response
                Log.d("Login attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(FriendRequest.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }
}

