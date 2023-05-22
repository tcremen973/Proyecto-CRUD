package dam.main;

import java.util.ArrayList;

import org.w3c.dom.Document;

public class Main {

	public static void main(String[] args) {
		// Creo DatabaseConnection
		DatabaseConnection connection = new DatabaseConnection("jdbc:mysql://localhost/manga?user=root&password=");	
		// Creo un DatabaseManager
		DatabaseManager databaseManager = new DatabaseManager(connection);
		
		ArrayList<Elemento> tabla = databaseManager.getTabla(DatabaseManager.MANGA);
		tabla.addAll(databaseManager.getTabla(DatabaseManager.AUTOR));
		tabla.addAll(databaseManager.getTabla(DatabaseManager.EDITORIAL));
		
		for (Elemento elemento : tabla) {
			System.out.println(elemento);
		}
		
		Document doc = databaseManager.exportarTabla(tabla, "/home/usuario/salidaBBDD/TablaExportada.xml");
		tabla = databaseManager.importarXml("/home/usuario/salidaBBDD/TablaExportada.xml");
		
		System.out.println("\n Importacion");
		for (Elemento elemento : tabla) {
			System.out.println(elemento);
		}
		
		System.out.println(databaseManager.comprobarIntegridad());
	}
}
