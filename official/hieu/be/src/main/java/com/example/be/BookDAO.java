package com.example.be;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDAO extends DAO{
	public BookDAO() {
		super();
	}
	
	
	public List<Book> getBooks() {
		List<Book> books = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "SELECT book.*, status.sold FROM book LEFT JOIN status ON book.id = status.id_book";
			ps = con.prepareStatement(query);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String des = rs.getString("description");
				Date release = rs.getDate("release");
				int pages = rs.getInt("pages");
				String category = rs.getString("category");
				String image = rs.getString("image");
				int sold = rs.getInt("sold");
				int price = rs.getInt("price");
				
				Book book = new Book(id, title, author, des, release, pages, category, price, image);
				book.setSold(sold);
				books.add(book);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return books;
	}
	
	public Book getBookById(int id) {
		Book book = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "SELECT book.*, status.sold FROM book LEFT JOIN status ON book.id = status.id_book WHERE book.id = ?;";
			ps = con.prepareStatement(query);
			ps.setInt(1, id);;
			
			rs = ps.executeQuery();
			while(rs.next()) {
				String title = rs.getString("title");
				String author = rs.getString("author");
				String des = rs.getString("description");
				Date release = rs.getDate("release");
				int pages = rs.getInt("pages");
				String category = rs.getString("category");
				String image = rs.getString("image");
				int sold = rs.getInt("sold");
				int price = rs.getInt("price");

				book = new Book(id, title, author, des, release, pages, category, price, image);
				book.setSold(sold);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return book;
	}
	
	public boolean upload(Book book) {
		PreparedStatement ps = null;
		boolean successful = true;
		try {
			String query_validate = "SELECT COUNT(*) as cnt FROM book WHERE title = ? AND author = ? AND id != ?;";
			ps = con.prepareStatement(query_validate);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setInt(3, book.getId());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt("cnt") > 0)
					return true;
				
			}
			String query = "INSERT INTO book(title, author, description, book.release, pages, category, price, image) VALUES (?,?,?,?,?,?,?,?);";
			ps = con.prepareStatement(query);

			if(book.getId() > 0) {
				query = "UPDATE book SET title = ?, author = ?, description = ?, book.release = ?, pages = ?, category = ?, price = ?, image=? WHERE id = ?;";
				ps = con.prepareStatement(query);
				ps.setInt(9, book.getId());
			}
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getDescription());
			ps.setDate(4, Utils.utilDate2sqlDate(book.getRelease()));
			ps.setInt(5, book.getPages());
			ps.setString(6, book.getCategory());
			ps.setInt(7, book.getPrice());
			ps.setString(8, book.getImage());
			
			successful = ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !successful;
	}
	
	public boolean delete(int id) {
		PreparedStatement ps = null;
		boolean successful = true;
		try {
			String query_validate = "DELETE FROM book WHERE id = ?;";
			ps = con.prepareStatement(query_validate);
			ps.setInt(1, id);
			
			successful = ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !successful;
	}
}
