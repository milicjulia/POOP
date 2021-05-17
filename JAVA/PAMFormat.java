package javapoop;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class PAMFormat{

	 static String tip;
	 static int width;
	 static int height;
	 static int depth;
	 static int maxval;
	 static String tultype;
	 static int[]pikseli;
	 static WritableRaster pixels;
	 static BufferedImage pamslika;
	
	@SuppressWarnings("resource")
	public static BufferedImage openimage(String name){

		InputStream fileInputStream;
			try {
			fileInputStream = new FileInputStream("C:\\Users\\User\\Desktop\\temp\\"+name+".pam");
			Scanner scan = new Scanner(fileInputStream);
			tip=scan.next("P7");
			scan.next();
			 width = scan.nextInt();
			 scan.next();
			 height = scan.nextInt();
			 scan.next();
			 depth = scan.nextInt();
			 scan.next();
			 maxval = scan.nextInt();
			 scan.next();
			 tultype = scan.next("RGB");
			//scan.next();
			 DataInputStream dis = new DataInputStream(fileInputStream);

			/* int numnewlines = 7;
			 while (numnewlines > 0) {
			     String c;
			     
			    	 c=scan.next();
			       // c = (char)(dis.read());
			 
			     numnewlines--;
			 }
			 */
			 pamslika=new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			 pikseli=new int[height*width];
			 
			 for (int i = 0; i < height*width; i++) {
					int pl = dis.read();
					int crv=dis.read();
					int zel=dis.read();
					int color=(int)((crv & 0xff) << 16) + (int)((zel & 0xff) << 8) + (int)(pl & 0xff);
					pamslika.setRGB((int)(i%width),(int)(i/width), color);
			 }
			 
			return pamslika;
			
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		

public static void sacuvajPAM(String name,BufferedImage pamimage) {
	try {
		OutputStream pom = new FileOutputStream("C:\\Users\\User\\Desktop\\temp\\"+name+".pam");
		@SuppressWarnings("resource")
		DataOutputStream d=new DataOutputStream(pom);
	
		d.writeBytes("P7\n");
		d.writeBytes("WIDTH "+pamimage.getWidth()+"\n");
		d.writeBytes("HEIGHT "+pamimage.getHeight()+"\n");
		d.writeBytes("DEPTH 3\n");
		d.writeBytes("MAXVAL 255\n");
		d.writeBytes("TUPLTYPE RGB\n");
		d.writeBytes("ENDHDR\n");

		byte[] bajt=new byte[Slika.mheight*Slika.mwidth*3];
		int j=0;
		for (int i =0;i< Slika.mheight*Slika.mwidth;i++) {
			int r = pamimage.getRGB(i%Slika.mwidth, i/Slika.mwidth);
			bajt[j++] = (byte) ((r >> 16) & 0xff);
			bajt[j++] = (byte) ((r >> 8) & 0xff);
			bajt[j++] = (byte) ((r) & 0xff);
		}
		d.write(bajt);
	
	} catch (IOException e) {
		e.printStackTrace();
	}
	
}
}
