package com.llp.main;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;

import java.awt.EventQueue;
import java.awt.Font;



public class LightSplash extends JWindow implements ActionListener{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; 
	private JProgressBar progressBar;
	
	int i;
	private JLabel lblNewLabel;

	
	public static void main(String[] args) {
		int i=0;
				try {
					final LightSplash window = new LightSplash();
					window.setVisible(true);	
					  final UserLoginFrame frame = new UserLoginFrame();
					 
					
					while(i<=100) {
						window.progressBar.setValue(i);
						Thread.sleep(70);
						i+=2;
					}				
				
					 window.dispose();
					 frame.setVisible(true);	
					 frame.usernameField.requestFocusInWindow();
						EventQueue.invokeLater( new Runnable() {
							@Override
							public void run() {
								frame.usernameField.requestFocusInWindow();
							}
						} );
						frame.getRootPane().setDefaultButton(frame.btnNewButton);
//					
//					new Timer().schedule(new TimerTask() {
//					    public void run() {				         				        
//					 //write any action here to happen after 7 seconds 					 
//					    }
//					}, 7000);		
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Unable to connect to server.");
					System.exit(0);
				}
			
	}

	/**
	 * Create the application.
	 */
	public LightSplash() {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);	
		contentPane.setLayout(null);		
		
		lblNewLabel = new JLabel("Loading...");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(142, 247, 109, 14);
		contentPane.add(lblNewLabel);
		
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setBounds(142, 266, 315, 20);
		contentPane.add(progressBar);
			
		JLabel label = new JLabel("", new ImageIcon(LightSplash.class.getResource("/resources/splash.png")), SwingConstants.CENTER);
		label.setBounds(5, 5, 590, 340);
		contentPane.add(label);
		setBounds(400, 150, 600, 350);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(i==20) {
			System.out.println("Progress Complete");			
		}
		i++;
		
	}
	
}
