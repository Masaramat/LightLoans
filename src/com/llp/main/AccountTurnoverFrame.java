package com.llp.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPMainInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.AccountTurnover;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AccountTurnoverFrame extends JFrame {

	private JPanel contentPane;
	private JTextField debitTurnoverField;
	private JTextField creditTurnoverField;
	private JTextField monthEndBalField;
	private JTextField incomeField;
	private JTable accountTurnoverTable;
	private JComboBox monthBox;
	
	private ArrayList<AccountTurnover> turnover_list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountTurnoverFrame frame = new AccountTurnoverFrame("");
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
	public AccountTurnoverFrame(final String application_id) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				LoanOfferFrame.accountTurnoverTable.requestFocus();
			}
		});
		setTitle("Account Turnover");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 540, 404);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
		
		
		JLabel lblNewLabel = new JLabel("Account Turnover For the Last Three Months");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 504, 29);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Month");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 51, 70, 23);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Debit Turnover");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1.setBounds(10, 85, 88, 23);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Credit Turnover");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1_1.setBounds(10, 119, 88, 23);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Month End Bal");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1_2.setBounds(280, 85, 88, 23);
		contentPane.add(lblNewLabel_1_1_2);
		
		JLabel lblNewLabel_1_1_3 = new JLabel("Income");
		lblNewLabel_1_1_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1_3.setBounds(280, 119, 88, 23);
		contentPane.add(lblNewLabel_1_1_3);
		
		debitTurnoverField = new JTextField();
		debitTurnoverField.setBounds(108, 86, 108, 20);
		contentPane.add(debitTurnoverField);
		debitTurnoverField.setColumns(10);
		
		creditTurnoverField = new JTextField();
		creditTurnoverField.setBounds(108, 120, 108, 20);
		contentPane.add(creditTurnoverField);
		creditTurnoverField.setColumns(10);
		
		monthEndBalField = new JTextField();
		monthEndBalField.setBounds(362, 86, 108, 20);
		contentPane.add(monthEndBalField);
		monthEndBalField.setColumns(10);
		
		incomeField = new JTextField();
		incomeField.setBounds(362, 120, 108, 20);
		contentPane.add(incomeField);
		incomeField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 164, 504, 137);
		contentPane.add(scrollPane);
		
		accountTurnoverTable = new JTable();
		
		accountTurnoverTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(accountTurnoverTable);
		
		monthBox = new JComboBox();
		monthBox.setModel(new DefaultComboBoxModel(new String[] {"SELECT", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"}));
		monthBox.setBounds(108, 51, 208, 22);
		contentPane.add(monthBox);
		
		JButton btnRemove_1 = new JButton("Remove");
		btnRemove_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (accountTurnoverTable.getSelectedRow()<0) {
					showPopup("No item selected.");					
				}
				else {
					try {
						mainInterface.deleteAccountTurnover(turnover_list.get(accountTurnoverTable.getSelectedRow()).getSnp());
						showPopup("Success!");
						turnover_list = mainInterface.getAccountTurnovers(application_id);
						updateAccountTurnoverTable(turnover_list);
					} catch (RemoteException e1) {
						
						e1.printStackTrace();
					}
				}
			}
		});
		btnRemove_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemove_1.setBounds(326, 331, 89, 23);
		contentPane.add(btnRemove_1);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (monthBox.getSelectedItem().toString().equalsIgnoreCase("SELECT") || incomeField.getText().length()<1 || monthEndBalField.getText().length()<1
					|| debitTurnoverField.getText().length()<1 || creditTurnoverField.getText().length()<1) {
					
					showPopup("Please complete the form.");					
				}else {
					
					try {
						AccountTurnover turnover = new AccountTurnover();
						turnover.setCreditTurnover(Double.parseDouble(creditTurnoverField.getText()));
						turnover.setDebitTurnover(Double.parseDouble(debitTurnoverField.getText()));
						turnover.setMonth(monthBox.getSelectedItem().toString());
						turnover.setMonthEndBalance(Double.parseDouble(monthEndBalField.getText()));
						turnover.setIncome(Double.parseDouble(incomeField.getText()));
						
						mainInterface.saveAccountTurnover(turnover, application_id);
						showPopup("Success!");
						
						creditTurnoverField.setText("");
						debitTurnoverField.setText("");
						monthEndBalField.setText("");
						incomeField.setText("");						
						
						turnover_list = mainInterface.getAccountTurnovers(application_id);
						updateAccountTurnoverTable(turnover_list);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdd.setBounds(227, 331, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoanOfferFrame.accountTurnoverTable.requestFocus();
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(425, 331, 89, 23);
		contentPane.add(btnNewButton);
		
		try {
			turnover_list = mainInterface.getAccountTurnovers(application_id);
			updateAccountTurnoverTable(turnover_list);
		} catch (RemoteException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void showPopup(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		
		JOptionPane.showMessageDialog(jf, message);
	}
	
	public void updateAccountTurnoverTable(ArrayList<AccountTurnover> list){
		
		Object[][] data = new Object[list.size()][6];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);
			
			data[i][1] = list.get(i).getMonth();
			data[i][2] = list.get(i).getDebitTurnover();
			data[i][3] = list.get(i).getCreditTurnover();
			data[i][4] = list.get(i).getMonthEndBalance();
			data[i][5] = list.get(i).getIncome();
			
		}
		
		Object[] columnNames = {"S/No", "Month", "Debit Turnover", "Credit Turnover", "Month End Bal", "Income"  };
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		accountTurnoverTable.setModel(model);
		accountTurnoverTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		accountTurnoverTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		accountTurnoverTable.getColumnModel().getColumn(1).setPreferredWidth(120);
		accountTurnoverTable.getColumnModel().getColumn(2).setPreferredWidth(120);
		accountTurnoverTable.getColumnModel().getColumn(2).setPreferredWidth(120);
		accountTurnoverTable.getColumnModel().getColumn(2).setPreferredWidth(120);
		accountTurnoverTable.getColumnModel().getColumn(2).setPreferredWidth(120);
		
			
	}
}
