package com.exercise.expressionevaluator.api.data;

import lombok.Data;

@Data
public class ExpressionEvaluationRequest {
    private Integer expressionId;
    private String data;
}
