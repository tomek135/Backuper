import java.io.BufferedOutputStream;
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

public class FileReceiver extends Thread {
	
	Socket socket;
	String fileName;
	String user;
	PrintWriter pw;
	private final CountDownLatch doneSignal;
	
	public FileReceiver(Socket socket, String fileName, CountDownLatch doneSignal, String user, PrintWriter pw) {
		this.doneSignal = doneSignal;
		this.socket = socket;
		this.fileName = fileName;
		this.user = user;
		this.pw = pw;
	}
	
	public void run() {
		try {
		System.out.println("poczatek przesylania");
		byte[] mybytearray = new byte[8192];
		InputStream is = socket.getInputStream();
		checkFile(fileName);
		if(checkFile(fileName) == false) {
			Files.createDirectories(Paths.get("P:/Backuper/Backuper/"+user));
			FileOutputStream fos = new FileOutputStream("P:/Backuper/Backuper/"+user+"/"+fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int count;
			while ((count = is.read(mybytearray)) > 0)
			{
				bos.write(mybytearray, 0, count);
			}
			System.out.println("koniec przesylania");
			//pw.println("SUCCESS");
			bos.close();
			fos.close();
		}else {
			//pw.println("ALREADY");
		}
		}catch(IOException e) {
			e.printStackTrace();
		}
		doneSignal.countDown();
  }
	
	boolean checkFile(String filename){
		// String size, String lastModification, String creationDate
		boolean change = false;
		try {
		Path p = Paths.get("P:/Backuper/Backuper/"+user+"/"+fileName);
		BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
		if(attr.size()== 15545) {
			change = true;
		}
		System.out.println(attr.size());
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return change;
		
	}
}
