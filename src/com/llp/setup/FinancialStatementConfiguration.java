package com.llp.setup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPSetupInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.FinanceItem;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class FinancialStatementConfiguration extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField itemField;
	private JTable table;
	private final JButton updateButton;
	private final JButton saveButton;
	
	
	ArrayList<FinanceItem> item_list = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinancialStatementConfiguration frame = new FinancialStatementConfiguration("");
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FinancialStatementConfiguration(final String source) throws RemoteException{
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Light Loan Processor- Financial Statement Configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 100, 541, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
		
		item_list = setupInterface.getAllFinanceItems();
		
		JLabel lblNewLabel = new JLabel("Finance type");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 31, 93, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblItem = new JLabel("Item");
		lblItem.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblItem.setBounds(10, 65, 93, 22);
		contentPane.add(lblItem);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Income", "Expenses"}));
		comboBox.setBounds(88, 31, 168, 22);
		contentPane.add(comboBox);
		
		itemField = new JTextField();
		itemField.setBounds(88, 64, 244, 22);
		contentPane.add(itemField);
		itemField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 119, 505, 160);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xy = table.getSelectedRow();
				comboBox.setSelectedItem(item_list.get(xy).getFinanceType());
				itemField.setText(item_list.get(xy).getItem());
				updateButton.setEnabled(true);
				saveButton.setEnabled(false);
			}
		});
		scrollPane.setViewportView(table);
		
		updateFinanceItemTable(item_list);
		
		saveButton = new JButton("Save");
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String finance_type = comboBox.getSelectedItem().toString();
				String item = itemField.getText();
				if(item.length()<1) {
					showPopUp("Please enter a valid item.");
				}else {
					try {
						setupInterface.createFinanceItem(finance_type, item);
						showPopUp("Success!");
						item_list = setupInterface.getAllFinanceItems();
						updateFinanceItemTable(item_list);
						saveButton.setEnabled(false);
						updateButton.setEnabled(true);
						itemField.setText("");
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
				
			}
		});
		saveButton.setBounds(129, 306, 89, 23);
		contentPane.add(saveButton);
		
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String finance_type = comboBox.getSelectedItem().toString();
				String item = itemField.getText();
				if(item.length()<1) {
					showPopUp("Please enter a valid item.");
				}else {
					try {
						int xy = table.getSelectedRow();
						setupInterface.updateFinanceItem(item_list.get(xy).getSno(), finance_type, item);
						showPopUp("Update Successfully!");
						item_list = setupInterface.getAllFinanceItems();
						updateFinanceItemTable(item_list);
						
						itemField.setText("");
						comboBox.setSelectedIndex(0);
						updateButton.setEnabled(false);
						saveButton.setEnabled(true);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
				
				
			}
		});
		updateButton.setEnabled(false);
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateButton.setBounds(228, 306, 89, 23);
		contentPane.add(updateButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox.setSelectedIndex(0);
				itemField.setText("");
				saveButton.setEnabled(true);
				updateButton.setEnabled(false);
				
			}
		});
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		clearButton.setBounds(327, 306, 89, 23);
		contentPane.add(clearButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (source.equalsIgnoreCase("af")) {
					//InterviewFaceSheet.updateItemBox();
				}
				dispose();
			}
		});
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cancelButton.setBounds(426, 306, 89, 23);
		contentPane.add(cancelButton);
		
		
	}
	
		
	// client methods to update finance items table
	public void updateFinanceItemTable(ArrayList<FinanceItem> list) {
		Object[][] data = new Object[list.size()][3];
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getFinanceType();
			data[i][2] = list.get(i).getItem();
			
		}
		
		Object[] columnNames = {"S/No", "Finance Type", "Item"};
		
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
