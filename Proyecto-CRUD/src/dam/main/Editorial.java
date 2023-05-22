package dam.main;

import java.time.LocalDate;
import org.eclipse.jdt.annotation.NonNull;

/**
 * La clase Editorial representa el objeto editorial 
 * en la base de datos relacional llamada manga.
 * @author Toni
 * @version 1.0
 */
public class Editorial implements Elemento {
	private int id;
	private String nombre;
	private String pais;
	private LocalDate fechaFundacion;
	private String direccion;

	// Atributos Editorial BBDD
	public static String 
	ID = "id", NOMBRE = "nombre", PAIS = "pais",
	FECHA_FUNDACION = "fecha_fundacion",DIRECCION = "direccion";

	/**
	 * Constructor de la clase Editorial.
	 * @param id 				El ID de la editorial.
	 * @param nombre 			El nombre de la editorial.
	 * @param pais 				El país de la editorial.
	 * @param fechaFundacion 	La fecha de fundación de la editorial.
	 * @param direccion 		La dirección de la editorial.
	 */
	public Editorial(@NonNull int id,@NonNull String nombre,@NonNull String pais,
			@NonNull LocalDate fechaFundacion,@NonNull String direccion) {
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.fechaFundacion = fechaFundacion;
		this.direccion = direccion;
	}

	// Getters y setters

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

	public LocalDate getFechaFundacion() {
		return fechaFundacion;
	}

	public void setFechaFundacion(LocalDate fechaFundacion) {
		this.fechaFundacion = fechaFundacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	// Método toString

	@Override
	public String toString() {
		return "Editorial [id=" + id + ", nombre=" + nombre + ", pais=" + pais + ", fechaFundacion=" + fechaFundacion
				+ ", direccion=" + direccion + "]";
	}
}
