package dam.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.eclipse.jdt.annotation.NonNull;
/**
 * Gestor de la base de datos
 * @author Toni
 * @version 1.0
 */
public class DatabaseManager {
	private Connection connection = null;
	private Statement statement = null;
	//private boolean mangaUpdated = false;
	//private ArrayList<Manga> mangaData;
	
	// Acceso a tablas
	public static int MANGA = 1, AUTOR = 2, EDITORIAL = 3;
	
	// Atributos tabla Manga
	//public static int ID_manga = 1; 
	
	/**
	 * Constructor especializado en inicializar objetos
	 * de tipo DatabaseManager a partir de un objeto de conexión
	 * que no puede ser nulo
	 * @param connection Objeto de conexión
	 */
	public DatabaseManager(@NonNull Connection connection) {
		this.connection = connection;
		try {
			this.statement = connection.createStatement();
			//this.mangaData = new ArrayList<Manga>();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodos Manga
	 */
	public ArrayList<Manga> getManga(){
		ArrayList<Manga> manga = null;
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT * FROM manga");
			ResultSet rs = ps.executeQuery();
			manga = new ArrayList<Manga>();
			while(rs.next()) {
				manga.add(new Manga(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getDate(5).toLocalDate(),
						rs.getInt(6),
						rs.getInt(7)));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return manga;
	}
	
	/**
	 * Metodo para buscar manga por id
	 * @param id 
	 * @return
	 */
	public ArrayList<Manga> getManga(int id){
		ArrayList<Manga> manga = null;
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT * FROM manga WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			manga = new ArrayList<Manga>();
			while(rs.next()) {
				manga.add(new Manga(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getDate(5).toLocalDate(),
						rs.getInt(6),
						rs.getInt(7)));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return manga;
	}
	
	/**
	 * Método para buscar manga por id de autor y editorial.
	 * Es una búsqueda de AND.
	 * @param id_autor
	 * @param id_editorial
	 * @return
	 */
	public ArrayList<Manga> getManga(int id_autor, int id_editorial){
		ArrayList<Manga> manga = null;
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT * FROM manga "
							+ "WHERE id_autor = ? AND id_editorial = ?");
			ps.setInt(1, id_autor);
			ps.setInt(2, id_editorial);
			ResultSet rs = ps.executeQuery();
			manga = new ArrayList<Manga>();
			while(rs.next()) {
				manga.add(new Manga(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getDate(5).toLocalDate(),
						rs.getInt(6),
						rs.getInt(7)));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return manga;
	}
	
	/**
	 * Metodos Autor
	 */
	public ArrayList<Autor> getAutor(){
		ArrayList<Autor> autor = null;
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT * FROM autor");
			ResultSet rs = ps.executeQuery();
			autor = new ArrayList<Autor>();
			while(rs.next()) {
				autor.add(new Autor(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getDate(4).toLocalDate(),
						rs.getDate(5) == null? null:rs.getDate(5).toLocalDate()));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return autor;
	}
	
	/**
	 * Metodos Editorial
	 */
	public ArrayList<Editorial> getEditorial(){
		ArrayList<Editorial> editorial = null;
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT * FROM editorial");
			ResultSet rs = ps.executeQuery();
			editorial = new ArrayList<Editorial>();
			while(rs.next()) {
				editorial.add(new Editorial(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getDate(4).toLocalDate(),
						rs.getString(5)));
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return editorial;
	}
}
