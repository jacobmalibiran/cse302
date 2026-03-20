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
		String target = "employee_info";
		String result = wordDealUtil.wordFormat4DB("employeeInfo");
		assertEquals(target, result);
	}
	// Test wordEormat4DB with normal condition
	@Test
	public void wordEormat4DBNull()
	{
		// This question was not mentioned on the Canvas page. This is what I assume was expected.
		String result = wordDealUtil.wordFormat4DB(null);
		assertNull(result);
	}

	// Test wordEormat4DB with empty string condition
	@Test
	public void wordEormat4DBEmpty()
	{
		String target = "";
		String result = wordDealUtil.wordFormat4DB("");
		assertEquals(target, result);
	}

	// Test First character with upper case
	@Test
	public void wordEormat4DBBegin()
	{
		// For some reason the Canvas instructions request the same target and result as wordEormat4DBNormal().
		// As this would not be useful, I instead capitalized the first letter.
		String target = "employee_info";
		String result = wordDealUtil.wordFormat4DB("EmployeeInfo");
		assertEquals(target, result);
	}
	// Test Last character with upper case
	@Test
	public void wordEormat4DBEnd()
	{
		String target = "employee_info_a";
		String result = wordDealUtil.wordFormat4DB("employeeInfoA");
		assertEquals(target, result);
	}
	// Test more than one character with upper case
	@Test
	public void wordEormat4DBTogether()
	{
		String target = "employee_a_info";
		String result = wordDealUtil.wordFormat4DB("employeeAInfo");
		assertEquals(target, result);
	}

}