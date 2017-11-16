import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MainWindow extends JFrame{
		
	private JPanel contentPane;
	private JTextField textField_port;
	Server serwer = new Server();
	
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
		setTitle("LogAgent");
		JLabel lblNewLabel = new JLabel("PORT");
		lblNewLabel.setBounds(132, 11, 36, 25);
		
		textField_port = new JTextField();
		textField_port.setBounds(85, 57, 130, 20);
		textField_port.setColumns(10);
		
		JButton wlaczSerwer = new JButton("W\u0142\u0105cz serwer");
		wlaczSerwer.setBounds(85, 107, 130, 23);
		
		JButton wylaczSerwer = new JButton("Wy\u0142\u0105cz serwer");
		wylaczSerwer.setBounds(85, 148, 130, 23);
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
				wylaczSerwer.setEnabled(false);
				serwer.close();
				setVisible(false);
            }
			
		});
		wlaczSerwer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
            {
				int port = Integer.parseInt(textField_port.getText());
				new Server(port);
            }			
		});	
	}
	

	
}