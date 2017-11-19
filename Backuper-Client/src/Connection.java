import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.ConnectException;
import java.rmi.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Connection {

	Socket socket;
	String host;
	String dataToSend;
	String login;
	int port;
	String[] list = {""};
	OutputStream os;
	PrintWriter pw;
	InputStream is;
	BufferedReader br;
	FileList fileList;
	
	

	String SendToServer(String toSend,String host,int port) {
			String response = null;
			
			try{
				socket = new Socket(host, port);
				os = socket.getOutputStream();
				pw = new PrintWriter(os, true);
				pw.println(toSend);
				is = socket.getInputStream();
				br = new BufferedReader(
				new InputStreamReader(is));
				response = br.readLine();
		
				
				}catch (UnknownHostException exc) {
						System.out.println("Nieznany host: " + host);
						response = "NOTCONNECTED";
				}catch (ConnectException exc) {
					System.out.println("Brak po³¹czenia z serwerem");
					response = "NOTCONNECTED";				
				}catch (Exception e) {
					response = "NOTCONNECTED";
				}
					return response;
		}
		
	String checkAuthorizationAfterLogin(String login,String haslo,String host,int port) {
		this.login = login;
		String response = null;
		dataToSend = "LOGIN"+";"+login+";"+haslo;
		System.out.println("data to send" +dataToSend);
		response =  SendToServer(dataToSend,host,port);
		if(response.equals("OK")) {

		}else {
			try {
				br.close();
				is.close();
				pw.close();
				os.close();
				socket.close();
			} catch (IOException e) {
				return "NOTCONNECTED";
				//e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Brak po³¹czenia z serwerem");
				return "NOTCONNECTED";
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
			return "NOTCONNECTED";
		}catch (Exception e) {
			System.out.println("Brak po³¹czenia z serwerem");
			return "NOTCONNECTED";
		}
		return response;
	}
	
	
	void fileListener(String command, String filename, long size, JFrame frame, String directory) {
	try {	
		if(command.equals("SEND")) {
				File myFile = new File(filename);
				pw.println(command+";"+filename+";"+size);
				FileSender sender = new FileSender(socket, filename, myFile,size, br, host,frame);
				sender.start();
		}else if(command.equals("DOWNLOAD")){ 
				pw.println(command+";"+filename);
				FileReceiver receiver = new FileReceiver(socket, filename, host, br, frame, directory);
				receiver.start();
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	void fileList(String command) {
		pw.println(command+";"+login);
		try {
			String message = br.readLine();
			list = message.split(";");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	String[] getList() {
		return list;
	}
	
}

