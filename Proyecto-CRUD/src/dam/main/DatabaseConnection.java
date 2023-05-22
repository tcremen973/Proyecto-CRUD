package dam.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Gestor de la conexión para la BBDD de manga.
 * @author Toni
 * @version 1.0
 */
public class DatabaseConnection {
	private Connection connection;
	private String connectionString;

	/**
	 * Constructor de la clase DataBaseConnection.
	 * @param stringConnection	Dirección de conexión a la BBDD.
	 */
	public DatabaseConnection (@NonNull String stringConnection) {
		try {
			// Se carga el driver al crear el objeto
			DriverManager.registerDriver (new com.mysql.cj.jdbc.Driver());
			this.connectionString = stringConnection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para establecer conexión con la BBDD.
	 * @return true si la conexión se ha establecido.
	 */
	public boolean connect() {
		try {
			// Creo la conexión con el Driver
			this.connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isConnected();
	}

	/**
	 * Método para cerrar la conexión con la BBDD.
	 * @return true si la conexión está cerrada.
	 */
	public boolean disconnect() {
		try {
			if(this.connection==null) return true;
			this.connection.close();
			return true;
		} catch (SQLException e) {			
			return false;
		}
	}

	/**
	 * Método para saber si el objeto está conectado a una BBDD.
	 * @return true si está conectado a una BBDD.
	 */
	public boolean isConnected() {
		try {
			return !this.connection.isClosed();
		} catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}
	}
	
	// Setters y getters
	
	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
