package com.llp.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.llp.entities.ApprovalDisbursementParameters;
import com.llp.entities.Branch;
import com.llp.entities.BusinessCategory;
import com.llp.entities.DocumentationParameters;
import com.llp.entities.FinanceItem;
import com.llp.entities.LoanProduct;
import com.llp.entities.MarketingQuestion;
import com.llp.entities.User;
import com.llp.entities.UserGroup;

public interface LLPSetupInterface extends Remote{
	
	// the following methods belong to the branch configuration class
	public ArrayList<Branch> getAllBranches() throws RemoteException;
	
	public ArrayList<String> createBranch(String branch_name) throws RemoteException;
	
	public String updateBranch(String branch_id, String branch_name) throws RemoteException;
	
	
	
	//these methods belong to the business category configuration class
	public ArrayList<String> createBusinessCategory(String category_desc) throws RemoteException;
	
	public ArrayList<BusinessCategory> getAllBusinessCategories() throws RemoteException;
	
	public void updateBusinessCategory(String category_id, String category_desc) throws RemoteException;
	
	
	
	//these methods belong to the financial statement configuration class
	public void createFinanceItem(String finance_type, String item) throws RemoteException;
	
	public void updateFinanceItem(int sno, String finace_type, String item) throws RemoteException;
	
	public ArrayList<FinanceItem> getAllFinanceItems() throws RemoteException;
	
	
	//these methods belong to the loan product configuration class
	public ArrayList<String> createLoanProduct(LoanProduct product) throws RemoteException;
	
	public String updateLoanProduct(LoanProduct product) throws RemoteException;
	
	public ArrayList<LoanProduct> getAllLoanProducts() throws RemoteException;
	
	
	//these methods belong to the marketing questions configuration class
	public String createMarketingQuestion(String category_id, String category_desc) throws RemoteException;
	
	public void updateMQuestion(String category_id, String question, int sno) throws RemoteException;
	
	public ArrayList<MarketingQuestion> getMarketingQuestions(String selection, String b_category) throws RemoteException;
	
	
	//these methods belong to the user groups configuration class
	public ArrayList<String> createUserGroup(String group_name, int group_cl) throws RemoteException;
	
	public String updateUserGroup(String group_id, String group_name, int access_level) throws RemoteException;
	
	public ArrayList<UserGroup> getAllUserGroups() throws RemoteException;
	
	
	//these methods belong to the user management class
	public ArrayList<String> createUser(User user) throws RemoteException;
	
	public String updateUser(User user) throws RemoteException;
	
	public ArrayList<User> getAllUsers() throws RemoteException;
	
	//approval disbursement checklist
	public void saveApprovalChecklistItem(String parameter, String Status) throws RemoteException;
	
	public void updateApprovalChecklistItem(String parameter, String Status, int sno) throws RemoteException;
	
	public ArrayList<ApprovalDisbursementParameters> getApprovalChecklistItems() throws RemoteException;
	
	
	//documentation checklist
		public void saveDocumentionChecklistItem(String parameter, String Status) throws RemoteException;
		
		public void updateDocumentionChecklistItem(String parameter, String Status, int sno) throws RemoteException;
		
		public ArrayList<DocumentationParameters> getDocumentionChecklistItems() throws RemoteException;
	


}
