package com.llp.main;

import java.awt.EventQueue;

import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.llp.api.LLPMainInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.AORecommendationNote;
import com.llp.entities.ApprovalDisbursementParameters;
import com.llp.entities.Customer;
import com.llp.entities.DocumentationParameters;
import com.llp.entities.FinancialStatement;
import com.llp.entities.LoanAnalysisParameters;
import com.llp.entities.LoanApplication;
import com.llp.entities.LoanOfferView;
import com.llp.entities.User;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("unused")
public class LoanApplicationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private  static JTextField appNoField;
	private  static JTextField appDateField;
	private static JTextField accountNoField; 
	private static JTextField nameField;
	private static JTextField dobField;
	private static JTextField phoneField;
	private static JTextField bvnField;
	private static JTextField loanFacilityField;
	private static JTextField loanTypeField;
	private static JTextField purposeField;
	private static JTextField tenorField;
	private static JTextField repaymentField;
	private static JTextField moField;
	private static JTextField branchField;
	private static JTextArea addressArea;
	private static JTextArea amountArea;
	private static JTextArea recommendedAmountArea;
	private static JTextArea commentArea;
	private static JButton btnClose;
	
	private static Customer customer1 = new Customer();
	private JLabel amountLabel;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel recommendLabel;
	
	CardLayout card_layout = new CardLayout();
	CardLayout card_layout_2 = new CardLayout();
	private JPanel panel_2;
	private static JButton btnNext;
	private static JButton btnPrevious; 
	private static JButton btnSave;
	private static JButton btnUpdate;
	private static JButton btnPrint;
	private static JButton btnClear;
	private JTextField securityField;
	private static JTable table;
	private static JButton btnSendForward;
	
	private static ArrayList<String> security_listxx = new ArrayList<>();
	private ArrayList<LoanAnalysisParameters> five_c_list = new ArrayList<>();
	private ArrayList<LoanAnalysisParameters> checklist = new ArrayList<>();
	
	private AORecommendationNote note = new AORecommendationNote();
	
	private JButton btnClear_1;
	private JButton btnSendOn;
	private JPanel mo_panel;
	private JPanel bm_panel;
	private JButton btnClose_2;
	private JButton btnPreview;
	private JButton btnPrevious_1;
	private static JButton btnApprove;
	private static JButton btnReject;
	private static JButton btnPrepareOffer;
	private static JButton btnReject_1;
	private JPanel co_panel;
	
	private JButton btnPrev;
	private JButton btnCancel_3;
	private JButton btnNext_2;
	
	private static JButton btnAddSec;
	private static JButton btnRemoveSec;
	private JTextField recommendedAmountField;
	private JLabel lblNewLabel_2_2;
	private JPanel panel_4;
	private JTextArea textAreaRecommendation;
	private JTable fiveCTable;
	private JTable analysis_table;
	private JComboBox comboBoxTenure;
	private JScrollPane scrollPane_3;
	final LLPMainInterface mainInterface;
	private static JTextField textFieldCustomerId;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanApplicationFrame frame = new LoanApplicationFrame(new User(), "", new LoanApplication(), new ArrayList<String>());
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
	@SuppressWarnings({} )
	public LoanApplicationFrame(final User user, String source, final LoanApplication loan_application, ArrayList<String> security_list) throws RemoteException{
		setTitle("Light Loan Processor - Loan Application Form");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 80, 800, 613);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(card_layout);
		
		mainInterface = InterfaceGenerator.getMainInterface();
		
		security_listxx = security_list;
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 757, 573);
		panel_1.setLayout(null);
		contentPane.add(panel_1, "panel_1");
		
		JLabel lblNewLabel = new JLabel("Loan Application Form");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 673, 24);
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Account Number");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(20, 79, 93, 22);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Customer Name");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1.setBounds(408, 79, 93, 22);
		panel_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Address");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_2.setBounds(20, 119, 93, 22);
		panel_1.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Application Number");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_3.setBounds(20, 46, 104, 22);
		panel_1.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Date of Birth");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_4.setBounds(20, 175, 93, 22);
		panel_1.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Phone No");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_5.setBounds(306, 175, 93, 22);
		panel_1.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("BVN");
		lblNewLabel_1_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_6.setBounds(573, 175, 93, 22);
		panel_1.add(lblNewLabel_1_6);
		
		JLabel lblNewLabel_1_6_7 = new JLabel("Application Date ");
		lblNewLabel_1_6_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_6_7.setBounds(408, 46, 93, 22);
		panel_1.add(lblNewLabel_1_6_7);
		
		appNoField = new JTextField(); 
		appNoField.setEditable(false);
		appNoField.setBounds(130, 47, 112, 20);
		panel_1.add(appNoField);
		appNoField.setColumns(10);
		
		appDateField = new JTextField();
		appDateField.setEditable(false);
		appDateField.setBounds(502, 47, 106, 20);
		panel_1.add(appDateField);
		appDateField.setColumns(10);
		
		
		
		JButton searchButton = new JButton("");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					CustomerSearchFrame frame  = new CustomerSearchFrame("laf");
					frame.setVisible(true);
				} catch (RemoteException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		searchButton.setIcon(new ImageIcon(LoanApplicationFrame.class.getResource("/resources/search04.png")));
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchButton.setBounds(252, 79, 47, 28);
		panel_1.add(searchButton);
		
		accountNoField = new JTextField();
		accountNoField.setEditable(false);
		accountNoField.setBounds(130, 80, 112, 20);
		panel_1.add(accountNoField);
		accountNoField.setColumns(10);
		
		nameField = new JTextField();
		nameField.setEditable(false);
		nameField.setBounds(502, 80, 235, 20);
		panel_1.add(nameField);
		nameField.setColumns(10);
		
		addressArea = new JTextArea();
		addressArea.setEditable(false);
		addressArea.setWrapStyleWord(true);
		addressArea.setLineWrap(true);
		addressArea.setBounds(128, 118, 281, 47);
		panel_1.add(addressArea);
		
		dobField = new JTextField();
		dobField.setEditable(false);
		dobField.setBounds(130, 176, 98, 20);
		panel_1.add(dobField);
		dobField.setColumns(10);
		
		phoneField = new JTextField();
		phoneField.setEditable(false);
		phoneField.setBounds(376, 176, 110, 20);
		panel_1.add(phoneField);
		phoneField.setColumns(10);
		
		bvnField = new JTextField();
		bvnField.setEditable(false);
		bvnField.setBounds(605, 176, 116, 20);
		panel_1.add(bvnField);
		bvnField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Loan Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 219, 727, 292);
		panel_1.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1_6_1 = new JLabel("Loan Facility");
		lblNewLabel_1_6_1.setBounds(10, 34, 93, 22);
		panel.add(lblNewLabel_1_6_1);
		lblNewLabel_1_6_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		loanFacilityField = new JTextField(); 
		loanFacilityField.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(loanFacilityField.getText().length()<1) {
					amountLabel.setText("");
				}else {					
					try {
						long number = Long.parseLong(loanFacilityField.getText());
						
						}catch(Exception ex) {							
							loanFacilityField.setText("");
							amountLabel.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		loanFacilityField.setBounds(134, 34, 136, 20);
		panel.add(loanFacilityField);
		loanFacilityField.setColumns(10);
		
		JLabel lblNewLabel_1_6_2 = new JLabel("Amount in Words");
		lblNewLabel_1_6_2.setBounds(10, 67, 93, 22);
		panel.add(lblNewLabel_1_6_2);
		lblNewLabel_1_6_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		amountArea = new JTextArea();
		amountArea.setBounds(134, 65, 336, 39);
		panel.add(amountArea);
		
		JLabel lblNewLabel_1_6_3 = new JLabel("Business");
		lblNewLabel_1_6_3.setBounds(10, 115, 93, 22);
		panel.add(lblNewLabel_1_6_3);
		lblNewLabel_1_6_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		loanTypeField = new JTextField(); 
		loanTypeField.setBounds(134, 115, 336, 20);
		panel.add(loanTypeField);
		loanTypeField.setColumns(10);
		
		JLabel lblNewLabel_1_6_4 = new JLabel("Purpose");
		lblNewLabel_1_6_4.setBounds(10, 148, 93, 22);
		panel.add(lblNewLabel_1_6_4);
		lblNewLabel_1_6_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		purposeField = new JTextField(); 
		purposeField.setBounds(134, 146, 532, 20);
		panel.add(purposeField);
		purposeField.setColumns(10);
		
		JLabel lblNewLabel_1_6_5 = new JLabel("Tenor");
		lblNewLabel_1_6_5.setBounds(10, 181, 93, 22);
		panel.add(lblNewLabel_1_6_5);
		lblNewLabel_1_6_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		tenorField = new JTextField();
		tenorField.setBounds(134, 177, 126, 20);
		panel.add(tenorField);
		tenorField.setColumns(10);
		
		JLabel lblNewLabel_1_6_6 = new JLabel("Source(s) of Repayment");
		lblNewLabel_1_6_6.setBounds(10, 214, 173, 22);
		panel.add(lblNewLabel_1_6_6);
		lblNewLabel_1_6_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		repaymentField = new JTextField();
		repaymentField.setBounds(134, 215, 336, 20);
		panel.add(repaymentField);
		repaymentField.setColumns(10);
		
		JLabel lblNewLabel_1_6_6_1 = new JLabel("Account Officer");
		lblNewLabel_1_6_6_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_6_6_1.setBounds(10, 248, 108, 22);
		panel.add(lblNewLabel_1_6_6_1);
		
		moField = new JTextField();  
		moField.setText(user.getFullName());
		moField.setEditable(false);
		moField.setBounds(134, 249, 184, 20);
		panel.add(moField);
		moField.setColumns(10);
		
		JLabel lblNewLabel_1_6_6_2 = new JLabel("Branch");
		lblNewLabel_1_6_6_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_6_6_2.setBounds(490, 248, 75, 22);
		panel.add(lblNewLabel_1_6_6_2);
		
		branchField = new JTextField();
		branchField.setText(user.getBranch());
		branchField.setEditable(false);
		branchField.setBounds(551, 249, 153, 20);
		panel.add(branchField);
		branchField.setColumns(10);
		
		amountLabel = new JLabel("");
		amountLabel.setForeground(new Color(255, 0, 0));
		amountLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		amountLabel.setBounds(290, 34, 275, 14);
		panel.add(amountLabel);
		
		btnClose = new JButton("Cancel");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(648, 532, 89, 23);
		panel_1.add(btnClose);
		
		moField.setText(user.getFullName()); 
		branchField.setText(user.getBranch());
		
		lblNewLabel_2 = new JLabel("*");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(280, 34, 18, 14);
		panel.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("*");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(476, 67, 18, 14);
		panel.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("*");
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(476, 119, 18, 14);
		panel.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("*");
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_5.setBounds(270, 177, 18, 14);
		panel.add(lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel("*");
		lblNewLabel_6.setForeground(Color.RED);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_6.setBounds(480, 214, 18, 14);
		panel.add(lblNewLabel_6);
		
		btnNext = new JButton("Next >>");
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String account_no = accountNoField.getText();
				String loanFacilityS = loanFacilityField.getText(); 
				String amount = amountArea.getText();
				String loanType = loanTypeField.getText();
				String purpose = purposeField.getText();
				String tenor = tenorField.getText();
				String repayment = repaymentField.getText();
				String branch = branchField.getText();
				String mo = moField.getText();			
				
				if(account_no.length()<1) {
					JOptionPane.showMessageDialog(null, "No customer selected.");
				}else if(loanFacilityS.length()<1 || amount.length()<1 || loanType.length()<1 || purpose.length()<1 || tenor.length()<1 || repayment.length()<1) {
					JOptionPane.showMessageDialog(null, "All fields marked * cannot be empty.");
				}else {
					card_layout.show(contentPane, "panel_3");
				}
			}
		});
		btnNext.setBounds(448, 532, 89, 23);
		panel_1.add(btnNext);
		
		btnClear_1 = new JButton("Clear");
		btnClear_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetLoanForm();
				
			}
		});
		btnClear_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClear_1.setBounds(549, 532, 89, 23);
		panel_1.add(btnClear_1);
		
		textFieldCustomerId = new JTextField();
		textFieldCustomerId.setEnabled(false);
		textFieldCustomerId.setEditable(false);
		textFieldCustomerId.setBounds(614, 120, 123, 20);
		panel_1.add(textFieldCustomerId);
		textFieldCustomerId.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Customer ID");
		lblNewLabel_10.setBounds(534, 123, 70, 14);
		panel_1.add(lblNewLabel_10);
		
		panel_2 = new JPanel();
		contentPane.add(panel_2, "panel_2");
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_7 = new JLabel("Securities");
		lblNewLabel_7.setBounds(33, 11, 236, 23);
		panel_2.add(lblNewLabel_7);
		
		securityField = new JTextField();
		securityField.setBounds(33, 46, 515, 20);
		panel_2.add(securityField);
		securityField.setColumns(10);
		
		btnAddSec = new JButton("Add");
		btnAddSec.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddSec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(securityField.getText().length()<1) {
					JOptionPane.showMessageDialog(null, "Security field cannot be empty.");
				}else {
					security_listxx.add(securityField.getText());
					securityField.setText("");
					updateSecurityTable(security_listxx);
					
					btnSendOn.setEnabled(false);
				}
			}
		});
		btnAddSec.setBounds(576, 45, 89, 23);
		panel_2.add(btnAddSec);
		
		btnRemoveSec = new JButton("Remove");
		btnRemoveSec.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemoveSec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int xy = table.getSelectedRow();
				if(xy<0) {
					JOptionPane.showMessageDialog(null, "No item selected.");
				}else {
					security_listxx.remove(xy);
					securityField.setText("");
					updateSecurityTable(security_listxx);
					btnSendOn.setEnabled(false);
				}
			}
		});
		btnRemoveSec.setBounds(675, 45, 89, 23);
		panel_2.add(btnRemoveSec);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 79, 731, 161);
		panel_2.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JPanel button_switch = new JPanel();
		button_switch.setLayout(card_layout_2);
		button_switch.setBounds(344, 455, 420, 85);
		panel_2.add(button_switch);
		
		mo_panel = new JPanel();
		button_switch.add(mo_panel, "mo_panel");
		mo_panel.setLayout(null);
		
		if(user.getUserGroup().equalsIgnoreCase("Managing Director") || user.getUserGroup().equalsIgnoreCase("Branch Manager")) {
			btnAddSec.setEnabled(false);
			btnRemoveSec.setEnabled(false);
			securityField.setEditable(false);
		}
		
		btnSendOn = new JButton("Send to BM");
		btnSendOn.setBounds(307, 11, 103, 23);
		mo_panel.add(btnSendOn);
		btnSendOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								    
				try {
					if(recommendedAmountField.getText().length() <1 ) {
						showPopup("Please enter a valid amount.");
						
					}else if(recommendedAmountArea.getText().length() <1 ) {
						showPopup("Please enter amount in words.");
					}else{
						
					JFrame ff = new JFrame();
					ff.setAlwaysOnTop(true);
					
						int confirmed = JOptionPane.showConfirmDialog(ff, 
						        "Are you sure you want to forward this loan application for approval?", "Forward Loan Application",
						        JOptionPane.YES_NO_OPTION);
						
						

						    if (confirmed == JOptionPane.YES_OPTION) {
						    	loan_application.setFacilityRecommended(Double.parseDouble(recommendedAmountField.getText()));
						    	loan_application.setFacilityRecommendedInWords(recommendedAmountArea.getText());
						    	loan_application.setComment(commentArea.getText());
						    	
						    	if(user.getBranch().equalsIgnoreCase("COCIN Headqaurters") || user.getBranch().equalsIgnoreCase("Gindiri") && Double.parseDouble(recommendedAmountField.getText())>100000) {
						    		loan_application.setClearance(2);
								}else if(user.getBranch().equalsIgnoreCase("Head Office") && Double.parseDouble(recommendedAmountField.getText())>300000) {
									loan_application.setClearance(2);
								}
								else {
									loan_application.setClearance(1);
								}
						    
						    	
						    							    	
						    	mainInterface.updateLoanRecommededAmount(loan_application);
						    	mainInterface.updateLoanApplicationStatus(appNoField.getText(), "forwarded", "forward_by", user.getFullName(), "forward_date");	
						    	showPopup("Loan Application forwarded successfully!");
						    	btnSendOn.setEnabled(false);
						    	btnUpdate.setEnabled(false);
						    	
						    }
					}
					
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		btnSendOn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSendOn.setEnabled(false);
		
		btnPrevious = new JButton("<< Previous");
		btnPrevious.setBounds(81, 45, 103, 23);
		mo_panel.add(btnPrevious);
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(contentPane, "panel_3");
			}
		});
		btnPrevious.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnSave = new JButton("Save");
		btnSave.setBounds(81, 11, 103, 23);
		mo_panel.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try {	
				
						
					
					String account_no = accountNoField.getText();
					String loanFacilityS = loanFacilityField.getText(); 
					String amount = amountArea.getText();
					String loanType = loanTypeField.getText();
					String purpose = purposeField.getText();
					String tenor = tenorField.getText();
					String repayment = repaymentField.getText();
					String branch = branchField.getText();
					String mo = moField.getText();
					
					double loanFacility = Double.parseDouble(loanFacilityS);
					//loanFacilityS amount loanType purpose tenor repayment branch mo loanFacility
					LoanApplication loan_app = new LoanApplication();					
					loan_app.setAccountNumber(account_no);
					loan_app.setAddress(amount);
					loan_app.setAmountInWords(amount);
					loan_app.setLoanFacility(loanFacility);
					loan_app.setLoanType(loanType);
					loan_app.setTenor(tenor);
					loan_app.setBranch(branch);
					loan_app.setSourceOfRepayment(repayment);
					loan_app.setPurpose(purpose);				
					loan_app.setMarketingOfficer(mo);
					loan_app.setClearance(0);
					loan_app.setCustomerId(customer1.getCustomerId());
					
					loan_app.setSearchStatus("pending");
					String application_id = "";
					if(user.getUserGroup().equalsIgnoreCase("Head of Marketing") || user.getUserGroup().equalsIgnoreCase("Marketing Supervisor")) {
						if(recommendedAmountField.getText().length() <1 ) {
							showPopup("Please enter a valid recommended amount.");
							
						}else if(recommendedAmountArea.getText().length() <1 ) {
							showPopup("Please enter recommended amount in words.");
						}else {
							application_id = mainInterface.saveLoanApplication(loan_app, security_listxx);
							if (application_id == null) {
								showPopup("Unfurtunately, an error occured please click save again");
							}else {
								saveApplication(application_id, user);
							}
									
						}
							
					}else {
						application_id = mainInterface.saveLoanApplication(loan_app, security_listxx);
						if (application_id == null) {
							showPopup("Unfurtunately, an error occured please click save again");
						}else {
							saveApplication(application_id, user);
						}
					}
					
						
				}catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
				
			
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnUpdate = new JButton("Update");
		btnUpdate.setBounds(194, 11, 103, 23);
		mo_panel.add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					try {
						String account_no = accountNoField.getText();
						String loanFacilityS = loanFacilityField.getText(); 
						String amount = amountArea.getText();
						String loanType = loanTypeField.getText();
						String purpose = purposeField.getText();
						String tenor = tenorField.getText();
						String repayment = repaymentField.getText();
						String branch = branchField.getText();
						String mo = moField.getText();
						String customer_id = textFieldCustomerId.getText();
						
						double loanFacility = Double.parseDouble(loanFacilityS);
						
						
							//loanFacilityS amount loanType purpose tenor re-payment branch m/o loanFacility
							LoanApplication loan_app = new LoanApplication();
							loan_app.setApplicationId(appNoField.getText());
							loan_app.setAccountNumber(account_no);					
							loan_app.setAmountInWords(amount);
							loan_app.setLoanFacility(loanFacility);
							loan_app.setLoanType(loanType);
							loan_app.setTenor(tenor);
							loan_app.setBranch(branch);
							loan_app.setSourceOfRepayment(repayment);
							loan_app.setPurpose(purpose);				
							loan_app.setMarketingOfficer(mo);
							loan_app.setApplicationStatus("pending");
							loan_app.setCustomerId(customer_id);
							
							if(user.getBranch().equalsIgnoreCase("COCIN Headqaurters") || user.getBranch().equalsIgnoreCase("Gindiri") && Double.parseDouble(recommendedAmountField.getText())>100000) {
								loan_app.setClearance(2);
							}else if(user.getBranch().equalsIgnoreCase("Head Office") && Double.parseDouble(recommendedAmountField.getText())>300000) {
								loan_app.setClearance(2);
							}
							else {
								loan_app.setClearance(1);
							}
							if(user.getUserGroup().equalsIgnoreCase("Head of Marketing") || user.getUserGroup().equalsIgnoreCase("Marketing Supervisor")) {
								if(recommendedAmountField.getText().length() <1 ) {
									showPopup("Please enter a valid recommended amount.");
									
								}else if(recommendedAmountArea.getText().length() <1 ) {
									showPopup("Please enter recommended amount in words.");
								}
								
							}
							
							mainInterface.updateLoanApplication(loan_app, security_listxx);
							if(user.getUserGroup().equalsIgnoreCase("Head of Marketing") || user.getUserGroup().equalsIgnoreCase("Marketing Supervisor") ||
									user.getUserGroup().equalsIgnoreCase("System Administrator")) {						
								LoanApplication app = new LoanApplication();
								app.setApplicationId(loan_app.getApplicationId());
								app.setFacilityRecommended(Double.parseDouble(recommendedAmountField.getText()));
								app.setFacilityRecommendedInWords(recommendedAmountArea.getText());
								app.setComment(commentArea.getText());
						    	mainInterface.updateLoanRecommededAmount(app);
								
								btnSendOn.setEnabled(true);
							}
							ArrayList<LoanAnalysisParameters> loan_five_c = new ArrayList<LoanAnalysisParameters>();
							ArrayList<LoanAnalysisParameters> loan_check_list = new ArrayList<LoanAnalysisParameters>();
							for(int i = 0; i<five_c_list.size(); i++) {
								
								LoanAnalysisParameters dp = new LoanAnalysisParameters();
								dp.setApplicationId(loan_app.getApplicationId());
								dp.setParameter(five_c_list.get(i).getParameter());
								dp.setValue(fiveCTable.getValueAt(i, 2).toString());
								dp.setAnalysisType("five c");
								
								loan_five_c.add(dp);					
							}
							
							for(int i = 0; i<checklist.size(); i++) {
								
								LoanAnalysisParameters ab = new LoanAnalysisParameters();
								ab.setApplicationId(loan_app.getApplicationId());
								ab.setParameter(checklist.get(i).getParameter());
								ab.setValue(analysis_table.getValueAt(i, 2).toString());
								ab.setAnalysisType("check");
								
								loan_check_list.add(ab);					
							}
							
							
							AORecommendationNote recommendationNote = new AORecommendationNote();
							recommendationNote.setApplicationId(loan_app.getApplicationId());
							recommendationNote.setRecommendationNote(textAreaRecommendation.getText());
							recommendationNote.setRepaymentType(comboBoxTenure.getSelectedItem().toString());
							
							mainInterface.saveLoanAnalysis(loan_five_c, loan_check_list, recommendationNote);
							
							
							five_c_list = mainInterface.getLoanAnalysis(loan_app.getApplicationId(), "five c");
							updateFiveCTable(five_c_list, 0);
							
							checklist = mainInterface.getLoanAnalysis(loan_app.getApplicationId(), "check");
							updateAnalysisTalbe(checklist, 0);
							JOptionPane.showMessageDialog(null, "Update Successful!");
							
							btnSave.setEnabled(false);
							btnPrint.setEnabled(true);
							btnUpdate.setEnabled(true);	
							btnSendOn.setEnabled(true);
							
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnPrint = new JButton("Preview");
		btnPrint.setBounds(194, 45, 103, 23);
		mo_panel.add(btnPrint);
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//LoanApplicationLetterFrame frame = new LoanApplicationLetterFrame(appNoField.getText());
				//frame.setVisible(true);
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrint.setEnabled(false);
		
		
		JButton btnClose_1 = new JButton("Close");
		btnClose_1.setBounds(307, 45, 103, 23);
		mo_panel.add(btnClose_1);
		btnClose_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		bm_panel = new JPanel();
		button_switch.add(bm_panel, "bm_panel");
		bm_panel.setLayout(null);
		
		btnClose_2 = new JButton("Close");
		btnClose_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose_2.setBounds(307, 45, 103, 23);
		bm_panel.add(btnClose_2);
		
		btnPreview = new JButton("Preview");
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//LoanApplicationLetterFrame frame = new LoanApplicationLetterFrame(appNoField.getText());
				//frame.setVisible(true);
				
			}
		});
		btnPreview.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPreview.setBounds(194, 45, 103, 23); 
		bm_panel.add(btnPreview);
		
		btnPrevious_1 = new JButton("<< Previous");
		btnPrevious_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(contentPane, "panel_1");
			}
		});
		btnPrevious_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrevious_1.setBounds(81, 45, 103, 23);
		bm_panel.add(btnPrevious_1);
		
		btnApprove = new JButton("Approve");
		btnApprove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ff = new JFrame();
				ff.setAlwaysOnTop(true);
				
				if (user.getUserGroup().equalsIgnoreCase("Branch Manager") && loan_application.getClearance()==2) {
					showPopup("You do not have appropriate clearance to approve this loan. \n Please send forward to the Managing Director.");
				} else if (user.getUserGroup().equalsIgnoreCase("Managing Director") && loan_application.getClearance()==1){
					showPopup("You do not have appropriate clearance to approve this loan. \n Please refer to the Branch Manager.");
				}else {
					
					
				    try {
			    		int confirmed = JOptionPane.showConfirmDialog(ff, 
					        "Are you sure you want to approve this loan application for approval?", "Forward Loan Application",
					        JOptionPane.YES_NO_OPTION);

					    if (confirmed == JOptionPane.YES_OPTION) {
					    	mainInterface.updateLoanApplicationStatus(appNoField.getText(), "approved", "approve_by", user.getFullName(), "approve_date");	
					    	showPopup("Loan Application approved successfully!");
					    	deactivateSomeButtons("");					    	
					    }
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}				
			}
		});
		btnApprove.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnApprove.setBounds(81, 11, 103, 23);
		bm_panel.add(btnApprove);
		
		btnReject = new JButton("Reject");
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								    
			    try {
			    	JFrame ff = new JFrame();
					ff.setAlwaysOnTop(true);
					int confirmed = JOptionPane.showConfirmDialog(ff, 
					        "Are you sure you want to reject this loan application?", "Confirm Loan Rejection",
					        JOptionPane.YES_NO_OPTION);

					    if (confirmed == JOptionPane.YES_OPTION) {
					    	RejectLoanFrame frame = new RejectLoanFrame(loan_application.getApplicationId(), "application", user);
							frame.setVisible(true);   
					    	
					    }
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnReject.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnReject.setBounds(194, 11, 103, 23);
		bm_panel.add(btnReject);
		
		btnSendForward = new JButton("Send to MD");
		btnSendForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			    try {
			    	JFrame ff = new JFrame();
					ff.setAlwaysOnTop(true);
					
					int confirmed = JOptionPane.showConfirmDialog(ff, 
					        "Are you sure you want to forward this loan application for approval?", "Forward Loan Application",
					        JOptionPane.YES_NO_OPTION);

					    if (confirmed == JOptionPane.YES_OPTION) {					    	
					    	mainInterface.updateLoanApplicationStatus(appNoField.getText(), "recommended");	
					    	showPopup("Loan Application forwarded successfully!");
					    	btnSendForward.setEnabled(false);
					    	
					    }
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		});
		btnSendForward.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSendForward.setBounds(307, 11, 103, 23);
		bm_panel.add(btnSendForward);
		btnSendForward.setEnabled(false);
		
		co_panel = new JPanel();
		button_switch.add(co_panel, "co_panel");
		co_panel.setLayout(null);
		
		JButton btnPrevious_2 = new JButton("<< Previous");
		btnPrevious_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(contentPane, "panel_1"); 
			}
		});
		btnPrevious_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrevious_2.setBounds(81, 45, 103, 23);
		co_panel.add(btnPrevious_2);
		
		JButton btnPreview_1 = new JButton("Preview");
		btnPreview_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//LoanApplicationLetterFrame frame = new LoanApplicationLetterFrame(appNoField.getText());
				//frame.setVisible(true);
			}
		});
		btnPreview_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPreview_1.setBounds(307, 11, 103, 23);
		co_panel.add(btnPreview_1);
		
		JButton btnClose_3 = new JButton("Close");
		btnClose_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose_3.setBounds(307, 45, 103, 23);
		co_panel.add(btnClose_3);
		
		btnReject_1 = new JButton("Reject Offer");
		btnReject_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				    
				    try {
				    	JFrame ff = new JFrame();
						ff.setAlwaysOnTop(true);
						int confirmed = JOptionPane.showConfirmDialog(ff, 
						        "Are you sure you want to reject this loan application?", "Confirm Loan Rejection",
						        JOptionPane.YES_NO_OPTION);

						    if (confirmed == JOptionPane.YES_OPTION) {
						    	RejectLoanFrame frame = new RejectLoanFrame(loan_application.getApplicationId(), "application", user);
								frame.setVisible(true); 			    	
						    }
					} catch (Exception e2) {
						e2.printStackTrace();
					}
			}
		});
		btnReject_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnReject_1.setBounds(194, 11, 103, 23);
		co_panel.add(btnReject_1);
		
		btnPrepareOffer = new JButton("Prepare Offer");
		btnPrepareOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								    
				    try {
				    	JFrame ff = new JFrame();
						ff.setAlwaysOnTop(true);
						int confirmed = JOptionPane.showConfirmDialog(ff, 
						        "Proceed to prepare an offer for this loan application?", "Confirm Loan Process",
						        JOptionPane.YES_NO_OPTION);

						    if (confirmed == JOptionPane.YES_OPTION) {
						    	mainInterface.updateSecurities(loan_application.getApplicationId(), security_listxx);
								LoanOfferFrame frame = new LoanOfferFrame( user, "new", loan_application,  new LoanOfferView());
								frame.setVisible(true);  
								deactivateSomeButtons("");
								dispose();
						    }
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				
			}
		});
		btnPrepareOffer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrepareOffer.setBounds(81, 11, 103, 23);
		co_panel.add(btnPrepareOffer);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Recommendation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(31, 265, 733, 166);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_8 = new JLabel("Amount:");
		lblNewLabel_8.setBounds(10, 25, 102, 23);
		panel_3.add(lblNewLabel_8);
		
		JLabel lblNewLabel_8_1 = new JLabel("Amount in Words:");
		lblNewLabel_8_1.setBounds(10, 59, 102, 23);
		panel_3.add(lblNewLabel_8_1);
		
		recommendLabel = new JLabel("");
		recommendLabel.setBounds(295, 29, 288, 14);
		panel_3.add(recommendLabel);
		
		JLabel lblNewLabel_8_2 = new JLabel("Comment:");
		lblNewLabel_8_2.setBounds(10, 111, 102, 23);
		panel_3.add(lblNewLabel_8_2);
		
		recommendedAmountField = new JTextField();
		recommendedAmountField.setEditable(false);
		recommendedAmountField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(recommendedAmountField.getText().length()<1) {
					recommendLabel.setText("");
				}else {					
					try {
						long number = Long.parseLong(recommendedAmountField.getText());
						
						}catch(Exception ex) {							
							recommendedAmountField.setText("");
							recommendLabel.setText("Only Numbers Allowed.");
						}
				}	
			}
		});
		recommendedAmountField.setBounds(122, 25, 154, 23);
		panel_3.add(recommendedAmountField);
		recommendedAmountField.setColumns(10);
		
		recommendedAmountArea = new JTextArea();
		recommendedAmountArea.setEditable(false);
		recommendedAmountArea.setWrapStyleWord(true);
		recommendedAmountArea.setLineWrap(true);
		recommendedAmountArea.setBounds(124, 59, 459, 41);
		panel_3.add(recommendedAmountArea);
		
		commentArea = new JTextArea();
		commentArea.setWrapStyleWord(true);
		commentArea.setLineWrap(true);
		commentArea.setEditable(false);
		commentArea.setBounds(124, 110, 459, 40);
		panel_3.add(commentArea);
		
		
		JLabel lblNewLabel_2_1 = new JLabel("*");
		lblNewLabel_2_1.setForeground(Color.RED);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_1.setBounds(280, 29, 18, 14);
		panel_3.add(lblNewLabel_2_1);
		
		lblNewLabel_2_2 = new JLabel("*");
		lblNewLabel_2_2.setForeground(Color.RED);
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2_2.setBounds(593, 63, 18, 14);
		panel_3.add(lblNewLabel_2_2);
		
		panel_4 = new JPanel();
		contentPane.add(panel_4, "panel_3");
		panel_4.setLayout(null);
		
		btnCancel_3 = new JButton("Cancel");
		btnCancel_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.gc();
			}
		});
		btnCancel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel_3.setBounds(675, 530, 89, 23);
		panel_4.add(btnCancel_3);
		
		btnNext_2 = new JButton("Next >>");
		btnNext_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(contentPane, "panel_2");
			}
		});
		btnNext_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNext_2.setBounds(576, 530, 89, 23);
		panel_4.add(btnNext_2);
		
		btnPrev = new JButton("<< Previous");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(contentPane, "panel_1");
			}
		});
		btnPrev.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrev.setBounds(452, 530, 105, 23);
		panel_4.add(btnPrev);
		
		JLabel lblNewLabel_17_1_1 = new JLabel("Recommendation by A/O on the 5 C's of the credit of the client:");
		lblNewLabel_17_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_17_1_1.setBounds(10, 394, 457, 23);
		panel_4.add(lblNewLabel_17_1_1);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 428, 754, 83);
		panel_4.add(scrollPane_3);
		
		textAreaRecommendation = new JTextArea();
		textAreaRecommendation.setLineWrap(true);
		textAreaRecommendation.setWrapStyleWord(true);
		scrollPane_3.setViewportView(textAreaRecommendation);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "5 C's credit check", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(10, 11, 754, 136);
		panel_4.add(panel_5);
		panel_5.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 22, 724, 108);
		panel_5.add(scrollPane_1);
		
		fiveCTable = new JTable();
		scrollPane_1.setViewportView(fiveCTable);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "Other checklist items", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_6.setBounds(10, 158, 754, 191);
		panel_4.add(panel_6);
		panel_6.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 21, 734, 159);
		panel_6.add(scrollPane_2);
		
		analysis_table = new JTable();
		scrollPane_2.setViewportView(analysis_table);
		
		JLabel lblNewLabel_9 = new JLabel("Repayment type:");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_9.setBounds(10, 360, 127, 23);
		panel_4.add(lblNewLabel_9);
		
		comboBoxTenure = new JComboBox();
		comboBoxTenure.setModel(new DefaultComboBoxModel(new String[] {"Daily", "Weekly", "Monthly", "Quarterly"}));
		comboBoxTenure.setBounds(128, 361, 112, 22);
		comboBoxTenure.setSelectedIndex(0);
		panel_4.add(comboBoxTenure);
		
		
		
		
		
		if(source.equalsIgnoreCase("edit")) {
				
			accountNoField.setText(loan_application.getAccountNumber());
			nameField.setText(loan_application.getFullName());
			dobField.setText(loan_application.getDateOfBirth()); 
			phoneField.setText(loan_application.getPhone()); 
			bvnField.setText(loan_application.getBVN());
			addressArea.setText(loan_application.getAddress());
			moField.setText(loan_application.getMarketingOfficer());
			branchField.setText(loan_application.getBranch());
			textFieldCustomerId.setText(loan_application.getCustomerId());
			
			loanFacilityField.setText(new BigDecimal(loan_application.getLoanFacility()).toPlainString());
			amountArea.setText(loan_application.getAmountInWords());
			loanTypeField.setText(loan_application.getLoanType());
			purposeField.setText(loan_application.getPurpose());
			tenorField.setText(loan_application.getTenor());
			repaymentField.setText(loan_application.getSourceOfRepayment());
			
			appNoField.setText(loan_application.getApplicationId());
			appDateField.setText(loan_application.getApplicationDate());
			
			recommendedAmountField.setText(loan_application.getFacilityRecommended() + "");
			recommendedAmountArea.setText(loan_application.getFacilityRecommendedInWords());
			commentArea.setText(loan_application.getComment());	
			
			
			searchButton.setEnabled(false);
			btnSave.setEnabled(false);
			btnUpdate.setEnabled(true);
			
			btnSendOn.setEnabled(true);
			
			updateSecurityTable(security_listxx);
		
		}
		if (source.equalsIgnoreCase("view")) {
			accountNoField.setText(loan_application.getAccountNumber());
			nameField.setText(loan_application.getFullName());
			dobField.setText(loan_application.getDateOfBirth()); 
			phoneField.setText(loan_application.getPhone()); 
			bvnField.setText(loan_application.getBVN());
			addressArea.setText(loan_application.getAddress());
			moField.setText(loan_application.getMarketingOfficer());
			branchField.setText(loan_application.getBranch());
			textFieldCustomerId.setText(loan_application.getCustomerId());
			
			loanFacilityField.setText(new BigDecimal(loan_application.getLoanFacility()).toPlainString());
			loanFacilityField.setEditable(false);
			amountArea.setText(loan_application.getAmountInWords());
			amountArea.setEditable(false);
			loanTypeField.setText(loan_application.getLoanType());
			loanTypeField.setEditable(false);
			purposeField.setText(loan_application.getPurpose());
			purposeField.setEditable(false);
			tenorField.setText(loan_application.getTenor());
			tenorField.setEditable(false);
			repaymentField.setText(loan_application.getSourceOfRepayment());
			repaymentField.setEditable(false);
			
			appNoField.setText(loan_application.getApplicationId());
			appDateField.setText(loan_application.getApplicationDate());
			
			recommendedAmountField.setText(loan_application.getFacilityRecommended() + "");
			recommendedAmountArea.setText(loan_application.getFacilityRecommendedInWords());
			commentArea.setText(loan_application.getComment());		
			
			searchButton.setEnabled(false);
			btnSave.setEnabled(false);
			btnUpdate.setEnabled(false);		
			
			btnPrint.setEnabled(false);
			
			securityField.setEditable(false);
			btnClear_1.setEnabled(false);
			btnSendOn.setEnabled(false);
			btnPreview.setEnabled(false);
			btnApprove.setEnabled(false);
			btnReject.setEnabled(false);
			btnPrepareOffer.setEnabled(false);
			btnReject_1.setEnabled(false);
			btnAddSec.setEnabled(false);
			btnRemoveSec.setEnabled(false);
			
			analysis_table.setEnabled(false);
			fiveCTable.setEnabled(false);
			comboBoxTenure.setEnabled(false);
			textAreaRecommendation.setEditable(false);
			
			
			
			
			updateSecurityTable(security_listxx);
			
		}
		
		if(user.getUserGroup().equalsIgnoreCase("Branch Manager") || user.getUserGroup().equalsIgnoreCase("Managing Director")){
			analysis_table.setEnabled(false);
			fiveCTable.setEnabled(false);
			comboBoxTenure.setEnabled(false);
			textAreaRecommendation.setEditable(false);
			
			if(user.getUserGroup().equalsIgnoreCase("Branch Manager")) {
				if(loan_application.getClearance() == 2 && loan_application.getApplicationStatus().equalsIgnoreCase("In-process")) {
					
					btnSendForward.setEnabled(true);
				}
				
			}
			card_layout_2.show(button_switch, "bm_panel");
		}else if(user.getUserGroup().equalsIgnoreCase("Credit Officer") || user.getUserGroup().equalsIgnoreCase("Head of Credit")){
			analysis_table.setEnabled(false);
			fiveCTable.setEnabled(false);
			comboBoxTenure.setEnabled(false);
			textAreaRecommendation.setEditable(false);
			card_layout_2.show(button_switch, "co_panel");
		}else {
			card_layout_2.show(button_switch, "mo_panel");
		}
		
		if (user.getUserGroup().equalsIgnoreCase("Head of marketing") || user.getUserGroup().equalsIgnoreCase("Marketing supervisor") || 
				user.getUserGroup().equalsIgnoreCase("System Administrator")) {
			recommendedAmountField.setEditable(true);
			recommendedAmountArea.setEditable(true);
			commentArea.setEditable(true);
		}
		
		
		
		
		
			five_c_list = mainInterface.getLoanAnalysisParams(loan_application.getApplicationId(), "five c");
			checklist = mainInterface.getLoanAnalysisParams(loan_application.getApplicationId(), "check");
			
			if (mainInterface.analysisExists(loan_application.getApplicationId())) {
				five_c_list = mainInterface.getLoanAnalysis(loan_application.getApplicationId(), "five c");
				checklist = mainInterface.getLoanAnalysis(loan_application.getApplicationId(), "check");
				
				note = mainInterface.getRecommendationNote(loan_application.getApplicationId());
				comboBoxTenure.setSelectedItem(note.getRepaymentType());
				textAreaRecommendation.setText(note.getRecommendationNote());
				
				updateAnalysisTalbe(checklist, 0);
				updateFiveCTable(five_c_list, 0);
			}else {
				updateAnalysisTalbe(checklist, 1);
				updateFiveCTable(five_c_list, 1);
			}
			
			
			
			
			updateTableColumns();
		
		
		
	}
	
	public void updateTableColumns() {
		TableColumn ADColumn = fiveCTable.getColumnModel().getColumn(2);
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Yes");
		comboBox.addItem("No");
		comboBox.addItem("N/A");		
		ADColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		TableColumn DCColumn = analysis_table.getColumnModel().getColumn(2);
		JComboBox comboBox2 = new JComboBox();
		comboBox2.addItem("N/A");
		comboBox2.addItem("Yes");
		comboBox2.addItem("No");		
		DCColumn.setCellEditor(new DefaultCellEditor(comboBox2));
	}
	
	
	//public client method to update customer information on loan form
	public static void fillCustomerInfo(Customer customer) {
		resetLoanForm();
		customer1 = customer;
		accountNoField.setText(customer1.getLmfbAccountNo());
		nameField.setText(customer1.getSurname()+" "+customer1.getOthernames());
		dobField.setText(customer1.getDob()); 
		phoneField.setText(customer1.getPhone()); 
		bvnField.setText(customer1.getBvn());
		addressArea.setText(customer1.getAddress());
		textFieldCustomerId.setText(customer1.getCustomerId());
		
		
		
	}
	
	public static void resetLoanForm() {
		loanFacilityField.setText(""); 
		loanTypeField.setText(""); 
		purposeField.setText(""); 
		tenorField.setText(""); 
		repaymentField.setText(""); 
		amountArea.setText("");
		
		security_listxx.clear();
		updateSecurityTable(security_listxx);
		btnSave.setEnabled(true);
		btnUpdate.setEnabled(false);
		
	}
	
	public void saveApplication(String application_id, User user) {
		try {		
		
		AORecommendationNote note = new AORecommendationNote();
		note.setApplicationId(application_id);
		note.setRecommendationNote(textAreaRecommendation.getText());
		
		if(user.getUserGroup().equalsIgnoreCase("Head of Marketing") || user.getUserGroup().equalsIgnoreCase("Marketing Supervisor") ||
				user.getUserGroup().equalsIgnoreCase("System Administrator")) {	
			
			
			LoanApplication app = new LoanApplication();
			app.setApplicationId(application_id);
			
			if(user.getBranch().equalsIgnoreCase("COCIN Headqaurters") || user.getBranch().equalsIgnoreCase("Gindiri") && Double.parseDouble(recommendedAmountField.getText())>100000) {
				app.setClearance(2);
			}else if(user.getBranch().equalsIgnoreCase("Head Office") && Double.parseDouble(recommendedAmountField.getText())>300000) {
				app.setClearance(2);
			}
			else {
				app.setClearance(1);
			}
			app.setFacilityRecommended(Double.parseDouble(recommendedAmountField.getText()));
			app.setFacilityRecommendedInWords(recommendedAmountArea.getText());
			app.setComment(commentArea.getText());
	    	mainInterface.updateLoanRecommededAmount(app);
			
			btnSendOn.setEnabled(true);
		}
		ArrayList<LoanAnalysisParameters> loan_five_c = new ArrayList<LoanAnalysisParameters>();
		ArrayList<LoanAnalysisParameters> loan_check_list = new ArrayList<LoanAnalysisParameters>();
		for(int i = 0; i<five_c_list.size(); i++) {
			
			LoanAnalysisParameters dp = new LoanAnalysisParameters();
			dp.setApplicationId(application_id);
			dp.setParameter(five_c_list.get(i).getParameter());
			dp.setValue(fiveCTable.getValueAt(i, 2).toString());
			dp.setAnalysisType("five c");
			
			loan_five_c.add(dp);					
		}
		
		for(int i = 0; i<checklist.size(); i++) {
			
			LoanAnalysisParameters ab = new LoanAnalysisParameters();
			ab.setApplicationId(application_id);
			ab.setParameter(checklist.get(i).getParameter());
			ab.setValue(analysis_table.getValueAt(i, 2).toString());
			ab.setAnalysisType("check");
			
			loan_check_list.add(ab);					
		}
		
		
		AORecommendationNote recommendationNote = new AORecommendationNote();
		recommendationNote.setApplicationId(application_id);
		recommendationNote.setRecommendationNote(textAreaRecommendation.getText());
		recommendationNote.setRepaymentType(comboBoxTenure.getSelectedItem().toString());
		
		mainInterface.saveLoanAnalysis(loan_five_c, loan_check_list, recommendationNote);
		
		
		five_c_list = mainInterface.getLoanAnalysis(application_id, "five c");
		updateFiveCTable(five_c_list, 0);
		
		checklist = mainInterface.getLoanAnalysis(application_id, "check");
		updateAnalysisTalbe(checklist, 0);
		JOptionPane.showMessageDialog(null, "Successful!");
		appNoField.setText(application_id);
		btnSave.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnPrint.setEnabled(true);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	// client methods to update loan security table
	public static void updateSecurityTable(ArrayList<String> list) {
			Object[][] data = new Object[list.size()][2];
			
			for(int i=0; i<list.size(); i++) {
				data[i][0] = (i+1);
				data[i][1] = list.get(i);
				
				
			}
			
			Object[] columnNames = {"S/No", "Item"};
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			table.setModel(model);
		
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(1).setPreferredWidth(630);
			
						
			}
	
	public void showPopup(String message) {
		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		
		JOptionPane.showMessageDialog(jFrame, message);
	}
	
	
	public static void deactivateSomeButtons(String rejection_type) {
		btnReject.setEnabled(false);
		btnApprove.setEnabled(false);
		btnReject_1.setEnabled(false);
		btnPrepareOffer.setEnabled(false);
	}
	
	//method to update Account turnover table	
	public void updateAnalysisTalbe(ArrayList<LoanAnalysisParameters> list, int xy){
		
		Object[][] data = new Object[list.size()][3];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);				
			data[i][1] = list.get(i).getParameter();
			if(xy == 1) {
				data[i][2] = "N/A";
			}else {
				data[i][2] = list.get(i).getValue();
			}
							
		}
		
		Object[] columnNames = {"S/No", "Parameter", "Completed"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		analysis_table.setModel(model);
		analysis_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		analysis_table.getColumnModel().getColumn(0).setPreferredWidth(50);
		analysis_table.getColumnModel().getColumn(1).setPreferredWidth(520);
		analysis_table.getColumnModel().getColumn(2).setPreferredWidth(120);
		
						
	}
	
	//method to update Account turnover table	
		public void updateFiveCTable(ArrayList<LoanAnalysisParameters> list, int xy){
			
			Object[][] data = new Object[list.size()][4];
			for(int i=0; i<list.size(); i++){ 
				data[i][0] = (i+1);				
				data[i][1] = list.get(i).getParameter();
				if(xy == 1) {
					data[i][2] = "Yes";
				}else {
					data[i][2] = list.get(i).getValue();
				}
								
			}
			
			Object[] columnNames = {"S/No", "Parameter", "Completed"};
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			fiveCTable.setModel(model);
			fiveCTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			fiveCTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			fiveCTable.getColumnModel().getColumn(1).setPreferredWidth(520);
			fiveCTable.getColumnModel().getColumn(2).setPreferredWidth(120);
			
							
		}
}
