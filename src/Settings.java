
import java.io.IOException;

public class Settings {

	public static String workingDir=null;
	public static String imagePath=null;
	public static String recipesPath=null;
	public static String databasePath=null; 
	
	//Gets the current DIR where this programm runs from and sets the needed working paths
	public static void updateCurrentWorkingDir() throws IOException {
		String current = new java.io.File( "." ).getCanonicalPath();
        workingDir = System.getProperty("user.dir");
        imagePath = workingDir+"\\screenshots\\";
        recipesPath = workingDir+"\\recipes\\";
        databasePath = workingDir+"\\db.db";
//      System.out.println("Current dir using System:" +currentDir);
	}
	
	
}
