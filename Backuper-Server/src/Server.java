
public class Server {

	public static void main(String[] args) {
		MainWindow frame = new MainWindow();
		frame.setVisible(true);
		Authentication ath = new Authentication("REGISTER","Marcos","fenix");
		Authentication ath2 = new Authentication("LOGIN","Marcos","fenix");
	}

}
