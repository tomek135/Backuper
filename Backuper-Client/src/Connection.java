import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
	JFrame frame;
	
	/**
	 * Funkcja wysy�a na serwer login i has�o wpisane przez u�ytkownika,
	 * ��czy si� z serwerem na hoscie i porcie podanym przez u�ytkownika podczas rejestracji 
	 * @param toSend
	 * @param host
	 * @param port
	 * @return
	 */
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
					System.out.println("Brak po��czenia z serwerem");
					response = "NOTCONNECTED";				
				}catch (Exception e) {
					response = "NOTCONNECTED";
				}
					return response;
		}
		
	/**
	 * Funkcja wysy�aj�ca na serwer dane u�ytkownika podczas logowania
	 * @param login
	 * @param haslo
	 * @param host
	 * @param port
	 * @return odpowiedz od serwera
	 */
	String checkAuthorizationAfterLogin(String login,String haslo,String host,int port) {
		this.login = login;
		String response = null;
		dataToSend = "LOGIN"+";"+login+";"+haslo;
		System.out.println("data to send" +dataToSend);
		response =  SendToServer(dataToSend,host,port);
		if(response.equals("OK")) {

		}
		else {
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
				System.out.println("Brak po��czenia z serwerem");
				return "NOTCONNECTED";
			}
		}
		return response;
	}
	
	/**
	 * Funkcja wysy�aj�ca na serwer dane u�ytkownika podczas rejestracji 
	 * @param login
	 * @param haslo
	 * @param host
	 * @param port
	 * @return odpowiedz od serwera
	 */
	
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
		}catch (IOException e) {
			return "NOTCONNECTED";
		}catch (Exception e) {
			System.out.println("Brak po��czenia z serwerem");
			return "NOTCONNECTED";
		}
		return response;
	}
	
	/**
	 * Funkcja pozwalaj�ca na wys�anie pliku na serwer, pobranie pliku z serwera, usuni�cie pliku z serwera
	 * @param command - zmienna oznaczaj�ca jak� akcj� ma wykona� serwer - wys�anie, pobranie, usuni�cie
	 * @param filename - nazwa pliku jaki ma zosta� wys�any, pobrany, usuni�ty
	 * @param size - rozmiar pliku wysy�anego
	 * @param frame - g��wne okno programu
	 * @param directory - �cie�ka do folderu gdzie ma zosta� zapisany pobrany plik
	 */
	
	void fileListener(String command, String filename, long size, JFrame frame, String directory) {
	try {	
		if(command.equals("SEND")) {
			try {
				File myFile = new File(filename);
				pw.println(command+";"+filename+";"+size);
				FileSender sender = new FileSender(socket, filename, myFile,size, br, host,frame);
				sender.start();
			}catch(NullPointerException e) {
				JOptionPane.showMessageDialog(frame, "Nie wybrano pliku do wys�ania.");
			}
		}else if(command.equals("DOWNLOAD")){ 
				pw.println(command+";"+filename);
				FileReceiver receiver = new FileReceiver(socket, filename, host, br, frame, directory);
				receiver.start();
		}else if(command.equals("DELETE")){ 
			pw.println(command+";"+filename);
	    }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Funkcja wysy�aj�ca na serwer komend� wyswietlenia listy plik�w kt�re znajaduj� si� w folderze danego u�ytkownika
	 * @param command - komenda pozwalaj�ca serwerowi okre�li� jak� funkcj� ma wykona�
	 */
	void fileList(String command) {
		pw.println(command+";"+login);
		try {
			String message = br.readLine();
			if(message.equals("")) {
				list[0] = "";
				JOptionPane.showMessageDialog(frame, "Na serwerze nie ma �adnych plik�w.");
			}else {
				list = message.split(";");
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Po��czenie zosta�o przerwane. Uruchom program jeszcze raz.");
		}
	
	}
	
	/**
	 * Funkcja zwracaj�ca list� wys�anych plik�w
	 * @return - lista plik�w na serwerze
	 */
	String[] getList() {
		return list;
	}
	
}

