package com.llp.main;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.llp.api.LLPMainInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.Customer;
import com.llp.entities.LoanApplication;
import com.llp.entities.User;
import com.llp.reports.LoanApplicationRepFrame;
import com.llp.reports.LoanAuditRepFrame;
import com.llp.reports.LoanDisburseRepFrame;
import com.llp.reports.LoanOfferRepFrame;
import com.llp.reports.RejectedLoanApplicationFrame;
import com.llp.setup.ApprovalDistChecklist;
import com.llp.setup.BranchesConfiguration;
import com.llp.setup.BusinessCategoryConfiguration;
import com.llp.setup.DocumentationChecklistSetup;
import com.llp.setup.FinancialStatementConfiguration;
import com.llp.setup.LoanProductConfiguration;
import com.llp.setup.MarketingQuestionsFrame;
import com.llp.setup.UserGroupConfiguration;
import com.llp.setup.UserManagementFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.SystemTray;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import javax.swing.JButton;

public class LLPMainPage extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField userField;
	private JTextField roleField;
	private JTextField branchField;
	private JTextField timeField;
	
	private JButton btnIndividualCustomer;
	private JButton btnCompany;
	private JButton btnLoanApplication;
	private JButton btnLogout;
	private JButton btnHelp;
	private JButton btnLoanOffer;
	private JButton btnLoanApproval;
	private JButton btnLoanDisbursement;
	private JButton btnPrint;
	private JButton btnBackup;
	private JButton btnUserManagement;
	private JMenuItem mntmLoanAppReport;
	
	private static JLabel lblMessageCount;
	
	JFrame jFrame = new JFrame();
	
	static Timer timer = new Timer();
	static Timer timer_1 = new Timer();
	static int action_count_holder = 0;
	static int action_count_holder_messenger = 0;
	
	public static User user_1;
	

	/**
	 * Launch the application.
	 */
	ArrayList<String> offer_status_list = new ArrayList<>();
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LLPMainPage frame = new LLPMainPage(new User());
					
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
	public LLPMainPage(final User user) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LLPMainPage.class.getResource("/resources/logo_1.png")));
		setBackground(SystemColor.activeCaption);
		setTitle("Light Loan Processor        v1.0                                                                      ");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(10, 10, 1380, 730);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		jFrame.setAlwaysOnTop(true);
		
		
		
		user_1 = user;
		
		
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		setJMenuBar(menuBar);
		
		JMenu mnCustomer = new JMenu("Customer");
		menuBar.add(mnCustomer);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Search Customer");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				try {
					CustomerSearchFrame frame = new CustomerSearchFrame("search");
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		mnCustomer.add(mntmNewMenuItem_3);
		
		JMenuItem mntmInterviewSheet = new JMenuItem("Customer Assessment Form");
		mntmInterviewSheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterviewFaceSheet frame;
				try {
					frame = new InterviewFaceSheet(user, new Customer());
					frame.setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				} 
				
			}	
		});		
		
		JMenuItem mntmNewCustomerRegistration = new JMenuItem("New Customer Registration");		
		mntmNewCustomerRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerProfileFrame frame;
				try {
					frame = new CustomerProfileFrame(user, "New");
					frame.setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JMenuItem mntmEditCustomerRegistration = new JMenuItem("Edit Customer Registration");		
		mntmEditCustomerRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerProfileFrame frame;
				try {
					frame = new CustomerProfileFrame(user, "Edit");
					frame.setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JMenuItem mntmNewInterviewSession = new JMenuItem("New Interview Session");		
		mntmNewInterviewSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewInterviewFrame frame;
				try {
					frame = new NewInterviewFrame(user, new Customer(), "");
					frame.setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				} 
				
			}
		});
	
		
		JMenuItem mntmLoanSummary = new JMenuItem("Customer Loan History");
		mntmLoanSummary.setEnabled(false);
		mntmLoanSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
		JMenu mnLoan = new JMenu("Loan   ");
		menuBar.add(mnLoan);
		
		JMenuItem mntmCreditSearch = new JMenuItem("Credit Search Clearance");
		mntmCreditSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreditSearchFrame frame;
				try {
					frame = new CreditSearchFrame(user);
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		
		
		JMenuItem mntmApproveLoanApplication = new JMenuItem("Approve Loan Application");
		mntmApproveLoanApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoanApplicationSearchFrame frame;
				try {
					frame = new LoanApplicationSearchFrame(user, "edit");
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JMenuItem mntmAuditLoanOffer = new JMenuItem("Audit Loan Offer");		
		mntmAuditLoanOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				offer_status_list.clear();
				offer_status_list.add("offered");
			
				
				try {
					LoanOfferSearchFrame frame = new LoanOfferSearchFrame(user, offer_status_list, "audit", "");
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JMenuItem mntmNewLoanApplication = new JMenuItem("New Loan Application");		
		mntmNewLoanApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					LoanApplicationFrame frame = new LoanApplicationFrame(user, "", new LoanApplication(), new ArrayList<String>());
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		
		JMenuItem mntmEditLoanApplication = new JMenuItem("Edit Loan Application");		
		mntmEditLoanApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoanApplicationSearchFrame frame;
				try {
					frame = new LoanApplicationSearchFrame(user, "edit");
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JMenuItem mntmViewLoanApplication = new JMenuItem("Search Loan Applications");
		mntmViewLoanApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoanApplicationSearchFrame frame;
				try {
					frame = new LoanApplicationSearchFrame(user, "view");
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		
		JMenuItem mntmReturnedLoanApplication = new JMenuItem("Returned Loan Applications");
		mntmReturnedLoanApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoanApplicationSearchFrame frame;
				try {
					frame = new LoanApplicationSearchFrame(user, "returned");
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		
		JMenuItem mntmNewLoanOffer = new JMenuItem("New Loan Offer");		
		mntmNewLoanOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoanApplicationSearchFrame frame;
				try {
					frame = new LoanApplicationSearchFrame(user, "edit");
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		JMenuItem mntmLoanOffer = new JMenuItem("Edit Loan Offer");		
		mntmLoanOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					offer_status_list.clear();
					offer_status_list.add("created");
					offer_status_list.add("rejected");
					offer_status_list.add("audited");
					offer_status_list.add("offered");
					LoanOfferSearchFrame frame = new LoanOfferSearchFrame(user, offer_status_list, "edit", "");
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		JMenuItem mntmViewLoanOffer = new JMenuItem("Search Loan Offers");
		mntmViewLoanOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					offer_status_list.clear();
					LoanOfferSearchFrame frame = new LoanOfferSearchFrame(user, offer_status_list, "view", "");
					frame.setVisible(true);
										
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		
		JMenuItem mntmRejectedLoanOffer = new JMenuItem("Rejected Loan Offers");		
		mntmRejectedLoanOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					offer_status_list.clear();
					offer_status_list.add("rejected");
					LoanOfferSearchFrame frame = new LoanOfferSearchFrame(user, offer_status_list, "rejected", "");
					frame.setVisible(true);
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});	
		
		JMenuItem mntmDisburseLoan = new JMenuItem("Disburse Loan");
		mntmDisburseLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					offer_status_list.clear();
					offer_status_list.add("audited");
					offer_status_list.add("disbursed");
					LoanOfferSearchFrame frame = new LoanOfferSearchFrame(user, offer_status_list, "disburse", "");
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		
		JMenu mnPrint = new JMenu("Print");
		menuBar.add(mnPrint);
		
		JMenuItem mntmLoanApplicationLetter = new JMenuItem("Loan Application Letter");
		mntmLoanApplicationLetter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoanApplicationSearchFrame frame;
				try {
					frame = new LoanApplicationSearchFrame(user, "print");
					frame.setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		mnPrint.add(mntmLoanApplicationLetter);
		
		JMenuItem mntmApprovalDisbursementForm = new JMenuItem("Approval Disbursement Forrm");
		mntmApprovalDisbursementForm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					offer_status_list.clear();
					offer_status_list.add("audited");
					LoanOfferSearchFrame frame = new LoanOfferSearchFrame(user, offer_status_list, "print", "approval");
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		
		JMenuItem mntmOfferLetter = new JMenuItem("Offer Letter");
		mntmOfferLetter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					offer_status_list.clear();
					offer_status_list.add("audited");
					LoanOfferSearchFrame frame = new LoanOfferSearchFrame(user, offer_status_list, "print", "offer");
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		
	
		
		JMenu mnReport = new JMenu("Report");
		menuBar.add(mnReport);
		
		mntmLoanAppReport = new JMenuItem("Loan Applications Report");
		mntmLoanAppReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					LoanApplicationRepFrame frame = new LoanApplicationRepFrame();
					frame.setVisible(true);
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});
		
		
		JMenuItem mntmLoanOffersReport = new JMenuItem("Loan Offers Report");
		mntmLoanOffersReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					LoanOfferRepFrame frame = new LoanOfferRepFrame();
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		
		JMenuItem mntmLoanAuditReport = new JMenuItem("Loans Audit Report");
		mntmLoanAuditReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					LoanAuditRepFrame frame = new LoanAuditRepFrame();
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Loans Disbursement Report");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					LoanDisburseRepFrame frame = new LoanDisburseRepFrame();
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});	
		
		mnReport.add(mntmLoanAppReport);
		mnReport.add(mntmLoanOffersReport);
		mnReport.add(mntmLoanAuditReport);
		mnReport.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Rejected Loans Report");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					RejectedLoanApplicationFrame frame = new RejectedLoanApplicationFrame();
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		mnReport.add(mntmNewMenuItem_4);
	
		
		JMenu mnSetup = new JMenu("Setup");
		menuBar.add(mnSetup);
		
		JMenuItem mntmRACParameters = new JMenuItem("Risk Acceptance Criteria");
		mntmRACParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ApprovalDistChecklist frame = new ApprovalDistChecklist();
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		mnSetup.add(mntmRACParameters);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Documentation Checklist");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DocumentationChecklistSetup frame = new DocumentationChecklistSetup();
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}			}
		});
		mnSetup.add(mntmNewMenuItem_2);
		
		JMenuItem mntmBusinessCategory = new JMenuItem("Business Categories");
		mntmBusinessCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BusinessCategoryConfiguration frame;
				try {
					frame = new BusinessCategoryConfiguration();
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			} 	
		});
		
		
		JMenuItem mntmFinancialStatement = new JMenuItem("Financial Statements");
		mntmFinancialStatement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FinancialStatementConfiguration frame;
				try {
					frame = new FinancialStatementConfiguration("");
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		
		JMenuItem mntmLoanProduct = new JMenuItem("Loan Products");
		mntmLoanProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					LoanProductConfiguration frame = new LoanProductConfiguration();
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		JMenuItem mntmMarketingQuestion = new JMenuItem("Marketing Questions");
		mntmMarketingQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					MarketingQuestionsFrame frame = new MarketingQuestionsFrame("");
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		JMenu mnAdmin = new JMenu("Admin");
		menuBar.add(mnAdmin);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Backup Database");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
					try {
						BackupFrame frame = new BackupFrame();
						frame.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		
	
		
		
		JMenuItem mntmUserManagement = new JMenuItem("User Management");
		mntmUserManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					UserManagementFrame frame = new UserManagementFrame();
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JMenuItem mntmBranchesSetup = new JMenuItem("Branches Configuration");		
		mntmBranchesSetup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					BranchesConfiguration frame = new BranchesConfiguration();
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} 
		});
		
		JMenuItem mntmUserGroup = new JMenuItem("User Groups Configuration");		
		mntmUserGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					UserGroupConfiguration frame = new UserGroupConfiguration();
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		JMenu mnUtility = new JMenu("Utility");
		menuBar.add(mnUtility); 
		
		JMenuItem mntmChangePassword = new JMenuItem("Change Password");
		mntmChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 ChangePasswordFrame frame = new ChangePasswordFrame(user);
				 frame.setVisible(true);
			}
		});
		mnUtility.add(mntmChangePassword);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				 int confirmed = JOptionPane.showConfirmDialog(jFrame, 
					        "Are you sure you want to logout of the program?", "Confirm Logout",
					        JOptionPane.YES_NO_OPTION);

					    if (confirmed == JOptionPane.YES_OPTION) {
					      System.exit(0);
					    }
			}
		});
		mnUtility.add(mntmLogout);
		
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
						
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy  hh:mm:ss a");
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBounds(0, 0, 1364, 49);
		contentPane.add(panel);	
		panel.setLayout(null);
		
		btnIndividualCustomer = new JButton(""); 
		btnIndividualCustomer.setEnabled(false);
		btnIndividualCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					CustomerProfileFrame frame = new CustomerProfileFrame(user, "New");
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnIndividualCustomer.setToolTipText("New Customer");
		btnIndividualCustomer.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/customer_icon.png")));
		btnIndividualCustomer.setBackground(SystemColor.menu);
		btnIndividualCustomer.setBorder(null);
		btnIndividualCustomer.setBounds(23, 11, 47, 39);
		panel.add(btnIndividualCustomer);
		
		 btnCompany = new JButton("");
		 btnCompany.setEnabled(false);
		btnCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCompany.setToolTipText("New Company Account");
		btnCompany.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/company-account.png")));
		btnCompany.setBorder(null);
		btnCompany.setBackground(SystemColor.menu);
		btnCompany.setBounds(183, 11, 60, 39);
		panel.add(btnCompany);
		
		 btnLoanOffer = new JButton("");
		 btnLoanOffer.setEnabled(false);
		btnLoanOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoanApplicationSearchFrame frame;
				try {
					frame = new LoanApplicationSearchFrame(user, "edit");
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnLoanOffer.setToolTipText("New Loan Offer");
		btnLoanOffer.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/loan-offer.png")));
		btnLoanOffer.setBorder(null);
		btnLoanOffer.setBackground(SystemColor.menu);
		btnLoanOffer.setBounds(343, 11, 60, 39);
		panel.add(btnLoanOffer);
		
		 btnLoanApproval = new JButton(""); 
		 btnLoanApproval.setEnabled(false);
		btnLoanApproval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					offer_status_list.clear();
					offer_status_list.add("offered");
					LoanOfferSearchFrame frame = new LoanOfferSearchFrame(user, offer_status_list, "audit", "");
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnLoanApproval.setToolTipText("Audit Loan Offer"); 
		btnLoanApproval.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/approve-loans.png")));
		btnLoanApproval.setBorder(null);
		btnLoanApproval.setBackground(SystemColor.menu);
		btnLoanApproval.setBounds(423, 11, 60, 39);
		panel.add(btnLoanApproval);
		
		 btnLoanDisbursement = new JButton(""); 
		 btnLoanDisbursement.setEnabled(false);
		btnLoanDisbursement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					offer_status_list.clear();
					offer_status_list.add("audited");
					LoanOfferSearchFrame frame = new LoanOfferSearchFrame(user, offer_status_list, "disburse", "");
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnLoanDisbursement.setToolTipText("Disburse Loan");
		btnLoanDisbursement.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/disbursement.png")));
		btnLoanDisbursement.setBorder(null);
		btnLoanDisbursement.setBackground(SystemColor.menu);
		btnLoanDisbursement.setBounds(503, 11, 60, 39);
		panel.add(btnLoanDisbursement);
		
		 btnPrint = new JButton(""); 
		 btnPrint.setEnabled(false);
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPrint.setToolTipText("Print Report");
		btnPrint.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/print.png")));
		btnPrint.setBorder(null);
		btnPrint.setBackground(SystemColor.menu);
		btnPrint.setBounds(583, 11, 60, 39);
		panel.add(btnPrint);
		
		 btnBackup = new JButton(""); 
		 btnBackup.setEnabled(false);
		btnBackup.setToolTipText("Backup");
		btnBackup.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/backup.png")));
		btnBackup.setBorder(null);
		btnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					BackupFrame frame = new BackupFrame();
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBackup.setBackground(SystemColor.menu);
		btnBackup.setBounds(663, 11, 60, 39);
		panel.add(btnBackup);
		
		 btnUserManagement = new JButton("");
		 btnUserManagement.setEnabled(false);
		btnUserManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					UserManagementFrame frame = new UserManagementFrame();
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUserManagement.setToolTipText("User Management");
		btnUserManagement.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/usermgt.png")));
		btnUserManagement.setBorder(null);
		btnUserManagement.setBackground(SystemColor.menu);
		btnUserManagement.setBounds(743, 11, 60, 39);
		panel.add(btnUserManagement);
		
		 btnLogout = new JButton(""); 
		 btnLogout.setEnabled(true);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(jFrame, 
				        "Are you sure you want to logout of the program?", "Confirm Logout",
				        JOptionPane.YES_NO_OPTION);

				    if (confirmed == JOptionPane.YES_OPTION) {
				      System.exit(0);
				    }
			}
		});
		btnLogout.setToolTipText("Logout");
		btnLogout.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/logout.png")));
		btnLogout.setBorder(null);
		btnLogout.setBackground(SystemColor.menu);
		btnLogout.setBounds(823, 11, 60, 39);
		panel.add(btnLogout);
		
		 btnHelp = new JButton("");
		 btnHelp.setEnabled(true);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setToolTipText("Help");
		btnHelp.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/help.png")));
		btnHelp.setBorder(null);
		btnHelp.setBackground(SystemColor.menu);
		btnHelp.setBounds(902, 11, 60, 39);
		panel.add(btnHelp);
		
		 btnLoanApplication = new JButton("");
		 btnLoanApplication.setEnabled(false);
		btnLoanApplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					LoanApplicationFrame frame = new LoanApplicationFrame(user, "", new LoanApplication(), new ArrayList<String>());
					frame.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLoanApplication.setToolTipText("New Loan Application");
		btnLoanApplication.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/loan-app.png")));
		btnLoanApplication.setBorder(null);
		btnLoanApplication.setBackground(SystemColor.menu);
		btnLoanApplication.setBounds(263, 11, 60, 39);
		panel.add(btnLoanApplication);
		
		JButton btnCompany_1 = new JButton("");
		btnCompany_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					CustomerSearchFrame frame = new CustomerSearchFrame("search");
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnCompany_1.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/browse_patient_1.png")));
		btnCompany_1.setToolTipText("New Company Account");
		btnCompany_1.setBorder(null);
		btnCompany_1.setBackground(SystemColor.menu);
		btnCompany_1.setBounds(98, 11, 60, 39);
		panel.add(btnCompany_1);
		
		JButton btnMessenger = new JButton("");
		btnMessenger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LightMessengerFrame frame = new LightMessengerFrame(user);
				frame.setVisible(true);
			}
		});
		btnMessenger.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/Light_1.png")));
		btnMessenger.setBounds(1155, 11, 79, 39);
		btnMessenger.setBorder(null);
		btnMessenger.setBackground(SystemColor.menu);
		panel.add(btnMessenger);
		
		lblMessageCount = new JLabel("");
		lblMessageCount.setForeground(Color.RED);
		lblMessageCount.setFont(new Font("Serif", Font.BOLD, 16));
		lblMessageCount.setBounds(1231, 11, 26, 27);	
		panel.add(lblMessageCount);
		
		
		
		
		timeField = new JTextField();
		timeField.setForeground(new Color(60, 179, 113));
		timeField.setFont(new Font("Tahoma", Font.BOLD, 13));
		timeField.setText(sdf.format(new Date()));
		timeField.setEditable(false);
		timeField.setColumns(10);
		timeField.setBounds(137, 559, 194, 20);
		contentPane.add(timeField);
		
		JLabel lblNewLabel_1_3 = new JLabel("Login Time:");
		lblNewLabel_1_3.setForeground(new Color(60, 179, 113));
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_3.setBounds(43, 558, 84, 22);
		contentPane.add(lblNewLabel_1_3);
		
		roleField = new JTextField();
		roleField.setForeground(new Color(60, 179, 113));
		roleField.setFont(new Font("Tahoma", Font.BOLD, 13));
		roleField.setText(user.getUserGroup());
		roleField.setEditable(false);
		roleField.setColumns(10);
		roleField.setBounds(137, 493, 194, 20);
		contentPane.add(roleField);
		
		JLabel lblNewLabel_1_1 = new JLabel("Role:");
		lblNewLabel_1_1.setForeground(new Color(60, 179, 113));
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(43, 492, 84, 22);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Branch:");
		lblNewLabel_1_2.setForeground(new Color(60, 179, 113));
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_2.setBounds(43, 525, 84, 22);
		contentPane.add(lblNewLabel_1_2);
		
		branchField = new JTextField();
		branchField.setForeground(new Color(60, 179, 113));
		branchField.setFont(new Font("Tahoma", Font.BOLD, 13));
		branchField.setText(user.getBranch());
		branchField.setEditable(false);
		branchField.setColumns(10);
		branchField.setBounds(137, 526, 194, 20);
		contentPane.add(branchField);
		
		userField = new JTextField();
		userField.setForeground(new Color(60, 179, 113));
		userField.setFont(new Font("Tahoma", Font.BOLD, 13));
		userField.setText(user.getFullName() +"   as (" +user.getUsername()+")");
		userField.setEditable(false);
		userField.setBounds(137, 460, 298, 20);
		contentPane.add(userField);
		userField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("User: ");
		lblNewLabel_1.setForeground(new Color(60, 179, 113));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(43, 459, 84, 22);
		contentPane.add(lblNewLabel_1);
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setText("Developed by: Light MfB - IT Department \u00A92021");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(986, 630, 368, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(LLPMainPage.class.getResource("/resources/new_Image.png")));
		lblNewLabel_2.setOpaque(false);
		lblNewLabel_2.setBounds(0, 60, 1364, 609);
		contentPane.add(lblNewLabel_2);
		
	
		switch (user.getUserGroup()) {
		case "Marketing Officer":
			mnCustomer.add(mntmNewCustomerRegistration); 
			mnCustomer.add(mntmNewInterviewSession);
			mnCustomer.add(mntmInterviewSheet);
			mnCustomer.add(mntmLoanSummary);
			
			
			mnLoan.add(mntmNewLoanApplication);	
			mnLoan.add(mntmEditLoanApplication);
			mnLoan.add(mntmReturnedLoanApplication);
			mnLoan.add(mntmViewLoanApplication);						
			mnLoan.add(mntmViewLoanOffer);
			
			mnPrint.add(mntmLoanApplicationLetter);
			
			mnSetup.add(mntmBusinessCategory);
			mnSetup.add(mntmMarketingQuestion);
			mnSetup.add(mntmFinancialStatement);
			
			
			btnIndividualCustomer.setEnabled(true);
			btnLoanApplication.setEnabled(true);			
			btnPrint.setEnabled(true);
			
			break;
			
		case "Head of Marketing":
			
			mnCustomer.add(mntmNewCustomerRegistration);
			mnCustomer.add(mntmEditCustomerRegistration);			
			mnCustomer.add(mntmNewInterviewSession);
			mnCustomer.add(mntmInterviewSheet);
			mnCustomer.add(mntmLoanSummary);
			
			mnLoan.add(mntmNewLoanApplication);
			mnLoan.add(mntmEditLoanApplication);			
			mnLoan.add(mntmReturnedLoanApplication);
			mnLoan.add(mntmViewLoanApplication);
			mnLoan.add(mntmViewLoanOffer);
			
			mnPrint.add(mntmLoanApplicationLetter);
			
			mnSetup.add(mntmBusinessCategory);			
			mnSetup.add(mntmMarketingQuestion);
			mnSetup.add(mntmFinancialStatement);
			
			btnIndividualCustomer.setEnabled(true);
			btnLoanApplication.setEnabled(true);			
			btnPrint.setEnabled(true);	
						
			break;
		case "Marketing Supervisor":
			
			mnCustomer.add(mntmNewCustomerRegistration);
			mnCustomer.add(mntmEditCustomerRegistration);			
			mnCustomer.add(mntmNewInterviewSession);
			mnCustomer.add(mntmInterviewSheet);
			mnCustomer.add(mntmLoanSummary);
			
			mnLoan.add(mntmNewLoanApplication);
			mnLoan.add(mntmEditLoanApplication);			
			mnLoan.add(mntmReturnedLoanApplication);
			mnLoan.add(mntmViewLoanApplication);
			mnLoan.add(mntmViewLoanOffer);
			
			mnPrint.add(mntmLoanApplicationLetter);
			
			mnSetup.add(mntmBusinessCategory);			
			mnSetup.add(mntmMarketingQuestion);
			mnSetup.add(mntmFinancialStatement);
			
			btnIndividualCustomer.setEnabled(true);
			btnLoanApplication.setEnabled(true);			
			btnPrint.setEnabled(true);	
						
			break;
			
			
		case "Credit Officer":
			mnCustomer.add(mntmInterviewSheet);		
					
			mnLoan.add(mntmNewLoanOffer);
			mnLoan.add(mntmLoanOffer);
			mnLoan.add(mntmRejectedLoanOffer);
			mnLoan.add(mntmViewLoanApplication);	
			mnLoan.add(mntmViewLoanOffer);
			mnLoan.add(mntmCreditSearch);		
			
			mnPrint.add(mntmLoanApplicationLetter);
			mnPrint.add(mntmOfferLetter);
			mnPrint.add(mntmApprovalDisbursementForm);
			
			mnSetup.add(mntmLoanProduct);
			
			
			
					
			btnPrint.setEnabled(true);	
			btnLoanOffer.setEnabled(true);			
			
			break;
			
			
		case "Head of Credit":
			mnCustomer.add(mntmInterviewSheet);
			
				
			mnLoan.add(mntmNewLoanOffer);
			mnLoan.add(mntmLoanOffer);			
			mnLoan.add(mntmRejectedLoanOffer);
			mnLoan.add(mntmViewLoanOffer);
			mnLoan.add(mntmViewLoanApplication);
			mnLoan.add(mntmCreditSearch);
			
			mnPrint.add(mntmLoanApplicationLetter);
			mnPrint.add(mntmOfferLetter);
			mnPrint.add(mntmApprovalDisbursementForm);
			
			mnSetup.add(mntmLoanProduct);
			
			btnPrint.setEnabled(true);	
			btnLoanOffer.setEnabled(true);	
			
			break;
			
			
		case "Branch Manager":
			mnCustomer.add(mntmNewCustomerRegistration);
			mnCustomer.add(mntmEditCustomerRegistration);			
			mnCustomer.add(mntmNewInterviewSession);
			mnCustomer.add(mntmInterviewSheet);
			mnCustomer.add(mntmLoanSummary);
			
			mnLoan.add(mntmApproveLoanApplication);
			mnLoan.add(mntmCreditSearch);
			mnLoan.add(mntmViewLoanApplication);
			mnLoan.add(mntmViewLoanOffer);	
			
			
			mnPrint.add(mntmLoanApplicationLetter);
			mnPrint.add(mntmOfferLetter); 
			mnPrint.add(mntmApprovalDisbursementForm);
			
			mnSetup.add(mntmLoanProduct);
			mnSetup.add(mntmBusinessCategory);
			mnSetup.add(mntmFinancialStatement);
			mnSetup.add(mntmMarketingQuestion);
			
			btnIndividualCustomer.setEnabled(true);				
			btnPrint.setEnabled(true);	
			
			
			break;
		case "Managing Director":
			mnCustomer.add(mntmNewCustomerRegistration);
			mnCustomer.add(mntmEditCustomerRegistration);			
			mnCustomer.add(mntmNewInterviewSession);
			mnCustomer.add(mntmInterviewSheet);
			mnCustomer.add(mntmLoanSummary);
			
			mnLoan.add(mntmApproveLoanApplication);
			mnLoan.add(mntmCreditSearch);
			mnLoan.add(mntmViewLoanApplication);
			mnLoan.add(mntmViewLoanOffer);
			
			mnPrint.add(mntmLoanApplicationLetter);
			mnPrint.add(mntmOfferLetter);
			mnPrint.add(mntmApprovalDisbursementForm);
			
			mnSetup.add(mntmLoanProduct);
			mnSetup.add(mntmBusinessCategory);
			mnSetup.add(mntmFinancialStatement);
			mnSetup.add(mntmMarketingQuestion);
			
			btnIndividualCustomer.setEnabled(true);				
			btnPrint.setEnabled(true);	
			
			break;
			
		case "Auditor":
			mnCustomer.add(mntmInterviewSheet);		
			
			mnLoan.add(mntmViewLoanApplication);			
			mnLoan.add(mntmViewLoanOffer);			
			mnLoan.add(mntmAuditLoanOffer);
			
			
			btnLoanApproval.setEnabled(true);	
			break;
			
		case "Operations Officer":
			mnCustomer.add(mntmInterviewSheet);
			
			mnLoan.add(mntmDisburseLoan);	
			mnLoan.add(mntmViewLoanApplication);			
			mnLoan.add(mntmViewLoanOffer);
					
				
			btnLoanDisbursement.setEnabled(true);		
				
			break;
			
			
		case "Head of Operations":
			mnCustomer.add(mntmInterviewSheet);
			
			mnLoan.add(mntmViewLoanApplication);			
			mnLoan.add(mntmViewLoanOffer);
			mnLoan.add(mntmDisburseLoan);
			
			btnLoanDisbursement.setEnabled(true);	
			
			break;
			
		case "MIS Officer":
			mnCustomer.add(mntmInterviewSheet);
			
			mnLoan.add(mntmCreditSearch);
			mnLoan.add(mntmViewLoanApplication);			
			mnLoan.add(mntmViewLoanOffer);
			
			mnAdmin.add(mntmBranchesSetup);
			mnAdmin.add(mntmUserGroup);
			mnAdmin.add(mntmUserManagement);
			mnAdmin.add(mntmNewMenuItem_1);
			
			btnBackup.setEnabled(true);
			btnUserManagement.setEnabled(true);
			
			break;
			
		case "System Administrator":
			mnCustomer.add(mntmNewCustomerRegistration);
			mnCustomer.add(mntmEditCustomerRegistration);			
			mnCustomer.add(mntmNewInterviewSession);
			mnCustomer.add(mntmInterviewSheet);
			mnCustomer.add(mntmLoanSummary);
			
			mnLoan.add(mntmNewLoanApplication);
			mnLoan.add(mntmEditLoanApplication);
			mnLoan.add(mntmReturnedLoanApplication);
			mnLoan.add(mntmCreditSearch);
			mnLoan.add(mntmApproveLoanApplication);
			mnLoan.add(mntmNewLoanOffer);
			mnLoan.add(mntmLoanOffer);
			mnLoan.add(mntmRejectedLoanOffer);
			mnLoan.add(mntmDisburseLoan);
			mnLoan.add(mntmAuditLoanOffer);
			
			mnLoan.add(mntmViewLoanApplication);			
			mnLoan.add(mntmViewLoanOffer);			
			
			
			mnPrint.add(mntmLoanApplicationLetter);
			mnPrint.add(mntmOfferLetter); 
			mnPrint.add(mntmApprovalDisbursementForm);
			
			mnSetup.add(mntmBusinessCategory);
			mnSetup.add(mntmMarketingQuestion);
			mnSetup.add(mntmFinancialStatement);			
			mnSetup.add(mntmLoanProduct);
			
			mnAdmin.add(mntmBranchesSetup);
			mnAdmin.add(mntmUserGroup);
			mnAdmin.add(mntmUserManagement);
			mnAdmin.add(mntmNewMenuItem_1);
			
			btnIndividualCustomer.setEnabled(true);
			btnLoanApplication.setEnabled(true);			
			btnPrint.setEnabled(true);	
			btnLoanOffer.setEnabled(true);		
			btnLoanApproval.setEnabled(true);		
			btnLoanDisbursement.setEnabled(true);		
			btnBackup.setEnabled(true);
			btnUserManagement.setEnabled(true);
			
			break;	
			

		default:
			break;
		}	
		
		
		new TimeTask().run();
