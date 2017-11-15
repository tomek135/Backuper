import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
;

public class ClientHandler extends Thread {

	Socket socket; 
	BufferedReader br;
	InputStream is;
	OutputStream os;
	PrintWriter pw;
	boolean exit = true;
	public ClientHandler(Socket socket) throws IOException { 
        this.socket = socket; 
    } 

   public void run() {
	   try {
		   is = socket.getInputStream();
		   br = new BufferedReader(new InputStreamReader(is));
		   os = socket.getOutputStream();
		   pw = new PrintWriter(os, true);
		
		   while(exit) {
				String[] message = br.readLine().split(";");
				String action = message[0];
				String login = message[1];
				String password = message[2];
				System.out.println(action);				
				Authentication auth = new Authentication();
				String messageBack = auth.authenticate(action, login, password);
				System.out.println("messageBack" + messageBack);
				if(messageBack.equals("OK")) {
					//fileListener();
					pw.println(messageBack);
				}else {
					pw.println(messageBack);
					exit=false;
					br.close();
					is.close();
					pw.close();
					os.close();
					socket.close();
				}
		   }
		System.out.println("koniec");
		
	   } catch (Exception e) {
		   System.err.println("Server exception: " + e);
	   }
	
   }
   
	void fileListener() {
		pw.println("OK");
		while(true) {
			
			String[] message = null;;
			try {
				message = br.readLine().split(";");
			} catch (IOException e) {
				e.printStackTrace();
			}
			String command = message[0];

			if(command.equals("SEND")) {
					int fileLenght = Integer.parseInt(message[1]); 
					String filename = message[2];
					FileReceiver receiver = new FileReceiver(socket, fileLenght, filename);
					receiver.start();
			}else if(command.equals("DOWNLOAD")){ 
					String filenames = message[1];
					File myFile = new File(filenames);
					int filelenghts = (int)(myFile.length());
					pw.println(filelenghts);
					FileSender sender = new FileSender(socket, myFile);
					sender.start();
			}
	}
	}
}

