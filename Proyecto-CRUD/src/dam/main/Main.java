package dam.main;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// Creo DatabaseConnection
		DatabaseConnection connection = new DatabaseConnection("jdbc:mysql://localhost/manga?user=root&password=");	
		// Creo un DatabaseManager
		DatabaseManager databaseManager = new DatabaseManager(connection);
		
		ArrayList<Elemento> tabla = databaseManager.getTabla(DatabaseManager.MANGA);
		
		for (Elemento elemento : tabla) {
			System.out.println(elemento);
		}
	}
}
