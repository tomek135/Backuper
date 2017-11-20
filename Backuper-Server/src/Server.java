import java.io.IOException;
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
			System.out.println("Serwer nas³uchuje na porcie: "+port);
			} catch (Exception e) {
				System.out.println("Wyst¹pi³ b³¹d");
				
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
            	//close();
            	e.printStackTrace();
            }  
       
          	} 
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}
}


