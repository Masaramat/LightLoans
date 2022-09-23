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

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class BranchesConfiguration extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField branchIdField;
	private JTextField nameField;
	private JTable table;
	
	final JButton updateButton;

	private ArrayList<Branch> branches = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BranchesConfiguration frame = new BranchesConfiguration();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public BranchesConfiguration() throws RemoteException {
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Light Loan Processor : Branch Configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 525, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
		
		branches = setupInterface.getAllBranches();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add/Edit Branch", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 21, 489, 108);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Branch ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 28, 62, 20);
		panel.add(lblNewLabel);
		
		branchIdField = new JTextField(); 
		branchIdField.setEditable(false);
		branchIdField.setBounds(92, 28, 86, 20);
		panel.add(branchIdField);
		branchIdField.setColumns(10);
		
		JLabel lblBranchName = new JLabel("Branch Name");
		lblBranchName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBranchName.setBounds(10, 59, 71, 20);
		panel.add(lblBranchName);
		
		nameField = new JTextField();
		nameField.setBounds(92, 59, 238, 20);
		panel.add(nameField);
		nameField.setColumns(10);
		
		final JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String branch_name = nameField.getText();
				if(branch_name.length()<1) {
					showPopUp("Enter a valid branch name.");
				}else {
					try {
						ArrayList<String>  list = setupInterface.createBranch(branch_name);
						if(list.get(1) == null) {
							branchIdField.setText(list.get(0));
							showPopUp("Success!");
							saveButton.setEnabled(false);
							//UpdateButton.setEnabled(false);
							
							branches = setupInterface.getAllBranches();
							updateBranchTable(branches);
						}else {
							showPopUp("Unable to create new branch: "+list.get(1));
						}
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					
					
					
					
					
					
				}
				
			}
		});
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveButton.setBounds(113, 327, 89, 23);
		contentPane.add(saveButton);
		
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String branch_id = branchIdField.getText();
				String branch_name = nameField.getText();
				
				try {
					if(branch_name.length()<1) {
						showPopUp("Enter a valid branch name.");
					}else {
						String done  = setupInterface.updateBranch(branch_id, branch_name);
						if(done==null) {
							showPopUp("Update Successful!");
							branches = setupInterface.getAllBranches();
							updateBranchTable(branches);						
							branchIdField.setText("");
							nameField.setText("");
							saveButton.setEnabled(true);
							updateButton.setEnabled(false);
								
						}else {
							showPopUp("Unable to update: "+done);
						}
						
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateButton.setBounds(212, 327, 89, 23);
		updateButton.setEnabled(false);
		contentPane.add(updateButton);
		
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cancelButton.setBounds(410, 327, 89, 23);
		contentPane.add(cancelButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 140, 489, 154);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xy = table.getSelectedRow();
				branchIdField.setText(branches.get(xy).getBranchId());
				nameField.setText(branches.get(xy).getBranchName());
				saveButton.setEnabled(false);
				updateButton.setEnabled(true);
				
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				branchIdField.setText("");
				nameField.setText("");
				saveButton.setEnabled(true);
				updateButton.setEnabled(false);
				table.clearSelection();
			}
		});
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		clearButton.setBounds(311, 327, 89, 23);
		contentPane.add(clearButton);
		
		try {
			branches = setupInterface.getAllBranches();
			updateBranchTable(branches);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		
	}
	
	
	
	// client methods to update branches table
	public void updateBranchTable(ArrayList<Branch> list) {
		Object[][] data = new Object[list.size()][3];
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getBranchId();
			data[i][2] = list.get(i).getBranchName();
			
		}
		
		Object[] columnNames = {"S/No", "Branch ID", "Branch Name"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setPreferredWidth(280);	
		
	}
	
	public void showPopUp(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}





}
