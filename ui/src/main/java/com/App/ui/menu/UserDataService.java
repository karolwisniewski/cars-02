package com.App.ui.menu;

import com.App.service.enums.SortItem;
import com.App.service.enums.StatisticItem;
import com.App.ui.exception.UserDataServiceException;
import com.app.converter.model.enums.CarBodyType;
import com.app.converter.model.enums.EngineType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class UserDataService {
    private static final Scanner sc = new Scanner(System.in);

    public static String getString(String message) {
        System.out.println(message);
        return sc.nextLine();
    }

    public static int getInt(String message) {
        System.out.println(message);

        String value = sc.nextLine();
        if (!value.matches("\\d+")) {
            throw new UserDataServiceException("value is not a number");
        }
        return Integer.parseInt(value);
    }
    public static double getDouble(String message) {
        System.out.println(message);

        String value = sc.nextLine();
        if (!value.matches("\\d+")) {
            throw new UserDataServiceException("value is not a number");
        }
        return Double.parseDouble(value);
    }

    public static BigDecimal getBigDecimal(String message) {
        System.out.println(message);

        String value = sc.nextLine();
        if (!value.matches("\\d+(.\\d)?")) {
            throw new UserDataServiceException("value is not a number");
        }
        return new BigDecimal(value);
    }

//    public static Color getColor() {
//        AtomicInteger counter = new AtomicInteger(1);
//        String message = Arrays
//                .stream(Color.values())
//                .map(color -> counter.getAndIncrement() + ". " + color)
//                .collect(Collectors.joining("\n"));
//        System.out.println(message);
//        int choice = getInt("Choose color number: ");
//
//        if (choice < 1 || choice > Color.values().length) {
//            throw new UserDataServiceException("no color with number "+ choice);
//        }
//
//        return Color.values()[choice - 1];
//    }

    public static SortItem getSortItem() {
        AtomicInteger counter = new AtomicInteger(1);
        String message = Arrays
                .stream(SortItem.values())
                .map(sortItem -> counter.getAndIncrement() + ". " + sortItem)
                .collect(Collectors.joining("\n"));
        System.out.println(message);
        int choice = getInt("Choose sort item number: ");

        if (choice < 1 || choice > SortItem.values().length) {
            throw new UserDataServiceException("no sort item with number "+ choice);
        }

        return SortItem.values()[choice - 1];
    }

    public static CarBodyType getSortCarBody() {
        AtomicInteger counter = new AtomicInteger(1);
        String message = Arrays
                .stream(CarBodyType.values())
                .map(carBody -> counter.getAndIncrement() + ". " + carBody)
                .collect(Collectors.joining("\n"));
        System.out.println(message);
        int choice = getInt("Choose car body type number: ");

        if (choice < 1 || choice > SortItem.values().length) {
            throw new UserDataServiceException("no sort item with number "+ choice);
        }

        return CarBodyType.values()[choice - 1];
    }

    public static EngineType getSortEngineType() {
        AtomicInteger counter = new AtomicInteger(1);
        String message = Arrays
                .stream(EngineType.values())
                .map(engineType -> counter.getAndIncrement() + ". " + engineType)
                .collect(Collectors.joining("\n"));
        System.out.println(message);
        int choice = getInt("Choose engine type number: ");

        if (choice < 1 || choice > SortItem.values().length) {
            throw new UserDataServiceException("no sort item with number "+ choice);
        }

        return EngineType.values()[choice - 1];
    }

    public static StatisticItem getStatisticItem() {
        AtomicInteger counter = new AtomicInteger(1);
        String message = Arrays
                .stream(StatisticItem.values())
                .map(statisticItem -> counter.getAndIncrement() + ". " + statisticItem)
                .collect(Collectors.joining("\n"));
        System.out.println(message);
        int choice = getInt("Choose engine type number: ");

        if (choice < 1 || choice > SortItem.values().length) {
            throw new UserDataServiceException("no sort item with number "+ choice);
        }

        return StatisticItem.values()[choice - 1];
    }

    public static boolean getBoolean(String message) {
        System.out.println(message + "[Y/N]?");
        return sc.nextLine().toLowerCase().equals("y");
    }

    public static List<String> chooseComponents(List<String> allComponents){
        AtomicInteger counter = new AtomicInteger();
        List<String> components = new ArrayList<>();
        do {
            counter.getAndSet(1);
            String message = allComponents
                    .stream()
                    .map(x -> counter.getAndIncrement() + ". " + x)
                    .collect(Collectors.joining("\n"));
            System.out.println(message);
            int choice = getInt("Choose components, insert 0 to finish");
            if(choice == 0){
                return components ;
            }
            components.add(allComponents.get(choice-1));
            allComponents.remove(choice-1);
        }while (true);
    }

    public void close() {
        if (sc != null) {
            sc.close();
        }
    }

}
