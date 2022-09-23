package com.llp.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

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

public interface LLPMainInterface extends Remote {
	
	//user login 
	public User getUserLogin(String username, String password, String branch_name) throws RemoteException;
	
	//utility
	public void changePassword(String new_password, User user) throws RemoteException;
	
	
	//customer registration
	public ArrayList<String> createCustomerProfile(Customer customer, ArrayList<OtherBankAccount> bank_list) throws RemoteException, ParseException;
	
	public void updateCustomerProfile(Customer customer, ArrayList<OtherBankAccount> bank_list) throws RemoteException;
	
	//customer search frame
	public ArrayList<Customer> getCustomerList(String search_criterion, String search_text) throws RemoteException;
	
	public ArrayList<OtherBankAccount> getOtherBankAccounts(String customer_id) throws RemoteException;
	
	//loan application frame
	public String saveLoanApplication(LoanApplication loan_application, ArrayList<String> security_list) throws RemoteException;
	
	public void updateLoanApplication(LoanApplication loan_application, ArrayList<String> security_list) throws RemoteException;
	
	public void updateLoanApplicationStatus(String application_id, String status, String column_name, String value, String column2) throws RemoteException;

	//loan application search frame
	public ArrayList<LoanApplication> getApplicationList(User user, String search_criterion, String search_text) throws RemoteException;
	
	public ArrayList<LoanApplication> getApplicationsView(User user, String search_criterion, String search_text) throws RemoteException;
	
	public ArrayList<LoanApplication> getApplicationList(User user) throws RemoteException;
	
	public ArrayList<String> getLoanSecurities(String application_id) throws RemoteException;
	
	public String getRejectionNote(String application_id) throws RemoteException;
	
	public ArrayList<LoanApplication> getPrintableApplications(User user) throws RemoteException;
	
	public ArrayList<LoanApplication> getReturnedApplications(User user) throws RemoteException;
	
	//loan offer 
	public ArrayList<LoanProduct> getAllLoanProducts() throws RemoteException;
	
	public void createLoanOffer(String branch, LoanOffer offer) throws RemoteException;
	
	public void updateLoanOffer(LoanOffer loan_offer, String type) throws RemoteException;
	
	public void updateApplicationStatus(String application_id, String status) throws RemoteException;
	
	public void updateOfferStatus(LoanOfferView loan_offer, String action, String username ) throws RemoteException;
	
	public ArrayList<LoanOfferView> getLoanOfferList(String application_id) throws RemoteException;
	
		
	//reject Loan Offer
	public void rejectLoanOffer(String application_id, String rejection_type, String comment, String username) throws RemoteException;
	
	//new interview session
	public String createInterviewSession(String account_no, String user_id) throws RemoteException;
	
	public int getSessionStatus(String account_no) throws RemoteException;
	
	//interview face sheet
	public ArrayList<InterviewSession> getCustomerInterviews(String account_no) throws RemoteException;
	
	public ArrayList<QuestionAnswer> getQuestionAnswers(String interview_id) throws RemoteException;
	
	public ArrayList<BusinessCategory> getAllBusinessCategories() throws RemoteException;
	
	public ArrayList<MarketingQuestion> getMarketingQuestions(String selection, String b_category) throws RemoteException;
	
	public void saveQuestionAndAnswer(String interview_id, String question, String answer) throws RemoteException;
	
	public void updateQuestionAnswer(int sno, String question, String answer) throws RemoteException;
	
	public ArrayList<FinanceItem> getFinanceItems(String finance_type) throws RemoteException;
	
	public void createFinancialStatement(String interview_id, String finance_type, String item, double amount) throws RemoteException;
	
	public ArrayList<FinancialStatement> getInterviewExpenses(String interview_id) throws RemoteException;
	
	public ArrayList<FinancialStatement> getInterviewIncome(String interview_id) throws RemoteException;
	
	public void endInterviewSession(String interview_id) throws RemoteException;
	
	public void updateFinancialStatement(int sno, String finance_type, String item, double amount) throws RemoteException;
	
	//loan offer search frame
	public ArrayList<LoanOfferView> getLoanOfferList(User user, String search_criterion, String search_text, String offer_status) throws RemoteException;
	
