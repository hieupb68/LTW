package com.example.be;

import java.util.Date;

public class Book {
	private int id;
	private String title;
	private String author;
	private String description;
	private Date release;
	private int pages;
	private String category;
	private String image;
	private int sold;
	private int price;
	
	public Book() {
		this.id = -1;
		this.title = "";
		this.author = "";
		this.description = "";
		this.release = new Date();
		this.pages = -1;
		this.category = "";
		this.image = "";
		this.sold = 0;
		this.price = 0;
	}
	
	public Book(int id, String title, String author, String des, Date release, int pages, String category, int price, String image) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.description = des;
		this.release = release;
		this.pages = pages;
		this.category = category;
		this.price = price;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRelease() {
		return release;
	}

	public void setRelease(Date release) {
		this.release = release;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", description=" + description
				+ ", release=" + release + ", pages=" + pages + ", category=" + category + ", image=" + image
				+ ", sold=" + sold + ", price=" + price + "]";
	}
	
}
