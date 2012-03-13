package com.rossmasters.unideadlines;

import java.util.Date;

public class Deadline {
	private String label;
	private Date deadline;
	private boolean pinned;
	
	public Deadline(String label, Date date) {
		this(label, date, false);
	}
	
	public Deadline(String label, Date date, boolean pinned) {
		this.label = label;
		this.deadline = date;
		this.pinned = pinned;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public Date getDeadline() {
		return this.deadline;
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
	
	public boolean isPinned() {
		return this.pinned;
	}
}