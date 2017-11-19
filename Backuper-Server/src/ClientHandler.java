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
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
;

public class ClientHandler extends Thread {
	
	static Set<Integer> portSet = new HashSet<Integer>();
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

				String filename = table[1];
				size = Long.parseLong(table[2]);
				FileReceiver receiver = new FileReceiver(socket, filename, user, pw, size);
				receiver.start();
			}else if(command.equals("DOWNLOAD")){
				String filenames = table[1];
				File myFile = new File(filenames);
				FileSender sender = new FileSender(socket, myFile, pw);
				sender.start();
			}else if(command.equals("LIST")){
				CountDownLatch doneSignal = new CountDownLatch(1);
				FileList fileList = new FileList(table[1],doneSignal);
				fileList.start();
				doneSignal.await();
				pw.println(fileList.getFiles());
				System.out.println("bbnbnbnbn:" +fileList.getFiles());
			}	
			
		} catch (IOException e) {
			close();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void close()
	{
		try{
			socket.close();
		}catch (IOException e) {
			System.out.println("Polaczenie nie moglo byc zamkniete");
		}finally {
			System.exit(0);
		}
	}
	
	
}	

