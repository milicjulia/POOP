package javapoop;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.CheckboxMenuItem;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

//MyFormatter c++ 44 92 144 
//KompOp c++ 53 
// ovde 346, XML 39 85 135 188 255 343 361 368, PAM 31 80

@SuppressWarnings("serial")
public class GlavniProzor extends Frame implements ActionListener, ItemListener {
	
public static Slika slika=new Slika();
Selection tmp;
	Label pozicija=new Label();
	 int brojac;
	String dobrodosli="Welcome";
	GlavniProzor g=this;
	boolean flag=false;
	 Label lab[]=new Label[2];
	 TextField txt[]=new TextField[2];
	 Point poc;
	 int duz,sir;
	 Panel sve=new Panel(new GridLayout());
	 Panel pomp=new Panel();
	 Panel pomchoice=new Panel();
	 static String slicica="";
boolean klik=false;
	 Panel dva=new Panel(new GridLayout(2,2));
	 
	 
	public GlavniProzor() {
		super("Welcome");
		setSize(500,500);
		addMenus();
		slusaj();
		addPozMisa();
		this.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent we) {System.exit(0);}});
		setVisible(true);
	}
	
	public void slusaj() {
			 this.addMouseListener(new MouseAdapter() {
				 @Override
				 public void mousePressed(MouseEvent me) {
					 poc=new Point(me.getPoint().x,me.getPoint().y);
				 }
				@Override
				public void mouseReleased(MouseEvent me) {
					if(brojac>0) {
					int xosa=(int)(me.getX()-poc.getX());
					int yosa=(int)(me.getY()-poc.getY());
					Rectangle rr=new Rectangle((int)poc.getX(), (int)poc.getY(), xosa, yosa);
					tmp.addRectangle(rr);
					}	
					brojac--;
					if (brojac==0) Slika.selekcije.add(tmp);
				}
			});
	}
	
	public void addPozMisa() {
		add(pozicija,BorderLayout.PAGE_END);
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				Point poz=new Point();
				poz=e.getLocationOnScreen();
				 pozicija.setText(poz.x+":"+poz.y);
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				Point poz=new Point();
				poz=e.getLocationOnScreen();
				 pozicija.setText(poz.x+":"+poz.y);
			}
		});
	}
	
	public void addMenus() {
		MenuBar traka=new MenuBar();
		
		Menu menuBarComp1=new Menu("File");
		menuBarComp1.add("Save");
		menuBarComp1.addActionListener(this);
		traka.add(menuBarComp1);
		
		Menu menuBarComp2=new Menu("Edit");
		menuBarComp2.add("Filter");
		menuBarComp2.add("Selection");
		menuBarComp2.addActionListener(this);
		traka.add(menuBarComp2);
		
		Menu menuBarComp3=new Menu("Image");
		menuBarComp3.add("Add");
		Menu menishow=new Menu("Show");
		menuBarComp3.add(menishow);
		for(Layer l:Slika.slika) {
			CheckboxMenuItem ch=new CheckboxMenuItem(l.ime,true);
			ch.addItemListener(this);
			menishow.add(ch);
		}
		menuBarComp3.addActionListener(this);
		traka.add(menuBarComp3);
		
		setMenuBar(traka);
	}
	
	@SuppressWarnings({ "static-access" })
	public void paint(Graphics g) {
		if(slika.slika.size()>0) { 
		g.drawImage(slika.getEndResult(),8,50,slika.mwidth,slika.mheight,null);
		setSize(slika.mwidth+100, slika.mheight+100);

		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String komanda=e.getActionCommand();
		switch(komanda) {
		case "Save": prozorSave();break;
		case "Filter": prozorFilter();break;
		case "Selection": prozorSelection();break;
		case "Add": prozorAdd();break;
			}
		}
		
	@Override
	public void itemStateChanged(ItemEvent e) {
		for(int i=0;i<Slika.slika.size();i++)
			if(e.getItemSelectable().toString().contains(Slika.slika.get(i).ime)) Slika.slika.get(i).setActivity(!Slika.slika.get(i).getActivity());
		repaint();
	}
	
	
	public void prozorSave() {
		 class Dijalog extends Frame implements ItemListener,ActionListener{
			Button b=new Button("Save");
			Panel p1=new Panel();
			Panel p2=new Panel();
			Label ime=new Label("Ime: ");
			TextField tekst=new TextField();
			CheckboxGroup grupa=new CheckboxGroup();
			Checkbox izbor[]=new Checkbox[3];
			
			public Dijalog() {
				super("Save");
				dodajKomp();
				setSize(200, 200);
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
					setVisible(false);}
					});
				setVisible(true);
			}
		
		void dodajKomp() {
		add(b,BorderLayout.PAGE_END);
		b.addActionListener(this);
		p1.setLayout(new GridLayout(1, 2));
		p2.setLayout(new GridLayout(3, 1));
		p1.add(ime);
		p1.add(tekst);
		izbor[0]=new Checkbox("BMP",grupa,false);
		izbor[1]=new Checkbox("PAM",grupa,false); 
		izbor[2]=new Checkbox("XML",grupa,false);
		for(int i=0;i<3;i++) {
			izbor[i].addItemListener(this);
			p2.add(izbor[i]);
		}
		add(p2,BorderLayout.CENTER);
		add(p1,BorderLayout.NORTH);
		}
		
		 @Override
		 public void itemStateChanged(ItemEvent e) {}
		 
		 public void actionPerformed(ActionEvent e) {
			 for(int i=0;i<3;i++) {
					if(!tekst.getText().isEmpty() && izbor[i].getState()) {
						switch(izbor[i].getLabel()) {
						case "BMP":BMPFormat.sacuvajBMP(tekst.getText(),slika.getEndResult());break;
						case "PAM":PAMFormat.sacuvajPAM(tekst.getText(),slika.getEndResult());break;
						case "XML":XMLFormat.closeXML(tekst.getText(), slika.getEndResult());break;
						}
					}
			 }
			 setVisible(false); 
		 }
		}
		 new Dijalog();
	}
	
	public void prozorAdd() {
		 class Dijalog extends Frame implements ItemListener,ActionListener{
			Button b=new Button("Add");
			Panel p1=new Panel();
			Panel p2=new Panel();
			Label ime=new Label("Ime: ");
			Label prozirnost=new Label("Prozirnost: ");
			TextField tekst=new TextField();
			TextField tekstp=new TextField();
			CheckboxGroup grupa=new CheckboxGroup();
			Checkbox izbor[]=new Checkbox[3];
			
			public Dijalog() {
				super("Add");
				dodajKomp();
				setSize(250, 250);
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent we) {
					setVisible(false);}
					});
				setVisible(true);
			}
		
		void dodajKomp() {
		add(b,BorderLayout.PAGE_END);
		b.addActionListener(this);
		p1.setLayout(new GridLayout(2, 2));
		p2.setLayout(new GridLayout(3, 1));
		p1.add(ime);
		p1.add(tekst);
		p1.add(prozirnost);
		p1.add(tekstp);
		izbor[0]=new Checkbox("BMP",grupa,false);
		izbor[1]=new Checkbox("PAM",grupa,false); 
		izbor[2]=new Checkbox("XML",grupa,false);
		for(int i=0;i<3;i++) {
			izbor[i].addItemListener(this);
			p2.add(izbor[i]);
		}
		add(p2,BorderLayout.CENTER);
		add(p1,BorderLayout.NORTH);
	}
		
		 @Override
		 public void itemStateChanged(ItemEvent e) {}
		 
		public void actionPerformed(ActionEvent e) {
			 for(int i=0;i<3;i++) {
				if(!tekst.getText().isEmpty() && izbor[i].getState()) {
					switch(izbor[i].getLabel()) {
					case "BMP":{
							Layer ll=new Layer(BMPFormat.openimage(tekst.getText()),tekst.getText());
							ll.setOpacity(Integer.parseInt(tekstp.getText()));
							Slika.slika.add(ll);
							g.repaint();
							break;
						}
					case "PAM":{
							Layer ll=new Layer(PAMFormat.openimage(tekst.getText()),tekst.getText());
							ll.setOpacity(Integer.parseInt(tekstp.getText()));
							Slika.slika.add(ll);
							g.repaint();
							break;
						}
					case "XML":XMLFormat.openXML(tekst.getText());break;
					}
					addMenus();
				}
			}
			setVisible(false); 
		 }
		}
		 new Dijalog();
	}
	
	public void prozorFilter() {
		 class FilterProzor extends Frame implements ActionListener, ItemListener {
			Button finish=new Button("Finish");
			Panel p[]=new Panel[15];
			Panel pom=new Panel();
			TextField tekst[]=new TextField[15];
			CheckboxGroup grupa=new CheckboxGroup();
			Checkbox izbor[]=new Checkbox[15];
		    String komanda="";
			 
			public FilterProzor() {
				super("Filter");
				setSize(500,500);
				addMenus();
				this.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent we) {
					setVisible(false);}
				});
				setVisible(true);
			}
			
			public void addMenus() {
				MenuBar traka=new MenuBar();
				
				Menu menuBarComp1=new Menu("Simple");
				Menu menuBarComp2=new Menu("Composite");
				MenuItem item1=new MenuItem("Simple");
				MenuItem item2=new MenuItem("Composite");
				
				menuBarComp1.add(item1);
				menuBarComp2.add(item2);
				menuBarComp1.addActionListener(this);
				menuBarComp2.addActionListener(this);
				traka.add(menuBarComp1);
				traka.add(menuBarComp2);
				setMenuBar(traka);
			}
			
			public void actionPerformed(ActionEvent e) {
			 komanda=e.getActionCommand();
				switch(komanda) {
				case "Simple": zaSimple(); break;
				case "Composite": zaComposite(); break;
				case "Finish": {
					for(int i=0;i<15;i++) {
						if(izbor[i].getState()) {
							Slika.br.add(i+1);
							if(i<11 && i!=6)
								Slika.param.add(Integer.parseInt(tekst[i].getText()));
							else Slika.param.add(0);
						}
					}
					setVisible(false);
					XMLFormat.closeXML("proba", slika.getEndResult());
					String file="C:\\Users\\User\\Desktop\\poop1\\poop_projekat1\\Debug\\poop_projekat1.exe "+
							"C:\\Users\\User\\Desktop\\temp\\proba.xml "+ "function1.xml";
					Runtime runtime=Runtime.getRuntime();
					try {
						Process process=runtime.exec(file);
						process.waitFor();
					}catch(IOException|InterruptedException q) {q.printStackTrace();}
			
					Slika.slika.clear();
					Slika.br.clear();
					Slika.param.clear();
					Slika.selekcije.clear();
					g.repaint();
					XMLFormat.openXML("proba");
					g.repaint();
					repaint();
					g.setVisible(true);
					break;
					}
				}addMenus();
			}
			
			public void zaSimple() {
				pom.setVisible(false);
				pom=new Panel();
				finish.addActionListener(this);
				izbor[0]=new Checkbox("Add",grupa,false);
				izbor[1]=new Checkbox("Sub",grupa,false); 
				izbor[2]=new Checkbox("Div",grupa,false);
				izbor[3]=new Checkbox("Mul",grupa,false);
				izbor[4]=new Checkbox("Power",grupa,false); 
				izbor[5]=new Checkbox("Log",grupa,false);
				izbor[6]=new Checkbox("Absolute",grupa,false);
				izbor[7]=new Checkbox("Min",grupa,false); 
				izbor[8]=new Checkbox("Max",grupa,false);
				izbor[9]=new Checkbox("InvSub",grupa,false);
				izbor[10]=new Checkbox("InvDiv",grupa,false);
				izbor[11]=new Checkbox("Inverzija",grupa,false); 
				izbor[12]=new Checkbox("Gray",grupa,false);
				izbor[13]=new Checkbox("BlackAndWhite",grupa,false);
				izbor[14]=new Checkbox("Medijana",grupa,false); 
				
				pom.setLayout(new GridLayout(20,1));
				
				for(int i=0;i<15;i++) {
					tekst[i]=new TextField();
					tekst[i].setVisible(false);
					p[i]=new Panel();
					izbor[i].addItemListener(this);
					p[i].setLayout(new GridLayout(1,3));
					p[i].add(izbor[i]);
					p[i].add(tekst[i]);
					pom.add(p[i]);
				}
				add(pom,BorderLayout.CENTER);
				add(finish,BorderLayout.EAST);
				pom.setVisible(true);
				this.setVisible(true);
			}

			public void zaComposite() {
			pom.setVisible(false);
			pom=new Panel();
			finish.addActionListener(this);
			izbor[0]=new Checkbox("Add",false);
			izbor[1]=new Checkbox("Sub",false); 
			izbor[2]=new Checkbox("Div",false);
			izbor[3]=new Checkbox("Mul",false);
			izbor[4]=new Checkbox("Power",false); 
			izbor[5]=new Checkbox("Log",false);
			izbor[6]=new Checkbox("Absolute",false);
			izbor[7]=new Checkbox("Min",false); 
			izbor[8]=new Checkbox("Max",false);
			izbor[9]=new Checkbox("InvSub",false);
			izbor[10]=new Checkbox("InvDiv",false);
			izbor[11]=new Checkbox("Inverzija",false); 
			izbor[12]=new Checkbox("Gray",false);
			izbor[13]=new Checkbox("BlackAndWhite",false);
			izbor[14]=new Checkbox("Medijana",false); 
			
			pom.setLayout(new GridLayout(20,1));
			
			for(int i=0;i<15;i++) {
				tekst[i]=new TextField();
				tekst[i].setVisible(false);
				p[i]=new Panel();
				izbor[i].addItemListener(this);
				p[i].setLayout(new GridLayout(1,3));
				p[i].add(izbor[i]);
				p[i].add(tekst[i]);
				pom.add(p[i]);
			}
			add(pom,BorderLayout.CENTER);
			add(finish,BorderLayout.EAST);
			this.setVisible(true);
			pom.setVisible(true);
		}
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				switch(komanda) {
				case "Simple":{
					for(int i=0;i<15;i++) {
						if(izbor[i].hasFocus() && i<11 && i!=6) tekst[i].setVisible(true);
						else tekst[i].setVisible(false);
					}break;
				}
				case "Composite":{
					for(int i=0;i<15;i++) {
						if(izbor[i].getState() && i<11 && i!=6) tekst[i].setVisible(true);
						else tekst[i].setVisible(false);
					}break;
				}
			}
			}
			
		 }
		 new FilterProzor();
		 }
	
	public void prozorSelection() {
		 class SelectionProzor extends Frame implements ActionListener, ItemListener{
			 Button dodaj=new Button("Dodaj");
			 Button sacu=new Button("Sacuvaj");
				Checkbox izb[]=new Checkbox[10];

				Choice izbor=new Choice();
				Label sel=new Label();
				Panel biraj=new Panel();
				Panel postojeci=new Panel();
				Panel novi=new Panel();
			    public SelectionProzor() {
					super("Selection");
					setSize(400, 400);
					addItems();
					this.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
						setVisible(false);}
					});
					setVisible(true);
			    }
						
			    public void addItems() {
			    	biraj.setVisible(false);
			    	sel.setText("Selekcija: ");
			    	izbor.add("------");
			    	izbor.add("Postojeca");
			    	izbor.add("Nova");
			    	izbor.addItemListener(this);
			    	biraj.add(sel);
			    	biraj.add(izbor);
			    	add(biraj);
			    	biraj.setVisible(true);
			    	this.setVisible(true);
			    	izbor.setVisible(true);
			    }
			    
			
				@Override
				public void itemStateChanged(ItemEvent e) {
					biraj.setVisible(false);
					novi.setVisible(false);
					postojeci.setVisible(false);
					//Checkbox izb[]=new Checkbox[10];
					String slucaj=izbor.getSelectedItem();
					if(slucaj=="Postojeca") {
						postojeci.removeAll();
						this.setVisible(false);
						for(int i=0;i<Slika.selekcije.size();i++) {
							String rec="";
							if(i==0) {
								Slika.selekcije.get(i).sel.clear();
								Slika.selekcije.get(i).sel.add(new Rectangle(8, 50, Slika.mwidth, Slika.mheight));
								izb[i]=new Checkbox("Cela"+"\n",true);
							}
							else {
								for(Rectangle r:Slika.selekcije.get(i).sel) rec+=" ("+r.x+","+r.y+") "+r.height+"x"+r.width+"\n";
								izb[i]=new Checkbox(Slika.selekcije.get(i).ime+"\n"+rec,true);
							}
							izb[i].setVisible(true);
							postojeci.add(izb[i]);

						}
						add(postojeci);
						sacu.addActionListener(this);
						add(sacu,BorderLayout.PAGE_END);
						postojeci.setVisible(true);
						
						this.setVisible(true);
						repaint();
					}
					else { 
						 this.setVisible(false);
						 flag=true;
						 lab[0]=new Label("Ime selekcije: ");
						 lab[1]=new Label("Broj selekcija: ");
						 txt[0]=new TextField(); txt[0].addActionListener(this);
						 txt[1]=new TextField(); txt[1].addActionListener(this);
						 novi.add(lab[0]);
						 novi.add(txt[0]);
						 novi.add(lab[1]);
						 novi.add(txt[1]);
						 add(novi,BorderLayout.CENTER);
						 add(dodaj,BorderLayout.LINE_END);
						 dodaj.setVisible(true);
						 dodaj.addActionListener(this);
						 novi.setVisible(true);
						 this.setVisible(true);
						 repaint();
					}
				}
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand()=="Dodaj") {
						brojac=Integer.parseInt(txt[1].getText());
						tmp=new Selection(txt[0].getText());
					}
					else 
						if(e.getActionCommand()=="Sacuvaj") {
							int i=0;
							while(izb[i]!=null) {
								Slika.selekcije.get(i).Selected(izb[i].getState());
								i++;
							}
						}
				}
			    
		 }
		 new SelectionProzor();
	}
	
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		GlavniProzor gl=new GlavniProzor();
		
	}


}
	