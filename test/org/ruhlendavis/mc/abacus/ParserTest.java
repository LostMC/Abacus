/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ruhlendavis.mc.abacus;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 *
 * @author iain
 */
@PrepareForTest(Parser.class)
public class ParserTest
{

	public ParserTest()
	{
	}

	@BeforeClass
	public static void setUpClass()
	{
	}

	@AfterClass
	public static void tearDownClass()
	{
	}

	@Before
	public void setUp()
	{
	}

	@After
	public void tearDown()
	{
	}

	/**
	 * Test of Constructor with an String argument, of class Parser.
	 *
	 */
	@Test
	public void testConstructorString()
	{
		String testString = "1+1";

		Parser parser = new Parser(testString);

		Assert.assertEquals(testString, parser.getOriginalExpression());
	}

	/**
	 * Test of Constructor with an Array Arg, of class Parser.
	 *
	 */
	@Test
	public void testConstructorArray()
	{
		String [] testArray = { "1", "+", "1" };
		String testString = "";

		for (String element : testArray)
		{
			testString = testString + element;
		}

		Parser parser = new Parser(testArray);

		Assert.assertEquals(testString, parser.getOriginalExpression());
	}

	/**
	 * Test of getResult method, of class Parser.
	 */
	@Test
	public void testGetResult()
	{
		String testString = "1+1";
		String testResult = "2";

		Parser parser = new Parser(testString);

		Assert.assertEquals(testResult, parser.getResult());
	}

	@Test
	public void testExpressionWithStackOperand()
	{
		String testString = "1s";
		String testResult = "64";

		Parser parser = new Parser(testString);

		Assert.assertEquals(testResult, parser.getResult());
	}

	@Test
	public void testExpressionWithPartialStackOperand()
	{
		String testString = "1p";
		String testResult = "16";

		Parser parser = new Parser(testString);

		Assert.assertEquals(testResult, parser.getResult());
	}

	@Test
	public void testExpressionWithStackResult()
	{
		String testString = "s1";
		String testResult = "0 stacks and 1 individual items.";

		Parser parser = new Parser(testString);

		Assert.assertEquals(testResult, parser.getResult());
	}

	@Test
	public void testExpressionWithPartialStackResult()
	{
		String testString = "p1";
		String testResult = "0 stacks and 1 individual items.";

		Parser parser = new Parser(testString);

		Assert.assertEquals(testResult, parser.getResult());
	}

	/**
	 * Testing that the comma operator operates correctly.
	 */
	@Test
	public void testCommaOperatorBasic()
	{
		String testString = "1,1";
		String testResult = "1, 1";

		Parser parser = new Parser(testString);

		Assert.assertEquals(testResult, parser.getResult());
	}

	/**
	 * Testing that the comma operator operates correctly.
	 */
	@Test
	public void testCommaOperatorComplex()
	{
		String testString = "1+2,3*3";
		String testResult = "3, 9";

		Parser parser = new Parser(testString);

		Assert.assertEquals(testResult, parser.getResult());
	}

	/**
	 * Testing that the return from prepare string is consistent.
	 */
	@Test
	public void testPrepareExpressionReturn()
	{
		String testString = "1";
		String testResult = "1";

		Parser parser = new Parser(testString);

		Assert.assertEquals(testResult, parser.getResult());
		Assert.assertEquals(testResult, parser.prepareExpression());
	}

	/**
	 * Test that prepare string succeeds in stripping spaces.
	 */
	@Test
	public void testPrepareExpressionStripsWhitespace()
	{
		String testString = "1 +\t1 *\n 3\n";
		String testResult = "1+1*3";

		Parser parser = new Parser(testString);

		Assert.assertEquals(testResult, parser.prepareExpression());
	}

	/**
	 * Test of prepareExpression method, of class Parser.
	 */
	@Test
	public void testPrepareExpression()
	{
	}
}
