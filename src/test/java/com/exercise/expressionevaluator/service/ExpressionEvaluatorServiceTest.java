package com.exercise.expressionevaluator.service;

import com.exercise.expressionevaluator.service.data.ComparisonOperatorType;
import com.exercise.expressionevaluator.service.data.DataType;
import com.exercise.expressionevaluator.service.data.ExpressionBase;
import com.exercise.expressionevaluator.service.data.SimpleExpression;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class ExpressionEvaluatorServiceTest {

    public static Stream<Arguments> provideParamsforEvaluateTrueSimpleExpression() {
        String recordString = "{\"customer\":{\"firstName\":\"JOHN\",\"lastName\":\"DOE\",\"address\":{\"city\":\"Chicago\",\"zipCode\":1234,\"street\":\"56th\",\"houseNumber\":2345},\"salary\":435.5,\"type\":null, \"vip\":true}}";;
        JsonObject record = JsonParser.parseString(recordString).getAsJsonObject();
        return Stream.of(
                Arguments.of(record, new SimpleExpression("customer.firstName", ComparisonOperatorType.EQUALS, DataType.STRING, "JOHN")),
                Arguments.of(record, new SimpleExpression("customer.firstName", ComparisonOperatorType.NOT_EQUALS, DataType.STRING, "MARK")),
                Arguments.of(record, new SimpleExpression("customer.address.zipCode", ComparisonOperatorType.EQUALS, DataType.NUMBER, "1234")),
                Arguments.of(record, new SimpleExpression("customer.address.zipCode", ComparisonOperatorType.NOT_EQUALS, DataType.NUMBER, "12345")),
                Arguments.of(record, new SimpleExpression("customer.address.zipCode", ComparisonOperatorType.GREATER_THAN, DataType.NUMBER, "1233")),
                Arguments.of(record, new SimpleExpression("customer.address.zipCode", ComparisonOperatorType.GREATER_THAN_OR_EQUALS, DataType.NUMBER, "1234")),
                Arguments.of(record, new SimpleExpression("customer.address.zipCode", ComparisonOperatorType.GREATER_THAN_OR_EQUALS, DataType.NUMBER, "5")),
                Arguments.of(record, new SimpleExpression("customer.address.zipCode", ComparisonOperatorType.LESS_THAN, DataType.NUMBER, "5000")),
                Arguments.of(record, new SimpleExpression("customer.address.zipCode", ComparisonOperatorType.LESS_THAN_OR_EQUALS, DataType.NUMBER, "1234")),
                Arguments.of(record, new SimpleExpression("customer.address.zipCode", ComparisonOperatorType.LESS_THAN_OR_EQUALS, DataType.NUMBER, "5000")),
                Arguments.of(record, new SimpleExpression("customer.salary", ComparisonOperatorType.EQUALS, DataType.NUMBER, "435.5")),
                Arguments.of(record, new SimpleExpression("customer.salary", ComparisonOperatorType.NOT_EQUALS, DataType.NUMBER, "135.5")),
                Arguments.of(record, new SimpleExpression("customer.salary", ComparisonOperatorType.GREATER_THAN, DataType.NUMBER, "435.4")),
                Arguments.of(record, new SimpleExpression("customer.salary", ComparisonOperatorType.GREATER_THAN_OR_EQUALS, DataType.NUMBER, "435.5")),
                Arguments.of(record, new SimpleExpression("customer.salary", ComparisonOperatorType.GREATER_THAN_OR_EQUALS, DataType.NUMBER, "435.3")),
                Arguments.of(record, new SimpleExpression("customer.salary", ComparisonOperatorType.LESS_THAN, DataType.NUMBER, "435.6")),
                Arguments.of(record, new SimpleExpression("customer.salary", ComparisonOperatorType.LESS_THAN_OR_EQUALS, DataType.NUMBER, "435.5")),
                Arguments.of(record, new SimpleExpression("customer.salary", ComparisonOperatorType.LESS_THAN_OR_EQUALS, DataType.NUMBER, "844")),
                Arguments.of(record, new SimpleExpression("customer.vip", ComparisonOperatorType.EQUALS, DataType.BOOLEAN, "true")),
                Arguments.of(record, new SimpleExpression("customer.vip", ComparisonOperatorType.NOT_EQUALS, DataType.BOOLEAN, "false"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsforEvaluateTrueSimpleExpression")
    void evaluateTrueSimpleExpression(JsonObject record, SimpleExpression expression) {
        ExpressionEvaluatorService expressionEvaluatorService = new ExpressionEvaluatorService();
        try {
            if(!expressionEvaluatorService.evaluateExpression(record, expression)) {
                fail("Expression should be true");
            }
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }


}