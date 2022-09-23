package com.llp.reports;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.time.DateUtils;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.SwingConstants;

import com.llp.api.LLPReportInterface;
import com.llp.clientInterface.InterfaceGenerator;

import com.llp.entities.LoanApplication;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class LoanApplicationRepFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	private ArrayList<String> branch_list = new ArrayList<String>();
	private ArrayList<String> user_list = new ArrayList<String>();
	
	private  ArrayList<String> column_list = new ArrayList<String>();
	private  ArrayList<String> data_list = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanApplicationRepFrame frame = new LoanApplicationRepFrame();
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
	public LoanApplicationRepFrame() throws RemoteException{
		setTitle("Loan Application Report");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(430, 150, 500, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPReportInterface reportInterface = InterfaceGenerator.getReportInterface();
		
		branch_list.add("All Branches");		
		for(int i=0; i< reportInterface.getBranchNames().size(); i++) {
			branch_list.add(reportInterface.getBranchNames().get(i));
		}
		
		user_list.add("All Users");
		for(int i=0; i< reportInterface.getLoanApplicationUsers().size(); i++) {
			user_list.add(reportInterface.getLoanApplicationUsers().get(i));
		}
		
		
		JLabel lblNewLabel = new JLabel("From Date");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(35, 80, 100, 21);
		contentPane.add(lblNewLabel);
		
		JLabel lblToDate = new JLabel("To Date");
		lblToDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblToDate.setBounds(35, 112, 100, 21);
		contentPane.add(lblToDate);
		
		JLabel lblBranch = new JLabel("Branch");
		lblBranch.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBranch.setBounds(35, 144, 100, 21);
		contentPane.add(lblBranch);
		
		JLabel lblMarketingOfficer = new JLabel("User");
		lblMarketingOfficer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMarketingOfficer.setBounds(35, 176, 100, 21);
		contentPane.add(lblMarketingOfficer);
		
		JLabel lblApplicationStatus = new JLabel("Application Status");
		lblApplicationStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblApplicationStatus.setBounds(35, 208, 100, 21);
		contentPane.add(lblApplicationStatus);
		
		JLabel lblNewLabel_1 = new JLabel("Loan Application Report");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 24, 464, 36);
		contentPane.add(lblNewLabel_1);
		
		final JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setDate(new Date());
		Date date = DateUtils.addYears(new Date(), -1);
		dateChooser.setMinSelectableDate(date);
		dateChooser.setMaxSelectableDate(new Date());
		dateChooser.setBounds(145, 81, 120, 20);
		contentPane.add(dateChooser);
		
		final JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setDateFormatString("dd-MM-yyyy");
		dateChooser_1.setDate(new Date());
		Date date2 = DateUtils.addYears(new Date(), -1);
		dateChooser_1.setMinSelectableDate(date2);
		dateChooser_1.setMaxSelectableDate(new Date());
		dateChooser_1.setBounds(145, 113, 120, 20);
		contentPane.add(dateChooser_1);
		
		final JComboBox branchBox = new JComboBox();
		for(int i=0; i<branch_list.size(); i++) {
			branchBox.addItem(branch_list.get(i));
		}
		branchBox.setBounds(145, 143, 227, 22);
		contentPane.add(branchBox);
		
		final JComboBox userBox = new JComboBox();
		for(int i=0; i<user_list.size(); i++) {
			userBox.addItem(user_list.get(i));
		}
		userBox.setBounds(145, 175, 227, 22);
		contentPane.add(userBox);
		
		final JComboBox statusBox = new JComboBox();
		statusBox.setModel(new DefaultComboBoxModel(new String[] {"All", "pending", "offered", "audited", "approved", "disbursed", "rejected", "blocked"}));
		statusBox.setBounds(145, 207, 150, 22);
		contentPane.add(statusBox);
		
		JButton btnCancel = new JButton("Close");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel.setBounds(362, 352, 100, 23);
		contentPane.add(btnCancel);
		
		JButton btnPreview = new JButton("Preview");
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				column_list.clear();
				data_list.clear();
				
				Date fromDate = dateChooser.getDate();
				java.sql.Date sqlfromDate = new java.sql.Date(fromDate.getTime());
				
				Date toDate = dateChooser_1.getDate();
				java.sql.Date sqltoDate = new java.sql.Date(toDate.getTime());
				
				 
				if(!(branchBox.getSelectedItem().toString().equalsIgnoreCase("All Branches"))) {
					column_list.add("branch");
					data_list.add(branchBox.getSelectedItem().toString());
				}
				if(!(userBox.getSelectedItem().toString().equalsIgnoreCase("All Users"))) {
					column_list.add("marketing_officer");
					data_list.add(userBox.getSelectedItem().toString());
				}
				if(!(statusBox.getSelectedItem().toString().equalsIgnoreCase("All"))) {
					column_list.add("application_status");
					data_list.add(statusBox.getSelectedItem().toString());
				}
				
				
				try {
					ArrayList<LoanApplication> listt = reportInterface.getLoanApplicationsReport(column_list, data_list, sqlfromDate, sqltoDate);
					if(listt.size() == 0) {
						JFrame jf = new JFrame();
						jf.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jf, "No results found!");
					}else {
						
						LoanReportsShowFrame frame = new LoanReportsShowFrame("loan_application", column_list, data_list, sqlfromDate, sqltoDate);
						frame.setVisible(true);
					}
					
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
				
				
				
				
				
				
				
			}
		});
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPreview.setBounds(252, 352, 100, 23);
		contentPane.add(btnPreview);
	}
	
	
	
	
	
	
	
}
