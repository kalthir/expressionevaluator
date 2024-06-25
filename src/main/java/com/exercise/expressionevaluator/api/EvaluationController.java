package com.exercise.expressionevaluator.api;

import com.exercise.expressionevaluator.api.data.ExpressionCreateCommand;
import com.exercise.expressionevaluator.api.data.ExpressionEvaluationRequest;
import com.exercise.expressionevaluator.service.IExpressionService;
import com.exercise.expressionevaluator.util.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EvaluationController {

    private final IExpressionService expressionService;

    @PostMapping("/expression")
    public ResponseEntity<Integer> createExpression(@RequestBody ExpressionCreateCommand expression) throws BusinessException {
        return new ResponseEntity(this.expressionService.createExpression(expression.getName(), expression.getExpression()), HttpStatus.OK);
    }

    @GetMapping("/evaluate")
    public ResponseEntity<Boolean> evaluateExpression(@RequestBody ExpressionEvaluationRequest request) throws BusinessException {
        return new ResponseEntity(this.expressionService.evaluateExpression(request.getExpressionId(), request.getData().toString()), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        if(e instanceof BusinessException) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
