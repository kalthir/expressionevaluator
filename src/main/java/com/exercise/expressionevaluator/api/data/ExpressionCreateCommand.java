package com.exercise.expressionevaluator.api.data;

import lombok.Data;

@Data
public class ExpressionCreateCommand {
    private String name;
    private String expression;
}
