package com.llp.setup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPSetupInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.LoanProduct;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.awt.Color;

public class LoanProductConfiguration extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private final JButton updateButton;
	private final JButton saveButton;
	
	ArrayList<LoanProduct> product_list = null;
	
	private JTextField interestField; 
	private JTextField monFeeField;
	private JTextField mgtFeeField;
	private JTextField compSavField;
	private JTextField riskPremField;
	
	
	private JLabel Label;
	private JLabel Label_1;
	private JLabel Label_2;
	private JLabel Label_3;
	private JLabel Label_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanProductConfiguration frame = new LoanProductConfiguration();
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
	public LoanProductConfiguration() throws RemoteException{
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Light Loan Processor - Loan Products Configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 100, 724, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
		
		product_list = setupInterface.getAllLoanProducts();
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add / Edit Loan Products", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 688, 225);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Product ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 31, 75, 19);
		panel.add(lblNewLabel);
		
		JLabel lblProductDescription = new JLabel("Product Description");
		lblProductDescription.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProductDescription.setBounds(10, 64, 107, 19);
		panel.add(lblProductDescription);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(118, 30, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(118, 61, 341, 43);
		panel.add(textArea);
		
		JLabel lblInterestRate = new JLabel("Interest Rate (%)");
		lblInterestRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInterestRate.setBounds(10, 124, 107, 19);
		panel.add(lblInterestRate);
		
		JLabel lblMonFeeRate = new JLabel("Mon. Fee Rate (%)");
		lblMonFeeRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMonFeeRate.setBounds(10, 154, 107, 19);
		panel.add(lblMonFeeRate);
		
		JLabel lblMgtFeeRate = new JLabel("Mgt. Fee Rate (%)");
		lblMgtFeeRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMgtFeeRate.setBounds(10, 184, 107, 19);
		panel.add(lblMgtFeeRate);
		
		JLabel lblRiskPremRate = new JLabel("Risk Prem. Rate (%)");
		lblRiskPremRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRiskPremRate.setBounds(342, 124, 119, 19);
		panel.add(lblRiskPremRate);
		
		JLabel lblCompSavingsRate = new JLabel("Comp. Savings Rate (%)");
		lblCompSavingsRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCompSavingsRate.setBounds(342, 154, 119, 19);
		panel.add(lblCompSavingsRate);
		
		interestField = new JTextField();
		interestField.setText("0.0");			
		interestField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(interestField.getText().length()<1) {
					Label.setText("");
				}else {					
					try {
						@SuppressWarnings("unused")
						double number = Double.parseDouble(interestField.getText());
						Label.setText("");
						
						}catch(Exception ex) {							
							interestField.setText("");
							Label.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		interestField.setBounds(118, 123, 55, 20);		
		panel.add(interestField);
		interestField.setColumns(10);
		
		monFeeField = new JTextField();
		monFeeField.setText("0.0");		 	
		monFeeField.setColumns(10);
		monFeeField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(monFeeField.getText().length()<1) {
					Label_1.setText("");
				}else {					
					try {
						@SuppressWarnings("unused")
						double number = Double.parseDouble(monFeeField.getText());
						Label_1.setText("");
						
						}catch(Exception ex) {							
							monFeeField.setText("");
							Label_1.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		monFeeField.setBounds(118, 154, 55, 20);
		panel.add(monFeeField);
		
		mgtFeeField = new JTextField();
		mgtFeeField.setText("0.0");		
		mgtFeeField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(mgtFeeField.getText().length()<1) {
					Label_2.setText("");
				}else {					
					try {
						@SuppressWarnings("unused")
						double number = Double.parseDouble(mgtFeeField.getText());
						Label_2.setText("");
						
						}catch(Exception ex) {							
							mgtFeeField.setText("");
							Label_2.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		mgtFeeField.setBounds(118, 183, 55, 20);
		panel.add(mgtFeeField);
		mgtFeeField.setColumns(10);
		
		compSavField = new JTextField();
		compSavField.setText("0.0");		
		compSavField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(compSavField.getText().length()<1) {
					Label_4.setText("");
				}else {					
					try {
						@SuppressWarnings("unused")
						double number = Double.parseDouble(compSavField.getText());
						Label_4.setText("");
						
						}catch(Exception ex) {							
							compSavField.setText("");
							Label_4.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		compSavField.setBounds(471, 151, 49, 20);
		panel.add(compSavField);
		compSavField.setColumns(10);
		
		riskPremField = new JTextField();
		riskPremField.setText("0.0");	
		riskPremField.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyReleased(KeyEvent e) {
				if(riskPremField.getText().length()<1) {
					Label_3.setText("");
				}else {					
					try {
						@SuppressWarnings("unused")
						double number = Double.parseDouble(riskPremField.getText());
						Label_3.setText("");
						
						}catch(Exception ex) {							
							riskPremField.setText("");
							Label_3.setText("Only Numbers Allowed.");
						}
				}			
			}
		});
		riskPremField.setBounds(471, 121, 49, 20);
		panel.add(riskPremField);
		riskPremField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("*");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(183, 126, 17, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("*");
		lblNewLabel_1_1.setForeground(Color.RED);
		lblNewLabel_1_1.setBounds(183, 156, 17, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("*");
		lblNewLabel_1_2.setForeground(Color.RED);
		lblNewLabel_1_2.setBounds(183, 186, 17, 14);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("*");
		lblNewLabel_1_3.setForeground(Color.RED);
		lblNewLabel_1_3.setBounds(530, 124, 17, 14);
		panel.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("*");
		lblNewLabel_1_3_1.setForeground(Color.RED);
		lblNewLabel_1_3_1.setBounds(530, 154, 17, 14);
		panel.add(lblNewLabel_1_3_1);
		
		Label = new JLabel("");
		Label.setForeground(new Color(255, 0, 0));
		Label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label.setBounds(193, 126, 119, 14);
		panel.add(Label);
		
		Label_1 = new JLabel("");
		Label_1.setForeground(new Color(255, 0, 0));
		Label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label_1.setBounds(193, 156, 119, 14);
		panel.add(Label_1);
		
		Label_2 = new JLabel("");
		Label_2.setForeground(new Color(255, 0, 0));
		Label_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label_2.setBounds(193, 186, 119, 14);
		panel.add(Label_2);
		
		Label_3 = new JLabel("");
		Label_3.setForeground(new Color(255, 0, 0));
		Label_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label_3.setBounds(540, 126, 119, 14);
		panel.add(Label_3);
		
		Label_4 = new JLabel("");
		Label_4.setForeground(new Color(255, 0, 0));
		Label_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		Label_4.setBounds(540, 156, 119, 14);
		panel.add(Label_4);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 247, 688, 161);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xy = table.getSelectedRow();
				textField.setText(product_list.get(xy).getProductId());
				textArea.setText(product_list.get(xy).getProductDescription());
				interestField.setText(product_list.get(xy).getIntrestRate() +"");
				monFeeField.setText(product_list.get(xy).getMonFeeRate()+"");
				mgtFeeField.setText(product_list.get(xy).getMgtFeeRate()+"");
				compSavField.setText(product_list.get(xy).getCompSavingsRate()+"");
				riskPremField.setText(product_list.get(xy).getRiskPremRate()+"");					
				
				updateButton.setEnabled(true);
				saveButton.setEnabled(false);
				
				
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				
				if(textArea.getText().length()<2) {
					showPopUp("Please enter a valid product description.");
				}else if(interestField.getText().length()<1 || monFeeField.getText().length()<1 || mgtFeeField.getText().length()<1 || 
						compSavField.getText().length()<1 || riskPremField.getText().length()<1) {
					showPopUp("All fields marked * cannot be empty.");
				}else {
					LoanProduct product = new LoanProduct();
					product.setProductDescription(textArea.getText());
					product.setIntrestRate(Double.parseDouble(interestField.getText()));
					product.setMonFeeRate(Double.parseDouble(monFeeField.getText()));
					product.setRiskPremRate(Double.parseDouble(riskPremField.getText()));
					product.setMgtFeeRate(Double.parseDouble(mgtFeeField.getText()));
					product.setCompSavingsRate(Double.parseDouble(compSavField.getText()));
					
					
					
					try {
						ArrayList<String> list = setupInterface.createLoanProduct(product);
						if(list.get(1) == null) {
							showPopUp("Success!");
							
							product_list = setupInterface.getAllLoanProducts();						
							updateLoanProductTable(product_list);
							saveButton.setEnabled(false);
							textField.setText(list.get(0));						
						}
						else {
							showPopUp("Unable to add new: "+list.get(1));						
						}	
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
			}
		});
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveButton.setBounds(310, 431, 89, 23);
		contentPane.add(saveButton);
		
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textArea.getText().length()<2) {
					showPopUp("Please enter a valid product description.");					
				}else if(interestField.getText().length()<1 || monFeeField.getText().length()<1 || mgtFeeField.getText().length()<1 || 
						compSavField.getText().length()<1 || riskPremField.getText().length()<1) {
					showPopUp("All fields marked * cannot be empty.");
				}else {
					LoanProduct product = new LoanProduct();
					product.setProductId(textField.getText());
					product.setProductDescription(textArea.getText());
					product.setIntrestRate(Double.parseDouble(interestField.getText()));
					product.setMonFeeRate(Double.parseDouble(monFeeField.getText()));
					product.setRiskPremRate(Double.parseDouble(riskPremField.getText()));
					product.setMgtFeeRate(Double.parseDouble(mgtFeeField.getText()));
					product.setCompSavingsRate(Double.parseDouble(compSavField.getText()));					
					
					try {
						String done = setupInterface.updateLoanProduct(product);
						
						if(done==null) {
							showPopUp("Update Successful!");
							
							product_list = setupInterface.getAllLoanProducts();						
							updateLoanProductTable(product_list);
							textField.setText("");
							textArea.setText("");
							interestField.setText("0.0");
							monFeeField.setText("0.0");
							mgtFeeField.setText("0.0");
							compSavField.setText("0.0");
							riskPremField.setText("0.0");
							
							updateButton.setEnabled(false);
							saveButton.setEnabled(true);
							table.clearSelection();
						}else {
							showPopUp("Unable to update: "+ done);

						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
				
			}
		});
		updateButton.setEnabled(false);
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateButton.setBounds(411, 431, 89, 23);
		contentPane.add(updateButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interestField.setText("0.0");
				 monFeeField.setText("0.0");
				 mgtFeeField.setText("0.0");
				 compSavField.setText("0.0");
				 riskPremField.setText("0.0");				
				textField.setText("");
				textArea.setText("");
				updateButton.setEnabled(false);
				saveButton.setEnabled(true);
				table.clearSelection();
				
			}
		});
		clearButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		clearButton.setBounds(510, 431, 89, 23);
		contentPane.add(clearButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setBounds(609, 431, 89, 23);
		contentPane.add(cancelButton);
		
		updateLoanProductTable(setupInterface.getAllLoanProducts());
	}
	
	
	
	
	// client methods to update loan products table
	public void updateLoanProductTable(ArrayList<LoanProduct> list) {
		Object[][] data = new Object[list.size()][8];
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getProductId();
			data[i][2] = list.get(i).getProductDescription();
			data[i][3] = list.get(i).getIntrestRate();
			data[i][4] = list.get(i).getMonFeeRate();
			data[i][5] = list.get(i).getRiskPremRate();
			data[i][6] = list.get(i).getMgtFeeRate();
			data[i][7] = list.get(i).getCompSavingsRate();
			
		}
		
		Object[] columnNames = {"S/No", "Product ID", "Product Description","Interest (%)", "Monitoring Fee (%)", "Risk Premium (%)", "Management Fee (%)", "Compulsory Savings (%)"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(90);
		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(100);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		
		
					
	}
	
	
	public void showPopUp(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}

}
