package com.app.converter.model;

import com.app.converter.model.enums.CarBodyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Car {
    private String model;
    private BigDecimal price;
    private int mileage;
    private Engine engine;
    private CarBody carBody;
    private Wheel wheel;

    public boolean doesItMatch (CarBodyType type, BigDecimal min, BigDecimal max){
        boolean match = true;
        if(type != carBody.getBodyType()){
            match = false;
        }
        if(price.compareTo(min)<0 || price.compareTo(max)>0){
            match = false;
        }
        return match;
    }
}
