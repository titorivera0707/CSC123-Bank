package com.usman.csudh.bank.core;
import java.util.*;
import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HooksHTTP extends Abstract{

	protected InputStream getInputStream() throws Exception {
		HttpRequest.Builder builder=HttpRequest.newBuilder();
		builder.uri(URI.create("http://www.usman.cloud/banking/exchange-rate.csv"));

		builder.method("GET", HttpRequest.BodyPublishers.noBody());
		
		//Step 2
		HttpRequest req=builder.build();
			
		//Step 3
		
		HttpClient client=HttpClient.newHttpClient();
						
		//Step 4
		
		HttpResponse<String> response = 
			client.send(req, HttpResponse.BodyHandlers.ofString());
		
		byte[] bytes = response.body().getBytes();
        return new ByteArrayInputStream(bytes);
	
	}
	
}