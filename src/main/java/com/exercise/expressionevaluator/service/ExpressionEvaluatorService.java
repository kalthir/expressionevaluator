package com.exercise.expressionevaluator.service;

import com.exercise.expressionevaluator.service.data.ComplexExpression;
import com.exercise.expressionevaluator.service.data.ExpressionBase;
import com.exercise.expressionevaluator.service.data.LogicalOperatorType;
import com.exercise.expressionevaluator.service.data.SimpleExpression;
import com.exercise.expressionevaluator.service.evaluators.ExpressionEvaluatorBase;
import com.exercise.expressionevaluator.util.BusinessException;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpressionEvaluatorService implements IExpressionEvaluatorService {
    @Override
    public boolean evaluateExpression(JsonObject record, ExpressionBase expression) throws BusinessException {
        if(expression instanceof SimpleExpression) {
            return evaluateSimpleExpression(record, (SimpleExpression) expression);
        } else {
            return evaluateComplexExpression(record, (ComplexExpression) expression);
        }
    }

    private boolean evaluateComplexExpression(JsonObject record, ComplexExpression expression) throws BusinessException {
        boolean returnValue = false;
       if(expression.getExpressions().size() == 0) {
           return true;
       } else if(expression.getExpressions().size() == 1) {
           return evaluateExpression(record, expression.getExpressions().get(0));
       } else {
              boolean tempValue = false;
              List<Boolean> resultsAfterResolvingAnd = new ArrayList<>();
              boolean initializeTempValue = true;
              for(ExpressionBase exp : expression.getExpressions()) {
                boolean result = evaluateExpression(record, exp);

                if(initializeTempValue){
                    tempValue = result;
                    initializeTempValue = false;
                }

                if(expression.getFollowingLogicalOperator() == LogicalOperatorType.OR || expression.getFollowingLogicalOperator() == null) {
                     resultsAfterResolvingAnd.add(tempValue);
                     initializeTempValue = true;
                } else {
                     tempValue = tempValue && result;
                }
              }

              returnValue = resultsAfterResolvingAnd.stream().reduce(false, (a, b) -> a || b);
       }

       if(expression.isInverse()){
           returnValue = !returnValue;
       }

       return returnValue;
    }

    private boolean evaluateSimpleExpression(JsonObject record, SimpleExpression expression) throws BusinessException {
        ExpressionEvaluatorBase evaluator = ExpressionEvaluatorBase.getEvaluator(expression);
        return evaluator.evaluateExpression(record, expression);
    }
}
