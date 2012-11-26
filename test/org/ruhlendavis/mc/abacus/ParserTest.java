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

/**
 *
 * @author iain
 */
public class ParserTest {

	public ParserTest() {
	}

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
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

	/**
	 * Test of setExpression method, of class Parser.
	 */
	@Test
	public void testSetExpression()
	{
	}

	/**
	 * Test of prepareExpression method, of class Parser.
	 */
	@Test
	public void testPrepareExpression()
	{
	}
}