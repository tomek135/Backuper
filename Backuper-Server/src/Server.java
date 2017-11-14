import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
	private ServerSocket serverSocket;
	private Socket socket;

	public Server() 
	{
		try{
			serverSocket = new ServerSocket(1234);
			} catch (Exception e) {
				System.err.println("Create server socket:" + e);
				return;
			}
		new Thread(this).start();
	}
	
	@Override
	public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
						socket = serverSocket.accept();
						InputStream is = socket.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						OutputStream os = socket.getOutputStream();
						PrintWriter pw = new PrintWriter(os, true);
						System.out.println("ajjjjj");
						System.out.println(br.readLine());
					} catch (Exception e) {
						System.err.println("Server exception: " + e);
					}
			}
	
	}
	
	
	public static void main(String[] args) {
		new Server();
		MainWindow frame = new MainWindow();
		frame.setVisible(true);
		Authentication ath = new Authentication("REGISTER","Marcos","fenix");
		Authentication ath2 = new Authentication("LOGIN","Marcos","fenix");
	}
}


