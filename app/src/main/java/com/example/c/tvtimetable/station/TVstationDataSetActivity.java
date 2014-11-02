package com.example.c.tvtimetable.station;

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
import com.example.c.tvtimetable.channel.TVchannelDataSetActivity;
import com.example.c.tvtimetable.db.DataSet;

import java.util.ArrayList;
import java.util.List;


public class TVstationDataSetActivity extends Activity {

    public final static String EXTRA_AREA_ID = "com.example.c.tvtimetable.AREAID";

    private DataSet dataSet;

    private ListView listView;
    private ArrayList<TVStation> items;
    private ArrayAdapter<TVStation> adapter;

    private String areaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvstation_data_set);

        listView = (ListView) findViewById(R.id.listView);
        items = new ArrayList<TVStation>();
        adapter = new ArrayAdapter<TVStation>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TVstationDataSetActivity.this,TVchannelDataSetActivity.class);
                intent.putExtra(TVchannelDataSetActivity.EXTRA_STATION_ID, items.get(i).getTvStationID());
                startActivity(intent);
            }
        });

        dataSet = new DataSet(this);
        dataSet.open();

        Intent intent = getIntent();
        areaID = intent.getStringExtra(EXTRA_AREA_ID);
        List<TVStation> values = dataSet.getAllStationsByAreaID(areaID);
        if(!values.isEmpty()){
            items.addAll(values);
            adapter.notifyDataSetChanged();
        }else{
            new TVstationDataSetPost(this,items,adapter,areaID,dataSet).execute();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tvstation_data_set, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.refresh) {
            new TVstationDataSetPost(this,items,adapter,areaID,dataSet).execute();
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
