package com.dgit.ex03;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;


public class MySqlConnectorTest {
	private static final String Driver="com.mysql.jdbc.Driver";
	private static final String URL="jdbc:mysql://127.0.0.1:3306/book_ex";
	private static final String USER="root";
	private static final String PW="rootroot";
	
	@Test
	public void testConnection() throws ClassNotFoundException{
		Class.forName(Driver);
		
		Connection conn=null;
		
		try{
			conn= DriverManager.getConnection(URL,USER,PW);
			System.out.println(conn);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
