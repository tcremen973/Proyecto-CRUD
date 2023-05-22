package dam.main;

import java.time.LocalDate;
import org.eclipse.jdt.annotation.NonNull;

public class Autor implements Elemento {
	private int id;
	private String nombre;
	private String pais;
	private LocalDate fechaNacimiento;
	private LocalDate fechaDefuncion;

	// Atributos Autor
	public static String ID = "id", NOMBRE = "nombre", PAIS = "pais",
			FECHA_NACIMIENTO = "fecha_nacimiento", FECHA_DEFUNCION = "fecha_defuncion";

	public Autor(@NonNull int id,@NonNull String nombre,@NonNull String pais,
			@NonNull LocalDate fechaNacimiento, LocalDate fechaDefuncion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaDefuncion = fechaDefuncion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public LocalDate getFechaDefuncion() {
		return fechaDefuncion;
	}

	public void setFechaDefuncion(LocalDate fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}

	@Override
	public String toString() {
		return "Autor [id=" + id + ", nombre=" + nombre + ", pais=" + pais + ", fechaNacimiento=" + fechaNacimiento
				+ ", fechaDefuncion=" + fechaDefuncion + "]";
	}
}
