import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.io.File;
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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private String file;
	private String katalog;
	private JScrollPane scrollPane;
	DefaultListModel listModel;
	JList list;
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
				     lblNewLabel.setText(katalog + file);
				     Path p = Paths.get(katalog+"/"+file);
				     BasicFileAttributes attr;
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
		});
		
		
		
		JButton btnNewButton_1 = new JButton("Wy\u015Blij");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					connection.fileListener("SEND", file,size);
			}
		});
		
		lblNewLabel = new JLabel("\u015Aciezka");
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
		btnNewButton_2.setBounds(10, 11, 130, 23);

		//String[] data = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5","Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 4", "Item 5","Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
		
		panel_1.setLayout(null);
		panel_1.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				connection.fileList("LIST");
				System.out.println("adasaaaa"+ Arrays.toString(connection.getList()));
				
				listModel=new DefaultListModel();
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
			}
		});
		
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Pobierz z serwera", null, panel_2, null);
	}
}
