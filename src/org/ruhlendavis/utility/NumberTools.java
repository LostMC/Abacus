package org.ruhlendavis.utility;

/**
 * A small collection of number related methods that don't belong anywhere else.
 * 
 * By design, this class cannot be instantiated nor can it be sub-classed.
 * 
 * @author Feaelin (Iain E. Davis) <iain@ruhlendavis.org>
 */
public final class NumberTools
{
	/**
	 * Private constructor to prevent instantiation of the utility class.
	 */
	private NumberTools()
	{
		throw new AssertionError();
	}
	
	/**
	 * Returns a random number in a given range. This uses Math.random(), so
	 * it only has Math.random()'s degree of randomness.
	 * 
	 * @param minimum Lowest possible number to return.
	 * @param maximum Highest possible number to return.
	 * @return The random number.
	 */
	public static int randomNumber(int minimum, int maximum)
	{
		return minimum + (int)(Math.random() * (maximum - minimum + 1));	
	}
	
	/**
	 *  Utility function to perform integer division that rounds up. This one is
	 *  safe to use with negative numbers.
	 * 
	 * @param number			long integer, number to be divided.
	 * @param divisor			long integer, number to divide by.
	 * @return						long integer result.
	 * @see #roundUpPositive
	 */
	public static long roundUpAny(long number, long divisor)
	{
    int sign = (number > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
    return sign * (Math.abs(number) + Math.abs(divisor) - 1) / Math.abs(divisor);
	}
	
	/**
	 *  Utility function to perform integer division that rounds up.
	 *  NOTE: Will not operate correctly on negative numbers, but is possibly
	 *  faster than roundUpAny()
	 * 
	 * @param number			long integer, number to be divided.
	 * @param divisor			long integer, number to divide by.
	 * @return						long integer result.
	 * @see #roundUpAny
	 */
	public static long roundUpPositive(long number, long divisor)
	{
    return (number + divisor - 1) / divisor;
	}	
}
