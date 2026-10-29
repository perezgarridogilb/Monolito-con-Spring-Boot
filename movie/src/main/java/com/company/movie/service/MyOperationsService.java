package com.company.movie.service;

import org.springframework.stereotype.Service;

@Service
public class MyOperationsService {
    public int factorial(int num) {
        // n -> n * (n-1) * (n-2) .... * 1
        // n >= 0
        // factorial(4) = 24
        // factorial(0) = 1 (caso base)
        if (num < 0) {
            throw new ArithmeticException();
        }

        if (num > 23) {
            throw new IllegalArgumentException("Overflow from integer");
        }

        // 0! = 1 y 1! = 1
        if (num == 1 || num== 0) return 1;
        return Math.multiplyExact(num, factorial(num - 1));
    }
}
