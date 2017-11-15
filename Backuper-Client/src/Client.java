import java.awt.EventQueue;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Connection connection = new Connection();
						MainWindow frame = new MainWindow(connection);
						frame.setVisible(true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
}
