import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket serverSocket;
	private Socket socket;
		
	public void createServer(){
		 System.out.println("Server is working");
			try{
				serverSocket = new ServerSocket(1234);
				} catch (Exception e) {
					System.err.println("Create server socket:" + e);
					return;
				}
		 
			while (true){ 
	             try {
					 socket = serverSocket.accept();
		             ClientHandler handler = new ClientHandler(socket); 
		             handler.start();
		             System.out.println("Connection established"); 
	             } catch (IOException e) {
					e.printStackTrace();
	             }  
            
	           	} 
	}
	
	
	public static void main(String[] args) {
		MainWindow frame = new MainWindow();
		Server server = new Server();
		server.createServer();
		

	}
}


