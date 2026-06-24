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
import logica.Item;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Principal {

	private JFrame frame;
	private JTable tablaItems;
	private JScrollPane scrollPane;

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
			Object[] fila = new Object[] {item.getNombre(), item.getTipo().getNombre(), nombrePrestamo};
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
			if (model.getValueAt(numeroFila, 2) != "No esta en un préstamo.") {
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
		frame.setBounds(100, 100, 630, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel PanelItems = new JPanel();
		PanelItems.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarItems();
			}
		});
		tabbedPane.addTab("Items", null, PanelItems, null);
		PanelItems.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 489, 249);
		PanelItems.add(scrollPane);
		
		tablaItems = new JTable();
		tablaItems.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Tipo", "Pr\u00E9stamo"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tablaItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaItems.getColumnModel().getColumn(0).setPreferredWidth(217);
		tablaItems.getColumnModel().getColumn(1).setPreferredWidth(199);
		tablaItems.getColumnModel().getColumn(2).setPreferredWidth(250);
		scrollPane.setViewportView(tablaItems);
		
		JButton botonBorrarItem = new JButton("Borrar");
		botonBorrarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrarItem();
			}
		});
		botonBorrarItem.setBounds(509, 114, 90, 22);
		PanelItems.add(botonBorrarItem);
		
		JButton botonCrearItem = new JButton("Crear");
		botonCrearItem.setBounds(509, 14, 90, 22);
		PanelItems.add(botonCrearItem);
		
		JPanel PanelPersonas = new JPanel();
		tabbedPane.addTab("Personas", null, PanelPersonas, null);
		PanelPersonas.setLayout(null);
		
		JPanel PanelCategorias = new JPanel();
		tabbedPane.addTab("Categorías", null, PanelCategorias, null);
		PanelCategorias.setLayout(null);
		
		JPanel PanelTipos = new JPanel();
		tabbedPane.addTab("Tipos", null, PanelTipos, null);
		PanelTipos.setLayout(null);
		
		JPanel PanelPrestamos = new JPanel();
		tabbedPane.addTab("Préstamos", null, PanelPrestamos, null);
		PanelPrestamos.setLayout(null);
		
		JPanel PanelReportes = new JPanel();
		tabbedPane.addTab("Reportes", null, PanelReportes, null);
		PanelReportes.setLayout(null);
	}
}
