package com.App.service.validator;

import com.app.converter.model.Car;

import java.math.BigDecimal;

public class CarValidator {
    public static boolean validation(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car object is null!!");
        }
        boolean isValid = true;

        if (!isCarModelValid(car)) {
            System.out.println("Car model is not valid");
            isValid = false;
        }
        if (!isWheelModelValid(car)) {
            System.out.println("Wheel model is not valid");
            isValid = false;
        }
        if (!isCarEngineValid(car)) {
            System.out.println("Engine power is not correct");
            isValid = false;
        }
        if (!isWheelSizeValid(car)) {
            System.out.println("Wheel size is not correct");
            isValid = false;
        }
        if (!isCarPriceValid(car)) {
            System.out.println("Car price is not valid");
            isValid = false;
        }
        if (!isCarMileageValid(car)) {
            System.out.println("Car mileage is not valid");
            isValid = false;
        }
        if (!isCarComponentsListValid(car)) {
            System.out.println("Car components list is not valid");
            isValid = false;
        }
        return isValid;
    }

    private static boolean isCarModelValid(Car car) {
        return car.getModel() != null && car.getModel().matches("([A-Z]+\\s?)+");
    }

    private static boolean isWheelModelValid(Car car) {
        return car.getWheel().getModel() != null && car.getWheel().getModel().matches("([A-Z]+\\s?)+");
    }

    private static boolean isCarPriceValid(Car car) {
        return car.getPrice() != null && car.getPrice().compareTo(BigDecimal.ZERO) >= 0;
    }

    private static boolean isCarEngineValid(Car car) {
        return car.getEngine().getPower() >= 0;
    }

    private static boolean isWheelSizeValid(Car car) {
        return car.getWheel().getSize() >= 0;
    }

    private static boolean isCarMileageValid(Car car) {
        return car.getMileage() >= 0;
    }

    private static boolean isCarComponentsListValid(Car car) {
        if (car.getCarBody().getComponents() == null) {
            return false;
        }
        return car.getCarBody().getComponents().stream().allMatch(component->component.matches("([A-Z]+\\s?)+"));
    }
}
