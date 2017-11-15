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
	
	public FileSender(Socket socket, File file) {
		this.socket = socket;
		this.file = file;
	}
	public void run() {
		try {

			byte[] mybytearray = new byte[(int) file.length()];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			bis.read(mybytearray, 0, mybytearray.length);
			OutputStream os = socket.getOutputStream();
			os.write(mybytearray, 0, mybytearray.length);
			os.close();
			bis.close();
			socket.close();
			System.out.println("koniec");
		}catch(IOException e) {
			e.printStackTrace();
		}
    }
 }
