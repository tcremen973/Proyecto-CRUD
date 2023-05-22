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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
 * Gestor de la base de datos de manga.
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
	 * @param connection Objeto tipo DatabaseConnection usado para establecer conexión.
	 */
	public DatabaseManager(@NonNull DatabaseConnection connection) {
		this.connection = connection;
	}

	/**
	 * Método para hacer una consulta Select a la BBDD.
	 * @param nTabla		Nombre de la tabla
	 * @param ordenarPor	Campor por el que ordenar.
	 * @param orden			Orden (Ascendente o Descendente)
	 * @param condiciones	Array de strings con las condiciones, 
	 * 						donde los valores pares son los campos y 
	 * 						los valores impares son las condiciones
	 * @return ArrayList con todos los objetos obtenidos de los registros.
	 */
	public ArrayList<Elemento> getTabla(@NonNull String nTabla, String ordenarPor, String orden, String[] condiciones){
		PreparedStatement ps = null;
		ArrayList<Elemento> tabla = new ArrayList<Elemento>();
		try {
			// Abro conexión
			connection.connect();
			// Construyo un String para luego usarlo en un PreparedStatement
			StringBuilder query = new StringBuilder("SELECT * FROM "+nTabla+" ");
			if (condiciones != null) {
				query.append("WHERE "+condiciones[0]+" = "+condiciones[1]+" ");
				for (int i = 3; i <= condiciones.length-1; i+=2) {
					query.append("AND "+condiciones[i-1]+" = '"+condiciones[i]+"' ");
				}
			}
			if (ordenarPor != null && orden != null) {
				query.append("ORDER BY "+ordenarPor+" "+orden);
			}
			// Establezco el PreparedStatement
			ps = connection.getConnection().prepareStatement(query.toString());
			
			// Ejecuto el PreparedStatement
			ResultSet rs = ps.executeQuery();
			// Introduzco los datos en el ArrayList
			switch (nTabla) {
			case "manga": // Caso para Manga
				while(rs.next()) {
					tabla.add(new Manga(
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
					tabla.add(new Autor(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getDate(4).toLocalDate(),
							rs.getDate(5) == null? null:rs.getDate(5).toLocalDate()));
				}; 
				break;
			case "editorial": // Caso para Editorial
				while(rs.next()) {
					tabla.add(new Editorial(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getDate(4).toLocalDate(),
							rs.getString(5)));
				}; 
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Cierro la conexión
			connection.disconnect();
		}
		return tabla;
	}

	/**
	 * Metodo para añadir datos a la BBDD
	 * @param elementos
	 */
	public void addRegistros(ArrayList<Elemento> elementos) {
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
	public void updateRegistros(String nTabla, String campo, String valor, String campoBusqueda, String condicion){
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
	 * @param nTabla 	Nombre de la tabla donde buscar
	 * @param campo		Nombre del campo de la tabla
	 * @param condicion	Condición para eliminar la entrada
	 */
	public void deleteRegistros(String nTabla, String campo, String condicion){
		PreparedStatement ps = null;
		try {
			connection.connect();
			ps = connection.getConnection().prepareStatement("DELETE FROM ? WHERE ? = ?");
			ps.setString(1, nTabla);
			ps.setString(2, campo);
			ps.setString(3, condicion);
			ps.execute();
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally {
			// Cierro la conexión
			this.connection.disconnect();
		}
	}

	/**
	 * Método para exportar una lista de elementos.
	 * @param elementos		Todos los elementos a exportar.
	 * @param rutaArchivo	Ruta donde se va a generar el xml.
	 */
	public void exportarDatos(ArrayList<Elemento> elementos, String rutaArchivo) {
		Document documento = null;
		try {
			// Creo el documento
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			documento=db.newDocument();

			// Añado una raiz al documento
			Element raiz = documento.createElement("elementos");
			documento.appendChild(raiz);
			
			// Para cada elemento genero sus atributos según su tipo.
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
				// Cada elemento lo añado a la raiz.
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
	}

	/**
	 * Método para importar un archivo xml a un ArrayList.
	 * @param rutaArchivo Ruta del archivo xml.
	 * @return ArrayList de los objetos de xml.
	 */
	public ArrayList<Elemento> importarXml(String rutaArchivo) {
		ArrayList<Elemento> tabla = new ArrayList<Elemento>();
		try {
			// Creo el documento
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document documento = db.parse(new File(rutaArchivo));

			// Añado todas las etiquetas a tabla con el nombre del elemento.
			// Primero las que tienen como nombre de la etiqueta manga.
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
			// Luego las que tienen de nombre autor.
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
			// Y por último editorial.
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

	/**
	 * Método para comprobar la integridad de la BBDD,
	 * esto lo hago comprobando que las ids de autor y editorial
	 * de manga estén en las tablas de autor y editorial.
	 * @return true si la BBDD está completamente bien.
	 */
	public boolean comprobarIntegridad() {
		HashSet<Integer> idsAutorEnManga = new HashSet<Integer>();
		HashSet<Integer> idsEditorialEnManga = new HashSet<Integer>();
		HashSet<Integer> idsDeAutor = new HashSet<Integer>();
		HashSet<Integer> idsDeEditorial = new HashSet<Integer>();
		// Recogo todas las ids de autor y editorial en manga
		for (Elemento manga : getTabla(DatabaseManager.MANGA,null,null,null)) {
			Manga m = (Manga)manga;
			idsAutorEnManga.add(m.getIdAutor());
			idsEditorialEnManga.add(m.getIdEditorial());
		}
		// Recogo las ids de todos los autores
		for (Elemento autor : getTabla(DatabaseManager.AUTOR,null,null,null)) {
			idsDeAutor.add(((Autor)autor).getId());
		}
		// Recogo las ids de todas las editoriales
		for (Elemento editorial : getTabla(DatabaseManager.EDITORIAL,null,null,null)) {
			idsDeEditorial.add(((Editorial)editorial).getId());
		}
		// Compruebo si todas las ids de autor en la tabla manga se 
		// encuentran en la tabla autor, luego lo mismo con editoriales
		return idsDeAutor.containsAll(idsAutorEnManga) && idsDeEditorial.containsAll(idsEditorialEnManga);
	}
}