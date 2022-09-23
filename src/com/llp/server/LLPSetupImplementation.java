package com.llp.server;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import com.llp.api.LLPSetupInterface;
import com.llp.dbconnection.DbConnection;
import com.llp.entities.ApprovalDisbursementParameters;
import com.llp.entities.Branch;
import com.llp.entities.BusinessCategory;
import com.llp.entities.DocumentationParameters;
import com.llp.entities.FinanceItem;
import com.llp.entities.LoanProduct;
import com.llp.entities.MarketingQuestion;
import com.llp.entities.User;
import com.llp.entities.UserGroup;

public class LLPSetupImplementation implements LLPSetupInterface {

	//method to get all branches in database
	@Override
	public ArrayList<Branch> getAllBranches() throws RemoteException {
		ArrayList<Branch> branch_list = new ArrayList<>();
		
		try {
		
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from branches";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				Branch branch = new Branch();
				branch.setBranchId(rs.getString(2));
				branch.setBranchName(rs.getString(3));
				
				branch_list.add(branch);			
			}
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
 		
		return branch_list;
	}

	@Override
	public ArrayList<String> createBranch(String branch_name) throws RemoteException {
		ArrayList<String> list = new ArrayList<String>();
		
		String error_message = null;
		String branch_id = null;
		int value = 0;
		
		try {
		Connection conn = DbConnection.getDbConnection();
		String query = "select * "
				+ "from id_generator "
				+ "where ref_table = 'branches' ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			value = rs.getInt("value");
			String idValue = value + "";
			branch_id = "bc-"+ leftPad( idValue, 3);
			
			String insert_query = "insert into branches(branch_id, branch_name) VALUES(?, ?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, branch_id);
			pst.setString(2, branch_name);
			
			pst.execute();			
			
		}
		
		String update_query = "UPDATE id_generator "
								+ "set value = ? "
								+ "where ref_table = 'branches' ";
		
		PreparedStatement ps = conn.prepareStatement(update_query);
		ps.setInt(1, (value+1));
		ps.executeUpdate();
		
		conn.close();
		}catch(SQLIntegrityConstraintViolationException ex) { error_message = "Branch name already exists."; }
		catch(NullPointerException ex) {error_message = "Unable to connect to server. ";}
		catch(Exception ex) { ex.printStackTrace(); }		
		list.add(branch_id);
		list.add(error_message);
		return list;
	
	}

	@Override
	public String updateBranch(String branch_id, String branch_name) throws RemoteException {
		String error_message = null;
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE branches "
									+" SET branch_name = ? "
									+" WHERE branch_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, branch_name);
			ps.setString(2, branch_id);
			
			ps.executeUpdate();
			
			conn.close();
		}catch(SQLIntegrityConstraintViolationException ex) { error_message = "Branch name already exists."; }
		catch(NullPointerException ex) {error_message = "Unable to connect to server. ";}
		catch(Exception ex) { ex.printStackTrace(); }
		
		
		return error_message;
	}
		
	public String leftPad(String value, int lgt) {
		int diff = lgt - value.length();
		
		for(int i=0; i<diff; i++) {
			value = "0" + value;
		}				
		return value;
	}
	
	@Override
	public ArrayList<String> createBusinessCategory(String category_desc) throws RemoteException {
		ArrayList<String> list = new ArrayList<String>();		
		String error_message = null;		
		String category_id = null;
		int value = 0;
		
		try {
		Connection conn = DbConnection.getDbConnection();
		String query = "select * "
				+ "from id_generator "
				+ "where ref_table = 'business_categories' ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			value = rs.getInt("value");
			String idValue = value + "";
			category_id = "ct-"+ leftPad( idValue, 3);
			
			String insert_query = "insert into business_categories(category_id, category_description) VALUES(?, ?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, category_id);
			pst.setString(2, category_desc);
				
			pst.execute();			
			
		}
		
		String update_query = "UPDATE id_generator "
								+ "set value = ? "
								+ "where ref_table = 'business_categories' ";
		
		PreparedStatement ps = conn.prepareStatement(update_query);
		ps.setInt(1, (value+1));
		ps.executeUpdate();
		
		conn.close();
		}catch(SQLIntegrityConstraintViolationException ex) { error_message = "Business category already exists."; }
		catch(NullPointerException ex) {error_message = "Unable to connect to server. ";}
		catch(Exception ex) { ex.printStackTrace(); }		
		
		list.add(category_id);
		list.add(error_message);
		
		return list;
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
	public void updateBusinessCategory(String category_id, String category_desc) throws RemoteException {
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE business_categories "
									+" SET category_description = ? "
									+" WHERE category_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, category_desc);
			ps.setString(2, category_id);
			
			ps.executeUpdate();
			
			conn.close();
			}catch(Exception ex) { 
				ex.printStackTrace();
				}
			
	}

		
	@Override
	public void createFinanceItem(String finance_type, String item) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();				
			
			String insert_query = "insert into financial_statement_items(finance_type, item) VALUES(?, ?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, finance_type);
			pst.setString(2, item);
			
			pst.execute();			
		
			conn.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
			
	}

	
	@Override
	public void updateFinanceItem(int sno, String finace_type, String item) throws RemoteException {
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE financial_statement_items "
									+" SET finance_type = ?, item = ? "
									+" WHERE sno = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, finace_type);
			ps.setString(2, item);
			ps.setInt(3, sno);
			
			ps.executeUpdate();
			
			conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }
		
	}

	
	@Override
	public ArrayList<FinanceItem> getAllFinanceItems() throws RemoteException {
		ArrayList<FinanceItem> item_list = new ArrayList<>();
		
		try {
		
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from financial_statement_items";
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
	public ArrayList<String> createLoanProduct(LoanProduct product) throws RemoteException {
		ArrayList<String> list = new  ArrayList<String>();
		String error_message = null;
		String product_id = null;
		int value = 0;
		
		try {
		Connection conn = DbConnection.getDbConnection();
		String query = "select * "
				+ "from id_generator "
				+ "where ref_table = 'loan_products' ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			value = rs.getInt("value");
			String idValue = value + "";
			product_id = "lp"+ leftPad( idValue, 3);
			
			String insert_query = "insert into loan_products(product_id, product_description, interest_rate, mon_fee_rate, risk_prem_rate, mgt_fee_rate, comp_sav_rate)"
					+ " VALUES(?, ?,?,?,?,?,?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, product_id);
			pst.setString(2, product.getProductDescription());
			pst.setDouble(3, product.getIntrestRate());
			pst.setDouble(4, product.getMonFeeRate());
			pst.setDouble(5, product.getRiskPremRate());
			pst.setDouble(6, product.getMgtFeeRate());
			pst.setDouble(7, product.getCompSavingsRate());			
			
			pst.execute();			
			
		}
		
		String update_query = "UPDATE id_generator "
								+ "set value = ? "
								+ "where ref_table = 'loan_products' ";
		
		PreparedStatement ps = conn.prepareStatement(update_query);
		ps.setInt(1, (value+1));
		ps.executeUpdate();
		
		conn.close();
		}catch(SQLIntegrityConstraintViolationException ex) { error_message = "Product already exists."; }
		catch(NullPointerException ex) {error_message = "Unable to connect to server. ";}
		catch(Exception ex) { ex.printStackTrace(); }		
		list.add(product_id);
		list.add(error_message);
		
		return list;
	}

	
	@Override
	public String updateLoanProduct(LoanProduct product) throws RemoteException {
		String error_message = null;
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE loan_products "
									+" SET product_description = ?,  interest_rate = ?, mon_fee_rate = ?, risk_prem_rate = ?, mgt_fee_rate = ?, comp_sav_rate = ? "
									+" WHERE product_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, product.getProductDescription());			
			ps.setDouble(2, product.getIntrestRate());
			ps.setDouble(3, product.getMonFeeRate());
			ps.setDouble(4, product.getRiskPremRate());
			ps.setDouble(5, product.getMgtFeeRate());
			ps.setDouble(6, product.getCompSavingsRate());			
			ps.setString(7, product.getProductId());
			
			ps.executeUpdate();
			conn.close();
		}catch(SQLIntegrityConstraintViolationException ex) { error_message = "Product already exists."; }
		catch(NullPointerException ex) {error_message = "Unable to connect to server. ";}
		catch(Exception ex) { ex.printStackTrace(); }
		
		return error_message;
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
	public String createMarketingQuestion(String category_id, String category_desc) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();			
			
			String insert_query = "insert into mkt_interview_questions(category_id, question) VALUES(?, ?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, category_id);
			pst.setString(2, category_desc);
				
			pst.execute();			
			
			conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }		
		
		return category_id;
	}

	
	@Override
	public void updateMQuestion(String category_id, String question, int sno) throws RemoteException {
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE mkt_interview_questions "
									+" SET question = ?, category_id = ? "
									+" WHERE sno = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, question);
			ps.setInt(3, sno);
			ps.setString(2, category_id);
			
			ps.executeUpdate();
			conn.close();
		}catch(Exception ex) { ex.printStackTrace(); }
		
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
	public ArrayList<String> createUserGroup(String group_name, int group_cl) throws RemoteException {

		ArrayList<String> list = new ArrayList<String>();
		String error_message = null;
		String group_id = null;
		int value = 0;
		
		try {
		Connection conn = DbConnection.getDbConnection();
		String query = "select * "
				+ "from id_generator "
				+ "where ref_table = 'user_groups' ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			value = rs.getInt("value");
			String idValue = value + "";
			group_id = "ug-"+ leftPad( idValue, idValue.length());
			
			String insert_query = "insert into user_groups(group_id, group_name, group_cl) VALUES(?, ?, ?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, group_id);
			pst.setString(2, group_name);
			pst.setInt(3, group_cl);
			
			pst.execute();			
			
		}
		
		String update_query = "UPDATE id_generator "
								+ "set value = ? "
								+ "where ref_table = 'user_groups' ";
		
		PreparedStatement ps = conn.prepareStatement(update_query);
		ps.setInt(1, (value+1));
		ps.executeUpdate();
		
		conn.close();
		}catch(SQLIntegrityConstraintViolationException ex) { error_message = "User group already exists."; }
		catch(NullPointerException ex) {error_message = "Unable to connect to server. ";}
		catch(Exception ex) { ex.printStackTrace(); }
		
		list.add(group_id);
		list.add(error_message);
		
		return list;
	}

	
	@Override
	public String updateUserGroup(String group_id, String group_name, int access_level) throws RemoteException {
		String error_message = null;
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE user_groups "
									+" SET group_name = ?, group_cl = ? "
									+" WHERE group_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, group_name);
			ps.setInt(2, access_level);
			ps.setString(3, group_id);
			
			ps.executeUpdate();
			
			conn.close();
		}catch(SQLIntegrityConstraintViolationException ex) { error_message = "User group already exists."; }
		catch(NullPointerException ex) {error_message = "Unable to connect to server. ";}
		catch(Exception ex) { ex.printStackTrace(); }
		
		return error_message;
	}

	
	@Override
	public ArrayList<UserGroup> getAllUserGroups() throws RemoteException {
		ArrayList<UserGroup> ug_list = new ArrayList<>();
		
		try {
		
			Connection conn = DbConnection.getDbConnection();
			String query = "SELECT * FROM user_groups ORDER BY 2";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				UserGroup ug = new UserGroup();
				ug.setGrouId(rs.getString(2));
				ug.setGroupName(rs.getString(3));
				ug.setAcessLevel(rs.getInt(4));
				
				ug_list.add(ug);			
			}
			conn.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
 		
		return ug_list;
	}

	
	@Override
	public ArrayList<String> createUser(User user) throws RemoteException {
		ArrayList<String> return_list = new ArrayList<>();
		String error_message = null;
		
		String user_id = null;
		int value = 0;
		
		try {
		Connection conn = DbConnection.getDbConnection();
		String query = "select * "
				+ "from id_generator "
				+ "where ref_table = 'application_users' ";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()) {
			value = rs.getInt("value");
			String idValue = value + "";
			user_id = "usr-"+ leftPad( idValue, 4);
			
			String insert_query = "insert into application_users(user_id, full_name, username, password, group_id, branch_id, user_status, gender) VALUES(?, ?, ?,?,?,?,?,?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, user_id);
			pst.setString(2, user.getFullName());
			pst.setString(3, user.getUsername());
			pst.setString(4, user.getPassword());
			pst.setString(5, user.getUserGroup());
			pst.setString(6, user.getBranch());
			pst.setInt(7, user.getUserStatus());
			pst.setString(8, user.getGender());
			
			pst.execute();			
			
		}
		
		String update_query = "UPDATE id_generator "
								+ "set value = ? "
								+ "where ref_table = 'application_users' ";
		
		PreparedStatement ps = conn.prepareStatement(update_query);
		ps.setInt(1, (value+1));
		ps.executeUpdate();
		
		conn.close();
		}catch(SQLIntegrityConstraintViolationException ex) { error_message = "Username or Fullname already exists."; }
		catch(NullPointerException ex) {error_message = "Unable to connect to server. ";}
		catch (SQLException e) { e.printStackTrace(); }
		
		return_list.add(user_id);
		return_list.add(error_message);
		return return_list;
	}

	
	@Override
	public String updateUser(User user) throws RemoteException {
		String error_message = null;
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE application_users "
									+" SET full_name = ?, username = ?, password = ?, group_id=?, branch_id = ?, user_status = ?, gender =? "
									+" WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, user.getFullName());
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setString(5, user.getBranch());
			ps.setString(4, user.getUserGroup());
			ps.setString(7, user.getGender());
			ps.setInt(6, user.getUserStatus());
			
			ps.setString(8, user.getUserId());
			
			ps.executeUpdate();
			conn.close();
		}catch(SQLIntegrityConstraintViolationException ex) { error_message = "Username or Fullname already exists."; }
		catch(NullPointerException ex) {error_message = "Unable to connect to server. ";}
		catch(Exception ex) { ex.printStackTrace(); }
		
		return error_message;
	}

	
	@Override
	public ArrayList<User> getAllUsers() throws RemoteException {
		ArrayList<User> user_list = new ArrayList<>();
		
		try {
		
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from app_users_view";
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				
				User user = new User();
				user.setUserId(rs.getString(1));
				user.setFullName(rs.getString(2));
				user.setUsername(rs.getString(3));
				user.setUserGroup(rs.getString(6));
				user.setBranch(rs.getString(8));
				user.setUserStatus(rs.getInt(10));
				user.setGender(rs.getString(11));
				
				user_list.add(user);			
			}
			
		}catch(NullPointerException ex) {ex.printStackTrace();}
		catch(Exception ex) {
			ex.printStackTrace();
		}
 		
		return user_list;
	} 
	

	@Override
	public void saveApprovalChecklistItem(String parameter, String Status) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();			
			
			String insert_query = "insert into approval_dist_parameters(parameter, status) VALUES(?, ?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, parameter);
			pst.setString(2, Status);
			
				
			pst.execute();
			
			conn.close();
		
		}catch(Exception ex) { ex.printStackTrace(); }
		
	}

	
	@Override
	public void updateApprovalChecklistItem(String parameter, String Status, int sno) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE approval_dist_parameters "
									+" SET parameter = ?, status = ?"
									+" WHERE sno = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, parameter);
			ps.setString(2, Status);
			ps.setInt(3, sno);	
			
			ps.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	public ArrayList<ApprovalDisbursementParameters> getApprovalChecklistItems() throws RemoteException {
		ArrayList<ApprovalDisbursementParameters> list = new ArrayList<ApprovalDisbursementParameters>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from approval_dist_parameters ";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				ApprovalDisbursementParameters params = new ApprovalDisbursementParameters();	
				params.setSno(rs.getInt(1));
				params.setParameter(rs.getString(2));
				params.setStatus(rs.getString(3));
				
				list.add(params);
				
			}
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return list;
	}
		

	@Override
	public void saveDocumentionChecklistItem(String parameter, String Status) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();			
			
			String insert_query = "insert into documentation_parameters(parameter, status) VALUES(?, ?) ";
			
			PreparedStatement pst = conn.prepareStatement(insert_query);
			pst.setString(1, parameter);
			pst.setString(2, Status);
			
				
			pst.execute();
			
			conn.close();
		
		}catch(Exception ex) { ex.printStackTrace(); }
		
	}

	@Override
	public ArrayList<DocumentationParameters> getDocumentionChecklistItems() throws RemoteException {
		ArrayList<DocumentationParameters> list = new ArrayList<DocumentationParameters>();
		try {
			
			Connection conn = DbConnection.getDbConnection();
			String query = "select * from documentation_parameters ";									
						
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				DocumentationParameters params = new DocumentationParameters();	
				params.setSno(rs.getInt(1));
				params.setParameter(rs.getString(2));
				params.setStatus(rs.getString(3));
				
				list.add(params);
				
			}
		conn.close();
			
		}catch(Exception ex) { ex.printStackTrace(); }
		
		return list;
	}

	
	@Override	
	public void updateDocumentionChecklistItem(String parameter, String Status, int sno) throws RemoteException {
		try {
			Connection conn = DbConnection.getDbConnection();
			String update_query = "UPDATE documentation_parameters "
									+" SET parameter = ?, status = ?"
									+" WHERE sno = ?";
			PreparedStatement ps = conn.prepareStatement(update_query);
			ps.setString(1, parameter);
			ps.setString(2, Status);
			ps.setInt(3, sno);	
			
			ps.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
