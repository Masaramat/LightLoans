package com.llp.reports;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang.time.DateUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import com.llp.api.LLPReportInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.LoanOfferView;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;

public class LoanDisburseRepFrame extends JFrame {

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
					LoanDisburseRepFrame frame = new LoanDisburseRepFrame();
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
	public LoanDisburseRepFrame() throws RemoteException{
		setAlwaysOnTop(true);
		setTitle("Loan Disbursement Report");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(430, 150, 431, 433);
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
		for(int i=0; i< reportInterface.getLoanApplicationDisburser().size(); i++) {
			user_list.add(reportInterface.getLoanApplicationDisburser().get(i));
		}
		
		JLabel lblNewLabel_1 = new JLabel("Loan DisbursementReport");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(0, 11, 464, 36);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("From Date");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(25, 67, 100, 21);
		contentPane.add(lblNewLabel);
		
		final JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setDate(new Date());
		Date date = DateUtils.addYears(new Date(), -1);
		dateChooser.setMinSelectableDate(date);
		dateChooser.setMaxSelectableDate(new Date());
		dateChooser.setBounds(135, 68, 120, 20);
		contentPane.add(dateChooser);
		
		final JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setDateFormatString("dd-MM-yyyy");
		dateChooser_1.setDate(new Date());
		Date date2 = DateUtils.addYears(new Date(), -1);
		dateChooser_1.setMinSelectableDate(date2);
		dateChooser_1.setMaxSelectableDate(new Date());
		dateChooser_1.setBounds(135, 100, 120, 20);
		contentPane.add(dateChooser_1);
		
		JLabel lblToDate = new JLabel("To Date");
		lblToDate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblToDate.setBounds(25, 99, 100, 21);
		contentPane.add(lblToDate);
		
		JLabel lblBranch = new JLabel("Branch");
		lblBranch.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBranch.setBounds(25, 131, 100, 21);
		contentPane.add(lblBranch);
		
		final JComboBox branchBox = new JComboBox();
		branchBox.setBounds(135, 130, 227, 22);
		for(int i=0; i<branch_list.size(); i++) {
			branchBox.addItem(branch_list.get(i));
		}
		contentPane.add(branchBox);
		
		
		final JComboBox userBox = new JComboBox();
		userBox.setBounds(135, 162, 227, 22);
		for(int i=0; i<user_list.size(); i++) {
			userBox.addItem(user_list.get(i));
		}
		contentPane.add(userBox);
		
		JLabel lblMarketingOfficer = new JLabel("User");
		lblMarketingOfficer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMarketingOfficer.setBounds(25, 163, 100, 21);
		contentPane.add(lblMarketingOfficer);
		
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
				
				
				
				
				try {
					ArrayList<LoanOfferView> listt = reportInterface.getLoanDisburseReport(column_list, data_list, sqlfromDate, sqltoDate);
					if(listt.size() == 0) {
						JFrame jf = new JFrame();
						jf.setAlwaysOnTop(true);
						JOptionPane.showMessageDialog(jf, "No results found!");
					}else {
						LoanReportsShowFrame frame = new LoanReportsShowFrame("loan_disbursement", column_list, data_list, sqlfromDate, sqltoDate);
						frame.setVisible(true);
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				
			}
		});
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPreview.setBounds(193, 323, 100, 23);
		contentPane.add(btnPreview);
		
		JButton btnCancel = new JButton("Close");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel.setBounds(303, 323, 100, 23);
		contentPane.add(btnCancel);
	}
	
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
