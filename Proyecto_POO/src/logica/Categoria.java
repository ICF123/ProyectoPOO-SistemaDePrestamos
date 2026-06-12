package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Categoria implements Serializable {
	private String nombre;
	private List<Item> items;
	
	public Categoria(String nombre) {
		this.nombre = nombre;
		items = new ArrayList<Item>();
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void modificarCategoría(String nombre, List<Item> items) {
		this.nombre = nombre;
		this.items = items;
	}
	
	public void consultarCategoria() {
		System.out.println("Nombre de categoría: " + getNombre() + "\nÍtems de esta categoría: " + getItems());
	}
	
	public void agregarItem(Item item) {
		items.add(item);
	}
	
	public void borrarItem(Item item) throws Exception {
		if (!items.contains(item)) {
			throw new Exception("Item doesn't exist.");
		}
		items.remove(item);
	}
}
