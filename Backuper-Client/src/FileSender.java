import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FileSender extends Thread {
	
	Socket socket;
	File file;
	long size;
	BufferedInputStream bis;
	OutputStream os;
	BufferedReader br;
	String host;
	JFrame frame;
	
	public FileSender(Socket socket,String filename, File file,long size, BufferedReader br, String host, JFrame frame) {
		
		this.socket = socket;
		this.file = file;
		this.size = size;
		this.host = host;
		this.br = br;
		this.frame = frame;
	}
	public void run() {
		try {
			int privatePort = Integer.parseInt(br.readLine());
			Socket privateSocket = new Socket(host, privatePort);
			byte[] mybytearray = new byte[8192];
			bis = new BufferedInputStream(new FileInputStream(file));
			os = privateSocket.getOutputStream();
			int count;
			while ((count = bis.read(mybytearray)) > 0)
			{
			  os.write(mybytearray, 0, count);
			}
			JOptionPane.showMessageDialog(frame, "Plik "+ file+ " zosta� wys�any");
			os.close();
			bis.close();
			privateSocket.close();
				System.out.println("koniec");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	

 }
