
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xml.internal.resolver.helpers.Debug;

import net.sourceforge.tess4j.TesseractException;

public class main {

	public static void main(String[] args) throws IOException, HeadlessException, AWTException, TesseractException, InterruptedException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		init();
//		OCR.takeScreenshot();
//		OCR.setGoldValues();
//		
//		System.out.println("Gold:"+Values.gold + " Silver:"+Values.silver + " Copper:"+Values.copper);
//		ForgeHelper.getMouseCordinates();
//		ForgeHelper.activateWindow();
		
//		int rotation=0;
//		int circles=5;
//		while(rotation<circles) {
////			ForgeHelper.fuseItems();
////			rotation++;
////		}
////		ForgeHelper.testRunMouse();
//		
//		ForgeHelper.fuseItems();
//		rotation++;
//		}
		
//		OCR.getMysticForgeInput();
//		OCR.getMysticForgeInput();
//		for(int i=0;i<40;i++) {
////			OCR.getMysticForgeItem(i);
//			OCR.getChecksumFromPicture(i);
////			ForgeHelper.debugPositionItems(i);
//		}
//		for(int i=0;i<15;i++) {
//			ForgeHelper.moveToForgeItem(i);
//		}
//		ForgeHelper.moveToForgeButton();

		boolean onward=true;
		while(onward==true) {
			onward=ForgeHelper.forgeSameItem();
		}
//		ForgeHelper.forgeSameItem();
		
	}
	
	//Does the nessesary steps before running the program
	public static void init() {
		try {
			//Sets the current path for the running path
			Settings.updateCurrentWorkingDir();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
