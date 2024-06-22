package com.exercise.expressionevaluator.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringToExpressionServiceTest {

    @Test
    void stringToExpressionExampleTest() {
        StringToExpressionService stringToExpressionService = new StringToExpressionService();
        try {
            stringToExpressionService.stringToExpression("(customer.firstName == \"JOHN\" && customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")");
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }

    @Test
    void stringToExpressionBasicConditionStringTest() {
        StringToExpressionService stringToExpressionService = new StringToExpressionService();
        try {
            stringToExpressionService.stringToExpression("customer.firstName == \"JOHN\"");
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }

    @Test
    void stringToExpressionBasicConditionNumberTest() {
        StringToExpressionService stringToExpressionService = new StringToExpressionService();
        try {
            stringToExpressionService.stringToExpression("customer.salary < 100");
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }
}