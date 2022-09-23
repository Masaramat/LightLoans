package com.llp.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.llp.api.LLPMainInterface;
import com.llp.api.LLPSetupInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.Branch;
import com.llp.entities.User;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class UserLoginFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JTextField usernameField;
	private JPasswordField passwordField;
	public static JButton btnNewButton;
	
	ArrayList<Branch> branches = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserLoginFrame frame = new UserLoginFrame();
					//splash screen location
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
	public UserLoginFrame() throws RemoteException{
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserLoginFrame.class.getResource("/resources/logo_1.png")));
		setTitle("Light Loan Processor : User Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 555, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
		final LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
		
		
		branches = setupInterface.getAllBranches();
		
		JLabel lblNewLabel = new JLabel("User Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(95, 11, 313, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Branch");
		lblNewLabel_1.setBounds(76, 70, 90, 20);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Username");
		lblNewLabel_1_1.setBounds(76, 101, 90, 20);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Password");
		lblNewLabel_1_2.setBounds(76, 132, 90, 20);
		contentPane.add(lblNewLabel_1_2);
		
		final JComboBox comboBox = new JComboBox();
		for(int i=0; i<branches.size(); i++) {
			comboBox.addItem(branches.get(i).getBranchName());
		}
		comboBox.setBounds(156, 69, 182, 21);
		contentPane.add(comboBox);
		
		usernameField = new JTextField();
		usernameField.setBounds(156, 101, 182, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(156, 132, 182, 20);
		contentPane.add(passwordField);
		
		btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = String.valueOf(passwordField.getPassword());
				String branch = comboBox.getSelectedItem().toString();
				
				if(username.length()<6) {
					JOptionPane.showMessageDialog(null, "Please enter a valid username with at least six characters.");
				}else if (passwordField.getText().length()<6) {
					JOptionPane.showMessageDialog(null, "Please enter a valid password with at least six characters.");
				}else {
									
					try {
						User user = mainInterface.getUserLogin(username, password, branch);
						
						if(user.getFullName() == null) {
							JOptionPane.showMessageDialog(null, "Invalid username/password/branch combination.");
						}else if(user.getUserStatus() == 0) {
							JOptionPane.showMessageDialog(null, "User is currently disabled from system.");
						}else {
							LLPMainPage frame  = new LLPMainPage(user);					
							frame.setVisible(true);

							dispose();						
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
			}
		});
		btnNewButton.setBounds(289, 259, 107, 23);
		contentPane.add(btnNewButton);
		
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(406, 259, 107, 23);
		contentPane.add(btnNewButton_1);
	}	

	
	
	public void showPopup(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}
	
	
	
	
	
}
