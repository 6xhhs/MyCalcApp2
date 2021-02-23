// CalcService.aidl
package com.simo.mycalcapp;

// Declare any non-default types here with import statements

interface CalcService {
    /**
     * Calcuator Interface to  add,subtract,multiply,divide
     */

    int add(in int value1, in int value2);
    int subtract(in int value1, in int value2);
    int multiply(in int value1, in int value2);
    int divide(in int value1, in int value2);
}