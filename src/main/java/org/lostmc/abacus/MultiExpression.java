package org.lostmc.abacus;

class MultiExpression {
    private String originalExpression;
    private String result;

    MultiExpression(String[] expressionArray) {
        originalExpression = "";

        for (String expressionPart : expressionArray) {
            originalExpression = originalExpression + expressionPart;
        }
    }

    public String getResult() {
        if (result == null) {
            evaluate();
        }
        return result;
    }

    private void evaluate() {
        if (originalExpression != null) {
            result = "";
            for (String subExpression : originalExpression.split(",")) {
                Expression expression = new Expression(subExpression);
                expression.evaluate();
                result = result + expression.evaluate() + ", ";
            }

            // Remove the extra comma & space.
            result = result.substring(0, result.length() - 2);
        }
    }
}
