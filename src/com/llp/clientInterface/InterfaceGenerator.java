package com.llp.clientInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.llp.api.LLPMainInterface;
import com.llp.api.LLPReportInterface;
import com.llp.api.LLPSetupInterface;
import com.llp.entities.User;

public class InterfaceGenerator {
	
	//private static String serverIP = "127.0.0.1";
	private static String serverIP = "192.168.1.8";
	
	public InterfaceGenerator() {} 
	
	//method to connect to server and get  setup interface 
	public static LLPSetupInterface getSetupInterface() {		
		try {
			Registry registry = LocateRegistry.getRegistry(serverIP, 3334);
			LLPSetupInterface setup_interface = (LLPSetupInterface) registry.lookup("setup");
			return setup_interface;
		} catch (RemoteException | NotBoundException e) { e.printStackTrace();	}
			return null;		
	}
	
	public static LLPMainInterface getMainInterface() {		
		try {
			Registry registry = LocateRegistry.getRegistry(serverIP, 3335);
			LLPMainInterface main_interface = (LLPMainInterface) registry.lookup("main");
			return main_interface;
		} catch (RemoteException | NotBoundException e) { e.printStackTrace();	}
			return null;		
	}
	
	public static LLPReportInterface getReportInterface() {		
		try {
			Registry registry = LocateRegistry.getRegistry(serverIP, 3336);
			LLPReportInterface report_interface = (LLPReportInterface)registry.lookup("report");
			return report_interface;
		} catch (RemoteException | NotBoundException e) { e.printStackTrace();	}
			return null;		
	}
		

}
