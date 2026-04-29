package tema4.resueltos.deustoikea;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class Tienda implements Serializable {
	protected String nombre;
	protected String direccion;
	protected String tipo;
	protected String horario;
	protected HashMap<Mueble, Integer> almacen;
	
	public Tienda(String nombre, String direccion, String tipo, String horario) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.tipo = tipo;
		this.horario = horario;
		this.almacen = new HashMap<>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public HashMap<Mueble, Integer> getAlmacen() {
		return almacen;
	}

	public void setAlmacen(HashMap<Mueble, Integer> almacen) {
		this.almacen = almacen;
	}

	@Override
	public String toString() {
		return "Tienda [nombre=" + nombre + ", direccion=" + direccion + ", tipo=" + tipo + ", horario=" + horario
				+ ", almacen=" + almacen.size() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tienda other = (Tienda) obj;
		return Objects.equals(nombre, other.nombre);
	}
	
	public int totalMueblesOnline() {
		int total = 0;
		
		for (Mueble mueble: this.almacen.keySet()) {
			int valor = this.almacen.get(mueble);
			if (mueble instanceof MuebleOnline) {
				total += valor;
			}
		}
		
		return total;
	}
	
	
}
