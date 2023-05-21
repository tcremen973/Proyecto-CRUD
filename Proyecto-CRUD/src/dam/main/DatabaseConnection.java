package dam.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.jdt.annotation.NonNull;

public class DatabaseConnection {
	private Connection connection;
	private String connectionString;
	// CONEXION = "jdbc:mysql://localhost/crud?user=root&password="
	
	public DatabaseConnection (@NonNull String stringConnection) {
		try {
			// Se carga el driver al crear el objeto
			DriverManager.registerDriver (new com.mysql.cj.jdbc.Driver());
			this.connectionString = stringConnection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean connect() {
		try {
			this.connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isConnected();
	}
	
	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public boolean disconnect() {
		try {
			if(this.connection==null) return true;
			this.connection.close();
			this.connectionString = "";
			return true;
		} catch (SQLException e) {			
			return false;
		}
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public boolean isConnected() {
		try {
			return !this.connection.isClosed();
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
	}
}
