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
import com.llp.entities.DocumentationParameters;
import com.llp.entities.LoanOfferView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

public class DocumentationChecklistSetup extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	private ButtonGroup bGroup;
	private JRadioButton rdbtnShow;
	private JRadioButton rdbtnHide;
	private JTextArea textArea;
	private JButton btnSave;
	private JButton btnUpdate;
	
	private ArrayList<DocumentationParameters> params_list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DocumentationChecklistSetup frame = new DocumentationChecklistSetup();
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
	public DocumentationChecklistSetup() throws RemoteException{
		setTitle("LLP - documentation Checklist Setup");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 518, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		bGroup = new ButtonGroup();
		
		final LLPSetupInterface setupInterface = InterfaceGenerator.getSetupInterface();
		
		
		
		JLabel lblNewLabel = new JLabel("Documentation Checklist ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 11, 502, 27);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Parameter");
		lblNewLabel_1.setBounds(10, 49, 79, 27);
		contentPane.add(lblNewLabel_1);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(84, 50, 408, 50);
		contentPane.add(textArea);
		
		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String status = "";
				if (rdbtnShow.isSelected()) {
					status = "enabled";
				}else if (rdbtnHide.isSelected()) {
					status = "disabled";
				}
				
				try {
					if(textArea.getText().length()<1) {
						showPopup("Please enter valid parameter.");
					}else {
						setupInterface.saveDocumentionChecklistItem(textArea.getText(), status);
						textArea.setText("");						showPopup("Success!");
						params_list = setupInterface.getDocumentionChecklistItems();
						updateDocumentationParameterTable(params_list);
						
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(205, 361, 89, 23);
		contentPane.add(btnSave);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 147, 482, 192);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnSave.setEnabled(false);
				btnUpdate.setEnabled(true);
				int xy = table.getSelectedRow();
				textArea.setText(params_list.get(xy).getParameter());
				if (params_list.get(xy).getStatus().equalsIgnoreCase("enabled")) {
					rdbtnShow.setSelected(true);
				}else {
					rdbtnHide.setSelected(true);
				}
			}
		});
		scrollPane.setViewportView(table);
		
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
						setupInterface.updateDocumentionChecklistItem(textArea.getText(), status, params_list.get(table.getSelectedRow()).getSno());
						showPopup("Updated successfully.");
						textArea.setText("");
						rdbtnShow.setSelected(true);
						btnSave.setEnabled(true);
						btnUpdate.setEnabled(false);
						params_list = setupInterface.getDocumentionChecklistItems();
						updateDocumentationParameterTable(params_list);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpdate.setEnabled(false);
		btnUpdate.setBounds(304, 361, 89, 23);
		contentPane.add(btnUpdate);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnClose.setBounds(403, 361, 89, 23);
		contentPane.add(btnClose);
		
		JLabel lblNewLabel_2 = new JLabel("Status");
		lblNewLabel_2.setBounds(10, 113, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		rdbtnShow = new JRadioButton("Show");
		rdbtnShow.setBounds(74, 109, 60, 23);
		contentPane.add(rdbtnShow);
		rdbtnShow.setSelected(true);
		
		rdbtnHide = new JRadioButton("Hide");
		rdbtnHide.setBounds(149, 109, 60, 23);
		contentPane.add(rdbtnHide);
		
		bGroup.add(rdbtnShow);
		bGroup.add(rdbtnHide);
		
		try {
			params_list = setupInterface.getDocumentionChecklistItems();
			updateDocumentationParameterTable(params_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	//client method to update loan offer search table
		public void updateDocumentationParameterTable(ArrayList<DocumentationParameters> list) {
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
