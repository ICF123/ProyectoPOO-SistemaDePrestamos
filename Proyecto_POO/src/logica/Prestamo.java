package logica;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Prestamo {
	private String nombre;
	private int tipoDeAlerta;
	private LocalDateTime alerta;
	private Persona personaPrestada;
	private List<Item> itemsPrestados;
	
	public Prestamo(String nombre, int tipoDeAlerta, Persona personaPrestada, int cantidadTiempoAlerta, int tipoDeIncremento) {
		this.nombre = nombre;
		this.tipoDeAlerta = tipoDeAlerta;
		this.personaPrestada = personaPrestada;
		LocalDateTime temp = LocalDateTime.now();
		if (tipoDeIncremento == 1) {
			alerta = temp.plusMinutes(cantidadTiempoAlerta);
		}
		else if (tipoDeIncremento == 2) {
			alerta = temp.plusHours(cantidadTiempoAlerta);
		}
		else {
			alerta = temp.plusDays(cantidadTiempoAlerta);
		}
		itemsPrestados = new ArrayList<Item>();
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Persona getPersonaPrestada() {
		return personaPrestada;
	}
	public void setPersonaPrestada(Persona personaPrestada) {
		this.personaPrestada = personaPrestada;
	}

	public List<Item> getItemsPrestados() {
		return itemsPrestados;
	}
	public void setItemsPrestados(List<Item> itemsPrestados) {
		this.itemsPrestados = itemsPrestados;
	}

	public LocalDateTime getAlerta() {
		return alerta;
	}
	
	public void agregarItem(Item item) {
		itemsPrestados.add(item);
	}
	
	public void eliminarItem(Item item) throws Exception {
		if (!itemsPrestados.contains(item)) {
			throw new Exception("Item no existe.");
		}
		itemsPrestados.remove(item);
	}
	
	public void alertaUnica() {
		LocalDateTime actualTruncado = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
		LocalDateTime alertaTruncado = alerta.truncatedTo(ChronoUnit.MINUTES);
		if (actualTruncado.equals(alertaTruncado)) {
			System.out.println("Alerta única de " + getNombre() + ".");
		}
	}
}
