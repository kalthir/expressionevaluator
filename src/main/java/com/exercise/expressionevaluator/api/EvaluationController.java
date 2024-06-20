package com.exercise.expressionevaluator.api;

import com.exercise.expressionevaluator.api.data.ExpressionCreateCommand;
import com.exercise.expressionevaluator.api.data.ExpressionEvaluationRequest;
import com.exercise.expressionevaluator.service.IExpressionService;
import com.exercise.expressionevaluator.util.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EvaluationController {

    private IExpressionService expressionService;

    @PostMapping("/expression")
    public Integer createExpression(@RequestBody ExpressionCreateCommand expression) throws BusinessException {
        return this.expressionService.createExpression(expression.getName(), expression.getExpression());
    }

    @GetMapping("/evaluate")
    public void evaluateExpression(@RequestBody ExpressionEvaluationRequest request) {
        this.expressionService.evaluateExpression(request.getExpressionId(), request.getData());
    }
}
