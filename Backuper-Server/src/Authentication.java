import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
               System.out.println("Serialized HashMap data is saved in hashmap.ser");
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
             System.out.println("Loaded data");
	      }catch(IOException ioe){
	         ioe.printStackTrace();
	      } catch (ClassNotFoundException e){
			e.printStackTrace();
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