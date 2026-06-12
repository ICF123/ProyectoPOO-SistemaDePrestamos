package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tipo implements Serializable {
	private String nombre;
	private List<Item> items;
	
	
	public Tipo(String nombre) {
		this.nombre = nombre;
		this.items = new ArrayList<Item>();
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
	
	public void modificarTipo(String nombre, ArrayList<Item> items) {
		setNombre(nombre);
		setItems(items);
	}
	
	public void consultarTipo() {
		System.out.println("Nombre de tipo: " + getNombre() + "\nÍtems de este tipo: " + getItems());
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
