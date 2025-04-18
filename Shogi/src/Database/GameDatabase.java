package Database;

import java.io.*;
import java.sql.*;
import java.util.*;


public class GameDatabase
{
  private Connection conn;
  //Add any other data fields you like – at least a Connection object is mandatory
  public GameDatabase()
  {
	  // Create properties object
	  Properties prop = new Properties();
	  FileInputStream fis = null;
	  
	  // open the db.properties with FileInputStream
	  try
	  {
		  fis = new FileInputStream("Database/db.properties");
	  }
	  catch(FileNotFoundException e)
	  {
		  e.printStackTrace();
	  }
	  
	  //parse the properties file (key/value)
	  try {
		prop.load(fis);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  String url = prop.getProperty("url");
	  String user = prop.getProperty("user");
	  String pass = prop.getProperty("password");
	  
	  try
	  {
		  conn = DriverManager.getConnection(url, user, pass);
	  }
	  catch(SQLException e)
	  {
		  e.printStackTrace();
	  }
  }
  
  public ArrayList<String> query(String query)
  {
	  //Declare the return value first
	  ArrayList<String> list = new ArrayList<String>();
	  String record = "";
	  
	  // Process the query
	  try
	  {
		  // 1. Create a Statement object
		  Statement stmt = conn.createStatement();
		  
		  // 2. Execute the Query on the stmt
		  ResultSet rs = stmt.executeQuery(query);
		  
		  // 3. Get the metadata
		  ResultSetMetaData rsmd = rs.getMetaData();
		  
		  int noFields = rsmd.getColumnCount();
		  
		  // 4. Iterate through each record
		  while(rs.next()) 
		  {
			  record = "";
			 for(int i = 0; i < noFields; i++)
			 {
				 record += rs.getString(i + 1);
				 record += ",";
			 }
			 list.add(record);
		  }
		  //Check for empty
		  if(list.isEmpty())
		  {
			  return null;
		  }
	  }
	  catch(SQLException e)
	  {
		  return null;
	  }
	  
	  return list;
	  
  }
  
  public void executeDML(String dml) throws SQLException
  {
	  // Create a statement from the connection
	  Statement stmt = conn.createStatement();
	  
	  // Execute the DML
	  stmt.execute(dml);
  }
  
  public boolean usernameExists(String username) {
	    String query = "SELECT * FROM User WHERE username = '" + username + "'";
	    ArrayList<String> result = query(query);
	    return result != null;
	}
  
  public boolean verifyUser(String username, String password) {
	    String encryptionKey = "key";
	    String query = "SELECT * FROM User WHERE username = '" + username + 
	                   "' AND password = AES_ENCRYPT('" + password + "', '" + encryptionKey + "')";
	    ArrayList<String> result = query(query);
	    return result != null;
	}
  
  public boolean createUser(String username, String password) {
	    String encryptionKey = "key";
	    String dml = "INSERT INTO User (username, password) VALUES ('" + username + "', AES_ENCRYPT('" + password + "', '" + encryptionKey + "'))";
	    try {
	        executeDML(dml);
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
  
  
}