//		new TimTask().run();
		
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    int confirmed = JOptionPane.showConfirmDialog(jFrame, 
			        "Are you sure you want to logout of the program?", "Confirm Logout",
			        JOptionPane.YES_NO_OPTION);

			    if (confirmed == JOptionPane.YES_OPTION) {
			      System.exit(0);
			    }
			  }
			});
		
		
		
	}
	
	public void displayTray(int messages, String status) throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("");
        tray.add(trayIcon);

        trayIcon.displayMessage("New Loan Application", messages+ " "+ status.substring(0, 1).toUpperCase() + status.substring(1)+"  Loan Applications", MessageType.INFO);
    }
	
	
	
	
	public static void getNewMessages(String user) {
		LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
		
		 try {
			 action_count_holder_messenger = mainInterface.getNewMessagesCount(user);
			 
			 if (action_count_holder_messenger<1) {
					lblMessageCount.setText("");
				}else {
					lblMessageCount.setText(" " + action_count_holder_messenger);
				}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	 }
	
	public static void getTrayMessage(String user) {
		LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
		String status = "";
		switch (user) {
		case "Marketing Officer":
			status = "returned";
			
			break;
			
		case "Head of Marketing":
			status = "pending";		
						
			break;
		case "Marketing Supervisor":
			status = "pending";
				
						
			break;
			
			
		case "Credit Officer":
				
			status = "approved";		
						
			
			break;
			
			
		case "Head of Credit":
			
			status = "approved";
			break;
			
			
		case "Branch Manager":
			status = "forwarded";
			
			break;
		case "Managing Director":
			
			status = "recommended";
			break;
			
		case "Auditor":
			status = "offered";	
			break;
			
		case "Operations Officer":
			status = "audited";	
				
			break;
			
			
		case "Head of Operations":
			status = "audited";
			
			break;
			
		case "MIS Officer":
			
			status = "pending";
			break;
			
		case "System Administrator":
			status = "pending";
			
			break;	
			

		default:
			break;
		}	
		 try {
			 int pending_messages = mainInterface.getApplicationsCount(user_1.getUserGroup(), status, user_1.getBranch());
			 if (pending_messages > action_count_holder) {
				 if (SystemTray.isSupported()) {
						TestNotification td = new TestNotification();
			            td.displayTray(pending_messages, status, user_1.getBranch());			           
			        } else {
			            System.err.println("System tray not supported!");
			        }
				 
				
			}
			 action_count_holder = pending_messages;
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	 }
	
	
	public static class TimeTask extends TimerTask {		
		
	    public void run() {
	    	try {
	    		
	    		int delay = (216000);
		        timer.schedule(new TimeTask(), delay); 
		        getTrayMessage(user_1.getUserGroup());
		       
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        
	    }
	}
	
//	public static class TimTask extends TimerTask {		
//		
//	    public void run() {
//	    	try {
//	    		
//	    		int delay = (1000);
//		        timer_1.schedule(new TimTask(), delay); 
//		        getNewMessages(user_1.getUsername());
//		  
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	        
//	        
//	    }
//	}
}
