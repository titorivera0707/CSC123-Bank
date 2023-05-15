package com.usman.csudh.bank.core;
import java.io.*;
import java.util.*;

public abstract class Abstract {
	
	public static String newString;
	
	public static Abstract getInstance(String type) throws Exception{
		if(type.equalsIgnoreCase("file")) {
			newString = new HooksFile().toString();
			return new HooksFile();
		}
		else if(type.equalsIgnoreCase("webservice")) {
			newString = new HooksFile().toString();
			return new HooksHTTP();
		}
		else {
			throw new Exception("Type "+type+ " not understood!");
		}
	}
	
	protected abstract InputStream getInputStream() throws Exception; 
	
	public ArrayList<String> readCurrencies() throws Exception{
		//get an input steam
		InputStream in=getInputStream();
		//Create stream readers / buffered reader
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		
		ArrayList<String> list=new ArrayList<String>();
		
		String line=null;
		//read lines 
		while((line=br.readLine())!=null) {
			//add lines to arraylist
			list.add(line);
		}
		
		//return array list 
		
		return list;
		
	}

}
