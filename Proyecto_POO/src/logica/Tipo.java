package logica;

import java.util.ArrayList;
import java.util.List;

public class Tipo {
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
	
	public List<Item> getItem() {
		return items;
	}
	public void setItem(List<Item> item) {
		this.items = item;
	}
	
	public void modificarTipo(String nombre, ArrayList<Item> item) {
		this.nombre = nombre;
		this.items = item;
	}
	public void addItem(Item item) {
		this.items.add(item);
	}
	public void borrarItem(Item item) throws Exception {
		if (!this.items.contains(item)) {
			throw new Exception("Item doesn't exist.");
		}
		this.items.remove(item);
	}
}
