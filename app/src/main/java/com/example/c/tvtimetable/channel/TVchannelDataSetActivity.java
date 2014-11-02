package com.example.c.tvtimetable.channel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c.tvtimetable.R;
import com.example.c.tvtimetable.db.DataSet;
import com.example.c.tvtimetable.program.TVprogramDataSetActivity;

import java.util.ArrayList;
import java.util.List;


public class TVchannelDataSetActivity extends Activity {

    public final static String EXTRA_STATION_ID = "com.example.c.tvtimetable.STATIONID";

    private DataSet dataSet;

    private ListView listView;
    private ArrayList<TVChannel> items;
    private ArrayAdapter<TVChannel> adapter;

    private String stationID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvchannel_data_set);

        listView = (ListView) findViewById(R.id.listView);
        items = new ArrayList<TVChannel>();
        adapter = new ArrayAdapter<TVChannel>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(TVchannelDataSetActivity.this,TVprogramDataSetActivity.class);
                intent.putExtra(TVprogramDataSetActivity.EXTRA_CHANNEL_ID,items.get(i).getTvChannelID());
                startActivity(intent);

            }
        });

        dataSet = new DataSet(this);
        dataSet.open();

        Intent intent = getIntent();
        stationID = intent.getStringExtra(EXTRA_STATION_ID);
        List<TVChannel> values = dataSet.getAllChannelsByStationID(stationID);
        if(!values.isEmpty()){
            items.addAll(values);
            adapter.notifyDataSetChanged();
        }else {
            new TVchannelDataSetPost(this,items,adapter,stationID,dataSet).execute();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tvchannel_data_set, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.refresh) {
            new TVchannelDataSetPost(this,items,adapter,stationID,dataSet).execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        dataSet.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        dataSet.open();
        super.onResume();
    }
}
