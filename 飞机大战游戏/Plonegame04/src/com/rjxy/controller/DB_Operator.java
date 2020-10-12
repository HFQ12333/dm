package com.rjxy.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.rjxy.util.JDBCTools;

public class DB_Operator {
	private Connection conn=null; 
	private PreparedStatement pstmt=null;
	private ResultSet rs=null;

	//��������DB_Operator����ģʽ
	private static DB_Operator db_Operator;
	public static DB_Operator getInstance(){
		if (db_Operator==null) {
			db_Operator=new DB_Operator();
		}
		return db_Operator;
	}
	
	//���ݿ����Ӧ�û����Ƿ����exist��name��
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
				System.out.println("�û��Ѵ���");
				return true;
			}else{
				System.out.println("�����ڣ���Ҫд�����ݿ�");
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
	//����������ӣ�ע��ɹ�add��name��pass��
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
	//ȥ���ݿ����Ӧ�û�����������ͬ��¼�Ƿ����check��name��pass��
	public boolean check(String name,String pass){
		String sql="select * from user where username=? and password=?";
		try {
			conn = JDBCTools.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, pass);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				//��������
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
	//�����û��������¼ �ȼ�level �÷�grade �ۼ�ʱ��time ��ߵ÷�maxgrade
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
	//get�����������û�����ȡlevel grade time maxgrade
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
			return -1;//����ʧ��
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(rs, pstmt, conn);
		}
		return -1;
	}
}
