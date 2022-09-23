package com.llp.main;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.llp.api.LLPMainInterface;
import com.llp.clientInterface.InterfaceGenerator;


public class TestNotification extends JFrame {

	private JPanel contentPane;
	
	static Timer timer = new Timer();
	static int action_count_holder = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				
					new TimeTask().run();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestNotification() {
		setTitle("Notification");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
	}
	
	 public void displayTray(int messages, String status, String branch) throws AWTException {
	        //Obtain only one instance of the SystemTray object
	        SystemTray tray = SystemTray.getSystemTray();

	        //If the icon is a file
	        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
	        //Alternative (if the icon is on the classpath):
	        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

	        TrayIcon trayIcon = new TrayIcon(image, "");
	        //Let the system resize the image if needed
	        trayIcon.setImageAutoSize(true);
	        //Set tooltip text for the tray icon
	        trayIcon.setToolTip("");
	        tray.add(trayIcon);

	        trayIcon.displayMessage("New Loan Application", messages+ " "+ status.substring(0, 1).toUpperCase() + status.substring(1)+"  Loan Applications", MessageType.INFO);
	    }
	 
	 public static void getMessage(String user) {
		LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
		String status = "";
		switch (user) {
		case "Marketing Officer":
			status = "returned";
			
			break;
			
		case "Head of Marketing":
			status = "pending";		
						
			break;
		case "Marketing Supervisor":
			status = "pending";
				
						
			break;
			
			
		case "Credit Officer":
				
			status = "approved";		
						
			
			break;
			
			
		case "Head of Credit":
			
			status = "approved";
			break;
			
			
		case "Branch Manager":
			status = "forwarded";
			
			break;
		case "Managing Director":
			
			status = "recommended";
			break;
			
		case "Auditor":
			status = "offered";	
			break;
			
		case "Operations Officer":
			status = "audited";	
				
			break;
			
			
		case "Head of Operations":
			status = "audited";
			
			break;
			
		case "MIS Officer":
			
			status = "pending";
			break;
			
		case "System Administrator":
			status = "pending";
			
			break;	
			

		default:
			break;
		}	
		 try {
			 int pending_messages = mainInterface.getApplicationsCount(user, status, "");
			 if (pending_messages > action_count_holder) {
				 if (SystemTray.isSupported()) {
						TestNotification td = new TestNotification();
			            td.displayTray(pending_messages, status, "");			           
			        } else {
			            System.err.println("System tray not supported!");
			        }
				 
				
			}
			 action_count_holder = pending_messages;
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	 }
	 
	 public static class TimeTask extends TimerTask {
			
		    public void run() {
		    	try {
		    		
		    		int delay = (4000);
			        timer.schedule(new TimeTask(), delay); 
			        getMessage("MIS Officer");
			        
			        System.gc();
				} catch (Exception e) {
					e.printStackTrace();
				}
		        
		        
		    }
		}

}
