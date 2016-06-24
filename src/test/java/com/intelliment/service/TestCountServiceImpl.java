package com.intelliment.service;
import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.intelliment.util.CountApiWordCountUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CountApiWordCountUtil.class)
public class TestCountServiceImpl {
	
	
	private CountServiceImpl countServiceImpl;
	
	@Before
	public void setup() throws Exception {
		countServiceImpl = new CountServiceImpl();
		PowerMockito.mockStatic(CountApiWordCountUtil.class);
		PowerMockito.when(CountApiWordCountUtil.getWordCountMap()).thenReturn(TestCounterApiDatabase.getWordCountMap());
	}



	@Test
	public void testGetOccurrence(){
		List<String> wordList = TestCounterApiDatabase.getWordList();
		Map<String,Integer> wordCountMap = countServiceImpl.getOccurence(wordList);
		int size = wordCountMap.get("Duis");
		System.out.println(size);
		assertEquals(size, 17);
	}
}
