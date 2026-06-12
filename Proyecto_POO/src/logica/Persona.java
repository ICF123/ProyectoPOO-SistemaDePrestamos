package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Persona {
	private String nombre;
	private String telefono;
	private String email;
	private List<Prestamo> prestamos;
	
	public Persona(String nombre, String telefono, String email) throws Exception {
		this.nombre = nombre;
		if (!esNumeroValido(telefono)) {
			throw new Exception("Telefono invalido.");
		}
		this.telefono = telefono;
		if (!esEmailValido(email)) {
			throw new Exception("Email invalido.");
		}
		this.email = email;
		prestamos = new ArrayList<Prestamo>();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public List<Prestamo> getPrestamos() {
		return prestamos;
	}
	public void setPrestamos(List<Prestamo> prestamos) {
		this.prestamos = prestamos;
	}
	
	public void modificarPersona(String nombre, String telefono, String email, List<Prestamo> prestamos) throws Exception {
		setNombre(nombre);
		if (!esNumeroValido(telefono)) {
			throw new Exception("Telefono invalido.");
		}
		setTelefono(telefono);
		if (!esEmailValido(email)) {
			throw new Exception("Email invalido.");
		}
		setEmail(email);
		setPrestamos(prestamos);
	}
	
	public void consultarPersona() {
		System.out.println("Nombre: " + getNombre() + " Email: " + getEmail() + " Teléfono: " + getTelefono() +
				"\n Préstamos: " + getPrestamos());
	}
	
	public void agregarPrestamo(Prestamo prestamo) {
		prestamos.add(prestamo);
	}
	
	public void borrarPrestamo(Prestamo prestamo) throws Exception {
		if (!prestamos.contains(prestamo)) {
			throw new Exception("Prestamo no existe.");
		}
		prestamos.remove(prestamo);
	}
	
	private boolean esEmailValido(String email) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	private boolean esNumeroValido(String numero) {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(numero);
		return m.matches();
	}
}
