package com.rjxy.hujie.controler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

import com.mysql.jdbc.Connection;
import com.rjxy.hujie.util.JDBCTools;

public class DB_Operater {
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet resultSet = null;
	// judge the db has a user's information by name and password 
		public boolean exist(String name) {
			String sql = "select * from user where user.username='"+name+"'";
			try {
				connection = (Connection) JDBCTools.getConnection();
				pstmt = connection.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				// 如果查询的ResultSet里有超过一条的记录，则登录成功
				if (rs.next()) {
					System.out.println(rs.getString("username"));
					System.out.println(rs.getString("level"));
					System.out.println(rs.getString("grade"));
					return true;
				}
				return false;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JDBCTools.releaseDB(resultSet, pstmt, connection);
			}
			return false;
		}
	// judge the db has a user's information by name and password 
	public boolean check(String name, String pswd) {
		String sql = "select * from user where user.username='"+name+"' and user.password='"+pswd+"'";
		try {
			connection = (Connection) JDBCTools.getConnection();
			pstmt = connection.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			// 如果查询的ResultSet里有超过一条的记录，则登录成功
			if (rs.next()) {
				System.out.println(rs.getString("username"));
				System.out.println(rs.getString("password"));
				System.out.println(rs.getString("level"));
				System.out.println(rs.getString("grade"));
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, pstmt, connection);
		}
		return false;
	}
	// update the user's level and grade
	public void update(String name, int level, int grade) {
		String sql = "update user set level=?,grade=? where username=?";
		try {
			connection = (Connection) JDBCTools.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(sql); 
			pstmt.setInt(1, level);
			pstmt.setInt(2, grade);
			pstmt.setString(3, name);
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, pstmt, connection);
		}
	}
	// uodate time by name
	public void update(String name, String parameter, String type) {
		String sql1 = "update user set time=? where username=?";
		String sql2 = "update user set blood=? where username=?";
		String sql3 = "update user set maxgrade=? where username=?";
		try {
			connection = (Connection) JDBCTools.getConnection();
			PreparedStatement pstmt = null;
			if (type.equals("time")) {
				pstmt = connection.prepareStatement(sql1);
			} 
			else if (type.equals("blood")) {
				pstmt = connection.prepareStatement(sql2);
			}
			else if (type.equals("maxgrade")) {
				pstmt = connection.prepareStatement(sql3);
			}
			pstmt.setInt(1, Integer.parseInt(parameter));
			pstmt.setString(2, name);
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, pstmt, connection);
		}
	}
	
	// add an new user's information
	public void add(String name, String pswd) {
		String sql="insert into user values(?,?,?,?,?,?,?)";
		try {
			connection = (Connection) JDBCTools.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(sql); 
			pstmt.setString(1, name);  
			pstmt.setString(2, pswd);
			pstmt.setInt(3, 0);
			pstmt.setInt(4, 0);
			pstmt.setInt(5, 0);
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			pstmt.execute();  
			System.out.println("注册成功请登录");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, pstmt, connection);
		}
	}

	// get grade or level by name
	public int get(String name, String type) {
		String sql = "select * from user where username=?";
		try {
			connection = (Connection) JDBCTools.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(sql); 
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			// 如果查询的ResultSet里有超过一条的记录，则登录成功
			if (rs.next()) {
				if (type.equals("level")) {
					return rs.getInt("level");
				} 
				else if (type.equals("grade")) {
					return rs.getInt("grade");
				}
				else if (type.equals("time")) {
					return rs.getInt("time");
				}
				else if (type.equals("blood")) {
					return rs.getInt("blood");
				}
				else if (type.equals("maxgrade")) {
					return rs.getInt("maxgrade");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, pstmt, connection);
		}
		return 0;
	}
}
