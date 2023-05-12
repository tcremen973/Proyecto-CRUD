package dam.main;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jdt.annotation.NonNull;
/**
 * Gestor de la base de datos
 * @author Toni
 * @version 1.0
 */
public class DatabaseManager {
	private Connection connection = null;
	private Statement statement = null;

	// Acceso a tablas
	public static String MANGA = "manga", AUTOR = "autor", EDITORIAL = "editorial";

	// Ordenar
	public static String ASCENDENTE = "ASC", DESCENDENTE = "DESC";

	/**
	 * Constructor que inicializa la conexion a la BBDD
	 * @param connection
	 */
	public DatabaseManager(@NonNull Connection connection) {
		this.connection = connection;
		try {
			this.statement = connection.createStatement();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	/**
	 * Metodo para ejecutar la linea de SQL recibida.
	 * @param nTabla
	 * @return
	 */
	private ArrayList<Elemento> getData(PreparedStatement ps, String nTabla){
		ArrayList<Elemento> tabla = new ArrayList<Elemento>();
		try {
			ResultSet rs = ps.executeQuery();
			// Guardo los datos de la consulta con el tipo que corresponde
			switch (nTabla) {
			case "manga": tabla.addAll(Manga.getData(rs)); break;
			case "autor": tabla.addAll(Autor.getData(rs)); break;
			case "editorial": tabla.addAll(Editorial.getData(rs)); break;
			}
			tabla.addAll(Manga.getData(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tabla;
	}

	/**
	 * Método para devolver una tabla completa sin condiciones
	 * @param nTabla
	 * @return
	 */
	public ArrayList<Elemento> getTabla(String nTabla){
		PreparedStatement ps = null;
		try {
			ps = this.connection.prepareStatement("SELECT * FROM "+nTabla);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return getData(ps, nTabla);
	}

	/**
	 * Método para devolver una tabla por una condición
	 * @param nTabla
	 * @param campo
	 * @param condicion
	 * @return
	 */
	public ArrayList<Elemento> getTabla(String nTabla, String campo, String condicion){
		PreparedStatement ps = null;
		try {
			ps = this.connection.
					prepareStatement("SELECT * FROM manga WHERE "+ campo +" = "+ condicion);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return getData(ps, nTabla);
	}

	/**
	 * Método para devolver una tabla por dos condiciones
	 * @param nTabla
	 * @param campo1
	 * @param condicion1
	 * @param campo2
	 * @param condicion2
	 * @return
	 */
	public ArrayList<Elemento> getTabla(String nTabla, String campo1, String condicion1, String campo2, String condicion2){
		PreparedStatement ps = null;
		try {
			ps = this.connection.
					prepareStatement("SELECT * FROM manga "
							+ "WHERE "+campo1+" = "+condicion1+" AND "+campo2+" = "+condicion2);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return getData(ps, nTabla);
	}

	public ArrayList<Elemento> ordenarTabla(String nTabla, String campo, String orden){
		PreparedStatement ps = null;
		try {
			ps = this.connection.
					prepareStatement("SELECT * FROM manga ORDER BY "+campo+" "+orden);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return getData(ps, nTabla);
	}

	public void addEntradas(ArrayList<Elemento> elementos) {
		PreparedStatement ps = null;
		for (Elemento elemento : elementos) {
			if (elemento instanceof Manga) {
				try {
					ps = this.connection.prepareStatement("INSERT INTO manga VALUES"
							+ "(?,?, ?, ?, ?, ?, ?)");
					Manga manga = ((Manga)elemento);
					ps.setInt(1, manga.getId());
					ps.setString(2, manga.getTitulo());
					ps.setString(3, manga.getGenero());
					ps.setString(4, manga.getSinopsis());
					ps.setDate(5, Date.valueOf(manga.getFechaPublicacion()));
					ps.setInt(6, manga.getIdAutor());
					ps.setInt(7, manga.getIdEditorial());
					ps.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (elemento instanceof Autor) {
				try {
					ps = this.connection.prepareStatement("INSERT INTO autor VALUES"
							+ "(?, ?, ?, ?, ?)");
					Autor autor = ((Autor)elemento);
					ps.setInt(1, autor.getId());
					ps.setString(2, autor.getNombre());
					ps.setString(3, autor.getPais());
					ps.setDate(4, Date.valueOf(autor.getFechaNacimiento()));
					ps.setDate(5, Date.valueOf(autor.getFechaDefuncion()));
					ps.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if (elemento instanceof Editorial) {
				try {
					ps = this.connection.prepareStatement("INSERT INTO editorial VALUES"
							+ "(?, ?, ?, ?, ?)");
					Editorial editorial = ((Editorial)elemento);
					ps.setInt(1, editorial.getId());
					ps.setString(2, editorial.getNombre());
					ps.setString(3, editorial.getPais());
					ps.setDate(4, Date.valueOf(editorial.getFechaFundacion()));
					ps.setString(5, editorial.getDireccion());
					ps.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Edita un campo de una tabla.
	 * @param campo
	 * @param valor
	 * @param campoBusqueda
	 * @param condicion
	 * @return
	 */
	public void updateEntradas(String nTabla, String campo, String valor, String campoBusqueda, String condicion){
		PreparedStatement ps = null;
		try {
			ps = this.connection.
					prepareStatement("UPDATE "+nTabla+" SET "+campo+" = '"+valor+"' WHERE "+campoBusqueda+" = "+condicion);
			boolean rs = ps.execute();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}

	/**
	 * Elimina entradas de una tabla por
	 * una condición indicada.
	 * @param nTabla
	 * @param campo
	 * @param condicion
	 */
	public void deleteEntradas(String nTabla, String campo, String condicion){
		PreparedStatement ps = null;
		try {
			ps = this.connection.
					prepareStatement("DELETE FROM "+nTabla+" WHERE "+campo+" = '"+condicion+"'");
			boolean rs = ps.execute();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}
	
	public void exportarTabla( ) {
		
	}
}