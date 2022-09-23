package com.llp.server;

import java.text.DecimalFormat;

import com.llp.entities.LoanOfferPrice;
import com.llp.entities.LoanOfferView;

public class ServerAlgorithm {
	
	public static LoanOfferPrice calculateOfferPricing(LoanOfferView offer) {
		DecimalFormat fz = new DecimalFormat("##");
		LoanOfferPrice offer_price = new LoanOfferPrice();
		
		double principal = 0;
		double interest = 0;
		double management_fee = 0; 
		double risk_premium = 0; 
		double monitoring_fee = 0; 
		double compulsory_savings = 0; 	
		
		double upfront_interest = 0;
		
		double installment_payment_principal = 0; 
		double installment_payment_interest_charges = 0;  
		double total_installment_repayment = 0; 		
		
		double total_interest_charges  = 0;
		double total_compulsory_savings  = 0;
		double total_repayment  = 0;		
		
		double total_interest  = 0; 
		double total_monitoring_fee  = 0;  
		double total_risk_premium  = 0;
		double gross_total = 0;
		double percentage_gross_total  = 0;
		
		principal = offer.getAmountApproved();	
		
		double  tmp_interest = principal * offer.getInterestRate()/100;
		risk_premium = offer.getRiskPremiumRate()/100 * tmp_interest;
		DecimalFormat f2 = new DecimalFormat("##.00");	    
	    risk_premium = Double.parseDouble(f2.format(risk_premium));
		
		monitoring_fee = offer.getMonitoringFeeRate()/100 * tmp_interest;
		
		installment_payment_principal = principal / offer.getTenorApproved();
		
		if(offer.getInterestType().equalsIgnoreCase("Reducing Balance")) {
				   
			double reducing_interest_sum = 0;
			double internal_principal = principal;
			
			double  reducing_interest = 0;
			double installment_principal = internal_principal / offer.getTenorApproved();	
			double annual_interest_rate = Double.parseDouble(fz.format(offer.getInterestRate()*12.0));
			
			
			
			while(internal_principal>0){
				reducing_interest = internal_principal * (annual_interest_rate/12.0)/100;        
		        reducing_interest_sum = reducing_interest_sum + reducing_interest;
		        internal_principal = internal_principal - installment_principal;
		    }
		    interest = reducing_interest_sum / offer.getTenorApproved();
		    DecimalFormat f5 = new DecimalFormat("##.00");	    
		    interest = Double.parseDouble(f5.format(interest));		  
			
		}else if(offer.getInterestType().equalsIgnoreCase("Flat Rate")){
			interest = principal * offer.getInterestRate() / 100;			
			
		}else if(offer.getInterestType().equalsIgnoreCase("Balloon Payment")) {
			interest = principal * offer.getInterestRate() / 100;
			installment_payment_principal = 0.00;
		}else if(offer.getInterestType().equalsIgnoreCase("Balloon Payment (Upfront Interest)")) {
			interest = principal * offer.getInterestRate() / 100;
			installment_payment_principal = 0.00;
		}	
		
		management_fee = offer.getManagementFeeRate()/100 * principal;
		if (offer.getTenorTypeApproved().equalsIgnoreCase("quarters")) {
			compulsory_savings = offer.getCompulsorySavingsRate() /100 * principal * 3;		
			
			//re-payment schedule table installment payment;
		
			installment_payment_interest_charges = (interest + monitoring_fee + risk_premium) * 3;
		}else {
			compulsory_savings = offer.getCompulsorySavingsRate() /100 * principal;		
			
			//re-payment schedule table installment payment;
		
			installment_payment_interest_charges = interest + monitoring_fee + risk_premium;
		}
		
		
		total_interest_charges = installment_payment_interest_charges * offer.getTenorApproved();
		
		if(offer.getInterestType().equalsIgnoreCase("Balloon Payment (Upfront Interest)")) {
			interest = principal * offer.getInterestRate() / 100;
			installment_payment_principal = 0.00;
			installment_payment_interest_charges = 0.00;
			upfront_interest = total_interest_charges;
			total_interest_charges = 0.00;
		}
		
		
		
				
		//re-payment schedule table total payment;		
		
		
		//from installment payment schedule
		total_installment_repayment = installment_payment_principal + installment_payment_interest_charges + compulsory_savings;
		total_compulsory_savings = compulsory_savings * offer.getTenorApproved();
		total_repayment = principal + total_interest_charges + total_compulsory_savings;
		
		//pricing table
		total_interest = interest * offer.getTenorApproved();		
		total_monitoring_fee = monitoring_fee * offer.getTenorApproved();
		total_risk_premium = risk_premium * offer.getTenorApproved();
		DecimalFormat f3 = new DecimalFormat("##.00");	    
		total_risk_premium = Double.parseDouble(f3.format(total_risk_premium));
		DecimalFormat f31 = new DecimalFormat("##.00");	
		gross_total = total_interest + management_fee + total_monitoring_fee + total_risk_premium;
		gross_total = Double.parseDouble(f31.format(gross_total));
		
		DecimalFormat f32 = new DecimalFormat("##.00");	
		percentage_gross_total = gross_total / principal * 100; 
		percentage_gross_total = Double.parseDouble(f32.format(percentage_gross_total));
		
		
		offer_price.setPrincipal(principal);
		offer_price.setInterest(interest);
		offer_price.setManagementFee(management_fee);
		offer_price.setRiskPremium(risk_premium);
		offer_price.setMonitoringFee(monitoring_fee);
		offer_price.setCompulsorySavings(compulsory_savings);
		offer_price.setTempInterest(tmp_interest);
		
		offer_price.setInstallmentPaymentPrincipal(installment_payment_principal);
		offer_price.setInstallmentPaymentInterestCharges(installment_payment_interest_charges);
		offer_price.setTotalInstallmentRepayment(total_installment_repayment);
		
		offer_price.setTotalInterestCharges(total_interest_charges);
		offer_price.setTotalCompulsorySavings(total_compulsory_savings);
		offer_price.setTotalRepayment(total_repayment);
		
		offer_price.setUpfrontInterest(upfront_interest);
		
		offer_price.setTotalInterest(total_interest);
		offer_price.setTotalMonitoringFee(total_monitoring_fee);
		offer_price.setTotalRiskPremium(total_risk_premium);
		offer_price.setGrossTotal(gross_total);
		offer_price.setPercentageGrossTotal(percentage_gross_total);
		
		
		
		
		return offer_price;
		
	}
	
	
}
