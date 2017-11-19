import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class FileSender extends Thread {
	Socket socket;
	File file;
	PrintWriter pw;
	
	public FileSender(Socket socket, File file, PrintWriter pw) {
		this.socket = socket;
		this.file = file;
		this.pw = pw;
	}
	public void run() {
		try {
			int privatePort = getPort();
			ServerSocket privateServerSocket = new ServerSocket(privatePort);
			System.out.println("przed wyslaniem portu");
			pw.println(privatePort);
			Socket privateSocket = privateServerSocket.accept();
			System.out.println("poczatek przesylania");
			byte[] mybytearray = new byte[8192];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream("admins/"+file));
			OutputStream os = privateSocket.getOutputStream();
			int count;
			while ((count = bis.read(mybytearray)) > -1)
			{
			  os.write(mybytearray, 0, count);
			}
			System.out.println("koniec przesylania");
			os.close();
			bis.close();
			privateSocket.close();
			privateServerSocket.close();
			System.out.println("koniec");
		}catch(IOException e) {
			e.printStackTrace();
		}
    }
	
	int getPort() {
		Random rand = new Random();
		int  port = rand.nextInt(8000) + 1000;
		while(ClientHandler.portSet.contains(port)) {
			port = rand.nextInt(8000) + 1000;
		}
		return port;
		
	}
 }
