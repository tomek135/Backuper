import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.HashMap;

public class MainWindow extends JFrame{
		
	private JPanel contentPane;
	
	public MainWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}