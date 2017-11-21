import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Random;

public class FileReceiver extends Thread {
	
	Socket socket;
	String fileName;
	String user;
	PrintWriter pw;
	String path=System.getProperty("user.dir");
	long size = 0;
	OutputStream os;
	
	public FileReceiver(Socket socket, String fileName, String user, PrintWriter pw, long size) {
		this.socket = socket;
		this.fileName = fileName;
		this.user = user;
		this.pw = pw;
		this.size = size;
		
	}
	
	public void run() {
		
		try {
			int privatePort = getPort();
			ServerSocket privateServerSocket = new ServerSocket(privatePort);
			System.out.println("przed wyslaniem portu");
			pw.println(privatePort);
			Socket privateSocket = privateServerSocket.accept();
			System.out.println("poczatek przesylania");
			System.out.println(path);
			byte[] mybytearray = new byte[8192];
			InputStream is = privateSocket.getInputStream();
			Files.createDirectories(Paths.get(path+"/"+ user));
			FileOutputStream fos = new FileOutputStream(path+"/"+user+"/"+fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int count;
			while ((count = is.read(mybytearray)) > 0)
			{
				bos.write(mybytearray, 0, count);
			}
			bos.close();
			fos.close();
			privateSocket.close();
			privateServerSocket.close();	
		}catch(IOException e) {
			System.out.println("Client zamkniety");
		}
  }
	
	boolean checkFile(String filename){
		boolean change = false;
		Path p = Paths.get(path+"/"+user+"/"+fileName);
		System.out.println("Œciezka: " + p.toString());
		File f = new File(p.toString());
		if(f.exists() && !f.isDirectory()) { 
			try {
				BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
					if(attr.size()== size) {
						change = true;
						System.out.println("Ten sam plik");
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
		}
		return change;
		
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
