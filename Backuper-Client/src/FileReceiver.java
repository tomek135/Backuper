import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceiver extends Thread {
	Socket socket;
	int fileLenght;
	String fileName;
	
	public FileReceiver(Socket socket, int fileLenght, String fileName) {
		this.socket = socket;
		this.fileLenght = fileLenght;
		this.fileName = fileName;
	}
	
	public void run() {
		try {
		byte[] mybytearray = new byte[fileLenght + 1000];
		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream(fileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		int bytesRead = is.read(mybytearray, 0, mybytearray.length);
		bos.write(mybytearray, 0, bytesRead);
		bos.close();
		fos.close();
		is.close();
		socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
  }
}
