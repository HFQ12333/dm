package com.rjxy.bean;

public class User {
private String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	//����UserΪ����ģʽ
	private static User uniqueInstance;
	public static User getInstance() {
		if (uniqueInstance ==null) {
			uniqueInstance=new User();
		}
		return uniqueInstance;
	}
	}
