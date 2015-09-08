package utility;

import org.junit.Test;
import org.lostmc.abacus.Expression;
import org.lostmc.abacus.MathException;
import org.lostmc.abacus.StackType;

import static org.junit.Assert.*;

public class ExpressionTest {
    private Expression expression = new Expression();

    @Test
    public void getOriginalNeverNull() {
        expression.evaluate();
        assertNotNull(expression.getOriginalText());
    }

    @Test
    public void textConstructor() {
        String text = "1+1";
        Expression local = new Expression(text);
        local.evaluate();
        assertEquals(text, local.getOriginalText());
    }

    @Test
    public void expressionConstructor() {
        String text = "1+1";
        Expression local = new Expression(text);
        Expression newLocal = new Expression(local);
        assertEquals(text, newLocal.getOriginalText());
    }

    @Test
    public void expressionSetter() {
        String text = "1+1";
        Expression local = new Expression(text);
        expression.setExpression(local);
        assertEquals(text, expression.getOriginalText());
    }

    @Test
    public void getPreparedNeverNull() {
        expression.evaluate();
        assertNotNull(expression.getPreparedText());
    }

    @Test
    public void emptyOriginalBegetsEmptyPrepared() {
        expression.setExpression("");
        expression.evaluate();
        assertEquals("", expression.getPreparedText());
    }

    @Test
    public void whiteSpaceOriginalBegetsEmptyPrepared() {
        String expressionText = "  ";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals("", expression.getPreparedText());
    }

    @Test
    public void preparedTextShouldNotHaveWhiteSpace() {
        String expressionText = "1    +     1";
        String cleanExpression = "1+1";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals(cleanExpression, expression.getPreparedText());
    }

    @Test
    public void preparedTextReplaceAngleBracketsWithParens() {
        String expressionText = "<>";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals("()", expression.getPreparedText());
    }

    @Test
    public void preparedTextReplaceCurlyBraceWithParens() {
        String expressionText = "{}";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals("()", expression.getPreparedText());
    }

    @Test
    public void preparedTextReplaceSquareBraceWithParens() {
        String expressionText = "[]";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals("()", expression.getPreparedText());
    }

    @Test
    public void preparedTextReplaceMultiplicationShortHandLeft() {
        String expressionText = "3(6)";
        String expected = "3*(6)";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals(expected, expression.getPreparedText());
    }

    @Test
    public void preparedTextReplaceMultiplicationShortHandRight() {
        String expressionText = "(6)3";
        String expected = "(6)*3";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals(expected, expression.getPreparedText());
    }

    @Test
    public void preparedTextReplaceMultiplicationShortHandBoth() {
        String expressionText = "2(6)3";
        String expected = "2*(6)*3";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals(expected, expression.getPreparedText());
    }

    @Test
    public void preparedTextLeadingNegativeShouldStayHyphen() {
        String expressionText = "-2";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals(expressionText, expression.getPreparedText());
    }

    @Test
    public void preparedTextSubtractPositiveShouldAddNegative() {
        String expressionText = "2-2";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals("2+-2", expression.getPreparedText());
    }

    @Test
    public void preparedTextSubtractNegativeShouldAddPositive() {
        String expressionText = "2--2";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals("2+2", expression.getPreparedText());
    }

    @Test
    public void preparedTextNegationOfParenExpressionShouldMultiply() {
        String expressionText = "2-(3-2)";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals("2+-1*(3+-2)", expression.getPreparedText());
    }

    @Test
    public void subExpressionAddNegativeNumberShouldStay() {
        String expressionText = "2+-1";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals("2+-1", expression.getPreparedText());

    }

    @Test
    public void preparedTextNoneStackGetsSet() {
        String expressionText = "1";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals(StackType.NONE, expression.getStackType());
    }

    @Test
    public void preparedTextFullStackGetsSet() {
        String expressionText = "s1";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals(StackType.FULL, expression.getStackType());
    }

