package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
	private String nombre;
	private final int codigo;
	private String descripcion;
	private Prestamo prestamo = null;
	private String tipo;
	private List<String> categorias;
	
	public Item(String nombre, String descripcion, int codigo, String tipo) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.tipo = tipo;
		categorias = new ArrayList<String>();
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}
	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<String> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public int getCodigo() {
		return codigo;
	}
	
	public void modificarItem(String nombre, String descripcion, String tipo) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.tipo = tipo;
	}
	
	public void consultarItem() {
		System.out.println("Nombre del ítem: " + nombre + "/nDescripción: " + descripcion + 
				"\nTipo: " + tipo + "\nCategorías: " + categorias.toString());
	}
	
	public void agregarCategoria(String categoria) throws Exception {
		if (categorias.contains(categoria)) {
			throw new Exception("Ya existe esa categoría.");
		}
		categorias.add(categoria);
	}
	
	public void borrarCategoria(String categoria) throws Exception {
		if (!categorias.contains(categoria)) {
			throw new Exception("Esa categoría no existe.");
		}
		categorias.remove(codigo);
	}
	
	public void agregarPrestamo(Prestamo prestamo) throws Exception {
		if (this.prestamo != null) {
			throw new Exception("Este ítem ya tiene un prestamo.");
		}
		this.prestamo = prestamo;
	}
	
	public void borrarPrestamo(Prestamo prestamo) throws Exception {
		if (this.prestamo == null) {
			throw new Exception("Este ítem no tiene prestamo.");
		}
		if (this.prestamo != prestamo) {
			throw new Exception("El préstamo de este ítem es distinto.");
		}
		this.prestamo = null;
	}
}
