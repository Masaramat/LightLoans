package com.llp.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.llp.api.LLPMainInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class RejectLoanFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idField;
	private JButton btnSave; 
	
	String check = "a";
	String rejection_type = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RejectLoanFrame frame = new RejectLoanFrame("","", new User());
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
	public RejectLoanFrame(final String application_id, final String source, final User user) throws RemoteException{
		setForeground(Color.LIGHT_GRAY);
		setBackground(Color.LIGHT_GRAY);
		setTitle("Loan Rejection Form");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 600, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Application ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(20, 62, 89, 20);
		contentPane.add(lblNewLabel);
		
		idField = new JTextField();
		idField.setEditable(false);
		idField.setBounds(119, 62, 146, 20);
		contentPane.add(idField);
		idField.setColumns(10);	
		idField.setText(application_id);
		
		final LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
		
		final ButtonGroup bg = new ButtonGroup();
		
		final JRadioButton rdbtnPermanent = new JRadioButton("Reject Permanently");
		rdbtnPermanent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSave.setEnabled(true);
			}
		});
		rdbtnPermanent.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnPermanent.setBounds(20, 251, 146, 23);
		contentPane.add(rdbtnPermanent);
		
		final JRadioButton rdbtnNormal = new JRadioButton("Reject for Review");
		rdbtnNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSave.setEnabled(true);
			}
		});
		rdbtnNormal.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnNormal.setBounds(20, 225, 178, 23);
		contentPane.add(rdbtnNormal);
		
		JLabel lblNewLabel_2 = new JLabel("Comment");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(20, 91, 89, 29);
		contentPane.add(lblNewLabel_2);
		bg.add(rdbtnNormal);
		bg.add(rdbtnPermanent);		
		
		final JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(119, 93, 441, 98);
		contentPane.add(textArea);
		
		btnSave = new JButton("Reject Loan");
		btnSave.setEnabled(false);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(rdbtnNormal.isSelected() && source.equalsIgnoreCase("application")) {
						check = "c";
						rejection_type = "returned";
					}else if(rdbtnNormal.isSelected() && source.equalsIgnoreCase("offer")) {
						check = "b";
						rejection_type = "rejected";
					}else if(rdbtnPermanent.isSelected() && source.equalsIgnoreCase("application")) {
						check = "c";
						rejection_type = "blocked";
					}else if(rdbtnPermanent.isSelected() && source.equalsIgnoreCase("offer")) {
						check = "b";
						rejection_type = "blocked";
					}
					
					if(textArea.getText().length()<1) {
						showPopup("Please enter valid text in the comment section.");	
					}else {
						mainInterface.rejectLoanOffer(idField.getText(), rejection_type, textArea.getText(), user.getFullName());				
						showPopup("Loan "+source+" rejected successfully!");
						if(check.equalsIgnoreCase("b")) {
							LoanOfferFrame.deactivateSomeButtons(rejection_type);
							
						}else if(check.equalsIgnoreCase("c")) {
							LoanApplicationFrame.deactivateSomeButtons(rejection_type);
							
						}
						btnSave.setEnabled(false);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				
				
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSave.setBackground(new Color(255, 69, 0));
		btnSave.setForeground(Color.WHITE);
		btnSave.setBounds(346, 289, 115, 23);
		contentPane.add(btnSave);
		
		JButton btnCancel = new JButton("Close");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancel.setBounds(471, 289, 89, 23);
		contentPane.add(btnCancel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		String textString = ("Loan "+ source +" Rejection Form").toUpperCase();
		lblNewLabel_1.setText(textString);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 11, 564, 23);
		contentPane.add(lblNewLabel_1);
		
		
	}
	

	public void showPopup(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}
}
