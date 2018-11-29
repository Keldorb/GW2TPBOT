
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xml.internal.resolver.helpers.Debug;

import net.sourceforge.tess4j.TesseractException;

public class main {

	public static void main(String[] args) throws IOException, HeadlessException, AWTException, TesseractException, InterruptedException, NoSuchAlgorithmException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		init();

		//TEST FOR FORGING WITH RECIPE 
//		List<String>  itemCodes = sqlInteractions.getCombinationFromDB("BronzeToIron");
//		//while loop missing for checking if return statuement is true to continue
//		ForgeHelper.forgeItems(itemCodes.get(0), itemCodes.get(1), itemCodes.get(2), itemCodes.get(3));
		
		//Test FOR FORGING SAME ITEMS
		Integer selectedItem = 2;
		OCR.initForge();
		String itemToForge = OCR.itemHashList.get(selectedItem);
		System.out.println(itemToForge);
		
		Boolean cont = true;
		while(cont==true) {
			cont = ForgeHelper.forgeSameItem(itemToForge);
		}
		
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
