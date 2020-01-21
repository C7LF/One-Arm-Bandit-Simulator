package Bandit;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Desktop;
import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.JCheckBox;

public class BanditVisual {

	private JFrame frmOneArmBandit;
	private JTextField creditAmount = new JTextField();
	private JLabel datetime;
	private Bandit bandit;
	private JTextField winningsFields = new JTextField();;
	int frn1, frn2, frn3;
	
	JLabel IconBox1 = new JLabel("");
	JLabel IconBox2 = new JLabel("");
	JLabel IconBox3 = new JLabel("");
	
	JLabel Icongrey1 = new JLabel("");
	JLabel Icongrey2 = new JLabel("");
	JLabel Icongrey3 = new JLabel("");
	
	JLabel lblReplay = new JLabel("REPLAY");
	
	JCheckBox chckbxHold = new JCheckBox("Hold");
	JCheckBox chckbxHold2 = new JCheckBox("Hold");
	JCheckBox chckbxHold3 = new JCheckBox("Hold");
	JCheckBox chckbxMute = new JCheckBox("Mute");
	JButton btnViewReplay = new JButton("View Replay");
	
	Timer spin1, spin2, spin3;
	Timer spin1replay, spin2replay, spin3replay;
	
	JButton btnSpin = new JButton("SPIN");
	
	JButton btnNudge = new JButton("Nudge");
	JButton btnNudge2 = new JButton("Nudge");
	JButton btnNudge3 = new JButton("Nudge");
	
	ArrayList<Integer> list = new ArrayList<Integer>();
	
