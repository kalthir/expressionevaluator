package com.exercise.expressionevaluator.service;

import com.exercise.expressionevaluator.util.BusinessException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExpressionService implements IExpressionService {

private IStringToExpressionService stringToExpressionService;

    @Override
    public Integer createExpression(String name, String expression) throws BusinessException {
        validateName(name);
        validateExpression(expression);
        return saveExpression(name, expression);
    }

    private Integer saveExpression(String name, String expression) {
        return 0;
    }

    private void validateExpression(String expression) throws BusinessException {
        this.stringToExpressionService.stringToExpression(expression);
    }

    private void validateName(String name) throws BusinessException {
        if(name == null || name.isEmpty()) {
            throw new BusinessException("Name cannot be null or empty");
        }
    }

    @Override
    public void evaluateExpression(Integer expressionId, String data) {

    }
}
