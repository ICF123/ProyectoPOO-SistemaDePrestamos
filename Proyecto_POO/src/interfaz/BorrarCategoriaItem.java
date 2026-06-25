package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Categoria;
import logica.Item;

public class BorrarCategoriaItem extends JDialog {


	private static final long serialVersionUID = 1L;
	private JTable tablaCategorias;
	private String nombreItem;

	/**
	 * Launch the application.
	 */
	
	private boolean existeCategoriaItem(String nombreCategoria, String nombreItem) {
		Controladora control = Controladora.getInstance();
		for (Item item : control.obtenerListadosÍtems()) {
			if (item.getNombre() == nombreItem) {
				for (Categoria categoria : control.obtenerListadosCategorías()) {
					if (categoria.getNombre() == nombreCategoria) {
						return true;
					}
				}
			}
		}
		return false;
	}
	private void cargarCategorias() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();
		model.setRowCount(0);
		List<Categoria> listaCategorias = control.obtenerListadosCategorías();
		for (Categoria categoria : listaCategorias) {
			if (existeCategoriaItem(categoria.getNombre(), nombreItem)) {
				Object[] fila = new Object[] {categoria.getNombre()};
				model.addRow(fila);	
			}
		}
	}
	/**
	 * Create the dialog.
	 */
	public BorrarCategoriaItem(String nombreItem) {
		this.nombreItem = nombreItem;
		getContentPane().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarCategorias();
			}
		});
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 550, 300);
		getContentPane().setLayout(null);
		
		JLabel lblCategorias = new JLabel("Categorías: ");
		lblCategorias.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblCategorias.setBounds(10, 11, 134, 36);
		getContentPane().add(lblCategorias);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(154, 11, 362, 231);
		getContentPane().add(scrollPane);
		
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
		tablaCategorias.getColumnModel().getColumn(0).setPreferredWidth(667);
		scrollPane.setViewportView(tablaCategorias);
		tablaCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numeroFila = tablaCategorias.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) tablaCategorias.getModel();
				if (numeroFila == -1) {
					JOptionPane.showMessageDialog(BorrarCategoriaItem.this, "Debe seleccionar un ítem.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					Controladora control = Controladora.getInstance();
					String nombreCategoria = (String) model.getValueAt(numeroFila, 0);
					try {
						control.borrarCategoriaItem(nombreCategoria, nombreItem);
					}
					catch (Exception er) {
						JOptionPane.showMessageDialog(BorrarCategoriaItem.this, "Error al borrar item: " + e.toString(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnBorrar.setBounds(10, 220, 134, 22);
		getContentPane().add(btnBorrar);

	}

}
