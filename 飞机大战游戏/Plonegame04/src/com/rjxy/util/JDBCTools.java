package com.rjxy.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * JDBC工具类
 * @author L
 * JDBC连接MySQL数据库步骤：
 * 1.加载驱动DriverManager
 * 2.创建链接Connection
 * 3.创建Statement对象（SQL语句）prepareastatement
 * 4.执行SQL查寻 execute（）/executrQuery()
 * 5.处理结果集ResultSet
 * 6.关闭数据库连接，释放资源(共有)
 */

public class JDBCTools {
	//获得数据库连接
	public static Connection getConnection() throws Exception {
		Connection connection = null;
		
		//加载驱动
		Class.forName("com.mysql.jdbc.Driver");
		//创建链接
		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "123456");
		
		if(connection!=null){
			System.out.println("创建链接成功");
		}else{
			System.out.println("数据库连接失败");
		}
		return connection;
	}
	//释放数据库资源
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
				System.out.println("数据库连接已断开");
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}
