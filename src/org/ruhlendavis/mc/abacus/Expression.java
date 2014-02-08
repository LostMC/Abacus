package org.ruhlendavis.mc.abacus;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ruhlendavis.utility.StringUtilities;

public class Expression
{
	private final static String PREPARE_OPERATORS = "d^*/\\%+-(";
	private final static String TOKENIZE_OPERATORS = "d^*/\\%+()";
	
	private String originalText = "";
	private String preparedText = "";
	private StackType stackType = StackType.NONE;
	private	List<String> tokens = new ArrayList<String>();

	public StackType getStackType()
	{
		return stackType;
	}

	public Expression()
	{
		originalText = "";
	}
	
	public Expression(String expressionText)
	{
		originalText = expressionText;
	}
	
	public Expression(Expression expression)
	{
		originalText = expression.getOriginalText();
	}
	
	public String evaluate()
	{
		prepare();
		tokenize();
		return "";
	}
	
	private void prepare()
	{
		preparedText = originalText;
		
		stackType = StackType.NONE;
		
		if (preparedText.startsWith("s"))
		{
			stackType = StackType.FULL;
			preparedText = preparedText.substring(1);
		}
		else if (preparedText.startsWith("p"))
		{
			stackType = StackType.PARTIAL;
			preparedText = preparedText.substring(1);
		}
		
		preparedText = preparedText.replaceAll("\\s", "");
		
		if (preparedText.isEmpty())
		{
			return;
		}
		
		preparedText = preparedText.replaceAll("<", "(").replaceAll(">", ")");
		preparedText = preparedText.replaceAll("\\{", "(").replaceAll("}", ")");
		preparedText = preparedText.replaceAll("\\[", "(").replaceAll("]", ")");
		
		// Replace 3(6+3) with 3*(6+3)
		Pattern pattern = Pattern.compile("(\\d+)\\(");
		Matcher matcher = pattern.matcher(preparedText);
		preparedText = matcher.replaceAll("$1*(");
		
		// Replace (6+3)3 with (6+3)*3
		pattern = Pattern.compile("\\)(\\d+)");
		matcher = pattern.matcher(preparedText);
		preparedText = matcher.replaceAll(")*$1");
		
		preparedText = preparedText.replaceAll("--", "+");
		
		if (preparedText.charAt(0) == '-')
		{
			preparedText = "~" + preparedText.substring(1);
		}
		
		pattern = Pattern.compile("([" + Pattern.quote(PREPARE_OPERATORS) +"])-");
		matcher = pattern.matcher(preparedText);
		preparedText = matcher.replaceAll("$1~");
		
		preparedText = preparedText.replaceAll("-", "+-");
		
		preparedText = preparedText.replaceAll("~", "-");
		
		preparedText = preparedText.replaceAll("-\\(", "-1*(");		
	}

	private void tokenize()
	{
		tokens.clear();
		
		int currentPosition = 0;
		int lastPosition = 0;
		
		while ((currentPosition = StringUtilities.find_first_of(preparedText, TOKENIZE_OPERATORS, lastPosition)) != -1)
		{
			// Add an operand...
			if (currentPosition != lastPosition)
			{
				tokens.add(preparedText.substring(lastPosition, currentPosition));
			}
			
			// Add the operator...
			tokens.add(preparedText.substring(currentPosition, currentPosition + 1));

			lastPosition = currentPosition + 1;
		}
		
		// Add final operand...
		if (lastPosition < preparedText.length())
		{
			tokens.add(preparedText.substring(lastPosition, preparedText.length()));
		}
	}

	/**
	 *  Determines whether a given operator is higher precedence than another
	 *
	 * @param left char for one operator
	 * @param right char for the other operator.
	 * @return true if the left is higher than the right.
	 */
	public static boolean isHigher(char left, char right)
	{
		//	 OPERATORS		"d^*/%+()"
		switch (right)
		{
			case 'd':
				return false;
			case '^':
				return left == 'd';

			case '*':
			case '/':
			case '%':
			case '\\':
				return left == 'd' || left == '^';

			case '+':
				return !(left == '+') && !(left == '(');

			case '(':
				return !(left == '(');
			default :
				return false;
		}
	}
	
	public String getOriginalText()
	{
		return originalText;
	}
	
	public String getPreparedText()
	{
		return preparedText;
	}
	
	public void setExpression(String expression)
	{
		this.originalText = expression;
	}
	
	public void setExpression(Expression expression)
	{
		this.originalText = expression.getOriginalText();
	}
	
	public List<String> getTokens()
	{
		return tokens;
	}
}
