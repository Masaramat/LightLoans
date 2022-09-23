package com.llp.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.llp.api.LLPMainInterface;
import com.llp.api.LLPReportInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.AORecommendationNote;
import com.llp.entities.LoanAnalysisParameters;
import com.llp.entities.LoanApplication;
import com.llp.entities.LoanOfferView;
import com.llp.entities.LoanSecurity;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;

public class LoanApplicationLetterFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	final LLPReportInterface reportInterface ;
	final LLPMainInterface mainInterface;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanApplicationLetterFrame frame = new LoanApplicationLetterFrame("");
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
	public LoanApplicationLetterFrame(String application_id) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		 reportInterface = InterfaceGenerator.getReportInterface();
		 mainInterface = InterfaceGenerator.getMainInterface();
		
		showReport(application_id);
		
	}
	
	public void showReport(String application_id){
		String sourceName = "/com/llp/main/LoanApplication.jrxml";	
		String subSourceName = "/com/llp/main/AnalysisSubReport.jrxml";	
		String subSourceNameCheck = "/com/llp/main/AnalysisSubReport.jrxml";
		
				
		try{
			
			ArrayList<LoanApplication> pb = reportInterface.getLoanApplication(application_id);
			ArrayList<LoanAnalysisParameters> five_c = mainInterface.getLoanAnalysis(application_id, "five c");
			ArrayList<LoanAnalysisParameters> checkList = mainInterface.getLoanAnalysis(application_id, "check");
			
			AORecommendationNote note = mainInterface.getRecommendationNote(application_id);
			String recommendation = note.getRecommendationNote();
			String repaymentType = note.getRepaymentType();
			
			
			JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(sourceName));
			JasperReport jasperSubReport = JasperCompileManager.compileReport(getClass().getResourceAsStream(subSourceName));
			JasperReport jasperSubReportCheck = JasperCompileManager.compileReport(getClass().getResourceAsStream(subSourceNameCheck));
			
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("fiveCList", five_c);
			parameters.put("AnalysisSubReport", jasperSubReport);
			parameters.put("checklist", checkList);
			parameters.put("checklistJasper", jasperSubReportCheck);
			parameters.put("fiveCNote", recommendation);
			parameters.put("repaymentType", repaymentType);

			JRDataSource jrdatasource = new JRBeanCollectionDataSource(pb);
			
			JasperPrint filledReport = JasperFillManager.fillReport(report, parameters, jrdatasource);			
			this.getContentPane().add(new JRViewer(filledReport), BorderLayout.CENTER);
			this.pack();				
			
			
		}catch(Exception ex){ ex.printStackTrace();	}
		
	}
	

}
