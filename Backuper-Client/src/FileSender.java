import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileSender extends Thread {
	
	Socket socket;
	File file;
	long size;
	BufferedInputStream bis;
	OutputStream os;
	
	public FileSender(Socket socket,String filename, File file,long size) {
		
		this.socket = socket;
		this.file = file;
		this.size = size;
		
	}
	public void run() {
		try {
			byte[] mybytearray = new byte[8192];
			bis = new BufferedInputStream(new FileInputStream(file));
			os = socket.getOutputStream();
			int count;
			while ((count = bis.read(mybytearray)) > 0)
			{
			  os.write(mybytearray, 0, count);
			}
				close();
				System.out.println("koniec");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			os.close();
			bis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("kappa");
		}
		
	}
 }
