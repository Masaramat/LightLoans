package com.llp.setup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPSetupInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.BusinessCategory;
import com.llp.entities.MarketingQuestion;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.ListSelectionModel;

public class MarketingQuestionsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JRadioButton rdbtnAll;
	private JRadioButton rdbtnSelect;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox_1;
	private JButton updateButton;
	private JButton saveButton;
	
	
	private ButtonGroup bg = new ButtonGroup();
	
	ArrayList<BusinessCategory> category_list = null;
	ArrayList<MarketingQuestion> question_list = null;
			
	
	 String selection = "";
	 String b_category = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MarketingQuestionsFrame frame = new MarketingQuestionsFrame("");
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
	public MarketingQuestionsFrame(final String source) throws RemoteException{
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("Light Loan Processor - Marketing Questions Configuration");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(320, 100, 699, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
		
		category_list = setupInterface.getAllBusinessCategories();
		
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Add / Edit Marketing Question", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 663, 138);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Business Category");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setBounds(10, 31, 106, 20);
		panel.add(lblNewLabel);
		
		JLabel lblQuestion = new JLabel("Question");
		lblQuestion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblQuestion.setBounds(10, 62, 106, 20);
		panel.add(lblQuestion);
		
		final JComboBox comboBox = new JComboBox();
		for(int i=0; i<category_list.size(); i++) {
			comboBox.addItem(category_list.get(i).getCategoryDecription());
		}
		comboBox.setBounds(117, 31, 214, 20);
		panel.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(117, 60, 411, 57);
		panel.add(scrollPane);
		
		final JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 206, 663, 161);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int xy = table.getSelectedRow();
				
				textArea.setText(question_list.get(xy).getQuestion());
				comboBox.setSelectedItem(question_list.get(xy).getCategoryDescription());
				saveButton.setEnabled(false);
				updateButton.setEnabled(true);
				
			}
		});
		scrollPane_1.setViewportView(table);
		
		rdbtnAll = new JRadioButton("View All");
		rdbtnAll.setSelected(true);
		rdbtnAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				
				try {
					comboBox_1.setEnabled(false);
					selection = "View all";
					question_list = setupInterface.getMarketingQuestions(selection, b_category);
					updateMQuestionsTable(question_list);
				} catch (Exception e1) {					
					e1.printStackTrace();
				}
				
				
				
			}
		});
		rdbtnAll.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnAll.setBounds(10, 172, 70, 20);
		contentPane.add(rdbtnAll);
		
		rdbtnSelect = new JRadioButton("Select");
		rdbtnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					comboBox_1.setEnabled(true);
					selection = "Select";
					b_category = comboBox_1.getSelectedItem().toString();
					question_list = setupInterface.getMarketingQuestions(selection, b_category);
					updateMQuestionsTable(question_list);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		rdbtnSelect.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnSelect.setBounds(96, 172, 64, 20);
		contentPane.add(rdbtnSelect);
		
		bg.add(rdbtnAll);
		bg.add(rdbtnSelect);
		
		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				
				try {
					selection = "Select";
					b_category = comboBox_1.getSelectedItem().toString();
					question_list = setupInterface.getMarketingQuestions(selection, b_category);
					updateMQuestionsTable(question_list);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		comboBox_1.setEnabled(false);
		for(int i=0; i<category_list.size(); i++) {
			comboBox_1.addItem(category_list.get(i).getCategoryDecription());
		}
		comboBox_1.setBounds(163, 172, 247, 20);
		contentPane.add(comboBox_1);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				
				try {
					String category_id = category_list.get(comboBox.getSelectedIndex()).getCategoryId();
					String question = textArea.getText();
					
					setupInterface.createMarketingQuestion(category_id, question);
					showPopUp("Success!");
					
					comboBox_1.setSelectedItem(comboBox.getSelectedItem().toString());
					selection = "Select";			
					b_category = comboBox_1.getSelectedItem().toString();
					rdbtnSelect.setSelected(true);
					comboBox_1.setEnabled(true);
					question_list = setupInterface.getMarketingQuestions(selection, b_category);
					updateMQuestionsTable(question_list);
					
					textArea.setText("");
					
					saveButton.setEnabled(true);
					updateButton.setEnabled(false);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveButton.setBounds(287, 392, 89, 23);
		contentPane.add(saveButton);
		
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				
				try {
					int xy = table.getSelectedRow();			
					
					String category_id = category_list.get(comboBox.getSelectedIndex()).getCategoryId();
					String question = textArea.getText();
					int sno = question_list.get(xy).getSno();
					
					setupInterface.updateMQuestion(category_id, question, sno);
					showPopUp("Update Successful!");
					
					comboBox_1.setSelectedItem(comboBox.getSelectedItem().toString());
					selection = "Select";			
					b_category = comboBox_1.getSelectedItem().toString();
					comboBox_1.setEnabled(true);
					rdbtnSelect.setSelected(true);
					question_list = setupInterface.getMarketingQuestions(selection, b_category);
					updateMQuestionsTable(question_list);
					
					saveButton.setEnabled(true);
					updateButton.setEnabled(false);
					table.clearSelection();
					textArea.setText("");
					comboBox.setSelectedIndex(0);	
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		updateButton.setEnabled(false);
		
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		updateButton.setBounds(386, 392, 89, 23);
		contentPane.add(updateButton);
		
		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveButton.setEnabled(true);
				updateButton.setEnabled(false);
				table.clearSelection();
				textArea.setText("");
				comboBox.setSelectedIndex(0);
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(485, 392, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(source.equalsIgnoreCase("ifs")) {
					//InterviewFaceSheet.updateQuestionBox();
				}
				dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.setBounds(584, 392, 89, 23);
		contentPane.add(btnNewButton_1);
		
		
		

		if(rdbtnAll.isSelected()) {
			selection = "View all";
		}else if(rdbtnSelect.isSelected()) {
			selection = "Select";
		}
		question_list = setupInterface.getMarketingQuestions(selection, b_category);		
		updateMQuestionsTable(question_list);
		
	}
	
	
	
	// client methods to update marketing question table
	public void updateMQuestionsTable(ArrayList<MarketingQuestion> list) {
		Object[][] data = new Object[list.size()][3];
		
		for(int i=0; i<list.size(); i++) {
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getCategoryDescription();
			data[i][2] = list.get(i).getQuestion();			
		}
		
		Object[] columnNames = {"S/No", "Business Category", "Question"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(450);
	
					
	}
		
	public void showPopUp(String message) {
		JFrame jf = new JFrame();
		jf.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(jf, message);
	}
	
	
	
}
