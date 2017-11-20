import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainWindow extends JFrame{
	
	private JFrame frame = this;
	private JPanel contentPane;
	private JTextField textField_port;
	Server serwer;
	boolean isOn = false;
	
	public MainWindow() {
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((screen.getWidth() - getWidth()) /2);
		int y = (int) ((screen.getHeight() -getHeight()) /2);
		setLocation(x, y); 
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		setBackground(Color.LIGHT_GRAY);
		setTitle("Serwer");
		JLabel lblNewLabel = new JLabel("PORT");
		lblNewLabel.setBounds(132, 11, 36, 25);
		
		textField_port = new JTextField();
		textField_port.setBounds(85, 57, 130, 20);
		textField_port.setColumns(10);
		
		JButton wlaczSerwer = new JButton("W\u0142\u0105cz serwer");
		wlaczSerwer.setBounds(85, 107, 130, 23);
		
		JButton wylaczSerwer = new JButton("Wy\u0142\u0105cz serwer");
		wylaczSerwer.setBounds(85, 148, 130, 23);
		wylaczSerwer.setEnabled(false);
		contentPane.setLayout(null);
		contentPane.add(lblNewLabel);
		contentPane.add(textField_port);
		contentPane.add(wlaczSerwer);
		contentPane.add(wylaczSerwer);
		setVisible(true);
		
		wylaczSerwer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
            {
				
				serwer.close();
				setVisible(false);
            }
			
		});
		wlaczSerwer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
            {
				String portString = textField_port.getText();
				if((portString.equals("") || !isInteger(portString)))
					JOptionPane.showMessageDialog(frame, "Wpisz prawid³owy numer portu!");
				else {
					int port = Integer.parseInt(textField_port.getText());
					if(port<65536 && isOn == false)
					{
						wylaczSerwer.setEnabled(true);
						serwer = new Server(port);
						isOn = true;
					}
					else if(isOn == true) {
						JOptionPane.showMessageDialog(frame, "Serwer jest juz uruchomiony.");
					}
					else if(isOn == false) {
						JOptionPane.showMessageDialog(frame, "Maksymalny numer portu to 2^16-1 (65535).");
					}
				}
            }			
		});	
	}
	
	boolean isInteger(String port) {
	    try { 
	        Integer.parseInt(port); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}

	
}