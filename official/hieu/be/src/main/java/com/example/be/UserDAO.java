package com.example.be;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO extends DAO{
	public UserDAO() {
		super();
	}
	
	public boolean isExistUser(String username) {
		PreparedStatement ps = null;
		boolean isExist = false;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM user WHERE user.username = ?;";
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				isExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isExist;
	}
	
	public User login(String username, String password) {
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		User loginUser = new User();
		loginUser.setUsername(username);
		try {
			String query = "SELECT id, fullname, position FROM user WHERE user.username = ? AND user.password = ?;";
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				loginUser.setId(resultSet.getInt("id"));
				loginUser.setFullname(resultSet.getString("fullname"));
				loginUser.setPosition(resultSet.getString("position"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginUser;
	}
	
	public boolean signUp(String username, String password, String fullname) {
		PreparedStatement ps = null;
		boolean successful = true;
		try {
			String query = "INSERT INTO user(username, password, fullname, position) VALUES (?,?,?, 'user');";
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, fullname);

			successful = ps.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !successful;
	}
}
