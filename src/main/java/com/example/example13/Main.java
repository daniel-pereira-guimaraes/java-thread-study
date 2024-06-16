package com.example.example13;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        var calculation = new ComplexCalculation();
        var expression1 = ComplexCalculation.powerExpression(2, 300);
        var expression2 = ComplexCalculation.powerExpression(3, 200);
        var result = calculation.calculateResult(expression1, expression2, 1000);

        System.out.println(expression1 + " + " + expression2 + " = " + result);
    }
}
