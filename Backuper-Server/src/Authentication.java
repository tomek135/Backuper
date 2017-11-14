import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;



public class Authentication{
	public Authentication(){};
	
	public Authentication(String action, String login, String password) {
		HashMap<String, String> userData= loadUserData();
		if(action=="LOGIN") {
			if(userData.keySet().contains(login) && userData.get(login).equals(password))
			{
				System.out.println("Zalogowano pomyslnie");
			}else {
				System.out.println("Nie ma takiego uzytkownika");
			}
		}else if(action=="REGISTER"){
			if(userData.keySet().contains(login))
			{
				System.out.println("Uzytkownik juz istnieje");
			}else {
				userData.put(login, password);
				saveUserData(userData);
			}

		}
		
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