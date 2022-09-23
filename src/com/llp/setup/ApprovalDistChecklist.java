package com.llp.setup;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.llp.api.LLPMainInterface;
import com.llp.api.LLPSetupInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.ApprovalDisbursementParameters;
import com.llp.entities.DocumentationParameters;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ApprovalDistChecklist extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	private JTextArea textArea; 
	private JRadioButton rdbtnShow;
	private JRadioButton rdbtnHide;
	
	private JButton btnUpdate;
	private JButton btnSave;
	
	private ButtonGroup bg;
	ArrayList<ApprovalDisbursementParameters> params;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApprovalDistChecklist frame = new ApprovalDistChecklist();
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
	public ApprovalDistChecklist() {
		setTitle("LLP - Risk Acceptance Criteria");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 530, 444);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
		
		bg = new ButtonGroup();
		
		
		JLabel lblNewLabel_1 = new JLabel("Parameter");
		lblNewLabel_1.setBounds(10, 49, 79, 27);
		contentPane.add(lblNewLabel_1);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(79, 50, 408, 50);
		contentPane.add(textArea);
		
		JLabel lblNewLabel_2 = new JLabel("Status");
		lblNewLabel_2.setBounds(10, 113, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		rdbtnShow = new JRadioButton("Show");
		rdbtnShow.setSelected(true);
		rdbtnShow.setBounds(74, 109, 60, 23);
		contentPane.add(rdbtnShow);
		
		rdbtnHide = new JRadioButton("Hide");
		rdbtnHide.setBounds(149, 109, 60, 23);
		contentPane.add(rdbtnHide);
		
		JLabel lblNewLabel = new JLabel("Risk Acceptance Criteria");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(0, 11, 514, 27);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 154, 482, 192);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnSave.setEnabled(false);
				btnUpdate.setEnabled(true);
				int xy = table.getSelectedRow();
				textArea.setText(params.get(xy).getParameter());
				if (params.get(xy).getStatus().equalsIgnoreCase("enabled")) {
					rdbtnShow.setSelected(true);
				}else {
					rdbtnHide.setSelected(true);
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String status = "";
				if (rdbtnShow.isSelected()) {
					status = "enabled";
				}else if (rdbtnHide.isSelected()) {
					status = "disabled";
				}
				if(textArea.getText().length()<1) {
					showPopup("No item selected.");
				}else {
					try {
						setupInterface.saveApprovalChecklistItem(textArea.getText(), status);
						showPopup("Success!");
						textArea.setText("");
						params = setupInterface.getApprovalChecklistItems();
						updateApprovalParameterTable(params);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					
				}
				
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.setBounds(215, 371, 89, 23);
		contentPane.add(btnSave);
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String status = "";
					if (rdbtnShow.isSelected()) {
						status = "enabled";
					}else if (rdbtnHide.isSelected()) {
						status = "disabled";
					}
					if(table.getSelectedRow()<0) {
						showPopup("No item selected.");
					}else {
						setupInterface.updateApprovalChecklistItem(textArea.getText(), status, params.get(table.getSelectedRow()).getSno());
						showPopup("Updated successfully.");
						textArea.setText("");
						rdbtnShow.setSelected(true);
						btnSave.setEnabled(true);
						btnUpdate.setEnabled(false);
						params = setupInterface.getApprovalChecklistItems();
						updateApprovalParameterTable(params);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpdate.setEnabled(false);
		btnUpdate.setBounds(314, 371, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(413, 371, 89, 23);
		contentPane.add(btnClose);
		
		bg.add(rdbtnHide);
		bg.add(rdbtnShow);
		
		
		try {
			params = setupInterface.getApprovalChecklistItems();
			updateApprovalParameterTable(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//client method to update loan offer search table
	public void updateApprovalParameterTable(ArrayList<ApprovalDisbursementParameters> list) {
		Object[][] data = new Object[list.size()][11];
				
		for(int i=0; i<list.size(); i++) {				
			data[i][0] = (i+1);
			data[i][1] = list.get(i).getParameter();
			data[i][2] = list.get(i).getStatus();
			
					
		}
		
		Object[] columnNames = {"S/No", "Parameter", "status"};
		
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		table.setModel(model);
	
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(350);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		
					
	}
		
	public void showPopup(String message) {
		JFrame jFrame = new JFrame();
		jFrame.setAlwaysOnTop(true);
		
		JOptionPane.showMessageDialog(jFrame, message);
	}
	
	
	
	
	
}
