package com.wbh.mymvc.util;

import java.util.Comparator;

import com.wbh.mymvc.servlet.Obstruct;

public class ComparatorObstructUtil implements Comparator<Obstruct> {

	@Override
	public int compare(Obstruct o1, Obstruct o2) {
		return o1.getIndex().compareTo(o2.getIndex());
	}

}
