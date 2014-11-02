package com.example.c.tvtimetable.program;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.c.tvtimetable.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class TVprogramDataSetActivity extends Activity {

    public final static String EXTRA_CHANNEL_ID = "com.example.c.tvtimetable.CHANNELID";

    private ListView listView;
    private ArrayList<TVProgram> items;
    private ArrayAdapter<TVProgram> adapter;

    private String channelID;
    private String[] selections;
    private AlertDialog alertDialog;

    private String displayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvprogram_date_set);

        listView = (ListView) findViewById(R.id.listView);
        items = new ArrayList<TVProgram>();
        adapter = new ArrayAdapter<TVProgram>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO actions for clicking on the programs
            }
        });
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        selections = new String[7];
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-2);
        for(int i=0;i<7;i++){
            selections[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        selections[2] += " (Today)";
        displayDate = selections[2];
        alertDialog = createAlertDialog();

        Intent intent = getIntent();
        channelID = intent.getStringExtra(EXTRA_CHANNEL_ID);

        String today = selections[2].substring(0,10);

        new TVprogramDataSetPost(this,items,adapter,channelID,today).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tvprogram_data_set, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.date) {
            alertDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem dateButton = menu.findItem(R.id.date);
        dateButton.setTitle(displayDate);
        return super.onPrepareOptionsMenu(menu);
    }

    private AlertDialog createAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select");
        builder.setItems(selections,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String date = selections[i].substring(0,10);
                displayDate = selections[i];
                new TVprogramDataSetPost(TVprogramDataSetActivity.this,items,adapter,channelID,date).execute();
                alertDialog.dismiss();
            }
        });
        return builder.create();
    }
}
