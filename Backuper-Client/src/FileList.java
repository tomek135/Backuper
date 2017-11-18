import java.io.File;

public class FileList extends Thread {
	
	String files = "LIST;";
	File curDir;
	
	public FileList(String directory) {
        curDir = new File("./"+directory);

	}
	
	public void run() {
        getAllFiles(curDir);
	}
	
	
    private void getAllFiles(File curDir) {

        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
                System.out.println(f.getName());
            if(f.isFile()){
                files += f.getName()+";";
            }
        }
        
        System.out.println("aaa "+ files);
    }
}
