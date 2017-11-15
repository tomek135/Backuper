import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField_login;
	private JTextField textField_adres;
	private JTextField textField_port;
	private JPasswordField passwordField;
	private String response;
	private JFrame frame = this;
	/**
	 * Create the frame.
	 */
	public MainWindow(Connection connection) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 375, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BACKUPER");
		lblNewLabel.setBounds(138, 11, 99, 22);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Login");
		lblNewLabel_1.setBounds(60, 61, 96, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(60, 86, 96, 14);
		contentPane.add(lblPassword);
		
		JLabel lblNowyUytkownik = new JLabel("Nowy u\u017Cytkownik");
		lblNowyUytkownik.setBounds(60, 207, 109, 14);
		contentPane.add(lblNowyUytkownik);
		
		JButton btnZarejestrujSi = new JButton("Zarejestruj si\u0119");
		btnZarejestrujSi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String login = textField_login.getText();
				System.out.println("login " +login);
				char[] password = passwordField.getPassword();
				System.out.println("pass " + password.toString());
				//String passwordToString = password.toString();
				String passwordToString ="a";
				String host = textField_adres.getText();
				System.out.println(textField_port.getText());
				int port = Integer.parseInt(textField_port.getText());
				System.out.println("port "+ port);
				response = connection.checkAuthorizationAfterRegister(login,passwordToString,host,port);
				
				switch(response) {
					case "REG":
						JOptionPane.showMessageDialog(frame, "Rejestracja przebieg³a pomyœlnie");
					case "BUSY":
						JOptionPane.showMessageDialog(frame, "Uzytkownik juz istnieje");
				}

			}
		});

		btnZarejestrujSi.setBounds(166, 203, 117, 23);
		contentPane.add(btnZarejestrujSi);
		
		textField_login = new JTextField();
		textField_login.setBounds(166, 58, 117, 20);
		contentPane.add(textField_login);
		textField_login.setColumns(10);
		
		JLabel lblAdresSerwera = new JLabel("Adres serwera");
		lblAdresSerwera.setBounds(60, 111, 96, 14);
		contentPane.add(lblAdresSerwera);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(60, 136, 96, 14);
		contentPane.add(lblPort);
		
		textField_adres = new JTextField();
		textField_adres.setBounds(166, 108, 117, 20);
		textField_adres.setColumns(10);
		textField_adres.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '.')) {
                    e.consume();
                }

            }
        });
		contentPane.add(textField_adres);
		
		textField_port = new JTextField();
		textField_port.setBounds(166, 133, 117, 20);
		textField_port.setColumns(10);
		/*textField_port.addKeyListener(new KeyAdapter() {
	            public void keyTyped(KeyEvent e) {
	                char character = e.getKeyChar();
	                if (((character < '0') || (character > '9'))
	                        && (character != '\b')) {
	                    e.consume();
	                }

	            }
	        });*/
		contentPane.add(textField_port);
		
		JButton btnZaloguj = new JButton("Zaloguj");
		btnZaloguj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String login = textField_login.getText();
				System.out.println("login " +login);
				char[] password = passwordField.getPassword();
				System.out.println("pass " + password.toString());
				String passwordToString = "a";
				String host = textField_adres.getText();
				System.out.println(textField_port.getText());
				int port = Integer.parseInt(textField_port.getText());
				System.out.println("port "+ port);
				response = connection.checkAuthorizationAfterLogin(login,passwordToString,host,port);
				
				switch(response) {
					case "OK":
						JOptionPane.showMessageDialog(frame, "Zalogowano pomyslnie");
						break;
					case "BRAK":
						JOptionPane.showMessageDialog(frame, "Nie ma takiego uzytkownika");
						break;
					case "WRONG":
						JOptionPane.showMessageDialog(frame, "Podane has³o jest nieprawid³owe");
						break;
				}
			}
		});
		btnZaloguj.setBounds(166, 169, 117, 23);
		contentPane.add(btnZaloguj);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(166, 83, 117, 20);
		contentPane.add(passwordField);
	}
}
