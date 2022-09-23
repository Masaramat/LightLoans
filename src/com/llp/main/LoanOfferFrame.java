package com.llp.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.llp.api.LLPMainInterface;
import com.llp.api.LLPSetupInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.AORecommendationNote;
import com.llp.entities.AccountTurnover;
import com.llp.entities.ApprovalDisbursementParameters;
import com.llp.entities.CollateralNote;
import com.llp.entities.DocumentationParameters;
import com.llp.entities.LoanApplication;
import com.llp.entities.LoanOffer;
import com.llp.entities.LoanOfferPrice;
import com.llp.entities.LoanOfferView;
import com.llp.entities.LoanProduct;
import com.llp.entities.OutstandingFacility;
import com.llp.entities.User;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.Color;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.ImageIcon;

public class LoanOfferFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	


	private JTextField appIdField;
	private JTextField appDateField;
	private static JTextField appStatusField;
	private JTextField moField;
	private JTextField branchField;
	private JTextField nameField;
	private JTextField businessField;
	private JTextField tenorField;
	private JTextArea purposeArea;	
	
	private JTextField interestField;  
	private JTextField monFeeField;
	private JTextField mgtFeeField;
	private JTextField riskPremField;
	private JTextField compSavField;
	
	private JTextField appFacilityField;
	private JTextField appTenorField;
	private JTextArea amountInWordsArea;
	@SuppressWarnings("rawtypes")
	private JComboBox productBox;
	@SuppressWarnings("rawtypes")
	private JComboBox interestTypeBox;
	@SuppressWarnings("rawtypes")
	private JComboBox tenorTypeBox;
	
	private JLabel Label_21;
	private JLabel Label_41;
	private JLabel Label;
	private JLabel Label_1;
	private JLabel Label_2;
	private JLabel Label_3;
	private JLabel Label_4;
	
	private JLabel principalLabel_1;
	private JLabel principalLabel_2; 
	private JLabel intChargesLabel_1;
	private JLabel intChargesLabel_2;
	private JLabel compSavings_1;
	private JLabel compSavings_2;
	private JLabel totalLabel_1;
	private JLabel totalLabel_2;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_2_1;
	  
	private JLabel ppIntFeeLbl;
	private JLabel ppIntFeeLbl_1;
	private JLabel ppMgtFeeLbl;
	private JLabel ppMgtFeeLbl_1;
	private JLabel ppMonFeeLbl;
	private JLabel ppMonFeeLbl_1;
	private JLabel ppRiskPremLbl;
	private JLabel ppRiskPremLbl_1;
	private JLabel ppGrossTotalLbl;
	private JLabel ppGrossTotalLbl_1;
	private JLabel ppGrossTotalPpLbl;
	private JLabel ppGrossTotalPpLbl_1;
	
	private JButton btnPrint; 
	private JButton btnFinish;
	
	private JPanel sw_panel_co;
	private static JButton btnRejectOffer;
	private static JButton btnAuditOffer;
	private JButton btnPrevious_2;
	private JButton btnDisburseLoan; 
	private JTextArea SoRTextArea;
	
	
	
	private ArrayList<LoanProduct> product_list = new ArrayList<LoanProduct>();
	private LoanOffer saveOffer = new LoanOffer();
	LoanOfferPrice offer_price = new LoanOfferPrice();
	CardLayout card_layout = new CardLayout();
	CardLayout switchCard_layout = new CardLayout();
	private JScrollPane scrollPane_1;
	private JButton btnClose_1;
	private JButton btnPrint_1;
	private JButton btnSendOn;

	final private LLPMainInterface mainInterface;
	private JPanel offer_panel_3;
	public static JTable outstandingBalanceTable;
	private JPanel offer_panel_4;
	private JButton btnAddOutstanding;
	public static JTable accountTurnoverTable;
	private JScrollPane scrollPane_2;
	private JButton btnNewButton_1;
	private JButton btnNextP2;
	private JButton btnPreviousP2;
	private JTextArea customerArea;
	private JTextArea collateralArea;
	
	private ArrayList<String> security_list;
	private CollateralNote note;
	
	
	private ArrayList<OutstandingFacility> facility_list;
	private ArrayList<AccountTurnover> turnover_list;
	
	
	private JLabel lblNewLabel_6_1_2;
	private JTable ADChecklistTable;
	private JTable docChecklistTable;
	
	
	
	private ArrayList<ApprovalDisbursementParameters> adp_list = new ArrayList<>(); 
	private ArrayList<DocumentationParameters> documentation_list = new ArrayList<>();
	private JLabel lblNewLabel_8;
	
	
	private ArrayList<ApprovalDisbursementParameters> loanRAC_list = new ArrayList<>();
	private ArrayList<DocumentationParameters> loanDocumentation_list = new ArrayList<>();
	
	
	
	private JButton btnNewButton_2;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_10;
	private JLabel lblNewLabel_11;
	private JLabel lblNewLabel_12;
	private JTextField facilityAppliedField;
	private JTextField facilityRecommendedField;
	private JTextArea facilityAppliedArea;
	private JTextArea facilityRecommendedArea;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanOfferFrame frame = new LoanOfferFrame(new User(), "", new LoanApplication(), new LoanOfferView());
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
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public LoanOfferFrame(final User user, final String source, final LoanApplication application,  final LoanOfferView loan_offer) throws RemoteException{
		setResizable(false);
		
		setTitle("Light Loan Processor - Loan Offer");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 70, 908, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(card_layout);
		
		mainInterface = InterfaceGenerator.getMainInterface();
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
				
	
		product_list = mainInterface.getAllLoanProducts();
		adp_list = setupInterface.getApprovalChecklistItems();
		documentation_list = setupInterface.getDocumentionChecklistItems();
		
		
		
		JPanel offer_panel_1 = new JPanel();
		contentPane.add(offer_panel_1, "op1");
		offer_panel_1.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Loan Application Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 862, 278);
		offer_panel_1.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Application ID");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(520, 22, 104, 20);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Application Date");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_1.setBounds(520, 53, 104, 20);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Branch");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_2.setBounds(520, 146, 92, 20);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Application Status");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_3.setBounds(520, 84, 104, 20);
		panel.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Account Officer");
		lblNewLabel_1_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_4.setBounds(520, 115, 92, 20);
		panel.add(lblNewLabel_1_4);
		
		appIdField = new JTextField();
		appIdField.setEditable(false);
		appIdField.setBounds(645, 22, 137, 20);
		panel.add(appIdField);
		appIdField.setColumns(10);
		
		appDateField = new JTextField();
		appDateField.setEditable(false);
		appDateField.setColumns(10);
		appDateField.setBounds(645, 53, 137, 20);
		panel.add(appDateField);
		
		appStatusField = new JTextField();
		appStatusField.setEditable(false);
		appStatusField.setColumns(10);
		appStatusField.setBounds(645, 84, 137, 20);
		panel.add(appStatusField);
		
		moField = new JTextField();
		moField.setEditable(false);
		moField.setColumns(10);
		moField.setBounds(645, 115, 137, 20);
		panel.add(moField);
		
		branchField = new JTextField();
		branchField.setEditable(false);
		branchField.setColumns(10);
		branchField.setBounds(645, 146, 137, 20);
		panel.add(branchField);
		
		JLabel lblNewLabel_1_5 = new JLabel("Customer Name");
		lblNewLabel_1_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_5.setBounds(23, 22, 92, 20);
		panel.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("Business");
		lblNewLabel_1_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_6.setBounds(23, 53, 92, 20);
		panel.add(lblNewLabel_1_6);
		
		JLabel lblNewLabel_1_7 = new JLabel("Purpose of Loan:");
		lblNewLabel_1_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_7.setBounds(23, 84, 92, 20);
		panel.add(lblNewLabel_1_7);
		
		nameField = new JTextField();
		nameField.setEditable(false);
		nameField.setBounds(150, 22, 180, 20);
		panel.add(nameField);
		nameField.setColumns(10);
		
		businessField = new JTextField();
		businessField.setEditable(false);
		businessField.setBounds(150, 53, 180, 20);
		panel.add(businessField);
		businessField.setColumns(10);
		
		JLabel lblNewLabel_1_10 = new JLabel("Tenor Requested");
		lblNewLabel_1_10.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_10.setBounds(23, 159, 92, 20);
		panel.add(lblNewLabel_1_10);
		
		JLabel lblNewLabel_1_11 = new JLabel("Sources of Repayment");
		lblNewLabel_1_11.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_11.setBounds(23, 132, 117, 20);
		panel.add(lblNewLabel_1_11);
		
		tenorField = new JTextField();
		tenorField.setEditable(false);
		tenorField.setBounds(150, 159, 166, 20);
		panel.add(tenorField);
		tenorField.setColumns(10);
		
		purposeArea = new JTextArea();
		purposeArea.setWrapStyleWord(true);
		purposeArea.setLineWrap(true);
		purposeArea.setEditable(false);
		purposeArea.setBounds(150, 82, 276, 37);
		panel.add(purposeArea);
		
		SoRTextArea = new JTextArea();
		SoRTextArea.setWrapStyleWord(true);
		SoRTextArea.setLineWrap(true);
		SoRTextArea.setBounds(150, 130, 276, 22);
		panel.add(SoRTextArea);
		
		lblNewLabel_9 = new JLabel("Facility Applied");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_9.setBounds(23, 190, 104, 22);
		panel.add(lblNewLabel_9);
		
		lblNewLabel_10 = new JLabel("Amount in Words");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_10.setBounds(23, 223, 104, 22);
		panel.add(lblNewLabel_10);
		
		lblNewLabel_11 = new JLabel("Facility Recommended");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_11.setBounds(520, 190, 117, 22);
		panel.add(lblNewLabel_11);
		
		lblNewLabel_12 = new JLabel("Amount in Words");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_12.setBounds(520, 223, 104, 22);
		panel.add(lblNewLabel_12);
		
		facilityAppliedField = new JTextField();
		facilityAppliedField.setEditable(false);
		facilityAppliedField.setBounds(150, 190, 166, 22);
		panel.add(facilityAppliedField);
		facilityAppliedField.setColumns(10);
		
		facilityRecommendedField = new JTextField();
		facilityRecommendedField.setEditable(false);
		facilityRecommendedField.setBounds(645, 191, 137, 20);
		panel.add(facilityRecommendedField);
		facilityRecommendedField.setColumns(10);
		
		facilityAppliedArea = new JTextArea();
		facilityAppliedArea.setEditable(false);
		facilityAppliedArea.setWrapStyleWord(true);
		facilityAppliedArea.setLineWrap(true);
		facilityAppliedArea.setBounds(150, 222, 276, 45);
		panel.add(facilityAppliedArea);
		
		facilityRecommendedArea = new JTextArea();
		facilityRecommendedArea.setEditable(false);
		facilityRecommendedArea.setWrapStyleWord(true);
		facilityRecommendedArea.setLineWrap(true);
		facilityRecommendedArea.setBounds(645, 222, 207, 45);
		panel.add(facilityRecommendedArea);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Loan Offer Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 300, 862, 234);
		offer_panel_1.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Loan Product");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_2_1.setBounds(10, 32, 92, 20);
		panel_1.add(lblNewLabel_1_2_1);
		
		productBox = new JComboBox();
		productBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (source.equalsIgnoreCase("new")) {
					int xy = productBox.getSelectedIndex();
					updateProductRates(product_list.get(xy));
				}
				
			}
		});
		productBox.setBounds(118, 31, 222, 22);
		panel_1.add(productBox);
		
		JLabel lblInterestRate = new JLabel("Interest Rate (%)");
		lblInterestRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInterestRate.setBounds(496, 33, 107, 19);
		panel_1.add(lblInterestRate);
		
		interestField = new JTextField();
		interestField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(interestField.getText().length()<1) {
					Label.setText("");
				}else {					
					try {
						double number = Double.parseDouble(interestField.getText());
						Label.setText("");
						
						}catch(Exception ex) {							
							interestField.setText("");
							Label.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		interestField.setText("0.0");
		interestField.setColumns(10);
		interestField.setBounds(624, 32, 55, 20);
		panel_1.add(interestField);
		
		JLabel lblNewLabel_1_12 = new JLabel("*");
		lblNewLabel_1_12.setForeground(Color.RED);
		lblNewLabel_1_12.setBounds(689, 35, 17, 14);
		panel_1.add(lblNewLabel_1_12);
		
		Label = new JLabel("");
		Label.setForeground(Color.RED);
		Label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label.setBounds(699, 33, 119, 14);
		panel_1.add(Label);
		
		JLabel lblMonFeeRate = new JLabel("Mon. Fee Rate (%)");
		lblMonFeeRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMonFeeRate.setBounds(496, 63, 107, 19);
		panel_1.add(lblMonFeeRate);
		
		monFeeField = new JTextField();
		monFeeField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(monFeeField.getText().length()<1) {
					Label_1.setText("");
				}else {					
					try {
						double number = Double.parseDouble(monFeeField.getText());
						Label_1.setText("");
						
						}catch(Exception ex) {							
							monFeeField.setText("");
							Label_1.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		monFeeField.setText("0.0");
		monFeeField.setColumns(10);
		monFeeField.setBounds(625, 62, 55, 20);
		panel_1.add(monFeeField);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("*");
		lblNewLabel_1_1_1.setForeground(Color.RED);
		lblNewLabel_1_1_1.setBounds(689, 68, 17, 14);
		panel_1.add(lblNewLabel_1_1_1);
		
		Label_1 = new JLabel("");
		Label_1.setForeground(Color.RED);
		Label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label_1.setBounds(699, 68, 119, 14);
		panel_1.add(Label_1);
		
		JLabel lblMgtFeeRate = new JLabel("Mgt. Fee Rate (%)");
		lblMgtFeeRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMgtFeeRate.setBounds(496, 94, 107, 19);
		panel_1.add(lblMgtFeeRate);
		
		mgtFeeField = new JTextField();
		mgtFeeField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(mgtFeeField.getText().length()<1) {
					Label_2.setText("");
				}else {					
					try {
						double number = Double.parseDouble(mgtFeeField.getText());
						Label_2.setText("");
						
						}catch(Exception ex) {							
							mgtFeeField.setText("");
							Label_2.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		mgtFeeField.setText("0.0");
		mgtFeeField.setColumns(10);
		mgtFeeField.setBounds(625, 93, 55, 20);
		panel_1.add(mgtFeeField);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("*");
		lblNewLabel_1_2_2.setForeground(Color.RED);
		lblNewLabel_1_2_2.setBounds(689, 96, 17, 14);
		panel_1.add(lblNewLabel_1_2_2);
		
		Label_2 = new JLabel("");
		Label_2.setForeground(Color.RED);
		Label_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label_2.setBounds(706, 99, 119, 14);
		panel_1.add(Label_2);
		
		JLabel lblRiskPremRate = new JLabel("Risk Prem. Rate (%)");
		lblRiskPremRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRiskPremRate.setBounds(496, 124, 119, 19);
		panel_1.add(lblRiskPremRate);
		
		riskPremField = new JTextField();
		riskPremField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(riskPremField.getText().length()<1) {
					Label_3.setText("");
				}else {					
					try {
						double number = Double.parseDouble(riskPremField.getText());
						Label_3.setText("");
						
						}catch(Exception ex) {							
							riskPremField.setText("");
							Label_3.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		riskPremField.setText("0.0");
		riskPremField.setColumns(10);
		riskPremField.setBounds(625, 121, 54, 20);
		panel_1.add(riskPremField);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("*");
		lblNewLabel_1_3_1.setForeground(Color.RED);
		lblNewLabel_1_3_1.setBounds(689, 121, 17, 14);
		panel_1.add(lblNewLabel_1_3_1);
		
		Label_3 = new JLabel("");
		Label_3.setForeground(Color.RED);
		Label_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label_3.setBounds(699, 123, 119, 14);
		panel_1.add(Label_3);
		
		JLabel lblCompSavingsRate = new JLabel("Comp. Savings Rate (%)");
		lblCompSavingsRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCompSavingsRate.setBounds(496, 154, 119, 19);
		panel_1.add(lblCompSavingsRate);
		
		compSavField = new JTextField();
		compSavField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(compSavField.getText().length()<1) {
					Label_4.setText("");
				}else {					
					try {
						double number = Double.parseDouble(compSavField.getText());
						Label_4.setText("");
						
						}catch(Exception ex) {							
							compSavField.setText("");
							Label_4.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		compSavField.setText("0.0");
		compSavField.setColumns(10);
		compSavField.setBounds(625, 151, 54, 20);
		panel_1.add(compSavField);
		
		JLabel lblNewLabel_1_3_1_1 = new JLabel("*");
		lblNewLabel_1_3_1_1.setForeground(Color.RED);
		lblNewLabel_1_3_1_1.setBounds(689, 146, 17, 14);
		panel_1.add(lblNewLabel_1_3_1_1);
		
		Label_4 = new JLabel("");
		Label_4.setForeground(Color.RED);
		Label_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label_4.setBounds(699, 148, 119, 14);
		panel_1.add(Label_4);
		
		JLabel lblNewLabel_1_2_1_1 = new JLabel("Interest Type");
		lblNewLabel_1_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_2_1_1.setBounds(10, 63, 92, 20);
		panel_1.add(lblNewLabel_1_2_1_1);
		
		interestTypeBox = new JComboBox();
		interestTypeBox.setModel(new DefaultComboBoxModel(new String[] {"Flat Rate", "Reducing Balance", "Balloon Payment", "Balloon Payment (Upfront Interest)"}));
		interestTypeBox.setBounds(118, 64, 222, 22);
		panel_1.add(interestTypeBox);
		
		JLabel lblNewLabel_1_2_1_2 = new JLabel("Loan Facility");
		lblNewLabel_1_2_1_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_2_1_2.setBounds(10, 94, 92, 20);
		panel_1.add(lblNewLabel_1_2_1_2);
		
		JLabel lblNewLabel_1_2_1_3 = new JLabel("Amount in Words");
		lblNewLabel_1_2_1_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_2_1_3.setBounds(10, 133, 92, 20);
		panel_1.add(lblNewLabel_1_2_1_3);
		
		JLabel lblNewLabel_1_2_1_4 = new JLabel("Proposed Tenor");
		lblNewLabel_1_2_1_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1_2_1_4.setBounds(10, 204, 92, 20);
		panel_1.add(lblNewLabel_1_2_1_4);
		
		appFacilityField = new JTextField();
		appFacilityField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(appFacilityField.getText().length()<1) {
					Label_21.setText("");
				}else {					
					try {
						double number = Double.parseDouble(appFacilityField.getText());
						Label_21.setText("");
						
						}catch(Exception ex) {							
							appFacilityField.setText("");
							Label_21.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		appFacilityField.setBounds(118, 97, 135, 20);
		panel_1.add(appFacilityField);
		appFacilityField.setColumns(10);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(118, 133, 246, 57);
		panel_1.add(scrollPane_1);
		
		amountInWordsArea = new JTextArea();
		scrollPane_1.setViewportView(amountInWordsArea);
		amountInWordsArea.setWrapStyleWord(true);
		amountInWordsArea.setLineWrap(true);
		
		appTenorField = new JTextField();
		appTenorField.addKeyListener(new KeyAdapter() {			
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(appTenorField.getText().length()<1) {
					Label_41.setText("");
				}else {					
					try {
						int number = Integer.parseInt(appTenorField.getText());
						Label_41.setText("");
						
						}catch(Exception ex) {							
							appTenorField.setText("");
							Label_41.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		appTenorField.setBounds(118, 204, 41, 20);
		panel_1.add(appTenorField);
		appTenorField.setColumns(10);
		
		tenorTypeBox = new JComboBox();
		tenorTypeBox.setModel(new DefaultComboBoxModel(new String[] {"Months", "Quarters", "Weeks"}));
		tenorTypeBox.setBounds(169, 203, 92, 22);
		panel_1.add(tenorTypeBox);
		
		Label_41 = new JLabel("");
		Label_41.setForeground(Color.RED);
		Label_41.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label_41.setBounds(288, 209, 119, 14);
		panel_1.add(Label_41);
		
		Label_21 = new JLabel("");
		Label_21.setForeground(Color.RED);
		Label_21.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label_21.setBounds(273, 100, 119, 14);
		panel_1.add(Label_21);
		
		JLabel lblNewLabel_1_3_1_1_1 = new JLabel("*");
		lblNewLabel_1_3_1_1_1.setForeground(Color.RED);
		lblNewLabel_1_3_1_1_1.setBounds(263, 97, 17, 14);
		panel_1.add(lblNewLabel_1_3_1_1_1);
		
		JLabel lblNewLabel_1_3_1_1_2 = new JLabel("*");
		lblNewLabel_1_3_1_1_2.setForeground(Color.RED);
		lblNewLabel_1_3_1_1_2.setBounds(374, 136, 17, 14);
		panel_1.add(lblNewLabel_1_3_1_1_2);
		
		JLabel lblNewLabel_1_3_1_1_3 = new JLabel("*");
		lblNewLabel_1_3_1_1_3.setForeground(Color.RED);
		lblNewLabel_1_3_1_1_3.setBounds(271, 207, 17, 14);
		panel_1.add(lblNewLabel_1_3_1_1_3);
		
		JButton btnCancel_1 = new JButton("Close");
		btnCancel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCancel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFrame ff = new JFrame();
					ff.setAlwaysOnTop(true);
					int confirmed = JOptionPane.showConfirmDialog(ff, 
					        "Do you want terminate this process?", "Confirm process termination",
					        JOptionPane.YES_NO_OPTION);

					    if (confirmed == JOptionPane.YES_OPTION) {
					    	dispose();
					    	System.gc();
					    	
					    }
					
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnCancel_1.setBounds(793, 545, 89, 23);
		offer_panel_1.add(btnCancel_1);
		
		JButton btnNext = new JButton("Next >>");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(interestField.getText().length()<1 || monFeeField.getText().length()<1 || mgtFeeField.getText().length()<1 || riskPremField.getText().length()<1 ||
					compSavField.getText().length()<1 || appFacilityField.getText().length()<1 || appTenorField.getText().length()<1 || amountInWordsArea.getText().length()<1 ) {
				showPopup("All fields marked * cannot be empty.");
			}else {
				
				LoanOffer offer = new LoanOffer();
				offer.setApplicationId(appIdField.getText());
				offer.setPrincipal(Double.parseDouble(appFacilityField.getText()));
				offer.setTenor(Integer.parseInt(appTenorField.getText()));
				offer.setTenorType(tenorTypeBox.getSelectedItem().toString());
				offer.setAmountInWords(amountInWordsArea.getText());
				offer.setInterestRate(Double.parseDouble(interestField.getText()));
				offer.setMonitoringFeeRate(Double.parseDouble(monFeeField.getText()));
				offer.setRiskPremiumRate(Double.parseDouble(riskPremField.getText()));
				offer.setManagementFeeRate(Double.parseDouble(mgtFeeField.getText()));
				offer.setCompulsorySavingsRate(Double.parseDouble(compSavField.getText()));
				offer.setInterestType(interestTypeBox.getSelectedItem().toString());
				offer.setLoanProduct(productBox.getSelectedItem().toString());
				offer.setStaff(user.getFullName());
				offer.setCustomerId(application.getCustomerId());
				
				if(user.getBranch().equalsIgnoreCase("COCIN Headqaurters") || user.getBranch().equalsIgnoreCase("Gindiri") && Double.parseDouble(appFacilityField.getText())>100000) {
					offer.setClearanceLevel(2);
				}else if(user.getBranch().equalsIgnoreCase("Head Office") && Double.parseDouble(appFacilityField.getText())>300000) {
					offer.setClearanceLevel(2);
				}
				else {
					offer.setClearanceLevel(1);
				}
				saveOffer = offer;
				offer_price = calculateOfferPricing(saveOffer);
				updatePricingTable(offer_price);
						
				card_layout.show(contentPane, "op2");
				
			}
			
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNext.setBounds(694, 545, 89, 23);
		offer_panel_1.add(btnNext);
		
		JPanel offer_panel_2 = new JPanel();
		contentPane.add(offer_panel_2, "op2");
		offer_panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("AMOUNT REPAYABLE");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(250, 11, 194, 25);
		offer_panel_2.add(lblNewLabel);
		
		lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(351, 48, 158, 25);
		offer_panel_2.add(lblNewLabel_2);		
		
		lblNewLabel_2_1 = new JLabel();
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2_1.setBounds(183, 48, 158, 25);
		offer_panel_2.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_3 = new JLabel("Principal");
		lblNewLabel_3.setBounds(31, 84, 121, 25);
		offer_panel_2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Interest Charges");
		lblNewLabel_3_1.setBounds(31, 120, 121, 25);
		offer_panel_2.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("Compulsory Savings");
		lblNewLabel_3_2.setBounds(31, 156, 121, 25);
		offer_panel_2.add(lblNewLabel_3_2);
		
		principalLabel_1 = new JLabel();
		principalLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		principalLabel_1.setBounds(183, 84, 158, 25);
		offer_panel_2.add(principalLabel_1);
		
		principalLabel_2 = new JLabel();
		principalLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		principalLabel_2.setBounds(351, 84, 158, 25);
		offer_panel_2.add(principalLabel_2);
		
		intChargesLabel_1 = new JLabel();
		intChargesLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		intChargesLabel_1.setBounds(183, 120, 158, 25);
		offer_panel_2.add(intChargesLabel_1); 
		
		intChargesLabel_2 = new JLabel();
		intChargesLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		intChargesLabel_2.setBounds(351, 120, 158, 25);
		offer_panel_2.add(intChargesLabel_2);
		
		compSavings_1 = new JLabel();
		compSavings_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		compSavings_1.setBounds(183, 156, 158, 25);
		offer_panel_2.add(compSavings_1);
		
		compSavings_2 = new JLabel();
		compSavings_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		compSavings_2.setBounds(351, 161, 158, 25);
		offer_panel_2.add(compSavings_2);
		
		JLabel lblNewLabel_3_2_1 = new JLabel("TOTAL");
		lblNewLabel_3_2_1.setBounds(31, 192, 121, 25);
		offer_panel_2.add(lblNewLabel_3_2_1);
		
		totalLabel_1 = new JLabel();
		totalLabel_1.setBounds(183, 192, 158, 25);
		offer_panel_2.add(totalLabel_1);
		
		totalLabel_2 = new JLabel();
		totalLabel_2.setBounds(351, 192, 158, 25);
		offer_panel_2.add(totalLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("PROPOSED PRICING");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_4.setBounds(250, 245, 194, 25);
		offer_panel_2.add(lblNewLabel_4);
		
		ppIntFeeLbl = new JLabel(""); 
		ppIntFeeLbl.setBounds(31, 281, 425, 25);
		offer_panel_2.add(ppIntFeeLbl);
		
		ppMgtFeeLbl = new JLabel("");
		ppMgtFeeLbl.setBounds(31, 317, 425, 25);
		offer_panel_2.add(ppMgtFeeLbl);
		
		ppMonFeeLbl = new JLabel("");
		ppMonFeeLbl.setBounds(31, 353, 425, 25);
		offer_panel_2.add(ppMonFeeLbl);
		
		ppRiskPremLbl = new JLabel("");
		ppRiskPremLbl.setBounds(31, 389, 425, 25);
		offer_panel_2.add(ppRiskPremLbl);
		
		ppGrossTotalLbl = new JLabel("");
		ppGrossTotalLbl.setBounds(31, 425, 425, 25);
		offer_panel_2.add(ppGrossTotalLbl);
		
		ppGrossTotalPpLbl = new JLabel("");
		ppGrossTotalPpLbl.setBounds(31, 461, 425, 25);
		offer_panel_2.add(ppGrossTotalPpLbl);
		
		ppIntFeeLbl_1 = new JLabel("");
		ppIntFeeLbl_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		ppIntFeeLbl_1.setBounds(466, 281, 255, 25);
		offer_panel_2.add(ppIntFeeLbl_1);
		
		ppMgtFeeLbl_1 = new JLabel("");
		ppMgtFeeLbl_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		ppMgtFeeLbl_1.setBounds(466, 317, 255, 25);
		offer_panel_2.add(ppMgtFeeLbl_1);
		
		ppMonFeeLbl_1 = new JLabel("");
		ppMonFeeLbl_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		ppMonFeeLbl_1.setBounds(466, 353, 255, 25);
		offer_panel_2.add(ppMonFeeLbl_1);
		
		ppRiskPremLbl_1 = new JLabel("");
		ppRiskPremLbl_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		ppRiskPremLbl_1.setBounds(466, 389, 255, 25);
		offer_panel_2.add(ppRiskPremLbl_1);
		
		ppGrossTotalLbl_1 = new JLabel("");
		ppGrossTotalLbl_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		ppGrossTotalLbl_1.setBounds(466, 425, 255, 25);
		offer_panel_2.add(ppGrossTotalLbl_1);
		
		ppGrossTotalPpLbl_1 = new JLabel("");
		ppGrossTotalPpLbl_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		ppGrossTotalPpLbl_1.setBounds(466, 461, 255, 25);
		offer_panel_2.add(ppGrossTotalPpLbl_1);
		
		btnNextP2 = new JButton("Next >>");
		btnNextP2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(contentPane, "op3");
				
			}
		});
		btnNextP2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNextP2.setBounds(633, 546, 123, 23);
		offer_panel_2.add(btnNextP2);
		
		btnPreviousP2 = new JButton("<< Previous");
		btnPreviousP2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(contentPane, "op1");
			}
		});
		btnPreviousP2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPreviousP2.setBounds(502, 546, 121, 23);
		offer_panel_2.add(btnPreviousP2);
		
		btnNewButton_2 = new JButton("Close");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFrame ff = new JFrame();
					ff.setAlwaysOnTop(true);
					int confirmed = JOptionPane.showConfirmDialog(ff, 
					        "Do you want terminate this process?", "Confirm process termination",
					        JOptionPane.YES_NO_OPTION);

					    if (confirmed == JOptionPane.YES_OPTION) {
					    	dispose();
					    	System.gc();
					    	
					    }
					
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(766, 546, 104, 23);
		offer_panel_2.add(btnNewButton_2);
		
		offer_panel_4 = new JPanel();
		contentPane.add(offer_panel_4, "op4");
		offer_panel_4.setLayout(null);
		
		JPanel switchButtonPanel = new JPanel();
		switchButtonPanel.setBounds(208, 497, 664, 73);
		switchButtonPanel.setBorder(null);
		switchButtonPanel.setLayout(switchCard_layout);
		offer_panel_4.add(switchButtonPanel);
		
		sw_panel_co = new JPanel();
		switchButtonPanel.add(sw_panel_co, "copanel");
		sw_panel_co.setLayout(null);
		
		JButton btnPrevious = new JButton("<< Previous");
		btnPrevious.setBounds(337, 39, 99, 23);
		sw_panel_co.add(btnPrevious);
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(contentPane, "op3");
			}
		});
		btnPrevious.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnFinish = new JButton("Save Offer");
		btnFinish.setBounds(337, 11, 99, 23);
		sw_panel_co.add(btnFinish);
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String appId = "";
				String type = "";
				if(source.equalsIgnoreCase("edit") || source.equalsIgnoreCase("view")) {
					appId = loan_offer.getApplicationId();
				}else {
					appId = application.getApplicationId();
					type = "update";
				}
				try {
if (appStatusField.getText().equalsIgnoreCase("approved")) {
						
						CollateralNote note = new CollateralNote();
						note.setCollateral(collateralArea.getText());
						note.setCostumerBackground(customerArea.getText());
						mainInterface.saveCollateralNote(note, appId);
						
						note = mainInterface.getCollateralNotes(appId);
						customerArea.setText(note.getCostumerBackground());
						collateralArea.setText(note.getCollateral());
						
						
						for(int i = 0; i<adp_list.size(); i++) {
							ApprovalDisbursementParameters adp = new ApprovalDisbursementParameters();
							adp.setApplicationId(appId);
							adp.setParameter(adp_list.get(i).getParameter());
							String string = "";
							String string2 = "";
							if(!(ADChecklistTable.getValueAt(i, 3) == null)){
								string = ADChecklistTable.getValueAt(i, 3).toString();
							}
							adp.setRemark(string);
							
							adp.setStatus(ADChecklistTable.getValueAt(i, 2).toString());
							loanRAC_list.add(adp);
						}
						
						mainInterface.saveLoanRAC(loanRAC_list);
						adp_list = mainInterface.getLoanRAC(appId);
						updateADChecklistTable(adp_list, 0);
						
						for(int i = 0; i<documentation_list.size(); i++) {
							DocumentationParameters dp = new DocumentationParameters();
							dp.setApplicationId(appId);
							dp.setParameter(documentation_list.get(i).getParameter());
							dp.setStatus(docChecklistTable.getValueAt(i, 2).toString());
							String string = "";
							if(!(docChecklistTable.getValueAt(i, 3) == null)) {
								string = docChecklistTable.getValueAt(i, 3).toString();
							}
							dp.setRemark(string);
							loanDocumentation_list.add(dp);					
						}
						mainInterface.saveLoanDocumentation(loanDocumentation_list);
						documentation_list = mainInterface.getLoanDocumentation(appId);
						updateDocChecklistTable(documentation_list, 0);
						
						mainInterface.createLoanOffer(application.getBranch(), saveOffer);
						showPopup( "Loan Offer created successfully!");
						disableComponents();
						btnSendOn.setEnabled(true);
						
						
					} else{
						
						CollateralNote note = new CollateralNote();
						note.setCollateral(collateralArea.getText());
						note.setCostumerBackground(customerArea.getText());
						mainInterface.saveCollateralNote(note, appId);
						
						note = mainInterface.getCollateralNotes(appId);
						customerArea.setText(note.getCostumerBackground());
						collateralArea.setText(note.getCollateral());
						
						
						for(int i = 0; i<adp_list.size(); i++) {
							ApprovalDisbursementParameters adp = new ApprovalDisbursementParameters();
							adp.setApplicationId(appId);
							adp.setParameter(adp_list.get(i).getParameter());
							String string = "";
							String string2 = "";
							if(!(ADChecklistTable.getValueAt(i, 3) == null)){
								string = ADChecklistTable.getValueAt(i, 3).toString();
							}
							adp.setRemark(string);
							
							adp.setStatus(ADChecklistTable.getValueAt(i, 2).toString());
							loanRAC_list.add(adp);
						}
						
						mainInterface.saveLoanRAC(loanRAC_list);
						adp_list = mainInterface.getLoanRAC(appId);
						updateADChecklistTable(adp_list, 0);
						
						for(int i = 0; i<documentation_list.size(); i++) {
							DocumentationParameters dp = new DocumentationParameters();
							dp.setApplicationId(appId);
							dp.setParameter(documentation_list.get(i).getParameter());
							dp.setStatus(docChecklistTable.getValueAt(i, 2).toString());
							String string = "";
							if(!(docChecklistTable.getValueAt(i, 3) == null)) {
								string = docChecklistTable.getValueAt(i, 3).toString();
							}
							dp.setRemark(string);
							loanDocumentation_list.add(dp);					
						}
						mainInterface.saveLoanDocumentation(loanDocumentation_list);
						documentation_list = mainInterface.getLoanDocumentation(appId);
						updateDocChecklistTable(documentation_list, 0);
				    	
						
						if(user.getUserGroup().equalsIgnoreCase("System Administrator") || user.getUserGroup().equalsIgnoreCase("MIS Officer")) {
							mainInterface.updateLoanOffer(saveOffer, "");
						}else {							
							mainInterface.updateLoanOffer(saveOffer, "change");						
							
						}
						
						
						
						
						showPopup("Loan Offer Updated successfully!");
						disableComponents();
						btnSendOn.setEnabled(true);
						
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		btnFinish.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnPrint = new JButton("Preview"); 
		btnPrint.setEnabled(false);
		btnPrint.setBounds(555, 11, 99, 23);
		sw_panel_co.add(btnPrint);
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OfferLetterFrame olf = new OfferLetterFrame(appIdField.getText());
				olf.setVisible(true);
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(555, 39, 99, 23);
		sw_panel_co.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnFinish.isEnabled()) {
					try {
						JFrame ff = new JFrame();
						ff.setAlwaysOnTop(true);
						int confirmed = JOptionPane.showConfirmDialog(ff, 
						        "Do you want terminate this process?", "Confirm process termination",
						        JOptionPane.YES_NO_OPTION);

						    if (confirmed == JOptionPane.YES_OPTION) {
						    	dispose();
						    	System.gc();
						    	
						    }
						
					}catch (Exception e2) {
						e2.printStackTrace();
					}
				}else {
					dispose();
			    	System.gc();
				}
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		btnSendOn = new JButton("Send to Audit");
		btnSendOn.setEnabled(false);
		btnSendOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ff = new JFrame();
				ff.setAlwaysOnTop(true);
				int clr = 1;
				String id = "";
				if (source.equalsIgnoreCase("edit")) {
					clr = loan_offer.getClearanceLevel();
					id = loan_offer.getApplicationId();
				}else {
					clr = application.getClearance();
					id = application.getApplicationId();
				}
				
				int confirmed = JOptionPane.showConfirmDialog(ff, 
				        "Do you want to forward this loan offer for audit?", "Forward Loan Offer",
				        JOptionPane.YES_NO_OPTION);

				    
				    try {
				    	if (confirmed == JOptionPane.YES_OPTION) {
				    		mainInterface.updateApplicationStatus(id, "offered");	
					    	showPopup("Loan offer forwarded successfully!");
					    	deactivateSomeButtons("");
					    	btnSendOn.setEnabled(false);
					    	btnFinish.setEnabled(false);
					    }
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				    
				
				
			}
		});
		btnSendOn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSendOn.setBounds(446, 11, 99, 23);
		sw_panel_co.add(btnSendOn);
		
				
		JPanel sw_panel_md = new JPanel();
		switchButtonPanel.add(sw_panel_md, "mdpanel");
		sw_panel_md.setLayout(null);
		
		btnDisburseLoan = new JButton("Disburse");
		btnDisburseLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ggFrame = new JFrame();
				ggFrame.setAlwaysOnTop(true);
				int confirmed = JOptionPane.showConfirmDialog(ggFrame, 
				        "Do you want to disburse this loan?", "Confirm Loan Disbursement",
				        JOptionPane.YES_NO_OPTION);
				
				    try {
				    	 if (confirmed == JOptionPane.YES_OPTION) {
						    	mainInterface.updateOfferStatus(loan_offer, "disburse", user.getFullName() );
						    	
						    	showPopup( "Loan Disbursed Successfully!");
						    	fillOfferForm(mainInterface.getLoanOfferList(loan_offer.getApplicationId()).get(0));
						    	btnDisburseLoan.setEnabled(false);
						    	
						    	
						    }
					} catch (Exception e2) {
						e2.printStackTrace();
					}
			}
		});
		btnDisburseLoan.setEnabled(false);
		btnDisburseLoan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDisburseLoan.setBounds(552, 11, 102, 23);
		sw_panel_md.add(btnDisburseLoan);
		
		btnRejectOffer = new JButton("Reject");
		btnRejectOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
								    
				try {
					JFrame ff = new JFrame();
					ff.setAlwaysOnTop(true);
					int confirmed = JOptionPane.showConfirmDialog(ff, 
					        "Do you want to rejecct this loan offer?", "Confirm Loan Rejection",
					        JOptionPane.YES_NO_OPTION);

					    if (confirmed == JOptionPane.YES_OPTION) {
					    	RejectLoanFrame frame = new RejectLoanFrame(loan_offer.getApplicationId(), "offer", user);
							frame.setVisible(true);   
					    	
					    }
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				
			}
		});
		btnRejectOffer.setEnabled(false);
		btnRejectOffer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRejectOffer.setBounds(440, 11, 102, 23);
		sw_panel_md.add(btnRejectOffer);
		
		btnAuditOffer = new JButton("Accept");
		btnAuditOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ff = new JFrame();
				ff.setAlwaysOnTop(true);
				
				String appId = "";
				if(source.equalsIgnoreCase("edit") || source.equalsIgnoreCase("view")) {
					appId = loan_offer.getApplicationId();
				}else {
					appId = application.getApplicationId();
				}
 								
				    try {
				    	int confirmed = JOptionPane.showConfirmDialog(ff, "Do you want to approve this loan offer for disbursement?", "Confirm Loan Audit", JOptionPane.YES_NO_OPTION);

					    if (confirmed == JOptionPane.YES_OPTION) {
					    	
					    	mainInterface.updateOfferStatus(loan_offer, "audit", user.getFullName());
					    	mainInterface.updateLoanMaturity(loan_offer);;
					    	showPopup("Loan Offer Audited Successfully!");
					    	fillOfferForm(mainInterface.getLoanOfferList(appId).get(0));//this is the error
					    	btnAuditOffer.setEnabled(false);
					    	btnRejectOffer.setEnabled(false);
					    	
					    }
					} catch (Exception e3) {
						e3.printStackTrace();
					}
			}
		});
		btnAuditOffer.setEnabled(false);
		btnAuditOffer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAuditOffer.setBounds(328, 11, 102, 23);
		sw_panel_md.add(btnAuditOffer);
		
		btnPrevious_2 = new JButton("<< Previous");
		btnPrevious_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				card_layout.show(contentPane, "op3");				
			}
		});
		btnPrevious_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrevious_2.setBounds(328, 39, 102, 23);
		sw_panel_md.add(btnPrevious_2);
		
		btnClose_1 = new JButton("Close");
		btnClose_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnAuditOffer.isEnabled()) {
					try {
						JFrame ff = new JFrame();
						ff.setAlwaysOnTop(true);
						int confirmed = JOptionPane.showConfirmDialog(ff, 
						        "Do you want terminate this process?", "Confirm process termination",
						        JOptionPane.YES_NO_OPTION);

						    if (confirmed == JOptionPane.YES_OPTION) {
						    	dispose();
						    	System.gc();
						    	
						    }
						
					}catch (Exception e2) {
						e2.printStackTrace();
					}
				}else {
					dispose();
			    	System.gc();
				}
				
			}
		});
		btnClose_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose_1.setBounds(552, 39, 102, 23);
		sw_panel_md.add(btnClose_1);
		
		btnPrint_1 = new JButton("Preview");
		btnPrint_1.setEnabled(false);
		btnPrint_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnPrint_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrint_1.setBounds(440, 39, 102, 23);
		sw_panel_md.add(btnPrint_1);
		
		JLabel lblNewLabel_7 = new JLabel("RISK ACCEPTANCE CRITERIA");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_7.setBounds(10, 11, 198, 25);
		offer_panel_4.add(lblNewLabel_7);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 38, 803, 197);
		offer_panel_4.add(scrollPane_3);
		
		ADChecklistTable = new JTable();
		scrollPane_3.setViewportView(ADChecklistTable);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 296, 803, 190);
		offer_panel_4.add(scrollPane_4);
		
		docChecklistTable = new JTable();
		scrollPane_4.setViewportView(docChecklistTable);
		
		lblNewLabel_8 = new JLabel("(Please fill in \"Yes\" or \"No\" as appropriate.)");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_8.setBounds(198, 15, 230, 19);
		offer_panel_4.add(lblNewLabel_8);
		
		JButton btnDeleteDOC = new JButton("");
		btnDeleteDOC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (docChecklistTable.getSelectedRow() < 0) {
					showPopup("No item selected.");
				}else {
					documentation_list.remove(docChecklistTable.getSelectedRow());
					try {
						if (mainInterface.loanDocExists(application.getApplicationId())) {
							updateDocChecklistTable(documentation_list, 0);
							updateTableColumns();
						}else {
							updateDocChecklistTable(documentation_list, 1);
							updateTableColumns();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btnDeleteDOC.setIcon(new ImageIcon(LoanOfferFrame.class.getResource("/resources/bin.png")));
		btnDeleteDOC.setBounds(823, 296, 39, 31);
		offer_panel_4.add(btnDeleteDOC);
		
		JButton btnRefreshDOC = new JButton("");
		btnRefreshDOC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (mainInterface.loanDocExists(application.getApplicationId())) {
						documentation_list = mainInterface.getLoanDocumentation(application.getApplicationId());
						updateDocChecklistTable(documentation_list, 0);
					}else {
						documentation_list = setupInterface.getDocumentionChecklistItems();
						updateDocChecklistTable(documentation_list, 1);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnRefreshDOC.setIcon(new ImageIcon(LoanOfferFrame.class.getResource("/resources/refresh.png")));
		btnRefreshDOC.setBounds(823, 338, 39, 31);
		offer_panel_4.add(btnRefreshDOC);
		
		JButton btnDeleteRAC = new JButton("");
		btnDeleteRAC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ADChecklistTable.getSelectedRow() < 0) {
					showPopup("No item selected.");
				}else {
					try {
						adp_list.remove(ADChecklistTable.getSelectedRow());
						if (mainInterface.loanRACExists(application.getApplicationId())) {								
							updateADChecklistTable(adp_list, 0);
							updateTableColumns();
						}else {							
							updateADChecklistTable(adp_list, 1);
							updateTableColumns();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
			}
		});
		btnDeleteRAC.setIcon(new ImageIcon(LoanOfferFrame.class.getResource("/resources/bin.png")));
		btnDeleteRAC.setBounds(823, 38, 39, 31);
		offer_panel_4.add(btnDeleteRAC);
		
		JButton btnRefreshRAC = new JButton("");
		btnRefreshRAC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (mainInterface.loanRACExists(application.getApplicationId())) {
						adp_list = mainInterface.getLoanRAC(application.getApplicationId());
						updateADChecklistTable(adp_list, 0);
						updateTableColumns();
					}else {
						adp_list = setupInterface.getApprovalChecklistItems();
						updateADChecklistTable(adp_list, 1);
						updateTableColumns();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnRefreshRAC.setIcon(new ImageIcon(LoanOfferFrame.class.getResource("/resources/refresh.png")));
		btnRefreshRAC.setBounds(823, 80, 39, 31);
		offer_panel_4.add(btnRefreshRAC);
		
		offer_panel_3 = new JPanel();
		contentPane.add(offer_panel_3, "op3");
		offer_panel_3.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Disbursement / Approval Form");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(10, 11, 862, 25);
		offer_panel_3.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Collateral");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBounds(29, 47, 126, 25);
		offer_panel_3.add(lblNewLabel_6);
		
		collateralArea = new JTextArea();
		collateralArea.setWrapStyleWord(true);
		collateralArea.setLineWrap(true);		
		collateralArea.setBounds(29, 72, 817, 83);
		
		offer_panel_3.add(collateralArea);
		
		JLabel lblNewLabel_6_1 = new JLabel("Customer Background");
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6_1.setBounds(29, 166, 809, 25);
		offer_panel_3.add(lblNewLabel_6_1);
		
		customerArea = new JTextArea();
		customerArea.setWrapStyleWord(true);
		customerArea.setLineWrap(true);
		customerArea.setBounds(29, 192, 809, 83);		
		offer_panel_3.add(customerArea);
				
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 335, 759, 83);
		offer_panel_3.add(scrollPane);
		
		outstandingBalanceTable = new JTable();
		outstandingBalanceTable.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				
				try {
					String appId = "";
					if(source.equalsIgnoreCase("edit") || source.equalsIgnoreCase("view")) {
						appId = loan_offer.getApplicationId();
					}else {
						appId = application.getApplicationId();
					}
					facility_list = mainInterface.getOutstandingFacilities(appId);
					updateOutstandingFacilityTable(facility_list);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		outstandingBalanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(outstandingBalanceTable);
		
		JLabel lblNewLabel_6_1_1 = new JLabel("Outstanding Facilities");
		lblNewLabel_6_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6_1_1.setBounds(29, 299, 384, 25);
		offer_panel_3.add(lblNewLabel_6_1_1);
		
		JButton btnNext_3 = new JButton("Next >>");
		btnNext_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNext_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (user.getUserGroup().equalsIgnoreCase("Head of credit") || user.getUserGroup().equalsIgnoreCase("Credit officer")) {
					if (customerArea.getText().length()<1) {
						showPopup("Please fill in the customer background information.");
					}else {
						try {							
							card_layout.show(contentPane, "op4");							
							
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				}else {
					card_layout.show(contentPane, "op4");
					
				}
			}
		});
		btnNext_3.setBounds(664, 547, 109, 23);
		offer_panel_3.add(btnNext_3);
		
		JButton btnPrevious_3 = new JButton("<< Previous");
		btnPrevious_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnPrevious_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card_layout.show(contentPane, "op2");
			}
		});
		btnPrevious_3.setBounds(545, 547, 109, 23);
		offer_panel_3.add(btnPrevious_3);
		
		btnAddOutstanding = new JButton("Add / Remove");
		btnAddOutstanding.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddOutstanding.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String appId = "";
				if(source.equalsIgnoreCase("edit") || source.equalsIgnoreCase("view")) {
					appId = loan_offer.getApplicationId();
				}else {
					appId = application.getApplicationId();
				}
				OutstandingFacilityFrame frame = new OutstandingFacilityFrame(appId);
				frame.setVisible(true);
			}
		});
		btnAddOutstanding.setBounds(656, 301, 132, 23);
		offer_panel_3.add(btnAddOutstanding);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(27, 461, 761, 72);
		offer_panel_3.add(scrollPane_2);
		
		accountTurnoverTable = new JTable();
		accountTurnoverTable.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				try {
					String appId = "";
					if(source.equalsIgnoreCase("edit") || source.equalsIgnoreCase("view")) {
						appId = loan_offer.getApplicationId();
					}else {
						appId = application.getApplicationId();
					}
					turnover_list = mainInterface.getAccountTurnovers(appId);
					updateAccountTurnoverTable(turnover_list);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		scrollPane_2.setViewportView(accountTurnoverTable);
		
		btnNewButton_1 = new JButton("Add / Remove");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String appId = "";
				if(source.equalsIgnoreCase("edit") || source.equalsIgnoreCase("view")) {
					appId = loan_offer.getApplicationId();
				}else {
					appId = application.getApplicationId();
				}
				AccountTurnoverFrame frame = new AccountTurnoverFrame(appId);
				frame.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(656, 430, 132, 23);
		offer_panel_3.add(btnNewButton_1);
		
		lblNewLabel_6_1_2 = new JLabel("Account Turnover For The Past Three Months");
		lblNewLabel_6_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6_1_2.setBounds(29, 428, 491, 25);
		offer_panel_3.add(lblNewLabel_6_1_2);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFrame ff = new JFrame();
					ff.setAlwaysOnTop(true);
					int confirmed = JOptionPane.showConfirmDialog(ff, 
					        "Do you want to rejecct this loan offer?", "Confirm Loan Rejection",
					        JOptionPane.YES_NO_OPTION);

					    if (confirmed == JOptionPane.YES_OPTION) {
					    	dispose();
					    	System.gc();
					    	
					    }
					
				}catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(783, 547, 89, 23);
		offer_panel_3.add(btnNewButton);
		
		if(user.getUserGroup().equalsIgnoreCase("Credit Officer")|| user.getUserGroup().equalsIgnoreCase("Head of Credit") || user.getUserGroup().equalsIgnoreCase("System Administrator")|| user.getUserGroup().equalsIgnoreCase("MIS Officer")) {
			switchCard_layout.show(switchButtonPanel, "copanel");
		}else {
			switchCard_layout.show(switchButtonPanel, "mdpanel");
		}
		
		//
		if(user.getUserGroup().equalsIgnoreCase("Auditor") && loan_offer.getApplicationStatus().equalsIgnoreCase("offered")) {
			btnRejectOffer.setEnabled(true); 
			btnAuditOffer.setEnabled(true);
		}else if((user.getUserGroup().equalsIgnoreCase("Operations Officer") || user.getUserGroup().equalsIgnoreCase("Head of Operations")) 
				&& loan_offer.getApplicationStatus().equalsIgnoreCase("audited")) {
			btnDisburseLoan.setEnabled(true);   
		}
		
		
		updateProductBox();
		
		//check if source is new before updating product rates from setup files else update from loan offer
		if (source.equalsIgnoreCase("new")) {
			int xy = productBox.getSelectedIndex();
			updateProductRates(product_list.get(xy));
			
		}
		
		
		fillApplicationForm(application);
		updateApprovalDisbursementForm(application.getApplicationId());	
		
		try {
			String appId = "";
			if(source.equalsIgnoreCase("edit") || source.equalsIgnoreCase("view")) {
				appId = loan_offer.getApplicationId();
			}else {
				appId = application.getApplicationId();
			}
			if (mainInterface.loanRACExists(appId)) {
				adp_list = mainInterface.getLoanRAC(appId);
				updateADChecklistTable(adp_list, 0);
			}else {
				updateADChecklistTable(adp_list, 1);
			}
			if (mainInterface.loanDocExists(appId)) {
				documentation_list = mainInterface.getLoanDocumentation(appId);
				updateDocChecklistTable(documentation_list, 0);
			}else {
				updateDocChecklistTable(documentation_list, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		if(source.equalsIgnoreCase("edit")) {
			fillOfferForm(loan_offer);
			updateApprovalDisbursementForm(loan_offer.getApplicationId());		
		}else if(source.equalsIgnoreCase("view")) {
			fillOfferForm(loan_offer);
			disableComponents();
			btnAuditOffer.setEnabled(false);
			btnDisburseLoan.setEnabled(false);
			btnRejectOffer.setEnabled(false);
			btnSendOn.setEnabled(false);
			btnPrint_1.setEnabled(false);
			btnPrint.setEnabled(false);
			btnFinish.setEnabled(false);
			updateApprovalDisbursementForm(loan_offer.getApplicationId());
		}
		
		if(user.getUserGroup().equalsIgnoreCase("Credit Officer") || user.getUserGroup().equalsIgnoreCase("Head of Credit") || user.getUserGroup().equalsIgnoreCase("System Administrator")) {
			docChecklistTable.setEnabled(true);
			ADChecklistTable.setEnabled(true);
			
		}else{
			disableComponents();
			docChecklistTable.setEnabled(false);
			ADChecklistTable.setEnabled(false);
		}
		
		
		updateTableColumns();
		
		
	}
	
	public void updateTableColumns() {
		TableColumn ADColumn = ADChecklistTable.getColumnModel().getColumn(2);
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("N/A");
		comboBox.addItem("Yes");
		comboBox.addItem("No");		
		ADColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		TableColumn DCColumn = docChecklistTable.getColumnModel().getColumn(2);
		JComboBox comboBox2 = new JComboBox();
		comboBox2.addItem("N/A");
		comboBox2.addItem("Yes");
		comboBox2.addItem("No");		
		DCColumn.setCellEditor(new DefaultCellEditor(comboBox2));
	}
	
	public void updateApprovalDisbursementForm(String application_id) {
		
		try {
			facility_list = mainInterface.getOutstandingFacilities(application_id);
			updateOutstandingFacilityTable(facility_list);
			
			turnover_list = mainInterface.getAccountTurnovers(application_id);
			updateAccountTurnoverTable(turnover_list);
			
			String collateral_note = "";
					
			if (mainInterface.collateralExists(application_id)) {
				note = mainInterface.getCollateralNotes(application_id);
				collateralArea.setText(note.getCollateral().toUpperCase());
				customerArea.setText(note.getCostumerBackground().toUpperCase());
			}else {				
				security_list = mainInterface.getLoanSecurities(application_id);
				if (security_list.size()<1) {
					collateral_note = "";
				}else {
					collateral_note = security_list.get(0);
					for(int i = 1; i<security_list.size(); i++) {						
						collateral_note = collateral_note + ", " + security_list.get(i);
						collateral_note = collateral_note.toUpperCase();
					}
				}
							
			collateralArea.setText(collateral_note);
			customerArea.setText("");
			
			
			}			
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void fillApplicationForm(LoanApplication application) {
		appIdField.setText(application.getApplicationId());
		appDateField.setText(application.getApplicationDate());
		appStatusField.setText(application.getApplicationStatus());
		moField.setText(application.getMarketingOfficer());
		branchField.setText(application.getBranch());
		nameField.setText(application.getFullName());
		businessField.setText(application.getLoanType());
		tenorField.setText(application.getTenor());
		
		SoRTextArea.setText(application.getSourceOfRepayment());
		purposeArea.setText(application.getPurpose());
		
		appFacilityField.setText(application.getFacilityRecommended() + "");
		amountInWordsArea.setText(application.getFacilityRecommendedInWords());
		
		facilityRecommendedField.setText(application.getFacilityRecommended() + "");
		facilityRecommendedArea.setText(application.getFacilityRecommendedInWords());
		
		facilityAppliedField.setText(application.getLoanFacility() + "");
		facilityAppliedArea.setText(application.getAmountInWords());
		
		
		 
		
	}


	public void fillOfferForm(LoanOfferView loan_offer) {	
		appIdField.setText(loan_offer.getApplicationId());
		appDateField.setText(loan_offer.getApplicationDate());
		appStatusField.setText(loan_offer.getApplicationStatus());
		moField.setText(loan_offer.getMarketingOfficer());
		branchField.setText(loan_offer.getBranch());
		nameField.setText(loan_offer.getFullName());
		businessField.setText(loan_offer.getBusiness());
		tenorField.setText(loan_offer.getTenor());
		
		SoRTextArea.setText(loan_offer.getSor());
		purposeArea.setText(loan_offer.getPurpose());
		
		facilityAppliedField.setText(loan_offer.getFacilityRequested() + "");
		facilityAppliedArea.setText(loan_offer.getAmountInWords());
		
		interestField.setText(loan_offer.getInterestRate()+"");
		monFeeField.setText(loan_offer.getMonitoringFeeRate()+ "");
		mgtFeeField.setText(loan_offer.getManagementFeeRate()+"");
		compSavField.setText(loan_offer.getCompulsorySavingsRate()+"");
		riskPremField.setText(loan_offer.getRiskPremiumRate()+"");		
		
		 appFacilityField.setText(loan_offer.getAmountApproved()+"");
		 appTenorField.setText(loan_offer.getTenorApproved()+"");
		 amountInWordsArea.setText(loan_offer.getAmountInWords());
		 productBox.setSelectedItem(loan_offer.getLoanProduct());
		 interestTypeBox.setSelectedItem(loan_offer.getInterestType());
		 tenorTypeBox.setSelectedItem(loan_offer.getTenorTypeApproved());
		 
		 appFacilityField.setText(loan_offer.getFacilityRecommended() + "");
		 amountInWordsArea.setText(loan_offer.getFacilityRecommendedInWords());
			
		 facilityRecommendedField.setText(loan_offer.getFacilityRecommended() + "");
		 facilityRecommendedArea.setText(loan_offer.getFacilityRecommendedInWords());
		
		
	}


	//client method to update product combo-box
	@SuppressWarnings("unchecked")
	
	public void updateProductBox() {
		
		try {
			product_list = mainInterface.getAllLoanProducts();
			for(int i=0; i<product_list.size(); i++) {
				productBox.addItem(product_list.get(i).getProductDescription());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateProductRates(LoanProduct product) {
		interestField.setText(product.getIntrestRate()+"");
		monFeeField.setText(product.getMonFeeRate()+ "");
		mgtFeeField.setText(product.getMgtFeeRate()+"");
		compSavField.setText(product.getCompSavingsRate()+"");
		riskPremField.setText(product.getRiskPremRate()+"");
		
	}

	
	public String leftPad(String value, int lgt) {
		int diff = lgt - value.length();		
		for(int i=0; i<diff; i++) {
			value = "0" + value;
		}				
		return value;
	} 
	

	public LoanOfferPrice calculateOfferPricing(LoanOffer offer) {
		DecimalFormat fz = new DecimalFormat("##");
		LoanOfferPrice offer_price = new LoanOfferPrice();
		
		double principal = 0;
		double interest = 0;
		double management_fee = 0; 
		double risk_premium = 0; 
		double monitoring_fee = 0; 
		double compulsory_savings = 0; 		
		
		double upfront_interest = 0;
		
		double installment_payment_principal = 0; 
		double installment_payment_interest_charges = 0;  
		double total_installment_repayment = 0; 		
		
		double total_interest_charges  = 0;
		double total_compulsory_savings  = 0;
		double total_repayment  = 0;		
		
		double total_interest  = 0; 
		double total_monitoring_fee  = 0;  
		double total_risk_premium  = 0;
		double gross_total = 0;
		double percentage_gross_total  = 0;
		
		principal = offer.getPrincipal();	
		
		double  tmp_interest = principal * offer.getInterestRate()/100;
		risk_premium = offer.getRiskPremiumRate()/100 * tmp_interest;
		DecimalFormat f2 = new DecimalFormat("##.00");	    
	    risk_premium = Double.parseDouble(f2.format(risk_premium));
		
		monitoring_fee = offer.getMonitoringFeeRate()/100 * tmp_interest;
		
		installment_payment_principal = principal / offer.getTenor();
		
		if(offer.getInterestType().equalsIgnoreCase("Reducing Balance")) {
				   
			double reducing_interest_sum = 0;
			double internal_principal = principal;
			
			double  reducing_interest = 0;
			double installment_principal = internal_principal / offer.getTenor();	
			double annual_interest_rate = Double.parseDouble(fz.format(offer.getInterestRate()*12.0));
			
			
			while(internal_principal>0){
				reducing_interest = internal_principal * (annual_interest_rate/12)/100; 
				
		        reducing_interest_sum = reducing_interest_sum + reducing_interest;
		        internal_principal = internal_principal - installment_principal;
		    }
		    interest = reducing_interest_sum / offer.getTenor();
		   
		    
		    DecimalFormat f5 = new DecimalFormat("##.00");	    
		    interest = Double.parseDouble(f5.format(interest));		  
			
		}else if(offer.getInterestType().equalsIgnoreCase("Flat Rate")){
			interest = principal * offer.getInterestRate() / 100;			
			
		}else if(offer.getInterestType().equalsIgnoreCase("Balloon Payment")) {
			interest = principal * offer.getInterestRate() / 100;
			installment_payment_principal = 0.00;
		}else if(offer.getInterestType().equalsIgnoreCase("Balloon Payment (Upfront Interest)")) {
			interest = principal * offer.getInterestRate() / 100;
			installment_payment_principal = 0.00;
		}
		
			
		management_fee = offer.getManagementFeeRate()/100 * principal;	
		if(offer.getTenorType().equalsIgnoreCase("quarters")) {
			compulsory_savings = offer.getCompulsorySavingsRate() /100 * principal * 3;		
			
			//re-payment schedule table installment payment;	
			installment_payment_interest_charges = (interest + monitoring_fee + risk_premium) * 3;	
			
		}else {
			compulsory_savings = offer.getCompulsorySavingsRate() /100 * principal;		
			
			//re-payment schedule table installment payment;	
			installment_payment_interest_charges = interest + monitoring_fee + risk_premium;	
		}
		
		
		if(offer.getInterestType().equalsIgnoreCase("Balloon Payment (Upfront Interest)")) {
			interest = principal * offer.getInterestRate() / 100;
			installment_payment_principal = 0.00;
			installment_payment_interest_charges = 0.00;
			upfront_interest = total_interest_charges;
			total_interest_charges = 0.00;
		}
		total_installment_repayment = installment_payment_principal + installment_payment_interest_charges + compulsory_savings;
				
		//re-payment schedule table total payment;	
		
		total_interest_charges = installment_payment_interest_charges * offer.getTenor();
		total_compulsory_savings = compulsory_savings * offer.getTenor();

	
		total_repayment = principal + total_interest_charges + total_compulsory_savings;
		
		
			
		//pricing table
		
		total_interest = interest * offer.getTenor();		
		total_monitoring_fee = monitoring_fee * offer.getTenor();
		total_risk_premium = risk_premium * offer.getTenor();
		DecimalFormat f3 = new DecimalFormat("##.00");	    
		total_risk_premium = Double.parseDouble(f3.format(total_risk_premium));
		gross_total = total_interest + management_fee + total_monitoring_fee + total_risk_premium;
		percentage_gross_total = gross_total / principal * 100; 
		
		
		offer_price.setPrincipal(principal);
		offer_price.setInterest(interest);
		offer_price.setManagementFee(management_fee);
		offer_price.setRiskPremium(risk_premium);
		offer_price.setMonitoringFee(monitoring_fee);
		offer_price.setCompulsorySavings(compulsory_savings);
		offer_price.setTempInterest(tmp_interest);
		
		offer_price.setInstallmentPaymentPrincipal(installment_payment_principal);
		offer_price.setInstallmentPaymentInterestCharges(installment_payment_interest_charges);
		offer_price.setTotalInstallmentRepayment(total_installment_repayment);
		
		offer_price.setTotalInterestCharges(total_interest_charges);
		offer_price.setTotalCompulsorySavings(total_compulsory_savings);
		offer_price.setTotalRepayment(total_repayment);
		
		offer_price.setUpfrontInterest(upfront_interest);
		
		offer_price.setTotalInterest(total_interest);
		offer_price.setTotalMonitoringFee(total_monitoring_fee);
		offer_price.setTotalRiskPremium(total_risk_premium);
		offer_price.setGrossTotal(gross_total);
		offer_price.setPercentageGrossTotal(percentage_gross_total);
		
		
		return offer_price;
		
	}

	//client method to update pricing table	
	public void updatePricingTable(LoanOfferPrice offer_price) {
		String tenor_type = "";
		if(saveOffer.getTenorType().equalsIgnoreCase("weeks")) {
			tenor_type = "WEEKLY";
		}else if(saveOffer.getTenorType().equalsIgnoreCase("months")) {
			tenor_type = "MONTHLY";
		}
		lblNewLabel_2.setText(saveOffer.getTenor() +" "+ saveOffer.getTenorType().toUpperCase() + " REPAYMENT"); 
		lblNewLabel_2_1.setText(tenor_type + " REPAYMENT"); 
		
		
		principalLabel_1.setText(formatDouble(offer_price.getInstallmentPaymentPrincipal())+"");		
	
		principalLabel_2.setText(formatDouble(offer_price.getPrincipal())+"");			
		
		intChargesLabel_1.setText(formatDouble(offer_price.getInstallmentPaymentInterestCharges())+"");	
				
		intChargesLabel_2.setText(formatDouble(offer_price.getTotalInterestCharges())+"");	
				
		compSavings_1.setText(formatDouble(offer_price.getCompulsorySavings())+"");			
		
		compSavings_2.setText(formatDouble(offer_price.getTotalCompulsorySavings())+"");	
				
		totalLabel_1.setText(formatDouble(offer_price.getTotalInstallmentRepayment())+"");	
				
		totalLabel_2.setText(formatDouble(offer_price.getTotalRepayment())+"");	
		
		
		ppIntFeeLbl.setText("Interest Rate "+saveOffer.getInterestRate()+"%("+saveOffer.getPrincipal()+"*"+saveOffer.getInterestRate()+"%*"+saveOffer.getTenor()+") "+saveOffer.getInterestType());
		ppMgtFeeLbl.setText("Mgt. Fee of "+ saveOffer.getManagementFeeRate()+"% of "+saveOffer.getPrincipal() + " payable upfront.");
		ppMonFeeLbl.setText("Monitoring fee "+ saveOffer.getMonitoringFeeRate()+"% flat of interest("+ offer_price.getTempInterest()+"*"+saveOffer.getMonitoringFeeRate()+"*"+saveOffer.getTenor()+").");
		ppRiskPremLbl.setText("Risk/Insurance Premium "+saveOffer.getRiskPremiumRate()+"% of interest.");
		ppGrossTotalLbl.setText("Gross Total: ");
		ppGrossTotalPpLbl.setText("% Gross Total: ");
		
		
		ppIntFeeLbl_1.setText(formatDouble(offer_price.getTotalInterest())+"");	
		
		ppMgtFeeLbl_1.setText(formatDouble(offer_price.getManagementFee())+"");		
		
		ppMonFeeLbl_1.setText(formatDouble(offer_price.getTotalMonitoringFee())+"");		
		
		ppRiskPremLbl_1.setText(formatDouble(offer_price.getTotalRiskPremium())+"");		
		
		ppGrossTotalLbl_1.setText(formatDouble(offer_price.getGrossTotal())+"");		
	
		ppGrossTotalPpLbl_1.setText(formatDouble(offer_price.getPercentageGrossTotal())+"");
		
	}
	
	public BigDecimal formatDouble(double figure) {
		DecimalFormat f13 = new DecimalFormat("##.00");
		BigDecimal be5 = new BigDecimal(f13.format(figure));
		return be5;
	}
	
	
	public void disableComponents() {
		btnFinish.setEnabled(false);
		btnPrint.setEnabled(false);
		interestField.setEditable(false);
		monFeeField.setEditable(false);
		mgtFeeField.setEditable(false);
		riskPremField.setEditable(false);				
		compSavField.setEditable(false);
		appFacilityField.setEditable(false);
		appTenorField.setEditable(false);
		amountInWordsArea.setEditable(false);
		productBox.setEnabled(false);
		interestTypeBox.setEnabled(false);
		tenorTypeBox.setEnabled(false);
	}

	public static void deactivateSomeButtons(String rejection_type) {
		btnRejectOffer.setEnabled(false);		
		btnAuditOffer.setEnabled(false);
		appStatusField.setText(rejection_type);
	}

	public void showPopup(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		
		JOptionPane.showMessageDialog(jf, message);
	}
	
	
	//method to update outstanding facilities
	public  void updateOutstandingFacilityTable(ArrayList<OutstandingFacility> list){
		
		Object[][] data = new Object[list.size()][6];
		for(int i=0; i<list.size(); i++){ 
			data[i][0] = (i+1);
			
			data[i][1] = list.get(i).getOutstandingFacility();
			data[i][2] = list.get(i).getLoanAmount();
			data[i][3] = list.get(i).getOutstandingBalance();
			data[i][4] = list.get(i).getExpiryDateString();
			data[i][5] = list.get(i).getSecurity();
			
		}
		
		Object[] columnNames = {"S/No", "Outstanding Facilities", "Amount", "Outstanding", "Expiry Date", "Security"  };
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		outstandingBalanceTable.setModel(model);
		outstandingBalanceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		outstandingBalanceTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		outstandingBalanceTable.getColumnModel().getColumn(1).setPreferredWidth(250);
		outstandingBalanceTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		outstandingBalanceTable.getColumnModel().getColumn(3).setPreferredWidth(90);
		outstandingBalanceTable.getColumnModel().getColumn(4).setPreferredWidth(90);
		outstandingBalanceTable.getColumnModel().getColumn(5).setPreferredWidth(200);
		
			
	}
	
	//method to update Account turnover table	
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
		accountTurnoverTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		accountTurnoverTable.getColumnModel().getColumn(2).setPreferredWidth(120);
		accountTurnoverTable.getColumnModel().getColumn(3).setPreferredWidth(120);
		accountTurnoverTable.getColumnModel().getColumn(4).setPreferredWidth(120);
		accountTurnoverTable.getColumnModel().getColumn(5).setPreferredWidth(120);
					
	}
	
	
	//method to update Account turnover table	
		public void updateADChecklistTable(ArrayList<ApprovalDisbursementParameters> list, int xy){
			
			Object[][] data = new Object[list.size()][4];
			for(int i=0; i<list.size(); i++){ 
				data[i][0] = (i+1);				
				data[i][1] = list.get(i).getParameter();
				if(xy == 1) {
					data[i][2] = "N/A";
				}else {
					data[i][2] = list.get(i).getStatus();
				}
				
				data[i][3] = list.get(i).getRemark();				
			}
			
			Object[] columnNames = {"S/No", "Parameter", "Available", "Remarks" };
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			ADChecklistTable.setModel(model);
			ADChecklistTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			ADChecklistTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			ADChecklistTable.getColumnModel().getColumn(1).setPreferredWidth(450);
			ADChecklistTable.getColumnModel().getColumn(2).setPreferredWidth(120);
			ADChecklistTable.getColumnModel().getColumn(3).setPreferredWidth(200);
							
		}
	
		//method to update Account turnover table	
		public void updateDocChecklistTable(ArrayList<DocumentationParameters> list, int xy){
			
			Object[][] data = new Object[list.size()][4];
			for(int i=0; i<list.size(); i++){ 
				data[i][0] = (i+1);				
				data[i][1] = list.get(i).getParameter();
				if(xy == 1) {
					data[i][2] = "N/A";
				}else {
					data[i][2] = list.get(i).getStatus();
				}
				data[i][3] = list.get(i).getRemark();				
			}
			
			Object[] columnNames = {"S/No", "Parameter", "Available", "Remarks" };
			
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			docChecklistTable.setModel(model);
			docChecklistTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			docChecklistTable.getColumnModel().getColumn(0).setPreferredWidth(50);
			docChecklistTable.getColumnModel().getColumn(1).setPreferredWidth(450);
			docChecklistTable.getColumnModel().getColumn(2).setPreferredWidth(120);
			docChecklistTable.getColumnModel().getColumn(3).setPreferredWidth(200);	
							
		}
}
