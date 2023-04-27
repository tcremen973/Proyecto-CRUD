package dam.main.conector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static String direccion;
	private static String baseDeDatos;
	private static String user;
	private static String password;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		
		
		Class.forName("com.mysql.cj.jdbc.Driver");	
		
		Connection conexion = DriverManager.getConnection("jdbc:mysql://"+direccion+"/"+baseDeDatos+"?user="+user+"&password="+password);
	}
}
