package com.llp.main;


import java.awt.CardLayout;
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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoanApplicationSearchFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private JTextArea textArea;
	private JPanel switch_panel;
	private JButton searchButton;
	
	private ArrayList<LoanApplication> application_list = new ArrayList<>();
	
	private LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
	
	CardLayout cLayout = new CardLayout();
	
	NumberFormat nf = NumberFormat.getCurrencyInstance();

	/**
	 * Launch the application.
	 */
	@SuppressWarnings({ "unused"} )
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanApplicationSearchFrame frame = new LoanApplicationSearchFrame(new User(), "");
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
	public LoanApplicationSearchFrame(final User user, final String source) throws RemoteException{
		setAlwaysOnTop(true);
		setTitle("Loan Application Search\r\n");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 100, 778, 533);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
			 
		DecimalFormatSymbols dfs = ((DecimalFormat) nf).getDecimalFormatSymbols();
		dfs.setCurrencySymbol("");
		((DecimalFormat) nf).setDecimalFormatSymbols(dfs);
		
		JLabel lblNewLabel = new JLabel("Search By");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 29, 78, 23);
		contentPane.add(lblNewLabel);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Customer Name", "Account Number", "Marketing Officer"}));
		comboBox.setBounds(75, 29, 114, 22);
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
					case "Marketing Officer":
						search_criterion = "marketing_officer";
						break;
				
					default:
						search_criterion = "customer_name";
						break;					
					}
					
					if(search_text.length()<1) {
					
						application_list.clear();
						updateLoanApplicationTable(application_list);
					}else {
						if (source.equalsIgnoreCase("view")) {
							application_list = mainInterface.getApplicationsView(user, search_criterion, search_text);
							updateLoanApplicationTable(application_list);						
						}else {
							application_list = mainInterface.getApplicationList(user, search_criterion, search_text);
							updateLoanApplicationTable(application_list);
						}
						
						
						
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		textField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField.setBounds(199, 30, 173, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		searchButton = new JButton("");
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
					case "Marketing Officer":
						search_criterion = "marketing_officer";
						break;
				
					default:
						search_criterion = "customer_name";
						break;					
					}
					
					if(search_text.length()<1) {
					
						showPopup("Please enter a valid search text.");
					}else {
						if (source.equalsIgnoreCase("view")) {
							application_list = mainInterface.getApplicationsView(user, search_criterion, search_text);
							updateLoanApplicationTable(application_list);						
						}else {
							application_list = mainInterface.getApplicationList(user, search_criterion, search_text);
							updateLoanApplicationTable(application_list);
						}
						
						
						
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		searchButton.setIcon(new ImageIcon(LoanApplicationSearchFrame.class.getResource("/resources/search04.png")));
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchButton.setBounds(382, 29, 47, 28);
		contentPane.add(searchButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 76, 742, 232);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					int xy = table.getSelectedRow();
					if(application_list.get(xy).getApplicationStatus().equalsIgnoreCase("returned")) {
						textArea.setText(mainInterface.getRejectionNote(application_list.get(xy).getApplicationId()));
					}else {
						textArea.setText("");
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		scrollPane.setViewportView(table);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(10, 337, 742, 88);
		contentPane.add(textArea);
		
		switch_panel = new JPanel();
		switch_panel.setBounds(286, 447, 466, 36);
		switch_panel.setLayout(cLayout);
		contentPane.add(switch_panel);
		
		JPanel panel_one = new JPanel();
		switch_panel.add(panel_one, "panel_one");
		panel_one.setLayout(null);
		
		JButton btnNewButton = new JButton("Open Application");
		btnNewButton.setBounds(202, 11, 155, 23);
		panel_one.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					int xy = table.getSelectedRow();				
					
					if(xy>-1 && source.equalsIgnoreCase("edit")) {
						LoanApplicationFrame frame = new LoanApplicationFrame(user, "edit", application_list.get(xy), mainInterface.getLoanSecurities(application_list.get(xy).getApplicationId()));
						frame.setVisible(true);
						dispose();
					}else if(xy>-1 && source.equalsIgnoreCase("returned")) {
						LoanApplicationFrame frame = new LoanApplicationFrame(user, "edit", application_list.get(xy), mainInterface.getLoanSecurities(application_list.get(xy).getApplicationId()));
						frame.setVisible(true);
						dispose();
					}else if(xy>-1 && source.equalsIgnoreCase("view")) {
						LoanApplicationFrame frame = new LoanApplicationFrame(user, "view", application_list.get(xy), mainInterface.getLoanSecurities(application_list.get(xy).getApplicationId()));
						frame.setVisible(true);
						dispose();
					}
					
					else {
						showPopup("No item selected");
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(367, 11, 89, 23);
		panel_one.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable(source, user);
			}
		});
		btnRefresh.setBounds(103, 11, 89, 23);
		panel_one.add(btnRefresh);
		
		JPanel panel_two = new JPanel();
		switch_panel.add(panel_two, "panel_two");
		panel_two.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Print");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int xy = table.getSelectedRow();
				if (xy<0) {
					showPopup("No item selected.");
				}else {
					LoanApplicationLetterFrame frame = new LoanApplicationLetterFrame(application_list.get(xy).getApplicationId());
					frame.setVisible(true);
				}
			}
		});
		btnNewButton_1.setBounds(268, 11, 89, 23);
		panel_two.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Close");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_2.setBounds(367, 11, 89, 23);
		panel_two.add(btnNewButton_2);
		
		JButton btnRefresh_1 = new JButton("Refresh");
		btnRefresh_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRefresh_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				refreshTable(source, user);				
			}
		});
		btnRefresh_1.setBounds(169, 11, 89, 23);
		panel_two.add(btnRefresh_1);
		
		refreshTable(source, user);
				
	}	
	
	//client method to refresh table
	public void refreshTable(String source, User user) {
		
		try {
			if (source.equalsIgnoreCase("print")) {
				application_list = mainInterface.getPrintableApplications(user);
				updateLoanApplicationTable(application_list);
				searchButton.setEnabled(false);
				cLayout.show(switch_panel, "panel_two");
				
			}else if (source.equalsIgnoreCase("returned")) {
				application_list = mainInterface.getReturnedApplications(user);
				updateLoanApplicationTable(application_list);
				searchButton.setEnabled(false);
				cLayout.show(switch_panel, "panel_one");
			
			}else if (source.equalsIgnoreCase("view")) {
				application_list = mainInterface.getApplicationsView(user, "customer_name", "123456789");
				updateLoanApplicationTable(application_list);
				searchButton.setEnabled(true);
				cLayout.show(switch_panel, "panel_one");
			}
						
			else {
				application_list = mainInterface.getApplicationList(user);
				updateLoanApplicationTable(application_list);
				cLayout.show(switch_panel, "panel_one");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//client method to update loan application search table
	public void updateLoanApplicationTable(ArrayList<LoanApplication> list) {
		Object[][] data = new Object[list.size()][11];
				
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).getApplicationStatus().equalsIgnoreCase("forwarded") || list.get(i).getApplicationStatus().equalsIgnoreCase("offered")) {
				list.get(i).setApplicationStatus("In-process");
			}
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getApplicationId();
			data[i][2] = list.get(i).getAccountNumber();
			data[i][3] = list.get(i).getFullName();			
			data[i][4] = nf.format(new BigDecimal(list.get(i).getLoanFacility()));
			data[i][5] = list.get(i).getTenor();
			data[i][6] = list.get(i).getApplicationDate();
			
			data[i][7] = list.get(i).getApplicationStatus();
			data[i][8] = list.get(i).getSearchStatus();	
			data[i][9] = list.get(i).getMarketingOfficer();
			data[i][10] = list.get(i).getBranch();
		}
		
		Object[] columnNames = {"S/No", "Application ID", "Account Number", "Customer Name", "Loan Facility", "Tenor", "Application Date", "Application Status", "Credit Search", "Marketing officer", "Branch"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(160);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(120);
		table.getColumnModel().getColumn(9).setPreferredWidth(140);
		table.getColumnModel().getColumn(10).setPreferredWidth(120);
					
	}
	
	
	public void showPopup(String message) {
		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		
		JOptionPane.showMessageDialog(jFrame, message);
	}
}
