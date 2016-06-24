package com.intelliment.service;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Counter-Api
 * 
 * @author Intelliment
 *
 */
public interface CountService {
	public void wordCountMap() throws Exception;

	public Map<String, Integer> getOccurence(List<String> wordList);

	public SortedMap<String, Integer> getWordsListByValue(Integer value);
}
