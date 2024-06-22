package com.exercise.expressionevaluator.service.evaluators;

import com.exercise.expressionevaluator.service.data.DataType;
import com.exercise.expressionevaluator.service.data.SimpleExpression;
import com.exercise.expressionevaluator.util.BusinessException;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public abstract class ExpressionEvaluatorBase {
    public abstract boolean evaluateExpression(JsonObject record, SimpleExpression expression) throws BusinessException;

    public static ExpressionEvaluatorBase getEvaluator(SimpleExpression expression) throws BusinessException {
        switch (expression.getType()) {
            case DataType.STRING:
                return new StringExpressionEvaluator();
            case DataType.NUMBER:
                return new NumberExpressionEvaluator();
            case DataType.BOOLEAN:
                return new BooleanExpressionEvaluator();
            default:
                throw new BusinessException("Invalid data type");
        }
    }

    protected JsonPrimitive getRecordValue(JsonObject record, SimpleExpression expression) {
        String[] path = expression.getPath().split("\\.");
        JsonObject currentRecord = record;
        for (int i = 0; i < path.length - 1; i++) {
            currentRecord = currentRecord.getAsJsonObject(path[i]);
        }
        return currentRecord.getAsJsonPrimitive(path[path.length - 1]);
    }

}
