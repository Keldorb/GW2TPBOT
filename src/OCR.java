
import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import net.sourceforge.tess4j.Tesseract;

public class OCR {
	
	public static BufferedImage imageCaptured = null;
	public static BufferedImage imageBLTP = null;
	public static BufferedImage goldSegment = null;
	public static BufferedImage myhsticForgeInput=null;
	public static HashMap<String, BufferedImage> itemMap = new HashMap<String, BufferedImage>();
	
	public static BufferedImage takeScreenshot() throws HeadlessException, AWTException {
		BufferedImage imageCaptured = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		return imageCaptured;
	}
	
	public static void getMysticForgeInput() throws IOException, HeadlessException, AWTException {
		BufferedImage screenshot = takeScreenshot();
		screenshot = screenshot.getSubimage(517, 767, 290, 625);
		BufferedImage scrapedForgeInput =new BufferedImage(screenshot.getWidth(), screenshot.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = screenshot.createGraphics();
		g.drawImage(imageCaptured, 0, 0, null);
		saveImage(screenshot, "myhsticForge");
		myhsticForgeInput=screenshot;
		
	}
	//umgebaut zum testen
	public static void getMysticForgeItem(int i) throws IOException, NoSuchAlgorithmException {
			BufferedImage item = null;
			int x= ForgeHelper.getItemPositionX(i);
			int y= ForgeHelper.getItemPositionY(i);
			item = myhsticForgeInput.getSubimage(x, y, 54, 54);
			itemMap.put("item"+i, item);
			saveImage(removeFrameFromItem(item), "item_"+i);
	}
	
	public static BufferedImage removeFrameFromItem(BufferedImage item) {
			BufferedImage itemWithoutFrame = null;
			itemWithoutFrame = item.getSubimage(2, 2, 50, 50);
			Graphics g = item.createGraphics();
			g.drawImage(imageCaptured, 0, 0, null);
			return itemWithoutFrame;
	}
	
	public static String getChecksumFromPicture(int i) throws IOException, NoSuchAlgorithmException{
		byte[] b = Files.readAllBytes(Paths.get(Settings.imagePath+"item_"+i+".png"));
		byte[] hash = MessageDigest.getInstance("MD5").digest(b);
		String actual = DatatypeConverter.printHexBinary(hash);
//		System.out.println("Hash for item_"+i+": "+actual);

		return actual;
	}
	

	
	public static void saveImage(BufferedImage image, String filename) throws IOException {
		ImageIO.write(image, "png", new File(Settings.imagePath+"\\"+filename+".png"));
	}
	
	public static BufferedImage getBLTPWindow() throws HeadlessException, AWTException, IOException {
		BufferedImage imageCaptured = takeScreenshot();
		imageCaptured = imageCaptured.getSubimage(0, 0, 1480, 1140); //fill in the corners of the desired crop location here
		BufferedImage copyOfImage = new BufferedImage(imageCaptured.getWidth(), imageCaptured.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = copyOfImage.createGraphics();
		g.drawImage(imageCaptured, 0, 0, null);
		imageBLTP = imageCaptured;
		saveImage(imageBLTP, "tpscreen");
		return imageBLTP;
	}
	
	public static void getGoldSegment() throws HeadlessException, AWTException, IOException {
		OCR.takeScreenshot();
		BufferedImage imageBLTP = getBLTPWindow();
		imageBLTP = imageBLTP.getSubimage(115, 140, 100, 35); //fill in the corners of the desired crop location here
		BufferedImage copyOfImage = new BufferedImage(imageBLTP.getWidth(), imageBLTP.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = copyOfImage.createGraphics();
		g.drawImage(imageCaptured, 0, 0, null);
		goldSegment = imageBLTP;
		saveImage(imageBLTP, "gold");
		
		imageBLTP = getBLTPWindow();
		imageBLTP = imageBLTP.getSubimage(250, 140, 43, 32); //fill in the corners of the desired crop location here
		copyOfImage = new BufferedImage(imageBLTP.getWidth(), imageBLTP.getHeight(), BufferedImage.TYPE_INT_RGB);
		g = copyOfImage.createGraphics();
		g.drawImage(imageCaptured, 0, 0, null);
		goldSegment = imageBLTP;
		saveImage(imageBLTP, "silver");
		
		imageBLTP = getBLTPWindow();
		imageBLTP = imageBLTP.getSubimage(325, 140, 37, 35); //fill in the corners of the desired crop location here
		copyOfImage = new BufferedImage(imageBLTP.getWidth(), imageBLTP.getHeight(), BufferedImage.TYPE_INT_RGB);
		g = copyOfImage.createGraphics();
		g.drawImage(imageCaptured, 0, 0, null);
		goldSegment = imageBLTP;
		saveImage(imageBLTP, "copper");
	}
	
	public static String getText(String fileName) throws net.sourceforge.tess4j.TesseractException{
		File imageFile = new File(Settings.imagePath, fileName);
		Tesseract instance = new Tesseract();
		instance.setDatapath(Settings.workingDir);
		String result = instance.doOCR(imageFile);
		System.out.println("Recognized Text:"+result);
		return result;
	}

	
	public static void setGoldValues() throws net.sourceforge.tess4j.TesseractException, HeadlessException, AWTException, IOException {
		getGoldSegment();
		Values.gold=getText("gold.png").replaceAll("[^\\d.]", "");
		Values.silver=getText("silver.png").replaceAll("[^\\d.]", "");
		Values.copper=getText("copper.png").replaceAll("[^\\d.]", "");
		
	}
}
