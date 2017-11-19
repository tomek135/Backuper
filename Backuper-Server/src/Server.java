import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
	
	private ServerSocket serverSocket;
	private Socket socket;
		
	public Server() {
		
	}
	
	public Server(int port) 
	{
		try{
			serverSocket = new ServerSocket(port);
			ClientHandler.portSet.add(port);
			System.out.println("Server is working on port: "+port);
			} catch (Exception e) {
				System.err.println("Create server socket:" + e);
				return;
			}
		new Thread(this).start();
	}
	
	public void close()
	{
		try{
			socket.close();
			serverSocket.close();
		}catch (IOException e) {
			System.out.println("Polaczenie nie moglo byc zamkniete");
		}finally {
			System.out.println("Serwer wyl¹czony ");
			System.exit(0);
		}
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true){ 
            try {
				 socket = serverSocket.accept();
	             ClientHandler handler = new ClientHandler(socket); 
	             handler.start();
	             System.out.println("Connection established"); 
            } catch (IOException e) {
            	close();
            }  
       
          	} 
	}
	
	public static void main(String[] args) {
		MainWindow frame = new MainWindow();
	}
}


