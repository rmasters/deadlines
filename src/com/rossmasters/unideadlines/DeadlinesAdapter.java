package com.rossmasters.unideadlines;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeadlinesAdapter extends ArrayAdapter<Deadline> {
	public int resource;
	public String response;
	public Context context;
	
	public DeadlinesAdapter(Context context, int resource, List<Deadline> items) {
		super(context, resource, items);
		this.resource = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout deadlineView;
		
		Deadline model = getItem(position);
		
		if (convertView == null) {
			deadlineView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi;
			vi = (LayoutInflater) getContext().getSystemService(inflater);
			vi.inflate(resource,deadlineView, true);
		} else {
			deadlineView = (LinearLayout) convertView;
		}
		
		TextView deadlineTimeLeft = (TextView) deadlineView.findViewById(R.id.timeLeft);
		TextView deadlineLabel = (TextView) deadlineView.findViewById(R.id.label);
		
		deadlineTimeLeft.setText(model.getTimeLeftShort());
		deadlineLabel.setText(model.getLabel());
		
		if (model.isPinned()) {
			deadlineView.setBackgroundColor(R.color.pinned);
		} else {
			deadlineView.setBackgroundColor(Color.TRANSPARENT);
		}
		
		return deadlineView;
	}
}
