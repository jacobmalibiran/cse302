package Q2;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;


public class WordDealUtilTest {

	private static WordDealUtil wordDealUtil = new  WordDealUtil();
	@Before
	public void setUp() throws Exception {
	}
	@Before
	public void echoBefore()
	{
		System.out.println("Check Test Environment! Start the Test");
	}
	
	@After
	public void echoAfter()
	{
		System.out.println("Test Finish! Check Result");
	}
	// Test wordEormat4DB with normal condition
	@Test
	public void wordEormat4DBNormal()
	{
		// add code here
	
	}
	// Test wordEormat4DB with normal condition
	@Test
	public void wordEormat4DBNull()
	{
		String target = null;
		String result = wordDealUtil.wordFormat4DB(target);
		assertNull(result);
	}
	
	// Test wordEormat4DB with empty string condition
	@Test
	public void wordEormat4DBEmpty()
	{
		//add code here
	
	}
	
	// Test First character with upper case
	@Test
	public void wordEormat4DBBegin()
	{
		//add code here
	}
	// Test Last character with upper case
	@Test
	public void wordEormat4DBEnd()
	{
		//add code here
	}
	// Test more than one character with upper case
	@Test
	public void wordEormat4DBTogether()
	{
		//add code here
	}

}
