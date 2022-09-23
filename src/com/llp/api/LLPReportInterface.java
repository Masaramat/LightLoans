package com.llp.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;

import com.llp.entities.LoanApplication;
import com.llp.entities.LoanOfferView;
import com.llp.entities.LoanSecurity;
import com.llp.entities.RejectedLoan;

public interface LLPReportInterface extends Remote {
	
	public ArrayList<LoanApplication> getLoanApplicationsReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate)
			throws RemoteException;
	
	public ArrayList<LoanOfferView> getLoanOffersReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate) 
			throws RemoteException;
	
	public  ArrayList<LoanOfferView> getLoanAuditReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate) 
			throws RemoteException;
	
	public  ArrayList<LoanOfferView> getLoanDisburseReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate) 
			throws RemoteException;
	
	public ArrayList<LoanOfferView> getLoanOfferView(String application_id, String source) throws RemoteException;
	
	public ArrayList<LoanApplication> getLoanApplication(String application_id) throws RemoteException;
	
	public ArrayList<LoanSecurity> getLoanSecurities(String application_id) throws RemoteException;

	public ArrayList<String> getLoanApplicationUsers() throws RemoteException;
	
	public ArrayList<String> getBranchNames() throws RemoteException;
	
	public ArrayList<String> getAuditors() throws RemoteException;
	
	public ArrayList<String> getLoanApplicationDisburser() throws RemoteException;
	
	public ArrayList<String> getCreditOfficers() throws RemoteException;
	
	public ArrayList<RejectedLoan> getRejectedLoansReport(ArrayList<String> column_list, ArrayList<String> data_list, Date fromDate, Date todateDate) throws RemoteException;
	
	
}
 