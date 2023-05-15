package com.usman.csudh.bank.core;
import java.util.*;
import java.io.*;
import java.net.URI;
import java.net.http.*;

import com.usman.csudh.util.UniqueCounter;

public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String accountName;
	private Customer accountHolder;
	private ArrayList<Transaction> transactions;
	private ArrayList<String> newforexArray;
	private ArrayList<String> forexPairs;
	
	private boolean open=true;
	private int accountNumber;
	private String newForex;

	protected Account(String name, Customer customer, String forex) {
		accountName=name;
		accountHolder=customer;
		String newForex = forex;
		transactions=new ArrayList<Transaction>();
		newforexArray = new ArrayList<String>();
		forexPairs = new ArrayList<String>();
		accountNumber=UniqueCounter.nextValue();
	}
	
	public String getAccountName() {
		return accountName;
	}

	public Customer getAccountHolder() {
		return accountHolder;
	}
	
	public String custName() {
		return accountHolder.getFirstName() + " " +accountHolder.getLastName();
	}
	
	public String custSSN() {
		return accountHolder.getSSN();
	}

	public ArrayList<String> getForexPairs() {
		return forexPairs;
	}
	public String getForVal()
	{
		return accountHolder.getForex();
	}
	public String setForVal(String forex) {
		return accountHolder.setForex(forex);
	}

	public void setForexPairs(ArrayList<String> forexPairs) {
		this.forexPairs = forexPairs;
	}
	

	public double getBalance() {
		double workingBalance=0;
		
		for(Transaction t: transactions) {
			if(t.getType()==Transaction.CREDIT)workingBalance+=t.getAmount();
			else workingBalance-=t.getAmount();
		}
		
		return workingBalance;
	}
	
	
	public void deposit(double amount, String forex)  throws AccountClosedException{
		double balance=getBalance();
		if(!isOpen()&&balance>=0) {
			throw new AccountClosedException("\nAccount is closed with positive balance, deposit not allowed!\n\n");
		}
		transactions.add(new Transaction(Transaction.CREDIT,amount, forex));
	}
	
	
	
	
	public void withdraw(double amount, String forex) throws InsufficientBalanceException {
			
		double balance=getBalance();
			
		if(!isOpen()&&balance<=0) {
			throw new InsufficientBalanceException("\nThe account is closed and balance is: "+balance+"\n\n");
		}
		
		transactions.add(new Transaction(Transaction.DEBIT,amount, forex));
	}
	
public ArrayList forexFileReader() throws Exception {
	
	BufferedReader readFile = new BufferedReader(new FileReader("config.txt"));
	String read;
	String newRead = "";
	String[] parser;
	String httpURL = null;
	ArrayList<String> newArr = new ArrayList<String>();
	Abstract instance = null;
	
	while((read = readFile.readLine()) != null) {
		newArr.add(read);
	}
	
	if(newArr.get(0).equals("support.currencies=false")) {
		System.out.println("The file does not support currencies.");
	}else if(newArr.get(0).equals("support.currencies=true")) {
		if(newArr.get(1).equals("currencies.source=file")) {
			instance = Abstract.getInstance("file");
		}else {
			instance = Abstract.getInstance("webservice");
		}
		
		String newInstance = instance.toString();
		
		for(String line:instance.readCurrencies()) {
			newRead = newRead + line + "\n";
		}
		
		String data = newRead;
		String fullData = "";
		String newFull = "";
		String currency;
		String forexConv;
		String[] newSplit;
		String[] initInfo;
		
		initInfo = data.split("\n");
		
		for(int i=0; i<initInfo.length; i++) {
			newFull = newFull + initInfo[i] + ",";
		}
		
		newSplit = newFull.split(",");
	
		for(int x=0; x < (newSplit.length); x = x+3) {
			currency = newSplit[x];
			forexConv = newSplit[x+2];
			forexPairs.add(currency);
			forexPairs.add(forexConv);
		}
		
//		parser = newArr.get(2).split(" ");
//		httpURL = parser[1];
//		
//		HttpRequest.Builder builder=HttpRequest.newBuilder();
//		builder.uri(URI.create("http://www.usman.cloud/banking/exchange-rate.csv"));
//
//		builder.method("GET", HttpRequest.BodyPublishers.noBody());
//		
//		//Step 2
//		HttpRequest req=builder.build();
//			
//		//Step 3
//		
//		HttpClient client=HttpClient.newHttpClient();
//						
//		//Step 4
//		
//		HttpResponse<String> response = 
//			client.send(req, HttpResponse.BodyHandlers.ofString());
//			String data = response.body();
//			String fullData = "";
//			String newFull = "";
//			String currency;
//			String forexConv;
//			String[] newSplit;
//			String[] initInfo;
//			
//			initInfo = data.split("\n");
//			
//			for(int i=0; i<initInfo.length; i++) {
//				newFull = newFull + initInfo[i] + ",";
//			}
//			
//			newSplit = newFull.split(",");
//		
//			for(int x=0; x < (newSplit.length); x = x+3) {
//				currency = newSplit[x];
//				forexConv = newSplit[x+2];
//				forexPairs.add(currency);
//				forexPairs.add(forexConv);
//			}
//	}
//		
	}
	return forexPairs;
}

public String valVal(String forexValue) throws Exception{
	if (forexFileReader().isEmpty()){
		return "1";
	}else {
	return (String) forexFileReader().get((forexFileReader().indexOf(forexValue)+1));
	}
}

public String findForex(String newFor) {
	
	if (forexPairs.contains(newFor)) {
		return newFor;
	}
	else return "USD";
	
}

public double getConv() throws Exception{		
	double forexBalance = 0;
	double workingBalance=0;
	
	
	
	for(Transaction t: transactions){
		if(t.getType()==Transaction.CREDIT) {
			workingBalance+=t.getAmount();
			forexBalance = Double.parseDouble(valVal(getForVal()));
		}
		else {
			workingBalance-=t.getAmount();
			forexBalance = Double.parseDouble(valVal(getForVal()));
		}
	}
	
	
	return workingBalance/forexBalance;
}
	
	public void close() {
		open=false;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	
	public String toString() {
		String aName = null;
		try {
			aName = accountNumber+"("+accountName+")"+" : "+accountHolder.toString()+ String.format("%.2f", getConv()) + " : (USD)"+getBalance()+ " : "+(open?"Account Open":"Account Closed");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aName;
	}
	 
	public void printTransactions(OutputStream out) throws IOException {
		
		out.write("\n\n".getBytes());
		out.write("------------------\n".getBytes());
	
		for(Transaction t: transactions) {
			out.write(t.toString().getBytes());
			out.write((byte)10);
		}
		out.write("------------------\n".getBytes());
		out.write(("Balance: "+getBalance()+"\n\n\n").getBytes());
		out.flush();
		
	}
}
