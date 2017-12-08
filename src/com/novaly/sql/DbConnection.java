package com.novaly.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
	private static Connection m_Connection;
	private static Statement m_Statement;
	private static ResultSet m_ResultSet;
	
	public static void connect(String url) throws ClassNotFoundException, SQLException
	{
		m_Connection = null;
		Class.forName("org.sqlite.JDBC");
		m_Connection = DriverManager.getConnection(url);
		System.out.println("Подключение установлено");
	}
	
	public static void printTable(String table) throws ClassNotFoundException, SQLException  
	{
		m_ResultSet = m_Statement.executeQuery("SELECT * FROM " + table);
		while (m_ResultSet.next()) {
			int id = m_ResultSet.getInt("Number");
			String name = m_ResultSet.getString("Text");
			System.out.print(id);
			System.out.println(" " + name);
		}
		System.out.println("--- Конец таблицы ---");
	}
	
	public static void closeConnection() throws ClassNotFoundException, SQLException 
	{
		// ! Порядок не менять
		if (null != m_Statement && !m_Statement.isClosed())
			m_Statement.close();
		
		if (null != m_ResultSet && !m_ResultSet.isClosed())
			m_ResultSet.close();
		
		if (null != m_Connection && !m_Connection.isClosed())
			m_Connection.close();
	}
	
	public static void testCreateTable() throws ClassNotFoundException, SQLException
	   {
		m_Statement = m_Connection.createStatement();
		m_Statement.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'phone' INT);");
		
		System.out.println("Таблица создана или уже существует.");
	   }
	
	public static void testWrite() throws SQLException
	{
		   m_Statement.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Petya', 125453); ");
		   m_Statement.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Vasya', 321789); ");
		   m_Statement.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Masha', 456123); ");
		  
		   System.out.println("Таблица заполнена");
	}
	
	public static void testRead() throws ClassNotFoundException, SQLException
	   {
		m_ResultSet = m_Statement.executeQuery("SELECT * FROM users");
		
		while(m_ResultSet.next())
		{
			int id = m_ResultSet.getInt("id");
			String  name = m_ResultSet.getString("name");
			String  phone = m_ResultSet.getString("phone");
	         System.out.println( "ID = " + id );
	         System.out.println( "name = " + name );
	         System.out.println( "phone = " + phone );
	         System.out.println();
		}	
		
		System.out.println("Таблица выведена");
	    }
	
	public static void main(String[] args) {
		String dataBase = "jdbc:sqlite:/home/okhmak/sqlite_db/test_db.db"; 
		try {
			connect(dataBase);
			// ! Порядок не изменять
			testCreateTable();
			testWrite();
			testRead();
			printTable("Main");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
