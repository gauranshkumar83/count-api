package com.intelliment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCounterApiDatabase {

	public static List<String> getWordList() {
		List<String> wordList = new ArrayList<String>();
		wordList.add("Duis");
		wordList.add("eget");
		wordList.add("et");
		return wordList;
	}
	
	public static Map<String,Integer> getWordCountMap(){
		Map<String,Integer> wordCountData = new HashMap<String,Integer>();
		wordCountData.put("Duis", 17);
		wordCountData.put("eget", 1);
		wordCountData.put("et", 33);
		wordCountData.put("Hello", 3);
		return wordCountData;
	}

}
