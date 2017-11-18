import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;

public class FileReceiver extends Thread {
	
	Socket socket;
	String fileName;
	String user;
	PrintWriter pw;
	private final CountDownLatch doneSignal;
	String path=System.getProperty("user.dir");
	long size = 0;
	
	public FileReceiver(Socket socket, String fileName, CountDownLatch doneSignal, String user, PrintWriter pw, long size) {
		this.doneSignal = doneSignal;
		this.socket = socket;
		this.fileName = fileName;
		this.user = user;
		this.pw = pw;
		this.size = size;
		//path=System.getProperty("user.dir");
	}
	
	public void run() {
		try {
		System.out.println("poczatek przesylania");
		System.out.println(path);
		byte[] mybytearray = new byte[8192];
		InputStream is = socket.getInputStream();
		//checkFile(fileName);
		if(true) {
			Files.createDirectories(Paths.get(path+"/"+ user));
			FileOutputStream fos = new FileOutputStream(path+"/"+user+"/"+fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int count;
			while ((count = is.read(mybytearray)) > 0)
			{
				bos.write(mybytearray, 0, count);
				System.out.println(count);
			}
			System.out.println("koniec przesylania");
			//pw.println("SUCCESS");
			bos.close();
			fos.close();
		}else {
			//pw.println("ALREADY");
		}
		}catch(IOException e) {
			System.out.println("Client zamkniety");
		}
		doneSignal.countDown();
  }
	
	boolean checkFile(String filename){
		// String size, String lastModification, String creationDate
		boolean change = false;
		
		Path p = Paths.get(path+"/"+user+"/"+fileName);
		System.out.println("sciezka: " + p.toString());
		File f = new File(p.toString());
		if(f.exists() && !f.isDirectory()) { 
			try {
				System.out.println("istnieje");
				BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
				if(attr.size()== size) {
					change = true;
					System.out.println("ten sam plik");
				}
				System.out.println(attr.size() + "aaa"+f.length()+"aaaa"+size);
				
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return change;
		
	}
}
