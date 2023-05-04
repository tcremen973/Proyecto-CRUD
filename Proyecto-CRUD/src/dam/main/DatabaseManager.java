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
	private boolean mangaUpdated = false;
	private ArrayList<Manga> mangaData;
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
			this.mangaData = new ArrayList<Manga>();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
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
