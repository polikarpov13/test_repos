package polikarpov.lesson5.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.xml.DOMConfigurator;

public class ConnectionUtils {
	private static String userName = "root";
	private static String userPassword = "Barec20082004";
	private static String URL = "jdbc:mysql://localhost/i_shop";
	
	public static Connection openConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
//		java.net.URL u = ConnectionUtils.class.getClassLoader().getResource("polikarpov.lesson5.logger/loggerConfig.xml");
		
		DOMConfigurator.configure("loggerConfig.xml");
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		
		return DriverManager.getConnection(URL, userName, userPassword);
	}
}
