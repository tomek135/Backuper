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
import java.util.concurrent.CountDownLatch;
;

public class ClientHandler extends Thread {

	Socket socket;
	String user;
	BufferedReader br;
	InputStream is;
	InputStreamReader isr;
	OutputStream os;
	PrintWriter pw;
	boolean exit = true;
	long size;
	public ClientHandler(Socket socket) throws IOException { 
        this.socket = socket; 
    } 

   public void run() {
	   try {
		   is = socket.getInputStream();
		   isr = new InputStreamReader(is);
		   br = new BufferedReader(isr);
		   os = socket.getOutputStream();
		   pw = new PrintWriter(os, true);
		

		   String[] message = br.readLine().split(";");
		   String action = message[0];
		   String login = message[1];
		   String password = message[2];
		   System.out.println(action);				
		   Authentication auth = new Authentication();
		   String messageBack = auth.authenticate(action, login, password);
		   System.out.println("messageBack" + messageBack);
		   if(messageBack.equals("OK")) {
			   user = login;
			   pw.println("OK");
			   while(exit) {
				   fileListener();
			   }
		   }else {
			   pw.println(messageBack);
			   exit=false;
			   br.close();
			   is.close();
			   pw.close();
			   os.close();
			   socket.close();
		   }

		System.out.println("koniec");
		
	   } catch (Exception e) {
		   System.err.println("Server exception: " + e);
		   e.printStackTrace();
	   }
	
   }
   
	void fileListener()  {
		
		try {

			String[] table = null;		
			String message = br.readLine();
			
			table = message.split(";");
			String command = table[0];

			if(command.equals("SEND")) {
				CountDownLatch doneSignal = new CountDownLatch(1);
				String filename = table[1];
				size = Long.parseLong(table[2]);
				FileReceiver receiver = new FileReceiver(socket, filename, doneSignal, user, pw,size);
				receiver.start();
				doneSignal.await();
			}else if(command.equals("DOWNLOAD")){
				CountDownLatch doneSignal = new CountDownLatch(1);
				String filenames = table[1];
				File myFile = new File(filenames);
				FileSender sender = new FileSender(socket, myFile);
				sender.start();
				doneSignal.await();
			}			
			exit=false;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();		
		}

	}
}	

