package com.usman.csudh.bank.core;
import java.io.*;
import java.util.*;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Bank {
	
	private static Map<Integer,Account> accounts=new TreeMap<Integer,Account>();
	private static Account newA = new Account (null, null, null);
	
	public static Account openCheckingAccount(String firstName, String lastName, String ssn, String forex, double overdraftLimit) {
		Customer c=new Customer(firstName,lastName, ssn, newA.findForex(forex));
		Account a=new CheckingAccount(c,overdraftLimit, newA.findForex(forex));
		accounts.put(a.getAccountNumber(), a);
		System.out.println("Account currency: " + newA.findForex(forex));
		return a;
		
	}
	
	public static Account openSavingAccount(String firstName, String lastName, String ssn, String forex) {
		Customer c=new Customer(firstName,lastName, ssn, newA.findForex(forex));
		Account a=new SavingAccount(c, newA.findForex(forex));
		accounts.put(a.getAccountNumber(), a);
		System.out.println("Account currency: " + newA.findForex(forex));
		return a;
		
	}
	
	public static Account lookup(int accountNumber) throws NoSuchAccountException{
		if(!accounts.containsKey(accountNumber)) {
			throw new NoSuchAccountException("\nAccount number: "+accountNumber+" nout found!\n\n");
		}
		
		return accounts.get(accountNumber);
	}
	
	public static String getForEx(int accountNumber) throws NoSuchAccountException{
		
		return lookup(accountNumber).getForVal();
		
	}
	
public static double getForExAmount(int accountNumber) throws NoSuchAccountException, NumberFormatException, IOException, InterruptedException{
		
		return lookup(accountNumber).getConv();
		
	}
	
	public static void makeDeposit(int accountNumber, double amount) throws AccountClosedException, NoSuchAccountException{
		
		lookup(accountNumber).deposit(amount,lookup(accountNumber).getForVal());
		
	}
	
	public static void currencyConv(int accountNumber, String firstInput, String secondInput) throws NoSuchAccountException{
		if(!firstInput.equals("USD")&&!secondInput.equals("USD")) {
			System.out.println("One value must be USD. Please try again.");
		}
		else if(firstInput.equals("USD")||secondInput.equals("USD")) {
				lookup(accountNumber).setForVal(secondInput);
		}
		
	}
	
	public static void makeWithdrawal(int accountNumber, double amount) throws InsufficientBalanceException, NoSuchAccountException {
		lookup(accountNumber).withdraw(amount,lookup(accountNumber).getForVal());
	}
	
	public static void closeAccount(int accountNumber) throws NoSuchAccountException {
		lookup(accountNumber).close();
	}

	
	public static double getBalance(int accountNumber) throws NoSuchAccountException {
		return lookup(accountNumber).getBalance();
	}
	
	public static void getAccountInfo(int accountNumber) throws NoSuchAccountException, NumberFormatException, IOException, InterruptedException {
		System.out.println("Account Number: " + accountNumber);
		System.out.println("Name: "+ lookup(accountNumber).custName());
		System.out.println("SSN: " + lookup(accountNumber).custSSN());
		System.out.println("Currency: " + lookup(accountNumber).getForVal());
		System.out.println("Currency Balance: "+ lookup(accountNumber).getForVal()+ String.format("%.2f", lookup(accountNumber).getConv()));
		System.out.println("USD Balance: USD" + lookup(accountNumber).getBalance());
	}

	public static void listAccounts(OutputStream out) throws IOException{
		
		out.write((byte)10);
		Collection<Account> col=accounts.values();
		
		for (Account a:col) {
			out.write(a.toString().getBytes());
			out.write((byte)10);
		}
		
		out.write((byte)10);
		out.flush();
	}
	
public static void getForexFileReader() throws IOException, InterruptedException {
	
		newA.forexFileReader();
	
	}
	
	public static void printAccountTransactions(int accountNumber, OutputStream out) throws IOException,NoSuchAccountException{
		
		lookup(accountNumber).printTransactions(out);
	}
				
	
	
	
	
}
