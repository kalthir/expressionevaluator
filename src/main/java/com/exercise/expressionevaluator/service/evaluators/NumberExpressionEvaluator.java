package com.exercise.expressionevaluator.service.evaluators;

import com.exercise.expressionevaluator.service.data.SimpleExpression;
import com.exercise.expressionevaluator.util.BusinessException;
import com.google.gson.JsonObject;

public class NumberExpressionEvaluator extends ExpressionEvaluatorBase {
    @Override
    public boolean evaluateExpression(JsonObject record, SimpleExpression expression) throws BusinessException {
        Integer recordValue = getRecordValue(record, expression).getAsInt();
        Integer expressionValue = Integer.parseInt(expression.getValue());

        switch(expression.getOperator()) {
            case EQUALS:
                return recordValue.equals(expressionValue);
            case NOT_EQUALS:
                return !recordValue.equals(expressionValue);
            case GREATER_THAN:
                return recordValue > expressionValue;
            case LESS_THAN:
                return recordValue < expressionValue;
            case GREATER_THAN_OR_EQUALS:
                return recordValue >= expressionValue;
            case LESS_THAN_OR_EQUALS:
                return recordValue <= expressionValue;
            default:
                throw new BusinessException("Invalid operator");
        }
    }
}
