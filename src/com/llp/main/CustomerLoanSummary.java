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
import com.llp.entities.LoanOfferView;

import javax.swing.JLabel;
import java.awt.Font;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerLoanSummary extends JFrame {

	private JPanel contentPane;
	private JTextField accountField;
	private JTextField nameField;
	private JTextField bvnField;
	private JTextField dobField;
	private JTextField phoneField;
	private JTable table;
	private JTextField trxNoField;
	
	private ArrayList<LoanOfferView> loan_list;
	
	NumberFormat nf = NumberFormat.getCurrencyInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerLoanSummary frame = new CustomerLoanSummary(new Customer());
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
	public CustomerLoanSummary(Customer customer) {
		setTitle("LMFB - Customer Loan History");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(320, 120, 729, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DecimalFormatSymbols dfs = ((DecimalFormat) nf).getDecimalFormatSymbols();
		dfs.setCurrencySymbol("");
		((DecimalFormat) nf).setDecimalFormatSymbols(dfs);
		
		final LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
		
		try {
			loan_list = mainInterface.getCustomerLoanHistory(customer.getCustomerId());
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JPanel plain = new JPanel();
		plain.setBounds(10, 45, 693, 147);
		contentPane.add(plain);
		plain.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Customer Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 45, 86, 23);
		plain.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Address");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1.setBounds(10, 79, 116, 23);
		plain.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Phone No.");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_2.setBounds(405, 79, 116, 23);
		plain.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Date of Birth");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_3.setBounds(405, 45, 116, 23);
		plain.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("LMFB Account No.");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_4.setBounds(10, 11, 116, 23);
		plain.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("BVN");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_5.setBounds(405, 11, 116, 23);
		plain.add(lblNewLabel_1_5);
		
		accountField = new JTextField();
		accountField.setEditable(false);
		accountField.setBounds(120, 11, 178, 23);
		plain.add(accountField);
		accountField.setColumns(10);
		accountField.setText(customer.getLmfbAccountNo());
		
		
		nameField = new JTextField();
		nameField.setEditable(false);
		nameField.setBounds(120, 45, 233, 23);
		plain.add(nameField);
		nameField.setColumns(10);
		nameField.setText(customer.getSurname() + " " + customer.getOthernames());
		
		JTextArea addressArea = new JTextArea();
		addressArea.setWrapStyleWord(true);
		addressArea.setLineWrap(true);
		addressArea.setEditable(false);
		addressArea.setBounds(120, 79, 233, 57);
		plain.add(addressArea);
		addressArea.setText(customer.getAddress());
		
		bvnField = new JTextField();
		bvnField.setEditable(false);
		bvnField.setBounds(490, 11, 150, 23);
		plain.add(bvnField);
		bvnField.setColumns(10);
		bvnField.setText(customer.getBvn() + "");
		
		dobField = new JTextField();
		dobField.setEditable(false);
		dobField.setBounds(490, 45, 150, 23);
		plain.add(dobField);
		dobField.setColumns(10);
		dobField.setText(customer.getDob());
		
		phoneField = new JTextField();
		phoneField.setEditable(false);
		phoneField.setBounds(489, 79, 151, 23);
		plain.add(phoneField);
		phoneField.setColumns(10);
		phoneField.setText(customer.getPhone());
		
		JLabel lblNewLabel_2 = new JLabel("No of Trx");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(405, 113, 73, 23);
		plain.add(lblNewLabel_2);
		
		trxNoField = new JTextField();
		trxNoField.setFont(new Font("Tahoma", Font.BOLD, 11));
		trxNoField.setBounds(490, 113, 95, 23);
		plain.add(trxNoField);
		trxNoField.setColumns(10);
		
		trxNoField.setText(loan_list.size() + "");
		
		JLabel lblNewLabel = new JLabel("Customer Loan History");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 693, 23);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 214, 693, 205);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(614, 441, 89, 23);
		contentPane.add(btnNewButton);
		
				
		updateCustomerLoanTable(loan_list);
	}
	
	// client methods to update user  table
	public void updateCustomerLoanTable(ArrayList<LoanOfferView> list) {
		Object[][] data = new Object[list.size()][8];
				
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getApplicationId();
			data[i][2] = list.get(i).getApplicationDate();
			data[i][3] = nf.format(list.get(i).getFacilityRequested());
			data[i][4] = nf.format(list.get(i).getAmountApproved());
			data[i][5] = list.get(i).getTenor();
			data[i][6] = list.get(i).getMaturity();
			data[i][7] = list.get(i).getBranch();			
					
		}
		
		Object[] columnNames = {"S/No", "Application ID", "Application Date", "Facility Applied", "Facility Approved", "Tenor", "Maturity", "Branch "};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setPreferredWidth(140);
		
					
	}
}
