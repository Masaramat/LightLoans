package com.llp.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPMainInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.OutstandingFacility;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import com.toedter.calendar.JDateChooser;

import net.sf.jasperreports.engine.part.FinalFillingPrintPart;

import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OutstandingFacilityFrame extends JFrame {

	private JPanel contentPane;
	private JTable outstandingBalanceTable;
	private JTextField outstandingFacility;
	private JTextField amountField;
	private JTextField outstandingAmount;
	
	private JDateChooser dateChooser;
	private JTextArea textArea;
	
	private ArrayList<OutstandingFacility> facility_list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OutstandingFacilityFrame frame = new OutstandingFacilityFrame("");
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
	public OutstandingFacilityFrame(final String application_id) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				LoanOfferFrame.outstandingBalanceTable.requestFocus();
			}
		});
		setAlwaysOnTop(true);
		setTitle("Outstanding Balance ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 596, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
	
		facility_list = new ArrayList<OutstandingFacility>();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 174, 560, 133);
		contentPane.add(scrollPane);
		
		outstandingBalanceTable = new JTable();
		scrollPane.setViewportView(outstandingBalanceTable);
		
		outstandingFacility = new JTextField();
		outstandingFacility.setBounds(129, 54, 109, 20);
		contentPane.add(outstandingFacility);
		outstandingFacility.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Outstanding Facility");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 54, 109, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Amount");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(10, 85, 97, 20);
		contentPane.add(lblNewLabel_1);
		
		amountField = new JTextField();
		amountField.setBounds(129, 85, 109, 20);
		contentPane.add(amountField);
		amountField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Outstanding Amount");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(10, 116, 109, 20);
		contentPane.add(lblNewLabel_2);
		
		outstandingAmount = new JTextField();
		outstandingAmount.setBounds(129, 116, 109, 20);
		contentPane.add(outstandingAmount);
		outstandingAmount.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Expiry Date");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(274, 54, 86, 20);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Security");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(274, 87, 86, 17);
		contentPane.add(lblNewLabel_4);
		
		textArea = new JTextArea();
		textArea.setBounds(351, 85, 207, 75);
		contentPane.add(textArea);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(351, 54, 119, 20);
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setDate(new Date());
		contentPane.add(dateChooser);
		
		JLabel lblNewLabel_5 = new JLabel("Outstanding Balance");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(10, 11, 560, 32);
		contentPane.add(lblNewLabel_5);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				LoanOfferFrame.outstandingBalanceTable.requestFocus();
				dispose();
				
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(481, 322, 89, 23);
		contentPane.add(btnClose);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (amountField.getText().length()<1 || outstandingAmount.getText().length()<1 || outstandingFacility.getText().length()<1 ) {
					showPopup("Please Enter valid values for all fields.");
				}else {
					String date = "";
					try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date d  = dateChooser.getDate();
					date = sdf.format(d);
					}catch(Exception ex) {
						ex.printStackTrace();
					}
					
					try {
						OutstandingFacility facility = new OutstandingFacility();
						facility.setOutstandingFacility(outstandingFacility.getText());
						facility.setOutstandingBalance(Double.parseDouble(outstandingAmount.getText()));
						facility.setLoanAmount(Double.parseDouble(amountField.getText()));
						facility.setExpiryDateString(date);
						facility.setSecurity(textArea.getText());
						
						mainInterface.saveOutstandingFacility(facility, application_id);
						showPopup("success!");
						
						outstandingFacility.setText("");
						outstandingAmount.setText("");
						amountField.setText("");
						textArea.setText("");
						
						facility_list = mainInterface.getOutstandingFacilities(application_id);
						updateOutstandingFacilityTable(facility_list);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAdd.setBounds(282, 322, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (outstandingBalanceTable.getSelectedRow()<0) {
					showPopup("No item selected.");
				}else {
					try {
						mainInterface.deleteOutstandingBalance(facility_list.get(outstandingBalanceTable.getSelectedRow()).getSno());
						showPopup("Success!");
						facility_list = mainInterface.getOutstandingFacilities(application_id);
						updateOutstandingFacilityTable(facility_list);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRemove.setBounds(381, 322, 89, 23);
		contentPane.add(btnRemove);
		try {
			facility_list = mainInterface.getOutstandingFacilities(application_id);
			updateOutstandingFacilityTable(facility_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//method to update outstanding facilities
	public void updateOutstandingFacilityTable(ArrayList<OutstandingFacility> list){
		
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
		outstandingBalanceTable.getColumnModel().getColumn(1).setPreferredWidth(90);
		outstandingBalanceTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		outstandingBalanceTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		outstandingBalanceTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		outstandingBalanceTable.getColumnModel().getColumn(2).setPreferredWidth(90);
		
			
	}
	
	public void showPopup(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		
		JOptionPane.showMessageDialog(jf, message);
	}
	
}
