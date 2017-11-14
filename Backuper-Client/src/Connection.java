import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class Connection {
	
	static Socket socket;
	static InetAddress address;
	static String dataToSend = "";

	static void SendToServer(String toSend,String host,int port) {
		
		try{
			socket = new Socket("localhost", port);
			System.out.println(host +" "+ port);
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);
			pw.println(toSend);
			InputStream is = socket.getInputStream();
			BufferedReader br = new BufferedReader(
			new InputStreamReader(is));
			System.out.println(br.readLine());
	
			}catch (UnknownHostException exc) {
					System.out.println("Nieznany host: " + host);
			}catch (Exception e) {
					System.err.println("Client exception: " + e);
			}
	
	}
	
	static void checkAuthorizationAfterLogin(String login,String haslo,String host,int port) {
		
		dataToSend = "LOGIN"+";"+login+";"+haslo;
		SendToServer(dataToSend,host,port);
		
	}
	
	static void checkAuthorizationAfterRegister(String login, String haslo,String host,int port) {
		dataToSend = "REGISTER"+";"+login+";"+haslo;
		SendToServer(dataToSend,host,port);
	}
}