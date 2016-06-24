package com.intelliment.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.intelliement.constant.Constants;
import com.intelliment.util.CountApiWordCountUtil;

/**
 * CounterServiceImpl fulfills request for counter-api application.
 * 
 * @author Intelliment
 *
 */
@Service("countService")
public class CountServiceImpl implements CountService {

	private static final Logger logger = Logger.getLogger(CountServiceImpl.class);

	/**
	 * This method will be called in life cycle of CounterServiceImpl.Once
	 * CounterService instance is created,this method will be called
	 * automatically as part of lifecycle.This method constructs the data for
	 * service to fulfill request from users.
	 * 
	 */
	@Override
	@PostConstruct
	public void wordCountMap() {
		logger.info("***** Method to create Word Count Map for application to serve request ******");
		try {
			CountApiWordCountUtil.wordCountMapFromFile(Constants.FILENAME);
		} catch (Exception ex) {
			logger.error("Exception occurs while setting up counter-api database " + ex.getLocalizedMessage());
		}
	}

	/**
	 * It returns occurrence of words coming as a request in parameter<List
	 * <String> wordList> from word count database.If word is not present in
	 * database,then it will return 0 as occurrence.
	 */
	@Override
	public Map<String, Integer> getOccurence(List<String> wordList) {
		logger.info("***** getOccurence(List<String> wordList) ******");
		logger.info("***** Get request to find word occurence ******");
		Map<String, Integer> wordMap = new LinkedHashMap<String, Integer>();
		for (String value : wordList) {
			Integer wordOccurence = CountApiWordCountUtil.getWordCountMap().get(value);
			wordMap.put(value, wordOccurence == null ? 0 : wordOccurence);
		}
		logger.info("***** Request Completed ******");
		return wordMap;
	}

	/**
	 * It returns word List with their occurrence from word count database based
	 * upon <value> parameter.<value> parameter decides the size of map returns.
	 */
	@Override
	public SortedMap<String, Integer> getWordsListByValue(Integer value) {
		logger.info("***** getWordsListByValue(Integer value) ******");
		logger.info("***** Get request to find words with value " + value + " ******");
		SortedMap<String, Integer> headMap = CountApiWordCountUtil.getHeadMap(value);
		logger.info("***** Request Completed ******");
		return headMap;
	}
}
