package com.exercise.expressionevaluator.service.evaluators;

import com.exercise.expressionevaluator.service.data.SimpleExpression;
import com.exercise.expressionevaluator.util.BusinessException;
import com.google.gson.JsonObject;

public class BooleanExpressionEvaluator extends ExpressionEvaluatorBase {
    @Override
    public boolean evaluateExpression(JsonObject record, SimpleExpression expression) throws BusinessException {
        Boolean recordValue = getRecordValue(record, expression).getAsBoolean();
        Boolean expressionValue = Boolean.parseBoolean(expression.getValue());

        switch(expression.getOperator()) {
            case EQUALS:
                return recordValue.equals(expression);
            case NOT_EQUALS:
                return !recordValue.equals(expression);
            default:
                throw new BusinessException("Invalid operator");
        }
    }
}
