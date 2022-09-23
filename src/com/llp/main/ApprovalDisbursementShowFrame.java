package com.llp.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
import com.llp.entities.AccountTurnover;
import com.llp.entities.ApprovalDisbursementParameters;
import com.llp.entities.CollateralNote;
import com.llp.entities.DocumentationParameters;
import com.llp.entities.LoanOfferView;
import com.llp.entities.LoanSecurity;
import com.llp.entities.OutstandingFacility;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;

public class ApprovalDisbursementShowFrame extends JFrame {

	private JPanel contentPane;
	LLPReportInterface reportInterface = InterfaceGenerator.getReportInterface();
	LLPMainInterface mainInterface = InterfaceGenerator.getMainInterface();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApprovalDisbursementShowFrame frame = new ApprovalDisbursementShowFrame("");
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
	public ApprovalDisbursementShowFrame(String application_id) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		showReport(application_id);
	}
	
	public void showReport(String application_id){
		String sourceName = "/com/llp/main/ApprovalDisbursementForm.jrxml";
		String subSourceName_1 = "/com/llp/main/OutstandingFacilitySubReport.jrxml";
		String subSourceName_2 = "/com/llp/main/AccountTurnoverSubReport.jrxml";
		String subSourceName_3 = "/com/llp/main/RACSubReport.jrxml";
		String subSourceName_4 = "/com/llp/main/DocumentationSubReport.jrxml";
		
//		InputStream inputStream = OfferLetterFrame.class.getResourceAsStream("Light LogoII.jpg"); 
				
		try{
			
			ArrayList<LoanOfferView> offer_list = reportInterface.getLoanOfferView(application_id, "approval");
			ArrayList<OutstandingFacility> of_list = mainInterface.getOutstandingFacilities(application_id);
			ArrayList<AccountTurnover> at_list = mainInterface.getAccountTurnovers(application_id);
			ArrayList<ApprovalDisbursementParameters> adp_list = mainInterface.getLoanRAC(application_id);
			ArrayList<DocumentationParameters>dp_list = mainInterface.getLoanDocumentation(application_id);
			CollateralNote note = mainInterface.getCollateralNotes(application_id);
			
			if (of_list.size()<1) {
				OutstandingFacility facility = new OutstandingFacility("NIL", "NIL", "NIL", 0.00, 0.00);
				of_list.add(facility);
			}
			
			if (at_list.size()<1) {
				AccountTurnover turnover = new AccountTurnover("NIL", 0.00, 0.00, 0.00, 0.00);
				at_list.add(turnover);
			}
			
			
			JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(sourceName));
			JasperReport jasperSubReport_1 = JasperCompileManager.compileReport(getClass().getResourceAsStream(subSourceName_1));
			JasperReport jasperSubReport_2 = JasperCompileManager.compileReport(getClass().getResourceAsStream(subSourceName_2));
			JasperReport jasperSubReport_3 = JasperCompileManager.compileReport(getClass().getResourceAsStream(subSourceName_3));
			JasperReport jasperSubReport_4 = JasperCompileManager.compileReport(getClass().getResourceAsStream(subSourceName_4));
			
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("collateralNote", note.getCollateral());
			parameters.put("customerBackground", note.getCostumerBackground());
			
			parameters.put("outstandingFacilityList", of_list);
			parameters.put("outstandingFacilityJasper", jasperSubReport_1);
			
			parameters.put("accountTurnoverList", at_list);
			parameters.put("accountTurnoverJasper", jasperSubReport_2);
			
			parameters.put("racList", adp_list);
			parameters.put("racJasper", jasperSubReport_3);
			
			parameters.put("documentationList", dp_list);
			parameters.put("documentationJasper", jasperSubReport_4);
			
		   // parameters.put("imageLogo", ImageIO.read(new ByteArrayInputStream(JRLoader.loadBytes(inputStream))));

			JRDataSource jrdatasource = new JRBeanCollectionDataSource(offer_list);
			
			JasperPrint filledReport = JasperFillManager.fillReport(report, parameters, jrdatasource);			
			this.getContentPane().add(new JRViewer(filledReport), BorderLayout.CENTER);
			this.pack();			
			
			
		}catch(Exception ex){ ex.printStackTrace();	}
		
		
	}

}
