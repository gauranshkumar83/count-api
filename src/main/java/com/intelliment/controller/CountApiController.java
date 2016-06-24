package com.intelliment.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.intelliment.exception.CountApiException;
import com.intelliment.service.CountService;

/**
 * This is
 * 
 * @author Counter Api controller to full fill request from users.
 *
 */
@RestController
public class CountApiController {

	@Autowired
	CountService countService;

	private static final Logger logger = Logger.getLogger(CountApiController.class);

	/**
	 * This method returns number of words from counter-api database based upon
	 * <topValue> provided by user.
	 * 
	 * @param topValue
	 * @return ResponseEntity<Map<String, Integer>>
	 */
	@RequestMapping(value = "/top/{topValue}", method = RequestMethod.GET)
	public ResponseEntity<SortedMap<String, Integer>> getWordsByValue(@PathVariable("topValue") String topValue) {
		Integer value;
		logger.info("Request for first " + topValue + " Words");
		try {
			value = Integer.parseInt(topValue);
		} catch (Exception ex) {
			throw new CountApiException("BAD_REQUEST", "Provided value should be integer");
		}
		SortedMap<String, Integer> map = countService.getWordsListByValue(value);
		if (map == null) {
			logger.info("No match found with Input");
			throw new CountApiException("NO_CONTENT", "count-api database is empty");
		}
		logger.info("*****Request Completed****");
		return new ResponseEntity<SortedMap<String, Integer>>(map, HttpStatus.OK);
	}

	/**
	 * This method returns the words with their occurrence for
	 * <wordList> provided in request.
	 * 
	 * @param wordList
	 * @return ResponseEntity<Map<String, Integer>>
	 */
	@RequestMapping(value = "/search/", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Integer>> getWordsOccurence(@RequestBody String[] wordList) {
		logger.info("Request for Word Occurece");
		if (wordList == null || Collections.emptyList().equals(wordList)) {
			logger.info("Empty Request");
			throw new CountApiException("BAD_REQUEST", "Please provide data in input");
		}
		Map<String, Integer> wordsMap = countService.getOccurence(Arrays.asList(wordList));
		if (wordsMap != null && wordsMap.size() > 0) {
			logger.info("Request is completed for Word Occurece");
			return new ResponseEntity<Map<String, Integer>>(wordsMap, HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, Integer>>(HttpStatus.NO_CONTENT);
	}
	
	 @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	  public ResponseEntity<String> accessDeniedPage(ModelMap model) {
		 return new ResponseEntity<String>("You are not authorized User", HttpStatus.OK);
	    }
}
