package com.exercise.expressionevaluator.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expressions")
@NoArgsConstructor
@Data
public class Expression
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "expressionValue")
    private String expressionValue;

    public Expression(String name, String value) {
        this.name = name;
        this.expressionValue = value;
    }
}
