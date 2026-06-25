package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Controladora;
import logica.Item;
import logica.Tipo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CrearItem extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable tablaTipos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CrearItem dialog = new CrearItem();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	/**
	 * Create the dialog.
	 */
	public CrearItem() {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 324);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				cargarTipos();
			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblNombre.setBounds(10, 11, 111, 36);
		contentPanel.add(lblNombre);
		
		JTextField textFieldNombre = new JTextField();
		textFieldNombre.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		textFieldNombre.setBounds(127, 11, 238, 36);
		contentPanel.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblDescripcion.setBounds(10, 58, 146, 36);
		contentPanel.add(lblDescripcion);
		
		JTextField textFieldDescripcion = new JTextField();
		textFieldDescripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		textFieldDescripcion.setBounds(164, 58, 238, 36);
		contentPanel.add(textFieldDescripcion);
		textFieldDescripcion.setColumns(10);
		
		JLabel lblTipo = new JLabel("Tipo: ");
		lblTipo.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblTipo.setBounds(10, 105, 63, 36);
		contentPanel.add(lblTipo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(77, 105, 337, 105);
		contentPanel.add(scrollPane);
		
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
		tablaTipos.getColumnModel().getColumn(0).setPreferredWidth(664);
		scrollPane.setViewportView(tablaTipos);
		tablaTipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		cargarTipos();
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controladora control = Controladora.getInstance();
				try {
					if (textFieldNombre.getText().length() == 0) {
						JOptionPane.showMessageDialog(CrearItem.this, "Debe escribir un nombre.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					if (textFieldDescripcion.getText().length() == 0) {
						JOptionPane.showMessageDialog(CrearItem.this, "Debe escribir una descripción.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					int numeroFila = tablaTipos.getSelectedRow();
					if (numeroFila == -1) {
						JOptionPane.showMessageDialog(CrearItem.this, "Debe seleccionar un tipo.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					if (textFieldNombre.getText().length() > 0 && textFieldDescripcion.getText().length() > 0
							&& numeroFila != -1) {
						DefaultTableModel model = (DefaultTableModel) tablaTipos.getModel();
						String nombreTipo = (String) model.getValueAt(numeroFila, 0);
						control.crearItem(textFieldNombre.getText(), textFieldDescripcion.getText(), nombreTipo);
						dispose();
					}
				}
				catch (Exception er) {
					JOptionPane.showMessageDialog(CrearItem.this, "Ya existe un ítem con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConfirmar.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
		btnConfirmar.setBounds(139, 221, 168, 48);
		contentPanel.add(btnConfirmar);
	}
}
