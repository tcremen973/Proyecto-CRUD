package dam.main;

import java.time.LocalDate;
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
		
		// Compruebo todas las tablas para comprobar el correcto funcionamiento del programa
		System.out.println("\nTabla Manga");
		ArrayList<Manga> tablaManga = databaseManager.getManga();
		for (Manga manga : tablaManga) {
			System.out.println(manga);
		}
		
		System.out.println("\nTabla Autor");
		ArrayList<Autor> tablaAutor = databaseManager.getAutor();
		for (Autor autor : tablaAutor) {
			System.out.println(autor);
		}
		
		System.out.println("\nTabla Editorial");
		ArrayList<Editorial> tablaEditorial = databaseManager.getEditorial();
		for (Editorial editorial : tablaEditorial) {
			System.out.println(editorial);
		}
		
		LocalDate fecha = null;
	}
}
