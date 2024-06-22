package com.exercise.expressionevaluator.service.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ComplexExpression  extends ExpressionBase {
    private List<ExpressionBase> expressions;

    public ComplexExpression() {
        this.expressions = new ArrayList<>();
    }
}
