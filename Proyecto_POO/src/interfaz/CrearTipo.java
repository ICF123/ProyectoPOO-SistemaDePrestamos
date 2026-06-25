package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.Controladora;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CrearTipo extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CrearTipo dialog = new CrearTipo();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CrearTipo() {
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 405, 150);
		getContentPane().setLayout(new BorderLayout());
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
		
		JButton btnNewButton = new JButton("Confirmar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controladora control = Controladora.getInstance();
				try {
					if (textFieldNombre.getText().length() == 0) {
						JOptionPane.showMessageDialog(CrearTipo.this, "Debe escribir un nombre.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						control.crearTipo(textFieldNombre.getText());
						dispose();
					}
				}
				catch (Exception er) {
					JOptionPane.showMessageDialog(CrearTipo.this, "Ya existe un ítem con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 26));
		btnNewButton.setBounds(107, 58, 168, 48);
		contentPanel.add(btnNewButton);
	}
}
