package dam.main;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jdt.annotation.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
/**
 * Gestor de la base de datos
 * @author Toni
 * @version 1.0
 */
public class DatabaseManager {
	private DatabaseConnection connection;
	private Statement statement;

	// Acceso a tablas
	public static String MANGA = "manga", AUTOR = "autor", EDITORIAL = "editorial";

	// Ordenar
	public static String ASCENDENTE = "ASC", DESCENDENTE = "DESC";

	/**
	 * Constructor que inicializa la conexion a la BBDD
	 * @param connection
	 */
	public DatabaseManager(@NonNull DatabaseConnection connection) {
		this.connection = connection;
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
		Connection connection;
		PreparedStatement ps = null;
		try {
			// Abro la conexión
			connection = DriverManager.getConnection(this.connection.getConnectionString());
			ps = connection.prepareStatement("SELECT * FROM "+nTabla);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Cierro la conexión
			this.connection.disconnect();
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
		Connection connection;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(this.connection.getConnectionString());
			ps = connection.prepareStatement("SELECT * FROM manga WHERE "+ campo +" = "+ condicion);
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			// Cierro la conexión
			this.connection.disconnect();
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
		Connection connection;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(this.connection.getConnectionString());
			ps = connection.prepareStatement("SELECT * FROM manga "
					+ "WHERE "+campo1+" = "+condicion1+" AND "+campo2+" = "+condicion2);
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			// Cierro la conexión
			this.connection.disconnect();
		}
		return getData(ps, nTabla);
	}

	public ArrayList<Elemento> ordenarTabla(String nTabla, String campo, String orden){
		Connection connection;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(this.connection.getConnectionString());
			ps = connection.prepareStatement("SELECT * FROM manga ORDER BY "+campo+" "+orden);
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			// Cierro la conexión
			this.connection.disconnect();
		}
		return getData(ps, nTabla);
	}

	public void addEntradas(ArrayList<Elemento> elementos) {
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(this.connection.getConnectionString());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		for (Elemento elemento : elementos) {
			if (elemento instanceof Manga) {
				try {
					ps = connection.prepareStatement("INSERT INTO manga VALUES"
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
					ps = connection.prepareStatement("INSERT INTO autor VALUES"
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
					ps = connection.prepareStatement("INSERT INTO editorial VALUES"
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
		this.connection.disconnect();
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
		Connection connection;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(this.connection.getConnectionString());
			ps = connection.prepareStatement("UPDATE "+nTabla+" SET "+campo+" = '"+valor+"' WHERE "+campoBusqueda+" = "+condicion);
			boolean rs = ps.execute();
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			// Cierro la conexión
			this.connection.disconnect();
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
		Connection connection;
		PreparedStatement ps = null;
		try {
			connection = DriverManager.getConnection(this.connection.getConnectionString());
			ps = connection.prepareStatement("DELETE FROM "+nTabla+" WHERE "+campo+" = '"+condicion+"'");
			boolean rs = ps.execute();
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			// Cierro la conexión
			this.connection.disconnect();
		}
	}

	public void exportarTabla(ArrayList<Elemento> elementos) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document documento=db.parse("TablaExportada.xml");

			Element raiz = documento.getDocumentElement();
			for (Elemento elemento : elementos) {
				Element elem = documento.createElement("elemento");
				if (elemento instanceof Manga) {
					Manga e = ((Manga)elemento);
					elem.setAttribute(Manga.ID, String.valueOf(e.getId()));
					elem.setAttribute(Manga.TITULO, e.getTitulo());
					elem.setAttribute(Manga.GENERO, e.getGenero());
					elem.setAttribute(Manga.SINOPSIS, e.getSinopsis());
					elem.setAttribute(Manga.FECHA_PUBLICACION, e.getFechaPublicacion().toString());
					elem.setAttribute(Manga.ID_AUTOR, String.valueOf(e.getIdAutor()));
					elem.setAttribute(Manga.ID_EDITORIAL, String.valueOf(e.getIdEditorial()));
				} else if (elemento instanceof Autor) {
					Autor e = ((Autor)elemento);
					elem.setAttribute(Autor.ID, String.valueOf(e.getId()));
					elem.setAttribute(Autor.NOMBRE, e.getNombre());
					elem.setAttribute(Autor.PAIS, e.getPais());
					elem.setAttribute(Autor.FECHA_NACIMIENTO, e.getFechaNacimiento().toString());
					elem.setAttribute(Autor.FECHA_DEFUNCION, e.getFechaDefuncion().toString());
				} else if (elemento instanceof Editorial) {
					Editorial e = ((Editorial)elemento);
					elem.setAttribute(Editorial.ID, String.valueOf(e.getId()));
					elem.setAttribute(Editorial.NOMBRE, e.getNombre());
					elem.setAttribute(Editorial.PAIS, e.getPais());
					elem.setAttribute(Editorial.FECHA_PUBLICACION, e.getFechaFundacion().toString());
					elem.setAttribute(Editorial.DIRECCION, e.getDireccion());
				}
				raiz.appendChild(elem);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}