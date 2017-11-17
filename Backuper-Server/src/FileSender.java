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

			byte[] mybytearray = new byte[8192];
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			OutputStream os = socket.getOutputStream();
			int count;
			while ((count = bis.read(mybytearray)) > -1)
			{
			  os.write(mybytearray, 0, count);
			}
			System.out.println("koniec przesylania");
			os.close();
			bis.close();
			System.out.println("koniec");
		}catch(IOException e) {
			e.printStackTrace();
		}
    }
 }
