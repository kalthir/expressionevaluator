package com.exercise.expressionevaluator.service;

import com.exercise.expressionevaluator.data.IExpressionRepository;
import com.exercise.expressionevaluator.data.model.Expression;
import com.exercise.expressionevaluator.util.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExpressionService implements IExpressionService {

private final IStringToExpressionService stringToExpressionService;
private final IExpressionRepository expressionRepository;

    @Override
    public Integer createExpression(String name, String expression) throws BusinessException {
        validateName(name);
        validateExpression(expression);
        return saveExpression(name, expression);
    }

    private Integer saveExpression(String name, String value) {
        Expression expression = expressionRepository.save(new Expression(name, value));
        return expression.getId();
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
