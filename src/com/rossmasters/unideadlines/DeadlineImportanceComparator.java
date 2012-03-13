package com.rossmasters.unideadlines;

import java.util.Comparator;

public class DeadlineImportanceComparator implements Comparator<Deadline> {
	public int compare(Deadline d1, Deadline d2) {
		// If either is pinned (compare by due-date if they are both pinned)
		if (d1.isPinned() || d2.isPinned() && !(d1.isPinned() && d2.isPinned())) {
			return d1.isPinned() ? -1 : 1;
		} else {
			return d1.getDeadline().compareTo(d2.getDeadline());
		}
	}
}