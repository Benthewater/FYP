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


public class ViewPromotions extends Activity implements View.OnClickListener {

    String[] promotions = {
            "Promotion 1",
            "Promotion 2",
            "Promotion 3",
            "Promotion 4",
            "Promotion 5",
            "Promotion 6",
            "Promotion 7"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_promotions);

        ListView PromotionList = (ListView)findViewById(R.id.promotions);
        PromotionList.setChoiceMode(PromotionList.CHOICE_MODE_SINGLE);

        PromotionList.setTextFilterEnabled(true);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_selectable_list_item, promotions);
        PromotionList.setAdapter(adapter);

        Button HomePage = (Button) findViewById(R.id.homepage);
        HomePage.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_promotions, menu);
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
            case(R.id.homepage):
                finish();
                break;
            default:
                break;
        }
    }
}
