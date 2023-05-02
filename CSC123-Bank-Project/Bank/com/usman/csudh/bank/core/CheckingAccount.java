package com.usman.csudh.bank.core;
import java.io.Serializable;

public class CheckingAccount extends Account implements Serializable{
	
	private static final long serialVersionUID = 1L;
	double overdraftLimit;
	String forex;
	
	public CheckingAccount(Customer customer,double od, String forex) {
		super("Checking",customer, forex);
		this.overdraftLimit=od;
		this.forex = forex;
	}


	//Withdrawal is not possible if the account is closed and has zero or negative
	public  void withdraw(double amount, String forex) throws InsufficientBalanceException{

		if (getBalance() + overdraftLimit - amount < 0) {
			throw new InsufficientBalanceException("Not enough funds to cover withdrawal");
		}

		super.withdraw(amount, forex);

	}

	public double getOverdraftLimit() {
		return overdraftLimit;
	}


	public void setOverdraftLimit(double overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}
	
	
}
