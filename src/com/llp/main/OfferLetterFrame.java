package com.llp.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.llp.api.LLPReportInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.LoanOfferView;
import com.llp.entities.LoanSecurity;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.engine.util.JRLoader;
import javax.imageio.ImageIO;


public class OfferLetterFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	

	/**
	 * Launch the application.
	 */
	
	LLPReportInterface reportInterface = InterfaceGenerator.getReportInterface();
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				int i=0;
				try {
					OfferLetterFrame frame = new OfferLetterFrame("");
					
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
	public OfferLetterFrame(String application_id) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 523, 226);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);		
		showReport(application_id);
	}
	
	
	
	public void showReport(String application_id){
		String sourceName = "/com/llp/main/OfferLetter.jrxml";
		String subSourceName = "/com/llp/main/securitiesReport.jrxml";	
		
		InputStream inputStream = OfferLetterFrame.class.getResourceAsStream("Light LogoII.jpg"); 
				
		try{
			
			ArrayList<LoanOfferView> offer_list = reportInterface.getLoanOfferView(application_id, "offer");
			ArrayList<LoanSecurity> sec_list = reportInterface.getLoanSecurities(application_id);
			
			
			JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(sourceName));
			JasperReport jasperSubReport = JasperCompileManager.compileReport(getClass().getResourceAsStream(subSourceName));
			
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("securityList", sec_list);
			parameters.put("securityJasper", jasperSubReport);
		    parameters.put("imageLogo", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(inputStream))));

			JRDataSource jrdatasource = new JRBeanCollectionDataSource(offer_list);
			
			JasperPrint filledReport = JasperFillManager.fillReport(report, parameters, jrdatasource);			
			this.getContentPane().add(new JRViewer(filledReport), BorderLayout.CENTER);
			this.pack();			
			
			
		}catch(Exception ex){ ex.printStackTrace();	}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
