package com.usman.csudh.bank.core;
import java.io.Serializable;

import com.usman.csudh.util.UniqueCounter;

public class Transaction implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final char CREDIT='C';
	public static final char DEBIT='D';
	public static String forex;
	
	int txnID;
	double amount;
	char type; //D|C
	public Transaction(char type,double amount, String forex) {
		super();
		this.amount = amount;
		this.type = type;
		this.forex = forex;
		this.txnID=UniqueCounter.nextValue();
		
	}

	public double getAmount() {
		return amount;
	}
	 
	public char getType() {
		return type;
	}
	
	public String getForex() {
		return forex;
	}
	
	public String toString() {
		return (this.txnID+":"+(type==this.CREDIT?"Deposit":"Withdrawal")+"\t: "+amount);
		
	}
	
	
	

}
