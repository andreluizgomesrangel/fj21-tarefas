package br.com.mobilesaude.jdbc;

import java.sql.*;

public class ConnectionFactory {

	public Connection getConnection() {
	     try {
	         return DriverManager.getConnection(
	 "jdbc:mysql://192.168.25.18/thweb_mob", "root", "");
	     } catch (SQLException e) {
	         throw new RuntimeException(e);
	     }
	 }
	
}
