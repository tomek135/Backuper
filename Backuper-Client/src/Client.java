import java.awt.EventQueue;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Connection connection = new Connection();
						LoginWindow frame = new LoginWindow(connection);
						frame.setVisible(true);
						//tutej wywo³ywane okno
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
}
