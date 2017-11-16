import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

public class Template extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Template() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("PORT");
		lblNewLabel.setBounds(132, 11, 36, 25);
		
		textField = new JTextField();
		textField.setBounds(85, 57, 130, 20);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("W\u0142\u0105cz serwer");
		btnNewButton.setBounds(85, 107, 130, 23);
		
		JButton btnNewButton_1 = new JButton("Wy\u0142\u0105cz serwer");
		btnNewButton_1.setBounds(85, 148, 130, 23);
		contentPane.setLayout(null);
		contentPane.add(lblNewLabel);
		contentPane.add(textField);
		contentPane.add(btnNewButton);
		contentPane.add(btnNewButton_1);
	}
}
