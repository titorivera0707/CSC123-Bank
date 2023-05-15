package com.usman.csudh.bank.core;
import java.util.*;
import java.io.*;

public class ForEx {
	
	public static String data;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		TreeSet<String> treeSet = new TreeSet<>();
		String data;
		File file = new File("./exchange-rate.csv");
		
		Scanner readFile = new Scanner(file);
		
		while(readFile.hasNext()) {
			data = readFile.nextLine();
			treeSet.add(data);
		}
		
		System.out.println(treeSet);
		
	}
	
	public ForEx() {	
		
	}
	
	public void getFile() {
		
		ArrayList<String> ForexArray = new ArrayList<String>();
		
	}
	
	public void getForEx() {
		
		
	}

}