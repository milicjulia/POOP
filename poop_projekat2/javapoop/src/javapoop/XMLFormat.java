package javapoop;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLFormat {

	@SuppressWarnings("resource")
	public static void napraviSlikuIzTxt(Sloj l) {
		Layer sloj=new Layer();
		sloj.opacity=l.getopacity();
		sloj.ime=l.getpath();
		sloj.height=Slika.mheight;
		sloj.width=Slika.mwidth;
		sloj.layer=new BufferedImage(Slika.mwidth, Slika.mheight, BufferedImage.TYPE_INT_RGB);
		try {
			FileInputStream f=new FileInputStream("C:\\Users\\User\\Desktop\\temp\\"+sloj.ime);

	        byte b;
			
	      	for(int i=0;i<Slika.mheight*Slika.mwidth;i++) {
	      		b=(byte)f.read();
	      		String r="";
	      		while(b>=48) {
	      			r+=(b-48);
	      			b=(byte)f.read();
	      		}
	      		b=(byte) f.read();
	      		String g="";
	      		while(b>=48) {
	      			g+=(b-48);
	      			b=(byte)f.read();
	      		}
	      		b=(byte) f.read();
	      		String bb="";
	      		while(b>=48) {
	      			bb+=(b-48);
	      			b=(byte)f.read();
	      		}
	      		b=(byte) f.read();
	      		String op="";
	      		while(b>=48) {
	      			op+=(b-48);
	      			b=(byte)f.read();
	      		}
	      		op="255";
	      		if(op!="") {
	      		int color=(((255-Integer.parseInt(op)) & 0xff) << 24)+((Integer.parseInt(r) & 0xff) << 16) + ((Integer.parseInt(g) & 0xff) << 8) + (Integer.parseInt(bb) & 0xff);
	      		sloj.layer.setRGB((int)(i%Slika.mwidth),(int)(i/Slika.mwidth), color);
	      		}
	      	} 
	      	Slika.slika.add(sloj);
		} catch (IOException e) { e.printStackTrace(); }       
		
    }

	@SuppressWarnings("resource")
	public static void napraviTxtIzSlike(Layer l,int xx) {
		FileOutputStream s;
		int a;
		int r;
		try {
			File f=new File("C:\\Users\\User\\Desktop\\temp\\layer"+xx+".txt");
			f.setWritable(true);
			f.setReadable(true);
			s=new FileOutputStream(f);
			PrintStream pout = new PrintStream(s);
			
	    		for (int i =0;i< Slika.mheight;i++) {
	    			for(int j=0;j<Slika.mwidth;j++) {
	    				if(i<l.getHeight() && j<l.getWidth()) {
	    					r = l.layer.getRGB(j,i);
	    					a=l.getOpacity();
	    				}
	    				else { r=0; a=0; }
	    			int red=(r >> 16) & 0xff;
	    			int green=(r >> 8) & 0xff;
	    			int blue=(r) & 0xff;
	    			pout.print(red); pout.print(',');
	    			pout.print(green); pout.print(',');
	    			pout.print(blue); pout.print(',');
	    			pout.print(a); pout.print(',');
	    			}
	    		}
		} catch (IOException e) { e.printStackTrace(); }
	}       
	
	@SuppressWarnings("resource")
	public static void napraviTxtIzSel(Selection s,String ime) {
		FileOutputStream t;
		try {
			File f=new File(ime);
			f.setWritable(true);
			f.setReadable(true);
			t = new FileOutputStream(f);
			PrintStream pout=new PrintStream(t);
			
	    		for (int i =0;i< s.sel.size();i++) {
	    			pout.print((s.sel.get(i).x-8)); pout.print(" ");
	    			pout.print((s.sel.get(i).y-50)); pout.print(" ");
	    			pout.print(s.sel.get(i).width); pout.print(" ");
	    			pout.print(s.sel.get(i).height); pout.print("\n");
	    	    }
	    		
		} catch (IOException e) { e.printStackTrace(); }
	}       
	
	
	public static void procitajComp(String path) {
		int ID;
		int par;
		try {
			File file=new File("C:\\Users\\User\\Desktop\\temp\\"+path);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			org.w3c.dom.Document document = documentBuilder.parse(file);
			
			NodeList list = document.getElementsByTagName("SimpleOperation");
			for (int i = 0; i < list.getLength(); i++) {
			    Node node = list.item(i);
			    if (node.getNodeType() == Node.ELEMENT_NODE) {
			       Element element = (Element) node;
			       ID=Integer.parseInt(element.getAttribute("br"));
		           par= Integer.parseInt(element.getAttribute("parameter"));
			       Slika.br.add(ID);
			       Slika.param.add(par);
			     }
			}
		} catch (ParserConfigurationException | SAXException | IOException e) { e.printStackTrace(); }
	}
		
	
	public static void napraviComp(String ime) {
		try {
			DocumentBuilderFactory dbFactory =DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			 Document doc = dBuilder.newDocument();
			        
			 Element rootElement = doc.createElement("KompozitnaFunkcija");
			 doc.appendChild(rootElement);     
			 for(int i=0;i<Slika.br.size();i++) {
				 Element simp = doc.createElement("SimpleOperation");
				 rootElement.appendChild(simp);
				 Attr br = doc.createAttribute("br");
				 br.setValue(Integer.toString(Slika.br.get(i)));
				 simp.setAttributeNode(br);
				 Attr par = doc.createAttribute("parameter");
				 par.setValue(Integer.toString(Slika.param.get(i)));
				 simp.setAttributeNode(par);
			 }
		
			 TransformerFactory transformerFactory = TransformerFactory.newInstance();
			 Transformer transformer = transformerFactory.newTransformer();
			 DOMSource source = new DOMSource(doc);
			 StreamResult result = new StreamResult(new File(ime));
			 transformer.transform(source, result);
        
		} catch (ParserConfigurationException | TransformerException e) { e.printStackTrace(); }
	}
	
	
	@SuppressWarnings({ "resource", "unused" })
	public static void openXML(String name){
		int height, width;
		try {
			File file=new File("C:\\Users\\User\\Desktop\\temp\\"+name+".xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		    org.w3c.dom.Document document = documentBuilder.parse(file);
		    
		    NodeList list = document.getElementsByTagName("Properties");
		    ArrayList<Sloj> slojevi = new ArrayList<>();
		    for (int i = 0; i < list.getLength(); i++) {
		    	Node node = list.item(i);
		    	if (node.getNodeType() == Node.ELEMENT_NODE) {
		    		Element element = (Element) node;
		    		width=Integer.parseInt(element.getAttribute("Width"));
		    		height= Integer.parseInt(element.getAttribute("Height"));
		    		Slika.mheight=height;
		    		Slika.mwidth=width;
		    		
		    		NodeList slojs = element.getElementsByTagName("Layer");
		    		for (int j = 0; j < slojs.getLength(); j++) {
		    			Node slojj = slojs.item(j);
		    			if (slojj.getNodeType() == Node.ELEMENT_NODE) {
		    				Element elementp = (Element) slojj;
		    				int op= Integer.parseInt(elementp.getAttribute("Opacity"));
		    				String ims=(elementp.getAttribute("Path"));
		    				Sloj sloj = new Sloj(ims,op);
		    				napraviSlikuIzTxt(sloj);
		    				slojevi.add(sloj);
		    			}
		    		}
		    		Properties property = new Properties(width,height,slojevi);   
		    	}
		    }
	    
		    NodeList list2 = document.getElementsByTagName("CompositeFunction");
		    ArrayList<CompositeF> fje = new ArrayList<>();
		    for (int i = 0; i < list2.getLength(); i++) {
		    	Node node2 = list2.item(i);
		    	if (node2.getNodeType() == Node.ELEMENT_NODE) {
		    		Element element2 = (Element) node2;
		    		NodeList fjs = element2.getElementsByTagName("Ime");
		    		for (int j = 0; j < fjs.getLength(); j++) {
		    			Node fjj = fjs.item(j);
		    			if (fjj.getNodeType() == Node.ELEMENT_NODE) {
		    				CompositeF func = new CompositeF(element2.getAttribute("Path"));
		    				fje.add(func);
		    			//	procitajComp(func.getpath());
		    			}
		    		}    
		    	}
		    }
	    
		    NodeList list3 = document.getElementsByTagName("Selections");
		    ArrayList<Selections> sle = new ArrayList<>();
		    for (int i = 0; i < list3.getLength(); i++) {
		    	Node node3 = list3.item(i);
		    	if (node3.getNodeType() == Node.ELEMENT_NODE) {
		    		Element element3 = (Element) node3;
		    		NodeList sls = element3.getElementsByTagName("Selection");
		    		for (int j = 0; j < sls.getLength(); j++) {
		    			Node sll = sls.item(j);
		    			if (sll.getNodeType() == Node.ELEMENT_NODE) {
		    				Element el4=(Element)sll;
		    				Selections s = new Selections(
		    						el4.getAttribute("Path"),
		    						(el4.getAttribute("Activity")=="true")?true:false,
		    						el4.getAttribute("Name")
		    						);
		    				sle.add(s);
		    				FileInputStream fsel=new FileInputStream("C:\\Users\\User\\Desktop\\temp\\"+el4.getAttribute("Path"));        
		    				Selection sf=new Selection(el4.getAttribute("Name"));
		    				sf.Selected((el4.getAttribute("Activity")=="true")?true:false);
		    				byte b;
		    				String string="";
		    				b=(byte) fsel.read();
		    				while(b!=(-1) && b!=10){
		    					while(b!=0x20) {
		    						string+=(b-48);
		    						b=(byte) fsel.read();
		    					}
		    					int xx=Integer.parseInt(string);
	    						b=(byte) fsel.read();
			    				string="";
			    				while(b!=0x20) {
		    						string+=(b-48);
		    						b=(byte) fsel.read();
		    					}
		    					int yy=Integer.parseInt(string);
	    						b=(byte) fsel.read();
			    				string="";
			    				while(b!=0x20) {
		    						string+=(b-48);
		    						b=(byte) fsel.read();
		    					}
		    					int sir=Integer.parseInt(string);
	    						b=(byte) fsel.read();
			    				string="";
			    				while(b!=0x20 && b!=-1 && b!=10) {
		    						string+=(b-48);
		    						b=(byte) fsel.read();
		    					}
		    					int vis=Integer.parseInt(string);
	    						b=(byte) fsel.read();
			    				string="";
		    					Rectangle r=new Rectangle(xx+8, yy+50, sir, vis);
		    					sf.addRectangle(r);
		    				}  
		    				Slika.selekcije.add(sf);	
		    			}
		    		}
		    	}
		    }
			} catch (ParserConfigurationException | SAXException | IOException e) { e.printStackTrace(); }
		
	}
	
	
	public static void closeXML(String name,BufferedImage image) {
		try {
        DocumentBuilderFactory dbFactory =DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        
        Element rootElement = doc.createElement("Image");
        doc.appendChild(rootElement);

        Element property = doc.createElement("Properties");
        rootElement.appendChild(property);
        Attr width = doc.createAttribute("Width");
        width.setValue(Integer.toString(Slika.mwidth));
        property.setAttributeNode(width);
        Attr height = doc.createAttribute("Height");
        height.setValue(Integer.toString(Slika.mheight));
        property.setAttributeNode(height);
        
        int xx=1;
        for(Layer l:Slika.slika) {
          Element layer = doc.createElement("Layer");
          Attr path = doc.createAttribute("Path");
          path.setValue("layer"+Integer.toString(xx)+".txt");
          layer.setAttributeNode(path);
          Attr opacity = doc.createAttribute("Opacity");
          opacity.setValue(String.valueOf(l.opacity));
          layer.setAttributeNode(opacity);
          property.appendChild(layer);
          napraviTxtIzSlike(l,xx);
          xx++;
         }
  
        Element comp = doc.createElement("CompositeFunction");
        rootElement.appendChild(comp);
        if(!Slika.br.isEmpty()) {
        	Element ime = doc.createElement("Ime");
        	Attr path = doc.createAttribute("Path");
        	path.setValue("function1"+".xml");
        	ime.setAttributeNode(path);
        	comp.appendChild(ime);
        	napraviComp("C:\\Users\\User\\Desktop\\temp\\function1.xml");
        }
        
        Element sel = doc.createElement("Selections");
        rootElement.appendChild(sel);
        int i=1;
        for(Selection l:Slika.selekcije) {
            Element sele = doc.createElement("Selection");
            Attr put = doc.createAttribute("Path");
            Attr act = doc.createAttribute("Activity");
            Attr n = doc.createAttribute("Name");
            put.setValue("selection"+Integer.toString(i)+".txt");
            act.setValue((l.selektovana)?"true":"false");
            n.setValue("selection"+Integer.toString(i)+".txt");
            sele.setAttributeNode(put);
            sele.setAttributeNode(act);
            sele.setAttributeNode(n);
            sel.appendChild(sele);
            napraviTxtIzSel(l, "C:\\Users\\User\\Desktop\\temp\\selection"+Integer.toString(i)+".txt");
            i++;
        }
  
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("C:\\Users\\User\\Desktop\\temp\\"+name+".xml"));
        transformer.transform(source, result);

     } catch (Exception e) { e.printStackTrace(); }
}
	
}