import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class Connection {
	
	Socket socket;
	String host;
	 String dataToSend;
	int port;
	OutputStream os;
	PrintWriter pw;
	InputStream is;
	 BufferedReader br;
	
	
	String SendToServer(String toSend,String host,int port) {
			String response = null;
			
			try{
				socket = new Socket("localhost", port);
				os = socket.getOutputStream();
				pw = new PrintWriter(os, true);
				pw.println(toSend);
				is = socket.getInputStream();
				br = new BufferedReader(
				new InputStreamReader(is));
				response = br.readLine();
		
				}catch (UnknownHostException exc) {
						System.out.println("Nieznany host: " + host);
				}catch (Exception e) {
						System.err.println("Client exception: " + e);
				}
			return response;
		}
		
	String checkAuthorizationAfterLogin(String login,String haslo,String host,int port) {
		String response = null;
		dataToSend = "LOGIN"+";"+login+";"+haslo;
		response =  SendToServer(dataToSend,host,port);
		if(response.equals("OK")) {
			fileListener();
		}else {
			try {
				br.close();
				is.close();
				pw.close();
				os.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}
	
	String checkAuthorizationAfterRegister(String login, String haslo,String host,int port) {
		String response = null;
		dataToSend = "REGISTER"+";"+login+";"+haslo;
		response = SendToServer(dataToSend,host,port);
		try {
			br.close();
			is.close();
			pw.close();
			os.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	static void fileListener() {
		
	}
}

