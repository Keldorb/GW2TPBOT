
import java.io.IOException;

public class Settings {

	public static String workingDir=null;
	public static String imagePath=null;
	

	public static void updateCurrentWorkingDir() throws IOException {
		String current = new java.io.File( "." ).getCanonicalPath();
        workingDir = System.getProperty("user.dir");
        imagePath = System.getProperty("user.dir")+"\\screenshots\\";
        
//        System.out.println("Current dir using System:" +currentDir);
	}
	
}
