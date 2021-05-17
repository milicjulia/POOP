package javapoop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BMPFormat{

	public static BufferedImage openimage(String name){
		 try {
			 File bmpFile = new File("C:\\Users\\User\\Desktop\\temp\\"+name+".bmp");
			return ImageIO.read(bmpFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public static void sacuvajBMP(String name,BufferedImage bmpimage) {
		try {
			File pom=new File("C:\\Users\\User\\Desktop\\temp\\"+name+".bmp");
			pom.setWritable(true);
			ImageIO.write(bmpimage, "BMP", pom);
			ImageIO.createImageOutputStream(pom);
			
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	
}
