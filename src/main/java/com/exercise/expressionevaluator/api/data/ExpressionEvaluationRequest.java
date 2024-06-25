package com.exercise.expressionevaluator.api.data;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class ExpressionEvaluationRequest {
    private Integer expressionId;
    private JsonNode data;
}
