package interfaz;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Categoria;
import logica.Item;
import logica.Persona;
import logica.Tipo;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal {

	private JFrame frame;
	private JTable tablaItems;
	private JScrollPane scrollPaneItems;
	private JTable tablaPersonas;
	private JScrollPane scrollPanePersonas;
	private JTable tablaCategorias;
	private JScrollPane scrollPaneCategorias;
	private JTable tablaTipos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal window = new Principal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void guardarDatos() {
		try {
			Controladora.guardarDatos();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					frame,
					"Error al guardar datos: " + e.toString(),
					"Error",
					JOptionPane.ERROR_MESSAGE);						
		}
	}
	private void cargarDatos() {
		try {
			Controladora.cargarDatos();
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(
					frame,
					"Error al cargar datos: " + e.toString(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Inician las funciones para la pestaña de Items:
	private void cargarItems() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaItems.getModel();
		model.setRowCount(0);
		List<Item> listaItems = control.obtenerListadosÍtems();
		for (Item item : listaItems) {
			String nombrePrestamo;
			if (item.getPrestamo() == null) {
				nombrePrestamo = "No esta en un préstamo.";
			}
			else {
				nombrePrestamo = item.getPrestamo().getNombre();
			}
			Object[] fila = new Object[] {item.getNombre(), item.getCodigo(), item.getTipo(), nombrePrestamo};
			model.addRow(fila);
		}
	}
	private void borrarItem() {
		int numeroFila = tablaItems.getSelectedRow();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(frame, "Debe seleccionar un ítem.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			DefaultTableModel model = (DefaultTableModel) tablaItems.getModel();
			if (model.getValueAt(numeroFila, 3) != "No esta en un préstamo.") {
				JOptionPane.showMessageDialog(frame, "Este ítem esta en un préstamo, no se puede borrar.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				String nombreItem = (String) model.getValueAt(numeroFila, 0);
				int respuesta = JOptionPane.showConfirmDialog(frame, "Se eliminara el ítem " + nombreItem + ".", 
						"Confirmar", JOptionPane.YES_NO_OPTION);
				if (respuesta == JOptionPane.YES_OPTION) {
					Controladora control = Controladora.getInstance();
					try {
						control.borrarItem(nombreItem);
						for (Item item : control.obtenerListadosÍtems()) {
							if (item.getNombre() == nombreItem) {
								Item itemBorrar = item;
								List<String> categoriasItemBorrar = itemBorrar.getCategorias();
								for (String categoriaNombre : categoriasItemBorrar) {
									control.borrarItemCategoria(nombreItem, categoriaNombre);
								}
								for (Tipo tipoBorrar : control.obtenerListadosTipos()) {
									if (tipoBorrar.getNombre() == itemBorrar.getTipo()) {
										control.borrarItemTipo(nombreItem, tipoBorrar.getNombre());
									}
								}
							}
						}
						cargarItems();
					}
					catch (Exception e) {
						JOptionPane.showMessageDialog(frame, "Error al borrar item: " + e.toString(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
	private void crearItem() {
		Controladora control = Controladora.getInstance();
		if (control.obtenerListadosTipos().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "No hay tipos para crear un ítem.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			CrearItem ventanaCrearItem = new CrearItem();
			ventanaCrearItem.setVisible(true);
			cargarItems();
		}
	}
	private void modificarItem() {
		int numeroFila = tablaItems.getSelectedRow();
		DefaultTableModel model = (DefaultTableModel) tablaItems.getModel();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(frame, "Debe seleccionar un ítem.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			ModificarItem ventanaModificarItem = new ModificarItem(model.getValueAt(numeroFila, 0).toString());
			ventanaModificarItem.setVisible(true);
			cargarItems();
		}
	}
	private void consultarItem() {
		int numeroFila = tablaItems.getSelectedRow();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(frame, "Debe seleccionar un ítem.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			Controladora control = Controladora.getInstance();
			DefaultTableModel model = (DefaultTableModel) tablaItems.getModel();
			String nombreItem = model.getValueAt(numeroFila, 0).toString();
			Item itemParametro;
			int posItem = 0;
			List<Item> listaItems = control.obtenerListadosÍtems();
			for (int i = 0; i < listaItems.size(); i++) {
				if (listaItems.get(i).getNombre() == nombreItem) {
					posItem = i;
					break;
				}
			}
			itemParametro = listaItems.get(posItem);
			control.obtenerListadosÍtems().get(numeroFila);
			ConsultarItem ventanaConsultarItem = new ConsultarItem(itemParametro); //Todo esto porque no me dejaba simplemente asignarle el item en el for
			ventanaConsultarItem.setVisible(true);
			cargarItems();
		}
	}
	private void agregarCategoriaItem() {
		int numeroFila = tablaItems.getSelectedRow();
		DefaultTableModel model = (DefaultTableModel) tablaItems.getModel();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(frame, "Debe seleccionar un ítem.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			AgregarCategoriaItem ventanaAgregarCategoriaItem = new AgregarCategoriaItem(model.getValueAt(numeroFila, 0).toString());
			ventanaAgregarCategoriaItem.setVisible(true);
			cargarItems();
		}
	}
	private void borrarCategoriaItem() {
		int numeroFila = tablaItems.getSelectedRow();
		DefaultTableModel model = (DefaultTableModel) tablaItems.getModel();
		if (numeroFila == -1) {
			JOptionPane.showMessageDialog(frame, "Debe seleccionar un ítem.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			BorrarCategoriaItem ventanaBorrarCategoriaItem = new BorrarCategoriaItem(model.getValueAt(numeroFila, 0).toString());
			ventanaBorrarCategoriaItem.setVisible(true);
			cargarItems();
		}
	}
	//Terminan las funciones para la pestaña de Items.
	
	//Inician las funciones para la pestaña de Personas:
	private void cargarPersonas() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaPersonas.getModel();
		model.setRowCount(0);
		List<Persona> listaPersonas = control.obtenerListadosPersonas();
		for (Persona persona : listaPersonas) {
			Object[] fila = new Object[] {persona.getNombre(), persona.getTelefono(), persona.getEmail()};
			model.addRow(fila);
		}
	}
	//Terminan las funciones para la pestaña de Personas.
	
	//Inician las funciones para la pestaña de Categorías:
	private void cargarCategorias() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaPersonas.getModel();
		model.setRowCount(0);
		List<Categoria> listaCategorias = control.obtenerListadosCategorías();
		for (Categoria categoria : listaCategorias) {
			Object[] fila = new Object[] {categoria.getNombre()};
			model.addRow(fila);
		}
	}
	//Terminan las funciones para la pestaña de Categorías.
	
	//Inician las funciones para la pestaña de Tipos:
	private void cargarTipos() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaTipos.getModel();
		model.setRowCount(0);
		List<Tipo> listaTipos = control.obtenerListadosTipos();
		for (Tipo tipo : listaTipos) {
			Object[] fila = new Object[] {tipo.getNombre()};
			model.addRow(fila);
		}
	}
	
	private void crearTipo() {
		Controladora control = Controladora.getInstance();
		CrearTipo ventanaCrearTipo = new CrearTipo();
		ventanaCrearTipo.setVisible(true);
		cargarTipos();
	}
	//Terminan las funciones para la pestaña de Tipos.
	
	//Inician las funciones para la pestaña de Préstamos:
	
	//Terminan las funciones para la pestaña de Préstamos.
	
	//Inician las funciones para la pestaña de Reportes:
	
	//Terminan las funciones para la pestaña de ReportesItems.
	
	//Inician las funciones para la pestaña de ReportesPersonas:
	
	//Terminan las funciones para la pestaña de ReportesPersonas.
	
	//Inician las funciones para la pestaña de ReportesTipos:
	
	//Terminan las funciones para la pestaña de ReportesTipos.
	
	//Inician las funciones para la pestaña de ReportesCategorías:
	
	//Terminan las funciones para la pestaña de ReportesCategorías.
	/**
	 * Create the application.
	 */
	public Principal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 684, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPanePrincipal = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPanePrincipal, BorderLayout.CENTER);
		
		JPanel PanelItems = new JPanel();
		PanelItems.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarItems();
			}
		});
		tabbedPanePrincipal.addTab("Items", null, PanelItems, null);
		PanelItems.setLayout(null);
		
		scrollPaneItems = new JScrollPane();
		scrollPaneItems.setBounds(10, 11, 489, 249);
		PanelItems.add(scrollPaneItems);
		
		tablaItems = new JTable();
		tablaItems.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "C\u00F3digo", "Tipo", "Pr\u00E9stamo"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaItems.getColumnModel().getColumn(0).setPreferredWidth(176);
		tablaItems.getColumnModel().getColumn(1).setPreferredWidth(116);
		tablaItems.getColumnModel().getColumn(2).setPreferredWidth(163);
		tablaItems.getColumnModel().getColumn(3).setPreferredWidth(250);
		tablaItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneItems.setViewportView(tablaItems);
		
		JButton btnCrearItem = new JButton("Crear");
		btnCrearItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearItem();
			}
		});
		btnCrearItem.setBounds(509, 14, 148, 22);
		PanelItems.add(btnCrearItem);
		
		JButton btnModificarItem = new JButton("Modificar");
		btnModificarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarItem();
			}
		});
		btnModificarItem.setBounds(509, 48, 148, 22);
		PanelItems.add(btnModificarItem);
		
		JButton btnConsultarItem = new JButton("Consultar");
		btnConsultarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consultarItem();
			}
		});
		btnConsultarItem.setBounds(510, 81, 147, 22);
		PanelItems.add(btnConsultarItem);
		
		JButton btnBorrarItem = new JButton("Borrar");
		btnBorrarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarItem();
			}
		});
		btnBorrarItem.setBounds(509, 114, 148, 22);
		PanelItems.add(btnBorrarItem);
		
		JButton btnAgregarCategoriaItem = new JButton("Agregar Categoía");
		btnAgregarCategoriaItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarCategoriaItem();
			}
		});
		btnAgregarCategoriaItem.setBounds(509, 147, 148, 22);
		PanelItems.add(btnAgregarCategoriaItem);
		
		JButton btnBorrarCategoriaItem = new JButton("Borrar Categoría");
		btnBorrarCategoriaItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarCategoriaItem();
			}
		});
		btnBorrarCategoriaItem.setBounds(509, 180, 148, 22);
		PanelItems.add(btnBorrarCategoriaItem);
		
		JButton btnItemGuardar = new JButton("Guardar datos");
		btnItemGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarDatos();
			}
		});
		btnItemGuardar.setBounds(509, 238, 148, 22);
		PanelItems.add(btnItemGuardar);
		
		JButton btnItemCargar = new JButton("Cargar datos");
		btnItemCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarDatos();
			}
		});
		btnItemCargar.setBounds(509, 274, 148, 22);
		PanelItems.add(btnItemCargar);
		
		JPanel PanelPersonas = new JPanel();
		PanelPersonas.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarPersonas();
			}
		});
		tabbedPanePrincipal.addTab("Personas", null, PanelPersonas, null);
		PanelPersonas.setLayout(null);
		
		scrollPanePersonas = new JScrollPane();
		scrollPanePersonas.setBounds(10, 11, 489, 249);
		PanelPersonas.add(scrollPanePersonas);
		
		tablaPersonas = new JTable();
		tablaPersonas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Tel\u00E9fono", "Email"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaPersonas.getColumnModel().getColumn(0).setPreferredWidth(228);
		tablaPersonas.getColumnModel().getColumn(1).setPreferredWidth(187);
		tablaPersonas.getColumnModel().getColumn(2).setPreferredWidth(253);
		tablaPersonas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPanePersonas.setViewportView(tablaPersonas);
		
		JButton btnCrearPersona = new JButton("Crear");
		btnCrearPersona.setBounds(509, 14, 148, 22);
		PanelPersonas.add(btnCrearPersona);
		
		JButton btnModificarPersona = new JButton("Modificar");
		btnModificarPersona.setBounds(509, 47, 148, 22);
		PanelPersonas.add(btnModificarPersona);
		
		JButton btnConsultarPersona = new JButton("Consultar");
		btnConsultarPersona.setBounds(509, 80, 148, 22);
		PanelPersonas.add(btnConsultarPersona);
		
		JButton btnBorrarPersona = new JButton("Borrar");
		btnBorrarPersona.setBounds(509, 113, 148, 22);
		PanelPersonas.add(btnBorrarPersona);
		
		JButton btnPersonaGuardar = new JButton("Guardar datos");
		btnPersonaGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarDatos();
			}
		});
		btnPersonaGuardar.setBounds(509, 238, 148, 22);
		PanelPersonas.add(btnPersonaGuardar);
		
		JButton btnPersonaCargar = new JButton("Cargar datos");
		btnPersonaCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarDatos();
			}
		});
		btnPersonaCargar.setBounds(509, 274, 148, 22);
		PanelPersonas.add(btnPersonaCargar);
		
		JPanel PanelCategorias = new JPanel();
		PanelCategorias.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarCategorias();
			}
		});
		tabbedPanePrincipal.addTab("Categorías", null, PanelCategorias, null);
		PanelCategorias.setLayout(null);
		
		scrollPaneCategorias = new JScrollPane();
		scrollPaneCategorias.setBounds(10, 11, 489, 249);
		PanelCategorias.add(scrollPaneCategorias);
		
		tablaCategorias = new JTable();
		tablaCategorias.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaCategorias.getColumnModel().getColumn(0).setPreferredWidth(663);
		tablaCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneCategorias.setViewportView(tablaCategorias);
		
		JButton btnCrearCategoria = new JButton("Crear");
		btnCrearCategoria.setBounds(509, 14, 148, 22);
		PanelCategorias.add(btnCrearCategoria);
		
		JButton btnModificarCategoria = new JButton("Modificar");
		btnModificarCategoria.setBounds(509, 47, 148, 22);
		PanelCategorias.add(btnModificarCategoria);
		
		JButton btnConsultarCategoria = new JButton("Consultar");
		btnConsultarCategoria.setBounds(509, 80, 148, 22);
		PanelCategorias.add(btnConsultarCategoria);
		
		JButton btnBorrarCategoria = new JButton("Borrar");
		btnBorrarCategoria.setBounds(509, 113, 148, 22);
		PanelCategorias.add(btnBorrarCategoria);
		
		JButton btnCategoriaGuardar = new JButton("Guardar datos");
		btnCategoriaGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarDatos();
			}
		});
		btnCategoriaGuardar.setBounds(509, 238, 148, 22);
		PanelCategorias.add(btnCategoriaGuardar);
		
		JButton btnCategoriaCargar = new JButton("Cargar datos");
		btnCategoriaCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarDatos();
			}
		});
		btnCategoriaCargar.setBounds(509, 274, 148, 22);
		PanelCategorias.add(btnCategoriaCargar);
		
		JPanel PanelTipos = new JPanel();
		PanelTipos.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarTipos();
			}
		});
		tabbedPanePrincipal.addTab("Tipos", null, PanelTipos, null);
		PanelTipos.setLayout(null);
		
		JScrollPane scrollPaneTipos = new JScrollPane();
		scrollPaneTipos.setBounds(10, 11, 489, 249);
		PanelTipos.add(scrollPaneTipos);
		
		tablaTipos = new JTable();
		tablaTipos.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaTipos.getColumnModel().getColumn(0).setPreferredWidth(667);
		tablaTipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneTipos.setViewportView(tablaTipos);
		
		JButton btnCrearTipo = new JButton("Crear");
		btnCrearTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearTipo();
			}
		});
		btnCrearTipo.setBounds(509, 14, 148, 22);
		PanelTipos.add(btnCrearTipo);
		
		JButton btnModificarTipo = new JButton("Modificar");
		btnModificarTipo.setBounds(509, 47, 148, 22);
		PanelTipos.add(btnModificarTipo);
		
		JButton btnConsultarTipo = new JButton("Consultar");
		btnConsultarTipo.setBounds(509, 80, 148, 22);
		PanelTipos.add(btnConsultarTipo);
		
		JButton btnBorrarTipo = new JButton("Borrar");
		btnBorrarTipo.setBounds(509, 113, 148, 22);
		PanelTipos.add(btnBorrarTipo);
		
		JButton btnTipoGuardar = new JButton("Guardar datos");
		btnTipoGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarDatos();
			}
		});
		btnTipoGuardar.setBounds(509, 238, 148, 22);
		PanelTipos.add(btnTipoGuardar);
		
		JButton btnTipoCargar = new JButton("Cargar datos");
		btnTipoCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarDatos();
			}
		});
		btnTipoCargar.setBounds(509, 274, 148, 22);
		PanelTipos.add(btnTipoCargar);
		
		JPanel PanelPrestamos = new JPanel();
		tabbedPanePrincipal.addTab("Préstamos", null, PanelPrestamos, null);
		PanelPrestamos.setLayout(null);
		
		JPanel PanelReportes = new JPanel();
		tabbedPanePrincipal.addTab("Reportes", null, PanelReportes, null);
		PanelReportes.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPaneReportes = new JTabbedPane(JTabbedPane.TOP);
		PanelReportes.add(tabbedPaneReportes, BorderLayout.CENTER);
		
		JPanel PanelReportesItems = new JPanel();
		tabbedPaneReportes.addTab("Reporte Items", null, PanelReportesItems, null);
		
		JPanel PanelReportePersonas = new JPanel();
		tabbedPaneReportes.addTab("Reporte Personas", null, PanelReportePersonas, null);
		
		JPanel PanelReporteTipos = new JPanel();
		tabbedPaneReportes.addTab("Reporte Tipos", null, PanelReporteTipos, null);
		
		JPanel PanelReporteCategorias = new JPanel();
		tabbedPaneReportes.addTab("Reporte Categorías", null, PanelReporteCategorias, null);
	}
}
