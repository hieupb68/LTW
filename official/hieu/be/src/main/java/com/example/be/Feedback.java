package com.example.be;

import java.util.Date;

public class Feedback {
	private int id;
	private int id_user;
	private String username;
	private int id_book;
	private int rate;
	private String message;
	private Date time;
	
	public Feedback() {
		this.id= -1;
		this.id_user = -1;
		this.username = "";
		this.id_book = -1;
		this.rate = 0;
		this.message = "";
		this.time = new Date();
	}
	
	public Feedback(String username, int id_book, int rate, String message, Date time) {
		this.id = -1;
		this.id_user = -1;
		this.username = username;
		this.id_book = id_book;
		this.rate = rate;
		this.message = message;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId_book() {
		return id_book;
	}

	public void setId_book(int id_book) {
		this.id_book = id_book;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", id_user=" + id_user + ", username=" + username + ", id_book=" + id_book
				+ ", rate=" + rate + ", message=" + message + ", time=" + time + "]";
	}

	
}
