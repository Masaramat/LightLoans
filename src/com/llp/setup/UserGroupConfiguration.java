package com.llp.setup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPSetupInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.UserGroup;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class UserGroupConfiguration extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idField;
	private JTextField nameField;
	private JTable table;
	@SuppressWarnings("rawtypes")
	private final JComboBox comboBox;
	private final JButton updateButton;
	private final JButton saveButton;
	
	private ArrayList<UserGroup> user_group_list = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserGroupConfiguration frame = new UserGroupConfiguration();
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
	public UserGroupConfiguration() throws RemoteException{
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("User Groups Configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 564, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
		
		user_group_list = setupInterface.getAllUserGroups();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add / Edit User Group", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 49, 526, 137);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Group ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 29, 70, 20);
		panel.add(lblNewLabel);
		
		JLabel lblGroupName = new JLabel("Group Name");
		lblGroupName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGroupName.setBounds(10, 60, 70, 20);
		panel.add(lblGroupName);
		
		idField = new JTextField();
		idField.setEditable(false);
		idField.setBounds(90, 29, 86, 20);
		panel.add(idField);
		idField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setBounds(90, 60, 198, 20);
		panel.add(nameField);
		nameField.setColumns(10);
		
		JLabel lblClearanec = new JLabel("Access Level");
		lblClearanec.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblClearanec.setBounds(10, 91, 70, 20);
		panel.add(lblClearanec);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"-Select", "1", "2", "3", "4", "5", "6", "7"}));
		comboBox.setBounds(90, 91, 97, 20);
		panel.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();		
		scrollPane.setBounds(10, 197, 526, 207);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xy = table.getSelectedRow();
				idField.setText(user_group_list.get(xy).getGrouId());
				nameField.setText(user_group_list.get(xy).getGroupName());
				comboBox.setSelectedItem(user_group_list.get(xy).getAcessLevel()+"");
				saveButton.setEnabled(false);
				updateButton.setEnabled(true);
			}
		});
		scrollPane.setViewportView(table);
		
		saveButton = new JButton("Save");
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String group_name = nameField.getText();
				String access_level = comboBox.getSelectedItem().toString();
				
				if(group_name.length()<1) {
					showPopUp("Please enter a valid user group name."); 					
				}else if(access_level.equalsIgnoreCase("-Select")) {
					showPopUp("Please select an access level."); 					
				}
				else {
					
					try {
						ArrayList<String> list = setupInterface.createUserGroup(group_name, Integer.parseInt(access_level));
						
						if(list.get(1)==null) {
							showPopUp("Success!");
							idField.setText(list.get(0));
							user_group_list = setupInterface.getAllUserGroups();
							updateUserGroupTable(user_group_list);
							
							clearForm();
						}else {
							showPopUp("Unable to add new item: "+ list.get(1));
						}	
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
				
			}
		});
		saveButton.setBounds(150, 426, 89, 23);
		contentPane.add(saveButton);
		
		updateButton = new JButton("Update");
		updateButton.setEnabled(false);
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String group_id = idField.getText();
				String group_name = nameField.getText();
				String access_level = comboBox.getSelectedItem().toString();
				
				
				if(group_name.length()<1) {
					showPopUp("Please enter a valid user group name."); 					
				}else if(access_level.equalsIgnoreCase("-Select")) {
					showPopUp("Please select an access level."); 					
				}
				else {
					
					try {
						String text = setupInterface.updateUserGroup(group_id, group_name, Integer.parseInt(access_level));	
						if(text==null) {
							showPopUp("Update Successful!");
							
							user_group_list = setupInterface.getAllUserGroups();
							updateUserGroupTable(user_group_list);
							
							idField.setText("");
							nameField.setText("");
							comboBox.setSelectedIndex(0);
							
							saveButton.setEnabled(true);
							updateButton.setEnabled(false);
							table.clearSelection();
			
						}else {
							showPopUp("Unable to update: "+ text);
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		updateButton.setBounds(249, 426, 89, 23);
		contentPane.add(updateButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
				
			}
		});
		clearButton.setBounds(348, 426, 89, 23);
		contentPane.add(clearButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setBounds(447, 426, 89, 23);
		contentPane.add(cancelButton);
		
		JLabel lblUserGroupsConfiguration = new JLabel("User Groups Configuration");
		lblUserGroupsConfiguration.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserGroupsConfiguration.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUserGroupsConfiguration.setBounds(10, 18, 526, 20);
		contentPane.add(lblUserGroupsConfiguration);
		
		updateUserGroupTable(user_group_list);
		
			
		
	}
	
	
	
	
	
	
	// client methods to update user group table
	public void updateUserGroupTable(ArrayList<UserGroup> list) {
		Object[][] data = new Object[list.size()][4];
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getGrouId();
			data[i][2] = list.get(i).getGroupName();
			data[i][3] = list.get(i).getAcessLevel();
			
		}
		
		Object[] columnNames = {"S/No", "Group ID", "Group Name", "Access Level"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(225);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
					
	}
	

	
	
	public String leftPad(String value, int str_length  ) {
		String ret_str = "";
		if(str_length == 1) {
			ret_str = "0"+value;
 		}else {
			ret_str = value;
 		}	
		
		return ret_str;
	} 
	
	
	
	
	

	public void showPopUp(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}
	
	
	public void clearForm() {
		idField.setText("");
		nameField.setText("");
		comboBox.setSelectedIndex(0);
		
		saveButton.setEnabled(true);
		updateButton.setEnabled(false);
		table.clearSelection();
	}
}
