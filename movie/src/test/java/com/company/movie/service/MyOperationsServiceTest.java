package com.company.movie.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyOperationsServiceTest {

    private MyOperationsService operaciones;

    @BeforeEach
    public void setup() {
        operaciones = new MyOperationsService();
    }

    @Test
    public void factorialTest() {
        Assertions.assertEquals(1, operaciones.factorial(1));
        Assertions.assertEquals(120, operaciones.factorial(5));
        Assertions.assertEquals(24, operaciones.factorial(4));
        Assertions.assertEquals(1, operaciones.factorial(0));
    }

    @Test
    public void factorialFailTest() {
        Assertions.assertThrows(ArithmeticException.class, () -> operaciones.factorial(-1));
        Assertions.assertThrows(ArithmeticException.class, () -> operaciones.factorial(-10));
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            operaciones.factorial(24);
        });
    }
}
