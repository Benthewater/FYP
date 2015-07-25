package com.example.fyp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class CreateNewSession extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button NextPage;
    ListView shoplist;
    String selected_shop;
    TextView selected_date;
    int member_ID, shop_ID;

    DBHelper db = new DBHelper(this);

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    private static final String LOGIN_URL = "http://www.benfyphangout.net63.net/webservices/createnewsession.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_session);

        NextPage = (Button) findViewById(R.id.nextpage);
        NextPage.setOnClickListener(this);


        shoplist = (ListView) findViewById(R.id.shopselection);
        LoadShopList();
        shoplist.setOnItemClickListener(this);


        selected_date = (TextView) findViewById(R.id.selecteddate);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_new_session, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so longg
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

        member_ID = db.getmemberID();
        shop_ID = db.getshopID(selected_shop);
        Log.d("Tag A", String.valueOf(member_ID));
        Log.d("Tag B", String.valueOf(shop_ID));
        Log.d("Tag C", selected_shop);
        new CreateSession().execute();
        Bundle localBundle = new Bundle();
        localBundle.putString("Selected_Shop", selected_shop);
        localBundle.putString("Selected_Date", selected_date.getText().toString());
        Intent nextpage = new Intent(this, SelectFriends.class);
        nextpage.putExtras(localBundle);
        startActivity(nextpage);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selected_shop = shoplist.getItemAtPosition(position).toString();
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            ((EditText) getActivity().findViewById(R.id.selecteddate)).setText(year + "-" + (month + 1) + "-" + day);
        }

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void LoadShopList() {
        DBHelper db = new DBHelper(getApplicationContext());

        List<String> shop_list = db.getShop();
        //List<String> shop_list = Arrays.asList("A", "B", "C", "D", "E");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, shop_list);
        shoplist.setChoiceMode(shoplist.CHOICE_MODE_SINGLE);
        shoplist.setTextFilterEnabled(true);
        shoplist.setAdapter(dataAdapter);

    }

    class CreateSession extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CreateNewSession.this);
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
            String date = selected_date.getText().toString();

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("date", date));
                params.add(new BasicNameValuePair("id", String.valueOf(member_ID)));
                params.add(new BasicNameValuePair("shop", String.valueOf(shop_ID)));

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
                Toast.makeText(CreateNewSession.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }
}
