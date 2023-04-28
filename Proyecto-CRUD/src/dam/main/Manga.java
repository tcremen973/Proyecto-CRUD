package dam.main;

import java.time.LocalDate;

public class Manga {
	private int id;
	private String titulo;
	private String genero;
	private String sinopsis;
	private LocalDate fechaPublicacion;
	private int idAutor;
	private int idEditorial;
	
	public Manga(int id, String titulo, String genero, String sinopsis, LocalDate fechaPublicacion, int idAutor,
			int idEditorial) {
		super();
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
}
