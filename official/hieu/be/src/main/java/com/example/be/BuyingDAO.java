package com.example.be;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuyingDAO extends DAO{
	public BuyingDAO() {
		super();
	}
	
	public List<Buying> getAllBuyings(int id) {
		List<Buying> buyings = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "SELECT buying.id as id, book.id as id_book, book.title, book.price, book.image, buying.quantity, buying.state, buying.buying, buying.receiving FROM book, buying,user WHERE buying.id_user = user.id AND buying.id_book = book.id AND user.id = ?;";
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int id_buying = rs.getInt("id");
				int id_book = rs.getInt("id_book");
				String title = rs.getString("title");
				String image = rs.getString("image");
				int price = rs.getInt("price");
				int quantity = rs.getInt("quantity");
				int state = rs.getInt("state");
				Date buyingDate = rs.getDate("buying");
				Date receiving = rs.getDate("receiving");
				
				Buying buying = new Buying(id_book, title, image, price, quantity, state, buyingDate, receiving);
				buying.setId(id_buying);
				buyings.add(buying);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buyings;
	}
	
	public boolean add(Buying buying, int id_user) {
		PreparedStatement ps = null;
		boolean successful = true;
		try {
			String query = "INSERT INTO buying(id_user, id_book, quantity, state, buying, receiving) VALUES (?, ?, ?, 0, ?, ?);";
			ps = con.prepareStatement(query);
			ps.setInt(1, id_user);
			ps.setInt(2, buying.getId_book());
			ps.setInt(3, buying.getQuantity());
			ps.setDate(4, Utils.utilDate2sqlDate(new Date()));
			ps.setDate(5, Utils.utilDate2sqlDate(Utils.getExceptionReceivingDate()));
			
			successful = ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !successful;
	}
	
	public boolean cancel(int id) {
		PreparedStatement ps = null;
		boolean successful = true;
		try {
			String query = "UPDATE buying SET state = -1 WHERE id = ?;";
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			
			successful = ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !successful;
	}
}
