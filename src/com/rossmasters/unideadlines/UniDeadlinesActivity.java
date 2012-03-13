package com.rossmasters.unideadlines;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	final public Handler handler = new Handler();
	
	private Runnable updateCounters = new Runnable() {
		public void run() {
			adapter.notifyDataSetChanged();
			handler.postDelayed(this, 1000);
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView lv = (ListView) findViewById(R.id.listview);
        
        deadlines = new ArrayList<Deadline>();
        adapter = new DeadlinesAdapter(this, R.layout.list_row, deadlines);
        lv.setAdapter(adapter);
        getData();
        
        // Update the counters each second
        Thread t = new Thread() {
        	public void run() {
        		Collections.sort(deadlines, new DeadlineImportanceComparator());
        		handler.post(updateCounters);
        	}
        };
        t.start();
    }
    
    private void getData() {
    	SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	try {
	    	deadlines.add(new Deadline("End of term", dateFmt.parse("2012-03-30 18:00:00"), true));
	        deadlines.add(new Deadline("End of year", dateFmt.parse("2012-05-18 18:00:00"), true));
	        
	        deadlines.add(new Deadline("Send updated ToC and chapter to Martin", dateFmt.parse("2012-03-15 17:00:00")));
	        deadlines.add(new Deadline("SOFT332 evaluation report", dateFmt.parse("2012-03-19 10:00:00")));
	        
	        deadlines.add(new Deadline("Test", dateFmt.parse("2012-03-13 20:13:00")));
    	} catch (ParseException e) {
    		Toast.makeText(this, "Error parsing date formats", Toast.LENGTH_SHORT).show();
    	}
    }
}