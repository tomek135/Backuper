import java.io.File;
import java.util.concurrent.CountDownLatch;

public class FileList extends Thread{
	
	String files = "";
	File curDir;
	private final CountDownLatch doneSignal;
	
	public FileList(String directory,CountDownLatch doneSignal) {
		this.doneSignal = doneSignal;
        curDir = new File("./"+directory);

	}
	
	public void run() {
        getAllFiles(curDir);
        doneSignal.countDown();
	}
	
	
    private void getAllFiles(File curDir) {
    	try {
    		
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
                System.out.println(f.getName());
            if(f.isFile()){
                files += f.getName()+";";
            }
        }
    
    } catch(NullPointerException e){
    	files = "";
    }
    }
    String getFiles() {
    	return files;
    }
}