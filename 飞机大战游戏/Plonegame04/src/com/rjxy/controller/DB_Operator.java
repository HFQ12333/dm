package com.rjxy.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rjxy.util.JDBCTools;

public class DB_Operator {
	private Connection conn=null; 
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;

	//定义设置DB_Operator单件模式
	private static DB_Operator db_Operator;
	public static DB_Operator getInstance(){
		if (db_Operator==null) {
			db_Operator=new DB_Operator();
		}
		return db_Operator;
	}
	
	//数据库检查对应用户名是否存在exist（name）
	public boolean exist(String name){
		String sql="select * from user where username=?";
		try {
			conn = JDBCTools.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				System.out.println(rs.getString("username"));
				System.out.println(rs.getString("password"));
				System.out.println("用户已存在");
				return true;
			}else{
				System.out.println("不存在，需要写入数据库");
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(rs, pstmt, conn);
		}
		return false;
	}
	//不存在再添加，注册成功add（name，pass）
	public void add(String name,String pass){
		String sql="insert into user (username,password) values (?,?)";
		try {
			conn = JDBCTools.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, pass);
			pstmt.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(rs, pstmt, conn);
		}
	}
	//去数据库检查对应用户名和密码相同记录是否存在check（name，pass）
	public boolean check(String name,String pass){
		String sql="select * from user where username=? and password=?";
		try {
			conn = JDBCTools.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, pass);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				//读到内容
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(rs, pstmt, conn);
		}
		return false;
	}
	//根据用户名保存记录 等级level 得分grade 累计时间time 最高得分maxgrade
	public void update(String name, int parameter, String type ) {
		String sql1="update user set level=? where username=?";
		String sql2="update user set grade=? where username=?";
		String sql3="update user set time=? where username=?";
		String sql4="update user set maxgrade=? where username=?";
		String sql=null;
		if (type.equals("level")) {
			sql=sql1;
		}else if (type.equals("grade")) {
			sql=sql2;
		}else if (type.equals("time")) {
			sql=sql3;
		}else if (type.equals("maxgrade")) {
			sql=sql4;
		}
		try {
			conn = JDBCTools.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, parameter);
			pstmt.setString(2, name);	
			pstmt.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(rs, pstmt, conn);
		}
	}
	//get方法，根据用户名获取level grade time maxgrade
	public int get(String name, String type ) {
		String sql="select * from user where username=?";
		try {
			conn = JDBCTools.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);	
			rs=pstmt.executeQuery();
			if (rs.next()) {
				if (type.equals("level")) {
					return rs.getInt("level");
				}else if (type.equals("grade")) {
					return rs.getInt("grade");
				}else if (type.equals("time")) {
					return rs.getInt("time");
				}else if (type.equals("maxgrade")) {
					return rs.getInt("maxgrade");
				}
			}
			return -1;//查找失败
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(rs, pstmt, conn);
		}
		return -1;
	}
}
