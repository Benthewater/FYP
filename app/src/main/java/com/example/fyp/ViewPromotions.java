package com.example.fyp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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


public class ViewPromotions extends Activity implements View.OnClickListener {

    ListView PromotionList;
    final Context context = this;
    String selectedpromotion;
    DBHelper db = new DBHelper(this);
    int point, memberpoint, newmemberpoint, memberid;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String REDEEM_URL = "http://www.benfyphangout.net63.net/webservices/redeem.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_promotions);

        memberid = db.getmemberID();
        PromotionList = (ListView)findViewById(R.id.promotions);
        PromotionList.setChoiceMode(PromotionList.CHOICE_MODE_SINGLE);
        PromotionList.setTextFilterEnabled(true);
        LoadPromotionList();
        PromotionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedpromotion = PromotionList.getItemAtPosition(position).toString();
                point = db.getPoints(selectedpromotion);
                dialogbox();
            }
        });

        Button HomePage = (Button) findViewById(R.id.homepage);
        HomePage.setOnClickListener(this);
    }

    private void dialogbox() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_add_friends_pop_up);
        dialog.setTitle("Confirmation");

        TextView text = (TextView) dialog.findViewById(R.id.Pop_up);
        text.setText(selectedpromotion + " \nRequired Points: " +  point);
        Button redeem = (Button) dialog.findViewById(R.id.accept);
        redeem.setText("Redeem");
        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberpoint=db.getmemberpoint();
                if (memberpoint > point) {
                    newmemberpoint = memberpoint - point;
                    new Redeem().execute();
                } else {
                    Toast.makeText(ViewPromotions.this, "Insufficient reward points!", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button cancel = (Button) dialog.findViewById(R.id.decline);
        cancel.setText("Cancel");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.homepage):
                finish();
                break;
            default:
                break;
        }
    }

    private void LoadPromotionList(){
        DBHelper db = new DBHelper(getApplicationContext());
        List<String> Promotion_list = db.getPromotion();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, Promotion_list);
        PromotionList.setAdapter(dataAdapter);

    }

    class Redeem extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewPromotions.this);
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
                params.add(new BasicNameValuePair("points", String.valueOf(newmemberpoint)));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        REDEEM_URL, "POST", params);

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
                Toast.makeText(ViewPromotions.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }
}