    @Test
    public void preparedTextFullStackSymbolGetsTrimmed() {
        String expressionText = "s1";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals("1", expression.getPreparedText());
    }

    @Test
    public void preparedTextPartialStackGetsSet() {
        String expressionText = "p1";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals(StackType.PARTIAL, expression.getStackType());
    }

    @Test
    public void preparedTextPartialStackSymbolGetsTrimmed() {
        String expressionText = "p1";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals("1", expression.getPreparedText());
    }

    @Test
    public void tokensShouldNeverReturnNull() {
        assertNotNull(expression.getTokens());
    }

    @Test
    public void tokensOneToken() {
        String expressionText = "1";
        expression.setExpression(expressionText);
        expression.evaluate();
        assertEquals(1, expression.getTokens().size());
        assertEquals("1", expression.getTokens().get(0));
    }

    @Test
    public void tokensParentheses() {
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
    public void tokensOperatorsAreSeparatedOut() {
        for (char character : "d^*/\\%+-".toCharArray()) {
            expression.setExpression("1" + character + "1");
            expression.evaluate();
            assertEquals(3, expression.getTokens().size());
            assertEquals("1", expression.getTokens().get(0));
            if (character == '-') {
                assertEquals("+", expression.getTokens().get(1));
                assertEquals("-1", expression.getTokens().get(2));
            } else {
                assertEquals(String.valueOf(character), expression.getTokens().get(1));
                assertEquals("1", expression.getTokens().get(2));
            }
        }
    }

    @Test
    public void ihLeftParenIsNotHigherThanLeftParen() {
        assertFalse(Expression.isHigherPriority('(', '('));
    }

    @Test
    public void ihLeftParenIsHigherThanAllOthers() {
        String operators = "d^*/%+";

        for (char operator : operators.toCharArray()) {
            assertTrue("Left paren should be higher than: " + operator, Expression.isHigherPriority('(', operator));
            assertFalse(operator + "should not be higher than left paren.", Expression.isHigherPriority(operator, '('));
        }
    }

    @Test
    public void ihDIsNotHigherThanD() {
        assertFalse(Expression.isHigherPriority('d', 'd'));
    }

    @Test
    public void ihDIsHigherThanOtherOperators() {
        String operators = "^*/%+";

        for (char operator : operators.toCharArray()) {
            assertTrue(Expression.isHigherPriority('d', operator));
            assertFalse(Expression.isHigherPriority(operator, 'd'));
        }
    }

    @Test
    public void ihExponentIsNotHigherThanExponent() {
        assertFalse(Expression.isHigherPriority('^', '^'));
    }

    @Test
    public void ihExponentHigherThanMostOtherOperators() {
        String operators = "*/%+";

        for (char operator : operators.toCharArray()) {
            assertTrue(Expression.isHigherPriority('^', operator));
            assertFalse(Expression.isHigherPriority(operator, '^'));
        }
    }

    @Test
    public void ihMultiplyIsNotHigherThanMultiply() {
        assertFalse(Expression.isHigherPriority('*', '*'));
    }

    @Test
    public void ihMultiplyIsNotHigherThanModulus() {
        assertFalse(Expression.isHigherPriority('*', '%'));
    }

    @Test
    public void ihMultiplyIsNotHigherThanDivide() {
        assertFalse(Expression.isHigherPriority('*', '/'));
    }

    @Test
    public void ihMultiplyIsNotHigherThanIntegerDivide() {
        assertFalse(Expression.isHigherPriority('*', '\\'));
    }

    @Test
    public void ihMultiplyHigherThanRemainingOperators() {
        String operators = "+";

        for (char operator : operators.toCharArray()) {
            assertTrue(Expression.isHigherPriority('^', operator));
            assertFalse(Expression.isHigherPriority(operator, '^'));
        }
    }

    @Test
    public void ihAddIsNotHigherThanAdd() {
        assertFalse(Expression.isHigherPriority('+', '+'));
    }

    @Test
    public void getPostfixStackNeverNull() {
        expression.evaluate();
        assertNotNull(expression.getPostfixStack());
    }

    @Test
    public void getPostfixStackReturnsEmptyWithEmptyExpression() {
        expression.setExpression("");
        expression.evaluate();
        assertNotNull(expression.getPostfixStack());
        assertEquals(0, expression.getPostfixStack().size());
    }

    @Test
    public void evaluateThreeTokens() {
        String expressionText = "1+1";
        expression.setExpression(expressionText);
        assertEquals("2", expression.evaluate());
    }

    @Test
    public void evaluateMixedPrecedenceOperatorsInProperOrder() {
        String expressionText = "1+1*5";
        expression.setExpression(expressionText);
        assertEquals("6", expression.evaluate());
    }

    @Test
    public void evaluateParentheticalInProperOrder() {
        String expressionText = "(1+1)*5";
        expression.setExpression(expressionText);
        assertEquals("10", expression.evaluate());
    }

    @Test
    public void evaluateParentheticalWithMultipleOperandsInProperOrder() {
        String expressionText = "1+2*3+(4*5+6)*7";
        expression.setExpression(expressionText);
        assertEquals("189", expression.evaluate());
    }

    @Test
    public void getPostfixStackMismatchParenThrowsException() {
        String expressionText = "(1+1";
        expression.setExpression(expressionText);

        try {
            expression.evaluate();
            fail("Should have thrown exception.");
        } catch (MathException exception) {
            assertEquals("Mismatched grouping. Make sure your (), {}, [], <> match up.", exception.getMessage());
        }
    }

    @Test
    public void evaluateNeverReturnsNull() {
        expression.evaluate();
        assertNotNull(expression.evaluate());
    }

    @Test
    public void evaluateReturnsZeroOnEmptyString() {
        expression.setExpression("");
        expression.evaluate();
        assertEquals("0", expression.evaluate());
    }

    @Test
    public void evaluateReturnsDigitOnlyWithDigit() {
        String number = String.valueOf(NumberUtilities.randomNumber(1, 100));
        expression.setExpression(number);
        expression.evaluate();
        assertEquals(number, expression.evaluate());
    }

    @Test
    public void evaluateThrowsExceptionDivisionByZero() {
        String expressionText = "1/0";
        expression.setExpression(expressionText);

        try {
            expression.evaluate();
            fail("Should have thrown exception.");
        } catch (MathException exception) {
            assertEquals("Division by zero not supported.", exception.getMessage());
        }
    }

    @Test
    public void evaluateThrowsExceptionModulusByZero() {
        String expressionText = "1%0";
        expression.setExpression(expressionText);

        try {
            expression.evaluate();
            fail("Should have thrown exception.");
        } catch (MathException exception) {
            assertEquals("Modulus division by zero not supported.", exception.getMessage());
        }
    }

    @Test
    public void evaluateThrowsExceptionIntegerDivisionByZero() {
        String expressionText = "1\\0";
        expression.setExpression(expressionText);

        try {
            expression.evaluate();
            fail("Should have thrown exception.");
        } catch (MathException exception) {
            assertEquals("Integer division by zero not supported.", exception.getMessage());
        }
    }

    @Test
    public void evaluateHandlesPartialStack() {
        String expressionText = "1p";
        expression.setExpression(expressionText);
        assertEquals("16", expression.evaluate());
    }

    @Test
    public void evaluateHandlesStack() {
        String expressionText = "1s";
        expression.setExpression(expressionText);
        assertEquals("64", expression.evaluate());
    }

    @Test
    public void evaluateHandlesMissingOperand() {
        String expressionText = "1+";
        expression.setExpression(expressionText);
        try {
            expression.evaluate();
            fail("Should have thrown exception.");
        } catch (MathException exception) {
            assertEquals("Insufficient operands. Check your formula.", exception.getMessage());
        }
    }
}