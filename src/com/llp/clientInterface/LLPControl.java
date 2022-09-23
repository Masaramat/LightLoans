package com.llp.clientInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.llp.main.UserLoginFrame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class LLPControl extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	public static String IP_ADDRESS;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LLPControl frame = new LLPControl();
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
	public LLPControl() {
		setTitle("Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 392, 202);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IP_ADDRESS = textField.getText();
				lblNewLabel_1.setText("Your IP: " + IP_ADDRESS);
				try {
					UserLoginFrame frame = new UserLoginFrame();
					frame.setVisible(true);
					dispose();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setBounds(220, 94, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("IP Address");
		lblNewLabel.setBounds(10, 59, 105, 23);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(100, 60, 209, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(10, 138, 329, 14);
		contentPane.add(lblNewLabel_1);
	}
}