	public void datetimer() {
	ActionListener updateClockAction = new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  datetime.setText(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		    }
		};
		Timer t = new Timer(100, updateClockAction);
		t.start();
	}
	
	
	// Save Fire method
		public void logGame(String msgnon) {
			
		    File log = new File("log.txt");
		    	    try{
		    	    if(log.exists()==false){
		    	            System.out.println("We had to make a new file.");
		    	            log.createNewFile();
		    	    }
		    	    PrintWriter out = new PrintWriter(new FileWriter(log, true));
		    	    out.append(msgnon + " " + Arrays.asList(list) + System.lineSeparator());
		    	    out.close();
		    	    }catch(IOException e){
		    	        System.out.println("COULD NOT LOG!!");
		    	    }
		}
	
	// method to disable button once clicked
	static void disable(final AbstractButton b, final long ms) {
	    b.setEnabled(false);
	    new SwingWorker() {
	        @Override protected Object doInBackground() throws Exception {
	            Thread.sleep(ms);
	            return null;
	        }
	        @Override protected void done() {
	            b.setEnabled(true);
	        }
	    }.execute();
	}

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BanditVisual window = new BanditVisual();
					window.frmOneArmBandit.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public BanditVisual() {
		initialize();
	}
	
	public void generateSpin() {
		//frn1 = Bandit.GenerateNumber1(1);
		// frn2 = Bandit.GenerateNumber1(2);
		// frn3 = Bandit.GenerateNumber1(3);
	}
	
	public void nudgebutton1() {
		frn1 = frn1 + 1;
		switch (frn1) {
		case 9: frn1 = 1;
			break;
		}
		list.set(0, frn1);
		System.out.println(Arrays.asList(list));
		IconBox1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn1 + ".png")));
		checkIfWinNudge();
		logGame("Nudge One: ");
		list.clear();
	}
	public void nudgebutton2() {
		frn2 = frn2 + 1;
		switch (frn2) {
		case 9: frn2 = 1;
			break;
		}
		list.set(1, frn2);
		System.out.println(Arrays.asList(list));
		IconBox2.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn2 + ".png")));
		checkIfWinNudge();
		logGame("Nudge Two: ");
		list.clear();
	}
	public void nudgebutton3() {
		frn3 = frn3 + 1;
		switch (frn3) {
		case 9: frn3 = 1;
			break;
		}
		list.set(2, frn3);
		System.out.println(Arrays.asList(list));
		IconBox3.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn3 + ".png")));
		checkIfWinNudge();
		logGame("Nudge Three: ");
		list.clear();
	}
	
	
	private void spinBandit() {
		if (Bandit.setCredit().compareTo(BigDecimal.ZERO) <= 0.00) { 
			Popup.infoBox("Not Enough Credits!", "Notice");
		} else {
			// Remove 0.2 credit
			creditAmount.setText(Bandit.SpinRemoveCredit().toString());
			
			btnViewReplay.setVisible(false);
			
			btnNudge.setEnabled(true);
			btnNudge2.setEnabled(true);
			btnNudge3.setEnabled(true);
			
			chckbxHold.setEnabled(true);
			chckbxHold2.setEnabled(true);
			chckbxHold3.setEnabled(true);
			
			// if hold button isn't selected 1
			if (!chckbxHold.isSelected()) {
				frn1 = Bandit.GenerateNumber1(1);
				int gfrn1 = frn1 + 1;
				switch (gfrn1) {
				case 9: gfrn1 = 1;
					break;
				}
				Icongrey1.setVisible(true);
				spin1 = new Timer(300, new ActionListener() {
					public int counter;   
				    public void actionPerformed(ActionEvent e) {
				    	IconBox1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + Bandit.GenerateNumber1(1) + ".png")));
				        counter++;
				        //Beep();
				        if (counter == 16) {
				        	IconBox1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn1 + ".png")));
				        	// Icongrey1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + gfrn1 + "g.png")));
				            spin1.stop();
				        } 
				    }
				});
				spin1.start();
				if (spin1.isRunning()) {
					IconBox1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn1 + ".png")));
					// Icongrey1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + gfrn1 + "g.png")));
				}
				list.clear();
			} else {
				btnNudge.setEnabled(false);
				list.clear();
			}
			
			// if hold button isn't selected 2
			if (!chckbxHold2.isSelected()) {
				frn2 = Bandit.GenerateNumber1(2);
				int gfrn2 = frn2 + 1;
				switch (gfrn2) {
				case 9: gfrn2  = 1;
					break;
				}
				Icongrey2.setVisible(true);
				spin2 = new Timer(300, new ActionListener() {
					public int counter;   
				    public void actionPerformed(ActionEvent e) {
				    	IconBox2.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + Bandit.GenerateNumber1(2) + ".png")));
				        counter++;
				        //Beep();
				        if (counter == 20) {
				        	IconBox2.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn2 + ".png")));
				        	// Icongrey1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + gfrn1 + "g.png")));
				            spin2.stop();
				        }   
				    }
				});
				spin2.start();
				if (spin2.isRunning()) {
					IconBox2.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn2 + ".png")));
					// Icongrey2.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + gfrn2 + "g.png")));
				}
				list.clear();
			} else {
				btnNudge2.setEnabled(false);
				list.clear();
			}
			
			// if hold button isn't selected 3
			if (!chckbxHold3.isSelected()) {
				frn3 = Bandit.GenerateNumber1(3);
				int gfrn3 = frn3 + 1;
				switch (gfrn3) {
				case 9: gfrn3  = 1;
					break;
				}
				Icongrey3.setVisible(true);
				spin3 = new Timer(300, new ActionListener() {
					public int counter;   
				    public void actionPerformed(ActionEvent e) {
				    	IconBox3.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + Bandit.GenerateNumber1(3) + ".png")));
				        counter++;
				        Beep();
				        if (counter == 24) {
				        	IconBox3.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn3 + ".png")));
				        	// Icongrey1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + gfrn1 + "g.png")));
				            spin3.stop();
				        }
				    }
				});
				spin3.start();
				if (spin3.isRunning()) {
					IconBox3.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn3 + ".png")));
					// Icongrey3.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + gfrn3 + "g.png")));
				}
			} else {
				btnNudge3.setEnabled(false);
			}
			disable(btnSpin, 7200);
			CreateArray();
			logGame("");
		}
		
	}
	public void CreateArray() {
		list.add(frn1);
		list.add(frn2);
		list.add(frn3);
		
		// check the array
		
		checkIfWin();
		System.out.println(Arrays.asList(list));
	}
	public void Beep() {
		if (!chckbxMute.isSelected()) {
			Toolkit.getDefaultToolkit().beep();
		}
    }
	
	     
	
	public void disableHold() {
		chckbxHold.setEnabled(false);
		chckbxHold2.setEnabled(false);
		chckbxHold3.setEnabled(false);
	}
	public void untickHold() {
		chckbxHold.setSelected(false);
		chckbxHold2.setSelected(false);
		chckbxHold3.setSelected(false);
	}
	public void disableNudge() {
		btnNudge.setEnabled(false);
		btnNudge2.setEnabled(false);
		btnNudge3.setEnabled(false);
	}
	
	public void checkIfWinNudge() {
		if( Collections.frequency(list, 1) == list.size() || Collections.frequency(list, 2) == list.size() || Collections.frequency(list, 3) == list.size() || Collections.frequency(list, 4) == list.size() || Collections.frequency(list, 5) == list.size() || Collections.frequency(list, 6) == list.size() || Collections.frequency(list, 7) == list.size() || Collections.frequency(list, 8) == list.size() ) {
			Popup.infoBox("You win 1.00 Credit!", "You Win!");
			disableHold();
			untickHold();
			disableNudge();
			Bandit.WinAmount(3);
			winningsFields.setText(Bandit.setWinningCredit().toString());
			btnViewReplay.setVisible(true);
		}
		if ((list.get(0) == list.get(1) || list.get(1) == list.get(2))) {
			if( Collections.frequency(list, 1) == list.size() || Collections.frequency(list, 2) == list.size() || Collections.frequency(list, 3) == list.size() || Collections.frequency(list, 4) == list.size() || Collections.frequency(list, 5) == list.size() || Collections.frequency(list, 6) == list.size() || Collections.frequency(list, 7) == list.size() || Collections.frequency(list, 8) == list.size() ) {
    		} else {
    			Popup.infoBox("You win 0.50 Credit!", "You Win!");
    			Bandit.WinAmount(2);
    			disableHold();
    			untickHold();
    			disableNudge();
    			btnViewReplay.setVisible(true);
    		}		
			winningsFields.setText(Bandit.setWinningCredit().toString());
		}
		if ( (Collections.frequency(list, 7)) == 1) {
			Popup.infoBox("You win 0.20 Credit!", "You Win!");
			disableHold();
			untickHold();
			disableNudge();
			Bandit.WinAmount(1);
			winningsFields.setText(Bandit.setWinningCredit().toString());
			btnViewReplay.setVisible(true);
		}
	}
	
	public void checkIfWin() {
		if( Collections.frequency(list, 1) == list.size() || Collections.frequency(list, 2) == list.size() || Collections.frequency(list, 3) == list.size() || Collections.frequency(list, 4) == list.size() || Collections.frequency(list, 5) == list.size() || Collections.frequency(list, 6) == list.size() || Collections.frequency(list, 7) == list.size() || Collections.frequency(list, 8) == list.size() ) {
				ActionListener listenerWin3 = new ActionListener(){
		        	public void actionPerformed(ActionEvent event){
		        		Popup.infoBox("You win 1.00 Credit!", "You Win!");
		        		disableHold();
		        		untickHold();
		        		disableNudge();
		        		Bandit.WinAmount(3);
		        		winningsFields.setText(Bandit.setWinningCredit().toString());
		        		btnViewReplay.setVisible(true);
		        	}
		    	};
		    	Timer timerWait3Win = new Timer(7200, listenerWin3);
		    	timerWait3Win.setRepeats(false);
		    	timerWait3Win.start();
		}

		if ((list.get(0) == list.get(1) || list.get(1) == list.get(2))) {
			ActionListener listenerWin2 = new ActionListener(){
	        	public void actionPerformed(ActionEvent event){
	        		if( Collections.frequency(list, 1) == list.size() || Collections.frequency(list, 2) == list.size() || Collections.frequency(list, 3) == list.size() || Collections.frequency(list, 4) == list.size() || Collections.frequency(list, 5) == list.size() || Collections.frequency(list, 6) == list.size() || Collections.frequency(list, 7) == list.size() || Collections.frequency(list, 8) == list.size() ) {
	        		} else {
	        			Popup.infoBox("You win 0.50 Credit!", "You Win!");
	        			Bandit.WinAmount(2);
	        			untickHold();
	        			disableHold();
	        			disableNudge();
	        			btnViewReplay.setVisible(true);
	        		}
	        		winningsFields.setText(Bandit.setWinningCredit().toString());
	        	}
	    	};
	    	Timer timerWait2Win = new Timer(7200, listenerWin2);
	    	timerWait2Win.setRepeats(false);
	    	timerWait2Win.start();
		}
		if ( (Collections.frequency(list, 7)) == 1) {
			ActionListener listenerWin1 = new ActionListener(){
	        	public void actionPerformed(ActionEvent event){
	        		Popup.infoBox("You win 0.20 Credit!", "You Win!");
	        		disableHold();
	        		untickHold();
	        		disableNudge();
	        		Bandit.WinAmount(1);
	        		winningsFields.setText(Bandit.setWinningCredit().toString());
	        		btnViewReplay.setVisible(true);
	        		System.out.println(Bandit.setWinningCredit());
	        	}
	    	};
	    	Timer timerWait1Win = new Timer(7200, listenerWin1);
	    	timerWait1Win.setRepeats(false);
	    	timerWait1Win.start();
		}
	}
	private void actionReplay() {
		
		lblReplay.setVisible(true);
	
        FileReader file = null;
		try {
			file = new FileReader("Log.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  //address of the file 
        Scanner sc=new Scanner(file);
        ArrayList<String> Lines = new ArrayList<String>();
        
        while(sc.hasNextLine()){  //checking for the presence of next Line
            Lines.add(sc.nextLine());  //reading and storing all lines
        
        }
        sc.close();  //close the scanner
        String lastLine = Lines.get(Lines.size()-1);
        int cleanResults = Integer.parseInt(lastLine.replaceAll("[^0-9]",""));
        
        int numRes = cleanResults;

        if (numRes<0) numRes=-numRes; // maybe you'd like to support negatives
        LinkedList<Integer> digits = new LinkedList<Integer>();

        while (numRes>0) {
            digits.add(0, numRes%10);
            numRes=numRes/10;
        }
        System.out.println(Arrays.toString(digits.toArray()));
        
        int rvalue1 = digits.get(0);
        int rvalue2 = digits.get(1);
        int rvalue3 = digits.get(2);
//        frn1 = rvalue1;
//        frn2 = rvalue2;
//        frn3 = rvalue3;
        
        Icongrey1.setVisible(false);
        Icongrey2.setVisible(false);
        Icongrey3.setVisible(false);
        
        // spin1replay
     	frn1 = rvalue1;
     	int gfrn1 = frn1 + 1;
     	switch (gfrn1) {
     	case 9: gfrn1 = 1;
     		break;
     	}

     	spin1replay = new Timer(600, new ActionListener() {
     	public int counter;   
     	public void actionPerformed(ActionEvent e) {
     		IconBox1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + Bandit.GenerateNumber1(1) + ".png")));
     		counter++;
     		//Beep();
     		if (counter == 16) {
     			IconBox1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn1 + ".png")));
     			spin1replay.stop();
     		} 
     	}
     	});
     	spin1replay.start();
     		if (spin1replay.isRunning()) {
     				IconBox1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn1 + ".png")));
     			}
     		
     		//spin2replay
     		frn2 = rvalue2;
         	int gfrn2 = frn2 + 1;
         	switch (gfrn2) {
         	case 9: gfrn2 = 1;
         		break;
         	}

         	spin2replay = new Timer(600, new ActionListener() {
         	public int counter;   
         	public void actionPerformed(ActionEvent e) {
         		IconBox2.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + Bandit.GenerateNumber1(2) + ".png")));
         		counter++;
         		//Beep();
         		if (counter == 16) {
         			IconBox2.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn2 + ".png")));
         			spin2replay.stop();
         		} 
         	}
         	});
         	spin2replay.start();
         		if (spin2replay.isRunning()) {
         				IconBox2.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn2 + ".png")));
         			}
         		
         		
         		//spin3replay
         		frn3 = rvalue3;
             	int gfrn3 = frn3 + 1;
             	switch (gfrn3) {
             	case 9: gfrn3 = 1;
             		break;
             	}

             	spin3replay = new Timer(600, new ActionListener() {
             	public int counter;   
             	public void actionPerformed(ActionEvent e) {
             		IconBox3.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + Bandit.GenerateNumber1(3) + ".png")));
             		counter++;
             		//Beep();
             		if (counter == 16) {
             			IconBox3.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn3 + ".png")));
             			spin3replay.stop();
             			lblReplay.setVisible(false);
             		} 
             	}
             	});
             	spin3replay.start();
             		if (spin3replay.isRunning()) {
             				IconBox3.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/" + frn3 + ".png")));
             			}
             btnViewReplay.setVisible(false);
	}

	private void initialize() {
		frmOneArmBandit = new JFrame();
		frmOneArmBandit.getContentPane().setBackground(Color.WHITE);
		frmOneArmBandit.setBackground(SystemColor.inactiveCaption);
		frmOneArmBandit.setForeground(SystemColor.desktop);
		frmOneArmBandit.setTitle("One Arm Bandit");
		frmOneArmBandit.setIconImage(Toolkit.getDefaultToolkit().getImage(BanditVisual.class.getResource("/Bandit/oab.png")));
		frmOneArmBandit.setBounds(100, 100, 807, 502);
		frmOneArmBandit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmOneArmBandit.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/slots-3000.png")));
		label.setBounds(21, 11, 319, 120);
		frmOneArmBandit.getContentPane().add(label);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);
		separator.setForeground(Color.DARK_GRAY);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(310, 0, 34, 463);
		frmOneArmBandit.getContentPane().add(separator);
		
		
		btnSpin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateSpin();
				spinBandit();
			}
		});
		btnSpin.setForeground(Color.WHITE);
		btnSpin.setBackground(new Color(105, 105, 105));
		btnSpin.setFont(new Font("Eras Demi ITC", Font.BOLD, 16));
		btnSpin.setBounds(21, 363, 257, 50);
		frmOneArmBandit.getContentPane().add(btnSpin);
		
		JPanel panel = new JPanel();
		panel.setBounds(310, 0, 481, 463);
		frmOneArmBandit.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(204, 204, 204)));
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(116, 145, 75, 75);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		IconBox1.setBounds(0, 0, 75, 75);
		IconBox1.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/1.png")));
		panel_3.add(IconBox1);
		IconBox1.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(204, 204, 204)));
		panel_4.setLayout(null);
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(201, 145, 75, 75);
		panel.add(panel_4);
		
		
		IconBox2.setHorizontalAlignment(SwingConstants.CENTER);
		IconBox2.setBounds(0, 0, 75, 75);
		panel_4.add(IconBox2);
		IconBox2.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/2.png")));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(204, 204, 204)));
		panel_5.setLayout(null);
		panel_5.setBackground(Color.WHITE);
		panel_5.setBounds(286, 145, 75, 75);
		panel.add(panel_5);
		
		
		IconBox3.setHorizontalAlignment(SwingConstants.CENTER);
		IconBox3.setBounds(0, 0, 75, 75);
		panel_5.add(IconBox3);
		IconBox3.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/3.png")));
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBackground(new Color(255, 255, 255));
		btnExit.setFont(new Font("Century Gothic", Font.BOLD, 13));
		btnExit.setBounds(401, 414, 70, 38);
		panel.add(btnExit);
		
		winningsFields.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(220, 20, 60)));
		winningsFields.setBackground(Color.WHITE);
		winningsFields.setEditable(false);
		winningsFields.setText("0.00");
		winningsFields.setHorizontalAlignment(SwingConstants.CENTER);
		winningsFields.setFont(new Font("Tahoma", Font.PLAIN, 13));
		winningsFields.setColumns(10);
		winningsFields.setBounds(85, 412, 59, 40);
		panel.add(winningsFields);
		
		JButton btnucx = new JButton("");
		btnucx.setFocusPainted(false);
		btnucx.setToolTipText("Transfer Winnings");
		btnucx.setIcon(new ImageIcon(BanditVisual.class.getResource("/Bandit/arrows1.png")));
		btnucx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Bandit.setWinningCredit().compareTo(BigDecimal.ZERO) <= 0.00) {
					Popup.infoBox("No Winnings to Transfer", "Notice");
				} else {
					Bandit.TranferWinning();
					creditAmount.setText(Bandit.setCredit().toString());
					winningsFields.setText(Bandit.setWinningCredit().toString());
					Popup.infoBox("Winnings have been Transfered", "Notice");
				}
			}
		});
		btnucx.setForeground(Color.BLACK);
		btnucx.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnucx.setBackground(new Color(255, 255, 255));
		btnucx.setBounds(21, 412, 59, 40);
		panel.add(btnucx);
		
		JLabel lblCredits = new JLabel("Credits.");
		lblCredits.setHorizontalAlignment(SwingConstants.LEFT);
		lblCredits.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblCredits.setBounds(154, 412, 60, 39);
		panel.add(lblCredits);
		
		JLabel lblWinnings = new JLabel("Winnings");
		lblWinnings.setHorizontalAlignment(SwingConstants.LEFT);
		lblWinnings.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblWinnings.setBounds(155, 431, 109, 30);
		panel.add(lblWinnings);
		
		chckbxHold.setVerticalAlignment(SwingConstants.BOTTOM);
		chckbxHold.setBounds(116, 100, 75, 38);
		panel.add(chckbxHold);
		
		
		chckbxHold2.setVerticalAlignment(SwingConstants.BOTTOM);
		chckbxHold2.setBounds(201, 100, 75, 38);
		panel.add(chckbxHold2);
		
		
		chckbxHold3.setVerticalAlignment(SwingConstants.BOTTOM);
		chckbxHold3.setBounds(286, 100, 75, 38);
		panel.add(chckbxHold3);
		
		JButton btnLogs = new JButton("View Game Logs");
		btnLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().open(new java.io.File("Log.txt"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnLogs.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		btnLogs.setBackground(Color.WHITE);
		btnLogs.setFocusPainted(false);
		btnLogs.setBounds(21, 11, 158, 38);
		panel.add(btnLogs);
		
		btnNudge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nudgebutton1();
				btnNudge.setEnabled(false);
				btnNudge2.setEnabled(false);
				btnNudge3.setEnabled(false);
				Icongrey3.setVisible(false);
				Icongrey2.setVisible(false);
				Icongrey1.setVisible(false);
			}
		});
		btnNudge.setFont(new Font("Century Gothic", Font.BOLD, 11));
		btnNudge.setBackground(Color.WHITE);
		btnNudge.setBounds(116, 220, 75, 23);
		panel.add(btnNudge);
		
		
		btnNudge2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nudgebutton2();
				btnNudge.setEnabled(false);
				btnNudge2.setEnabled(false);
				btnNudge3.setEnabled(false);
				Icongrey3.setVisible(false);
				Icongrey2.setVisible(false);
				Icongrey1.setVisible(false);
			}
		});
		btnNudge2.setFont(new Font("Century Gothic", Font.BOLD, 11));
		btnNudge2.setBackground(Color.WHITE);
		btnNudge2.setBounds(201, 220, 75, 23);
		panel.add(btnNudge2);
		
		
		btnNudge3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nudgebutton3();
				btnNudge.setEnabled(false);
				btnNudge2.setEnabled(false);
				btnNudge3.setEnabled(false);
				Icongrey3.setVisible(false);
				Icongrey2.setVisible(false);
				Icongrey1.setVisible(false);
			}
		});
		btnNudge3.setFont(new Font("Century Gothic", Font.BOLD, 11));
		btnNudge3.setBackground(Color.WHITE);
		btnNudge3.setBounds(286, 220, 75, 23);
		panel.add(btnNudge3);
		
		btnNudge.setEnabled(false);
		btnNudge2.setEnabled(false);
		btnNudge3.setEnabled(false);
		
		Icongrey1.setHorizontalAlignment(SwingConstants.CENTER);
		Icongrey1.setBounds(116, 247, 75, 75);
		panel.add(Icongrey1);
		
		Icongrey2.setHorizontalAlignment(SwingConstants.CENTER);
		Icongrey2.setBounds(201, 249, 75, 75);
		panel.add(Icongrey2);
		
		Icongrey3.setHorizontalAlignment(SwingConstants.CENTER);
		Icongrey3.setBounds(286, 249, 75, 75);
		panel.add(Icongrey3);
		
		chckbxMute.setBounds(416, 7, 59, 38);
		panel.add(chckbxMute);
		btnViewReplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionReplay();
			}
		});
		
		btnViewReplay.setForeground(Color.BLACK);
		btnViewReplay.setBackground(Color.ORANGE);
		btnViewReplay.setBounds(136, 322, 200, 50);
		btnViewReplay.setVisible(false);
		panel.add(btnViewReplay);
		
		JButton btnCashOutWinnings = new JButton("Cash Out Winnings");
		btnCashOutWinnings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Bandit.getCurrentWinningCredit().compareTo(BigDecimal.ZERO) <= 0.00) {
					Popup.infoBox("No Winnings to Cash Out!", "Notice");
				} else {
					Bandit.cashOut();
					Popup.infoBox(winningsFields.getText() + " Has been cashed out", "Notice");
					winningsFields.setText("0.00");
				}
			}
		});
		btnCashOutWinnings.setBackground(Color.WHITE);
		btnCashOutWinnings.setFont(new Font("Century Gothic", Font.BOLD, 11));
		btnCashOutWinnings.setBounds(225, 414, 166, 38);
		panel.add(btnCashOutWinnings);
		lblReplay.setVisible(false);
		lblReplay.setForeground(new Color(139, 0, 0));
		lblReplay.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblReplay.setBounds(377, 171, 94, 50);
		panel.add(lblReplay);
		
		creditAmount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		creditAmount.setHorizontalAlignment(SwingConstants.CENTER);
		creditAmount.setText("0.00");
		creditAmount.setEditable(false);
		creditAmount.setBounds(151, 312, 71, 40);
		frmOneArmBandit.getContentPane().add(creditAmount);
		creditAmount.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Credits:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel_1.setBounds(81, 312, 60, 39);
		frmOneArmBandit.getContentPane().add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 433, 310, 30);
		frmOneArmBandit.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		datetime = new JLabel();
		datetime.setHorizontalAlignment(SwingConstants.CENTER);
		datetime.setBounds(10, 0, 290, 30);
		panel_1.add(datetime);
		
		JButton buttonAdd = new JButton("+");
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				creditAmount.setText(Bandit.getCredit().toString());
			}	
		});
		buttonAdd.setForeground(Color.BLACK);
		buttonAdd.setBackground(Color.LIGHT_GRAY);
		buttonAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
		buttonAdd.setBounds(228, 312, 50, 40);
		frmOneArmBandit.getContentPane().add(buttonAdd);
		
		JLabel lblCredit = new JLabel("1 Credit = 5 Spins");
		lblCredit.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCredit.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblCredit.setBounds(30, 332, 109, 30);
		frmOneArmBandit.getContentPane().add(lblCredit);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 135, 310, 102);
		frmOneArmBandit.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblMatchingShapes = new JLabel("3 Matching Shapes = 1 Credit");
		lblMatchingShapes.setFont(new Font("Eras Medium ITC", Font.PLAIN, 12));
		lblMatchingShapes.setBounds(22, 33, 169, 28);
		panel_2.add(lblMatchingShapes);
		
		JLabel labelGuide = new JLabel("Guide:");
		labelGuide.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelGuide.setBounds(22, 11, 55, 21);
		panel_2.add(labelGuide);
		
		JLabel lblMatchingShapes_1 = new JLabel("2 Matching Shapes = 0.5 Credits");
		lblMatchingShapes_1.setBackground(new Color(245, 245, 245));
		lblMatchingShapes_1.setFont(new Font("Eras Medium ITC", Font.PLAIN, 12));
		lblMatchingShapes_1.setBounds(22, 49, 180, 28);
		panel_2.add(lblMatchingShapes_1);
		
		JLabel lblDiamond = new JLabel("1 Diamond = 0.2 Credits");
		lblDiamond.setFont(new Font("Eras Medium ITC", Font.PLAIN, 12));
		lblDiamond.setBounds(22, 65, 180, 28);
		panel_2.add(lblDiamond);
		datetimer();
		
		bandit = new Bandit();
		
	}
	
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}
