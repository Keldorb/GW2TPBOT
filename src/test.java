import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;


public class test {

	public static void main(String[] args) {
		File imageFile = new File("D:\\Backup Windows\\21.06.2018\\eclipse-workspace\\GW2TPBOT\\test-data\\eurotext.tif");
		
		/**
		* JNA Direct Mapping
		**/
		ITesseract instance = new Tesseract();
		
        /**
		 * You either set your own tessdata folder with your custom language pack or
		 * use LoadLibs to load the default tessdata folder for you.
		 **/
		instance.setDatapath("D:\\Backup Windows\\21.06.2018\\eclipse-workspace\\GW2TPBOT\\tessdata");

		try {
			String result = instance.doOCR(imageFile);
			System.out.println(result);
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
	}
}