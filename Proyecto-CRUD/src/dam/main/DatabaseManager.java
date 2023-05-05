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
	public static String MANGA = "manga", AUTOR = "autor", EDITORIAL = "editorial";
	
	// Ordenar
	public static String ASCENDENTE = "ASC", DESCENDENTE = "DESC";
	
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
	private ArrayList<Manga> transformarManga(ResultSet rs){
		ArrayList<Manga> mangas = new ArrayList<Manga>();
		try {
			while(rs.next()) {
				mangas.add(new Manga(
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
		return mangas;
	}
	
	/**
	 * Devuelve la tabla completa.
	 * @return
	 */
	public ArrayList<Manga> getManga(){
		try {
			PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM manga");
			ResultSet rs = ps.executeQuery();
			return transformarManga(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Metodo para buscar manga por un campo.
	 * @return
	 */
	public ArrayList<Manga> getManga(String campo, String condicion){
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT * FROM manga WHERE "+ campo +" = "+ condicion);
			ResultSet rs = ps.executeQuery();
			return transformarManga(rs);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Método para buscar manga por dos campos.
	 * Es una búsqueda de AND.
	 * @return
	 */
	public ArrayList<Manga> getManga(String campo1, String condicion1, String campo2, String condicion2){
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT * FROM manga "
							+ "WHERE "+campo1+" = "+condicion1+" AND "+campo2+" = "+condicion2);
			ResultSet rs = ps.executeQuery();
			return transformarManga(rs);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Ordena la tabla manga por un campo.
	 * @param campo
	 * @param orden
	 * @return
	 */
	public ArrayList<Manga> ordenarManga(String campo, String orden){
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("SELECT * FROM manga ORDER BY "+campo+" "+orden);
			ResultSet rs = ps.executeQuery();
			return transformarManga(rs);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Edita un campo de una entrada.
	 * @param campo
	 * @param valor
	 * @param campoBusqueda
	 * @param condicion
	 * @return
	 */
	public void updateManga(String campo, String valor, String campoBusqueda, String condicion){
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("UPDATE manga SET "+campo+" = '"+valor+"' WHERE "+campoBusqueda+" = "+condicion);
			boolean rs = ps.execute();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	public void deleteManga(String campo, String valor){
		try {
			PreparedStatement ps = this.connection.
					prepareStatement("DELETE FROM manga WHERE "+campo+" = '"+valor+"'");
			boolean rs = ps.execute();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
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
