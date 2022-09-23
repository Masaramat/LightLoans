package com.llp.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPMainInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.Customer;
import com.llp.entities.OtherBankAccount;
import com.llp.entities.User;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("unused")
public class CustomerSearchFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	
	ArrayList<Customer> customer_list = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerSearchFrame frame = new CustomerSearchFrame("");
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
	public CustomerSearchFrame(final String source) throws RemoteException{
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Search Customer Profile");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(450, 150, 606, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 25, 566, 240);
		contentPane.add(scrollPane);
		
		final LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					int xy = table.getSelectedRow();
					if(source.equalsIgnoreCase("Customer-Reg")) {
						CustomerProfileFrame.fillCustomerForm(customer_list.get(xy), mainInterface.getOtherBankAccounts(customer_list.get(xy).getCustomerId()));
						dispose();
					}else if(source.equalsIgnoreCase("New-Interview")) {
						NewInterviewFrame.fillCustomerDetails(customer_list.get(xy));
						dispose();
					}else if(source.equalsIgnoreCase("face-sheet")) {
						InterviewFaceSheet.updateFaceSheet(customer_list.get(xy));
						dispose();
					}
					else if(source.equalsIgnoreCase("laf")) {
						LoanApplicationFrame.fillCustomerInfo(customer_list.get(xy));
						dispose();
					}else if(source.equalsIgnoreCase("search")) {
						CustomerLoanSummary frame = new CustomerLoanSummary(customer_list.get(xy));
						frame.setVisible(true);
						dispose();
					}else {
						dispose();
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				
				
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Search By");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 295, 66, 20);
		contentPane.add(lblNewLabel);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Customer Name", "Account Number", "BVN", "Phone Number"}));
		comboBox.setBounds(68, 295, 139, 21);
		contentPane.add(comboBox);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String search_criterion = ""; 
					String search_text = textField.getText();
					
					switch(comboBox.getSelectedItem().toString()) {
					case "Customer Name":
						search_criterion = "customer_name";
						break;
					case "Account Number":
						search_criterion = "account_no";
						break;
					case "BVN":
						search_criterion = "bvn";
						break;
					case "Phone Number":
						search_criterion = "phone_no";
						break;
					default:
						search_criterion = "customer_name";
						break;					
					}
					
					if(search_text.length()<1) {
						JFrame jf =new JFrame();
						jf.setAlwaysOnTop(true);
						
						customer_list.clear();
						updateCustomerTable(customer_list);
					}else {
						customer_list = mainInterface.getCustomerList(search_criterion, search_text);
						updateCustomerTable(customer_list);
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		textField.setBounds(217, 295, 203, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton searchButton = new JButton("");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				
				try {
					String search_criterion = ""; 
					String search_text = textField.getText();
					
					switch(comboBox.getSelectedItem().toString()) {
					case "Customer Name":
						search_criterion = "customer_name";
						break;
					case "Account Number":
						search_criterion = "account_no";
						break;
					case "BVN":
						search_criterion = "bvn";
						break;
					case "Phone Number":
						search_criterion = "phone_no";
						break;
					default:
						search_criterion = "customer_name";
						break;					
					}
					
					if(search_text.length()<1) {
						JFrame jf =new JFrame();
						jf.setAlwaysOnTop(true);
						
						JOptionPane.showMessageDialog(jf, "Please enter a valid search text.");
					}else {
						customer_list = mainInterface.getCustomerList(search_criterion, search_text);
						updateCustomerTable(customer_list);
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				
				
				
				
				
			}
		});
		searchButton.setIcon(new ImageIcon(CustomerSearchFrame.class.getResource("/resources/search04.png")));
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchButton.setBounds(430, 295, 47, 29);
		contentPane.add(searchButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setBounds(487, 294, 89, 23);
		contentPane.add(cancelButton);
	}	
	
	
	// client methods to update user  table
	public void updateCustomerTable(ArrayList<Customer> list) {
		Object[][] data = new Object[list.size()][10];
				
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getCustomerId();
			data[i][2] = list.get(i).getLmfbAccountNo();
			data[i][3] = list.get(i).getSurname();
			data[i][4] = list.get(i).getOthernames();
			data[i][5] = list.get(i).getAddress();
			data[i][6] = list.get(i).getDob();
			data[i][7] = list.get(i).getBvn();
			data[i][8] = list.get(i).getPhone();
			data[i][9] = list.get(i).getBranch();
					
		}
		
		Object[] columnNames = {"S/No", "Customer ID", "Account No", "Surname", "Othernames", "Address", "Date of Birth", "BVN", "Phone", "Branch "};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(140);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(200);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(120);
		table.getColumnModel().getColumn(9).setPreferredWidth(100);
					
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
