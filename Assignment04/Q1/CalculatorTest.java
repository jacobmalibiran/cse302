package Q1;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CalculatorTest {
	private static Calculator calculator = new Calculator();
	@Before
	public void setUp() throws Exception {
		calculator.clear();
	}

	@Test
	public void testAdd() {
		calculator.add(2);
		calculator.add(3);

		int target = 5;
		int result = calculator.getResult();

		assertEquals(target, result);

	}

	@Test
	public void testSubstract() {
		calculator.add(10);
		calculator.substract(2);

		int target = 8;
		int result = calculator.getResult();

		assertEquals(target, result);

	}

	@Ignore("Multiply() Not yet implemented")
	@Test
	public void testMultiply() {
	}

	@Test
	public void testDivide() {
		calculator.add(8);
		calculator.divide(2);

		int target = 4;
		int result = calculator.getResult();

		assertEquals(target, result);
	}

	@Test
	public void testSquare() {
		calculator.square(2);

		int target = 4;
		int result = calculator.getResult();

		assertEquals(target, result);
	}

	// Q5 requests tests for squareRoot() and multiply(), but these are not yet implemented.
}