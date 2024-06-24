package com.exercise.expressionevaluator.service.data;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class SimpleExpression extends ExpressionBase {
    private String path;
    private ComparisonOperatorType operator;
    private DataType type;
    private String value;

    public SimpleExpression(String path, ComparisonOperatorType operator, DataType type, String value) {
        this.path = path;
        this.operator = operator;
        this.type = type;
        this.value = value;
    }

    public SimpleExpression(String path, ComparisonOperatorType operator, DataType type, String value, LogicalOperatorType followingLogicalOperator) {
        this(path, operator, type, value);
        this.followingLogicalOperator = followingLogicalOperator;
    }
}
