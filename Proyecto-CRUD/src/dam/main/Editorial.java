package dam.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Editorial implements Elemento {
	private int id;
	private String nombre;
	private String pais;
	private LocalDate fechaFundacion;
	private String direccion;
	
	public Editorial(int id, String nombre, String pais, LocalDate fechaFundacion, String direccion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.fechaFundacion = fechaFundacion;
		this.direccion = direccion;
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

	@Override
	public String toString() {
		return "Editorial [id=" + id + ", nombre=" + nombre + ", pais=" + pais + ", fechaFundacion=" + fechaFundacion
				+ ", direccion=" + direccion + "]";
	}

	public static ArrayList<Elemento> getData(ResultSet rs) {
		ArrayList<Elemento> data = new ArrayList<Elemento>();
		try {
			while(rs.next()) {
				data.add(new Editorial(
						rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getDate(4).toLocalDate(),
						rs.getString(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
}
