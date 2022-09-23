package com.llp.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.llp.api.LLPMainInterface;
import com.llp.api.LLPSetupInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.AppUserGroup;
import com.llp.entities.Branch;
import com.llp.entities.LightMessage;
import com.llp.entities.User;
import com.llp.entities.UserGroup;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

public class LightMessengerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1309934423992571051L;
	private JPanel contentPane;
	private JTextPane textPane;
	private StyleContext context;
	private StyledDocument doc;
	
	 SimpleAttributeSet rightAlign;
	 SimpleAttributeSet leftAlign;
	 
	 JList list;
	 private ArrayList<Branch> branch_list;
	 static ArrayList<AppUserGroup> list2;
	 ArrayList<String> userlist = new ArrayList<String>();
	 JLabel userNameLabel;
	 JTextArea textArea ;
	 static JButton btnSend;
	 static JComboBox userGroupBox ;
	 static JComboBox branchBox;
	 
	 
	 
	 private String receiverrr = "";
	 static Timer timer = new Timer();
	 
	 static User user_1;
	 
	 final static LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();
	 final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
	 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LightMessengerFrame frame = new LightMessengerFrame(new User());
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
	public LightMessengerFrame(final User user) {	
		
		context = new StyleContext();
        doc = new DefaultStyledDocument(context);
        
        try {
			branch_list = setupInterface.getAllBranches();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        rightAlign = new SimpleAttributeSet();
        leftAlign = new SimpleAttributeSet();
        
        StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(rightAlign, new Color(0,153,115));
        StyleConstants.setFontSize(rightAlign, 13);
        StyleConstants.setBold(rightAlign, true);
        
        StyleConstants.setAlignment(leftAlign, StyleConstants.ALIGN_LEFT);
        StyleConstants.setForeground(leftAlign, new Color(0,0,153));
        StyleConstants.setFontSize(leftAlign, 13);
        StyleConstants.setBold(leftAlign, true);
        
        
        
        user_1 = user;       
        
       
		setTitle("Light Messenger");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(652, 105, 713, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 55, 234, 384);
		contentPane.add(panel);
		panel.setLayout(null);	
		
		
			
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 57, 214, 277);
		panel.add(scrollPane_2);
		
		list = new JList();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {
					if (userGroupBox.getSelectedIndex() <0 && branchBox.getSelectedIndex() < 0) {
						
					}else {
						int jk = userGroupBox.getSelectedIndex();
						int gk = branchBox.getSelectedIndex();
						int kk  = list.getSelectedIndex();
						
						if (list.getSelectedValue()!=null) {
							
							
							list2 = mainInterface.getUserGroups(branch_list.get(branchBox.getSelectedIndex()).getBranchName());	
							userGroupBox.removeAllItems();
							updateUserBox(list2, user);
							
							branchBox.removeAllItems();
							branch_list = setupInterface.getAllBranches();
							
							updateBranchBox(branch_list, user);
							
							branchBox.setSelectedIndex(gk);
							userGroupBox.setSelectedIndex(jk);
							
							textArea.setText("");
							userNameLabel.setText("@"+userlist.get(kk));
							receiverrr = userlist.get(kk);
							mainInterface.updateMessageStatus(user.getUsername(), receiverrr);
							
								
							userlist = mainInterface.getUsernames(user.getUsername(), list2.get(jk).getGroupName(), branch_list.get(gk).getBranchName());
							

							updateJList(userlist);	
				
							Texting(user.getUsername(), receiverrr);
							
							textArea.requestFocusInWindow();
							EventQueue.invokeLater( new Runnable() {
								@Override
								public void run() {
									textArea.requestFocusInWindow();
								}
							} );
							
						}
					}
					
					
					userlist = mainInterface.getUsernames(user.getUsername(), list2.get(userGroupBox.getSelectedIndex()).getGroupName(), branch_list.get(branchBox.getSelectedIndex()).getBranchName());
					updateJList(userlist);
					Texting(user.getUsername(), receiverrr);	
				
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
								
				
			}
		});
		list.setToolTipText("Application Users");
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(list);
		
		JPanel switch_panel = new JPanel();
		switch_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		switch_panel.setBounds(254, 55, 433, 384);
		contentPane.add(switch_panel);
		switch_panel.setLayout(new CardLayout(0, 0));
		
		JPanel message_panel = new JPanel();
		switch_panel.add(message_panel, "msg_panel");
		message_panel.setLayout(null);
			
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 302, 324, 69);
		message_panel.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (textArea.getText().length()<1 || receiverrr.length()<2) {
					btnSend.setEnabled(false);
				}else {
					btnSend.setEnabled(true);
					
				}
			}
		});
		scrollPane.setViewportView(textArea);
		
		
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				try {
					mainInterface.sendMessage(user.getUsername(), receiverrr, textArea.getText());
					Texting(user.getUsername(), receiverrr);
					textArea.setText("");
					btnSend.setEnabled(false);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnSend.setBounds(344, 339, 79, 32);
		btnSend.setEnabled(false);
		message_panel.add(btnSend);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 29, 413, 262);
		message_panel.add(scrollPane_1);
		
		textPane = new JTextPane(doc);
		scrollPane_1.setViewportView(textPane);
		textPane.setBackground(new Color(255, 255, 255));
		textPane.setEditable(false);
		
		userNameLabel = new JLabel();
		userNameLabel.setBounds(10, 0, 411, 20);
		message_panel.add(userNameLabel);
		
		branchBox = new JComboBox();
		branchBox.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				try {
					if(branchBox.getSelectedIndex()<0) {
						
					}else {
											
						list2 = mainInterface.getUserGroups(branch_list.get(branchBox.getSelectedIndex()).getBranchName());	
						userGroupBox.removeAllItems();
						updateUserBox(list2, user);
						
						userlist = mainInterface.getUsernames(user.getUsername(), list2.get(userGroupBox.getSelectedIndex()).getGroupName(), branch_list.get(branchBox.getSelectedIndex()).getBranchName());
						updateJList(userlist);
						receiverrr = "";
						userNameLabel.setText("");
						Texting(user.getUsername(), receiverrr);
						textArea.setText("");
						btnSend.setEnabled(false);
						
						
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		branchBox.setBounds(20, 22, 215, 22);
		contentPane.add(branchBox);
		
		userGroupBox = new JComboBox();
		userGroupBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if (userGroupBox.getSelectedIndex()<0) {
						
					}
					else{
						
						
						userlist = mainInterface.getUsernames(user.getUsername(), list2.get(userGroupBox.getSelectedIndex()).getGroupName(), branch_list.get(branchBox.getSelectedIndex()).getBranchName());
						updateJList(userlist);
						receiverrr = "";
						userNameLabel.setText("");
						Texting(user.getUsername(), receiverrr);
						textArea.setText("");
						btnSend.setEnabled(false);				
						
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		userGroupBox.setBounds(10, 11, 214, 22);
		panel.add(userGroupBox);
		try {
			
			updateBranchBox(branch_list, user);	
			
			list2 = mainInterface.getUserGroups(branch_list.get(branchBox.getSelectedIndex()).getBranchName());	
			userGroupBox.removeAllItems();
			updateUserBox(list2, user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		//new MessageTimer().run();
	}
	
	public void updateUserBox(ArrayList<AppUserGroup> list, User user) {
		try {
			
			
			for (int iterator = 0; iterator<list2.size(); iterator++) {
				
				int xy = mainInterface.getGroupMessageCount(user.getUsername(), list.get(iterator).getGroupName());
				if (xy < 1) {
					userGroupBox.addItem(list.get(iterator).getGroupName());
				}else {
					userGroupBox.addItem(list.get(iterator).getGroupName() +" - (" +xy+") ");
					
				}
							
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateBranchBox(ArrayList<Branch> list, User user) {
		try {
			branchBox.removeAll();
			
			for (int iterator = 0; iterator<list.size(); iterator++) {
				
				int xy = mainInterface.getBranchMessageCount(user.getUsername(), list.get(iterator).getBranchName());
				if (xy < 1) {
					branchBox.addItem(list.get(iterator).getBranchName());
				}else {
					branchBox.addItem(list.get(iterator).getBranchName() +" - (" +xy+") ");
				}
							
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void Texting(String user1, String user2) {
		try {
			textPane.setText("");
	        ArrayList<LightMessage> messages = mainInterface.getMessages(user1, user2);
	        
	        String var = "";
	       
	        
	        for (LightMessage lightMessage : messages) {
						
				if(lightMessage.getSender().equalsIgnoreCase(user1)) {
	        		try {  
	        			var = "You: "+lightMessage.getMessage()+ "\n(" +lightMessage.getDate() +" - " +lightMessage.getTime()+") \n\n";
		                doc.insertString(doc.getLength(), var, leftAlign);
		               
		            } catch (BadLocationException e) {
		                e.printStackTrace();
		            }
	        	}else {
	        		try {
	        			var = "Reply: "+lightMessage.getMessage()+ "\n(" +lightMessage.getDate() +" - " +lightMessage.getTime()+") \n\n";
		                doc.insertString(doc.getLength(), var, rightAlign);
		               	            } catch (BadLocationException e) {
		                e.printStackTrace();
		            }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
      
        
    }
	
	public void updateJList(ArrayList<String> userlist) {
		
		try {
			DefaultListModel listModel = new DefaultListModel();
			for (int i = 0; i < userlist.size(); i++)
			{
				int xy = mainInterface.getNewMessageCount(userlist.get(i), user_1.getUsername());
				if (xy < 1) {
					listModel.addElement(userlist.get(i));
				}else {
					 listModel.addElement(userlist.get(i)+" - ("+xy+")");
				}
				
			   
			}
			list.setModel(listModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	
	public static void checkMessageCount() {
		
		try {
			int jj = userGroupBox.getSelectedIndex();
			userGroupBox.removeAllItems();
			
			list2 = mainInterface.getUserGroups(branchBox.getSelectedItem().toString());
			for (int iterator = 0; iterator<list2.size(); iterator++) {
				
				int xy = mainInterface.getGroupMessageCount(user_1.getUsername(), list2.get(iterator).getGroupName());
				if (xy < 1) {
					userGroupBox.addItem(list2.get(iterator).getGroupName());
				}else {
					userGroupBox.addItem(list2.get(iterator).getGroupName() +" - (" +xy+") ");
				}
							
			}
			userGroupBox.setSelectedIndex(jj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
		
	static class MessageTimer extends TimerTask{
		public void run() {
			try {
				int delay = (1*2000);
				timer.schedule(new MessageTimer(), delay);
				checkMessageCount();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	
}
