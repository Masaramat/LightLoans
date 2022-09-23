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
import com.llp.entities.LoanOfferView;
import com.llp.entities.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoanOfferSearchFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	@SuppressWarnings("rawtypes")
	private JComboBox statusBox;
	private JTextArea textArea;
	private JPanel switch_panel;
	
	ArrayList<LoanOfferView> offer_listxx = new ArrayList<LoanOfferView>();
	
	private LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
	
	CardLayout cLayout = new CardLayout();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanOfferSearchFrame frame = new LoanOfferSearchFrame(new User(), new ArrayList<String>(), "", "");
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
	public LoanOfferSearchFrame(final User user, final ArrayList<String> offer_status_list, final String source, final String print_page) throws RemoteException{
		setTitle("Loan Offer Search ");
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 100, 803, 523);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JLabel lblNewLabel = new JLabel("Search By");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 50, 78, 23);
		contentPane.add(lblNewLabel);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Customer Name", "Account Number", "Credit Officer"}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBox.setBounds(84, 50, 146, 22);
		contentPane.add(comboBox);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String search_criterion = ""; 
					String search_text = textField.getText();
					String offer_status = "";
					
					switch(comboBox.getSelectedItem().toString()) {
					case "Customer Name":
						search_criterion = "customer_name";
						break;
					case "Account Number":
						search_criterion = "account_no";
						break;
					case "Credit Officer":
						search_criterion = "credit_officer";
						break;
				
					default:
						search_criterion = "customer_name";
						break;					
					}
					
					if(search_text.length()<1) {				
						offer_listxx.clear();
						 updateLoanOfferTable(offer_listxx);
					}else {	
						
						 if (source.equalsIgnoreCase("view")) {
								offer_listxx = mainInterface.getLoanOfferView(user, search_criterion, search_text);
								 updateLoanOfferTable(offer_listxx);
						}else {
							 offer_status = statusBox.getSelectedItem().toString();
							offer_listxx = mainInterface.getLoanOfferList(user, search_criterion, search_text, offer_status);
							 updateLoanOfferTable(offer_listxx);
						}
						 
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		textField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField.setColumns(10);
		textField.setBounds(240, 51, 212, 20);
		contentPane.add(textField);
		
		JButton searchButton = new JButton("");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					String search_criterion = ""; 
					String search_text = textField.getText();
					String offer_status = "";
					
					switch(comboBox.getSelectedItem().toString()) {
					case "Customer Name":
						search_criterion = "customer_name";
						break;
					case "Account Number":
						search_criterion = "account_no";
						break;
					case "Credit Officer":
						search_criterion = "credit_officer";
						break;
				
					default:
						search_criterion = "customer_name";
						break;					
					}
					
					if(search_text.length()<1) {				
						
						showPopUp( "Please enter a valid search text.");
					}else {	
						
						 if (source.equalsIgnoreCase("view")) {
								offer_listxx = mainInterface.getLoanOfferView(user, search_criterion, search_text);
								 updateLoanOfferTable(offer_listxx);
						}else {
							 offer_status = statusBox.getSelectedItem().toString();
							offer_listxx = mainInterface.getLoanOfferList(user, search_criterion, search_text, offer_status);
							 updateLoanOfferTable(offer_listxx);
						}
						 
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		searchButton.setIcon(new ImageIcon(LoanOfferSearchFrame.class.getResource("/resources/search04.png")));
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchButton.setBounds(476, 50, 47, 28);
		contentPane.add(searchButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 129, 754, 194);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					int xy = table.getSelectedRow();
					if(offer_listxx.get(xy).getApplicationStatus().equalsIgnoreCase("rejected") || offer_listxx.get(xy).getApplicationStatus().equalsIgnoreCase("offered")) {
						textArea.setText(mainInterface.getRejectionNote(offer_listxx.get(xy).getApplicationId()));
					}else {
						textArea.setText("");
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_2 = new JLabel("Loan Status");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(10, 89, 78, 22);
		contentPane.add(lblNewLabel_2);
		
		statusBox = new JComboBox();
		statusBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				 try {
					 offer_listxx = mainInterface.getLoanOfferList(user, statusBox.getSelectedItem().toString());
					 updateLoanOfferTable(offer_listxx);
					 
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		for(int i=0; i<offer_status_list.size(); i++) {
			statusBox.addItem(offer_status_list.get(i));
		}
		statusBox.setBounds(84, 89, 146, 22);
		contentPane.add(statusBox);
		
		JLabel lblNewLabel_1 = new JLabel("Loan Offers");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 11, 754, 28);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 347, 754, 77);
		contentPane.add(scrollPane_1);
		
		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		
		switch_panel = new JPanel();
		switch_panel.setLayout(cLayout);
		switch_panel.setBounds(332, 435, 432, 48);
		contentPane.add(switch_panel);
		
		JPanel panel_one = new JPanel();
		switch_panel.add(panel_one, "panel_one");
		panel_one.setLayout(null);
		
		JButton btnOpenOffer = new JButton("Open Offer");
		btnOpenOffer.setBounds(222, 11, 89, 23);
		panel_one.add(btnOpenOffer);
		btnOpenOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int xy = table.getSelectedRow();
				if(xy<0) {
					showPopUp("No item selected.");
					
				}else {
										
					try {
						if(source.equalsIgnoreCase("view")) {
							LoanOfferFrame frame = new LoanOfferFrame(user, "view", new LoanApplication(), offer_listxx.get(xy));
							frame.setVisible(true);
							dispose();
						}else {
							LoanOfferFrame frame = new LoanOfferFrame(user, "edit", new LoanApplication(), offer_listxx.get(xy));
							frame.setVisible(true);
							dispose();
						}
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
				
			}
		});
		btnOpenOffer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton cancelButton = new JButton("Close");
		cancelButton.setBounds(321, 11, 101, 23);
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
		btnRefresh.setBounds(123, 11, 89, 23);
		panel_one.add(btnRefresh);
		
		JPanel panel_two = new JPanel();
		switch_panel.add(panel_two, "panel_two");
		panel_two.setLayout(null);
		
		JButton printButton = new JButton("Print");
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int xy = table.getSelectedRow();			
				if (xy<0) {
					showPopUp("No item selected.");
				}else {
					if(print_page.equalsIgnoreCase("offer")) {
						OfferLetterFrame frame = new OfferLetterFrame(offer_listxx.get(xy).getApplicationId());
						frame.setVisible(true);
					}else if(print_page.equalsIgnoreCase("approval")) {
						ApprovalDisbursementShowFrame frame = new ApprovalDisbursementShowFrame(offer_listxx.get(xy).getApplicationId());
						frame.setVisible(true);
					}
					
				}
			}
		});
		printButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		printButton.setBounds(216, 11, 89, 23);
		panel_two.add(printButton);
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closeButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		closeButton.setBounds(319, 11, 89, 23);
		panel_two.add(closeButton);
		
		
		
		JButton btnRefresh_1 = new JButton("Refresh");
		btnRefresh_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRefresh_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshTable(source, user);
			}
		});
		btnRefresh_1.setBounds(117, 11, 89, 23);
		panel_two.add(btnRefresh_1);	 
		
		refreshTable(source, user);
	}
	
	//client method to refresh table
	public void refreshTable(String source, User user) {
		 
		 try {
			 if (source.equalsIgnoreCase("print")) {
				 offer_listxx = mainInterface.getLoanOfferList(user, statusBox.getSelectedItem().toString());
				 updateLoanOfferTable(offer_listxx); 
					cLayout.show(switch_panel, "panel_two");			
			}else if (source.equalsIgnoreCase("view")) {
					offer_listxx = mainInterface.getLoanOfferView(user, "customer_name", "123456789");
					 updateLoanOfferTable(offer_listxx);
					 statusBox.setEnabled(false);
			}else {
				offer_listxx = mainInterface.getLoanOfferList(user, statusBox.getSelectedItem().toString());
				 updateLoanOfferTable(offer_listxx);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//client method to update loan offer search table
	public void updateLoanOfferTable(ArrayList<LoanOfferView> list) {
		Object[][] data = new Object[list.size()][11];
				
		for(int i=0; i<list.size(); i++) {
			
			
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getApplicationId();
			data[i][2] = list.get(i).getAccountNo();
			data[i][3] = list.get(i).getFullName();
			data[i][6] = list.get(i).getApplicationDate();
			data[i][10] = list.get(i).getBranch();
			data[i][4] = list.get(i).getAmountApproved();
			data[i][5] = list.get(i).getTenor();
			data[i][8] = list.get(i).getSearchStatus();
			
			data[i][9] = list.get(i).getCreditOfficer();
			if(list.get(i).getApplicationStatus().equalsIgnoreCase("forwarded") || list.get(i).getApplicationStatus().equalsIgnoreCase("offered")) {
				data[i][7] = "In-process";
			}else {
				data[i][7] = list.get(i).getApplicationStatus();
			}
					
		}
		
		Object[] columnNames = {"S/No", "Application ID", "Account Number", "Customer Name", "Loan Facility", "Tenor",  "Application Date", "Application Status", "Credit Search", "Credit Officer",  "Branch"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(160);
		table.getColumnModel().getColumn(4).setPreferredWidth(120);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(120);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(120);
		table.getColumnModel().getColumn(9).setPreferredWidth(100);
					
	}
	
	public void showPopUp(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}


	












}
