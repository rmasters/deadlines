package com.rossmasters.unideadlines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

public class UniDeadlinesActivity extends Activity {
	private List<Deadline> deadlines;
	private DeadlinesAdapter adapter;
	private DeadlinesDAO deadlinesDao;
	
	final private Handler handler = new Handler();
	final private Runnable updateItems = new Runnable() {
		public void run() {
			Collections.sort(deadlines, new DeadlineImportanceComparator());
    		adapter.notifyDataSetChanged();
    		handler.postDelayed(updateItems, 1000);
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView lv = (ListView) findViewById(R.id.listview);
        
        deadlinesDao = new DeadlinesDAO(this);
        deadlinesDao.open();
        
        deadlines = new ArrayList<Deadline>();
        adapter = new DeadlinesAdapter(this, R.layout.list_row, deadlines);
        lv.setAdapter(adapter);
        getData();
        
        // Update the counters each second
		handler.postDelayed(updateItems, 1000);
    }
    
    @Override
    protected void onResume() {
    	deadlinesDao.open();
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	deadlinesDao.close();
    	super.onPause();
    }
    
    private void getData() {
    	deadlines.addAll(deadlinesDao.fetchAll());
    	adapter.notifyDataSetChanged();
    }
}