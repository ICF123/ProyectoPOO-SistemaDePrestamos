package interfaz;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Categoria;
import logica.Item;

import javax.swing.JScrollPane;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class ConsultarItem extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTable tablaCategorias;
	private Item item;

	/**
	 * Launch the application.
	 */
	private void cargarCategorias() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();
		model.setRowCount(0);
		List<Categoria> listaCategorias = control.obtenerListadosCategorías();
		for (Categoria categoria : listaCategorias) {
			Object[] fila = new Object[] {categoria.getNombre()};
			model.addRow(fila);
		}
	}
	/**
	 * Create the dialog.
	 */
	public ConsultarItem(Item item) {
		this.item = item;
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarCategorias();
			}
		});
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 546, 452);
		getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre: " + item.getNombre());
		lblNombre.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblNombre.setBounds(10, 11, 418, 36);
		getContentPane().add(lblNombre);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblDescripcion.setBounds(10, 58, 146, 36);
		getContentPane().add(lblDescripcion);
		
		JTextArea textFieldDescripcion = new JTextArea();
		textFieldDescripcion.setFont(new Font("Monospaced", Font.PLAIN, 18));
		textFieldDescripcion.setEditable(false);
		textFieldDescripcion.setText(item.getDescripcion());
		textFieldDescripcion.setBounds(154, 58, 370, 36);
		getContentPane().add(textFieldDescripcion);
		
		JLabel lblCodigo = new JLabel("Código: " + item.getCodigo());
		lblCodigo.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblCodigo.setBounds(10, 105, 250, 36);
		getContentPane().add(lblCodigo);
		
		String nombrePrestamo;
		if (item.getPrestamo() == null) {
			nombrePrestamo = "No hay prestamo.";
		}
		else {
			nombrePrestamo = item.getPrestamo().getNombre();
		}
		
		JLabel lblPrestamo = new JLabel("Préstamo: " + nombrePrestamo);
		lblPrestamo.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblPrestamo.setBounds(10, 152, 430, 36);
		getContentPane().add(lblPrestamo);
		
		JLabel lblTipo = new JLabel("Tipo: " + item.getTipo());
		lblTipo.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblTipo.setBounds(10, 199, 356, 36);
		getContentPane().add(lblTipo);
		
		JLabel lblCategorias = new JLabel("Categorías:");
		lblCategorias.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblCategorias.setBounds(10, 246, 132, 36);
		getContentPane().add(lblCategorias);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(154, 246, 370, 160);
		getContentPane().add(scrollPane);
		
		tablaCategorias = new JTable();
		scrollPane.setViewportView(tablaCategorias);
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
		tablaCategorias.getColumnModel().getColumn(0).setPreferredWidth(661);
		tablaCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
