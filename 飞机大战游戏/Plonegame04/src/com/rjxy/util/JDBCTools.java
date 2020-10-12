package com.rjxy.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * JDBC������
 * @author L
 * JDBC����MySQL���ݿⲽ�裺
 * 1.��������DriverManager
 * 2.��������Connection
 * 3.����Statement����SQL��䣩prepareastatement
 * 4.ִ��SQL��Ѱ execute����/executrQuery()
 * 5.��������ResultSet
 * 6.�ر����ݿ����ӣ��ͷ���Դ(����)
 */

public class JDBCTools {
	//������ݿ�����
	public static Connection getConnection() throws Exception {
		Connection connection = null;
		
		//��������
		Class.forName("com.mysql.jdbc.Driver");
		//��������
		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "123456");
		
		if(connection!=null){
			System.out.println("�������ӳɹ�");
		}else{
			System.out.println("���ݿ�����ʧ��");
		}
		return connection;
	}
	//�ͷ����ݿ���Դ
	public static void releaseDB(ResultSet resultSet,PreparedStatement pstmt,Connection connection){
		if(resultSet !=null){
			try{
				resultSet.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(pstmt !=null){
			try{
				pstmt.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(connection !=null){
			try{
				connection.close();
				System.out.println("���ݿ������ѶϿ�");
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
