import java.awt.EventQueue;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MainWindow frame = new MainWindow();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
}
