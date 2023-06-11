package com.example.be;

import java.util.List;

public class BookDetail {
	private Book book;
	private double avg_rate;
	private List<Feedback> fb;
	
	public BookDetail(Book book, List<Feedback> fb) {
		this.book = book;
		this.fb = fb;
		this.avg_rate = 0;
		int r = 0;
		for(Feedback f : fb) {
			this.avg_rate += f.getRate();
			r ++;
		}
		
		this.avg_rate = this.avg_rate/r;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public List<Feedback> getFb() {
		return fb;
	}

	public void setFb(List<Feedback> fb) {
		this.fb = fb;
	}
	
	public double getAvg_rate() {
		return avg_rate;
	}

	public void setAvg_rate(double avg_rate) {
		this.avg_rate = avg_rate;
	}

	@Override
	public String toString() {
		return "BookDetail [book=" + book + ", avg_rate=" + avg_rate + ", fb=" + fb + "]";
	}
}
