package de.bistrosoft.palaver.util;

public class Week {
	private int week;
	private int year;
	
	public Week(int week, int year) {
		this.week=week;
		this.year=year;
	}

	public Week() {
	}

	public int getWeek() {
		return week;
	}
	
	public void setWeek(int week) {
		this.week = week;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
}
