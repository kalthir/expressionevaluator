package com.exercise.expressionevaluator.service;

import com.exercise.expressionevaluator.util.BusinessException;

public interface IExpressionService {
    Integer createExpression(String name, String expression) throws BusinessException;
    void evaluateExpression(Integer expressionId, String data) throws BusinessException;
}
