package com.example.be;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO extends DAO{
	
	public List<Feedback> getFb(int id_book) {
		List<Feedback> fbs = new ArrayList<Feedback>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "SELECT feedback.id, user.fullname as name, id_book, rate, message, feedback.time FROM feedback JOIN user ON user.id = id_user AND id_book = ?;";
			ps = con.prepareStatement(query);
			ps.setInt(1, id_book);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Feedback fb = new Feedback(rs.getString("name"), rs.getInt("id_book"), rs.getInt("rate"), rs.getString("message"), rs.getDate("time"));
				fb.setId(rs.getInt("id"));
				fbs.add(fb);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fbs;
	}
	
	public boolean addFb(Feedback fb) {
		PreparedStatement ps = null;
		boolean successful = true;
		try {
			String query_validate = "SELECT COUNT(*) as cnt FROM book WHERE title = ? AND author = ? AND id != ?;";
			ps = con.prepareStatement(query_validate);
			
			String query = "INSERT INTO feedback(id_user, id_book, rate, message, feedback.time) VAlUES (?, ?, ?, ?, ?);";
			ps = con.prepareStatement(query);
			ps.setInt(1, fb.getId_user());
			ps.setInt(2, fb.getId_book());
			ps.setInt(3, fb.getRate());
			ps.setString(4, fb.getMessage());
			ps.setDate(5, Utils.utilDate2sqlDate(fb.getTime()));
			
			
			successful = ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !successful;
	}
}
