import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FileReceiver extends Thread {
	Socket socket;
	String fileName;
	String host;
	BufferedReader br;
	JFrame frame;
	String directory;
	
	public FileReceiver(Socket socket, String fileName, String host, BufferedReader br, JFrame frame, String directory) {
		this.socket = socket;
		this.fileName = fileName;
		this.host = host;
		this.br = br;
		this.frame = frame;
		this.directory = directory;
	}
	
	public void run() {
		try {
			String mes = br.readLine();
			System.out.println("["+LocalDateTime.now()+"]"+"Port: " + mes);
			int privatePort = Integer.parseInt(mes);
			Socket privateSocket = new Socket(host, privatePort);
			byte[] mybytearray = new byte[8192];
			InputStream is = privateSocket.getInputStream();
			System.out.println("["+LocalDateTime.now()+"]"+"Œcie¿ka: "+ directory +"/" + fileName);
			FileOutputStream fos = new FileOutputStream(directory+"/"+ fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int count;
			while ((count = is.read(mybytearray)) > 0)
			{
			  bos.write(mybytearray, 0, count);
			}
			JOptionPane.showMessageDialog(frame, "Plik "+ fileName + " zosta³ pobrany.S");
			bos.close();
			fos.close();
			is.close();
			privateSocket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
  }
}
