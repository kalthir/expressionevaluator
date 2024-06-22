package com.exercise.expressionevaluator.service.evaluators;

import com.exercise.expressionevaluator.service.data.SimpleExpression;
import com.exercise.expressionevaluator.util.BusinessException;
import com.google.gson.JsonObject;

public class StringExpressionEvaluator extends ExpressionEvaluatorBase {
    @Override
    public boolean evaluateExpression(JsonObject record, SimpleExpression expression) throws BusinessException {
        String recordValue = getRecordValue(record, expression).getAsString();

        switch(expression.getOperator()) {
            case EQUALS:
                return recordValue.equals(expression.getValue());
            case NOT_EQUALS:
                return !recordValue.equals(expression.getValue());
            default:
                throw new BusinessException("Invalid operator");
        }
    }
}
