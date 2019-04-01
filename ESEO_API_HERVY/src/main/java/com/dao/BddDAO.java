package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class BddDAO {

	public void returnBDD() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/maven?useLegacyDatetimeCode=false&serverTimezone=Europe/Paris&useSSL=false","root","");
			
			Statement stmt = conn.createStatement();
			
			String query = "SELECT * FROM ville_france LIMIT 20";
			ResultSet result = stmt.executeQuery(query);
			while(result.next()) { //Tant qu'il y a des lignes dispos 
				System.out.println("*****************");
				System.out.println(result.getInt(1));
				System.out.println(result.getString(2));
				System.out.println(result.getInt(3));
				System.out.println(result.getString(4));
				System.out.println(result.getString(5));
				System.out.println(result.getInt(6));
			}
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
