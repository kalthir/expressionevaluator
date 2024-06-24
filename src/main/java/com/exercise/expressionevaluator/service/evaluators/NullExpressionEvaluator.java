package com.exercise.expressionevaluator.service.evaluators;

import com.exercise.expressionevaluator.service.data.SimpleExpression;
import com.exercise.expressionevaluator.util.BusinessException;
import com.google.gson.JsonObject;

public class NullExpressionEvaluator extends ExpressionEvaluatorBase {
    @Override
    public boolean evaluateExpression(JsonObject record, SimpleExpression expression) throws BusinessException {
        boolean isNull = getRecordValue(record, expression).isJsonNull();

        switch(expression.getOperator()) {
            case EQUALS:
                return isNull;
            case NOT_EQUALS:
                return !isNull;
            default:
                throw new BusinessException("Invalid operator");
        }
    }
}
