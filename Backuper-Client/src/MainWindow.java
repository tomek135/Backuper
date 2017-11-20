import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class MainWindow extends JFrame {

	private JFrame frame = this;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private String file;
	private String katalog;
	private JScrollPane scrollPane;
	DefaultListModel listModel;
	JList list;
	boolean listSelected = false;
	String path;
	long size = 0;
	int i = 0;

	/**
	 * Create the frame.
	 */
	public MainWindow(Connection connection) {
		
		path = System.getProperty("user.dir");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450,300);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((screen.getWidth() - getWidth()) /2);
		int y = (int) ((screen.getHeight() -getHeight()) /2);
		setLocation(x, y); 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(200, 250));
		contentPane.add(tabbedPane, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Wybór pliku", null, panel, null);
		
		JButton btnNewButton = new JButton("Wybierz plik");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fd =new FileDialog(new MainWindow(connection),"Wczytaj",FileDialog.LOAD);
				     fd.setVisible(true);
				     katalog=fd.getDirectory();
				     file = fd.getFile();
				     Path p = Paths.get(katalog+"/"+file);
				     BasicFileAttributes attr;
				     if(czyWybranoPlik(file,katalog))
				     {
				    	lblNewLabel.setText(katalog + file);
						try {
							attr = Files.readAttributes(p, BasicFileAttributes.class);
							size = attr.size();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					     
					     System.out.println("Wybrano plik: " + file);
					     System.out.println("w katalogu: "+ katalog);
					     System.out.println("Œcie¿ka: "+ katalog + file); 
				     }
				     else {
				    	 JOptionPane.showMessageDialog(frame, "Plik nie zosta³ wybrany.");
				     }
			}
		});
		
		
		
		JButton btnNewButton_1 = new JButton("Wy\u015Blij");
		btnNewButton_1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			try {
				connection.fileListener("SEND", file,size,frame,file);
				}catch(Exception e) {
					JOptionPane.showMessageDialog(frame, "Po³¹czenie zosta³o przerwane. Uruchom program jeszcze raz.");
				}
			}
		});
		
		lblNewLabel = new JLabel("");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(84)
							.addComponent(btnNewButton)
							.addGap(35)
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(70)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(73, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1))
					.addGap(96))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
	
		tabbedPane.addTab("Pliki na serwerze", null, panel_1, null);
		
		JButton btnNewButton_2 = new JButton("Wy\u015Bwietl list\u0119");
		btnNewButton_2.setBounds(0, 11, 130, 23);
		
		panel_1.setLayout(null);
		panel_1.add(btnNewButton_2);
		listModel=new DefaultListModel();
		JButton btnNewButton_3 = new JButton("Usu\u0144 plik");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(listSelected == true) {
						if(list.getSelectedValue() != null) {
							file = (String) list.getSelectedValue();
							connection.fileListener("DELETE", file, size, frame, null);
							connection.fileList("LIST");
							System.out.println(Arrays.toString(connection.getList()));
							

							scrollPane.setVisible(false);
							listModel.clear();
									
							for (int i=0; i<connection.getList().length; i++) {
							  listModel.addElement(connection.getList()[i]);
							  System.out.println(connection.getList()[i]);
							}
							list = new JList(listModel);
						    scrollPane = new JScrollPane(list);
							scrollPane.setBounds(0, 45, 429, 177);
							panel_1.add(scrollPane);
							scrollPane.setVisible(true);
							scrollPane.setVisible(false);
							scrollPane.setVisible(true);
							JOptionPane.showMessageDialog(frame, "Plik " + file+ " zosta³ usuniêty.");
							
						}else {
							JOptionPane.showMessageDialog(frame, "Wybierz plik do usuniêcia.");
						}
				}else {
					JOptionPane.showMessageDialog(frame, "Wyswietl liste aby wybraæ plik.");
				}
			}
		});
		btnNewButton_3.setBounds(148, 11, 130, 23);
		panel_1.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Pobierz plik");
		btnNewButton_4.setBounds(299, 11, 130, 23);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(listSelected == true) {
					if(list.getSelectedValue() != null) {
				        JFileChooser f = new JFileChooser();
				        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
				        f.showSaveDialog(null);
				        String directory = f.getCurrentDirectory().getAbsolutePath();
						file = (String) list.getSelectedValue();
						connection.fileListener("DOWNLOAD", file, size, frame, directory);
						
					}else {
						JOptionPane.showMessageDialog(frame, "Wybierz plik do usuniêcia");
					}
				}else {
					JOptionPane.showMessageDialog(frame, "Wyswietl liste aby wybraæ plik");
				}
			}
		});
		
		panel_1.add(btnNewButton_4);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listSelected = true;
				try {
					connection.fileList("LIST");
					System.out.println(Arrays.toString(connection.getList()));
					
					if(i>0)
					{
						scrollPane.setVisible(false);
						listModel.clear();
					}				
					for (int i=0; i<connection.getList().length; i++) {
					  listModel.addElement(connection.getList()[i]);
					  System.out.println(connection.getList()[i]);
					}
					list = new JList(listModel);
				    scrollPane = new JScrollPane(list);
					scrollPane.setBounds(0, 45, 429, 177);
					panel_1.add(scrollPane);
					scrollPane.setVisible(true);
					scrollPane.setVisible(false);
					scrollPane.setVisible(true);
					i++;
				}catch(Exception e) {
					JOptionPane.showMessageDialog(frame, "Po³¹czenie zosta³o przerwane. Uruchom program jeszcze raz.");
				}
			}
		});
		
	}
	
	boolean czyWybranoPlik(String plik, String katalog) {
		if (plik !=null && katalog != null)
			return true;
		else
			return false;
	}
}
