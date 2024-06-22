package com.exercise.expressionevaluator.service;

import com.exercise.expressionevaluator.service.data.ComparisonOperatorType;
import com.exercise.expressionevaluator.service.data.DataType;
import com.exercise.expressionevaluator.service.data.ExpressionBase;
import com.exercise.expressionevaluator.service.data.SimpleExpression;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class ExpressionEvaluatorServiceTest {

    private String exampleObject = "{\"customer\":{\"firstName\":\"JOHN\",\"lastName\":\"DOE\",\"address\":{\"city\":\"Chicago\",\"zipCode\":1234,\"street\":\"56th\",\"houseNumber\":2345},\"salary\":99,\"type\":\"BUSINESS\"}}";;

    @Test
    void evaluateTrueStringEqualsExpression() {
        ExpressionEvaluatorService expressionEvaluatorService = new ExpressionEvaluatorService();
        JsonObject record = JsonParser.parseString(exampleObject).getAsJsonObject();
        ExpressionBase expression = new SimpleExpression("customer.firstName", ComparisonOperatorType.EQUALS, DataType.STRING, "JOHN");
        try {
            if(!expressionEvaluatorService.evaluateExpression(record, expression)) {
                fail("Expression should be true");
            }
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }

    @Test
    void evaluateFalseStringEqualsExpression() {
        ExpressionEvaluatorService expressionEvaluatorService = new ExpressionEvaluatorService();
        JsonObject record = JsonParser.parseString(exampleObject).getAsJsonObject();
        ExpressionBase expression = new SimpleExpression("customer.firstName", ComparisonOperatorType.EQUALS, DataType.STRING, "MARK");
        try {
            if(expressionEvaluatorService.evaluateExpression(record, expression)) {
                fail("Expression should be false");
            }
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }
}