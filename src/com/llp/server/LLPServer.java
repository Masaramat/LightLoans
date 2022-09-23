package com.llp.server;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import com.llp.api.LLPMainInterface;
import com.llp.api.LLPReportInterface;
import com.llp.api.LLPSetupInterface;
import com.llp.entities.User;

public class LLPServer {
	
	
	static LLPSetupImplementation llpSetup = new LLPSetupImplementation();
	static LLPMainImplementation llpMain = new LLPMainImplementation();
	static LLPReportImplementation llpReport = new LLPReportImplementation();
	
	private static Registry registry = null;
	private static Registry registry_2 = null;
	private static Registry registry_3 = null;
	
		
	
	public LLPServer() {}
	
	public static void main(String[] args) throws RemoteException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				startServer();
			}
		});
	}
	
	
	public static void startServer(){
		try {
			
			System.setProperty("java.rmi.server.hostname", "192.168.1.8");
			//System.setProperty("java.rmi.server.hostname", "127.0.0.1");
			
			registry = LocateRegistry.createRegistry(3334);					 
			LLPSetupInterface setup = (LLPSetupInterface) UnicastRemoteObject.exportObject(llpSetup, 0);
			registry.rebind("setup", setup);
			
			registry_2 = LocateRegistry.createRegistry(3335);					 
			LLPMainInterface main = (LLPMainInterface) UnicastRemoteObject.exportObject(llpMain, 0);
			registry_2.rebind("main", main);
			
			registry_3 = LocateRegistry.createRegistry(3336);					 
			LLPReportInterface report = (LLPReportInterface) UnicastRemoteObject.exportObject(llpReport, 0);
			registry_3.rebind("report", report);
					
			System.out.println("Light Loans Server started at "+ new Date()+ "... ");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void stopServer(){
		try {			
			registry.unbind("setup");			
			UnicastRemoteObject.unexportObject(registry, true);		
						
			registry_2.unbind("main");			
			UnicastRemoteObject.unexportObject(registry_2, true);			
			
			registry_3.unbind("report");
			UnicastRemoteObject.unexportObject(registry_3, true);
					
			System.out.println("LLP Server has stopped... ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	

}
