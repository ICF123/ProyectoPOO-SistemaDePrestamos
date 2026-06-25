package interfaz;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;

public class ConsultarItem extends JDialog {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultarItem dialog = new ConsultarItem();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public ConsultarItem() {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 546, 386);
		getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblNombre.setBounds(10, 11, 418, 36);
		getContentPane().add(lblNombre);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblDescripcion.setBounds(10, 58, 146, 36);
		getContentPane().add(lblDescripcion);
		
		JTextArea textFieldDescripcion = new JTextArea();
		String variable = "abc";
		textFieldDescripcion.setText(variable);
		textFieldDescripcion.setBounds(154, 58, 370, 36);
		getContentPane().add(textFieldDescripcion);
		
		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblCodigo.setBounds(10, 105, 250, 36);
		getContentPane().add(lblCodigo);
		
		JLabel lblPrestamo = new JLabel("Préstamo: ");
		lblPrestamo.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		lblPrestamo.setBounds(10, 152, 430, 36);
		getContentPane().add(lblPrestamo);

	}
}
