package dam.main;

import java.time.LocalDate;
import org.eclipse.jdt.annotation.NonNull;

public class Manga implements Elemento{
	private int id;
	private String titulo;
	private String genero;
	private String sinopsis;
	private LocalDate fechaPublicacion;
	private int idAutor;
	private int idEditorial;

	// Atributos Manga
	public static String ID = "id", TITULO = "titulo", GENERO = "genero", 
			SINOPSIS = "sinopsis", FECHA_PUBLICACION = "fecha_publicacion",
			ID_AUTOR = "id_autor", ID_EDITORIAL = "id_editorial";

	public Manga(@NonNull int id,@NonNull String titulo,@NonNull String genero,@NonNull String sinopsis, 
			@NonNull LocalDate fechaPublicacion,@NonNull int idAutor,@NonNull int idEditorial) {
		this.id = id;
		this.titulo = titulo;
		this.genero = genero;
		this.sinopsis = sinopsis;
		this.fechaPublicacion = fechaPublicacion;
		this.idAutor = idAutor;
		this.idEditorial = idEditorial;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public int getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(int idAutor) {
		this.idAutor = idAutor;
	}

	public int getIdEditorial() {
		return idEditorial;
	}

	public void setIdEditorial(int idEditorial) {
		this.idEditorial = idEditorial;
	}

	@Override
	public String toString() {
		return "Manga [id=" + id + ", titulo=" + titulo + ", genero=" + genero + ", sinopsis=" + sinopsis
				+ ", fechaPublicacion=" + fechaPublicacion + ", idAutor=" + idAutor + ", idEditorial=" + idEditorial
				+ "]";
	}
}