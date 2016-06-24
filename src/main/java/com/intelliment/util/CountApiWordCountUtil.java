package com.intelliment.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.intelliement.constant.Constants;
import com.intelliment.controller.CountApiController;
import com.intelliment.exception.CountApiException;
import com.test.Test;

/**
 * This class read file from a specific location and populates Map of words with
 * their count.It has two maps one is sorted and other one is unsorted to solve
 * different purposes.
 * 
 * @author Intelliment
 *
 */
public class CountApiWordCountUtil {

	private static Map<String, Integer> wordCountMap = new HashMap<String, Integer>();
	private static SortedMap<String, Integer> sortedWordCountMap = new TreeMap<String, Integer>();
	private static final Logger logger = Logger.getLogger(CountApiController.class);

	public static Map<String, Integer> getWordCountMap() {
		return wordCountMap;
	}

	

	public static SortedMap<String, Integer> getSortedWordCountMap() {
		return sortedWordCountMap;
	}

	

	public static void main(String[] args) throws Exception {
		String fileName = Constants.FILENAME;
		wordCountMapFromFile(fileName);
	}

	/**
	 * This method read the file from physical location and create a map which
	 * will further create a sort map using unsorted map.
	 * 
	 * @throws Exception
	 */
	public static void wordCountMapFromFile(String fileName) throws Exception {
		logger.info("****** Begin wordCountMapFromFile  ******");
		InputStream inputStream = Test.class.getClassLoader().getResourceAsStream(Constants.FILENAME);
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		String strLine;
		logger.info("****** Start Reading File " + Constants.FILENAME + "  ******");
		while ((strLine = br.readLine()) != null && strLine.length() > 0) {
			// Remove unused characters from content of file such as . , which
			// can be any in future.
			strLine = strLine.replaceAll(Constants.UNUSED_CHARACTERS, "");
			String[] words = strLine.split(" ");
			if (words != null && words.length > 0) {
				createWordCountMap(words);
			}
		}
		logger.info("****** File Reading Done " + Constants.FILENAME + "  ******");
		// sort map based on values
		if (wordCountMap != null && wordCountMap.size() > 0) {
			logger.info("****** size of word count map :  " + wordCountMap.size());
			sortedWordCountMap = sortByValues(wordCountMap);
			logger.info("****** sorting of word count map is done ******");
		} else {
			throw new CountApiException("Counter-Api Database", "Counter-API Data base is empty");
		}
		br.close();
		logger.info("****** End wordCountMapFromFile  ******");
	}

	/**
	 * This methods create a map of word with their count in file.
	 * 
	 * @param words
	 */
	private static void createWordCountMap(String[] words) {
		for (String word : words) {
			word = word.replaceAll("[ ]", "");
			if (wordCountMap.containsKey(word)) {
				wordCountMap.put(word, wordCountMap.get(word) + 1);
			} else {
				wordCountMap.put(word, 1);
			}
		}
	}

	/**
	 * It sorts the provided map based upon their values in decreasing order.
	 * 
	 * @param map
	 * @return
	 */
	private static <K, V extends Comparable<V>> SortedMap<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = map.get(k2).compareTo(map.get(k1));
				if (compare == 0)
					return 1;
				else
					return compare;
			}
		};
		SortedMap<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}

	/**
	 * This method returns the sub map based upon the provided value.sub map
	 * will have the size of value.
	 * 
	 * @param value
	 * @return
	 */
	public static SortedMap<String, Integer> getHeadMap(int value) {
		int sizeOfMap = sortedWordCountMap.size();
		if (value >= sizeOfMap) {
			return sortedWordCountMap;
		}
		value = value >= sizeOfMap ? sizeOfMap : value;
		int count = 0;
		SortedMap<String, Integer> target = new TreeMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : sortedWordCountMap.entrySet()) {
			if (count >= value)
				break;
			target.put(entry.getKey(), entry.getValue());
			count++;
		}
		return target;
	}
}
