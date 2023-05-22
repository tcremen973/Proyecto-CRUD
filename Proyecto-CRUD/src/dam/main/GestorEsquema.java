package dam.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Clase GestorEsquema que se encarga de crear una BBDD 
 * utilizando un script almacenado en un archivo.
 * Utiliza una conexión a la BBDD proporcionada
 * a través de un objeto DatabaseConnection.
 * @author usuario
 * @version 1.0
 */
public class GestorEsquema {
	private DatabaseConnection connection;

	/**
	 * Constructor de la clase GestorEsquema
	 * @param connection La conexión a la BBDD.
	 */
	public GestorEsquema(DatabaseConnection connection) {
		this.connection = connection;
	}

	/**
	 * Método para crear una BBDD con un script almacenado.
	 * @param rutaScript La ruta donde se encuentra nuestro Script.
	 */
	public void crearBaseDatos(String rutaScript) {
		try {
			// Creo una conexión a la BBDD
			Connection connection = DriverManager.getConnection(this.connection.getConnectionString());
			// Creo un objeto Statement para luego ejecutar el script.
			Statement statement = connection.createStatement();
			// Un objeto BufferedReader leerá el archivo que le hemos pasado por parámetro.
			BufferedReader br = new BufferedReader(new FileReader(rutaScript));
			// Creo un objeto StringBuilder para guardar el script.
			StringBuilder script = new StringBuilder();
			String linea;
			// Uso un bucle para guardar correctamente cada línea del Script.
			while ((linea = br.readLine()) != null) {
				// Añado un salto de línea al final de cada línea.
				script.append(linea).append("\n");
			}
			// Ejecuto el Script
			statement.executeUpdate(script.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
