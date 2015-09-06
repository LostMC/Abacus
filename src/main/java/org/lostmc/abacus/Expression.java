package org.lostmc.abacus;

import utility.NumberUtilities;
import utility.StringUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private final static String OPERATORS_BEFORE_NEGATION = "d^*/\\%+-(";
    private final static String TOKENIZE_OPERATORS = "d^*/\\%+()";
    private final static String POSTFIX_OPERATORS = "d^*/\\%+";
    private final static String EVALUATE_OPERATORS = "d^*/\\%+";
    private final static int FULL_STACK = 64;
    private final static int SMALL_STACK = 16;

    private String originalText;
    private String preparedText;
    private StackType stackType;
    private List<String> tokens;
    private Stack<String> postfixStack;

    public Expression() {
        initialize();
    }

    public Expression(String expressionText) {
        initialize();
        originalText = expressionText;
    }

    public Expression(Expression expression) {
        initialize();
        originalText = expression.getOriginalText();
    }

    /**
     * Determines whether a given operator is higher priority than another
     *
     * @param left  char for one operator
     * @param right char for the other operator.
     * @return true if the left is higher than the right.
     */
    public static boolean isHigherPriority(char left, char right) {
        //	 OPERATORS		"d^*/\%+("
        switch (right) {
            case '(':
                return false;
            case 'd':
                return left == '(';
            case '^':
                return left == '(' || left == 'd';

            case '*':
            case '/':
            case '%':
            case '\\':
                return left == '(' || left == 'd' || left == '^';

            case '+':
                return !(left == '+');

            default:
                return false;
        }
    }

    private void initialize() {
        originalText = "";
        preparedText = "";
        stackType = StackType.NONE;
        tokens = new ArrayList<String>();
        postfixStack = new Stack<String>();
    }

    public String evaluate() {
        prepare();
        tokenize();
        postfix();
        Float value = evaluatePostfix();

        if (getStackType() == StackType.FULL || getStackType() == StackType.PARTIAL) {
            Integer stacks;
            Integer remainder;

            if (getStackType() == StackType.FULL) {
                stacks = value.intValue() / FULL_STACK;
                remainder = value.intValue() % FULL_STACK;
            } else {
                stacks = value.intValue() / SMALL_STACK;
                remainder = value.intValue() % SMALL_STACK;
            }

            return stacks.toString() + " stacks and " + remainder.toString() + " individual items.";
        } else {
            return value.toString().replace(".0", "");
        }
    }

    private void evaluateBinaryOperator(Stack<Float> operands, Stack<String> postfixStack) throws MathException {
        if (operands.size() < 2) {
            throw new MathException("Insufficient operands. Check your formula.");
        }

        Float operand2 = operands.pop();
        Float operand1 = operands.pop();
        int temp = 0;

        switch (postfixStack.pop().charAt(0)) {
            case 'd': /* Random Number */
                for (int i = 1; i <= operand1; i++) {
                    temp = temp + NumberUtilities.randomNumber(1, operand2.intValue());
                }
                operands.push(new Float(temp));
                break;
            case '^': /* Power Operator */
                operands.push(new Float(Math.pow(operand1, operand2)));
                break;
            case '*': /* Multiplication */
                operands.push(operand1 * operand2);
                break;
            case '/': /* Division */
                if (operand2 == 0) {
                    throw new MathException("Division by zero not supported.");
                } else {
                    operands.push(operand1 / operand2);
                }
                break;
            case '\\': /* Integer Division */
                if (operand2 == 0) {
                    throw new MathException("Integer division by zero not supported.");
                } else {
                    operands.push(new Float(operand1.intValue() / operand2.intValue()));
                }
                break;
            case '%': /* Modulus */
                if (operand2 == 0) {
                    throw new MathException("Modulus division by zero not supported.");
                } else {
                    operands.push(operand1 % operand2);
                }
                break;
            case '+': /* Addition */
                operands.push(operand1 + operand2);
                break;
        }
    }

    private void prepare() {
        preparedText = originalText;

        stackType = StackType.NONE;

        if (preparedText.startsWith("s")) {
            stackType = StackType.FULL;
            preparedText = preparedText.substring(1);
        } else if (preparedText.startsWith("p")) {
            stackType = StackType.PARTIAL;
            preparedText = preparedText.substring(1);
        }

        preparedText = preparedText.replaceAll("\\s", "");

        if (preparedText.isEmpty()) {
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

        if (preparedText.charAt(0) == '-') {
            preparedText = "~" + preparedText.substring(1);
        }

        pattern = Pattern.compile("([" + Pattern.quote(OPERATORS_BEFORE_NEGATION) + "])-");
        matcher = pattern.matcher(preparedText);
        preparedText = matcher.replaceAll("$1~");

        preparedText = preparedText.replaceAll("-", "+-");

        preparedText = preparedText.replaceAll("~", "-");

        preparedText = preparedText.replaceAll("-\\(", "-1*(");
    }

    private void tokenize() {
        tokens.clear();

        int currentPosition;
        int lastPosition = 0;

        while ((currentPosition = StringUtilities.find_first_of(preparedText, TOKENIZE_OPERATORS, lastPosition)) != -1) {
            // Add an operand...
            if (currentPosition != lastPosition) {
                tokens.add(preparedText.substring(lastPosition, currentPosition));
            }

            // Add the operator...
            tokens.add(preparedText.substring(currentPosition, currentPosition + 1));

            lastPosition = currentPosition + 1;
        }

        // Add final operand...
        if (lastPosition < preparedText.length()) {
            tokens.add(preparedText.substring(lastPosition, preparedText.length()));
        }
    }

    private void postfix() throws MathException {
        Stack<String> operatorStack = new Stack();

        for (String token : tokens) {
            if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                String operator = operatorStack.peek();
                while (!operatorStack.isEmpty() && !operator.equals("(")) {
                    postfixStack.add(0, operator);
                    operatorStack.pop();
                    operator = operatorStack.peek();
                }
                operatorStack.pop();
            } else if (StringUtilities.find_first_of(token, POSTFIX_OPERATORS, 0) != -1) {
                while (!operatorStack.isEmpty() && !"(".equals(operatorStack.peek()) && !isHigherPriority(token.charAt(0), operatorStack.peek().charAt(0))) {
                    postfixStack.add(0, operatorStack.pop());
                }
                operatorStack.push(token);
            } else {
                postfixStack.add(0, token);
            }
        }

        while (!operatorStack.isEmpty()) {
            String operator = operatorStack.pop();
            if ("(".equals(operator) || ")".equals(operator)) {
                throw new MathException("Mismatched grouping. Make sure your (), {}, [], <> match up.");
            }
            postfixStack.add(0, operator);
        }
    }

    public String getOriginalText() {
        return originalText;
    }

    public Stack<String> getPostfixStack() {
        return postfixStack;
    }

    public String getPreparedText() {
        return preparedText;
    }

    public StackType getStackType() {
        return stackType;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setExpression(String expression) {
        this.originalText = expression;
    }

    public void setExpression(Expression expression) {
        this.originalText = expression.getOriginalText();
    }

    private Float evaluatePostfix() throws NumberFormatException, MathException {
        if (postfixStack.isEmpty()) {
            return new Float(0);
        }

        Stack<Float> operands = new Stack<Float>();

        while (!postfixStack.isEmpty()) {
            String item = postfixStack.peek();

            if (StringUtilities.find_first_of(item, EVALUATE_OPERATORS, 0) == -1) {
                int index = item.lastIndexOf('s');
                if (index == -1) {
                    index = item.lastIndexOf('p');
                    if (index == -1) {
                        operands.push(Float.parseFloat(item));
                    } else {
                        operands.push(Float.parseFloat(item.substring(0, index)) * SMALL_STACK);
                    }
                } else {
                    operands.push(Float.parseFloat(item.substring(0, index)) * FULL_STACK);
                }
                postfixStack.pop();
            } else {
                evaluateBinaryOperator(operands, postfixStack);
            }
        }

        return operands.pop();
    }
}
