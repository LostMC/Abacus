//package utility;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.powermock.api.mockito.PowerMockito.verifyNew;
//import static org.powermock.api.mockito.PowerMockito.whenNew;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(MultiExpression.class)
//public class MultiExpressionTest {
//    private static final String[] EMPTY_ARRAY = new String[0];
//
//    @Test
//    public void getOriginalExpressionNeverReturnsNull() {
//        MultiExpression multiExpression = new MultiExpression(EMPTY_ARRAY);
//        assertNotNull(multiExpression.getOriginalExpression());
//    }
//
//    @Test
//    public void getOriginalExpressionReturnsHandlesBlankStrings() {
//        String[] strings = {""};
//        MultiExpression multiExpression = new MultiExpression(strings);
//        assertEquals("", multiExpression.getOriginalExpression());
//    }
//
//    @Test
//    public void getOriginalExpressionReturnsWhatIsSet() {
//        String[] strings = {"1+1"};
//        MultiExpression multiExpression = new MultiExpression(strings);
//        assertEquals("1+1", multiExpression.getOriginalExpression());
//    }
//
//    @Test
//    public void getOriginalExpressionReturnsWhatIsSetInArray() {
//        String[] strings = {"1", "+", "1"};
//        MultiExpression multiExpression = new MultiExpression(strings);
//        assertEquals("1+1", multiExpression.getOriginalExpression());
//    }
//
//    @Test
//    public void getResultNeverReturnsNull() {
//        MultiExpression multiExpression = new MultiExpression(EMPTY_ARRAY);
//        assertNotNull(multiExpression.getResult());
//    }
//
//    @Test
//    public void getResultCallsExpressionConstructorCorrectly() throws Exception {
//        String string = "1+1";
//        Expression expression = new Expression(string);
//        whenNew(Expression.class).withArguments(string).thenReturn(expression);
//        String[] strings = {string};
//        MultiExpression multiExpression = new MultiExpression(strings);
//        multiExpression.getResult();
//        verifyNew(Expression.class).withArguments(string);
//    }
//
//    @Test
//    public void getResultCallsExpressionConstructorCorrectlyWithMultipartExpression() throws Exception {
//        String string = "1+1";
//        Expression expression = new Expression(string);
//        whenNew(Expression.class).withArguments(string).thenReturn(expression);
//        String[] strings = {"1", "+", "1"};
//        MultiExpression multiExpression = new MultiExpression(strings);
//        multiExpression.getResult();
//        verifyNew(Expression.class).withArguments(string);
//    }
//
//    @Test
//    public void getResultCallsExpressionConstructorMultipleTimesWithMultipleExpressions() throws Exception {
//        String subString1 = "1+1";
//        String subString2 = "1+2";
//        String subString3 = "1+3";
//        Expression expression1 = mock(Expression.class);
//        Expression expression2 = mock(Expression.class);
//        Expression expression3 = mock(Expression.class);
//
//        whenNew(Expression.class).withArguments(subString1).thenReturn(expression1);
//        whenNew(Expression.class).withArguments(subString2).thenReturn(expression2);
//        whenNew(Expression.class).withArguments(subString3).thenReturn(expression3);
//
//        when(expression1.evaluate()).thenReturn("0");
//        when(expression2.evaluate()).thenReturn("0");
//        when(expression3.evaluate()).thenReturn("0");
//
//        String[] strings = {subString1 + "," + subString2 + "," + subString3};
//        MultiExpression multiExpression = new MultiExpression(strings);
//        multiExpression.getResult();
//        verifyNew(Expression.class).withArguments(subString1);
//        verifyNew(Expression.class).withArguments(subString2);
//        verifyNew(Expression.class).withArguments(subString3);
//    }
//
//    @Test
//    public void getResultCallsReturnsCommaSeparatedResultsWithMultipleExpressions() throws Exception {
//        String subString1 = "1+1";
//        String subString2 = "1+2";
//        String subString3 = "1+3";
//        Expression expression1 = mock(Expression.class);
//        Expression expression2 = mock(Expression.class);
//        Expression expression3 = mock(Expression.class);
//
//        whenNew(Expression.class).withArguments(subString1).thenReturn(expression1);
//        whenNew(Expression.class).withArguments(subString2).thenReturn(expression2);
//        whenNew(Expression.class).withArguments(subString3).thenReturn(expression3);
//
//        when(expression1.evaluate()).thenReturn("0");
//        when(expression2.evaluate()).thenReturn("1");
//        when(expression3.evaluate()).thenReturn("2");
//
//        String[] strings = {subString1 + "," + subString2 + "," + subString3};
//        MultiExpression multiExpression = new MultiExpression(strings);
//        assertEquals("0, 1, 2", multiExpression.getResult());
//    }
//
//    public void getResultCallsWithMultipleExpressionsDoesNotHaveTrailingSpaceOrComma() throws Exception {
//        String subString1 = "1+1";
//        String subString2 = "1+2";
//        String subString3 = "1+3";
//        Expression expression1 = mock(Expression.class);
//        Expression expression2 = mock(Expression.class);
//        Expression expression3 = mock(Expression.class);
//
//        whenNew(Expression.class).withArguments(subString1).thenReturn(expression1);
//        whenNew(Expression.class).withArguments(subString2).thenReturn(expression2);
//        whenNew(Expression.class).withArguments(subString3).thenReturn(expression3);
//
//        when(expression1.evaluate()).thenReturn("0");
//        when(expression2.evaluate()).thenReturn("1");
//        when(expression3.evaluate()).thenReturn("2");
//
//        String[] strings = {subString1 + "," + subString2 + "," + subString3};
//        MultiExpression multiExpression = new MultiExpression(strings);
//        assertFalse(multiExpression.getResult().endsWith(" "));
//        assertFalse(multiExpression.getResult().endsWith(","));
//        assertFalse(multiExpression.getResult().endsWith(", "));
//    }
//}