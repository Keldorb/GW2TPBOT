import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import net.sourceforge.tess4j.TesseractException;

public class ForgeHelper {

	public static int lastMousePositionX = 0;
	public static int lastMousePositionY = 0;
 
	
	public static void getMouseCordinates(int time) {
		for(int i=0;i<time;i++){
			System.out.println("X:"+MouseInfo.getPointerInfo().getLocation().getX()+" Y:"+MouseInfo.getPointerInfo().getLocation().getY());
		}
	}
	public static void activateWindow() throws AWTException, InterruptedException {
		Robot bot = new Robot();
		bot.mouseMove(0, 0);
		Thread.sleep(100);
		bot.mouseMove(35, 220);
		bot.mousePress(InputEvent.BUTTON1_MASK);
	    bot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	public static void mouseClick() {
		Robot bot;
		try {
			bot = new Robot();
			bot.mousePress(InputEvent.BUTTON1_MASK);
		    bot.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void mouseDoubleClick() throws InterruptedException {
		Robot bot;
		try {
			bot = new Robot();
//			Thread.sleep(200);
			bot.mousePress(InputEvent.BUTTON1_MASK);
		    bot.mouseRelease(InputEvent.BUTTON1_MASK);
//		    Thread.sleep(200);
		    bot.mousePress(InputEvent.BUTTON1_MASK);
		    bot.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void mouseLbHold() throws InterruptedException {
		Robot bot;
		try {
			bot = new Robot();
//			Thread.sleep(200);
			bot.mousePress(InputEvent.BUTTON1_MASK);
		    bot.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void mouseLbRelease() throws InterruptedException {
		Robot bot;
		try {
			bot = new Robot();
//			Thread.sleep(200);
		    bot.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void moveMouseWhileLbPressed(int fromX, int fromY, int toX, int toY) throws AWTException, InterruptedException {
		Robot bot = new Robot();
		for(int i=0;fromX+i<toX;i++) {
			bot.mouseMove(fromX+i, fromY);
		}
		for(int i=0;fromY+i<toY;i++) {
			bot.mouseMove(toX, fromY+i);
		}
		updateLastMousePosition(toX, toY);
		Thread.sleep(200);
	}
	
	public static void moveMousefast(int fromX, int fromY, int toX, int toY) throws AWTException, InterruptedException {
		int x=0;
		int y=0;
		Robot bot = new Robot();
		while(fromX!=toX && fromY!=toY) {
			if(fromX<toX) {
				x++;
			}else if (fromX>toX) {
				x--;
			}
			if(fromY<toY) {
				y++;
			}else if (fromY>toY) {
				y--;
			}	
		}
		for(int i=0;fromX+i<toX;i++) {
			bot.mouseMove(fromX+i, fromY);
		}
		for(int i=0;fromY+i<toY;i++) {
			bot.mouseMove(toX, fromY+i);
		}
		updateLastMousePosition(toX, toY);
		Thread.sleep(200);
	}
	
	public static void moveMouse(int fromX, int fromY, int toX, int toY) throws AWTException, InterruptedException {
		Robot bot = new Robot();
		for(int i=0;fromX+i<toX;i++) {
			bot.mouseMove(fromX+i, fromY);
		}
		for(int i=0;fromY+i<toY;i++) {
			bot.mouseMove(toX, fromY+i);
		}
		updateLastMousePosition(toX, toY);
		Thread.sleep(200);
	}
//	public static void debugMouseMove(int x, int y) throws InterruptedException, AWTException {
//		Robot bot = new Robot();
//		bot.mouseMove(0, 0);
//		bot.mouseMove(x, y);
//		Thread.sleep(500);
//		System.out.println("InputPos:"+x+","+y+"\nOutputPos:"+MouseInfo.getPointerInfo().getLocation().getX()+","+MouseInfo.getPointerInfo().getLocation().getY()+"\n");
//		
//	}
	
	//Depricated . Rezepte werden jetzt in der DB gepflegt.
//	public static void loadRecipesFromFile() throws IOException {
//		FileReader fr = new FileReader(Settings.recipesPath+"\\recipes.txt");
//	    BufferedReader br = new BufferedReader(fr);
//	    
//	    String line = br.readLine();
//	    while(line!=null) {
//	    	
//	    }
//	}
	
	public static boolean forgeSameItem(String itemHash) throws HeadlessException, IOException, AWTException, NoSuchAlgorithmException, InterruptedException, TesseractException {
		//Muss dann übergeben werden aber ich setzte den jetzt einfach mal statisch.
		//Item BCCEC657F8E06AF993E111C26CFEED3C shall be forged
		//Gets the Window Inventory Windows for the MysticForge
//		itemHash="BCCEC657F8E06AF993E111C26CFEED3C";
		OCR.initForge();
		boolean moreForgeable = false;
		if(checkRecipePossible(itemHash, itemHash, itemHash, itemHash)==true) {
			forgeItems(itemHash, itemHash, itemHash, itemHash);
		}
		OCR.initForge();
		moreForgeable=checkRecipePossible(itemHash, itemHash, itemHash, itemHash);
		System.out.println("Weitere Forges:"+moreForgeable);
		Thread.sleep(3000);
		return moreForgeable;
	}
	
	//Takes 4 item hashes looks for them at the forge menu and forge it.
	public static boolean forgeItems(String item1, String item2, String item3, String item4) throws HeadlessException, IOException, AWTException, NumberFormatException, InterruptedException, NoSuchAlgorithmException, TesseractException {
		Boolean ongoing = false;
		OCR.getMysticForgeWindow();
		for(int i=0;i<55;i++) {
			OCR.getMysticForgeItem(i);
		}
		OCR.createHashList();
		if(checkRecipePossible(item1, item2, item3, item4)==true) {
			String item1Pos=getItemPositionFromHash(item1);
			String item2Pos=getItemPositionFromHash(item2);
			String item3Pos=getItemPositionFromHash(item3);
			String item4Pos=getItemPositionFromHash(item4);
			moveToForgeItem(Integer.parseInt(item1Pos));
			mouseDoubleClick();
			moveToForgeItem(Integer.parseInt(item2Pos));
			mouseDoubleClick();
			moveToForgeItem(Integer.parseInt(item3Pos));
			mouseDoubleClick();
			moveToForgeItem(Integer.parseInt(item4Pos));
			mouseDoubleClick();
			moveToForgeButton();
			mouseClick();
		}else {
			System.out.println("No items found!");
//			System.out.println("itemPos1:"+item1Pos+"\nitemPos2:"+item2Pos+"\nitemPos3:"+item3Pos+"\nitemPos4:"+item4Pos);
			
		}
		ongoing = checkRecipePossible(item1, item2, item3, item4);
		return ongoing;
		
	}
	
	public static boolean checkRecipePossible (String item1, String item2, String item3, String item4) {
		Boolean possible = false;
		String item1Pos=getItemPositionFromHash(item1);
		String item2Pos=getItemPositionFromHash(item2);
		String item3Pos=getItemPositionFromHash(item3);
		String item4Pos=getItemPositionFromHash(item4);
		if(item1Pos==null || item2Pos==null || item3Pos==null || item4Pos==null) {
			possible=false;
		}else {
			possible=true;
		}
		return possible;
	}
	
	public static String getItemPositionFromHash(String hash) {
		String itemPosition=null;
		int counter=0;
		boolean found = false;
		while(found!=true && counter<OCR.itemHashList.size()) {
			if(hash.equals(OCR.itemHashList.get(counter))) {
				itemPosition=String.valueOf(counter);
				found=true;
//				System.out.println("Found item:"+hash+" on position:"+counter);
			}
			counter++;
		}
		return itemPosition;
		
	}
	public static void debugMouseMoveWithBot(Robot bot,int x, int y) throws InterruptedException, AWTException {
		bot.mouseMove(0, 0);
		Thread.sleep(500);
		bot.mouseMove(x, y);
		Thread.sleep(500);
		System.out.println("InputPos:"+x+","+y+"\nOutputPos:"+MouseInfo.getPointerInfo().getLocation().getX()+","+MouseInfo.getPointerInfo().getLocation().getY()+"\n");
	}
	
	public static void fuseItems() throws AWTException, InterruptedException {

	}
	
	public static void moveToForgeItem(int item) throws AWTException, InterruptedException {
		moveMouse(lastMousePositionX, lastMousePositionY, 517, 767);
		moveMouse(517, 767, getForgePositionXByItem(item), getForgePositionYByItem(item));

	}
	
	public static void moveToForgeButton() throws AWTException, InterruptedException {
		moveMouse(lastMousePositionX, lastMousePositionY,1106, 1234);
	}
	
	public static int getForgePositionXByItem(int item) {
		int xPos= 517+getItemPositionX(item)+10;
		return xPos;
	}
	
	public static int getForgePositionYByItem(int item) {
		int yPos= 767+getItemPositionY(item)+10;
		
		return yPos;
	}
		
	public static int getItemPositionX(int item){
		// x 0, 59, 118, 177, 236 
		int posX=(item%5)*59;
		return posX;
	}
	
	public static int getItemPositionY(int item){		
		int posY=(item/5)*59;
		return posY;
	}
	
//	public static void debugPositionItems(int item) {
//		System.out.println("Item "+item+" hat die position "+getItemPositionX(item)+"/"+getItemPositionY(item));
//	}
	
	public static void updateLastMousePosition(int x,int y) {
		lastMousePositionX = x;
		lastMousePositionY = y;
	}
}
