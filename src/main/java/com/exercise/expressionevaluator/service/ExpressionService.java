package com.exercise.expressionevaluator.service;

import com.exercise.expressionevaluator.data.IExpressionRepository;
import com.exercise.expressionevaluator.data.model.Expression;
import com.exercise.expressionevaluator.service.data.ExpressionBase;
import com.exercise.expressionevaluator.util.BusinessException;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExpressionService implements IExpressionService {

private final IStringToExpressionService stringToExpressionService;
private final IExpressionEvaluatorService expressionEvaluatorService;
private final IExpressionRepository expressionRepository;

    @Override
    public Integer createExpression(String name, String expression) throws BusinessException {
        validateName(name);
        validateExpression(expression);
        return saveExpression(name, expression);
    }

    @Override
    public void evaluateExpression(Integer expressionId, String data) throws BusinessException {
        Gson gson = new Gson();
        Expression expression = expressionRepository.findById(expressionId).orElseThrow(() -> new BusinessException("Expression not found"));
        ExpressionBase expressionBase = stringToExpressionService.stringToExpression(expression.getExpressionValue());
        JsonParser parser = new JsonParser();
        JsonElement elem   = parser.parse(data);
        JsonArray dataArray = elem.getAsJsonArray();
        JsonArray resultArray = new JsonArray();
        for(JsonElement record : dataArray) {
            JsonObject recordObject = record.getAsJsonObject();
            if(expressionEvaluatorService.evaluateExpression(recordObject, expressionBase)) {
                resultArray.add(recordObject);
            }
        }
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
}
