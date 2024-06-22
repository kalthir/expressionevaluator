package com.exercise.expressionevaluator.data;

import com.exercise.expressionevaluator.data.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExpressionRepository extends JpaRepository<Expression, Integer> {
}
