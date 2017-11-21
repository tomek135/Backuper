import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server implements Runnable{
	
	private ServerSocket serverSocket;
	private Socket socket;
	static Set<Integer> portSet = new HashSet<Integer>();
			
	public Server() {
		
	}
	
	/**
	 * Konstruktor tworzacy socket serwera i uruchamiajcy jego watek
	 * @param port
	 */
	public Server(int port) 
	{
		try{
			serverSocket = new ServerSocket(port);
			Server.portSet.add(port);
			System.out.println("Serwer nas³uchuje na porcie: "+port);
			} catch (Exception e) {
				System.out.println("Wyst¹pi³ b³¹d");
				
			}
		new Thread(this).start();
	}
	
	/**
	 * Funkcja konczaca dzialanie programu
	 */
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
	/**
	 * Funkcja akceptujaca nowych uzytkownikow, tworzaca nowy watek dla kazdego z nich
	 */

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


