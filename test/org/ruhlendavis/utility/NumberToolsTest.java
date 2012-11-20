/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ruhlendavis.utility;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author Feaelin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({NumberTools.class, Math.class})
public class NumberToolsTest
{
	
	public NumberToolsTest()
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
	 * Test of randomNumber method, of class NumberTools.
	 */
	@Test
	public void testRandomNumber()
	{
		int minimum = 1;
		int maximum = 10;
		
		PowerMockito.mockStatic(Math.class);
		
		when(Math.random()).thenReturn(0.0);
		Assert.assertEquals("When random() produces 0.0, randomNumber should return 1",
						            1, NumberTools.randomNumber(minimum, maximum));
		when(Math.random()).thenReturn(0.5);
		Assert.assertEquals("When random() produces 0.5, randomNumber should return 6",
						            6, NumberTools.randomNumber(minimum, maximum));
		when(Math.random()).thenReturn(0.9);
		Assert.assertEquals("When random() produces 0.9, randomNumber should return 10",
						            10, NumberTools.randomNumber(minimum, maximum));
		
	}

	/**
	 * Test of divideRoundUpAny method, of class NumberTools.
	 */
	@Test
	public void testDivideRoundUpAny()
	{
		Assert.assertEquals("1 divided by 2 should equal 1", 1,
						            NumberTools.divideRoundUpAny(1, 2));
		Assert.assertEquals("2 divided by 2 should equal 1", 1,
						            NumberTools.divideRoundUpAny(2, 2));
		Assert.assertEquals("2 divided by -2 should equal -1", -1,
						            NumberTools.divideRoundUpAny(2, -2));
		Assert.assertEquals("-2 divided by -2 should equal 1", 1,
						            NumberTools.divideRoundUpAny(-2, -2));
		Assert.assertEquals("1 divided by -2 should equal -1", -1,
						            NumberTools.divideRoundUpAny(1, -2));
	}

	/**
	 * Test of divideRoundUpPositive method, of class NumberTools.
	 */
	@Test
	public void testDivideRoundUpPositive()
	{
		Assert.assertEquals("1 divided by 2 should equal 1", 1,
						            NumberTools.divideRoundUpPositive(1, 2));
		Assert.assertEquals("2 divided by 2 should equal 1", 1,
						            NumberTools.divideRoundUpPositive(2, 2));
	}
}
