package com.example.be;

import java.util.Date;

public class Buying {
	private int id;
	private int id_book;
	private String title;
	private int price;
	private String image;
	private int quantity;
	private int state;
	private Date buying;
	private Date receiving;
	
	public Buying() {
		this.id = -1;
		this.id_book = -1;
		this.title = "";
		this.price = 0;
		this.image = "";
		this.quantity = 0;
		this.state = -1;
		this.buying = new Date();
		this.receiving = new Date();
	}
	
	public Buying(int id_book, String title,  String image, int price, int quantity, int state, Date buying, Date receiving) {
		this.id = -1;
		this.id_book = id_book;
		this.title = title;
		this.price = price;
		this.image = image;
		this.quantity = quantity;
		this.state = state;
		this.buying = buying;
		this.receiving = receiving;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_book() {
		return id_book;
	}

	public void setId_book(int id_book) {
		this.id_book = id_book;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getBuying() {
		return buying;
	}

	public void setBuying(Date buying) {
		this.buying = buying;
	}

	public Date getReceiving() {
		return receiving;
	}

	public void setReceiving(Date receiving) {
		this.receiving = receiving;
	}

	@Override
	public String toString() {
		return "Buying [id=" + id + ", id_book=" + id_book + ", title=" + title + ", price=" + price + ", image="
				+ image + ", quantity=" + quantity + ", state=" + state + ", buying=" + buying + ", receiving="
				+ receiving + "]";
	}
}
