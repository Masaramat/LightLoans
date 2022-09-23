package com.llp.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPMainInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.LoanApplication;
import com.llp.entities.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class CreditSearchFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;

	private ArrayList<LoanApplication> application_list = new ArrayList<>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreditSearchFrame frame = new CreditSearchFrame(new User());
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
	public CreditSearchFrame(final User user) throws RemoteException {
		setTitle("Credit Search");
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 100, 696, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
		
		JLabel lblNewLabel = new JLabel("CREDIT SEARCH");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 651, 23);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 651, 246);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			dispose();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(572, 376, 89, 23);
		contentPane.add(btnClose);
		
		JButton btnNewButton = new JButton("Update Status");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()<0) {
					
				}else {
					 
				    try {
				    	JFrame ff = new JFrame();
						ff.setAlwaysOnTop(true);
						int confirmed = JOptionPane.showConfirmDialog(ff, 
						        "Update credit search status for this Loan Application?", "Credit Status Update",
						        JOptionPane.YES_NO_OPTION);

						    if (confirmed == JOptionPane.YES_OPTION) {
						    		mainInterface.updateSearchStatus(application_list.get(table.getSelectedRow()).getApplicationId());	
						    		showPopUp("Updated Successfully!!");
						    		
						    		application_list = mainInterface.getPendingCreditSearch(user);
						    		updateLoanApplicationTable(application_list);
						    }
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(442, 376, 120, 23);
		contentPane.add(btnNewButton);
		
		application_list = mainInterface.getPendingCreditSearch(user);
		updateLoanApplicationTable(application_list);
	}
	
	
	public void updateLoanApplicationTable(ArrayList<LoanApplication> list) {
		Object[][] data = new Object[list.size()][11];
				
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getApplicationId();
			data[i][2] = list.get(i).getAccountNumber();
			data[i][3] = list.get(i).getFullName();
			data[i][4] = list.get(i).getAddress();
			data[i][7] = list.get(i).getPhone();
			data[i][10] = list.get(i).getBranch();
			data[i][5] = list.get(i).getDateOfBirth();
			data[i][6] = list.get(i).getBVN();
			data[i][9] = list.get(i).getMarketingOfficer();			
			data[i][8] = list.get(i).getSearchStatus();		
		}
		
		Object[] columnNames = {"S/No", "Application ID", "Account Number", "Customer Name", "Address", "Date of Birth","BVN","Phone No", "Credit Search", "Marketing officer", "Branch"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(160);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(120);
		table.getColumnModel().getColumn(9).setPreferredWidth(120);
		table.getColumnModel().getColumn(10).setPreferredWidth(100);
		
					
	}
	
	public void showPopUp(String message) {
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		
		JOptionPane.showMessageDialog(frame, message);
	}
	
}
