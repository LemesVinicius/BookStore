
package core.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnect {
	
	public static Connection getConnection() 
	throws ClassNotFoundException, 
	SQLException{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://192.168.0.80:3306/mydb";
		String user = "les";
		String password = "administrador";
		Class.forName( driver );
		Connection conn = 
		DriverManager.getConnection( url, user, password);

		return conn;
		
		

	}

}
