package com.llp.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.lang.time.*;



import com.llp.api.LLPMainInterface;
import com.llp.dbconnection.DbConnection;
import com.llp.entities.AORecommendationNote;
import com.llp.entities.AccountTurnover;
import com.llp.entities.AppUserGroup;
import com.llp.entities.ApprovalDisbursementParameters;
import com.llp.entities.BusinessCategory;
import com.llp.entities.CollateralNote;
import com.llp.entities.Customer;
import com.llp.entities.DocumentationParameters;
import com.llp.entities.FinanceItem;
import com.llp.entities.FinancialStatement;
import com.llp.entities.InterviewSession;
import com.llp.entities.LightMessage;
import com.llp.entities.LoanAnalysisParameters;
import com.llp.entities.LoanApplication;
import com.llp.entities.LoanOffer;
import com.llp.entities.LoanOfferView;
import com.llp.entities.LoanProduct;
import com.llp.entities.MarketingQuestion;
import com.llp.entities.OtherBankAccount;
import com.llp.entities.OutstandingFacility;
import com.llp.entities.QuestionAnswer;
import com.llp.entities.User;
import java.text.ParseException;

public class LLPMainImplementation implements LLPMainInterface {

	@Override
	public User getUserLogin(String username, String password, String branch_name) throws RemoteException {
		User user = new User();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from app_users_view "
							+ "WHERE username = '" +username +"' "
							+ "AND password = '"+password+"' "
							+ "AND branch_name = '"+branch_name+"' ";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {			
				
				user.setUserId(rs.getString(1));
				user.setFullName(rs.getString(2));
				user.setUsername(rs.getString(3));
				user.setUserGroup(rs.getString(6));
				user.setBranch(rs.getString(8));
				user.setUserStatus(rs.getInt(10));
				user.setPassword(rs.getString(4));
				
			}
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}		
		return user;
	}

	@Override
	public void changePassword(String new_password, User user) throws RemoteException {
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE application_users "
									+" SET password = ?  "
									+" WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, new_password);		
			
			ps.setString(2, user.getUserId());
			
			ps.executeUpdate();
			conn.close();
		}catch(Exception ex) { 
			ex.printStackTrace();
			}
		
	}

	public ArrayList<String> createCustomerProfile(Customer customer, ArrayList<OtherBankAccount> bank_list) throws RemoteException, ParseException {
		ArrayList<String> list = new ArrayList<>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");			
		Date date = formatter.parse(customer.getDob());				
		java.sql.Date sql_date = new java.sql.Date(date.getTime());			
		
		String error_message = null;
		String customer_id = null;
		int value = 0;
		
		try {
		Connection conn = DbConnection.getDbConnection();
		String query = "select * "
				+ "from id_generator "
				+ "where ref_table = 'customer_profile' ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			value = rs.getInt("value");
			String idValue = value + "";
			customer_id = "cp"+ leftPad(idValue, 6);
			
			String insert_query = "insert into customer_profile(customer_id, account_no, surname, othernames, birth_date, bvn, phone_no, no_of_wives, no_of_children, "
					+ "no_of_child_in_sch, address, branch, title, gender, user)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, customer_id);
			pst.setString(2, customer.getLmfbAccountNo());
			pst.setString(3,customer.getSurname());
			pst.setString(4, customer.getOthernames());
			pst.setDate(5, sql_date);
			pst.setString(6, customer.getBvn());
			pst.setString(7, customer.getPhone());
			pst.setInt(8, customer.getNoOfWives());
			pst.setInt(9, customer.getNoOfChildren());
			pst.setInt(10, customer.getNoOfSchoolChildren());
			pst.setString(11, customer.getAddress());
			pst.setString(12, customer.getBranch());
			pst.setString(13, customer.getTitle());
			pst.setString(14, customer.getGender());
			pst.setString(15, customer.getUser());
			
			pst.execute();	
			
			
			String insert_query_2 = "INSERT INTO other_bank_accounts(customer_id, account_name, other_account_no, account_type, bank) VALUES (?,?,?,?,?)";
			 PreparedStatement ps1 = conn.prepareStatement(insert_query_2);
			 
			 for(int i=0; i<bank_list.size(); i++) {
				 ps1.setString(1, customer_id);
				 ps1.setString(2, bank_list.get(i).getOtherAccountName());
				 ps1.setString(3, bank_list.get(i).getOtherAccountNo());
				 ps1.setString(4, bank_list.get(i).getOtherAccountType());
				 ps1.setString(5, bank_list.get(i).getOtherBank());
				  ps1.addBatch();
			 }

			 int[] results = ps1.executeBatch();
			
		}
		
		String update_query = "UPDATE id_generator "
								+ "set value = ? "
								+ "where ref_table = 'customer_profile' ";
		
		PreparedStatement ps = conn.prepareStatement(update_query);
		ps.setInt(1, (value+1));
		ps.executeUpdate();
		conn.close();
		
		}catch(SQLIntegrityConstraintViolationException ex) { error_message = "Account number already exists."; }
		catch(NullPointerException ex) {error_message = "Unable to connect to server. ";}
		catch(Exception ex) { ex.printStackTrace(); }		
		
		list.add(customer_id);
		list.add(error_message);		
		
		return list;
	}

	public String leftPad(String value, int lgt) {
		int diff = lgt - value.length();
		
		for(int i=0; i<diff; i++) {
			value = "0" + value;
		}				
		return value;
	}

	@Override
	public void updateCustomerProfile(Customer customer, ArrayList<OtherBankAccount> bank_list) throws RemoteException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");			
		Date date;
		java.sql.Date sql_date = null;
		try {
			date = formatter.parse(customer.getDob());
			sql_date = new java.sql.Date(date.getTime());	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		
		try {		
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE customer_profile "
									+" SET surname = ?, othernames = ?, address =?, birth_date = ?, bvn = ?, "
									+ "phone_no = ?, no_of_wives =?, no_of_children = ?, no_of_child_in_sch = ? , title = ?, gender = ? "
									+" WHERE customer_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(update_query);
			
			ps.setString(1, customer.getSurname());
			ps.setString(2, customer.getOthernames());
			ps.setString(3, customer.getAddress());
			ps.setDate(4, sql_date);	
			ps.setString(5, customer.getBvn());
			ps.setString(6, customer.getPhone());
			ps.setInt(7, customer.getNoOfWives());
			ps.setInt(8, customer.getNoOfChildren());
			ps.setInt(9, customer.getNoOfSchoolChildren());
			ps.setString(10, customer.getTitle());
			ps.setString(11, customer.getGender());	
			ps.setString(12, customer.getCustomerId());
			
			ps.executeUpdate();
			
			String delete_query = "DELETE FROM other_bank_accounts WHERE customer_id = '"+ customer.getCustomerId()+"' ";
			PreparedStatement ps2 = conn.prepareStatement(delete_query);
			ps2.execute();
			
			String insert_query_2 = "INSERT INTO other_bank_accounts(customer_id, account_name, other_account_no, account_type, bank) VALUES (?,?,?,?,?)";
			 PreparedStatement ps1 = conn.prepareStatement(insert_query_2);
			 
			 for(int i=0; i<bank_list.size(); i++) {
				 ps1.setString(1, customer.getCustomerId());
				 ps1.setString(2, bank_list.get(i).getOtherAccountName());
				 ps1.setString(3, bank_list.get(i).getOtherAccountNo());
				 ps1.setString(4, bank_list.get(i).getOtherAccountType());
				 ps1.setString(5, bank_list.get(i).getOtherBank());
				  ps1.addBatch();
			 }

			 int[] results = ps1.executeBatch();
			 conn.close();
			
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
	}

	@Override
	public ArrayList<Customer> getCustomerList(String search_criterion, String search_text) throws RemoteException {
		ArrayList<Customer> customer_list = new ArrayList<Customer>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = " ";
			
			if(search_criterion.equalsIgnoreCase("customer_name")) {
				query = "select * from customer_profile "+ "where surname like '%"+search_text+"%' "+ " or othernames like '%"+search_text+"%' ORDER BY 5";
			}else {
				query = "select * from customer_profile where "+ search_criterion+ " = '"+search_text+"' ORDER BY 5";
			}
			
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setCustomerId(rs.getString(2));
				customer.setLmfbAccountNo(rs.getString(3));
				customer.setSurname(rs.getString(5));
				customer.setOthernames(rs.getString(6));
				customer.setDob(rs.getDate(8).toString());
				customer.setBvn(rs.getString(9));
				customer.setPhone(rs.getString(10));
				customer.setNoOfWives(rs.getInt(11));
				customer.setNoOfChildren(rs.getInt(12));
				customer.setNoOfSchoolChildren(rs.getInt(13));
				customer.setAddress(rs.getString(14));
				customer.setBranch(rs.getString(16));
				customer.setGender(rs.getString(7));
				customer.setTitle(rs.getString(4));
				customer.setUser(rs.getString(15));
				
				
				customer_list.add(customer);
			}
			
			conn.close();
			
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return customer_list;
	}

	@Override
	public ArrayList<OtherBankAccount> getOtherBankAccounts(String customer_id) throws RemoteException {
		ArrayList<OtherBankAccount> bank_list = new ArrayList<>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = " SELECT * FROM other_bank_accounts WHERE customer_id = '"+ customer_id+"' ";		
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				OtherBankAccount account = new OtherBankAccount();
				account.setOtherAccountName(rs.getString(3));
				account.setOtherAccountNo(rs.getString(4));
				account.setOtherAccountType(rs.getString(5));
				account.setOtherBank(rs.getString(6));
				
				bank_list.add(account);
			}
			conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }		
		
		return bank_list;
	}


	@Override
	public String saveLoanApplication(LoanApplication loan_application, ArrayList<String> security_list) throws RemoteException {
		String application_id = null;
		int value = 0;
		String error_message = null;
		
		try {
		Connection conn = DbConnection.getDbConnection();
		String query = "select * "
				+ "from id_generator "
				+ "where ref_table = 'loan_applications' ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			value = rs.getInt("value");
			String idValue = value + "";
			application_id = "la"+ leftPad( idValue, 6);
			
			
			String insert_query = "insert into loan_applications(application_id, application_date, account_no, branch, loan_facility, "
					+ "amount_in_words, loan_type, purpose, tenor, sources_of_repayment, application_status, marketing_officer, clear_level, search_status, "
					+ "recommended_amount, recommended_in_words, comment, customer_id) "
					+ " VALUES(?, curdate(), ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			
			pst.setString(1, application_id);			
			pst.setString(2, loan_application.getAccountNumber());
			pst.setString(3, loan_application.getBranch());
			pst.setDouble(4, loan_application.getLoanFacility());
			pst.setString(5, loan_application.getAmountInWords());
			pst.setString(6, loan_application.getLoanType());
			pst.setString(7, loan_application.getPurpose());
			pst.setString(9, loan_application.getSourceOfRepayment());
			pst.setString(8, loan_application.getTenor());
			pst.setString(10, "pending");
			pst.setString(11, loan_application.getMarketingOfficer());
			pst.setInt(12, loan_application.getClearance());
			pst.setString(13, loan_application.getSearchStatus());
			pst.setDouble(14, 0.0);
			pst.setString(15, "");
			pst.setString(16, "");
			pst.setString(17, loan_application.getCustomerId());
			
			pst.execute();	
			
			
			String insert_query_2 = "INSERT INTO loan_securities(application_id, security_item) VALUES (?, ?)";
			 PreparedStatement ps1 = conn.prepareStatement(insert_query_2);
			 
			 for(int i=0; i<security_list.size(); i++) {
				 ps1.setString(1, application_id);
				  ps1.setString(2, security_list.get(i));
				  ps1.addBatch();
			 }

			 int[] results = ps1.executeBatch();	
			
		}
		
		String update_query = "UPDATE id_generator "
								+ "set value = ? "
								+ "where ref_table = 'loan_applications' ";
		
		PreparedStatement ps = conn.prepareStatement(update_query);
		ps.setInt(1, (value+1));
		ps.executeUpdate();
		
		conn.close();
		}catch(SQLIntegrityConstraintViolationException ex) { 
			try {
				Connection conn = DbConnection.getDbConnection();
				String query = "select * "
						+ "from id_generator "
						+ "where ref_table = 'loan_applications' ";
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				
				while(rs.next()) {
					value = rs.getInt("value");				
				} 
				String update_query = "UPDATE id_generator "
						+ "set value = ? "
						+ "where ref_table = 'loan_applications' ";

				PreparedStatement ps = conn.prepareStatement(update_query);
				ps.setInt(1, (value+1));
				ps.executeUpdate();
				application_id  = null;
				conn.close();			
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return application_id;
	}
	
	public void updateSecurities(String application_id, ArrayList<String> security_list) throws RemoteException{		
		 
		 try {
			 Connection conn = DbConnection.getDbConnection();
			 
			 String delete_query = "DELETE FROM loan_securities WHERE application_id = '"+ application_id+"' ";
			PreparedStatement ps2 = conn.prepareStatement(delete_query);
			ps2.execute();
		 
			String insert_query_2 = "INSERT INTO loan_securities(application_id, security_item) VALUES (?, ?)";
			 PreparedStatement ps1 = conn.prepareStatement(insert_query_2);
			 
			 for(int i=0; i<security_list.size(); i++) {
				 ps1.setString(1, application_id);
				  ps1.setString(2, security_list.get(i));
				  ps1.addBatch();
			 }

			 int[] results = ps1.executeBatch();
			 conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateLoanApplication(LoanApplication loan_application, ArrayList<String> security_list) throws RemoteException {
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE loan_applications "
									+" SET loan_facility = ?, amount_in_words = ?,  loan_type = ?,  purpose = ?, tenor = ?, "
									+ "sources_of_repayment = ?, clear_level = ?, application_status = ? "
									+" WHERE application_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setDouble(1, loan_application.getLoanFacility());
			ps.setString(2, loan_application.getAmountInWords());
			ps.setString(3, loan_application.getLoanType());
			ps.setString(4, loan_application.getPurpose());
			ps.setString(5, loan_application.getTenor());
			ps.setString(6, loan_application.getSourceOfRepayment());			
			ps.setInt(7, loan_application.getClearance());
			ps.setString(8, loan_application.getApplicationStatus());
			
			ps.setString(9, loan_application.getApplicationId());		
			
			ps.executeUpdate();
		
			
			String delete_query = "DELETE FROM loan_securities WHERE application_id = '"+ loan_application.getApplicationId()+"' ";
			PreparedStatement ps2 = conn.prepareStatement(delete_query);
			ps2.execute();
			
			String delete_query2 = "DELETE FROM rejected_loans WHERE application_id = '"+ loan_application.getApplicationId()+"' ";
			PreparedStatement ps3 = conn.prepareStatement(delete_query2);
			ps2.execute();
			
			String insert_query_2 = "INSERT INTO loan_securities(application_id, security_item) VALUES (?, ?)";
			 PreparedStatement ps1 = conn.prepareStatement(insert_query_2);
			 
			 for(int i=0; i<security_list.size(); i++) {
				 ps1.setString(1, loan_application.getApplicationId());
				  ps1.setString(2, security_list.get(i));
				  ps1.addBatch();
			 }

			 int[] results = ps1.executeBatch();	
			
			
			
			conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }
	}

	@Override
	public void updateLoanApplicationStatus(String application_id, String status, String column_name, String value, String column2) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE loan_applications "
									+" SET application_status = ?, "+column_name+ " = ?, " +column2+" = curdate()"
									+" WHERE application_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, status);
			ps.setString(2, value);
			ps.setString(3, application_id);	
			
			ps.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void updateLoanApplicationStatus(String application_id, String status) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE loan_applications "
									+" SET application_status = ? "
									+" WHERE application_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, status);
			ps.setString(2, application_id);	
			
			ps.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void updateLoanRecommededAmount(LoanApplication application) throws RemoteException {
	
		try {
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE loan_applications "
									+" SET clear_level = ?, recommended_amount = ?, recommended_in_words = ?, comment = ? "
									+" WHERE application_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setInt(1, application.getClearance());
			ps.setDouble(2, application.getFacilityRecommended());
			ps.setString(3, application.getFacilityRecommendedInWords());
			ps.setString(4, application.getComment());
			ps.setString(5, application.getApplicationId());			
			
			ps.executeUpdate();
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public ArrayList<LoanApplication> getApplicationList(User user, String search_criterion, String search_text) throws RemoteException {
		ArrayList<LoanApplication> application_list = new ArrayList<LoanApplication>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = " ";
			
			
			if (user.getUserGroup().equalsIgnoreCase("Managing Director")) {
				
				if(search_criterion.equalsIgnoreCase("customer_name")) {
					query = "select * from loan_applications_view "
							+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) "  
							+ "and application_status = 'recommended' ORDER BY 8"; 							
							
				}else if(search_criterion.equalsIgnoreCase("marketing_officer")) {
					query = "select * from loan_applications_view "
							+ "where marketing_officer like '%"+search_text+"%' "
							+ "and application_status = 'recommended' ORDER BY 8";						
				}else {
					query = "select * from loan_applications_view "
							+ "where "+ search_criterion+ " = '"+search_text+"' "
							+ "and application_status = 'recommended' ORDER BY 8";
						
				}
				
			}else if (user.getUserGroup().equalsIgnoreCase("System Administrator")) {
				
				if(search_criterion.equalsIgnoreCase("customer_name")) {
					query = "select * from loan_applications_view "
							+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) "  
							+ " ORDER BY 8"; 							
							
				}else if(search_criterion.equalsIgnoreCase("marketing_officer")) {
					query = "select * from loan_applications_view "
							+ "where marketing_officer like '%"+search_text+"%' "
							+ "ORDER BY 8";						
				}else {
					query = "select * from loan_applications_view "
							+ "where "+ search_criterion+ " = '"+search_text+"' "
							+ "ORDER BY 8";
						
				}
				
			}else if (user.getUserGroup().equalsIgnoreCase("Branch Manager")){
				
				if(search_criterion.equalsIgnoreCase("customer_name")) {
					query = "select * from loan_applications_view "
							+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) "  
							+ "and application_status = 'forwarded' " 
							+ "and branch = '"+ user.getBranch()+"' ORDER BY 8";
							
				}else if(search_criterion.equalsIgnoreCase("marketing_officer")) {
					query = "select * from loan_applications_view "
							+ "where marketing_officer like '%"+search_text+"%' and application_status = 'forwarded' "
							+ " and branch = '"+ user.getBranch()+"' ORDER BY 8"; 						
				}else {
					query = "select * from loan_applications_view "
							+ "where "+ search_criterion+ " = '"+search_text+"' "
							+ "and application_status = 'forwarded' "
							+ " and branch = '"+ user.getBranch()+"' ORDER BY 8";
				}
			}else  if (user.getUserGroup().equalsIgnoreCase("Head of Credit")){
				
				if(search_criterion.equalsIgnoreCase("customer_name")) {
					query = "select * from loan_applications_view "
							+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) "  
							+ "and application_status = 'approved' ORDER BY 8" ;							
				}else if(search_criterion.equalsIgnoreCase("marketing_officer")) {
					query = "select * from loan_applications_view "
							+ "where marketing_officer like '%"+search_text+"%' and application_status = 'approved' ORDER BY 8";							 						
				}else {
					query = "select * from loan_applications_view "
							+ "where "+ search_criterion+ " = '"+search_text+"' "
							+ "and application_status = 'approved' ORDER BY 8";						
				}
			}else  if (user.getUserGroup().equalsIgnoreCase("Credit Officer")){
				
				if(search_criterion.equalsIgnoreCase("customer_name")) {
					query = "select * from loan_applications_view "
							+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) "  
							+ "and application_status = 'approved' " 
							+ "and branch = '"+ user.getBranch()+"' ORDER BY 8";
							
				}else if(search_criterion.equalsIgnoreCase("marketing_officer")) {
					query = "select * from loan_applications_view "
							+ "where marketing_officer like '%"+search_text+"%' and application_status = 'approved' "
							+ " and branch = '"+ user.getBranch()+"' ORDER BY 8"; 						
				}else {
					query = "select * from loan_applications_view "
							+ "where "+ search_criterion+ " = '"+search_text+"' "
							+ "and application_status = 'approved' "
							+ " and branch = '"+ user.getBranch()+"' ORDER BY 8";
				}
			}
			else {				
				if(search_criterion.equalsIgnoreCase("customer_name")) {
					query = "select * from loan_applications_view "
							+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) "
							+ " and (application_status = 'pending' OR application_status = 'returned' OR application_status = 'forwarded' )" 
							+ "and branch = '"+ user.getBranch()+"' ORDER BY 8";
							
							//
				}else if(search_criterion.equalsIgnoreCase("marketing_officer")) {
					query = "select * from loan_applications_view "
							+ "where marketing_officer like '%"+search_text+"%' "
							+ "and (application_status = 'pending'  OR application_status = 'returned' OR application_status = 'forwarded')"
							+ " and branch = '"+ user.getBranch()+"' ORDER BY 8"; 						
				}else {
					query = "select * from loan_applications_view "
							+ "where "+ search_criterion+ " = '"+search_text+"' "
							+ "and (application_status = 'pending'  OR application_status = 'returned' OR application_status = 'forwarded')"
							+ " and branch = '"+ user.getBranch()+"' ORDER BY 8";
				}
				
			}			
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				LoanApplication application = new LoanApplication();
				application.setAccountNumber(rs.getString(2));
				application.setFullName(rs.getString(3) + " " + rs.getString(4));
				application.setApplicationId(rs.getString(8));
				application.setApplicationDate(rs.getDate(9).toString());
				application.setBranch(rs.getString(10));
				application.setLoanFacility(rs.getDouble(11));
				application.setAmountInWords(rs.getString(12));
				application.setLoanType(rs.getString(13));
				application.setPurpose(rs.getString(14));
				application.setTenor(rs.getString(15));
				application.setSourceOfRepayment(rs.getString(16));
				application.setMarketingOfficer(rs.getString(17));
				application.setApplicationStatus(rs.getString(18));
				application.setPhone(rs.getString(7));
				application.setDateOfBirth(rs.getDate(5).toString());
				application.setBVN(rs.getString(6));
				application.setAddress(rs.getString(19));
				application.setClearance(rs.getInt(21));
				application.setSearchStatus(rs.getString(26));
				application.setFacilityRecommended(rs.getDouble(27));
				application.setFacilityRecommendedInWords(rs.getString(28));
				application.setComment(rs.getString(29));
				application.setCustomerId(rs.getString(1));
				
				application_list.add(application);
			}
			
		conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return application_list;
	}

	public ArrayList<LoanApplication> getApplicationsView(User user, String search_criterion, String search_text) throws RemoteException {
		ArrayList<LoanApplication> application_list = new ArrayList<LoanApplication>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = " ";
			
			
			if (user.getUserGroup().equalsIgnoreCase("Managing Director") || user.getUserGroup().equalsIgnoreCase("Head of Credit") ||
					user.getUserGroup().equalsIgnoreCase("System Administrator") ||user.getUserGroup().equalsIgnoreCase("Auditor")) {
				
				if(search_criterion.equalsIgnoreCase("customer_name")) {
					query = "select * from loan_applications_view "
							+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) ORDER BY 8";														
							
				}else if(search_criterion.equalsIgnoreCase("marketing_officer")) {
					query = "select * from loan_applications_view "
							+ "where marketing_officer like '%"+search_text+"%' ORDER BY 8";
											
				}else {
					query = "select * from loan_applications_view "
							+ "where "+ search_criterion+ " = '"+search_text+"' ORDER BY 8";							
				}
				
			}else if (user.getUserGroup().equalsIgnoreCase("Branch Manager") || user.getUserGroup().equalsIgnoreCase("Credit Officer")){
				
				if(search_criterion.equalsIgnoreCase("customer_name")) {
					query = "select * from loan_applications_view "
							+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) "							 
							+ "and branch = '"+ user.getBranch()+"' ORDER BY 8";
							
				}else if(search_criterion.equalsIgnoreCase("marketing_officer")) {
					query = "select * from loan_applications_view "
							+ "where marketing_officer like '%"+search_text+"%' "
							+ " and branch = '"+ user.getBranch()+"' ORDER BY 8"; 						
				}else {
					query = "select * from loan_applications_view "
							+ "where "+ search_criterion+ " = '"+search_text+"' "								
							+ " and branch = '"+ user.getBranch()+"' ORDER BY 8";
				}
				
			
			}else {				
				if(search_criterion.equalsIgnoreCase("customer_name")) {
					query = "select * from loan_applications_view "
							+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) "								
							+ "and branch = '"+ user.getBranch()+"' ORDER BY 8";
							
							//
				}else if(search_criterion.equalsIgnoreCase("marketing_officer")) {
					query = "select * from loan_applications_view "
							+ "where marketing_officer like '%"+search_text+"%' "								
							+ " and branch = '"+ user.getBranch()+"' ORDER BY 8"; 						
				}else {
					query = "select * from loan_applications_view "
							+ "where "+ search_criterion+ " = '"+search_text+"' "								
							+ " and branch = '"+ user.getBranch()+"' ORDER BY 8";
				}
				
			}			
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				LoanApplication application = new LoanApplication();
				application.setAccountNumber(rs.getString(2));
				application.setFullName(rs.getString(3) + " " + rs.getString(4));
				application.setApplicationId(rs.getString(8));
				application.setApplicationDate(rs.getDate(9).toString());
				application.setBranch(rs.getString(10));
				application.setLoanFacility(rs.getDouble(11));
				application.setAmountInWords(rs.getString(12));
				application.setLoanType(rs.getString(13));
				application.setPurpose(rs.getString(14));
				application.setTenor(rs.getString(15));
				application.setSourceOfRepayment(rs.getString(16));
				application.setMarketingOfficer(rs.getString(17));
				application.setApplicationStatus(rs.getString(18));
				application.setPhone(rs.getString(7));
				application.setDateOfBirth(rs.getDate(5).toString());
				application.setBVN(rs.getString(6));
				application.setAddress(rs.getString(19));
				application.setClearance(rs.getInt(21));
				application.setSearchStatus(rs.getString(26));
				application.setFacilityRecommended(rs.getDouble(27));
				application.setFacilityRecommendedInWords(rs.getString(28));
				application.setComment(rs.getString(29));
				application.setCustomerId(rs.getString(1));
				
				application_list.add(application);
			}
			
			conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return application_list;
	}


	public ArrayList<LoanApplication> getApplicationList(User user) throws RemoteException {
		ArrayList<LoanApplication> application_list = new ArrayList<LoanApplication>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "";
			
			if (user.getUserGroup().equalsIgnoreCase("Managing Director")) {				
				query = "select * from loan_applications_view "
						+ "where application_status = 'recommended' ORDER BY 8";						
				
			}else if (user.getUserGroup().equalsIgnoreCase("Branch Manager")){
				query = "select * from loan_applications_view "
						+ "where application_status = 'forwarded'"
						+ " and branch = '"+ user.getBranch()+"' ORDER BY 8";		
				
			}else  if (user.getUserGroup().equalsIgnoreCase("Head of Credit")){
				query = "select * from loan_applications_view "
						+ "where application_status = 'approved' ORDER BY 8";							
				
			}else  if (user.getUserGroup().equalsIgnoreCase("Credit Officer")){
				query = "select * from loan_applications_view "
						+ "where application_status = 'approved' "
						+ " and branch = '"+ user.getBranch()+"' ORDER BY 8";		
				
			}else {				
				query = "select * from loan_applications_view "
						+ "where  (application_status = 'pending'  OR application_status = 'returned' )"
						+ " and branch = '"+ user.getBranch()+"' ORDER BY 8";		
				
			}						
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				LoanApplication application = new LoanApplication();
				application.setAccountNumber(rs.getString(2));
				application.setFullName(rs.getString(3) + " " + rs.getString(4));
				application.setApplicationId(rs.getString(8));
				application.setApplicationDate(rs.getDate(9).toString());
				application.setBranch(rs.getString(10));
				application.setLoanFacility(rs.getDouble(11));
				application.setAmountInWords(rs.getString(12));
				application.setLoanType(rs.getString(13));
				application.setPurpose(rs.getString(14));
				application.setTenor(rs.getString(15));
				application.setSourceOfRepayment(rs.getString(16));
				application.setMarketingOfficer(rs.getString(17));
				application.setApplicationStatus(rs.getString(18));
				application.setPhone(rs.getString(7));
				application.setDateOfBirth(rs.getDate(5).toString());
				application.setBVN(rs.getString(6));
				application.setAddress(rs.getString(19));
				application.setClearance(rs.getInt(21));
				application.setSearchStatus(rs.getString(26));
				application.setFacilityRecommended(rs.getDouble(27));
				application.setFacilityRecommendedInWords(rs.getString(28));
				application.setComment(rs.getString(29));
				application.setCustomerId(rs.getString(1));
				
				application_list.add(application);
			}
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return application_list;
	}

	@Override
	public ArrayList<String> getLoanSecurities(String application_id) throws RemoteException {
		ArrayList<String> security_list = new ArrayList<String>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = " SELECT * FROM loan_securities WHERE application_id = '"+ application_id+"' ";		
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				security_list.add(rs.getString(3));
			}			
			
			conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }		
		
		return security_list;
	}


	@Override
	public String getRejectionNote(String application_id) throws RemoteException {
		String note = "";
		
		try {
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from rejected_loans "
						+ "where  application_id = '"+ application_id+"' ";				
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				note = rs.getString(6);
			}
		conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return note;
	}


	@Override
	public ArrayList<LoanApplication> getPrintableApplications(User user) throws RemoteException {
		ArrayList<LoanApplication> application_list = new ArrayList<LoanApplication>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "";
			
			if (user.getUserGroup().equalsIgnoreCase("Managing Director")) {				
				query = "select * from loan_applications_view "
						+ "where application_status = 'approved' ";						
				
			}else if (user.getUserGroup().equalsIgnoreCase("Branch Manager")){
				query = "select * from loan_applications_view "
						+ "where application_status = 'approved' "
						+ " and branch = '"+ user.getBranch()+"' ";		
				
			}else  if (user.getUserGroup().equalsIgnoreCase("Head of Credit")){
				query = "select * from loan_applications_view "
						+ "where application_status = 'approved' ";							
				
			}else  if (user.getUserGroup().equalsIgnoreCase("Credit Officer")){
				query = "select * from loan_applications_view "
						+ "where application_status = 'approved' "
						+ " and branch = '"+ user.getBranch()+"' ";		
				
			}else {				
				query = "select * from loan_applications_view "
						+ "where application_status = 'approved' "
						+ " and branch = '"+ user.getBranch()+"' ";		
				
			}						
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				LoanApplication application = new LoanApplication();
				application.setAccountNumber(rs.getString(2));
				application.setFullName(rs.getString(3) + " " + rs.getString(4));
				application.setApplicationId(rs.getString(8));
				application.setApplicationDate(rs.getDate(9).toString());
				application.setBranch(rs.getString(10));
				application.setLoanFacility(rs.getDouble(11));
				application.setAmountInWords(rs.getString(12));
				application.setLoanType(rs.getString(13));
				application.setPurpose(rs.getString(14));
				application.setTenor(rs.getString(15));
				application.setSourceOfRepayment(rs.getString(16));
				application.setMarketingOfficer(rs.getString(17));
				application.setApplicationStatus(rs.getString(18));
				application.setPhone(rs.getString(7));
				application.setDateOfBirth(rs.getDate(5).toString());
				application.setBVN(rs.getString(6));
				application.setAddress(rs.getString(19));
				application.setClearance(rs.getInt(21));
				application.setSearchStatus(rs.getString(26));
				application.setFacilityRecommended(rs.getDouble(27));
				application.setFacilityRecommendedInWords(rs.getString(28));
				application.setComment(rs.getString(29));
				
				application_list.add(application);
			}
			
		conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return application_list;
	}


	@Override
	public ArrayList<LoanApplication> getReturnedApplications(User user) throws RemoteException {
		ArrayList<LoanApplication> application_list = new ArrayList<LoanApplication>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "";
			
			if (user.getUserGroup().equalsIgnoreCase("Managing Director")) {				
				query = "select * from loan_applications_view "
						+ "where application_status = 'returned' ";						
				
			}else if (user.getUserGroup().equalsIgnoreCase("Branch Manager")){
				query = "select * from loan_applications_view "
						+ "where where application_status = 'returned' "
						+ " and branch = '"+ user.getBranch()+"' ";		
				
			}else  if (user.getUserGroup().equalsIgnoreCase("Head of Credit")){
				query = "select * from loan_applications_view "
						+ "where application_status = 'returned' ";							
				
			}else  if (user.getUserGroup().equalsIgnoreCase("Credit Officer")){
				query = "select * from loan_applications_view "
						+ "where application_status = 'returned' "
						+ " and branch = '"+ user.getBranch()+"' ";		
				
			}else {				
				query = "select * from loan_applications_view "
						+ "where application_status = 'returned' "
						+ " and branch = '"+ user.getBranch()+"' ";		
				
			}						
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				LoanApplication application = new LoanApplication();
				application.setAccountNumber(rs.getString(2));
				application.setFullName(rs.getString(3) + " " + rs.getString(4));
				application.setApplicationId(rs.getString(8));
				application.setApplicationDate(rs.getDate(9).toString());
				application.setBranch(rs.getString(10));
				application.setLoanFacility(rs.getDouble(11));
				application.setAmountInWords(rs.getString(12));
				application.setLoanType(rs.getString(13));
				application.setPurpose(rs.getString(14));
				application.setTenor(rs.getString(15));
				application.setSourceOfRepayment(rs.getString(16));
				application.setMarketingOfficer(rs.getString(17));
				application.setApplicationStatus(rs.getString(18));
				application.setPhone(rs.getString(7));
				application.setDateOfBirth(rs.getDate(5).toString());
				application.setBVN(rs.getString(6));
				application.setAddress(rs.getString(19));
				application.setClearance(rs.getInt(21));
				application.setSearchStatus(rs.getString(26));
				application.setFacilityRecommended(rs.getDouble(27));
				application.setFacilityRecommendedInWords(rs.getString(28));
				application.setComment(rs.getString(29));
				
				application_list.add(application);
			}
			
		conn.close();	
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return application_list;
	}

	
	@Override
	public ArrayList<LoanProduct> getAllLoanProducts() throws RemoteException {
		ArrayList<LoanProduct> product_list = new ArrayList<>();
		
		try {		
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from loan_products";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				LoanProduct product = new LoanProduct();
				
				product.setProductId(rs.getString(2));
				product.setProductDescription(rs.getString(3));
				product.setIntrestRate(rs.getDouble(4));
				product.setMonFeeRate(rs.getDouble(5));
				product.setRiskPremRate(rs.getDouble(6));
				product.setMgtFeeRate(rs.getDouble(7));
				product.setCompSavingsRate(rs.getDouble(8));
				product_list.add(product);			
			}
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
 		
		return product_list;
	}

	
	@Override
	public void createLoanOffer(String branch, LoanOffer offer) throws RemoteException {
		String reference_no = "";
		String query = "";
		String abv = "";
		int value = 0;
		
		try {
			Connection conn = DbConnection.getDbConnection();			
			
			Date date = new Date();
			SimpleDateFormat year = new SimpleDateFormat("yyyy");
			SimpleDateFormat month = new SimpleDateFormat("MM");			
			
			if(branch.equalsIgnoreCase("Head Office")) {
				query = "select * "
						+ "from id_generator "
						+ "where ref_table = 'customer_loan_offers' ";
				abv = "HO";
			}else if (branch.equalsIgnoreCase("Cocin Headquarters")) {
				query = "select * "
						+ "from id_generator "
						+ "where ref_table = 'hq_loan_offers' ";
				abv = "CH";
			}else if (branch.equalsIgnoreCase("Gindiri")) {
				query = "select * "
						+ "from id_generator "
						+ "where ref_table = 'gindiri_loan_offers' ";
				abv = "GR";
			}
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				value = rs.getInt("value");
				String idValue = value + "";
				reference_no = abv +"-"+ leftPad(idValue, 4)+"/" + month.format(date) +"/"+year.format(date);
			
			
			
			String insert_query = "insert into customer_loan_offers(application_id, amount_approved, tenor_approved, tenor_type_approved, clearance_level, amount_in_words, "
					+ "interest_rate, monitoring_fee_rate, mgt_fee_rate, risk_premium_rate, compulsory_savings_rate, loan_product, interest_type, staff, ref_no, created_on, customer_id) "
					+ "VALUES(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, curdate(),?)";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, offer.getApplicationId());
			pst.setDouble(2, offer.getPrincipal());
			pst.setInt(3, offer.getTenor());
			pst.setString(4, offer.getTenorType());
			pst.setInt(5, offer.getClearanceLevel());
			pst.setString(6, offer.getAmountInWords());
			pst.setDouble(7, offer.getInterestRate());
			pst.setDouble(8, offer.getMonitoringFeeRate());
			pst.setDouble(9, offer.getManagementFeeRate());
			pst.setDouble(10, offer.getRiskPremiumRate());
			pst.setDouble(11, offer.getCompulsorySavingsRate());
			pst.setString(12, offer.getLoanProduct());
			pst.setString(13, offer.getInterestType());
			pst.setString(14, offer.getStaff());
			pst.setString(15, reference_no);
			pst.setString(16, offer.getCustomerId());
			
			pst.execute();			
			
			String update_query = "UPDATE loan_applications "
								+ "SET application_status = ?" 
								+"WHERE application_id = ?";
			
			PreparedStatement pst2 = conn.prepareStatement(update_query);
			pst2.setString(1, "created");
			pst2.setString(2, offer.getApplicationId());
		
			pst2.executeUpdate();			
			}
			
			String update_query2 = "";
			
			if(branch.equalsIgnoreCase("Head Office")) {
				update_query2 = "UPDATE id_generator "
						+ "set value = ? "
						+ "where ref_table = 'customer_loan_offers' ";
				
			}else if (branch.equalsIgnoreCase("Cocin Headquarters")) {
				update_query2 = "UPDATE id_generator "
						+ "set value = ? "
						+ "where ref_table = 'hq_loan_offers' ";
				
			}else if (branch.equalsIgnoreCase("Gindiri")) {
				update_query2 = "UPDATE id_generator "
						+ "set value = ? "
						+ "where ref_table = 'gindiri_loan_offers' ";
				
			}			
			

			PreparedStatement ps = conn.prepareStatement(update_query2);
			ps.setInt(1, (value+1));
			ps.executeUpdate();
						
			conn.close();
			}catch(Exception ex) { ex.printStackTrace(); }
	}

	
	@Override
	public void updateLoanOffer(LoanOffer loan_offer, String type) throws RemoteException {
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE customer_loan_offers "
									+" SET amount_approved = ?, tenor_approved = ?,  tenor_type_approved = ?,  amount_in_words = ?, interest_rate = ?, mgt_fee_rate = ?, "
									+ "monitoring_fee_rate = ?, risk_premium_rate = ?, compulsory_savings_rate = ?, loan_product = ?, interest_type = ?"
									+" WHERE application_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setDouble(1, loan_offer.getPrincipal());
			ps.setDouble(2, loan_offer.getTenor());
			ps.setString(3, loan_offer.getTenorType());
			ps.setString(4, loan_offer.getAmountInWords());
			ps.setDouble(5, loan_offer.getInterestRate());
			ps.setDouble(6, loan_offer.getManagementFeeRate());
			ps.setDouble(7, loan_offer.getMonitoringFeeRate());
			ps.setDouble(8, loan_offer.getRiskPremiumRate());
			ps.setDouble(9, loan_offer.getCompulsorySavingsRate());
			ps.setString(10, loan_offer.getLoanProduct());
			ps.setString(11, loan_offer.getInterestType());
			
			ps.setString(12, loan_offer.getApplicationId());			
			ps.executeUpdate();
			
					
			if(type.equalsIgnoreCase("change")) {
				String update_query2 = "UPDATE loan_applications "
						+ "SET application_status = 'created' "
						+ " WHERE application_id = ?";
				
				PreparedStatement ps2 = conn.prepareStatement(update_query2);
				ps2.setString(1, loan_offer.getApplicationId());
				ps2.executeUpdate();
			}
			
				
			
			conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }
	}

	
	@Override
	public void updateApplicationStatus(String application_id, String status) throws RemoteException {
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE loan_applications "
									+" SET application_status = ? " 
									+" WHERE application_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, status);
			ps.setString(2, application_id);
			
			ps.executeUpdate();		
			
		
		}catch(Exception ex) { ex.printStackTrace(); }
	}

	
	@Override
	public void updateOfferStatus(LoanOfferView loan_offer, String action, String username) throws RemoteException {
		String column1 = "";
		String column2 = "";
		String status = "";
		
		switch(action) {
		case "audit":
			column1 = "audit_by";
			column2 = "audit_date";
			status = "audited";
			break;
			
		case "approve":
			column1 = "approve_by";
			column2 = "approval_date";
			status = "approved";
			break;
			
		case "disburse":
			column1 = "disburse_by";
			column2 = "disbursement_date";
			status = "disbursed";
			break;
			
		default:
			column1 = "";
			column2 = "";
			status = "";
			break;
		}
		
		
		try {
			
			Connection conn = DbConnection.getDbConnection();
			
			
			String update_query = "UPDATE customer_loan_offers "
						+" SET "+ column1+ "= ?, " + column2 + " = curdate() "
						+" WHERE application_id = ?";

			
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, username);
			ps.setString(2, loan_offer.getApplicationId());
			
			ps.executeUpdate();
								
			
			
			conn.close();
			}catch(Exception ex) { ex.printStackTrace(); }
		
		updateApplicationStatus(loan_offer.getApplicationId(), status);
	}
	
	

	
	@Override
	public ArrayList<LoanOfferView> getLoanOfferList(String application_id) throws RemoteException {
		ArrayList<LoanOfferView> offer_list = new ArrayList<LoanOfferView>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String	query = "select * from loan_offers_view "
						+ "where  application_id = '"+ application_id+ "' ORDER BY 10";
			
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
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
				offer.setAmountInWords(rs.getString(38));
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
				offer.setFacilityApplied(rs.getString(36)+" only");				
				offer.setFullName(rs.getString(3)+" "+ rs.getString(4));
				offer.setTenor(rs.getInt(20)+" "+rs.getString(21));
				offer.setFacilityApplied(rs.getString(36));
				offer.setSearchStatus(rs.getString(42));
				offer.setCreatedOn(rs.getString(43));
				offer.setFacilityRecommended(rs.getDouble(44));
				offer.setFacilityRecommendedInWords(rs.getString(45));
				offer.setComment(rs.getString(46));
				
				offer_list.add(offer);
			}
			conn.close();
			
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return offer_list;
	}

	
		
	@Override
	public void rejectLoanOffer(String application_id, String rejection_type, String comment, String username) throws RemoteException {
		try {
			int note_exists = -1;
			Connection conn = DbConnection.getDbConnection();
			String query = "select count(*) from rejected_loans "
					+ "where  application_id = '"+ application_id+"' ";				
		
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				note_exists = rs.getInt(1);				
			}
			
			if (note_exists == 0) {
				String insert_query = "insert into rejected_loans (application_id, rejection_type, rejection_date, rejected_by, comment)"
						+ "VALUES(?,?,curdate(),?,?)";
				PreparedStatement pst = conn.prepareStatement(insert_query);
				
				pst.setString(1, application_id);
				pst.setString(2, rejection_type);
				pst.setString(3, username);
				pst.setString(4, comment);			
				pst.execute();	
			}else if (note_exists == 1) {
				
				String new_comment = getRejectionNote(application_id) + " \n" + comment;
				String update_query = "UPDATE rejected_loans "
						+" SET rejection_type = ?, rejection_date = curdate(), rejected_by = ?, comment = ?"
						+" WHERE application_id = ?";
				PreparedStatement pst = conn.prepareStatement(update_query);
				
				pst.setString(1, rejection_type);
				pst.setString(2, username);
				pst.setString(3, new_comment);
				pst.setString(4, application_id);
				pst.executeUpdate();
			}
			
			String update_query = "UPDATE loan_applications "
									+" SET application_status = ?"
									+" WHERE application_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);			
			
			ps.setString(1, rejection_type);
			ps.setString(2, application_id);		
			ps.executeUpdate();
			
					
			
			conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }
	}

	
	@Override
	public String createInterviewSession(String account_no, String user_id) throws RemoteException {
		String session_id = null;
		int value = 0;
		
		try {
		Connection conn = DbConnection.getDbConnection();
		String query = "select * "
				+ "from id_generator "
				+ "where ref_table = 'customer_interviews' ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			value = rs.getInt("value");
			String idValue = value + "";
			session_id = "ss-"+ leftPad( idValue, 4);
			
			String insert_query = "insert into customer_interviews(account_no, interview_id, interview_date, staff_id, interview_status)"
					+ " VALUES(?,?,curdate(),?,?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, account_no);
			pst.setString(2, session_id);
			pst.setString(3, user_id);
			pst.setString(4, "open");
			
			pst.execute();
			
			
		}
		
		String update_query = "UPDATE id_generator "
								+ "set value = ? "
								+ "where ref_table = 'customer_interviews' ";
		
		PreparedStatement ps = conn.prepareStatement(update_query);
		ps.setInt(1, (value+1));
		ps.executeUpdate();
		conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }		
		
		return session_id;
	}

	
	@Override
	public int getSessionStatus(String account_no) throws RemoteException {
		int xy = 0;

		try {
		Connection conn = DbConnection.getDbConnection();
		String query = "select count(*) "
				+ "from customer_interview_sessions "
				+ "where account_no = '"+ account_no +"' and interview_status = 'open'";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			xy = rs.getInt(1);
		}
		
		conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return xy;
	}

	
	@Override
	public ArrayList<InterviewSession> getCustomerInterviews(String account_no) throws RemoteException {
		ArrayList<InterviewSession> interview_list = new ArrayList<InterviewSession>();
		
		try {			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from customer_interview_sessions where account_no = '"+account_no+"' ";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {	
				
				InterviewSession ss = new InterviewSession();
				ss.setAccountNumber(rs.getString(1));
				ss.setSurname(rs.getString(2));
				ss.setOthernames(rs.getString(3));
				ss.setDob(rs.getDate(4).toString());
				ss.setBvn(rs.getString(5));
				ss.setPhoneNo(rs.getString(6));
				ss.setInterviewDate(rs.getDate(8).toString());
				ss.setSessionId(rs.getString(7));
				ss.setStaffId(rs.getString(9));
				ss.setStaffName(rs.getString(10));
				ss.setStaffRole(rs.getString(11));
				ss.setInterviewStatus(rs.getString(12));			
				
				interview_list.add(ss);			
			}
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
 		
		
		return interview_list;
	}

	
	@Override
	public ArrayList<QuestionAnswer> getQuestionAnswers(String interview_id) throws RemoteException {
		ArrayList<QuestionAnswer> question_answer_list = new ArrayList<QuestionAnswer>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			
			
			String query = "select * from customer_interview_answers where interview_id = '"+interview_id+"' ";
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				QuestionAnswer question = new QuestionAnswer();
				question.setSerialNumber(rs.getInt(1));
				question.setInterviewId(rs.getString(2));
				question.setQuestion(rs.getString(3));
				question.setAnswer(rs.getString(4));
				
				question_answer_list.add(question);			
			}
			
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		} 		
		return question_answer_list;
	}

	
	@Override
	public ArrayList<BusinessCategory> getAllBusinessCategories() throws RemoteException {
		ArrayList<BusinessCategory> categories = new ArrayList<>();
		
		try {
		
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from business_categories";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				BusinessCategory category = new BusinessCategory();
				category.setCategoryId(rs.getString(2));
				category.setCategoryDecription(rs.getString(3));
				
				categories.add(category);			
			}
			
			conn.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
 		
		return categories;
	}

	
	@Override
	public ArrayList<MarketingQuestion> getMarketingQuestions(String selection, String b_category) throws RemoteException {
		ArrayList<MarketingQuestion> question_list = new ArrayList<>();
		
		try {			
			Connection conn = DbConnection.getDbConnection();
			
			String query = "select * from marketing_questions_view";
			String query2 = "select * from marketing_questions_view where category_description = '"+b_category+"' ";
			
			Statement st = conn.createStatement();
			ResultSet rs = null;
			
			if(selection.equalsIgnoreCase("View all")) {
				rs = st.executeQuery(query);
			}else if(selection.equalsIgnoreCase("Select")) {
				rs = st.executeQuery(query2);
			}
			
			while(rs.next()) {
				
				MarketingQuestion question = new MarketingQuestion();
				question.setSno(rs.getInt(1));
				question.setCategoryDescription(rs.getString(3));
				question.setQuestion(rs.getString(4));
				
				question_list.add(question);			
			}
			
			conn.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		} 		
		
		return question_list;
	}

	
	@Override
	public void saveQuestionAndAnswer(String interview_id, String question, String answer) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();			
			
			String insert_query = "insert into customer_interview_answers(interview_id, question, answer) VALUES(?, ?, ?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, interview_id);
			pst.setString(2, question);
			pst.setString(3, answer);
				
			pst.execute();
			
			conn.close();
		
		}catch(Exception ex) { ex.printStackTrace(); }
	}

	
	@Override
	public void updateQuestionAnswer(int sno, String question, String answer) throws RemoteException {
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE customer_interview_answers "
									+" SET question = ?, answer = ?"
									+" WHERE sno = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, question);
			ps.setString(2, answer);
			ps.setInt(3, sno);
			
			
			ps.executeUpdate();
			conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
	}

	
	@Override
	public ArrayList<FinanceItem> getFinanceItems(String finance_type) throws RemoteException {
		ArrayList<FinanceItem> item_list = new ArrayList<>();
		
		try {
		
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from financial_statement_items where finance_type = '"+ finance_type+ "'";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				FinanceItem item = new FinanceItem();
				item.setItem(rs.getString(3));
				item.setFinanceType(rs.getString(2));
				item.setSno(rs.getInt(1));
				
				item_list.add(item);			
			}
			
			conn.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
 		
		return item_list;
	}

	
	@Override
	public void createFinancialStatement(String interview_id, String finance_type, String item, double amount) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();			
			
			String insert_query = "insert into customer_financial_statement(interview_id, finance_type, item, amount) VALUES(?, ?, ?, ?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, interview_id);
			pst.setString(2, finance_type);
			pst.setString(3, item);
			pst.setDouble(4, amount);
				
			pst.execute();	
			
			conn.close();
		
		}catch(Exception ex) { ex.printStackTrace(); }
	}

	
	@Override
	public ArrayList<FinancialStatement> getInterviewExpenses(String interview_id) throws RemoteException {
		ArrayList<FinancialStatement> expense_list = new ArrayList<>();
		
		try {
		
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from customer_financial_statement where interview_id = '"+ interview_id+ "' and finance_type = 'Expenses' ";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				FinancialStatement expense = new FinancialStatement();
				expense.setSno(rs.getInt(1));
				expense.setInterviewId(rs.getString(2));
				expense.setFinanceType(rs.getString(3));
				expense.setItem(rs.getString(4));
				expense.setAmount(rs.getDouble(5));
				
				
				expense_list.add(expense);			
			}
			
			conn.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return expense_list;
	}

	
	@Override
	public ArrayList<FinancialStatement> getInterviewIncome(String interview_id) throws RemoteException {
		ArrayList<FinancialStatement> income_list = new ArrayList<>();
		
		try {
		
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from customer_financial_statement where interview_id = '"+ interview_id+ "' and finance_type = 'Income' ";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				FinancialStatement income = new FinancialStatement();
				income.setSno(rs.getInt(1));
				income.setInterviewId(rs.getString(2));
				income.setFinanceType(rs.getString(3));
				income.setItem(rs.getString(4));
				income.setAmount(rs.getDouble(5));
				
				
				income_list.add(income);			
			}
			
			conn.close();
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return income_list;
	}

	
	@Override
	public void endInterviewSession(String interview_id) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();
			String querry = "UPDATE customer_interviews "
					+ "SET interview_status = 'close' "
					+ "where interview_id = ?";
			PreparedStatement pst = conn.prepareStatement(querry);
			
			pst.setString(1, interview_id);
			
			pst.executeUpdate();
			conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
	}

	
	@Override
	public void updateFinancialStatement(int sno, String finance_type, String item, double amount) throws RemoteException {
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE customer_financial_statement "
									+" SET finance_type = ?, item = ?, amount = ?"
									+" WHERE sno = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, finance_type);
			ps.setString(2, item);
			ps.setDouble(3, amount);
			ps.setInt(4, sno);
			
			
			ps.executeUpdate();
			
			conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
	}

	
	@Override
	public ArrayList<LoanOfferView> getLoanOfferList(User user, String search_criterion, String search_text, String offer_status) throws RemoteException {
		ArrayList<LoanOfferView> offer_list = new ArrayList<LoanOfferView>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = " ";
			
			if(user.getUserGroup().equalsIgnoreCase("Managing Director") || user.getUserGroup().equalsIgnoreCase("Auditor") ||
					user.getUserGroup().equalsIgnoreCase("Head of Operations") ||
					user.getUserGroup().equalsIgnoreCase("Head of Credit") || user.getUserGroup().equalsIgnoreCase("System Administrator")) {
				
					if(search_criterion.equalsIgnoreCase("customer_name")) {
						query = "select * from loan_offers_view "
								+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) and application_status = '"+ offer_status+ "' ORDER BY 10";								
								
					}else if(search_criterion.equalsIgnoreCase("credit_officer")) {
						query = "select * from loan_offers_view "
								+ "where marketing_officer like '%"+search_text+"%' and application_status = '"+ offer_status+ "' ORDER BY 10"; 						
					}else {
						query = "select * from loan_offers_view "
								+ "where "+ search_criterion+ " = '"+search_text+"' and application_status = '"+ offer_status+ "' ORDER BY 10";
					}
			}
			else {
			
					if(search_criterion.equalsIgnoreCase("customer_name")) {
						query = "select * from loan_offers_view "
								+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) and application_status = '"+ offer_status+ "' ORDER BY 10";
								
					}else if(search_criterion.equalsIgnoreCase("credit_officer")) {
						query = "select * from loan_offers_view "
								+ "where marketing_officer like '%"+search_text+"%' and application_status = '"+ offer_status+ "' and branch = '"+ user.getBranch()+"' ORDER BY 10"; 						
					}else {
						query = "select * from loan_offers_view "
								+ "where "+ search_criterion+ " = '"+search_text+"' and application_status = '"+ offer_status+ "' and branch = '"+ user.getBranch()+"' ORDER BY 10";
					}
			}
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
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
				offer.setAmountInWords(rs.getString(36));
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
				offer.setFacilityApplied(rs.getString(36)+" only");
				
				offer.setFullName(rs.getString(3)+" "+ rs.getString(4));
				offer.setTenor(rs.getInt(20)+" "+rs.getString(21));
				offer.setFacilityApplied(rs.getString(36));
				offer.setSearchStatus(rs.getString(42));
				offer.setCreatedOn(rs.getString(43));
				offer.setFacilityRecommended(rs.getDouble(44));
				offer.setFacilityRecommendedInWords(rs.getString(45));
				offer.setComment(rs.getString(46));
				
				offer_list.add(offer);
			}
			
			conn.close();
			
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return offer_list;
	}

	
	@Override
	public ArrayList<LoanOfferView> getLoanOfferList(User user, String offer_status) throws RemoteException {
		ArrayList<LoanOfferView> offer_list = new ArrayList<LoanOfferView>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = " ";
			
			if(user.getUserGroup().equalsIgnoreCase("Managing Director") || user.getUserGroup().equalsIgnoreCase("Auditor") ||
					user.getUserGroup().equalsIgnoreCase("Head of Operations") ||
					user.getUserGroup().equalsIgnoreCase("Head of Credit") || user.getUserGroup().equalsIgnoreCase("System Administrator")) {
				query = "select * from loan_offers_view "
						+ "where  application_status = '"+ offer_status+ "' ";							
						
			}else {
				query = "select * from loan_offers_view "
						+ "where  application_status = '"+ offer_status+ "' and branch = '"+ user.getBranch()+"' ORDER BY 10";	
			}
			
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
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
				offer.setAmountInWords(rs.getString(36));
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
				offer.setFacilityApplied(rs.getString(36)+" only");
				
				offer.setFullName(rs.getString(3)+" "+ rs.getString(4));
				offer.setTenor(rs.getInt(20)+" "+rs.getString(21));
				offer.setFacilityApplied(rs.getString(36));
				offer.setSearchStatus(rs.getString(42));
				offer.setCreatedOn(rs.getString(43));
				offer.setFacilityRecommended(rs.getDouble(44));
				offer.setFacilityRecommendedInWords(rs.getString(45));
				offer.setComment(rs.getString(46));
				
				offer_list.add(offer);
			}
			
			conn.close();
			
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return offer_list;
	}

	
	@Override
	public ArrayList<LoanOfferView> getLoanOfferView(User user, String search_criterion, String search_text) throws RemoteException {
		ArrayList<LoanOfferView> offer_list = new ArrayList<LoanOfferView>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = " ";
			
			if(user.getUserGroup().equalsIgnoreCase("Managing Director") || user.getUserGroup().equalsIgnoreCase("Auditor") ||
					user.getUserGroup().equalsIgnoreCase("Head of Operations") ||
					user.getUserGroup().equalsIgnoreCase("Head of Credit") || user.getUserGroup().equalsIgnoreCase("System Administrator")) {
				
					if(search_criterion.equalsIgnoreCase("customer_name")) {
						query = "select * from loan_offers_view "
								+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) ORDER BY 10";								
								
					}else if(search_criterion.equalsIgnoreCase("credit_officer")) {
						query = "select * from loan_offers_view "
								+ "where marketing_officer like '%"+search_text+"%' ORDER BY 10"; 						
					}else {
						query = "select * from loan_offers_view "
								+ "where "+ search_criterion+ " = '"+search_text+"' 10";
					}
			}
			else {
			
					if(search_criterion.equalsIgnoreCase("customer_name")) {
						query = "select * from loan_offers_view "
								+ "where (surname like '%"+search_text+"%' or othernames like '%"+search_text+"%' ) ORDER BY 10";
								
					}else if(search_criterion.equalsIgnoreCase("credit_officer")) {
						query = "select * from loan_offers_view "
								+ "where marketing_officer like '%"+search_text+"%'  and branch = '"+ user.getBranch()+"' ORDER BY 10"; 						
					}else {
						query = "select * from loan_offers_view "
								+ "where "+ search_criterion+ " = '"+search_text+"' and branch = '"+ user.getBranch()+"' ORDER BY 10";
					}
			}
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
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
				offer.setAmountInWords(rs.getString(36));
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
				offer.setFacilityApplied(rs.getString(36)+" only");
				
				offer.setFullName(rs.getString(3)+" "+ rs.getString(4));
				offer.setTenor(rs.getInt(20)+" "+rs.getString(21));
				offer.setFacilityApplied(rs.getString(36));
				offer.setSearchStatus(rs.getString(42));
				offer.setCreatedOn(rs.getString(43));
				offer.setFacilityRecommended(rs.getDouble(44));
				offer.setFacilityRecommendedInWords(rs.getString(45));
				offer.setComment(rs.getString(46));
				
				offer_list.add(offer);
			}
			
			conn.close();
			
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return offer_list;
	}

	@Override
	public ArrayList<LoanOfferView> getRejectedLoanOfferList(User user) throws RemoteException{
		ArrayList<LoanOfferView> offer_list = new ArrayList<LoanOfferView>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from loan_offers_view " + "where  application_status = 'rejected' and branch = '"+ user.getBranch()+"' ORDER BY 10";				
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
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
				offer.setAmountInWords(rs.getString(36));
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
				offer.setFacilityApplied(rs.getString(36)+" only");
				
				offer.setFullName(rs.getString(3)+" "+ rs.getString(4));
				offer.setTenor(rs.getInt(20)+" "+rs.getString(21));
				offer.setFacilityApplied(rs.getString(36));
				offer.setSearchStatus(rs.getString(42));
				offer.setCreatedOn(rs.getString(43));
				offer.setFacilityRecommended(rs.getDouble(44));
				offer.setFacilityRecommendedInWords(rs.getString(45));
				offer.setComment(rs.getString(46));
				
				offer_list.add(offer);
			}
			
			conn.close();
			
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return offer_list;
		}
	
	
	@Override
	public ArrayList<LoanApplication> getApprovedLoanApplications(String branch) throws RemoteException {
		ArrayList<LoanApplication> application_list = new ArrayList<LoanApplication>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = " ";
							
			query = "select * from loan_applications_view "
					+ "where branch = '"+branch+"' and application_status = 'approved' ORDER BY 8";			
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				LoanApplication application = new LoanApplication();
				application.setAccountNumber(rs.getString(2));
				application.setFullName(rs.getString(3) + " " + rs.getString(4));
				application.setApplicationId(rs.getString(8));
				application.setApplicationDate(rs.getDate(9).toString());
				application.setBranch(rs.getString(10));
				application.setLoanFacility(rs.getDouble(11));
				application.setAmountInWords(rs.getString(12));
				application.setLoanType(rs.getString(13));
				application.setPurpose(rs.getString(14));
				application.setTenor(rs.getString(15));
				application.setSourceOfRepayment(rs.getString(16));
				application.setMarketingOfficer(rs.getString(17));
				application.setApplicationStatus(rs.getString(18));
				application.setPhone(rs.getString(7));
				application.setDateOfBirth(rs.getDate(5).toString());
				application.setBVN(rs.getString(6));
				application.setClearance(rs.getInt(21));
				
				application.setSearchStatus(rs.getString(26));
				application.setFacilityRecommended(rs.getDouble(27));
				application.setFacilityRecommendedInWords(rs.getString(28));
				application.setComment(rs.getString(29));
				
				application_list.add(application);
			}
				
			conn.close();	
			}catch(Exception ex) { ex.printStackTrace(); }
			
			return application_list;
	}

	
	@Override
	public void updateSearchStatus(String application_id) throws RemoteException {
		try {
					
					Connection conn = DbConnection.getDbConnection();
					String update_query = "UPDATE loan_applications "
										+" SET search_status = ? " 
										+" WHERE application_id = ?";
				PreparedStatement ps = conn.prepareStatement(update_query);
				ps.setString(1, "done");
				ps.setString(2, application_id);
				
				ps.executeUpdate();		
				
				conn.close();
			}catch(Exception ex) { ex.printStackTrace(); }
			
	}

	
	@Override
	public ArrayList<LoanApplication> getPendingCreditSearch(User user) throws RemoteException {
		ArrayList<LoanApplication> application_list = new ArrayList<LoanApplication>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from loan_applications_view "
					+ "where search_status = 'pending'"
					+ " and branch = '"+ user.getBranch()+"' ORDER BY 10";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				LoanApplication application = new LoanApplication();
				application.setAccountNumber(rs.getString(2));
				application.setFullName(rs.getString(3) + " " + rs.getString(4));
				application.setApplicationId(rs.getString(8));
				application.setApplicationDate(rs.getDate(9).toString());
				application.setBranch(rs.getString(10));
				application.setLoanFacility(rs.getDouble(11));
				application.setAmountInWords(rs.getString(12));
				application.setLoanType(rs.getString(13));
				application.setPurpose(rs.getString(14));
				application.setTenor(rs.getString(15));
				application.setSourceOfRepayment(rs.getString(16));
				application.setMarketingOfficer(rs.getString(17));
				application.setApplicationStatus(rs.getString(18));
				application.setPhone(rs.getString(7));
				application.setDateOfBirth(rs.getDate(5).toString());
				application.setBVN(rs.getString(6));
				application.setAddress(rs.getString(19));
				application.setClearance(rs.getInt(21));
				application.setSearchStatus(rs.getString(26));
				application.setFacilityRecommended(rs.getDouble(27));
				application.setFacilityRecommendedInWords(rs.getString(28));
				application.setComment(rs.getString(29));
				
				application_list.add(application);
			}
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return application_list;
	}

	@Override
	public String databaseBackup(String filepath) throws RemoteException {
		
		 String executeCmd = "mysqldump -u root -pSudoP@ssw0rd@1234 lightmfb_loans -r "+filepath+".sql";
		 String message = "";
		 Process runtimeProcess;
		    try {
		    	runtimeProcess = Runtime.getRuntime().exec(executeCmd);
		        int processComplete = runtimeProcess.waitFor();

		        if (processComplete == 0) {
		            
		            message = "Backup created successfully";
		          
		        } else {
		           
		            message = "Could not create the backup ";
		        }
		    } catch (IOException | InterruptedException ex) {
		        
		    }
       
		return message;
	}

	@Override
	public void saveOutstandingFacility(OutstandingFacility facility, String application_id) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();			
			
			String insert_query = "insert into outstanding_balance(application_id, outstanding_facility, amount, outstanding_amount, expiry_date, security) "
					+ "VALUES(?, ?, ?, ?,?,?) ";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(facility.getExpiryDateString());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, application_id);
			pst.setString(2, facility.getOutstandingFacility());
			pst.setDouble(3, facility.getLoanAmount());
			pst.setDouble(4, facility.getOutstandingBalance());
			pst.setDate(5, sqlDate);
			pst.setString(6, facility.getSecurity());				
			pst.execute();
			
			conn.close();
		
		}catch(Exception ex) { ex.printStackTrace(); }
		
	}
	
	@Override
	public void saveAccountTurnover(AccountTurnover turnover, String application_id) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();			
			
			String insert_query = "insert into account_turnover(application_id, month, debit_turnover, credit_turnover, month_end_balance, income) VALUES(?, ?, ?, ?,?,?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, application_id);
			pst.setString(2, turnover.getMonth());
			pst.setDouble(3, turnover.getDebitTurnover());
			pst.setDouble(4, turnover.getCreditTurnover());
			pst.setDouble(5, turnover.getMonthEndBalance());
			pst.setDouble(6, turnover.getIncome());
			
				
			pst.execute();
			
			conn.close();
		
		}catch(Exception ex) { ex.printStackTrace(); }
		
	}
	
	@Override
	public void deleteOutstandingBalance(int sno) throws RemoteException {
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "delete  from outstanding_balance "
					+ "where sno  = '"+ sno+"' ";									
						
			PreparedStatement ps2 = conn.prepareStatement(query);
			ps2.execute();			
			
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
	}
	
	@Override
	public void deleteAccountTurnover(int sno) throws RemoteException {
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "delete  from account_turnover "
					+ "where sno  = '"+ sno+"' ";									
						
			PreparedStatement ps2 = conn.prepareStatement(query);
			ps2.execute();			
			
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
	}
	
	@Override
	public ArrayList<OutstandingFacility> getOutstandingFacilities(String application_id) throws RemoteException {
		ArrayList<OutstandingFacility> list = new ArrayList<OutstandingFacility>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from outstanding_balance where application_id  = '"+ application_id+"' ";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				OutstandingFacility facility = new OutstandingFacility();	
				facility.setSno(rs.getInt(1));
				facility.setOutstandingFacility(rs.getString(3));
				facility.setLoanAmount(rs.getDouble(4));
				facility.setOutstandingBalance(rs.getDouble(5));
				facility.setExpiryDateString(rs.getString(6));
				facility.setSecurity(rs.getString(7));
				
				list.add(facility);
				
			}
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return list;
	}
	
	@Override
	public ArrayList<AccountTurnover> getAccountTurnovers(String application_id) throws RemoteException {
		ArrayList<AccountTurnover> list = new ArrayList<AccountTurnover>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from account_turnover where application_id  = '"+ application_id+"' ";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				AccountTurnover turnover = new AccountTurnover();	
				turnover.setSnp(rs.getInt(1));
				turnover.setMonth(rs.getString(3));
				turnover.setDebitTurnover(rs.getDouble(4));
				turnover.setCreditTurnover(rs.getDouble(5));
				turnover.setMonthEndBalance(rs.getDouble(6));
				turnover.setIncome(rs.getDouble(7));
				
				list.add(turnover);				
				
			}
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return list;
	}

	@Override
	public void saveCollateralNote(CollateralNote note, String application_id) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();	
			
			String query = "delete  from collateral_notes "
					+ "where application_id  = '"+application_id+"' ";									
						
			PreparedStatement ps2 = conn.prepareStatement(query);
			ps2.execute();
			
			
			String insert_query = "insert into collateral_notes (application_id, collateral_note, customer_background) VALUES(?, ?, ?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, application_id);
			pst.setString(2, note.getCollateral());
			pst.setString(3, note.getCostumerBackground());		
				
			pst.execute();
			
			conn.close();
		
		}catch(Exception ex) { ex.printStackTrace(); }
		
	}

	@Override
	public CollateralNote getCollateralNotes(String application_id) throws RemoteException {
		CollateralNote note = new CollateralNote();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from collateral_notes where application_id  = '"+ application_id+"' ";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
				
			while(rs.next()) {
				
				note.setSno(rs.getInt(1));
				note.setCollateral(rs.getString(3));
				note.setCostumerBackground(rs.getString(4));		
				
			}
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return note;
	}

	@Override
	public boolean collateralExists(String application_id) throws RemoteException {		
		boolean value = false;
		try {
			Connection conn = DbConnection.getDbConnection();
		
			String query = "select * from collateral_notes where application_id  = '"+ application_id+"' ";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			if (rs.next() == false) {     value = false;   } 
			else {		value = true;	}
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}

	
	@Override
	public void saveLoanRAC(ArrayList<ApprovalDisbursementParameters> list) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();
			
			 String query = "delete  from approval_dist_checklist "
						+ "where application_id  = '"+ list.get(0).getApplicationId()+"' ";									
							
				PreparedStatement ps2 = conn.prepareStatement(query);
				ps2.execute();
			
						
			String insert_query = "INSERT INTO approval_dist_checklist (application_id, parameter, value, remark) VALUES (?, ?, ?, ?)";
			 PreparedStatement ps1 = conn.prepareStatement(insert_query);
			 
			 for(int i=0; i<list.size(); i++) {
				 ps1.setString(1, list.get(i).getApplicationId());
				  ps1.setString(2, list.get(i).getParameter());
				  ps1.setString(3, list.get(i).getStatus());
				  ps1.setString(4, list.get(i).getRemark());
				  ps1.addBatch();
			 }

			 int[] results = ps1.executeBatch();			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	public ArrayList<ApprovalDisbursementParameters> getLoanRAC(String application_id) throws RemoteException {
		ArrayList<ApprovalDisbursementParameters> list = new ArrayList<>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from approval_dist_checklist where application_id  = '"+ application_id+"' ";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
				
			while(rs.next()) {
				
				ApprovalDisbursementParameters check = new ApprovalDisbursementParameters();
				check.setParameter(rs.getString(3));
				check.setStatus(rs.getString(4));
				check.setRemark(rs.getString(5));
				
				list.add(check);
				
			}
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return list;
	}

	
	@Override
	public void saveLoanDocumentation(ArrayList<DocumentationParameters> list) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();
			
			String query = "delete  from documentation_checklist "
					+ "where application_id  = '"+ list.get(0).getApplicationId()+"' ";									
						
			PreparedStatement ps2 = conn.prepareStatement(query);
			ps2.execute();
			
			String insert_query = "INSERT INTO documentation_checklist (application_id, parameter, value, remark) VALUES (?, ?, ?, ?)";
			 PreparedStatement ps1 = conn.prepareStatement(insert_query);
			 
			 
			 
			 for(int i=0; i<list.size(); i++) {
				 ps1.setString(1, list.get(i).getApplicationId());
				  ps1.setString(2, list.get(i).getParameter());
				  ps1.setString(3, list.get(i).getStatus());
				  ps1.setString(4, list.get(i).getRemark());
				  ps1.addBatch();
			 }

			 int[] results = ps1.executeBatch();
			conn.close();					 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	public ArrayList<DocumentationParameters> getLoanDocumentation(String application_id) throws RemoteException {
		ArrayList<DocumentationParameters> list = new ArrayList<>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from documentation_checklist where application_id  = '"+ application_id+"' ";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
				
			while(rs.next()) {
				
				DocumentationParameters check = new DocumentationParameters();
				check.setParameter(rs.getString(3));
				check.setStatus(rs.getString(4));
				check.setRemark(rs.getString(5));
				
				list.add(check);
				
			}
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return list;
	}

	@Override
	public boolean loanRACExists(String application_id) throws RemoteException {
		boolean value = false;
		try {
			Connection conn = DbConnection.getDbConnection();
		
			String query = "select * from approval_dist_checklist where application_id  = '"+ application_id+"' ";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			if (rs.next() == false) {     value = false;   } 
			else {		value = true;	}
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}

	@Override
	public boolean loanDocExists(String application_id) throws RemoteException {
		boolean value = false;
		try {
			Connection conn = DbConnection.getDbConnection();
		
			String query = "select * from documentation_checklist where application_id  = '"+ application_id+"' ";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			if (rs.next() == false) {     value = false;   } 
			else {		value = true;	}
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}

	public void updateLoanMaturity(LoanOfferView loan_offer) throws RemoteException{
							
			try {
				Connection conn = DbConnection.getDbConnection();
				
				Date date2 = new Date();
				
				if(loan_offer.getTenorTypeApproved().equalsIgnoreCase("months")) {
					date2 = DateUtils.addMonths(date2, loan_offer.getTenorApproved());			
				}else if(loan_offer.getTenorTypeApproved().equalsIgnoreCase("weeks")) {
					date2 = DateUtils.addWeeks(date2, loan_offer.getTenorApproved());		
				}
				
				
				java.sql.Date sqldate = new java.sql.Date(date2.getTime());
				String update_query3 = "UPDATE customer_loan_offers "
						+" SET maturity = ? "
						+" WHERE application_id = ?";

					PreparedStatement ps4 = conn.prepareStatement(update_query3);
					ps4.setDate(1, sqldate);
					ps4.setString(2, loan_offer.getApplicationId());
					
					ps4.executeUpdate();	
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	@Override
	public ArrayList<LoanOfferView> getCustomerLoanHistory(String customer_id) throws RemoteException {
		ArrayList<LoanOfferView> offer_list = new ArrayList<LoanOfferView>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String	query = "select * from loan_offers_view "
						+ "where  customer_id = '"+ customer_id+ "' ORDER BY 10 DESC";
			
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
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
				offer.setFacilityApplied(rs.getString(36)+" only");				
				offer.setFullName(rs.getString(3)+" "+ rs.getString(4));
				offer.setTenor(rs.getInt(20)+" "+rs.getString(21));
				offer.setFacilityApplied(rs.getString(36));
				offer.setSearchStatus(rs.getString(42));
				offer.setCreatedOn(rs.getString(43));
				offer.setFacilityRecommended(rs.getDouble(44));
				offer.setFacilityRecommendedInWords(rs.getString(45));
				offer.setComment(rs.getString(46));
				offer.setCustomerId(rs.getString(47));
				
				offer_list.add(offer);
			}
			conn.close();
			
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return offer_list;
	}

	public int getApplicationsCount(String user, String status, String branch)throws RemoteException {
		int xy = 0;
		try {
			Connection conn = DbConnection.getDbConnection();
			
			String query = "";
			
				if (user.equalsIgnoreCase("MIS Officer") || user.equalsIgnoreCase("System Administrator")) {
					query = "select count(*) from loan_applications_view "
							+ "where search_status = 'pending' and branch = '"+branch+"'" ;
				}else {
					query = "select count(*) from loan_applications_view "
							+ "where application_status = '"+status+"' and branch = '"+branch+"'" ;
				}		
										
							
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				xy = rs.getInt(1);		
				
			}
			
			
			conn.close();
		} catch (Exception e) {	e.printStackTrace();}
		
		return xy;
		
	
	
	
	}

	@Override
	public ArrayList<AppUserGroup> getUserGroups(String branch) throws RemoteException {
		ArrayList<AppUserGroup> user_groups = new ArrayList();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select distinct group_name, count(*) as no_of_staff FROM app_users_view "
							+ "WHERE branch_name = '"+branch+"' "							
							+ "GROUP BY 1";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {			
				
				user_groups.add(new AppUserGroup(rs.getString(1), rs.getInt(2)));
				
			}
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}		
		return user_groups;
	}

	@Override
	public ArrayList<String> getUsernames(String user, String group_name, String branch) throws RemoteException {
		ArrayList<String> usernames = new ArrayList();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select distinct username FROM app_users_view "
							+ "WHERE ((group_name = '"+group_name+"' AND branch_name = '" +branch+"') AND user_status = 1) AND username NOT IN ('"+user+"')"							
							+ "ORDER BY 1";
			
			
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {				
				usernames.add(rs.getString(1));				
			}
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}		
		return usernames;
	}
	
	

	@Override
	public void sendMessage(String sender, String receiver, String message) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();
			String query ="INSERT into messenger(message, sender, receiver, date, time, status) VALUES(?,?,?, curdate(), curtime(), ?)";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, message);
			ps.setString(2, sender);
			ps.setString(3, receiver);
			ps.setString(4, "delivered");
			
			ps.execute();
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateMessageStatus(String receiver, String sender) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();
			String query ="UPDATE messenger "
					+ "SET status= 'seen' "
					+ "WHERE sender = '" +sender +"' AND receiver = '" +receiver+"' " ;
			PreparedStatement ps = conn.prepareStatement(query);			
			ps.executeUpdate();
			
			conn.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int getNewMessageCount(String sender, String receiver) throws RemoteException {
int xy = 0;
		
		try {
			Connection conn = DbConnection.getDbConnection();
			String query = "select count(*) FROM messenger "
							+ "WHERE (sender = '"+sender+"' AND receiver = '"+receiver+"') AND status = 'delivered' ";							
							
			Statement st = conn.createStatement();			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {				
				xy = rs.getInt(1);	
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return xy;
	}

	@Override
	public int getGroupMessageCount(String receiver, String group) throws RemoteException {
		int xy = 0;
		try {
			Connection conn = DbConnection.getDbConnection();
			String query = "select count(*) FROM messenger_view "
							+ "WHERE (receiver = '"+receiver+"' AND sdr_group = '"+group+"') AND status = 'delivered' ";							
							
			Statement st = conn.createStatement();			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {				
				xy = rs.getInt(1);	
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return xy;
	}	
	
	@Override
	public int getBranchMessageCount(String receiver, String branch) throws RemoteException {
		int xy = 0;
		try {
			Connection conn = DbConnection.getDbConnection();
			String query = "select count(*) FROM messenger_view "
							+ "WHERE (receiver = '"+receiver+"' AND sdr_branch = '"+branch+"') AND status = 'delivered' ";							
							
			Statement st = conn.createStatement();			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {				
				xy = rs.getInt(1);	
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return xy;
	}
	

	@Override
	public ArrayList<LightMessage> getMessages(String user1, String user2) throws RemoteException {
		ArrayList<LightMessage> messages = new ArrayList<LightMessage>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from messenger_view "
							+ "WHERE (sender = '" +user1 +"' AND receiver = '" +user2+"' ) " 
							+ "OR (sender = '" +user2 +"' AND receiver = '" +user1+"' ) "
							+ "ORDER BY 5, 6";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {			
					java.sql.Date j_date = rs.getDate(5);
					java.util.Date u_date = new java.util.Date(j_date.getTime());
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					//sdf.format(u_date);
				messages.add(new LightMessage(rs.getString(2), rs.getString(3), rs.getString(4), sdf.format(u_date), rs.getTime(6).toString(), rs.getString(7)));
				
			}
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}		
		return messages;
	}

	@Override
	public int getNewMessagesCount(String receiver) throws RemoteException {
		int xy = 0;
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "SELECT COUNT(*) FROM messenger_view "
							+ "WHERE status = 'delivered' AND receiver = '"+receiver+"'";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {			
				xy = rs.getInt(1);
			}
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}		
		return xy;
	}

	@Override
	public boolean analysisExists(String application_id) throws RemoteException {
		boolean value = false;
		try {
			Connection conn = DbConnection.getDbConnection();
		
			String query = "select * from loan_analysis where application_id  = '"+ application_id+"' ";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			if (rs.next() == false) {     value = false;   } 
			else {		value = true;	}
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}

	
	@Override
	public ArrayList<LoanAnalysisParameters> getLoanAnalysisParams(String application_id, String type) throws RemoteException {
		ArrayList<LoanAnalysisParameters> list = new ArrayList<>();
		try {
			String query = "";
			Connection conn = DbConnection.getDbConnection();
			
			if (type.equalsIgnoreCase("five c")) {
				query = "select * from five_c";
				
			}else if (type.equalsIgnoreCase("check")) {
				query = "select * from analysis_checklist_parameters";
				
			}
												
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
				
			while(rs.next()) {
				
				LoanAnalysisParameters check = new LoanAnalysisParameters();
				check.setParameter(rs.getString(2));
												
				list.add(check);
				
			}
			
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return list;
	}

	@Override
	public ArrayList<LoanAnalysisParameters> getLoanAnalysis(String application_id, String type) throws RemoteException {
		ArrayList<LoanAnalysisParameters> list = new ArrayList<>();
		try {
			String query = "";
			Connection conn = DbConnection.getDbConnection();
			if (type.equalsIgnoreCase("five c")) {
				query = "select * from loan_analysis where application_id  = '"+application_id+"' and type = '"+type+"'";
			}else if (type.equalsIgnoreCase("check")) {
				query = "select * from loan_analysis where application_id  = '"+application_id+"' and type = '"+type+"' ";
			}
			
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
				
			while(rs.next()) {
				
				LoanAnalysisParameters check = new LoanAnalysisParameters();
				check.setParameter(rs.getString(3));
				check.setValue(rs.getString(4));
								
				list.add(check);
				
			}
			
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return list;
	}

	@Override
	public void saveLoanAnalysis(ArrayList<LoanAnalysisParameters> five_c_list,	ArrayList<LoanAnalysisParameters> check_list, AORecommendationNote note) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();
			
			String query = "delete  from loan_analysis "
					+ "where application_id  = '"+ five_c_list.get(0).getApplicationId()+"' ";									
						
			PreparedStatement ps2 = conn.prepareStatement(query);
			ps2.execute();
			
			String query1 = "delete  from recommendation_note "
					+ "where application_id  = '"+ five_c_list.get(0).getApplicationId()+"' ";									
						
			PreparedStatement ps3 = conn.prepareStatement(query1);
			ps3.execute();
			
			String insert_five_c = "INSERT INTO loan_analysis (application_id, parameter, value, type) VALUES (?, ?, ?, ?)";
			 PreparedStatement ps1 = conn.prepareStatement(insert_five_c);			 
			 
			 for(int i=0; i<five_c_list.size(); i++) {
				 ps1.setString(1, five_c_list.get(i).getApplicationId());
				  ps1.setString(2, five_c_list.get(i).getParameter());
				  ps1.setString(3, five_c_list.get(i).getValue());
				  ps1.setString(4, five_c_list.get(i).getAnalysisType());
				  ps1.addBatch();
			 }

			 int[] results = ps1.executeBatch();
			 
			 
			 String insert_checklist = "INSERT INTO loan_analysis (application_id, parameter, value, type) VALUES (?, ?, ?, ?)";
			 PreparedStatement ps4 = conn.prepareStatement(insert_checklist);			 
			 
			 for(int i=0; i<check_list.size(); i++) {
				 ps1.setString(1, check_list.get(i).getApplicationId());
				  ps1.setString(2, check_list.get(i).getParameter());
				  ps1.setString(3, check_list.get(i).getValue());
				  ps1.setString(4, check_list.get(i).getAnalysisType());
				  ps1.addBatch();
			 }

			 int[] results1 = ps1.executeBatch(); 
			 
			 String insert_note = "insert into recommendation_note (application_id, recommended_note, repayment_type) VALUES(?, ?, ?) ";
				
				PreparedStatement pst = conn.prepareStatement(insert_note);
				pst.setString(1, note.getApplicationId());
				pst.setString(2, note.getRecommendationNote());
				pst.setString(3, note.getRepaymentType());		
					
				pst.execute();
			conn.close();					 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

	@Override
	public AORecommendationNote getRecommendationNote(String application_id) throws RemoteException {
		AORecommendationNote note = new AORecommendationNote();
		try {
			String query = "";
			Connection conn = DbConnection.getDbConnection();
			
			query = "select * from recommendation_note where application_id  = '"+application_id+"' ";
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
				
			while(rs.next()) {			
				
				note.setRecommendationNote(rs.getString(3));
				note.setRepaymentType(rs.getString(4));				
				
			}
			
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return note;
	}

	@Override
	public boolean accountExist(Customer customer) throws RemoteException {
		boolean value = false;
		try {
			Connection conn = DbConnection.getDbConnection();
		
			String query = "select * from customer_profile where bvn = '"+customer.getBvn()+"' OR phone_no = '"+customer.getPhone()+"' OR (account_no  = '"+customer.getLmfbAccountNo()+"' and branch = '"+customer.getBranch()+"') ";
			
			
			
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			if (rs.next() == false) {     value = false;   } 
			else {		value = true;	}
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	@Override
	public boolean accountExistEdit(Customer customer) throws RemoteException {
		boolean value = false;
		try {
			Connection conn = DbConnection.getDbConnection();
		
			String query = "select * from customer_profile where bvn = '"+customer.getBvn()+"' OR phone_no = '"+customer.getPhone()+"' OR (account_no  = '"+customer.getLmfbAccountNo()+"' and branch = '"+customer.getBranch()+"') ";
			
			
			
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			if (rs.next() == false){     
				value = false;
			}else if( rs.getString(3) != customer.getLmfbAccountNo() ) {
				value = false; 
			}else if( rs.getString(9) != customer.getBvn()) {
				value = false; 
			}else if( rs.getString(10) != customer.getPhone() ) {
				value = false; 
			}else {
				value = true;	 
			}
				
			
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}

	
	
	
	
	
}
