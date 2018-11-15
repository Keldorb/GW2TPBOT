import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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
	
	public static boolean forgeSameItem() throws HeadlessException, IOException, AWTException, NoSuchAlgorithmException, InterruptedException {
		//Muss dann übergeben werden aber ich setzte den jetzt einfach mal statisch.
		//Item BCCEC657F8E06AF993E111C26CFEED3C shall be forged
		//Gets the Window Inventory Windows for the MysticForge
		String choosenItem="BCCEC657F8E06AF993E111C26CFEED3C";
		OCR.getMysticForgeInput();
		boolean moreForgeable = false;
		//55 items
		ArrayList<Integer> itemList = new ArrayList<>();
		int foundItems=0;
		int item=0;
		while(item<40) {
			OCR.getMysticForgeItem(item);
			if(OCR.getChecksumFromPicture(item).equals(choosenItem)){
//				System.out.println("Item "+item+" is the choosen one!");
				itemList.add(item);
				foundItems++;
			}
			item++;		
		}
		
		System.out.println(itemList.size()+" Items zum klicken entdeckt");
		
		if(itemList.size()>3) {
			for(int i=0;i<4;i++) {
				moveToForgeItem(itemList.get(i));
//				Thread.sleep(500);
				mouseDoubleClick();
			}
		}else {
			System.out.println("Not enough Items to forge");
		}
		moveToForgeButton();
		mouseDoubleClick();
		
		if(itemList.size()>3)
			moreForgeable=true;
		System.out.println("Weiter: "+moreForgeable);
		Thread.sleep(3000);
		return moreForgeable;
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
