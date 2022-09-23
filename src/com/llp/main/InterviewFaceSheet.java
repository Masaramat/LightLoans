package com.llp.main;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPMainInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.BusinessCategory;
import com.llp.entities.Customer;
import com.llp.entities.FinanceItem;
import com.llp.entities.FinancialStatement;
import com.llp.entities.InterviewSession;
import com.llp.entities.MarketingQuestion;
import com.llp.entities.QuestionAnswer;
import com.llp.entities.User;
import com.llp.setup.FinancialStatementConfiguration;
import com.llp.setup.MarketingQuestionsFrame;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;

public class InterviewFaceSheet extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JTextField accountNoField;
	private static JTextField nameField;
	private static JTextField dobField;
	private static JTable table;
	@SuppressWarnings("rawtypes")
	private  static JComboBox comboBox_1 ;
	@SuppressWarnings("rawtypes")
	private static JComboBox comboBox;
	@SuppressWarnings("rawtypes")
	private static JComboBox itemBox;
	@SuppressWarnings("rawtypes")
	private static JComboBox comboBox_2;
	private final JTextArea textArea_1;
	private JLabel lblNewLabel_5;
	
	private final JButton saveButton; 
	private final JButton saveButton_1;
	private final JButton updateButton;
	private final JButton updateButton_1;
	
	private final JButton refreshButton;
	private final JButton refreshButton_1;	
	private final JButton clearButton_1;
	private final JButton setupButton;
	
	
	
	private static JTable table_1;
	private JTextField amountField;
	private static JTable incomeTable;
	private static JTable expensesTable;
	private static JTextField incomeField;
	private static JTextField expenseField;
	private static JTextField balanceField;
	
	
	private static ArrayList<InterviewSession> interview_list = new ArrayList<InterviewSession>();
	private ArrayList<BusinessCategory> category_list = null;
	private static ArrayList<MarketingQuestion> question_list = null;
	private ArrayList<QuestionAnswer> qa_list = null;
	private static ArrayList<FinanceItem> item_list = null;
	private ArrayList<FinancialStatement> income_lists = new ArrayList<>();
	private ArrayList<FinancialStatement> expense_lists = new ArrayList<>();
	private static Customer customer1 = null;
	private static LLPMainInterface mainInterface;
	
	
	double final_income=0;
	double final_expenses=0;
	double final_balance=0;
	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterviewFaceSheet frame = new InterviewFaceSheet(new User(), new Customer());
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
	public InterviewFaceSheet(final User user, Customer customer) throws RemoteException{
		setIconImage(Toolkit.getDefaultToolkit().getImage(InterviewFaceSheet.class.getResource("/resources/logo_1.png")));
		setAlwaysOnTop(true);
		setTitle("Customer Assessment Form");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(250, 20, 928, 647);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 mainInterface = InterfaceGenerator.getMainInterface();
		
		category_list = mainInterface.getAllBusinessCategories();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 210, 890, 347);
		contentPane.add(tabbedPane);
		
		
		
		JPanel question_tab = new JPanel();
		tabbedPane.addTab("Question & Answer ", null, question_tab, null);
		question_tab.setLayout(null);
		comboBox = new JComboBox();
		comboBox.setBounds(119, 25, 199, 22);
		question_tab.add(comboBox);
		
		for(int i=0; i<category_list.size(); i++) {
			comboBox.addItem(category_list.get(i).getCategoryDecription());
		}
		
		JButton btnNewButton_1 = new JButton("Setup");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				try {
					MarketingQuestionsFrame frame = new MarketingQuestionsFrame("ifs");
					frame.setAlwaysOnTop(true);
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(718, 161, 138, 22);
		question_tab.add(btnNewButton_1);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 158, 679, 142);
		question_tab.add(scrollPane_2);
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xy = table_1.getSelectedRow();
				comboBox_1.setSelectedItem(qa_list.get(xy).getQuestion().toString());
				textArea_1.setText(qa_list.get(xy).getAnswer());
				saveButton.setEnabled(false);
				updateButton.setEnabled(true);
			}
		});
		scrollPane_2.setViewportView(table_1);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String question_value = comboBox_1.getSelectedItem().toString();
					String answer = textArea_1.getText();
					
					if(question_value.length()<1) {
						showPopUp("Please select a question.");
					}else if(answer.length()<1) {
						showPopUp( "Please type an answer for the question.");
					}
					else {
						
						int xy = table.getSelectedRow();
						if(xy<0) {
							showPopUp("Please select an interview session.");
						}else {
							mainInterface.saveQuestionAndAnswer(interview_list.get(xy).getSessionId(), question_value, answer);
							//showPopUp( "Success!");
							qa_list = mainInterface.getQuestionAnswers(interview_list.get(xy).getSessionId());				
							updateQuestionAnswerTable(qa_list);
							textArea_1.setText("");
						}					
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		saveButton.setBounds(718, 25, 138, 23);
		question_tab.add(saveButton);
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String question_value = comboBox_1.getSelectedItem().toString();
					String answer = textArea_1.getText();
					
					if(question_value.length()<1) {
						showPopUp("Please select a question.");
					}else if(answer.length()<1) {
						showPopUp("Please type an answer for the question.");
					}
					else {	
						int xx = table.getSelectedRow();
						int xy = table_1.getSelectedRow();
						if(xy <0) {
							showPopUp( "No selection made!");
						}else {
							mainInterface.updateQuestionAnswer(qa_list.get(xy).getSerialNumber(), question_value, answer);
							showPopUp("Success!");
							qa_list = mainInterface.getQuestionAnswers(interview_list.get(xx).getSessionId());				
							updateQuestionAnswerTable(qa_list);
							textArea_1.setText("");
							comboBox_1.setSelectedIndex(0);
							saveButton.setEnabled(true);
							updateButton.setEnabled(false);
						}
											
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});
		updateButton.setBounds(718, 59, 138, 23);
		question_tab.add(updateButton);
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateButton.setEnabled(false);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_1.clearSelection();
				textArea_1.setText("");
				comboBox.setSelectedIndex(0);
				comboBox_1.setSelectedIndex(0);
				saveButton.setEnabled(true);
				updateButton.setEnabled(false);
			}
		});
		clearButton.setBounds(718, 93, 138, 23);
		question_tab.add(clearButton);
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		refreshButton = new JButton("Refresh");   
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateQuestionBox();
			}
		});
		refreshButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		refreshButton.setBounds(718, 127, 138, 23);
		question_tab.add(refreshButton);
		
		JLabel lblNewLabel_2 = new JLabel("Business Category");
		lblNewLabel_2.setBounds(10, 26, 99, 21);
		question_tab.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblQeustion = new JLabel("Question");
		lblQeustion.setBounds(10, 58, 99, 21);
		question_tab.add(lblQeustion);
		lblQeustion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblAnswer = new JLabel("Answer");
		lblAnswer.setBounds(10, 90, 99, 21);
		question_tab.add(lblAnswer);
		lblAnswer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_1.setText("");
			}
		});
		comboBox_1.setBounds(119, 58, 519, 21);
		question_tab.add(comboBox_1);
		
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(119, 95, 417, 43);
		question_tab.add(scrollPane_1);
		
		textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				updateQuestionBox();
			}
		});
		
		JPanel financial_tab = new JPanel();
		tabbedPane.addTab("Financial Assessment", null, financial_tab, null);
		financial_tab.setLayout(null);

		
		
		
		saveButton_1 = new JButton("Save");
		saveButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double amount = 0.00;
				try {
					amount = Double.parseDouble(amountField.getText());
					String finance_type = comboBox_2.getSelectedItem().toString();
					String item = itemBox.getSelectedItem().toString();
					int xy = table.getSelectedRow();
					if(xy<0) {
						showPopUp("Please select an interview session.");
					}else {
						
						if(amountField.getText().length()<1) {
							showPopUp("Please enter a valid amount.");
						}else {
							String session_id = interview_list.get(xy).getSessionId();
							mainInterface.createFinancialStatement(session_id, finance_type, item, amount);
							//JOptionPane.showMessageDialog(null, "Success!");
							amountField.setText("");
							
							 income_lists = mainInterface.getInterviewIncome(interview_list.get(xy).getSessionId());
							 updateIncomeTable(income_lists);
							 				 
							 expense_lists = mainInterface.getInterviewExpenses(interview_list.get(xy).getSessionId());
							 updateExpensesTable(expense_lists);
							 
							 updateBalanceSheet();
						}
						
						
						
						
					}
				}catch(Exception ex) {
					//showPopUp("Please enter a valid amount.");
				}
				
				
			}
		});
		saveButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveButton_1.setBounds(718, 25, 138, 23);
		financial_tab.add(saveButton_1);
		
		updateButton_1 = new JButton("Update");
		updateButton_1.setEnabled(false);
		updateButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int xy = -1;
					int sno = -1;
					if(expensesTable.getSelectionModel().isSelectionEmpty()) {
						xy = incomeTable.getSelectedRow();
						sno = income_lists.get(xy).getSno();
					}else if(incomeTable.getSelectionModel().isSelectionEmpty()) {
						xy = expensesTable.getSelectedRow();
						sno = expense_lists.get(xy).getSno();
					}
					mainInterface.updateFinancialStatement(sno, comboBox_2.getSelectedItem().toString(), itemBox.getSelectedItem().toString(), Double.parseDouble(amountField.getText()));
					showPopUp("Update Successful.");
					amountField.setText("");
					int xxy = table.getSelectedRow();
					 income_lists = mainInterface.getInterviewIncome(interview_list.get(xxy).getSessionId());
					 updateIncomeTable(income_lists);
					 				 
					 expense_lists = mainInterface.getInterviewExpenses(interview_list.get(xxy).getSessionId());
					 updateExpensesTable(expense_lists);
					 
					 updateBalanceSheet();
					 saveButton_1.setEnabled(true);
					 updateButton_1.setEnabled(false);
					 expensesTable.clearSelection();
					 incomeTable.clearSelection();
					
				}catch(Exception e2){
					e2.printStackTrace();
				}
				
			}
			
			
		});
		updateButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateButton_1.setBounds(718, 59, 138, 23);
		financial_tab.add(updateButton_1);
		
		clearButton_1 = new JButton("Clear"); 
		clearButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				amountField.setText("");
				itemBox.setSelectedIndex(0);
				updateButton_1.setEnabled(false);
				saveButton_1.setEnabled(true);
			}
		});
		clearButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		clearButton_1.setBounds(718, 93, 138, 23);
		financial_tab.add(clearButton_1);
		
		
		
		setupButton = new JButton("Setup");
		setupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					FinancialStatementConfiguration frame = new FinancialStatementConfiguration("af");
					frame.setVisible(true);
				}catch (Exception e3) {
					e3.printStackTrace();
				}
				
			}
		});
		setupButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		setupButton.setBounds(718, 161, 138, 23);
		financial_tab.add(setupButton);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Summary of Income", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 127, 331, 151);
		financial_tab.add(panel_3);
		panel_3.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 23, 306, 117);
		panel_3.add(scrollPane_3);
		
		incomeTable = new JTable();
		incomeTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				expensesTable.clearSelection();
				int xy = incomeTable.getSelectedRow();
				comboBox_2.setSelectedItem(income_lists.get(xy).getFinanceType());
				updateItemBox();
				
				itemBox.setSelectedItem(income_lists.get(xy).getItem());
				amountField.setText(income_lists.get(xy).getAmount()+ "");
				saveButton_1.setEnabled(false);
				updateButton_1.setEnabled(true);
				
			}
		});
		scrollPane_3.setViewportView(incomeTable);
		
		JPanel panel_3_1 = new JPanel();
		panel_3_1.setBorder(new TitledBorder(null, "Summary of Expenses", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3_1.setBounds(363, 127, 326, 151);
		financial_tab.add(panel_3_1);
		panel_3_1.setLayout(null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 23, 306, 117);
		panel_3_1.add(scrollPane_4);
		
		expensesTable = new JTable();
		expensesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				incomeTable.clearSelection();
				int xy = expensesTable.getSelectedRow();
				comboBox_2.setSelectedItem(expense_lists.get(xy).getFinanceType());
				updateItemBox();
				
				itemBox.setSelectedItem(expense_lists.get(xy).getItem());
				amountField.setText(expense_lists.get(xy).getAmount()+ "");
				saveButton_1.setEnabled(false);
				updateButton_1.setEnabled(true);
			}
		});
		scrollPane_4.setViewportView(expensesTable);
		
		JLabel lblNewLabel_4 = new JLabel("Total Income");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(10, 289, 91, 22);
		financial_tab.add(lblNewLabel_4);
		
		incomeField = new JTextField();  
		incomeField.setFont(new Font("Tahoma", Font.BOLD, 12));
		incomeField.setForeground(Color.BLACK);
		incomeField.setEditable(false);
		incomeField.setBounds(90, 290, 91, 20);
		financial_tab.add(incomeField);
		incomeField.setColumns(10);
		
		JLabel lblNewLabel_4_1 = new JLabel("Total Expenses");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4_1.setBounds(223, 289, 91, 22);
		financial_tab.add(lblNewLabel_4_1);
		
		expenseField = new JTextField();
		expenseField.setFont(new Font("Tahoma", Font.BOLD, 12));
		expenseField.setForeground(Color.BLACK);
		expenseField.setEditable(false);
		expenseField.setColumns(10);
		expenseField.setBounds(309, 290, 86, 20);
		financial_tab.add(expenseField);
		
		JLabel lblNewLabel_4_2 = new JLabel("Balance");
		lblNewLabel_4_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4_2.setForeground(Color.BLACK);
		lblNewLabel_4_2.setBounds(444, 289, 62, 22);
		financial_tab.add(lblNewLabel_4_2);
		
		balanceField = new JTextField();
		balanceField.setFont(new Font("Tahoma", Font.BOLD, 12));
		balanceField.setEditable(false);
		balanceField.setColumns(10);
		balanceField.setBounds(501, 289, 91, 20);
		financial_tab.add(balanceField);
		
		JLabel lblNewLabel_3 = new JLabel("Finance type");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(10, 25, 96, 21);
		financial_tab.add(lblNewLabel_3);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(90, 25, 165, 21);
		financial_tab.add(comboBox_2);
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateItemBox();
			}
		});
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Income", "Expenses"}));
		
		refreshButton_1 = new JButton("Refresh");
		refreshButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateItemBox();
			}
		});
		refreshButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		refreshButton_1.setBounds(718, 127, 138, 23);
		financial_tab.add(refreshButton_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("Item");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3_1.setBounds(10, 57, 79, 21);
		financial_tab.add(lblNewLabel_3_1);
		
		
		itemBox = new JComboBox();
		itemBox.setBounds(90, 57, 372, 22);
		financial_tab.add(itemBox);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Amount");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3_1_1.setBounds(10, 89, 79, 21);
		financial_tab.add(lblNewLabel_3_1_1);
		
		amountField = new JTextField();
		amountField.setBounds(90, 89, 129, 21);
		financial_tab.add(amountField);
		amountField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(amountField.getText().length()<1) {
					lblNewLabel_5.setText("");
				}else {					
					try {
						@SuppressWarnings("unused")
						long number = Long.parseLong(amountField.getText());
						
						}catch(Exception ex) {							
							amountField.setText("");
							lblNewLabel_5.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		amountField.setColumns(10);
		
		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setForeground(Color.RED);
		lblNewLabel_5.setBounds(223, 93, 272, 14);
		financial_tab.add(lblNewLabel_5);
		
		JButton newSessionButton = new JButton("New Interview Session");
		newSessionButton.setBounds(491, 577, 151, 23);
		contentPane.add(newSessionButton);
		newSessionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewInterviewFrame nif;
				try {
					nif = new NewInterviewFrame(user, customer1, "ifs");
					nif.setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		newSessionButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton endInterviewButton = new JButton("End Interview Session");
		endInterviewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					int xy = table.getSelectedRow();			
					
					if(xy < 0) {
						showPopUp("Please select a record from interview table");
					}
					else {
						String interview_id = interview_list.get(xy).getSessionId();
						String interview_status = interview_list.get(xy).getInterviewStatus();
						if(interview_status.equalsIgnoreCase("close")) {
							showPopUp("The interview session is closed");
						}
						else {
							mainInterface.endInterviewSession(interview_id);
							showPopUp( "Session closed successfully");
							interview_list = mainInterface.getCustomerInterviews(accountNoField.getText());
							updateInterviewsTable(interview_list);
																	
						}
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		endInterviewButton.setBounds(652, 577, 151, 23);
		contentPane.add(endInterviewButton);
		endInterviewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			dispose();
			}
		});
		closeButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		closeButton.setBounds(813, 577, 89, 23);
		contentPane.add(closeButton);
		
		JLabel errorLabel = new JLabel("Please select an interview session from the table above");
		errorLabel.setForeground(new Color(65, 105, 225));
		errorLabel.setBounds(579, 185, 321, 14);
		contentPane.add(errorLabel);		
		
		JLabel lblNewLabel = new JLabel("Account Number");
		lblNewLabel.setBounds(10, 24, 89, 20);
		contentPane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		accountNoField = new JTextField();  
		accountNoField.setBounds(109, 24, 154, 20);
		contentPane.add(accountNoField);
		accountNoField.setEditable(false);
		accountNoField.setColumns(10);
		
		JButton searchButton = new JButton("");
		searchButton.setBounds(271, 24, 47, 28);
		contentPane.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSearchFrame frame;
				try {
					frame = new CustomerSearchFrame("face-sheet");
					frame.setVisible(true);
				} catch (Exception e3) {
					e3.printStackTrace();
				}
				
			}
		});
		searchButton.setIcon(new ImageIcon(InterviewFaceSheet.class.getResource("/resources/search04.png")));
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNewLabel_1 = new JLabel("Customer Name ");
		lblNewLabel_1.setBounds(379, 24, 100, 20);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		nameField = new JTextField();
		nameField.setBounds(481, 24, 207, 20);
		contentPane.add(nameField);
		nameField.setEditable(false);
		nameField.setColumns(10);
		
		JLabel lblDateOfBirth = new JLabel("Date of Birth");
		lblDateOfBirth.setBounds(727, 24, 71, 20);
		contentPane.add(lblDateOfBirth);
		lblDateOfBirth.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		dobField = new JTextField();
		dobField.setBounds(799, 24, 86, 20);
		contentPane.add(dobField);
		dobField.setEditable(false);
		dobField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 69, 890, 108);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				try {
					int xy = table.getSelectedRow();
					if(xy<0) {
						showPopUp("Please select an interview session.");
					}else {
						qa_list = mainInterface.getQuestionAnswers(interview_list.get(xy).getSessionId());
						updateQuestionAnswerTable(qa_list);
						
						 income_lists = mainInterface.getInterviewIncome(interview_list.get(xy).getSessionId());
						 updateIncomeTable(income_lists);
						 				 
						 expense_lists = mainInterface.getInterviewExpenses(interview_list.get(xy).getSessionId());
						 updateExpensesTable(expense_lists);
						 
						 deactivateButtons(interview_list.get(xy).getInterviewStatus());
						 updateBalanceSheet();
						 updateQuestionBox();
						 
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		updateItemBox();
		customer1 = customer;
		updateFaceSheet(customer1);
		
		if(user.getUserGroup().equalsIgnoreCase("Marketing Officer") || user.getUserGroup().equalsIgnoreCase("Head of Marketing")) {
			newSessionButton.setEnabled(true);
			endInterviewButton.setEnabled(true);
		}else {
			newSessionButton.setEnabled(false);
			endInterviewButton.setEnabled(false);
			saveButton.setEnabled(false); 
			saveButton_1.setEnabled(false); 
			updateButton.setEnabled(false); 
			updateButton_1.setEnabled(false);
		}
		
	}
	
	
	
	public static void updateFaceSheet(Customer customer) {
				 
		 try {
			 customer1 = customer;
			 accountNoField.setText(customer1.getLmfbAccountNo());
			 nameField.setText(customer1.getSurname()+" "+ customer.getOthernames()); 
			 dobField.setText(customer1.getDob());
			 
			 interview_list = mainInterface.getCustomerInterviews(customer.getLmfbAccountNo());
			 updateInterviewsTable(interview_list);	
			 
			 updateQuestionAnswerTable(new ArrayList<QuestionAnswer>());
			 updateIncomeTable(new ArrayList<FinancialStatement>());
			 updateExpensesTable(new ArrayList<FinancialStatement>());
			 incomeField.setText(""); 
			 expenseField.setText("");
			 balanceField.setText("");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//client method to update interview table updateInterviewsTable
	public static void updateInterviewsTable(ArrayList<InterviewSession> list) {
		Object[][] data = new Object[list.size()][6];
		
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getSessionId();
			data[i][2] = list.get(i).getInterviewDate();
			data[i][3] = list.get(i).getStaffName();
			data[i][4] = list.get(i).getStaffRole();
			data[i][5] = list.get(i).getInterviewStatus();			
		}
		
		Object[] columnNames = {"S/No", "Session ID", "Interview Date ", "Staff Name", "Staff Role", "Interview Status"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.getColumnModel().getColumn(2).setPreferredWidth(140);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(180);
		table.getColumnModel().getColumn(5).setPreferredWidth(140);
		
					
		
	}
	
	//client Method to update question comboBox
	@SuppressWarnings("unchecked")
	public static void updateQuestionBox() {	
		
		try {
			String category = comboBox.getSelectedItem().toString();
			question_list = mainInterface.getMarketingQuestions("Select", category);
			comboBox_1.removeAllItems();
			for(int i=0; i<question_list.size(); i++) {
				comboBox_1.addItem(question_list.get(i).getQuestion());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// client methods to update interview question and answer table
	public static void updateQuestionAnswerTable(ArrayList<QuestionAnswer> list) {
		Object[][] data = new Object[list.size()][3];
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getQuestion();
			data[i][2] = list.get(i).getAnswer();
			
		}
		
		Object[] columnNames = {"S/No", "Question", "Answer"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table_1.setModel(model);
	
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(70);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(270);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(350);
					
		}

	@SuppressWarnings("unchecked")
	public static void updateItemBox() {	
		
		try {
			String category = comboBox_2.getSelectedItem().toString();
			item_list = mainInterface.getFinanceItems(category);
			itemBox.removeAllItems();
			for(int i=0; i<item_list.size(); i++) {
				itemBox.addItem(item_list.get(i).getItem());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	// client methods to update interview question and answer table
	public static void updateIncomeTable(ArrayList<FinancialStatement> list) {
		Object[][] data = new Object[list.size()][3];
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getItem();
			data[i][2] = list.get(i).getAmount();
			
		}
		
		Object[] columnNames = {"S/No", "Item", "Amount"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		incomeTable.setModel(model);
	
		incomeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		incomeTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		incomeTable.getColumnModel().getColumn(1).setPreferredWidth(170);
		incomeTable.getColumnModel().getColumn(2).setPreferredWidth(80);
					
		}
		
	
	// client methods to update interview question and answer table
	public static void updateExpensesTable(ArrayList<FinancialStatement> list) {
		Object[][] data = new Object[list.size()][3];
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getItem();
			data[i][2] = list.get(i).getAmount();
			
		}
		
		Object[] columnNames = {"S/No", "Item", "Amount"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		expensesTable.setModel(model);
	
		expensesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		expensesTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		expensesTable.getColumnModel().getColumn(1).setPreferredWidth(170);
		expensesTable.getColumnModel().getColumn(2).setPreferredWidth(80);
					
		}
		
	public void updateBalanceSheet() {		
		
		final_balance = 0;
		final_expenses = 0;
		final_income = 0;
		
		for(int i=0; i<income_lists.size(); i++) {
			final_income = final_income + income_lists.get(i).getAmount();
		}
		
		for(int i=0; i<expense_lists.size(); i++) {
			final_expenses = final_expenses + expense_lists.get(i).getAmount();
		}
		
		final_balance = final_income - final_expenses;
		
		incomeField.setText(final_income+"");
		expenseField.setText(final_expenses+"");
		balanceField.setText(final_balance+"");
		if(final_balance <= 0) {
			
			balanceField.setForeground(Color.RED);
			
		}else {
			
			balanceField.setForeground(new Color(60, 179, 113));
		}
		
	}
		
	//client method to deactivate some buttons when session is closed
	public void deactivateButtons(String interview_status ) {
		switch (interview_status){
		case "open":
			saveButton.setEnabled(true);
			saveButton_1.setEnabled(true);
			updateButton.setEnabled(true);
			updateButton_1.setEnabled(true);
			break;
			
		case "close":
			saveButton.setEnabled(false);
			saveButton_1.setEnabled(false);
			updateButton.setEnabled(false);
			updateButton_1.setEnabled(false);
		break;
		
		default:
			saveButton.setEnabled(false);
			saveButton_1.setEnabled(false);
			updateButton.setEnabled(false);
			updateButton_1.setEnabled(false);	
			
		}
		
	}
		
	public void showPopUp(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		
		JOptionPane.showMessageDialog(jf, message);;
	}
	
	
	
	
	
	
}