	public ArrayList<LoanOfferView> getLoanOfferList(User user, String offer_status) throws RemoteException;
	
	public ArrayList<LoanOfferView> getLoanOfferView(User user, String search_criterion, String search_text) throws RemoteException;
	
	//Pending Loan Application Search Frame
	public ArrayList<LoanApplication> getApprovedLoanApplications( String branch) throws RemoteException;

	public ArrayList<LoanOfferView> getRejectedLoanOfferList(User user) throws RemoteException;
	
	public void updateSearchStatus(String application_id) throws RemoteException;
	
	public ArrayList<LoanApplication> getPendingCreditSearch(User user) throws RemoteException;
	
	public String databaseBackup(String filepath) throws RemoteException;
	
	
	
	//outstanding balance and account turnover
	public void saveOutstandingFacility(OutstandingFacility facility, String application_id) throws RemoteException;
	
	public void saveAccountTurnover(AccountTurnover turnover, String application_id) throws RemoteException;
	
	public void deleteOutstandingBalance(int sno) throws RemoteException;
	
	public void deleteAccountTurnover(int sno) throws RemoteException;
	
	public ArrayList<OutstandingFacility> getOutstandingFacilities(String application_id) throws RemoteException;
	
	public ArrayList<AccountTurnover> getAccountTurnovers(String application_id) throws RemoteException;
	
	//collateral note and customer background
	
	public void saveCollateralNote(CollateralNote note, String application_id) throws RemoteException;
	
	public CollateralNote getCollateralNotes(String application_id) throws RemoteException;
	
	public boolean collateralExists(String application_id) throws RemoteException;
	
	public void saveLoanRAC(ArrayList<ApprovalDisbursementParameters> list) throws RemoteException;
	
	public ArrayList<ApprovalDisbursementParameters> getLoanRAC(String application_id) throws RemoteException;
	
	public void saveLoanDocumentation(ArrayList<DocumentationParameters> list) throws RemoteException;
	
	public ArrayList<DocumentationParameters> getLoanDocumentation(String application_id) throws RemoteException;
	
	public boolean loanRACExists(String application_id) throws RemoteException;
	
	public boolean loanDocExists(String application_id) throws RemoteException;
	
	public void updateSecurities(String application_id, ArrayList<String> security_list) throws RemoteException;
	
	public void updateLoanMaturity(LoanOfferView loanOffer) throws RemoteException;
	
	public void updateLoanRecommededAmount(LoanApplication application) throws RemoteException;
	
	public void updateLoanApplicationStatus(String application_id, String status) throws RemoteException;
	
	public ArrayList<LoanOfferView> getCustomerLoanHistory(String customer_id) throws RemoteException;
	
	public int getApplicationsCount(String user, String status, String branch) throws RemoteException;
	
	public ArrayList<AppUserGroup> getUserGroups(String branch) throws RemoteException;
	
	public ArrayList<String> getUsernames(String user, String group_name, String branch) throws RemoteException;
	
	public void sendMessage(String sender, String receiver, String message) throws RemoteException;
	
	public void updateMessageStatus(String receiver, String sender) throws RemoteException;
	
	public int getNewMessageCount(String sender, String receiver) throws RemoteException;
	
	public int getGroupMessageCount(String receiver, String group) throws RemoteException;
	
	public int getBranchMessageCount(String receiver, String group) throws RemoteException;
	
	public ArrayList<LightMessage> getMessages(String user1, String user2) throws RemoteException;
	
	public int getNewMessagesCount(String receiver) throws RemoteException;
	
	public boolean analysisExists(String application_id) throws RemoteException;
	
	public ArrayList<LoanAnalysisParameters> getLoanAnalysisParams(String application_id, String type) throws RemoteException;
	
	public ArrayList<LoanAnalysisParameters> getLoanAnalysis(String application_id, String type) throws RemoteException;
	
	
	public void saveLoanAnalysis(ArrayList<LoanAnalysisParameters> five_c_list, ArrayList<LoanAnalysisParameters> check_list, AORecommendationNote note) throws RemoteException;
	
	public AORecommendationNote getRecommendationNote(String application_id) throws RemoteException;
	
	public boolean accountExist(Customer customer) throws RemoteException;
	
	public boolean accountExistEdit(Customer customer) throws RemoteException;
	

	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
