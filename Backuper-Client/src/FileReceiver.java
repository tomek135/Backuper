import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceiver extends Thread {
	Socket socket;
	String fileName;
	
	public FileReceiver(Socket socket, String fileName) {
		this.socket = socket;
		this.fileName = fileName;
	}
	
	public void run() {
		try {
		byte[] mybytearray = new byte[8192];
		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream(fileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		int count;
		while ((count = is.read(mybytearray)) > -1)
		{
		  bos.write(mybytearray, 0, count);
		}
		bos.close();
		fos.close();
		is.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
  }
}
