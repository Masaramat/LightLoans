package com.llp.server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.llp.entities.User;

import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ControlPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea;
	private JButton btnStart;
	private JButton btnStop;
	
	private LLPServer frameLlpServer = new LLPServer();
	private JTextField ipField;
	
	public static String IP_ADDRESS = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControlPanel frame = new ControlPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ControlPanel() {
		setTitle("LLP Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 150, 450, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Control Panel");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setForeground(new Color(107, 142, 35));
		lblNewLabel.setBounds(0, 11, 434, 33);
		contentPane.add(lblNewLabel);
		
		
		btnStart = new JButton("Start Server");
		btnStart.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(ipField.getText().length()<1) {
					
				}else {
					String message = "Starting Server...";
					textArea.setText(message);
					frameLlpServer.startServer();
					textArea.setText(message + "\nLight Loans Server started at "+ new Date()+ "... ");
					btnStart.setEnabled(false);
					btnStop.setEnabled(true);
				}
				
				
				
			}
		});
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnStart.setBounds(25, 97, 145, 33);
		contentPane.add(btnStart);
		
		btnStop = new JButton("Stop Server");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				String message = "Stoping Server...";
				textArea.setText(message);
				frameLlpServer.stopServer();
				textArea.setText(message + "\nLight Loans Server stopped at "+ new Date()+ "... ");
				btnStart.setEnabled(true);
				btnStop.setEnabled(false);
				
			}
		});
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnStop.setEnabled(false);
		btnStop.setBounds(231, 97, 145, 33);
		contentPane.add(btnStop);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Server status", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 141, 412, 141);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textArea = new JTextArea();
		textArea.setBounds(6, 16, 400, 119);
		panel.add(textArea);
		
		ipField = new JTextField();
		ipField.setBounds(88, 59, 200, 23);
		contentPane.add(ipField);
		ipField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Your IP");
		lblNewLabel_1.setBounds(25, 55, 86, 27);
		contentPane.add(lblNewLabel_1);
		User.ipAddress = ipField.getText();
	}
}
