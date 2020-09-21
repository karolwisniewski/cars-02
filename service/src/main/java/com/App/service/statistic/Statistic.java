package com.App.service.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor


public class Statistic {
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal avr;

    @Override
    public String toString() {
        return "MIN: " + min + " " +
                "MAX: " + max + " " +
                "AVR: " + avr;
    }
}
