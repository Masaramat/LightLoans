package com.llp.reports;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.llp.api.LLPReportInterface;
import com.llp.clientInterface.InterfaceGenerator;
import com.llp.entities.LoanApplication;
import com.llp.entities.LoanOfferView;
import com.llp.entities.RejectedLoan;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

public class LoanReportsShowFrame extends JFrame {

	private JPanel contentPane;
	LLPReportInterface reportInterface = InterfaceGenerator.getReportInterface();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanReportsShowFrame frame = new LoanReportsShowFrame("", null, null, null, null);
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
	
	public LoanReportsShowFrame(String report_type, ArrayList<String> table_list, ArrayList<String> data_list, Date fromDate, Date toDate) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		showReport(report_type, table_list, data_list, fromDate, toDate);
		
		
	}
	public void showReport(String report_type, ArrayList<String> table_list, ArrayList<String> data_list, Date fromDate, Date toDate) {
		
		String sourceName = "";	
		
		JRDataSource jrdatasource = null;
		
		
		ArrayList<LoanOfferView> pb = null;
		ArrayList<LoanApplication> cb = null;
		ArrayList<RejectedLoan> rl = null;
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		double total = 0;
		
		NumberFormat nf = NumberFormat.getCurrencyInstance();	 
		DecimalFormatSymbols dfs = ((DecimalFormat) nf).getDecimalFormatSymbols();
		dfs.setCurrencySymbol("");
		((DecimalFormat) nf).setDecimalFormatSymbols(dfs);
		
		String totalString = "";
		String fromdate = fromDate.toString();
		String todate = toDate.toString();
		
		
		
		try {
			switch (report_type) {
			case "loan_application" :
				sourceName = "/com/llp/reports/LoanApplicationReport.jrxml";
				cb = reportInterface.getLoanApplicationsReport(table_list, data_list, fromDate, toDate);
				
				parameters.clear();
				for(int i =0; i<cb.size(); i++) {
					total = total + cb.get(i).getLoanFacility();
				}
				totalString = nf.format(total);
				
				parameters.put("totalFacilityApplied", totalString); 
				parameters.put("fromdate", fromdate); 
				parameters.put("todate", todate); 
				
				jrdatasource = new JRBeanCollectionDataSource(cb);
				
				break;
			case "loan_offer" :
				sourceName = "/com/llp/reports/LoanOffersReport.jrxml";
				pb = reportInterface.getLoanOffersReport(table_list, data_list, fromDate, toDate);
				
				parameters.clear();
				for(int i =0; i<pb.size(); i++) {
					total = total + pb.get(i).getAmountApproved();
				}
				totalString = nf.format(total);
				
				parameters.put("totalLoanFacility", totalString); 
				parameters.put("fromdate", fromdate); 
				parameters.put("todate", todate); 
				
				jrdatasource = new JRBeanCollectionDataSource(pb);
				
				break;
			case "loan_audit" :
				sourceName = "/com/llp/reports/LoanAuditReport.jrxml";
				pb = reportInterface.getLoanAuditReport(table_list, data_list, fromDate, toDate);
				parameters.clear();
				for(int i =0; i<pb.size(); i++) {
					total = total + pb.get(i).getAmountApproved();
				}
				totalString = nf.format(total);
				
				parameters.put("totalLoanFacility", totalString); 
				parameters.put("fromdate", fromdate); 
				parameters.put("todate", todate); 
				
				jrdatasource = new JRBeanCollectionDataSource(pb);
				
				break;
				
			case "loan_disbursement" :
				sourceName = "/com/llp/reports/LoanDisbursementReport.jrxml";	
				pb = reportInterface.getLoanDisburseReport(table_list, data_list, fromDate, toDate);
				
				parameters.clear();
				for(int i =0; i<pb.size(); i++) {
					total = total + pb.get(i).getAmountApproved();
				}
				totalString = nf.format(total);
				
				parameters.put("totalLoanFacility", totalString); 
				parameters.put("fromdate", fromdate); 
				parameters.put("todate", todate); 
				
				jrdatasource = new JRBeanCollectionDataSource(pb);
				
				break;
				
			case "rejected_loans" :
				sourceName = "/com/llp/reports/RejectedLoansReport.jrxml";	
				rl = reportInterface.getRejectedLoansReport(table_list, data_list, fromDate, toDate);
				
				
				parameters.clear();				 
				parameters.put("fromdate", fromdate); 
				parameters.put("todate", todate); 
				
				jrdatasource = new JRBeanCollectionDataSource(rl);
				break;

			default:
				break;
			}
			
		
			
			JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream(sourceName));			
			JasperPrint filledReport = JasperFillManager.fillReport(report, parameters, jrdatasource);			
			this.getContentPane().add(new JRViewer(filledReport), BorderLayout.CENTER);
			this.pack();	
			
			
			
		}catch(Exception ex){ ex.printStackTrace();	}
		
	}

}
