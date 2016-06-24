package com.intelliment.client;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class WordAppClient {

	public static final String REST_SERVICE_URI = "http://localhost:8080/counter-api";
	public static void main(String[] args) throws Exception {
		
		getWordOccurence();
	}
	
	/* POST */
    private static void getWordOccurence() {
		System.out.println(" ----- Testing Get Occurence -----");
    	RestTemplate restTemplate = new RestTemplate();
        String[] searchContent = {"Duis","Sed","124"}; 
        @SuppressWarnings("unchecked")
		Map<String,Integer> wordMap = restTemplate.postForObject(REST_SERVICE_URI+"/search/",new HttpEntity(createHeaders("vipin","vipin")),Map.class);
        if(wordMap!=null){
        printMap(wordMap);
        }
        
    }
    
   private static HttpHeaders createHeaders( String username, String password ){
    	   return new HttpHeaders(){
    	      {
    	         String auth = username + ":" + password;
    	         byte[] encodedAuth = Base64.getEncoder().encode(
    	            auth.getBytes(Charset.forName("US-ASCII")) );
    	         String authHeader = "Basic " + new String( encodedAuth );
    	         set( "Authorization", authHeader );
    	      }
    	   };
    	}

    
    private static void printMap(Map<String, Integer> sortedWordCountMap) {
		System.out.println(" ---- Printing Map ----");
		
		for (Entry<String, Integer> entry : sortedWordCountMap.entrySet()) {
			System.out.println(entry.getKey() + "::" + entry.getValue());
		}
	}
	/// get top 5

}
