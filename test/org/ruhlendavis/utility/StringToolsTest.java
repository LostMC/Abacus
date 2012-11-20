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
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
/**
 *
 * @author Feaelin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(StringTools.class)
public class StringToolsTest
{
	
	public StringToolsTest()
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
	 * Test of find_first_of method, of class StringTools.
	 */
	@Test
	public void testFind_first_of_String_String()
	{
		helper_test_find_first_of();
	}
	
	/**
	 * Nearly all the tests for find_first_of(String, String) and (String, String, int)
	 * are the same. They are in this method, rather than being duplicated in
	 * both Test Methods.
	 * 
	 */
	private void helper_test_find_first_of()
	{
		int position;
		
		position = StringTools.find_first_of("", "a", 0);
		Assert.assertEquals("search on empty string should return -1", -1, position);
		
		position = StringTools.find_first_of("alpha", "", 0);
		Assert.assertEquals("search with empty character string should return -1",
						            -1, position);
		
		position = StringTools.find_first_of("alpha", "z", 0);
		Assert.assertEquals("search for 'z' in 'alpha' should return -1",
						            -1, position);
		
		position = StringTools.find_first_of("alpha", "p", 0);
		Assert.assertEquals("search for 'p' in 'alpha' should return 2",
						            2, position);
				
	}
	
	/**
	 * Test of find_first_of method, of class StringTools.
	 */
	@Test
	public void testFind_first_of_3args()
	{
		int position;
		
		position = StringTools.find_first_of("alpha", "", 5);
		Assert.assertEquals("search start position greater than string length should return -1",
						            -1, position);
		
		helper_test_find_first_of();
	}
}
