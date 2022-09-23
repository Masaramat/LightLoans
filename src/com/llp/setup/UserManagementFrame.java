package com.llp.setup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPSetupInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.Branch;
import com.llp.entities.User;
import com.llp.entities.UserGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.JRadioButton;

public class UserManagementFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idField;
	private JTextField fullNameField;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JTable table;
	private final JButton updateButton;
	private final JButton saveButton;
	private JRadioButton rdbtnMale;
	private JRadioButton rdbtnFemale;
	private JCheckBox chckbxEnable;
	private JCheckBox chckbxDisable;
	@SuppressWarnings("rawtypes")
	private JComboBox groupComboBox;
	@SuppressWarnings("rawtypes")
	private JComboBox branchComboBox;
	
	private ButtonGroup bg = new ButtonGroup();
	private ButtonGroup bg2 = new ButtonGroup();
	
	ArrayList<User> user_list = null;
	ArrayList<UserGroup> ug_list = null;
	ArrayList<Branch> branches = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserManagementFrame frame = new UserManagementFrame();
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
	public UserManagementFrame() throws RemoteException{
		setAlwaysOnTop(true);
		setTitle("User Management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 70, 724, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
		
		ug_list = setupInterface.getAllUserGroups();
		branches = setupInterface.getAllBranches();
		user_list = setupInterface.getAllUsers();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add / Edit Application User", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 688, 288);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblUesrid = new JLabel("User ID");
		lblUesrid.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUesrid.setBounds(10, 22, 70, 20);
		panel.add(lblUesrid);
		
		idField = new JTextField(); 
		idField.setEditable(false);
		idField.setColumns(10);
		idField.setBounds(90, 22, 86, 20);
		panel.add(idField);
		
		JLabel lblFullName = new JLabel("Full Name");
		lblFullName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFullName.setBounds(10, 53, 70, 20);
		panel.add(lblFullName);
		
		fullNameField = new JTextField();
		fullNameField.setColumns(10);
		fullNameField.setBounds(90, 53, 256, 20);
		panel.add(fullNameField);
		
		JLabel lblUserGroup = new JLabel("User Group");
		lblUserGroup.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUserGroup.setBounds(9, 177, 70, 20);
		panel.add(lblUserGroup);
		
		groupComboBox = new JComboBox();
		groupComboBox.setBounds(88, 177, 187, 20);
		for(int i=0; i<ug_list.size(); i++) {
			groupComboBox.addItem(ug_list.get(i).getGroupName());
		}
		panel.add(groupComboBox);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUsername.setBounds(10, 84, 70, 20);
		panel.add(lblUsername);
		
		usernameField = new JTextField(); 
		usernameField.setBounds(90, 84, 186, 20);
		panel.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPassword.setBounds(10, 115, 70, 20);
		panel.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(90, 115, 137, 20);
		panel.add(passwordField);
		
		JLabel lblBrach = new JLabel("Branch");
		lblBrach.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBrach.setBounds(9, 208, 70, 20);
		panel.add(lblBrach);
		
		branchComboBox = new JComboBox();
		for(int i=0; i<branches.size(); i++) {
			branchComboBox.addItem(branches.get(i).getBranchName());
		}
		branchComboBox.setBounds(89, 208, 187, 20);
		panel.add(branchComboBox);
		
		chckbxEnable = new JCheckBox("Enable");
		chckbxEnable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxEnable.setBounds(89, 241, 70, 20);
		chckbxEnable.setSelected(true);
		panel.add(chckbxEnable);
		
		chckbxDisable = new JCheckBox("Disable");
		chckbxDisable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxDisable.setBounds(161, 241, 70, 20);
		panel.add(chckbxDisable);
		
		bg.add(chckbxEnable);
		bg.add(chckbxDisable);
		
		JLabel lblUserStatus = new JLabel("User Status");
		lblUserStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUserStatus.setBounds(9, 241, 70, 20);
		panel.add(lblUserStatus);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGender.setBounds(10, 146, 70, 20);
		panel.add(lblGender);
		
		rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnMale.setBounds(90, 142, 70, 23);
		panel.add(rdbtnMale);
		bg2.add(rdbtnMale);
		
		rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnFemale.setBounds(166, 142, 75, 23);
		panel.add(rdbtnFemale);
		bg2.add(rdbtnFemale);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 310, 688, 178);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				int xy = table.getSelectedRow();
				idField.setText(user_list.get(xy).getUserId());
				fullNameField.setText(user_list.get(xy).getFullName());
				usernameField.setText(user_list.get(xy).getUsername());
				passwordField.setText(user_list.get(xy).getPassword());
				groupComboBox.setSelectedItem(user_list.get(xy).getUserGroup());
				branchComboBox.setSelectedItem(user_list.get(xy).getBranch());
				
				if(user_list.get(xy).getGender().equalsIgnoreCase("M")) {
					rdbtnMale.setSelected(true);
				}else if(user_list.get(xy).getGender().equalsIgnoreCase("F")) {
					rdbtnFemale.setSelected(true);
				}
				
				if(user_list.get(xy).getUserStatus()==0) {
					chckbxDisable.setSelected(true);
				}else if(user_list.get(xy).getUserStatus()==1) {
					chckbxEnable.setSelected(true);
				}
				
				saveButton.setEnabled(false);
				updateButton.setEnabled(true);
				
				
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String full_name = fullNameField.getText();
				String username = usernameField.getText();
				String password = String.valueOf(passwordField.getPassword());
				
				int user_status = 0;
				if(chckbxEnable.isSelected()) {
					user_status = 1;
				}else if(chckbxDisable.isSelected()) {
					user_status = 0;
				}
				
				if(full_name.length()<1) {
					showPopUp("Please enter a valid full name.");
				}else if(username.length()<6) {
					showPopUp("Please enter a valid username with at least six characters.");
				}else if(password.length()<6) {
					showPopUp("Please enter a valid password with at least six characters.");
				}else if(!(rdbtnMale.isSelected()) && !(rdbtnFemale.isSelected())) {
					showPopUp("Please select gender.");					
				}else {
					User user = new User();
					user.setFullName(full_name);
					user.setUsername(username);
					user.setPassword(password);
					
					int xy = branchComboBox.getSelectedIndex();
					user.setBranch(branches.get(xy).getBranchId());
					
					int xx = groupComboBox.getSelectedIndex();
					user.setUserGroup(ug_list.get(xx).getGrouId());
					
					user.setUserStatus(user_status);
					
					if(rdbtnMale.isSelected()) {
						user.setGender("M");
					}else if(rdbtnFemale.isSelected()) {
						user.setGender("F");
					}				
					
					//call method to create a new user					
					try {
						ArrayList<String> list = setupInterface.createUser(user);
						if(list.get(1) == null) {
							idField.setText(list.get(0));
							showPopUp("Success!");
							
							user_list = setupInterface.getAllUsers();
							updateUserTable(user_list);
							resetForm();	
						}else {
							showPopUp(list.get(1));
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}		
					
				}

				
				
			}
		});
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveButton.setBounds(312, 507, 89, 23);
		contentPane.add(saveButton);
		
		updateButton = new JButton("Update");
		updateButton.setEnabled(false);
		updateButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String full_name = fullNameField.getText();
				String username = usernameField.getText();
				String password = String.valueOf(passwordField.getPassword());
			
				
				int user_status = 0;
				if(chckbxEnable.isSelected()) {
					user_status = 1;
				}else if(chckbxDisable.isSelected()) {
					user_status = 0;
				}
				
				if(full_name.length()<1) {
					showPopUp("Please enter a valid full name.");
				}else if(username.length()<6) {
					showPopUp("Please enter a valid username with at least six characters.");
				}else if(passwordField.getText().length()<6) {
					showPopUp("Please enter a valid password with at least six characters.");
				}else if(!(rdbtnMale.isSelected()) && !(rdbtnFemale.isSelected())) {
					showPopUp("Please select gender.");					
				}else {
					User user = new User();
					user.setUserId(idField.getText());
					user.setFullName(full_name);
					user.setUsername(username);
					user.setPassword(password);
					
					int xy = branchComboBox.getSelectedIndex();
					user.setBranch(branches.get(xy).getBranchId());
					
					int xx = groupComboBox.getSelectedIndex();
					user.setUserGroup(ug_list.get(xx).getGrouId());
					
					user.setUserStatus(user_status);
					
					if(rdbtnMale.isSelected()) {
						user.setGender("M");
					}else if(rdbtnFemale.isSelected()) {
						user.setGender("F");
					}
					
					
					try {
						String text = setupInterface.updateUser(user);
						if(text==null) {
							showPopUp( "Update Successful!");						
							user_list = setupInterface.getAllUsers();
							updateUserTable(user_list);						
							resetForm();	
						}else {
							showPopUp( "Unable to update: "+text);
						}
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}				
				}
			}
		});
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateButton.setBounds(411, 507, 89, 23);
		contentPane.add(updateButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			dispose();
			}
		});
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cancelButton.setBounds(609, 507, 89, 23);
		contentPane.add(cancelButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetForm();
			}
		});
		clearButton.setBounds(510, 507, 89, 23);
		contentPane.add(clearButton);
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		updateUserTable(user_list);
	}
	
	
	
	// client methods to update user  table
	public void updateUserTable(ArrayList<User> list) {
		Object[][] data = new Object[list.size()][7];
		String user_status = "";
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getUserId();
			data[i][2] = list.get(i).getFullName();
			data[i][3] = list.get(i).getUsername();
			data[i][4] = list.get(i).getUserGroup();
			data[i][5] = list.get(i).getBranch();
			
			if(list.get(i).getUserStatus() == 0) {
				user_status = "Disabled";
			}else if(list.get(i).getUserStatus() == 1) {
				user_status = "Enabled";
			}
							
			data[i][6] = user_status;
			
		}
		
		Object[] columnNames = {"S/No", "User ID", "Full Name", "Username", "User Group", "Branch", "User Status"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(225);
		table.getColumnModel().getColumn(3).setPreferredWidth(140);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		table.getColumnModel().getColumn(5).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
					
	}


	public void showPopUp(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}

	public void resetForm() {
		idField.setText("");
		fullNameField.setText(""); 
		groupComboBox.setSelectedIndex(0);
		chckbxEnable.setSelected(true);
		usernameField.setText(""); 
		passwordField.setText("");
		branchComboBox.setSelectedIndex(0);
		bg2.clearSelection();
		updateButton.setEnabled(false);
		saveButton.setEnabled(true);
	}










}
