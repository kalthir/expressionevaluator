package com.exercise.expressionevaluator.service;

import com.exercise.expressionevaluator.service.data.ExpressionBase;
import com.exercise.expressionevaluator.util.BusinessException;
import com.google.gson.JsonObject;

public interface IExpressionEvaluatorService {
    boolean evaluateExpression(JsonObject record, ExpressionBase expression) throws BusinessException;
}
