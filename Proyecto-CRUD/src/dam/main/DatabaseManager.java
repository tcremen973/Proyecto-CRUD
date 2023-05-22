package dam.main;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jdt.annotation.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 * Gestor de la base de datos
 * @author Toni
 * @version 1.0
 */
public class DatabaseManager {
	private DatabaseConnection connection;

	// Constantes para acceso a tablas
	public static String MANGA = "manga", AUTOR = "autor", EDITORIAL = "editorial";

	// Constantes para ordenar
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
		ArrayList<Elemento> data = new ArrayList<Elemento>();
		try {
			ResultSet rs = ps.executeQuery();
			// Guardo los datos de la consulta con el tipo que corresponde
			switch (nTabla) {

			case "manga": // Caso para Manga
				while(rs.next()) {
					data.add(new Manga(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getDate(5).toLocalDate(),
							rs.getInt(6),
							rs.getInt(7)));
				}; 
				break;

			case "autor": // Caso para Autor
				while(rs.next()) {
					data.add(new Autor(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getDate(4).toLocalDate(),
							rs.getDate(5) == null? null:rs.getDate(5).toLocalDate()));
				}; 
				break;
			case "editorial": // Caso para Editorial
				while(rs.next()) {
					data.add(new Editorial(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getDate(4).toLocalDate(),
							rs.getString(5)));
				}; 
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
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
			ps.execute();
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
			ps.execute();
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			// Cierro la conexión
			this.connection.disconnect();
		}
	}

	public Document exportarTabla(ArrayList<Elemento> elementos, String rutaArchivo) {
		Document documento = null;
		try {
			// Creo el documento
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			documento=db.newDocument();

			// Añado una raiz al documento
			Element raiz = documento.createElement("elementos");
			documento.appendChild(raiz);
			Element elem = null;

			for (Elemento elemento : elementos) {
				if (elemento instanceof Manga) {
					Manga e = ((Manga)elemento);
					elem = documento.createElement("manga");
					elem.setAttribute(Manga.ID, String.valueOf(e.getId()));
					elem.setAttribute(Manga.TITULO, e.getTitulo());
					elem.setAttribute(Manga.GENERO, e.getGenero());
					elem.setAttribute(Manga.SINOPSIS, e.getSinopsis());
					elem.setAttribute(Manga.FECHA_PUBLICACION, e.getFechaPublicacion().toString());
					elem.setAttribute(Manga.ID_AUTOR, String.valueOf(e.getIdAutor()));
					elem.setAttribute(Manga.ID_EDITORIAL, String.valueOf(e.getIdEditorial()));
				} else if (elemento instanceof Autor) {
					Autor e = ((Autor)elemento);
					elem = documento.createElement("autor");
					elem.setAttribute(Autor.ID, String.valueOf(e.getId()));
					elem.setAttribute(Autor.NOMBRE, e.getNombre());
					elem.setAttribute(Autor.PAIS, e.getPais());
					elem.setAttribute(Autor.FECHA_NACIMIENTO, e.getFechaNacimiento().toString());
					if (e.getFechaDefuncion() == null) {elem.setAttribute(Autor.FECHA_DEFUNCION, null);} 
					else {elem.setAttribute(Autor.FECHA_DEFUNCION, e.getFechaDefuncion().toString());}
				} else if (elemento instanceof Editorial) {
					Editorial e = ((Editorial)elemento);
					elem = documento.createElement("editorial");
					elem.setAttribute(Editorial.ID, String.valueOf(e.getId()));
					elem.setAttribute(Editorial.NOMBRE, e.getNombre());
					elem.setAttribute(Editorial.PAIS, e.getPais());
					elem.setAttribute(Editorial.FECHA_FUNDACION, e.getFechaFundacion().toString());
					elem.setAttribute(Editorial.DIRECCION, e.getDireccion());
				}
				raiz.appendChild(elem);
			}
			// Guardo el documento en un archivo
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(documento);
			StreamResult result = new StreamResult(new File(rutaArchivo));
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return documento;
	}

	public ArrayList<Elemento> importarXml(String rutaArchivo) {
		ArrayList<Elemento> tabla = new ArrayList<Elemento>();
		try {
			// Creo el documento
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document documento = db.parse(new File(rutaArchivo));

			// Guardo todas las etiquetas con el nombre elemento
			NodeList nodeList = documento.getElementsByTagName("manga");
			for (int i = 0; i < nodeList.getLength(); i++) {
				NamedNodeMap item = nodeList.item(i).getAttributes();
				tabla.add(new Manga(
						Integer.valueOf(item.getNamedItem(Manga.ID).getNodeValue()),
						item.getNamedItem(Manga.TITULO).getNodeValue(),
						item.getNamedItem(Manga.GENERO).getNodeValue(),
						item.getNamedItem(Manga.SINOPSIS).getNodeValue(),
						LocalDate.parse(item.getNamedItem(Manga.FECHA_PUBLICACION).getNodeValue()),
						Integer.valueOf(item.getNamedItem(Manga.ID_AUTOR).getNodeValue()),
						Integer.valueOf(item.getNamedItem(Manga.ID_AUTOR).getNodeValue())
						));
			}
			nodeList = documento.getElementsByTagName("autor");
			for (int i = 0; i < nodeList.getLength(); i++) {
				NamedNodeMap item = nodeList.item(i).getAttributes();
				String fechaDefuncion = item.getNamedItem(Autor.FECHA_DEFUNCION).getNodeValue();
				tabla.add(new Autor(
						Integer.valueOf(item.getNamedItem(Autor.ID).getNodeValue()),
						item.getNamedItem(Autor.NOMBRE).getNodeValue(),
						item.getNamedItem(Autor.PAIS).getNodeValue(),
						LocalDate.parse(item.getNamedItem(Autor.FECHA_NACIMIENTO).getNodeValue()),
						fechaDefuncion.equals("")? null: LocalDate.parse(fechaDefuncion)
						));
			}
			nodeList = documento.getElementsByTagName("editorial");
			for (int i = 0; i < nodeList.getLength(); i++) {
				NamedNodeMap item = nodeList.item(i).getAttributes();
				tabla.add(new Editorial(
						Integer.valueOf(item.getNamedItem(Editorial.ID).getNodeValue()),
						item.getNamedItem(Editorial.NOMBRE).getNodeValue(),
						item.getNamedItem(Editorial.PAIS).getNodeValue(),
						LocalDate.parse(item.getNamedItem(Editorial.FECHA_FUNDACION).getNodeValue()),
						item.getNamedItem(Editorial.DIRECCION).getNodeValue()
						));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tabla;
	}

	public boolean comprobarIntegridad() {
		HashSet<Integer> idsAutorEnManga = new HashSet<Integer>();
		HashSet<Integer> idsEditorialEnManga = new HashSet<Integer>();
		HashSet<Integer> idsDeAutor = new HashSet<Integer>();
		HashSet<Integer> idsDeEditorial = new HashSet<Integer>();

		for (Elemento manga : getTabla(DatabaseManager.MANGA)) {
			Manga m = (Manga)manga;
			idsAutorEnManga.add(m.getIdAutor());
			idsEditorialEnManga.add(m.getIdEditorial());
		}

		for (Elemento autor : getTabla(DatabaseManager.AUTOR)) {
			idsDeAutor.add(((Autor)autor).getId());
		}

		for (Elemento editorial : getTabla(DatabaseManager.EDITORIAL)) {
			idsDeAutor.add(((Editorial)editorial).getId());
		}

		return idsDeAutor.containsAll(idsAutorEnManga) && idsDeEditorial.containsAll(idsEditorialEnManga);
	}
}