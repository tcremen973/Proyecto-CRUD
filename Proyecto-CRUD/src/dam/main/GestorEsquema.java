package dam.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class GestorEsquema {
	private DatabaseConnection connection;

	public GestorEsquema(DatabaseConnection connection) {
		this.connection = connection;
	}

	public void crearBaseDatos(String rutaScript) {
		try {
			Connection connection = DriverManager.getConnection(this.connection.getConnectionString());
			Statement statement = connection.createStatement();
			BufferedReader br = new BufferedReader(new FileReader(rutaScript));

			StringBuilder script = new StringBuilder();
			String linea;
			while ((linea = br.readLine()) != null) {
				script.append(linea).append("\n");
			}
			statement.executeUpdate(script.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
