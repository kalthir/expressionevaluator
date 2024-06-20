package com.exercise.expressionevaluator.service.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class ExpressionBase {

    protected LogicalOperatorType followingLogicalOperator;
    protected boolean isInverse;
    public abstract boolean evaluate();
}
