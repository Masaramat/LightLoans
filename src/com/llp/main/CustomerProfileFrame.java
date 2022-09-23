package com.llp.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import com.llp.api.LLPMainInterface;
import com.llp.api.LLPSetupInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.Branch;
import com.llp.entities.Customer;
import com.llp.entities.OtherBankAccount;
import com.llp.entities.User;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CustomerProfileFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JTextField idField;
	private static JTextField acctNoField;
	private static JTextField surnameField;
	private static JTextField othernamesField;
	private static JTextField bvnField;
	private static JTextField phoneField;
	private static JTextField wivesField;
	private static JTextField childField;
	private static JTextField childSchField;
	
	final private JLabel printLbl;
	final private JLabel printLbl_1;
	final private JLabel printLbl_2;
	final private JLabel acctLbl;
	final private JLabel bvnLbl;
	final private JLabel phoneLbl;
	final private JLabel surnameLbl;
	final private JLabel othernamesLbl;
	final private JLabel addressLbl;
	final private JLabel dateLbl;
	private static JButton updateButton;
	private static JButton saveButton;
	private static  JDateChooser dobChooser;
	private static JRadioButton rdbtnMale;
	private static JRadioButton rdbtnFemale;
	
	@SuppressWarnings("rawtypes")
	private static JComboBox comboBox;
	private static JTextArea addressArea;
	ButtonGroup bg = new ButtonGroup();	
	CardLayout cLayout = new CardLayout();				
			
	private ArrayList<Branch> branch_list = null;
	private static JTextField titleField;
	private JPanel panel_one;
	private JButton btnNext;
	private JPanel panel_two;
	private static JTable table;
	private JTextField acctNameField;
	private JTextField otherAcctNoField;
	private JTextField otherAcctTypeField;
	private JTextField otherBankName;
	private JLabel otherAcctLbl;
	
	private static ArrayList<OtherBankAccount> otherBankAccounts = new ArrayList<>();
	
	private Customer customer1 = new Customer();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerProfileFrame frame = new CustomerProfileFrame(new User(), "");
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CustomerProfileFrame(final User user, String frame_type) throws RemoteException{
		
		setAlwaysOnTop(true);
		setTitle("Light Loan Processor - Customer Registration Form");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 70, 730, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
		final LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
		
		
		
		branch_list = setupInterface.getAllBranches();
		
		final JPanel switch_panel = new JPanel();
		switch_panel.setBounds(10, 11, 694, 563);
		switch_panel.setLayout(cLayout);
		contentPane.add(switch_panel);
		
		panel_one = new JPanel();
		switch_panel.add(panel_one, "p_one");
		panel_one.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Customer Information");
		lblNewLabel_1.setBounds(198, 11, 313, 20);
		panel_one.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel = new JLabel("Customer ID");
		lblNewLabel.setBounds(10, 42, 90, 20);
		panel_one.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		idField = new JTextField(); 
		idField.setBounds(150, 42, 115, 20);
		panel_one.add(idField);
		idField.setEditable(false);
		idField.setColumns(10);
		
		JLabel lblBranch = new JLabel("Branch");
		lblBranch.setBounds(10, 73, 90, 20);
		panel_one.add(lblBranch);
		lblBranch.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		comboBox = new JComboBox();
		for(int i=0; i<branch_list.size(); i++) {
			comboBox.addItem(branch_list.get(i).getBranchName());
		}
		
		comboBox.setBounds(150, 73, 206, 20);
		panel_one.add(comboBox);
		comboBox.setEnabled(true);
		comboBox.setSelectedItem(user.getBranch());
		
		JLabel lblLmfbAcctNo = new JLabel("LMFB Account No");
		lblLmfbAcctNo.setBounds(10, 104, 90, 20);
		panel_one.add(lblLmfbAcctNo);
		lblLmfbAcctNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		acctNoField = new JTextField();
		acctNoField.setBounds(150, 104, 133, 20);
		panel_one.add(acctNoField);
		acctNoField.addKeyListener(new KeyAdapter() {
			String xy = "";
			@SuppressWarnings("unused")
			@Override
			public void keyReleased(KeyEvent e) {
				xy = acctNoField.getText();
				if(acctNoField.getText().length()<1) {
					acctLbl.setText("");
				}else {
					
					if(xy.length()==12) {
						acctLbl.setText("");
					}
					
					try {
						long number = Long.parseLong(acctNoField.getText());
						
						}catch(Exception ex) {
							
							acctNoField.setText("");
							acctLbl.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		acctNoField.setColumns(10);
		
		JButton searchButton = new JButton("");
		searchButton.setBounds(293, 103, 48, 29);
		panel_one.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					CustomerSearchFrame frame;
					frame = new CustomerSearchFrame("Customer-Reg");
					frame.setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		searchButton.setIcon(new ImageIcon(CustomerProfileFrame.class.getResource("/resources/search04.png")));
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setBounds(10, 171, 90, 20);
		panel_one.add(lblSurname);
		lblSurname.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		surnameField = new JTextField();
		surnameField.setBounds(150, 171, 133, 20);
		panel_one.add(surnameField);
		surnameField.setColumns(10);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(10, 140, 90, 20);
		panel_one.add(lblTitle);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		titleField = new JTextField();
		titleField.setBounds(150, 140, 78, 20);
		panel_one.add(titleField);
		titleField.setColumns(10);
		
		JLabel lblOthername = new JLabel("Othernames");
		lblOthername.setBounds(10, 202, 90, 20);
		panel_one.add(lblOthername);
		lblOthername.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setBounds(10, 267, 90, 20);
		panel_one.add(lblAddress);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		othernamesField = new JTextField();
		othernamesField.setBounds(150, 202, 206, 20);
		panel_one.add(othernamesField);
		othernamesField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(150, 267, 334, 45);
		panel_one.add(scrollPane);
		
		addressArea = new JTextArea();
		addressArea.setWrapStyleWord(true);
		addressArea.setLineWrap(true);
		scrollPane.setViewportView(addressArea);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setBounds(10, 233, 90, 20);
		panel_one.add(lblGender);
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(150, 229, 59, 23);
		panel_one.add(rdbtnMale);
		rdbtnMale.setFont(new Font("Tahoma", Font.PLAIN, 11));
		bg.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(211, 229, 72, 23);
		panel_one.add(rdbtnFemale);
		rdbtnFemale.setFont(new Font("Tahoma", Font.PLAIN, 11));
		bg.add(rdbtnFemale);
		
		acctLbl = new JLabel("");
		acctLbl.setBounds(367, 110, 293, 14);
		panel_one.add(acctLbl);
		acctLbl.setForeground(Color.RED);
		acctLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		addressLbl = new JLabel("");
		addressLbl.setBounds(494, 267, 179, 20);
		panel_one.add(addressLbl);
		addressLbl.setForeground(Color.RED);
		addressLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		dateLbl = new JLabel("");
		dateLbl.setBounds(287, 326, 328, 14);
		panel_one.add(dateLbl);
		dateLbl.setForeground(Color.RED);
		dateLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		dobChooser = new JDateChooser();		
		dobChooser.setBounds(150, 323, 114, 20);
		panel_one.add(dobChooser);
		dobChooser.setDateFormatString("yyyy-MM-dd");
		
		JLabel lblBirthDate = new JLabel("Date of Birth");
		lblBirthDate.setBounds(10, 323, 90, 20);
		panel_one.add(lblBirthDate);
		lblBirthDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblBvn = new JLabel("BVN");
		lblBvn.setBounds(10, 351, 90, 20);
		panel_one.add(lblBvn);
		lblBvn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		bvnField = new JTextField();
		bvnField.setBounds(150, 351, 133, 20);
		panel_one.add(bvnField);
		bvnField.addKeyListener(new KeyAdapter() {
			String xy = "";
			@Override
			public void keyReleased(KeyEvent e) {
				if(bvnField.getText().length()<1) {
					bvnLbl.setText("");
				}else {
					xy = bvnField.getText();
					if(xy.length()==11) {
						bvnLbl.setText("");
					}
					try {
						@SuppressWarnings("unused")
						long number = Long.parseLong(bvnField.getText());
						
						}catch(Exception ex) {
							
							bvnField.setText("");
							bvnLbl.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		bvnField.setColumns(10);
		
		bvnLbl = new JLabel("");
		bvnLbl.setBounds(293, 354, 328, 14);
		panel_one.add(bvnLbl);
		bvnLbl.setForeground(Color.RED);
		bvnLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		phoneLbl = new JLabel("");
		phoneLbl.setBounds(293, 385, 328, 14);
		panel_one.add(phoneLbl);
		phoneLbl.setForeground(Color.RED);
		phoneLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		phoneField = new JTextField(); 
		phoneField.setBounds(150, 382, 133, 20);
		panel_one.add(phoneField);
		phoneField.addKeyListener(new KeyAdapter() {
			String xy = "";
			@Override
			public void keyReleased(KeyEvent e) {
				if(phoneField.getText().length()<1) {
					phoneLbl.setText("");
				}else {
					xy = phoneField.getText();
					if(xy.length()==11) {
						phoneLbl.setText("");
					}
					try {
						@SuppressWarnings("unused")
						long number = Long.parseLong(phoneField.getText());
						
						}catch(Exception ex) {
							
							phoneField.setText("");
							phoneLbl.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		phoneField.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone No");
		lblPhone.setBounds(10, 382, 90, 20);
		panel_one.add(lblPhone);
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNoOfWives = new JLabel("No of Wives");
		lblNoOfWives.setBounds(10, 413, 90, 20);
		panel_one.add(lblNoOfWives);
		lblNoOfWives.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		wivesField = new JTextField();
		wivesField.setBounds(150, 413, 78, 20);
		panel_one.add(wivesField);
		wivesField.addKeyListener(new KeyAdapter() {
			String xy = "";
			@Override
			public void keyReleased(KeyEvent e) {
				if(wivesField.getText().length()<1) {
					printLbl.setText("");
				}else {
					xy = wivesField.getText();
					if(xy.length() > 0) {
						printLbl.setText("");
					}
					try {
						@SuppressWarnings("unused")
						int number = Integer.parseInt(wivesField.getText());
						
						}catch(Exception ex) {
							
							wivesField.setText("");
							printLbl.setText("Only  Numbers Allowed.");
						}
				}			
			}
		});
		wivesField.setColumns(10);
		
		printLbl = new JLabel(""); 
		printLbl.setBounds(238, 416, 371, 14);
		panel_one.add(printLbl);
		printLbl.setForeground(Color.RED);
		printLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		printLbl_1 = new JLabel("");
		printLbl_1.setBounds(238, 447, 371, 14);
		panel_one.add(printLbl_1);
		printLbl_1.setForeground(Color.RED);
		printLbl_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		printLbl_2 = new JLabel("");
		printLbl_2.setBounds(238, 478, 383, 14);
		panel_one.add(printLbl_2);
		printLbl_2.setForeground(Color.RED);
		printLbl_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		childSchField = new JTextField(); 
		childSchField.setBounds(150, 475, 78, 20);
		panel_one.add(childSchField);
		childSchField.addKeyListener(new KeyAdapter() {
			String xy = "";
			@Override
			public void keyReleased(KeyEvent e) {
				if(childSchField.getText().length()<1) {
					printLbl_2.setText("");
				}else {
					xy = childSchField.getText();
					if(xy.length() > 0) {
						printLbl_2.setText("");
					}
					try {
						@SuppressWarnings("unused")
						long number = Long.parseLong(childSchField.getText());
						
						}catch(Exception ex) {
							
							childSchField.setText("");
							printLbl_2.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		childSchField.setColumns(10);
		
		
		
		childField = new JTextField();
		childField.setBounds(150, 444, 78, 20);
		panel_one.add(childField);
		childField.addKeyListener(new KeyAdapter() {
			String xy = "";
			@Override
			public void keyReleased(KeyEvent e) {
				if(childField.getText().length()<1) {
					printLbl_1.setText("");
				}else {
					xy = childField.getText();
					if(xy.length() > 0) {
						printLbl_1.setText("");
					}
					try {
						@SuppressWarnings("unused")
						int number = Integer.parseInt(childField.getText());
						
						}catch(Exception ex) {
							
							childField.setText("");
							printLbl_1.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		childField.setColumns(10);
		
		JLabel lblNoOfChildre = new JLabel("No of Children");
		lblNoOfChildre.setBounds(10, 444, 90, 20);
		panel_one.add(lblNoOfChildre);
		lblNoOfChildre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNoOfChildren = new JLabel("No of Children in Sch.");
		lblNoOfChildren.setBounds(10, 475, 114, 20);
		panel_one.add(lblNoOfChildren);
		lblNoOfChildren.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton cancelButton_1 = new JButton("Cancel");
		cancelButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			dispose();
			}
		});
		cancelButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cancelButton_1.setBounds(595, 529, 89, 23);
		panel_one.add(cancelButton_1);
		
		btnNext = new JButton("Next >>");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String date = "";
				try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date d  = dobChooser.getDate();
				date = sdf.format(d);
				}catch(Exception ex) {
					dateLbl.setText("Invalid date format");
				}
				
				if(acctNoField.getText().length() != 12){
					acctLbl.setText("LMFB Account Number must contain 12 digits.");
					
				}else if(surnameField.getText().length() < 1){
					surnameLbl.setText("This field cannot be empty.");
				}else if(othernamesField.getText().length() < 1){
					othernamesLbl.setText("This field cannot be empty.");
				}else if(addressArea.getText().length() < 1){
					addressLbl.setText("This field cannot be empty.");
				}else if(bvnField.getText().length() != 11){
					bvnLbl.setText("BVN must contain 11 digits.");
				}else if(phoneField.getText().length() != 11){
					phoneLbl.setText("Phone Number must contain 11 digits.");
				}else if(wivesField.getText().length() < 1){
					printLbl.setText("This Field must contain at least 1 digit.");
				}else if(childField.getText().length() < 1){
					printLbl_1.setText("This Field must contain at least 1 digit.");
				}else if(childSchField.getText().length() <1){
					printLbl_2.setText("This Field must contain at least 1 digit.");
				}else if(!(rdbtnMale.isSelected()) && !(rdbtnFemale.isSelected())) {
						showPopUp("Please select gender.");					
				}else {
					Customer customer = new Customer();
					customer.setCustomerId(idField.getText());
					customer.setTitle(titleField.getText());
					customer.setSurname(surnameField.getText());
					customer.setLmfbAccountNo(acctNoField.getText());
					customer.setOthernames(othernamesField.getText());
					customer.setAddress(addressArea.getText());
					customer.setDob(date);
					customer.setBvn(bvnField.getText());
					customer.setPhone(phoneField.getText());
					customer.setNoOfWives(Integer.parseInt(wivesField.getText()));
					customer.setNoOfChildren(Integer.parseInt(childField.getText()));
					customer.setNoOfSchoolChildren(Integer.parseInt(childSchField.getText()));
					//customer.setBranchId(branch_list.get(comboBox.getSelectedIndex()).getBranchId());
					customer.setBranch(comboBox.getSelectedItem()+"");
					customer.setUser(user.getFullName());
					
					if(rdbtnMale.isSelected()) {
						customer.setGender("M");
					}else if(rdbtnFemale.isSelected()) {
						customer.setGender("F");
					}
					
					customer1 = customer;
					cLayout.show(switch_panel, "p_two");
				}
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNext.setBounds(496, 529, 89, 23);
		panel_one.add(btnNext);
		
		othernamesLbl = new JLabel("");
		othernamesLbl.setBounds(311, 208, 328, 14);
		panel_one.add(othernamesLbl);
		othernamesLbl.setForeground(Color.RED);
		othernamesLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		surnameLbl = new JLabel("");
		surnameLbl.setBounds(311, 177, 328, 14);
		panel_one.add(surnameLbl);
		surnameLbl.setForeground(Color.RED);
		surnameLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		panel_two = new JPanel();
		switch_panel.add(panel_two, "p_two");
		panel_two.setLayout(null);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(298, 529, 89, 23);
		panel_two.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ArrayList<String> list;
					try {
						if(mainInterface.accountExist(customer1)) {
							showPopUp( "Unable to create customer profile: Account already exist in the branch");
						}else {
							list = mainInterface.createCustomerProfile(customer1, otherBankAccounts);
							if(list.get(1) == null) {
								showPopUp("Success!");
								idField.setText(list.get(0));
								saveButton.setEnabled(false);
								updateButton.setEnabled(false);	
								cLayout.show(switch_panel, "p_one");
							}else {
								
								
							}
							
						}
						
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					
					
				} catch (ParseException e1) {
					e1.printStackTrace();
				} 
				
			}
		});
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		updateButton = new JButton("Update");
		updateButton.setBounds(397, 529, 89, 23);
		panel_two.add(updateButton);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(mainInterface.accountExistEdit(customer1)) {
						showPopUp( "Unable to update customer profile: Duplicate Account No, Bvn or Phone Number");
					}else {
						mainInterface.updateCustomerProfile(customer1, otherBankAccounts);						
						showPopUp("Update Successful!");
						cLayout.show(switch_panel, "p_one");
					}
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateButton.setEnabled(false);
		
		final JButton clearButton = new JButton("Clear");
		clearButton.setBounds(496, 529, 89, 23);
		panel_two.add(clearButton);
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		final JButton cancelButton = new JButton("Close");
		cancelButton.setBounds(595, 529, 89, 23);
		panel_two.add(cancelButton);
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNewLabel_1_1 = new JLabel("Customer Other Bank Account(s)");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setBounds(166, 11, 313, 20);
		panel_two.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Account Name");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(23, 61, 100, 20);
		panel_two.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Account No.");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1.setBounds(23, 92, 100, 20);
		panel_two.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("Account Type");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_2.setBounds(23, 123, 100, 20);
		panel_two.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("Bank");
		lblNewLabel_2_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_3.setBounds(23, 154, 100, 20);
		panel_two.add(lblNewLabel_2_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 227, 674, 207);
		panel_two.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int xy = table.getSelectedRow();
				if(otherBankAccounts.size()<1 && xy<0) {
					showPopUp("No items in the list.");
				}else if(otherBankAccounts.size()>0 && xy<0) {
					showPopUp("Please select the item you wish to remove.");
				}else {
					otherBankAccounts.remove(xy);
					updateOtherAccountsTable(otherBankAccounts);
				}
			}
		});
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemove.setBounds(583, 199, 89, 23);
		panel_two.add(btnRemove);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(acctNameField.getText().length()<1  || otherAcctNoField.getText().length()<1 || otherAcctTypeField.getText().length()<1 || 
						otherBankName.getText().length()<1) {
					showPopUp("Please enter valid text in all fields.");
				}else {
					OtherBankAccount account = new OtherBankAccount();
					account.setOtherAccountName(acctNameField.getText());
					account.setOtherAccountNo(otherAcctNoField.getText());
					account.setOtherAccountType(otherAcctTypeField.getText());
					account.setOtherBank(otherBankName.getText());
					
					otherBankAccounts.add(account);
					updateOtherAccountsTable(otherBankAccounts);
				}	
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdd.setBounds(484, 199, 89, 23);
		panel_two.add(btnAdd);
		
		acctNameField = new JTextField();
		acctNameField.setBounds(133, 61, 200, 20);
		panel_two.add(acctNameField);
		acctNameField.setColumns(10);
		
		otherAcctNoField = new JTextField();
		otherAcctNoField.addKeyListener(new KeyAdapter() {
			String xy = "";
			@Override
			public void keyReleased(KeyEvent e) {
				if(otherAcctNoField.getText().length()<1) {
					otherAcctLbl.setText("");
				}else {
					xy = otherAcctNoField.getText();
					if(xy.length()==10) {
						otherAcctLbl.setText("");
					}
					try {
						@SuppressWarnings("unused")
						long number = Long.parseLong(otherAcctNoField.getText());
						
						}catch(Exception ex) {
							otherAcctNoField.setText("");
							otherAcctLbl.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		otherAcctNoField.setBounds(133, 92, 144, 20);
		panel_two.add(otherAcctNoField);
		otherAcctNoField.setColumns(10);
		
		otherAcctTypeField = new JTextField();
		otherAcctTypeField.setBounds(133, 123, 144, 20);
		panel_two.add(otherAcctTypeField);
		otherAcctTypeField.setColumns(10);
		
		otherBankName = new JTextField();
		otherBankName.setBounds(133, 154, 200, 20);
		panel_two.add(otherBankName);
		otherBankName.setColumns(10);
		
		otherAcctLbl = new JLabel("");
		otherAcctLbl.setForeground(Color.RED);
		otherAcctLbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
		otherAcctLbl.setBounds(331, 95, 293, 14);
		panel_two.add(otherAcctLbl);
		
		JButton btnPrevious = new JButton("<< Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cLayout.show(switch_panel, "p_one");
			}
		});
		btnPrevious.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrevious.setBounds(188, 529, 100, 23);
		panel_two.add(btnPrevious);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				otherBankAccounts.clear();
				updateOtherAccountsTable(otherBankAccounts);
				dispose();
			}
		});
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFrame ff = new JFrame();
				ff.setAlwaysOnTop(true);
 				
				int confirmed = JOptionPane.showConfirmDialog(ff, 
				        "Are you sure you want to clear this page?", "Clear Customer Regitration Form",
				        JOptionPane.YES_NO_OPTION);

				    if (confirmed == JOptionPane.YES_OPTION) {
				    	
				    	idField.setText("");
						acctNoField.setText("");
						surnameField.setText("");
						othernamesField.setText(""); 
						addressArea.setText("");
						bvnField.setText("");
						phoneField.setText("");
						comboBox.setSelectedItem(user.getBranch());
						wivesField.setText("");
						childField.setText("");
						childSchField.setText("");
						printLbl.setText("");
						printLbl_1.setText("");
						printLbl_2.setText("");
						updateButton.setEnabled(false);
						saveButton.setEnabled(true);
						titleField.setText("");
						bg.clearSelection();
						
						dobChooser.setDate(null);
						
						otherBankAccounts.clear();
						updateOtherAccountsTable(otherBankAccounts);
						cLayout.show(switch_panel, "p_one");
				    }
				
			
				
				
			}
		});
		
		if(frame_type.equalsIgnoreCase("New")) {
			searchButton.setEnabled(false);
		}else if(frame_type.equalsIgnoreCase("Edit")) {			
			saveButton.setEnabled(false);
			updateButton.setEnabled(true);
		}
		
		updateOtherAccountsTable(otherBankAccounts);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				otherBankAccounts.clear();
				updateOtherAccountsTable(otherBankAccounts);
			}
		});
		
		
	}
	
	
	public static void fillCustomerForm(Customer customer, ArrayList<OtherBankAccount> bank_list) {
		idField.setText(customer.getCustomerId());
		acctNoField.setText(customer.getLmfbAccountNo());
		acctNoField.setEditable(false);
		surnameField.setText(customer.getSurname());
		othernamesField.setText(customer.getOthernames()); 
		addressArea.setText(customer.getAddress());
		bvnField.setText(customer.getBvn());
		phoneField.setText(customer.getPhone());
		comboBox.setSelectedItem(customer.getBranch());
		wivesField.setText(customer.getNoOfWives()+"");
		childField.setText(customer.getNoOfChildren()+"");
		childSchField.setText(customer.getNoOfSchoolChildren()+"");
		comboBox.setSelectedItem(customer.getBranchId()+1);
		
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");			
		try {
			 date = formatter.parse(customer.getDob());
		} catch (ParseException e) {			
			e.printStackTrace();
		}			
		dobChooser.setDate(date);
		
		if(customer.getGender().equalsIgnoreCase("M")) {
			rdbtnMale.setSelected(true);
		}else if(customer.getGender().equalsIgnoreCase("F")) {
			rdbtnFemale.setSelected(true);
		}
		titleField.setText(customer.getTitle());
		
		updateButton.setEnabled(true);
		saveButton.setEnabled(false);
		
		otherBankAccounts = bank_list;
		updateOtherAccountsTable(otherBankAccounts);
		
	}

		
	public void showPopUp(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}
	
	
	// client methods to update other bank accounts table
	public static void updateOtherAccountsTable(ArrayList<OtherBankAccount> list) {
		Object[][] data = new Object[list.size()][5];
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getOtherAccountName();
			data[i][2] = list.get(i).getOtherAccountNo();
			data[i][3] = list.get(i).getOtherAccountType();
			data[i][4] = list.get(i).getOtherBank();			
			
		}
		
		Object[] columnNames = {"S/No", "Account Name", "Account Number", "Account Type", "Bank"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setPreferredWidth(110);
			
						
	}



}
