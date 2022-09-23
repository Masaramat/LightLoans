package com.llp.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.llp.api.LLPMainInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.Customer;
import com.llp.entities.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import javax.swing.SwingConstants;

public class NewInterviewFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextField accountNoField;
	private static JTextField sessionIdField;
	private static JTextField nameField;
	private static JTextField dobField;
	private static JTextField statusField;
	private static JTextField userField;
	private static JTextArea textArea;
	private JButton interviewButton;
	
	private static Customer customer1 = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewInterviewFrame frame = new NewInterviewFrame(new User(), new Customer(), "");
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
	public NewInterviewFrame(final User user, final Customer customer, String source) throws RemoteException{
		setTitle("Customer Interview Session\r\n");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 150, 572, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Account No");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 101, 73, 20);
		contentPane.add(lblNewLabel);
		
		final LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
		
		accountNoField = new JTextField();
		accountNoField.setEditable(false);
		accountNoField.setBounds(105, 101, 151, 20);
		contentPane.add(accountNoField);
		accountNoField.setColumns(10);
		
		JButton searchButton = new JButton("");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					CustomerSearchFrame frame = new CustomerSearchFrame("New-Interview");
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		searchButton.setIcon(new ImageIcon(NewInterviewFrame.class.getResource("/resources/search04.png")));
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchButton.setBounds(266, 100, 48, 29);
		contentPane.add(searchButton);
		
		JLabel lblNewLabel_1 = new JLabel("Customer Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 144, 85, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Address");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(10, 175, 85, 20);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Date of Birth");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(10, 227, 85, 20);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Interview Date");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(10, 258, 85, 20);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblStaff = new JLabel("Staff");
		lblStaff.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblStaff.setBounds(10, 320, 85, 20);
		contentPane.add(lblStaff);
		
		JLabel lblNewLabel_4_1 = new JLabel("Interview Status");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4_1.setBounds(10, 289, 85, 20);
		contentPane.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_4_2 = new JLabel("Session ID");
		lblNewLabel_4_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4_2.setBounds(10, 70, 73, 20);
		contentPane.add(lblNewLabel_4_2);
		
		sessionIdField = new JTextField();
		sessionIdField.setEditable(false);
		sessionIdField.setBounds(105, 70, 121, 20);
		contentPane.add(sessionIdField);
		sessionIdField.setColumns(10);
		
		nameField = new JTextField(); 
		nameField.setText("");
		nameField.setEditable(false);
		nameField.setBounds(105, 144, 283, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(105, 175, 315, 41);
		contentPane.add(textArea);
		
		dobField = new JTextField();
		dobField.setEditable(false);
		dobField.setBounds(105, 227, 131, 20);
		contentPane.add(dobField);
		dobField.setColumns(10);
		
		statusField = new JTextField();
		statusField.setForeground(new Color(0, 128, 0));
		statusField.setEditable(false);
		statusField.setText("Open");
		statusField.setBounds(103, 289, 110, 20);
		contentPane.add(statusField);
		statusField.setColumns(10);
		
		userField = new JTextField();
		userField.setText(user.getUsername());
		userField.setEditable(false);
		userField.setBounds(105, 320, 250, 20);
		contentPane.add(userField);
		userField.setColumns(10);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(104, 258, 109, 20);
		dateChooser.setDate(new Date());
		dateChooser.setEnabled(false);
		contentPane.add(dateChooser);
		
		final JButton startButton = new JButton("Start Session");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(accountNoField.getText().length()<1) {
						JOptionPane.showMessageDialog(null, "Please enter a valid account number.");
					}else {					
						int xy = mainInterface.getSessionStatus(accountNoField.getText());
					
						if(xy>0) {
							JOptionPane.showMessageDialog(null, "Customer has an open interview session. \n Go to interview.");
						}else {
							String ss_id = mainInterface.createInterviewSession(accountNoField.getText(), user.getUserId());
							JOptionPane.showMessageDialog(null, "Success!");
							sessionIdField.setText(ss_id);
							startButton.setEnabled(false);
							interviewButton.setEnabled(true);
						}				
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
		});
		startButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		startButton.setBounds(165, 372, 131, 23);
		contentPane.add(startButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cancelButton.setBounds(447, 372, 99, 23);
		contentPane.add(cancelButton);
		
		interviewButton = new JButton("Go to Assessment");
		interviewButton.setEnabled(false);
		interviewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					InterviewFaceSheet ifs = new InterviewFaceSheet(user, customer1);
					ifs.setVisible(true);
					dispose();
				}catch(Exception e4) {
					e4.printStackTrace();
				}
			}
			
		});
		interviewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		interviewButton.setBounds(306, 372, 131, 23);
		contentPane.add(interviewButton);
		
		JLabel lblNewLabel_4_2_1 = new JLabel("New Customer Interview Session");
		lblNewLabel_4_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4_2_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4_2_1.setBounds(10, 11, 536, 29);
		contentPane.add(lblNewLabel_4_2_1);
		
		if(source.equalsIgnoreCase("ifs")) {
			searchButton.setEnabled(false);
		}
		
		customer1=customer;
		fillCustomerDetails(customer1);
	}
	
	 
	public static void fillCustomerDetails(Customer customer) {
		customer1 = customer;
		accountNoField.setText(customer1.getLmfbAccountNo());
		nameField.setText(customer1.getSurname()+" " + customer.getOthernames());
		textArea.setText(customer1.getAddress());
		dobField.setText(customer1.getDob());	
		
	}
	
	
	public String leftPad(String value, int lgt) {
		int diff = lgt - value.length();
		
		for(int i=0; i<diff; i++) {
			value = "0" + value;
		}				
		return value;
	} 
	
	
	
	
	
	
	
	
}
