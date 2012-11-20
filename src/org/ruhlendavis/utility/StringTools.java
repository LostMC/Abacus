package org.ruhlendavis.utility;

/**
 * A small collection of number related methods that don't belong anywhere else.
 * 
 * By design, this class cannot be instantiated nor can it be sub-classed.
 * 
 * @author Feaelin (Iain E. Davis) <iain@ruhlendavis.org>
 */
public final class StringTools
{
	/**
	 * Private constructor to prevent instantiation of the utility class.
	 */
	private StringTools()
	{
		throw new AssertionError();
	}

	/**
	 *  Finds the first character in a String that matches any of the characters in
	 *  another String, starting with the first character of the string.
	 * 
	 *  @param string String containing the string to search.
	 *  @param characters String containing the characters to search for.
	 *  @return int indicating the location in the search string of a character
	 *          found or -1 if not found.
	 *  @see find_first_of(String, String, int)
	 */
	public static int find_first_of(String string, String characters)
	{
		return find_first_of(string, characters, 0);
	}
	
	/**
	 *  Finds the first character in a String that matches any of the characters in
	 *  another String
	 *  @param string String containing the string to search.
	 *  @param characters String containing the characters to search for.
	 *  @param startingPoint int indicating where in the search string to start.
	 *  @return int indicating the location in the search string of a character
	 *          found or -1 if not found.
	 */
	public static int find_first_of(String string, String characters, int startingPoint)
	{
		for (int position = startingPoint; position < string.length(); position++)
		{
			if (characters.indexOf(string.charAt(position)) > -1)
			{
				return position;
			}
		}
		return -1;
	}
	public static void main(String [] a)
	{
		System.out.println(StringTools.find_first_of("", "a", 0));
	}
}