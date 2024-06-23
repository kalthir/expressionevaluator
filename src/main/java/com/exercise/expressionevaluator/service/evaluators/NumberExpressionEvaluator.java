package com.exercise.expressionevaluator.service.evaluators;

import com.exercise.expressionevaluator.service.data.SimpleExpression;
import com.exercise.expressionevaluator.util.BusinessException;
import com.google.gson.JsonObject;

import java.math.BigDecimal;

public class NumberExpressionEvaluator extends ExpressionEvaluatorBase {
    @Override
    public boolean evaluateExpression(JsonObject record, SimpleExpression expression) throws BusinessException {
        BigDecimal recordValue = getRecordValue(record, expression).getAsBigDecimal();
        BigDecimal expressionValue = new BigDecimal(expression.getValue());

        switch(expression.getOperator()) {
            case EQUALS:
                return recordValue.equals(expressionValue);
            case NOT_EQUALS:
                return !recordValue.equals(expressionValue);
            case GREATER_THAN:
                return recordValue.compareTo(expressionValue) > 0;
            case LESS_THAN:
                return recordValue.compareTo(expressionValue) < 0;
            case GREATER_THAN_OR_EQUALS:
                return recordValue.compareTo(expressionValue) >= 0;
            case LESS_THAN_OR_EQUALS:
                return recordValue.compareTo(expressionValue) <= 0;
            default:
                throw new BusinessException("Invalid operator");
        }
    }
}
