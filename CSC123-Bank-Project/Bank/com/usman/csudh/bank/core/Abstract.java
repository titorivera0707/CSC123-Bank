package com.usman.csudh.bank.core;
import java.io.*;
import java.util.*;

public abstract class Abstract {
	
	public static Abstract getInstance(String type) throws Exception{
		if(type.equalsIgnoreCase("file")) {
			return new HooksFile();
		}
		else if(type.equalsIgnoreCase("webservice")) {
			return new HooksHTTP();
		}
		else {
			throw new Exception("Type "+type+ " not accepted");
		}
	}
	
	protected abstract InputStream getInputStream() throws Exception; 
	
	public ArrayList<String> readCurrencies() throws Exception{
		InputStream inStream=getInputStream();
		BufferedReader buffRead=new BufferedReader(new InputStreamReader(inStream));
		ArrayList<String> arrList=new ArrayList<String>();
		
		String newLine=null;
		//read lines 
		while((newLine=buffRead.readLine())!=null) {
			arrList.add(newLine);
		}
		
		return arrList;
		
	}

}
