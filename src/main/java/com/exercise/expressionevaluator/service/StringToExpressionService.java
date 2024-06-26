package com.exercise.expressionevaluator.service;

import com.exercise.expressionevaluator.service.data.*;
import com.exercise.expressionevaluator.util.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StringToExpressionService implements IStringToExpressionService {

    private static final String SIMPLE_EXPRESSION_REGEX = "^(?<path>\\w+(?:\\.\\w+)*)\\s*(?<comparisonOperator>==|!=|>|<|<=|>=){1}\\s*(?<value>(?:-?\\d+(\\.\\d+)?)|(?:\\\"[^\\\"]*\\\")|true|false|null){1}\\s*(?<logicalOperator>AND|OR|&&|\\|\\|)?";
    private static final String LOGICAL_OPERATOR_REGEX = "^\\s*(?:AND|OR|&&|\\|\\|)\\s*";

    public ExpressionBase stringToExpression(String expression) throws BusinessException {

        List<ExpressionBase> expressionCollection = new ArrayList<>();
        Pattern simpleExpression = Pattern.compile(SIMPLE_EXPRESSION_REGEX, Pattern.CASE_INSENSITIVE);
        Pattern logicalOperator = Pattern.compile(LOGICAL_OPERATOR_REGEX, Pattern.CASE_INSENSITIVE);

        for(int i = 0; i < expression.length();) {
            String subExpression = expression.substring(i);
            Matcher matcher = simpleExpression.matcher(subExpression);
            if(subExpression.startsWith(" ")) {
                i++;
                continue;
            } else if(matcher.find()) {
                expressionCollection.add(createSimpleExpression(matcher));
                i = i + matcher.group().length();
            } else if(subExpression.startsWith("(") || subExpression.startsWith("!(")) {
                int closingParenthesisIndex = findClosingParenthesisIndex(expression.substring(i));
                ExpressionBase parenthesisExpression = stringToExpression(subExpression.substring(subExpression.indexOf("(") + 1, closingParenthesisIndex));
                if(subExpression.startsWith(("!"))) {
                    parenthesisExpression.setInverse(!parenthesisExpression.isInverse());
                }

                i = i + closingParenthesisIndex + 1;
                matcher = logicalOperator.matcher(expression.substring(i));
                if(matcher.find()) {
                    LogicalOperatorType logicalOperatorType = stringToLogicalOperatorType(matcher.group());
                    parenthesisExpression.setFollowingLogicalOperator(logicalOperatorType);
                    i = i + matcher.group().length();
                }

                expressionCollection.add(parenthesisExpression);
            } else {
                throw new BusinessException("Invalid expression: " + expression + "Could not parse " + subExpression);
            }
        }

        if(expressionCollection.size() == 1) {
            return expressionCollection.get(0);
        } else {
            ComplexExpression complexExpression = new ComplexExpression();
            complexExpression.setExpressions(expressionCollection);
            return complexExpression;
        }
    }

    private LogicalOperatorType stringToLogicalOperatorType(String value) throws BusinessException {
        value = value.trim();
        switch(value) {
            case "AND":
            case "&&":
                return LogicalOperatorType.AND;
            case "OR":
            case "||":
                return LogicalOperatorType.OR;
            default:
                throw new BusinessException("Invalid logical operator: " + value);
        }
    }

    private ExpressionBase createSimpleExpression(Matcher matcher) throws BusinessException {
        String path = matcher.group("path");
        ComparisonOperatorType operator = stringToComparisonOperatorType(matcher.group("comparisonOperator"));
        DataType dataType = stringToDataType(matcher.group("value"));
        String value = matcher.group("value");
        String escapedQuotes = "\"";
        value = StringUtils.stripStart(value, escapedQuotes);
        value = StringUtils.stripEnd(value, escapedQuotes);


        SimpleExpression expression = new SimpleExpression(path, operator, dataType, value);
        if(matcher.group("logicalOperator") != null && !matcher.group("logicalOperator").isEmpty()) {
            LogicalOperatorType logicalOperator = stringToLogicalOperatorType(matcher.group("logicalOperator"));
            expression.setFollowingLogicalOperator(logicalOperator);
        }

        return expression;
    }

    private DataType stringToDataType(String value) throws BusinessException {
        value = value.trim();
         if(value.matches("-?\\d+(\\.\\d+)?")) {
            return DataType.NUMBER;
        } else if(value.matches("\\\"[^\\\"]*\\\"")) {
            return DataType.STRING;
        } else if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return DataType.BOOLEAN;
        } else if(value.equalsIgnoreCase("null")) {
            return DataType.NULL;
        } else {
            throw new BusinessException("Invalid data type: " + value);
        }
    }

    private ComparisonOperatorType stringToComparisonOperatorType(String value) throws BusinessException {
        value = value.trim();
        switch(value) {
            case "==":
                return ComparisonOperatorType.EQUALS;
            case "!=":
                return ComparisonOperatorType.NOT_EQUALS;
            case ">":
                return ComparisonOperatorType.GREATER_THAN;
            case "<":
                return ComparisonOperatorType.LESS_THAN;
            case ">=":
                return ComparisonOperatorType.GREATER_THAN_OR_EQUALS;
            case "<=":
                return ComparisonOperatorType.LESS_THAN_OR_EQUALS;
            default:
                throw new BusinessException("Invalid comparison operator: " + value);
        }
    }

    private int findClosingParenthesisIndex(String expression) throws BusinessException {
        int openParenthesisCount = 0;
        for(int i = 0; i < expression.length(); i++) {
            if(expression.charAt(i) == '(') {
                openParenthesisCount++;
            } else if(expression.charAt(i) == ')') {
                openParenthesisCount--;
                if(openParenthesisCount == 0) {
                    return i;
                } else if (openParenthesisCount < 0) {
                    throw new BusinessException("Unbalanced parenthesis in expression: " + expression);
                }
            }
        }

        throw new BusinessException("Unbalanced parenthesis in expression: " + expression);
    }
}
