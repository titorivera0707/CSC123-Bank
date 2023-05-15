package com.usman.csudh.bank.core;
import java.util.*;
import java.io.*;

public class HooksFile extends Abstract{

	@Override
	protected InputStream getInputStream()throws Exception {
		return new FileInputStream(new File("exchange-rate.csv"));
	}
	
}
