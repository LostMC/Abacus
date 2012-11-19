package org.ruhlendavis.mc.abacus;

/**
 * Provides a special exception class for the Parser to throw.
 * 
 * @author Feaelin (Iain E. Davis) <iain@ruhlendavis.org>
 */
public class ParserMathException extends ArithmeticException
{
	/**
	 * Basic constructor.
	 */
	ParserMathException()
	{
		super();
	}

	/**
	 * Message constructor. Merely calls ArithmeticException constructor with the
	 * message.
	 * 
	 * @param message Message for the thrown exception.
	 */
	ParserMathException(String message)
	{
		super(message);
	}
}
