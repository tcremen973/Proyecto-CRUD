package dam.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Autor implements Elemento {
	private int id;
	private String nombre;
	private String pais;
	private LocalDate fechaNacimiento;
	private LocalDate fechaDefuncion;

	public Autor(int id, String nombre, String pais, LocalDate fechaNacimiento, LocalDate fechaDefuncion) {
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

	public static ArrayList<Elemento> getData(ResultSet rs) {
		ArrayList<Elemento> data = new ArrayList<Elemento>();
		try {
			while(rs.next()) {
				data.add(new Autor(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getDate(4).toLocalDate(),
						rs.getDate(5) == null? null:rs.getDate(5).toLocalDate()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
}
