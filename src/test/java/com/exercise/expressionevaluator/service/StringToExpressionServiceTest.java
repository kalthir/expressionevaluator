package com.exercise.expressionevaluator.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringToExpressionServiceTest {

    @Test
    void stringToExpression() {
        StringToExpressionService stringToExpressionService = new StringToExpressionService();
        try {
            stringToExpressionService.stringToExpression("(customer.firstName == \"JOHN\" && customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")");
        } catch (Exception e) {
            fail("Exception thrown");
        }
    }
}