package dam.main;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// Me conecto a la BBDD
		System.out.println("Conectando a la base de datos...");
		DatabaseConnection conexion = new DatabaseConnection();
		conexion.connect("jdbc:mysql://localhost/manga?user=root&password=");
		
		// Compruebo la conexión
		System.out.println(conexion.isConnected()? "Conectado":"No conectado");
		// Si no está conectado para el programa
		if (!conexion.isConnected()) System.exit(1);
		
		// Creo un DatabaseManager
		DatabaseManager databaseManager = new DatabaseManager(conexion.getConnection());
		
		// Compruebo el funcionamiento
		System.out.println("\nTabla Tabla");
		ArrayList<Elemento> tabla = databaseManager.getTabla(DatabaseManager.MANGA, Manga.ID_AUTOR, "1");
		for (Elemento elemento : tabla) {
			System.out.println(elemento);
		}
	}
}
