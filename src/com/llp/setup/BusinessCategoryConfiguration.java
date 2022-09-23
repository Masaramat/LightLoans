package com.llp.setup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPSetupInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.BusinessCategory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.ListSelectionModel;

public class BusinessCategoryConfiguration extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idField;
	private JTextField descField;
	private JTable table;
	private final JButton updateButton;
	private final JButton saveButton;
	
	ArrayList<BusinessCategory> category_list = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BusinessCategoryConfiguration frame = new BusinessCategoryConfiguration();
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
	public BusinessCategoryConfiguration() throws RemoteException{
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Light Loan Processor - Business Category Configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 535, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
		
		category_list = setupInterface.getAllBusinessCategories();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add / Edit Business Category", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 499, 103);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("B/Category ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 29, 84, 20);
		panel.add(lblNewLabel);
		
		JLabel lblCategory = new JLabel("B/Category Description");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCategory.setBounds(10, 60, 117, 20);
		panel.add(lblCategory);
		
		idField = new JTextField();
		idField.setEditable(false);
		idField.setBounds(137, 29, 86, 20);
		panel.add(idField);
		idField.setColumns(10);
		
		descField = new JTextField();
		descField.setBounds(137, 60, 339, 20);
		panel.add(descField);
		descField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 140, 499, 148);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xy = table.getSelectedRow();
				idField.setText(category_list.get(xy).getCategoryId());
				descField.setText(category_list.get(xy).getCategoryDecription());
			
				saveButton.setEnabled(false);
				updateButton.setEnabled(true);
			}
		});
		scrollPane.setViewportView(table);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String category_desc = descField.getText();
				if(category_desc.length()<2) {
					showPopUp("Please enter a valid business category description.");
				}else {					 
					try {
						ArrayList<String> list = setupInterface.createBusinessCategory(category_desc);
						if(list.get(1) == null) {
							showPopUp("Success!.");
							idField.setText(list.get(0));
							category_list = setupInterface.getAllBusinessCategories();
							updateCategoriesTable(category_list);
							saveButton.setEnabled(false);
							updateButton.setEnabled(true);
						
						}else {
							showPopUp("Unable to add new: "+list.get(1));
						}	
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}						
									
				}
				
			}
		});
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveButton.setBounds(123, 313, 89, 23);
		contentPane.add(saveButton);
		
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String category_desc = descField.getText();
				String category_id = idField.getText();
				if(category_desc.length()<2) {
					showPopUp("Please enter a valid business category description.");
				}else {
					try {
					setupInterface.updateBusinessCategory(category_id, category_desc);
					showPopUp("Update Successful!.");
					
					category_list = setupInterface.getAllBusinessCategories();
					updateCategoriesTable(category_list);
					
					table.clearSelection();
					idField.setText("");
					descField.setText("");
					saveButton.setEnabled(true);
					updateButton.setEnabled(false);
					}catch (RemoteException ex) {
						ex.printStackTrace();
					}
				}
				
			
			}
		});
		updateButton.setEnabled(false);
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateButton.setBounds(222, 313, 89, 23);
		contentPane.add(updateButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.clearSelection();
				idField.setText("");
				descField.setText("");
				saveButton.setEnabled(true);
				updateButton.setEnabled(false);
			}
		});
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		clearButton.setBounds(321, 313, 89, 23);
		contentPane.add(clearButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cancelButton.setBounds(420, 313, 89, 23);
		contentPane.add(cancelButton);
		
		category_list = setupInterface.getAllBusinessCategories();
		updateCategoriesTable(category_list);
		
	}
	
	
	
	

	// client methods to update business categories table
	public void updateCategoriesTable(ArrayList<BusinessCategory> list) {
		Object[][] data = new Object[list.size()][3];
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getCategoryId();
			data[i][2] = list.get(i).getCategoryDecription();			
			
		}
		
		Object[] columnNames = {"S/No", "B/Category ID", "B/Category Description"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);
		table.getColumnModel().getColumn(2).setPreferredWidth(300);
		
					
	}

	
	

	public void showPopUp(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}
	











}
