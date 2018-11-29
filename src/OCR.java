
import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.MappedByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCR {
	
	public static BufferedImage imageCaptured = null;
	public static BufferedImage imageBLTP = null;
	public static BufferedImage goldSegment = null;
	public static BufferedImage myhsticForgeInput=null;
	public static HashMap<String, BufferedImage> itemMap = new HashMap<String, BufferedImage>();
	public static HashMap<String, BufferedImage> itemMapWithoutQuantity = new HashMap<String, BufferedImage>();
	public static HashMap<String, BufferedImage> itemMapQuantity = new HashMap<String, BufferedImage>();
	public static List<String> itemHashList = new ArrayList<String>();
	
	public static BufferedImage takeScreenshot() throws HeadlessException, AWTException {
		BufferedImage imageCaptured = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		return imageCaptured;
	}
	

	
	public static void getMysticForgeWindow() throws IOException, HeadlessException, AWTException {
		BufferedImage screenshot = takeScreenshot();
		screenshot = screenshot.getSubimage(517, 767, 290, 625);
		BufferedImage scrapedForgeInput =new BufferedImage(screenshot.getWidth(), screenshot.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = screenshot.createGraphics();
		g.drawImage(imageCaptured, 0, 0, null);
		saveImage(screenshot, "myhsticForge");
		myhsticForgeInput=screenshot;
		
	}
	//umgebaut zum testen
	public static void getMysticForgeItem(int i) throws IOException, NoSuchAlgorithmException, TesseractException {
			itemMap.clear();
			itemMapQuantity.clear();
			itemMapWithoutQuantity.clear();
			BufferedImage item = null;
			int x= ForgeHelper.getItemPositionX(i);
			int y= ForgeHelper.getItemPositionY(i);
			item = myhsticForgeInput.getSubimage(x, y, 54, 54);
			itemMap.put("item"+i, item);
			itemMapQuantity.put("item"+i, getQuantityFromItem(removeFrameFromItem(item)));
			itemMapWithoutQuantity.put("item"+i, removeQuantityFromItem(removeFrameFromItem(item)));
			saveImage(removeFrameFromItem(item), "item_"+i);
			saveImage(getQuantityFromItem(removeFrameFromItem(item)), "item_"+i+"_count");
			saveImage(removeQuantityFromItem(removeFrameFromItem(item)), "item_"+i+"_clear");
			itemHashList.add(getChecksumFromitemMapWithoutQuantity(i));
	}
	//This method is used for getting 50 items (hardcoded) from the forge windows and fill the hashmaps itemMap, itemmapQuantity, itemMapWithoutQuantity and 
	//creates the hashtable for the images
	public static void initForge() throws IOException, NoSuchAlgorithmException, TesseractException, HeadlessException, AWTException {
		itemMap.clear();
		itemMapQuantity.clear();
		itemMapWithoutQuantity.clear();
		getMysticForgeWindow();
		for(int i=0;i<40;i++) {
			BufferedImage item = null;
			int x= ForgeHelper.getItemPositionX(i);
			int y= ForgeHelper.getItemPositionY(i);
			item = myhsticForgeInput.getSubimage(x, y, 54, 54);
			itemMap.put("item"+i, item);
			itemMapQuantity.put("item"+i, getQuantityFromItem(removeFrameFromItem(item)));
			itemMapWithoutQuantity.put("item"+i, removeQuantityFromItem(removeFrameFromItem(item)));
			saveImage(removeFrameFromItem(item), "item_"+i);
			saveImage(getQuantityFromItem(removeFrameFromItem(item)), "item_"+i+"_count");
			saveImage(removeQuantityFromItem(removeFrameFromItem(item)), "item_"+i+"_clear");
			itemHashList.add(getChecksumFromitemMapWithoutQuantity(i));
		}
		
}
	
	public static BufferedImage removeFrameFromItem(BufferedImage item) {
			BufferedImage itemWithoutFrame = null;
			itemWithoutFrame = item.getSubimage(2, 2, 50, 50);
			Graphics g = item.createGraphics();
			g.drawImage(imageCaptured, 0, 0, null);
			return itemWithoutFrame;
	}
	
	public static BufferedImage getQuantityFromItem(BufferedImage item) throws IOException {
			BufferedImage itemQuantity = null;
			itemQuantity = item.getSubimage(25, 1, 20, 10);
			Graphics g = item.createGraphics();
			g.drawImage(imageCaptured, 0, 0, null);
			return itemQuantity;
			
	}
	
	//Generates hashes for 
	public static void createHashList() throws NoSuchAlgorithmException, IOException {
		itemHashList.clear();
		if(itemMap!=null) {
			for(int i=0;i<itemMap.size();i++) {
				itemHashList.add(getChecksumFromPicture(i));
			}
			
		}
	}
	
	public static void createHashListNew() throws NoSuchAlgorithmException, IOException {
		itemHashList.clear();
		if(itemMap!=null) {
			for(int i=0;i<itemMap.size();i++) {
				itemHashList.add(getChecksumFromitemMapWithoutQuantity(i));
			}
			
		}
	}
	
	//Removes the quantity from the original item picture without frame
	public static BufferedImage removeQuantityFromItem(BufferedImage item) {
			BufferedImage itemWithoutQuantity = null;
			itemWithoutQuantity = item.getSubimage(0, 12, 49, 37);
			Graphics g = item.createGraphics();
			g.drawImage(imageCaptured, 0, 0, null);
			return itemWithoutQuantity;
	}
	
	public static void getForgedItemResult() throws IOException, HeadlessException, AWTException {
		BufferedImage screenshot = takeScreenshot();
		BufferedImage scraping = screenshot;
		BufferedImage item = scraping.getSubimage(1017, 950, 62, 62);
//		saveImage(getQuantityFromItem(item),"forgedItemQuantity");
		saveImage(removeQuantityFromItem(item),"forgedItem");
//		scraping = screenshot;
//		BufferedImage text = scraping.getSubimage(1019, 951, 62, 62);
//		saveImage(text, "forgedItemText");
	}
	
	public static String getChecksumFromPicture(int i) throws IOException, NoSuchAlgorithmException{
		byte[] b = Files.readAllBytes(Paths.get(Settings.imagePath+"item_"+i+"_clear.png"));
		byte[] hash = MessageDigest.getInstance("MD5").digest(b);
		String actual = DatatypeConverter.printHexBinary(hash);
//		System.out.println("Hash for item_"+i+": "+actual);

		return actual;
	}
	
	public static String getChecksumFromitemMapWithoutQuantity(int pos) throws IOException, NoSuchAlgorithmException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(itemMapWithoutQuantity.get(pos), "jpg", baos);
		byte[] b = baos.toByteArray();
		byte[] hash = MessageDigest.getInstance("MD5").digest(b);
		String actual = DatatypeConverter.printHexBinary(hash);
//		System.out.println("Hash for item_"+i+": "+actual);

		return actual;
	}
	
	
	private String getMD5Checksum(BufferedImage fileImg) throws Exception {
        byte[] b = createChecksum(fileImg);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            result.append(String.format("%02x",b[i]));
        }
        return result.toString();
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
