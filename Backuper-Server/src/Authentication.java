import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;



public class Authentication{
	
	public String authenticate(String action, String login, String password) {
		HashMap<String, String> userData= loadUserData();
		String raport = null;
		
		if(action.equals("LOGIN")) {
			if(userData.keySet().contains(login) && userData.get(login).equals(password))
			{
				raport = "OK";
			}else if(userData.keySet().contains(login) && !userData.get(login).equals(password)){
				raport = "WRONG";
			}else {
				raport = "BRAK";
			}
		}else if(action.equals("REGISTER")){
			if(userData.keySet().contains(login))
			{
				raport = "BUSY";
			}else {
				userData.put(login, password);
				saveUserData(userData);
				String path=System.getProperty("user.dir");
				try {
					Files.createDirectories(Paths.get(path+"/"+ login));
				} catch (IOException e) {
					e.printStackTrace();
				}
				raport = "REG";
			}

		}
		return raport;
		
	}
	
	public void createMap() {
		HashMap<String, String> userData= new HashMap<String, String>();
		
		try
        {
               FileOutputStream fos = new FileOutputStream("userData.ser");
               ObjectOutputStream oos = new ObjectOutputStream(fos);
               oos.writeObject(userData);
               oos.close();
               fos.close();
               System.out.println("["+LocalDateTime.now()+"]"+"Serialized HashMap data is saved in hashmap.ser");
        }catch(IOException ioe)
         {
               ioe.printStackTrace();
         }
	}
	
	public HashMap<String,String> loadUserData(){
		
		HashMap<String, String> loadedMap = null;
		
		try{
	         FileInputStream fis = new FileInputStream("userData.ser");
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         loadedMap = (HashMap) ois.readObject();
	         ois.close();
	         fis.close();
             System.out.println("["+LocalDateTime.now()+"]"+"Dane za³adowane");
	      }catch(IOException ioe){
	         ioe.printStackTrace();
			 createMap();
	      } catch (ClassNotFoundException e){
			e.printStackTrace();
			createMap();
	      }
		
		return loadedMap;
		
	}
	
	public void saveUserData(HashMap<String,String> userData){
		
		HashMap<String, String> saveMap = userData;
		
		try{
            FileOutputStream fos = new FileOutputStream("userData.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userData);
            oos.close();
            fos.close();
            System.out.println("Saved data");
	      }catch(IOException ioe){
	         ioe.printStackTrace();
	      }
		

	}
	

	
}