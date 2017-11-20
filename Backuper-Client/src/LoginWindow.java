import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField_login;
	private JTextField textField_adres;
	private JTextField textField_port;
	private JPasswordField passwordField;
	private String response;
	private JFrame frame = this;
	
	String login ="";
	String passwordToString="";
	String host="";
	int port;
	/**
	 * Create the frame.
	 */
	public LoginWindow(Connection connection) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(375,300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((screen.getWidth() - getWidth()) /2);
		int y = (int) ((screen.getHeight() -getHeight()) /2);
		setLocation(x, y); 
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
		
		JLabel lblPassword = new JLabel("Has³o");
		lblPassword.setBounds(60, 86, 96, 14);
		contentPane.add(lblPassword);
		
		JLabel lblNowyUytkownik = new JLabel("Nowy u\u017Cytkownik");
		lblNowyUytkownik.setBounds(60, 207, 109, 14);
		contentPane.add(lblNowyUytkownik);
		
		JButton btnZarejestrujSi = new JButton("Zarejestruj si\u0119");
		btnZarejestrujSi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login = textField_login.getText();
				char[] password = passwordField.getPassword();
				passwordToString = String.valueOf(password);
				host = textField_adres.getText();
				try {
					port = Integer.parseInt(textField_port.getText());
					
					if(weryfikujDane(login,passwordToString,host))
					{
						
						response = connection.checkAuthorizationAfterRegister(login,passwordToString,host,port);
						
						switch(response) {
							case "REG":
								JOptionPane.showMessageDialog(frame, "Rejestracja przebieg³a pomyœlnie");
								break;
							case "BUSY":
								JOptionPane.showMessageDialog(frame, "Uzytkownik juz istnieje");
								break;
							case "NOTCONNECTED":
								JOptionPane.showMessageDialog(frame, "Brak po³¹czenia z serwerem");
								break;
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "Nie wszytkie dane zosta³y podane lub podane s¹ nieprawid³owo! ");
					}
				}catch (Exception e)
				{
					JOptionPane.showMessageDialog(frame, "Podano nie poprawny port");
					return;
				}

			}
		});

		btnZarejestrujSi.setBounds(166, 203, 117, 23);
		contentPane.add(btnZarejestrujSi);
		
		textField_login = new JTextField();
		textField_login.setBounds(166, 58, 117, 20);
		contentPane.add(textField_login);
		textField_login.setColumns(10);
		textField_login.setText("admins");
		
		JLabel lblAdresSerwera = new JLabel("Adres serwera");
		lblAdresSerwera.setBounds(60, 111, 96, 14);
		contentPane.add(lblAdresSerwera);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(60, 136, 96, 14);
		contentPane.add(lblPort);
		
		textField_adres = new JTextField();
		textField_adres.setBounds(166, 108, 117, 20);
		textField_adres.setColumns(10);
		textField_adres.setText("localhost");
		contentPane.add(textField_adres);
		
		textField_port = new JTextField();
		textField_port.setBounds(166, 133, 117, 20);
		textField_port.setColumns(10);
		textField_port.setText("1234");
		contentPane.add(textField_port);
		
		JButton btnZaloguj = new JButton("Zaloguj");
		btnZaloguj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login = textField_login.getText();
				char[] password = passwordField.getPassword();
				passwordToString = String.valueOf(password);
				host = textField_adres.getText();
				try {
					port = Integer.parseInt(textField_port.getText());
					
					if(weryfikujDane(login,passwordToString,host))
					{
						response = connection.checkAuthorizationAfterLogin(login,passwordToString,host,port);
						System.out.println("odp: " +response);
						switch(response) {
							case "OK":
							{
								JOptionPane.showMessageDialog(frame, "Zalogowano pomyslnie");
								MainWindow mainWindow = new MainWindow(connection);
								mainWindow.setVisible(true);
								setVisible(false);
								break;
							}
							case "BRAK":
								JOptionPane.showMessageDialog(frame, "Nie ma takiego uzytkownika");
								break;
							case "WRONG":
								JOptionPane.showMessageDialog(frame, "Podane has³o jest nieprawid³owe");
								break;
							case "NOTCONNECTED":
								JOptionPane.showMessageDialog(frame, "Brak po³¹czenia z serwerem");
								break;
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "Nie wszytkie dane zosta³y podane lub podane s¹ nieprawid³owo! ");
					}
				}catch (Exception e)
				{
					JOptionPane.showMessageDialog(frame, "Podano nie poprawny port");
					return;
				}

			}
		});
		btnZaloguj.setBounds(166, 169, 117, 23);
		contentPane.add(btnZaloguj);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(166, 83, 117, 20);
		contentPane.add(passwordField);
	}
	
	boolean weryfikujDane(String login, String haslo, String host) {
		
			if(login.equals("") || haslo.equals("") || host.equals(""))
			{
				return false;
			}
			else
				return true;	
	}
}
