import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

public class TpHandler {
	//be sure to drag the tp window in the upper right corner to autoadjust the tp window

	public static void initTPWindow() throws AWTException, InterruptedException {
		Robot bot;
		try {
			bot = new Robot();
			ForgeHelper.moveMouse(1750, 60, 1830, 60);
			bot.mousePress(InputEvent.BUTTON1_MASK);
			ForgeHelper.moveMouse(ForgeHelper.lastMousePositionX, ForgeHelper.lastMousePositionY, 650, 700);
		    bot.mouseRelease(InputEvent.BUTTON1_MASK);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void closeTP() throws AWTException, InterruptedException {
			ForgeHelper.moveMouse(ForgeHelper.lastMousePositionX, ForgeHelper.lastMousePositionY, 1430, 700);
			ForgeHelper.mouseClick();
	}
	
	public static void TPButtonClick() throws AWTException, InterruptedException {
		ForgeHelper.moveMouse(ForgeHelper.lastMousePositionX, ForgeHelper.lastMousePositionY, 160, 60);
		ForgeHelper.mouseClick();
	}
	
	
}

