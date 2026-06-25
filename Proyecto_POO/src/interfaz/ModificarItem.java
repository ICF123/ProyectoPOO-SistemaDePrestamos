package interfaz;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import control.Controladora;
import logica.Tipo;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificarItem extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTable tablaTipos;
	private String itemACambiar;

	/**
	 * Launch the application.
	 */

	private void cargarTipos() {
		Controladora control = Controladora.getInstance();
		DefaultTableModel model = (DefaultTableModel) tablaTipos.getModel();
		model.setRowCount(0);
		List<Tipo> listaTipos = control.obtenerListadosTipos();
		for (Tipo tipo : listaTipos) {
			String nombreTipo = tipo.getNombre();
			Object[] fila = new Object[] {nombreTipo};
			model.addRow(fila);
		}
	}
	/**
	 * Create the dialog.
	 */
	public ModificarItem(String itemACambiar) {
		this.itemACambiar = itemACambiar;
		getContentPane().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarTipos();
			}
		});
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 320);
		getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblNombre.setBounds(10, 11, 111, 36);
		getContentPane().add(lblNombre);
		
		JTextField textFieldNombre = new JTextField();
		textFieldNombre.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		textFieldNombre.setColumns(10);
		textFieldNombre.setBounds(129, 11, 238, 36);
		getContentPane().add(textFieldNombre);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblDescripcion.setBounds(10, 58, 146, 36);
		getContentPane().add(lblDescripcion);
		
		JTextField textFieldDescripcion = new JTextField();
		textFieldDescripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		textFieldDescripcion.setColumns(10);
		textFieldDescripcion.setBounds(161, 58, 238, 36);
		getContentPane().add(textFieldDescripcion);
		
		JLabel lblTipo = new JLabel("Tipo: ");
		lblTipo.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblTipo.setBounds(10, 105, 63, 36);
		getContentPane().add(lblTipo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(77, 105, 337, 105);
		getContentPane().add(scrollPane);
		
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
		tablaTipos.getColumnModel().getColumn(0).setPreferredWidth(666);
		scrollPane.setViewportView(tablaTipos);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controladora control = Controladora.getInstance();
				try {
					if (textFieldNombre.getText().length() == 0) {
						JOptionPane.showMessageDialog(ModificarItem.this, "Debe escribir un nombre.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					if (textFieldDescripcion.getText().length() == 0) {
						JOptionPane.showMessageDialog(ModificarItem.this, "Debe escribir una descripción.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					int numeroFila = tablaTipos.getSelectedRow();
					if (numeroFila == -1) {
						JOptionPane.showMessageDialog(ModificarItem.this, "Debe seleccionar un tipo.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					if (textFieldNombre.getText().length() > 0 && textFieldDescripcion.getText().length() > 0
							&& numeroFila != -1) {
						DefaultTableModel model = (DefaultTableModel) tablaTipos.getModel();
						String nombreTipo = (String) model.getValueAt(numeroFila, 0);
						control.modificarItem(textFieldNombre.getText(), textFieldDescripcion.getText(), nombreTipo, itemACambiar);
						dispose();
					}
				}
				catch (Exception er) {
					JOptionPane.showMessageDialog(ModificarItem.this, "Ya existe un ítem con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConfirmar.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
		btnConfirmar.setBounds(139, 221, 168, 48);
		getContentPane().add(btnConfirmar);

	}
}
