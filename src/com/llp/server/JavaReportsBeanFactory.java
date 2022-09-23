package com.llp.server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.llp.dbconnection.DbConnection;
import com.llp.entities.LoanApplication;
import com.llp.entities.LoanOfferPrice;
import com.llp.entities.LoanOfferView;
import com.llp.entities.LoanSecurity;


public class JavaReportsBeanFactory {

	public JavaReportsBeanFactory() {
		
	}
	
	
	public static ArrayList<LoanApplication> getLoanApplicationsReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate){
		ArrayList<LoanApplication> list = new ArrayList<LoanApplication>();
		try {
			
			Connection connection = DbConnection.getDbConnection();
			String query = "";
			
				if(column_list.size()==0) {
				query = "SELECT * FROM loan_applications_view "
						+ "WHERE application_date between '"+fromDate+"' AND '"+todateDate+"' ";
				}else if (column_list.size() == 1) {
					query = "SELECT * FROM loan_applications_view "
							+ "WHERE (application_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND "+column_list.get(0) +" = '"+data_list.get(0)+"' ";
				}else if (column_list.size() == 2) {
					query = "SELECT * FROM loan_applications_view "
							+ "WHERE (application_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') ";
				}else if (column_list.size() == 3) {
					query = "SELECT * FROM loan_applications_view "
							+ "WHERE (application_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND ("+column_list.get(2) +" = '"+data_list.get(2)+"') ";
				}
			
				//System.out.println(query);
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
				Locale locale = new Locale("en", "NG");
				NumberFormat nf = NumberFormat.getCurrencyInstance(locale);	 
				
				LoanApplication loan_app = new LoanApplication();
				loan_app.setAccountNumber(rs.getString(2));
				loan_app.setFullName(rs.getString(3)+" "+ rs.getString(4));
				loan_app.setDateOfBirth(rs.getDate(5).toString());
				loan_app.setBVN(rs.getString(6));
				loan_app.setPhone(rs.getString(7));
				loan_app.setApplicationId(rs.getString(8));
				loan_app.setApplicationDate(rs.getDate(9).toString());	
				if(rs.getString(10).equalsIgnoreCase("gindiri") || rs.getString(10).equalsIgnoreCase("head office")) {
					loan_app.setBranch(rs.getString(10));
				}else if(rs.getString(10).equalsIgnoreCase("cocin headquarters")) {
					loan_app.setBranch("Cocin Hqtrs");
				}
				
							
				loan_app.setLoanFacility(rs.getDouble(11));
				loan_app.setFacilityApplied(""+nf.format(rs.getDouble(11)));
				loan_app.setAmountInWords(rs.getString(12));
				loan_app.setLoanType(rs.getString(13));
				loan_app.setPurpose(rs.getString(14));
				loan_app.setTenor(rs.getString(15));
				loan_app.setSourceOfRepayment(rs.getString(16));
				loan_app.setMarketingOfficer(rs.getString(17));
				loan_app.setApplicationStatus(rs.getString(18));
				loan_app.setAddress(rs.getString(19));
				
				list.add(loan_app);
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return list;
	}
	
	
	public static ArrayList<LoanOfferView> getLoanOffersReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate){
		ArrayList<LoanOfferView> list = new ArrayList<LoanOfferView>();
		try {
			
			Connection connection = DbConnection.getDbConnection();
			String query = "";
			
				if(column_list.size()==0) {
				query = "SELECT * FROM loan_offers_view "
						+ "WHERE application_date between '"+fromDate+"' AND '"+todateDate+"' ";
				}else if (column_list.size() == 1) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (application_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND "+column_list.get(0) +" = '"+data_list.get(0)+"' ";
				}else if (column_list.size() == 2) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (application_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') ";
				}else if (column_list.size() == 3) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (application_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND ("+column_list.get(2) +" = '"+data_list.get(2)+"') ";
				}else if (column_list.size() == 4) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (application_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND ("+column_list.get(2) +" = '"+data_list.get(2)+"') "
									+ "AND ("+column_list.get(3) +" = '"+data_list.get(3)+"') ";
				}
			
				//System.out.println(query);
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
				Locale locale = new Locale("en", "NG");
				NumberFormat nf = NumberFormat.getCurrencyInstance(locale);	 
				
				LoanOfferView offer = new LoanOfferView();
				
				
				offer.setFullName(rs.getString(3)+" "+rs.getString(4));
				offer.setAccountNo(rs.getString(9));
				offer.setApplicationId(rs.getString(10));				
				offer.setApplicationDate(rs.getDate(11).toString());
								
				if(rs.getString(12).equalsIgnoreCase("gindiri") || rs.getString(12).equalsIgnoreCase("head office")) {
					offer.setBranch(rs.getString(12));
				}else if(rs.getString(12).equalsIgnoreCase("cocin headquarters")) {
					offer.setBranch("Cocin Hqtrs");
				}
				
				offer.setApplicationStatus(rs.getString(18));
				offer.setAmountApproved(rs.getDouble(19));				
				offer.setTenor(rs.getInt(20)+" "+rs.getString(21));
				offer.setCreditOfficer(rs.getString(31));
				offer.setFacilityApproved(nf.format(rs.getDouble(19)));
				
				
				list.add(offer);
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return list;
	}
	
	public static ArrayList<LoanOfferView> getLoanAuditReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate){
		ArrayList<LoanOfferView> list = new ArrayList<LoanOfferView>();
		try {
			
			Connection connection = DbConnection.getDbConnection();
			String query = "";
			
				if(column_list.size()==0) {
				query = "SELECT * FROM loan_offers_view "
						+ "WHERE audit_date between '"+fromDate+"' AND '"+todateDate+"' ";
				}else if (column_list.size() == 1) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (audit_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND "+column_list.get(0) +" = '"+data_list.get(0)+"' ";
				}else if (column_list.size() == 2) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (audit_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') ";
				}else if (column_list.size() == 3) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (audit_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND ("+column_list.get(2) +" = '"+data_list.get(2)+"') ";
				}else if (column_list.size() == 4) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (audit_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND ("+column_list.get(2) +" = '"+data_list.get(2)+"') "
									+ "AND ("+column_list.get(3) +" = '"+data_list.get(3)+"') ";
				}
			
				//System.out.println(query);
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
				new Locale("");
				NumberFormat nf = NumberFormat.getCurrencyInstance();	 
				DecimalFormatSymbols dfs = ((DecimalFormat) nf).getDecimalFormatSymbols();
				dfs.setCurrencySymbol("");
				((DecimalFormat) nf).setDecimalFormatSymbols(dfs);
				
				
				LoanOfferView offer = new LoanOfferView();
				
				
				offer.setFullName(rs.getString(3)+" "+rs.getString(4));
				offer.setAccountNo(rs.getString(9));
				offer.setApplicationId(rs.getString(10));				
				offer.setApplicationDate(rs.getDate(11).toString());
								
				if(rs.getString(12).equalsIgnoreCase("gindiri") || rs.getString(12).equalsIgnoreCase("head office")) {
					offer.setBranch(rs.getString(12));
				}else if(rs.getString(12).equalsIgnoreCase("cocin headquarters")) {
					offer.setBranch("Cocin Hqtrs");
				}
				
				offer.setApplicationStatus(rs.getString(18));
				offer.setAmountApproved(rs.getDouble(19));				
				offer.setTenor(rs.getInt(20)+" "+rs.getString(21));
				offer.setCreditOfficer(rs.getString(31));
				offer.setFacilityApproved(nf.format(rs.getDouble(19)));
				offer.setAuditedBy(rs.getString(39));
				offer.setAuditDate(rs.getDate(38).toString());
				offer.setMaturity(rs.getDate(34) +"");
				offer.setMaturityDate(rs.getDate(34) +"");
				
				
				
				list.add(offer);
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return list;
	}
	
	public static ArrayList<LoanOfferView> getLoanDisburseReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate){
		ArrayList<LoanOfferView> list = new ArrayList<LoanOfferView>();
		try {
			
			Connection connection = DbConnection.getDbConnection();
			String query = "";
			
				if(column_list.size()==0) {
				query = "SELECT * FROM loan_offers_view "
						+ "WHERE disbursement_date between '"+fromDate+"' AND '"+todateDate+"' ";
				}else if (column_list.size() == 1) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (disbursement_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND "+column_list.get(0) +" = '"+data_list.get(0)+"' ";
				}else if (column_list.size() == 2) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (disbursement_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') ";
				}else if (column_list.size() == 3) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (disbursement_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND ("+column_list.get(2) +" = '"+data_list.get(2)+"') ";
				}else if (column_list.size() == 4) {
					query = "SELECT * FROM loan_offers_view "
							+ "WHERE (disbursement_date between '"+fromDate+"' AND '"+todateDate+"') "
									+ "AND ("+column_list.get(0) +" = '"+data_list.get(0)+"') "
									+ "AND ("+column_list.get(1) +" = '"+data_list.get(1)+"') "
									+ "AND ("+column_list.get(2) +" = '"+data_list.get(2)+"') "
									+ "AND ("+column_list.get(3) +" = '"+data_list.get(3)+"') ";
				}
			
				//System.out.println(query);
			Statement statement = connection.createStatement();
			ResultSet rs= statement.executeQuery(query);
			
			while(rs.next()) {
				new Locale("");
				NumberFormat nf = NumberFormat.getCurrencyInstance();	 
				DecimalFormatSymbols dfs = ((DecimalFormat) nf).getDecimalFormatSymbols();
				dfs.setCurrencySymbol("");
				((DecimalFormat) nf).setDecimalFormatSymbols(dfs);
				
				
				LoanOfferView offer = new LoanOfferView();
				
				
				offer.setFullName(rs.getString(3)+" "+rs.getString(4));
				offer.setAccountNo(rs.getString(9));
				offer.setApplicationId(rs.getString(10));				
				offer.setApplicationDate(rs.getDate(11).toString());
								
				if(rs.getString(12).equalsIgnoreCase("gindiri") || rs.getString(12).equalsIgnoreCase("head office")) {
					offer.setBranch(rs.getString(12));
				}else if(rs.getString(12).equalsIgnoreCase("cocin headquarters")) {
					offer.setBranch("Cocin Hqtrs");
				}
				
				offer.setApplicationStatus(rs.getString(18));
				offer.setAmountApproved(rs.getDouble(19));				
				offer.setTenor(rs.getInt(20)+" "+rs.getString(21));
				offer.setCreditOfficer(rs.getString(31));
				offer.setFacilityApproved(nf.format(rs.getDouble(19)));
				offer.setAuditedBy(rs.getString(39));
				offer.setAuditDate(rs.getDate(38).toString());
				offer.setMaturity(rs.getDate(34) +"");
				offer.setDisburseBy(rs.getString(41));
				offer.setDisbursementDate(rs.getDate(33) + "");				
				
				list.add(offer);
			}
			
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return list;
	}
	
	
	// Server method to get a loan offer view
	public ArrayList<LoanOfferView> getLoanOfferView(String application_id) {
		ArrayList<LoanOfferView> offer_list = new ArrayList<LoanOfferView>();
		try {
		
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from loan_offers_view "
							+ "WHERE application_id = '"+application_id+"' ";
			Statement st = conn.createStatement();			
			ResultSet rs = st.executeQuery(query);
			
			
			while(rs.next()) {	
				Locale locale = new Locale("en", "NG");
				NumberFormat nf = NumberFormat.getCurrencyInstance(locale);	 
				
				LoanOfferView offer = new LoanOfferView();
				offer.setCustomerId(rs.getString(1));
				offer.setTitle(rs.getString(2));
				offer.setSurname(rs.getString(3));
				offer.setOthernames(rs.getString(4));
				offer.setDob(rs.getDate(5).toString());
				offer.setBvn(rs.getString(6));
				offer.setPhoneNo(rs.getString(7));
				offer.setAddress(rs.getString(8));
				offer.setAccountNo(rs.getString(9));
				offer.setApplicationId(rs.getString(10));
				offer.setApplicationDate(rs.getDate(11).toString());
				offer.setBranch(rs.getString(12));
				offer.setFacilityRequested(rs.getDouble(13));
				offer.setBusiness(rs.getString(14));
				offer.setPurpose(rs.getString(15));
				offer.setSor(rs.getString(16));
				offer.setMarketingOfficer(rs.getString(17));
				offer.setApplicationStatus(rs.getString(18));
				offer.setAmountApproved(rs.getDouble(19));
				offer.setTenorApproved(rs.getInt(20));
				offer.setTenorTypeApproved(rs.getString(21));
				offer.setAmountInWords(rs.getString(22));
				offer.setLoanProduct(rs.getString(23));
				offer.setInterestType(rs.getString(24));
				offer.setClearanceLevel(rs.getInt(25));
				offer.setInterestRate(rs.getDouble(26));
				offer.setManagementFeeRate(rs.getDouble(27));
				offer.setMonitoringFeeRate(rs.getDouble(28));
				offer.setRiskPremiumRate(rs.getDouble(29));
				offer.setCompulsorySavingsRate(rs.getDouble(30));
				offer.setCreditOfficer(rs.getString(31));
				offer.setApprovalDate(rs.getDate(32)+"");
				offer.setDisbursementDate(rs.getDate(33)+"");
				offer.setMaturity(rs.getDate(34)+"");
				offer.setReferenceNo("Our Ref: LMFB/CM/"+rs.getString(35));
				
				if(rs.getString(37).equalsIgnoreCase("M")) {
					offer.setGender("Dear Sir,");
				}else if(rs.getString(37).equalsIgnoreCase("F")) {
					offer.setGender("Dear Ma,");
				}
					
				
				offer.setFullName(rs.getString(2).toUpperCase()+". "+rs.getString(3).toUpperCase()+" "+ rs.getString(4).toUpperCase());
				offer.setPrincipalIntroduction("We are pleased to inform you that " + nf.format(rs.getDouble(19)) +"(" + rs.getString(22) +")" 
				+" only has been approved for you under the following terms and conditions: ");
				
				offer.setFacilityApplied(nf.format(rs.getDouble(13))+ " (" +rs.getString(36)+") only");
				offer.setFacilityApproved(nf.format(rs.getDouble(19))+" (" + rs.getString(22)+") only");
				
				offer.setPricingInterest("1. Interest of "+ rs.getDouble(26)+ "% "+rs.getString(24));
				String rep = "";
				if(rs.getString(21).equalsIgnoreCase("weeks")) {
					rep = "Weekly";
				}else if(rs.getString(21).equalsIgnoreCase("months")) {
					rep = "Monthly";
				}
				offer.setPricingMonFee("2. Monitoring fee... "+rs.getDouble(28)+"% flat of Interest "+rep);
				offer.setPricingRiskPremium("3. Risk Premium... "+rs.getDouble(29)+"% flat of Interest "+rep);
				offer.setDefaultPenalty("4. Default Penalty 1% flat on default amount");
				offer.setPricingMgtFee("5. Management fee of "+rs.getDouble(27)+"% upfront");				
				offer.setTenor(rs.getInt(20)+" "+rs.getString(21));
				
				java.sql.Date sql_date = rs.getDate(34);
				java.util.Date j_date = new java.util.Date(sql_date.getTime());
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");			
				offer.setMaturityDate(sdf.format(j_date));
				
				
				LoanOfferPrice ofx = ServerAlgorithm.calculateOfferPricing(offer);
				
				offer.setRepaymentSource(rep +" equal installment of "+nf.format(ofx.getTotalInstallmentRepayment())+"/ "+rs.getString(16));
				offer.setTermlyRepaymentHeader(rep.toUpperCase()+" REPAYMENTS");
				offer.setFullRepaymentHeader((rs.getInt(20)   +" "+rs.getString(21)+" REPAYMENT").toUpperCase());
				
				offer.setInstallmentPrincipal(nf.format(ofx.getInstallmentPaymentPrincipal()));
				offer.setInstallmentInterestCharges(nf.format(ofx.getInstallmentPaymentInterestCharges()));
				offer.setInstallmentCompulsorySavings(nf.format(ofx.getCompulsorySavings()));
				offer.setTotalInstallmentRepayment(nf.format(ofx.getTotalInstallmentRepayment()));
				
				offer.setTotalInterestCharges(nf.format(ofx.getTotalInterestCharges()));
				offer.setTotalCompulsorySavings(nf.format(ofx.getTotalCompulsorySavings()));
				offer.setTotalRepayment(nf.format(ofx.getTotalRepayment()));
				offer.setPrincipal(nf.format(rs.getDouble(19)));
				
				offer_list.add(offer);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
 		
		return offer_list;
		}


	public ArrayList<LoanApplication> getLoanApplication(String application_id){
		ArrayList<LoanApplication> application_list = new ArrayList<LoanApplication>();
		try {
		
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from loan_applications_view "
							+ "WHERE application_id = '"+application_id+"' ";
			Statement st = conn.createStatement();			
			ResultSet rs = st.executeQuery(query);
			
			
			while(rs.next()) {	
				Locale locale = new Locale("en", "NG");
				NumberFormat nf = NumberFormat.getCurrencyInstance(locale);	 
				
				LoanApplication loan_app = new LoanApplication();
				loan_app.setAccountNumber(rs.getString(2));
				loan_app.setFullName(rs.getString(3)+" "+ rs.getString(4));
				loan_app.setDateOfBirth(rs.getDate(5).toString());
				loan_app.setBVN(rs.getString(6));
				loan_app.setPhone(rs.getString(7));
				loan_app.setApplicationId(rs.getString(8));
				loan_app.setApplicationDate(rs.getDate(9).toString());
				
				if(rs.getString(10).equalsIgnoreCase("gindiri") || rs.getString(10).equalsIgnoreCase("cocin headquaters")) {
					loan_app.setBranch(rs.getString(10)+" Branch");
				}else if(rs.getString(10).equalsIgnoreCase("head office")) {
					loan_app.setBranch("Corporate "+rs.getString(10)+ " Branch");
				}
				
				loan_app.setLoanFacility(rs.getDouble(11));
				loan_app.setFacilityApplied("APPLICATION FOR LOAN FACILITY: "+nf.format(rs.getDouble(11)));
				loan_app.setAmountInWords(rs.getString(12));
				loan_app.setLoanType(rs.getString(13));
				loan_app.setPurpose(rs.getString(14));
				loan_app.setTenor(rs.getString(15));
				loan_app.setSourceOfRepayment(rs.getString(16));
				loan_app.setMarketingOfficer(rs.getString(17));
				loan_app.setApplicationStatus(rs.getString(18));
				loan_app.setAddress(rs.getString(19));
				
				String query2 = "select gender from app_users_view where branch_name = ? and group_name = ? ";
				
				PreparedStatement pst = conn.prepareStatement(query2);
				pst.setString(1, rs.getString(10));
				pst.setString(2, "Branch Manager");
				
				ResultSet rs1 = pst.executeQuery();
				
				while(rs1.next()) {
					if(rs1.getString(1).equalsIgnoreCase("M")) {
						loan_app.setBmGender("Dear Sir,");
					}else if(rs1.getString(1).equalsIgnoreCase("F")) {
						loan_app.setBmGender("Dear Ma,");
					}
				}
				
				application_list.add(loan_app);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
 		
		return application_list;
		
		
	}
	
	
	public ArrayList<LoanSecurity> getLoanSecurities(String application_id){
		ArrayList<LoanSecurity> security_list = new ArrayList<LoanSecurity>();
		try {
		
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from loan_securities "
					+ "WHERE application_id = '"+application_id+"' ";
			Statement st = conn.prepareStatement(query)	;
			//st.setString(1, application_id);
			ResultSet rs = st.executeQuery(query);
			
			int counter = 1;
			while(rs.next()) {	
				
				LoanSecurity security = new LoanSecurity();
				security.setSecurity(counter+". "+rs.getString(3));
				
				security_list.add(security);
				counter++;
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
 		
		return security_list;
		
		
	}

	
	
	

}
