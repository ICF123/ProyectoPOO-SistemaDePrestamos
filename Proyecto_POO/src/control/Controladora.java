package control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import logica.Categoria;
import logica.Item;
import logica.Persona;
import logica.Prestamo;
import logica.Tipo;

public class Controladora implements Serializable {
	private static Controladora instance = null;
	private int codigoItem;
	private List<Tipo> tipos;
	private List<Categoria> categorias;
	private List<Item> items;
	private List<Prestamo> prestamos;
	private List<Persona> personas;
	
	private Controladora() {
		codigoItem = 1;
		tipos = new ArrayList<Tipo>();
		categorias = new ArrayList<Categoria>();
		items = new ArrayList<Item>();
		prestamos = new ArrayList<Prestamo>();
		personas = new ArrayList<Persona>();
		try {
			this.crearItem("Bola", "Rebota", "Juguete");
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static Controladora getInstance() {
		if (instance == null) {
			instance = new Controladora();
		}
		return instance;
	}
	private boolean existeItem(String nombreItem) {
		for (Item item : items) {
			if (item.getNombre() == nombreItem) {
				return true;
			}
		}
		return false;
	}
	public void crearItem(String nombre, String descripcion, String tipo) throws Exception {
		if (existeItem(nombre)) {
			throw new Exception("Ya existe un ítem con ese nombre.");
		}
		Item item = new Item(nombre, descripcion, codigoItem, tipo);
		items.add(item);
		codigoItem++;
	}
	
	public void modificarItem(String nombre, String descripcion, String tipo, String nombreItemCambiar) throws Exception {
		if (!existeItem(nombreItemCambiar)) {
			throw new Exception("No existe ese item.");
		}
		Item itemTemporario = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getNombre() == nombreItemCambiar) {
				itemTemporario = items.get(i);
				itemTemporario.modificarItem(nombre, descripcion, tipo);
				items.set(i, itemTemporario);
				break;
			}
		}
	}
	public void consultarItem(Item item) throws Exception {
		if (!items.contains(item)) {
			throw new Exception("No existe ese item.");
		}
		Item itemTemporario = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == item) {
				itemTemporario = items.get(i);
				itemTemporario.consultarItem();
				break;
			}
		}
	}
	
	public void agregarCategoriaItem(String categoria, Item item) throws Exception {
		if (!items.contains(item)) {
			throw new Exception("No existe ese item.");
		}
		Item itemTemporario = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == item) {
				itemTemporario = items.get(i);
				itemTemporario.agregarCategoria(categoria);
				items.set(i, itemTemporario);
				break;
			}
		}
	}
	
	public void borrarCategoriaItem(String categoria, Item item) throws Exception {
		if (!items.contains(item)) {
			throw new Exception("No existe ese item.");
		}
		Item itemTemporario = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == item) {
				itemTemporario = items.get(i);
				itemTemporario.borrarCategoria(categoria);
				items.set(i, itemTemporario);
				break;
			}
		}
	}
	
	public void agregarPrestamoItem(Prestamo prestamo, Item item) throws Exception {
		if (!items.contains(item)) {
			throw new Exception("No existe ese item.");
		}
		Item itemTemporario = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == item) {
				itemTemporario = items.get(i);
				itemTemporario.agregarPrestamo(prestamo);
				items.set(i, itemTemporario);
				break;
			}
		}
	}
	
	public void borrarPrestamoItem(Prestamo prestamo, Item item) throws Exception {
		if (!items.contains(item)) {
			throw new Exception("No existe ese item.");
		}
		Item itemTemporario = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == item) {
				itemTemporario = items.get(i);
				itemTemporario.borrarPrestamo(prestamo);
				items.set(i, itemTemporario);
				break;
			}
		}
	}
	
	public void borrarItem(String nombreItem) throws Exception {
		if (!existeItem(nombreItem)) {
			throw new Exception("No existe ese item.");
		}
		int posItem = 0;
		Item itemBorrar;
		for (Item item : items) {
			if (item.getNombre() == nombreItem) {
				itemBorrar = items.get(posItem);
				items.remove(itemBorrar);
				break;
			}
			posItem++;
		}
	}
	
	public void crearPersona(String nombre, String telefono, String email) throws Exception {
		Persona persona = new Persona(nombre, telefono, email);
		personas.add(persona);
	}
	
	public void modificarPersona(String nombre, String telefono, String email, 
			ArrayList<Prestamo> prestamos, Persona persona) throws Exception {
		if (!personas.contains(persona)) {
			throw new Exception("No existe esa persona.");
		}
		Persona personaTemporaria = null;
		for (int i = 0; i < personas.size(); i++) {
			if (personas.get(i) == persona) {
				personaTemporaria = personas.get(i);
				personaTemporaria.modificarPersona(nombre, telefono, email, prestamos);
				personas.set(i, personaTemporaria);
				break;
			}
		}
	}
	
	public void consultarPersona(Persona persona) throws Exception {
		if (!personas.contains(persona)) {
			throw new Exception("No existe esa persona.");
		}
		Persona personaTemporaria = null;
		for (int i = 0; i < personas.size(); i++) {
			if (personas.get(i) == persona) {
				personaTemporaria = personas.get(i);
				personaTemporaria.consultarPersona();
				break;
			}
		}
	}
	
	public void agregarPrestamoPersona(Prestamo prestamo, Persona persona) throws Exception {
		if (!personas.contains(persona)) {
			throw new Exception("No existe esa persona.");
		}
		Persona personaTemporaria = null;
		for (int i = 0; i < personas.size(); i++) {
			if (personas.get(i) == persona) {
				personaTemporaria = personas.get(i);
				personaTemporaria.agregarPrestamo(prestamo);
				personas.set(i, personaTemporaria);
				break;
			}
		}
	}
	
	public void borrarPrestamoPersona(Prestamo prestamo, Persona persona) throws Exception {
		if (!personas.contains(persona)) {
			throw new Exception("No existe esa persona.");
		}
		Persona personaTemporaria = null;
		for (int i = 0; i < personas.size(); i++) {
			if (personas.get(i) == persona) {
				personaTemporaria = personas.get(i);
				personaTemporaria.borrarPrestamo(prestamo);
				personas.set(i, personaTemporaria);
				break;
			}
		}
	}
	
	public void borrarPersona(Persona persona) throws Exception {
		if (!personas.contains(persona)) {
			throw new Exception("No existe esa persona.");
		}
		personas.remove(persona);
	}
	
	public void crearCategoría(String nombre) {
		Categoria categoria = new Categoria(nombre);
		categorias.add(categoria);
	}
	
	public void modificarCategoria(String nombre, ArrayList<Item> items, Categoria categoria) throws Exception {
		if (!categorias.contains(categoria)) {
			throw new Exception("No existe esa categoría.");
		}
		Categoria categoriaTemporaria = null;
		for (int i = 0; i < categorias.size(); i++) {
			if (categorias.get(i) == categoria) {
				categoriaTemporaria = categorias.get(i);
				categoriaTemporaria.modificarCategoría(nombre, items);
				categorias.set(i, categoriaTemporaria);
				break;
			}
		}
	}
	
	public void consultarCategoria(Categoria categoria) throws Exception {
		if (!categorias.contains(categoria)) {
			throw new Exception("No existe esa categoría.");
		}
		Categoria categoriaTemporaria = null;
		for (int i = 0; i < categorias.size(); i++) {
			if (categorias.get(i) == categoria) {
				categoriaTemporaria = categorias.get(i);
				categoriaTemporaria.consultarCategoria();
				break;
			}
		}
	}
	
	public void agregarItemCategoria(Item item, Categoria categoria) throws Exception {
		if (!categorias.contains(categoria)) {
			throw new Exception("No existe esa categoría.");
		}
		Categoria categoriaTemporaria = null;
		for (int i = 0; i < categorias.size(); i++) {
			if (categorias.get(i) == categoria) {
				categoriaTemporaria = categorias.get(i);
				categoriaTemporaria.agregarItem(item);
				categorias.set(i, categoriaTemporaria);
				break;
			}
		}
	}
	
	public void borrarItemCategoria(Item item, Categoria categoria) throws Exception {
		if (!categorias.contains(categoria)) {
			throw new Exception("No existe esa categoría.");
		}
		Categoria categoriaTemporaria = null;
		for (int i = 0; i < categorias.size(); i++) {
			if (categorias.get(i) == categoria) {
				categoriaTemporaria = categorias.get(i);
				categoriaTemporaria.borrarItem(item);
				categorias.set(i, categoriaTemporaria);
				break;
			}
		}
	}
	
	public void borrarCategoria(Categoria categoria) throws Exception {
		if (!categorias.contains(categoria)) {
			throw new Exception("No existe esa categoría.");
		}
		categorias.remove(categoria);
	}
	
	public void crearTipo(String nombre) throws Exception {
		Tipo tipo = new Tipo(nombre);
		tipos.add(tipo);
	}
	
	public void modificarTipo(String nombre, ArrayList<Item> items, Tipo tipo) throws Exception {
		if (!tipos.contains(tipo)) {
			throw new Exception("No existe ese tipo.");
		}
		Tipo tipoTemporario = null;
		for (int i = 0; i < tipos.size(); i++) {
			if (tipos.get(i) == tipo) {
				tipoTemporario = tipos.get(i);
				tipoTemporario.modificarTipo(nombre, items);
				tipos.set(i, tipoTemporario);
				break;
			}
		}
	}
	
	public void consultarTipo(Tipo tipo) throws Exception {
		if (!tipos.contains(tipo)) {
			throw new Exception("No existe ese tipo.");
		}
		Tipo tipoTemporario = null;
		for (int i = 0; i < tipos.size(); i++) {
			if (tipos.get(i) == tipo) {
				tipoTemporario = tipos.get(i);
				tipoTemporario.consultarTipo();
				break;
			}
		}
	}
	
	public void agregarItemTipo(Item item, Tipo tipo) throws Exception {
		if (!tipos.contains(tipo)) {
			throw new Exception("No existe ese tipo.");
		}
		Tipo tipoTemporario = null;
		for (int i = 0; i < tipos.size(); i++) {
			if (tipos.get(i) == tipo) {
				tipoTemporario = tipos.get(i);
				tipoTemporario.agregarItem(item);
				tipos.set(i, tipoTemporario);
				break;
			}
		}
	}
	
	public void borrarItemTipo(Item item, Tipo tipo) throws Exception {
		if (!tipos.contains(tipo)) {
			throw new Exception("No existe ese tipo.");
		}
		Tipo tipoTemporario = null;
		for (int i = 0; i < tipos.size(); i++) {
			if (tipos.get(i) == tipo) {
				tipoTemporario = tipos.get(i);
				tipoTemporario.borrarItem(item);
				tipos.set(i, tipoTemporario);
				break;
			}
		}
	}
	
	public void borrarTipo(Tipo tipo) throws Exception {
		if (!tipos.contains(tipo)) {
			throw new Exception("No existe ese tipo.");
		}
		tipos.remove(tipo);
	}
	
	public void crearPrestamo(String nombre, int tipoDeAlerta, Persona personaPrestada, 
			int cantidadTiempoAlerta, int tipoDeIncremento) throws Exception {
		Prestamo prestamo = new Prestamo(nombre, tipoDeAlerta, personaPrestada, cantidadTiempoAlerta, tipoDeIncremento);
		prestamos.add(prestamo);
	}
	
	public void agregarItemPrestamo(Item item, Prestamo prestamo) throws Exception {
		if (!prestamos.contains(prestamo)) {
			throw new Exception("No existe ese préstamo.");
		}
		Prestamo prestamoTemporario = null;
		for (int i = 0; i < prestamos.size(); i++) {
			if (prestamos.get(i) == prestamo) {
				prestamoTemporario = prestamos.get(i);
				prestamoTemporario.agregarItem(item);
				prestamos.set(i, prestamoTemporario);
				break;
			}
		}
	}
	
	public void eliminarItemPrestamo(Item item, Prestamo prestamo) throws Exception {
		if (!prestamos.contains(prestamo)) {
			throw new Exception("No existe ese préstamo.");
		}
		Prestamo prestamoTemporario = null;
		for (int i = 0; i < prestamos.size(); i++) {
			if (prestamos.get(i) == prestamo) {
				prestamoTemporario = prestamos.get(i);
				prestamoTemporario.eliminarItem(item);
				prestamos.set(i, prestamoTemporario);
				break;
			}
		}
	}
	
	public void retornarItem(Item item, Prestamo prestamo) throws Exception {
		if (!prestamos.contains(prestamo)) {
			throw new Exception("No existe ese préstamo.");
		}
		Prestamo prestamoTemporario = null;
		for (int i = 0; i < prestamos.size(); i++) {
			if (prestamos.get(i) == prestamo) {
				prestamoTemporario = prestamos.get(i);
				prestamoTemporario.retornarItem(item);
				prestamos.set(i, prestamoTemporario);
				break;
			}
		}
	}
	
	public void finalizarPréstamo(Prestamo prestamo) throws Exception {
		if (!prestamos.contains(prestamo)) {
			throw new Exception("No existe ese préstamo.");
		}
		prestamos.remove(prestamo);
	}
	
	public void borrarPréstamo(Prestamo prestamo) throws Exception {
		if (!prestamos.contains(prestamo)) {
			throw new Exception("No existe ese préstamo.");
		}
		prestamos.remove(prestamo);
	}
	
	public void reporteUsuarios() {
		Persona persona = null;
		for (int i = 0; i < personas.size(); i++) {
			persona = personas.get(i);
			persona.consultarPersona();
		}
	}
	
	public void reporteítems() {
		Item item = null;
		for (int i = 0; i < items.size(); i++) {
			item = items.get(i);
			item.consultarItem();
		}
	}
	
	public void reporteCategorías() {
		Categoria categoria = null;
		for (int i = 0; i < categorias.size(); i++) {
			categoria = categorias.get(i);
			categoria.consultarCategoria();
		}
	}
	
	public void reporteTipos() {
		Tipo tipo = null;
		for (int i = 0; i < tipos.size(); i++) {
			tipo = tipos.get(i);
			tipo.consultarTipo();
		}
	}
	
	public void guardarDatos() throws IOException {
		FileOutputStream file = new FileOutputStream("DatosPrestamos.bin");
		ObjectOutputStream stream = new ObjectOutputStream(file);
		stream.writeObject(instance);
		stream.close();
		file.close();
	}
	
	public void cargarDatos() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream("DatosPrestamos.bin");
		ObjectInputStream stream = new ObjectInputStream(file);
		instance = (Controladora) stream.readObject();
		stream.close();
		file.close();
	}
	
	public List<Persona> obtenerListadosPersonas() {
		return personas;
	}
	
	public List<Item> obtenerListadosÍtems() {
		return items;
	}
	
	public List<Categoria> obtenerListadosCategorías() {
		return categorias;
	}
	
	public List<Tipo> obtenerListadosTipos() {
		return tipos;
	}
	
	public List<Prestamo> obtenerListadosPréstamos() {
		return prestamos;
	}
}
