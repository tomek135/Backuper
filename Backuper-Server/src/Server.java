import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Server implements Runnable{
	
	private ServerSocket serverSocket;
	private Socket socket;
	
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
			ClientHandler.portSet.add(port);
			System.out.println("["+LocalDateTime.now()+"]"+"Serwer nas³uchuje na porcie: "+port);
			} catch (Exception e) {
				System.out.println("["+LocalDateTime.now()+"]"+"Wyst¹pi³ b³¹d: "+ e);
				
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
			System.out.println("["+LocalDateTime.now()+"]"+"Polaczenie nie moglo byc zamkniete");
		}finally {
			System.out.println("["+LocalDateTime.now()+"]"+"Serwer wyl¹czony ");
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
	             System.out.println("["+LocalDateTime.now()+"]"+"Client po³¹czy³ siê z Serwerem"); 
            } catch (IOException e) {
            	//close();
            	System.out.println("["+LocalDateTime.now()+"]"+ e);
  
            }  
       
          	} 
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}
}


