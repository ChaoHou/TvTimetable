package com.example.c.tvtimetable.area;

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
import com.example.c.tvtimetable.station.TVstationDataSetActivity;

import java.util.ArrayList;
import java.util.List;


public class AreaDataSetActivity extends Activity {

    private DataSet dataSet;

    private ListView listView;

    private ArrayList<Area> items;
    private ArrayAdapter<Area> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_data_set);

        listView = (ListView) findViewById(R.id.listView);
        items = new ArrayList<Area>();
        adapter = new ArrayAdapter<Area>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(AreaDataSetActivity.this,TVstationDataSetActivity.class);
                intent.putExtra(TVstationDataSetActivity.EXTRA_AREA_ID,items.get(i).getAreaID());
                startActivity(intent);

            }
        });
        dataSet = new DataSet(this);
        dataSet.open();

        List<Area> values = dataSet.getAllAreas();
        if(!values.isEmpty()){
            items.addAll(values);
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.area_data_set, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.refresh) {
            new AreaDataSetPost(AreaDataSetActivity.this,items,adapter, dataSet).execute();
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
