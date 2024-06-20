package com.exercise.expressionevaluator.service;

import com.exercise.expressionevaluator.service.data.ExpressionBase;
import com.exercise.expressionevaluator.util.BusinessException;

public interface IStringToExpressionService {
    public ExpressionBase stringToExpression(String expression) throws BusinessException;
}
