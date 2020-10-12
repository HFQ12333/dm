package com.rjxy.hujie.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBC �Ĺ�����
 * 
 * ���а���: ��ȡ���ݿ�����, �ر����ݿ���Դ�ȷ���.
 */
public class JDBCTools {

	public static Connection getConnection() throws Exception {
		Properties properties = new Properties();
		InputStream inStream = JDBCTools.class.getClassLoader()
				.getResourceAsStream("jdbc.properties");
		properties.load(inStream);

		// 1. ׼����ȡ���ӵ� 4 ���ַ���: user, password, url, jdbcDriver
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		String url= properties.getProperty("url");
		String jdbcDriver= properties.getProperty("jdbcDriver");

		// 2. ��������: Class.forName(driverClass)
		Class.forName(jdbcDriver);

		// 3.��ȡ���ݿ�����
		Connection connection = DriverManager.getConnection(url, user,
				password);
		return connection;
	}

	public static void releaseDB(ResultSet resultSet, Statement statement,
			Connection connection) {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}