package com.exercise.expressionevaluator.integration;

import com.exercise.expressionevaluator.service.ExpressionEvaluatorService;
import com.exercise.expressionevaluator.service.StringToExpressionService;
import com.exercise.expressionevaluator.service.data.ComparisonOperatorType;
import com.exercise.expressionevaluator.service.data.DataType;
import com.exercise.expressionevaluator.service.data.ExpressionBase;
import com.exercise.expressionevaluator.service.data.SimpleExpression;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

public class ExpressionCreationAndEvaluationTest {
    private static String exampleRecordString = "{\"customer\":{\"firstName\":\"JOHN\",\"lastName\":\"DOE\",\"address\":{\"city\":\"Chicago\",\"zipCode\":1234,\"street\":\"56th\",\"houseNumber\":2345},\"salary\":435.5,\"type\":null, \"vip\":true}}";

    @ParameterizedTest
    @MethodSource("provideParamsForCreateAndEvaluateExpression")
    void createAndEvaluateExpression(String expressionString, String recordString, boolean expectedResult) {

        try {
            StringToExpressionService stringToExpressionService = new StringToExpressionService();
            ExpressionEvaluatorService expressionEvaluatorService = new ExpressionEvaluatorService();
            ExpressionBase expression = stringToExpressionService.stringToExpression(expressionString);
            JsonParser parser = new JsonParser();
            JsonElement elem   = parser.parse(recordString);
            JsonObject record = elem.getAsJsonObject();
            Assertions.assertEquals(expressionEvaluatorService.evaluateExpression(record, expression), expectedResult);
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }

    public static Stream<Arguments> provideParamsForCreateAndEvaluateExpression() {
        return Stream.of(
                Arguments.of("customer.firstName == \"JOHN\"", exampleRecordString, true)
        );
    }
}
