package com.v2.memento;

public class Memento {
	// data
	String name;
	int grade ;
	int level  ; 
	int time ;
	
	int maxgrade;
	
	public Memento(String name, int grade, int level, int time) {
		this.name = name;
		this.grade = grade;
		this.level = level;
		this.time = time;
	}

	
	public Memento() {
		// TODO Auto-generated constructor stub
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getGrade() {
		return grade;
	}


	public void setGrade(int grade) {
		this.grade = grade;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public int getTime() {
		return time;
	}


	public void setTime(int time) {
		this.time = time;
	}
	
	
	public int getMaxgrade() {
		return maxgrade;
	}
	
	public void setMaxgrade(int maxgrade) {
		this.maxgrade = maxgrade;
	}
}
