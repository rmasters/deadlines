package com.rossmasters.unideadlines;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class Deadline {
	private long id;
	private String label;
	private Date deadline;
	private boolean pinned;
	
	private final static String TAG = "DeadlineModel";
	
	public Deadline(long id, String label, Date date, boolean pinned) {
		this.id = id;
		this.label = label;
		this.deadline = date;
		this.pinned = pinned;
	}
	
	public Deadline(String label, Date date, boolean pinned) {
		this(0, label, date, pinned);
	}
	
	public Deadline(String label, Date date) {
		this(0, label, date, false);
	}
	
	public Deadline() {
		this(0, "", new Date(), false);
	}
	
	public long getId() {
		return this.id;
	}
	
	public Deadline setId(long id) {
		this.id = id;
		return this;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public Deadline setLabel(String label) {
		this.label = label;
		return this;
	}
	
	public Date getDeadline() {
		return this.deadline;
	}
	
	public Deadline setDeadline(Date date) {
		this.deadline = date;
		return this;
	}
	
	public Deadline setDeadline(String date) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.deadline = fmt.parse(date);
		} catch (ParseException e) {
			Log.w(TAG, "Failed to parse deadline - must match format " + fmt.toPattern());
		}
		return this;
	}
	
	public boolean isPinned() {
		return this.pinned;
	}
	
	public Deadline setPinned(boolean pinned) {
		this.pinned = pinned;
		return this;
	}
	
	public boolean deadlinePassed() {
		return new Date().getTime() > this.deadline.getTime();
	}
	
	public String getTimeLeftShort() {
		long deadlineTs = this.deadline.getTime() / 1000;
		long nowTs = new Date().getTime() / 1000;
		long diff = deadlineTs - nowTs;
		
		// Check if deadline has passed
		if (diff <= 0) {
			return "Gone!";
		}
		
		int minute = 60;
		double hour = Math.pow(minute, 2);
		double day = hour * 24;
		double week = day * 7;
		double year = week * 52;
		
		if (diff > year) {
			return String.valueOf(Math.round(diff / year)) + "y";
		} else if (diff > week * 2) {
			return String.valueOf(Math.round(diff / week)) + "w";
		} else if (diff > week) {
			long weeks = Math.round(diff / week);
			long days = Math.round((diff - week*weeks) / day);
			return String.valueOf(weeks) + "w " + String.valueOf(days) + "d";
		} else if (diff > day * 3) {
			return String.valueOf(Math.round(diff / day)) + "d";
		} else if (diff > day) {
			long days = (long) Math.floor(diff / day);
			long hours = Math.round((diff - day*days) / hour);
			return String.valueOf(days) + "d " + String.valueOf(hours) + "h";
		} else if (diff > hour * 12) {
			return String.valueOf(Math.round(diff / hour)) + "h";
		} else if (diff > hour) {
			long hours = (long) Math.floor(diff / hour);
			long minutes = Math.round((diff - hour*hours) / minute);
			return String.valueOf(hours) + "h " + String.valueOf(minutes) + "m";
		} else if (diff > minute) {
			long minutes = (long) Math.floor(diff / minute);
			long seconds = (diff - minute*minutes);
			return String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s";
		} else {
			return String.valueOf(diff) + "s";
		}
	}
	
	@Override
	public String toString() {
		return this.label;
	}
}