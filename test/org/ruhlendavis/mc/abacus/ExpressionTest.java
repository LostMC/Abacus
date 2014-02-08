package org.ruhlendavis.mc.abacus;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iain E. Davis <iain@ruhlendavis.org>
 */
public class ExpressionTest
{
	private Expression expression = new Expression();

	@Test
	public void getOriginalNeverNull()
	{
		expression.evaluate();
		assertNotNull(expression.getOriginalText());
	}
	
	@Test
	public void getPreparedNeverNull()
	{
		expression.evaluate();
		assertNotNull(expression.getPreparedText());
	}
	
	@Test
	public void emptyOriginalBegetsEmptyPrepared()
	{
		expression.setExpression("");
		expression.evaluate();
		assertEquals("", expression.getPreparedText());
	}
	
	@Test
	public void whiteSpaceOriginalBegetsEmptyPrepared()
	{
		String expressionText = "  ";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals("", expression.getPreparedText());
	}
	
	@Test
	public void preparedTextShouldNotHaveWhiteSpace()
	{
		String expressionText = "1    +     1";
		String cleanExpression = "1+1";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(cleanExpression, expression.getPreparedText());
	}

	@Test
	public void preparedTextReplaceLeftAngleBracketsWithLeftParens()
	{
		String expressionText = "<";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals("(", expression.getPreparedText());
	}
	
	@Test
	public void preparedTextReplaceRightAngleBracketsWithRightParens()
	{
		String expressionText = ">";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(")", expression.getPreparedText());
	}
	
	@Test
	public void preparedTextReplaceLeftCurlyBraceWithLeftParens()
	{
		String expressionText = "{";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals("(", expression.getPreparedText());
	}

	@Test
	public void preparedTextReplaceRightCurlyBraceWithRightParens()
	{
		String expressionText = "}";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(")", expression.getPreparedText());
	}
	
	@Test
	public void preparedTextReplaceLeftSquareBraceWithLeftParens()
	{
		String expressionText = "[";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals("(", expression.getPreparedText());
	}

	@Test
	public void preparedTextReplaceRightSquareBraceWithRightParens()
	{
		String expressionText = "]";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(")", expression.getPreparedText());
	}
	
	@Test
	public void preparedTextReplaceMultiplicationShortHandLeft()
	{
		String expressionText = "3(6)";
		String expected = "3*(6)";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(expected, expression.getPreparedText());
	}
	
	@Test
	public void preparedTextReplaceMultiplicationShortHandRight()
	{
		String expressionText = "(6)3";
		String expected = "(6)*3";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(expected, expression.getPreparedText());
	}
	
	@Test
	public void preparedTextReplaceMultiplicationShortHandBoth()
	{
		String expressionText = "2(6)3";
		String expected = "2*(6)*3";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(expected, expression.getPreparedText());
	}
	
	@Test
	public void preparedTextLeadingNegativeShouldStayHyphen()
	{
		String expressionText = "-2";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(expressionText, expression.getPreparedText());
	}
	
	@Test
	public void preparedTextSubtractPositiveShouldAddNegative()
	{
		String expressionText = "2-2";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals("2+-2", expression.getPreparedText());
	}
	
	@Test
	public void preparedTextSubtractNegativeShouldAddPositive()
	{
		String expressionText = "2--2";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals("2+2", expression.getPreparedText());
	}
	
	@Test
	public void preparedTextNegationOfParenExpressionShouldMultiply()
	{
		String expressionText = "2-(3-2)";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals("2+-1*(3+-2)", expression.getPreparedText());
	}
	
	@Test
	public void subExpressionAddNegativeNumberShouldStay()
	{
		String expressionText = "2+-1";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals("2+-1", expression.getPreparedText());
		
	}
	
	@Test
	public void preparedTextNoneStackGetsSet()
	{
		String expressionText = "1";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(StackType.NONE, expression.getStackType());
	}
	
	@Test
	public void preparedTextFullStackGetsSet()
	{
		String expressionText = "s1";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(StackType.FULL, expression.getStackType());
	}
	
	@Test
	public void preparedTextFullStackSymbolGetsTrimmed()
	{
		String expressionText = "s1";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals("1", expression.getPreparedText());
	}
	
	@Test
	public void preparedTextPartialStackGetsSet()
	{
		String expressionText = "p1";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(StackType.PARTIAL, expression.getStackType());
	}
	
	@Test
	public void preparedTextPartialStackSymbolGetsTrimmed()
	{
		String expressionText = "p1";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals("1", expression.getPreparedText());
	}

	@Test
	public void tokensShouldNeverReturnNull()
	{
		assertNotNull(expression.getTokens());
	}
	
	@Test
	public void tokensOneToken()
	{
		String expressionText = "1";
		expression.setExpression(expressionText);
		expression.evaluate();
		assertEquals(1, expression.getTokens().size());
		assertEquals("1", expression.getTokens().get(0));
	}
	
	@Test
	public void tokensParentheses()
	{
		expression.setExpression("(15+15)");
		expression.evaluate();
		assertEquals(5, expression.getTokens().size());
		assertEquals("(", expression.getTokens().get(0));
		assertEquals("15", expression.getTokens().get(1));
		assertEquals("+", expression.getTokens().get(2));
		assertEquals("15", expression.getTokens().get(3));
		assertEquals(")", expression.getTokens().get(4));
	}
	
	@Test
	public void tokensOperatorsAreSeparatedOut()
	{
		for (char character : "d^*/\\%+-".toCharArray())
		{
			expression.setExpression("1" + character + "1");
			expression.evaluate();
			assertEquals(3, expression.getTokens().size());
			assertEquals("1", expression.getTokens().get(0));
			if (character == '-')
			{
				assertEquals("+", expression.getTokens().get(1));
				assertEquals("-1", expression.getTokens().get(2));
			}
			else
			{
				assertEquals(String.valueOf(character), expression.getTokens().get(1));
				assertEquals("1", expression.getTokens().get(2));
			}
		}
	}
}