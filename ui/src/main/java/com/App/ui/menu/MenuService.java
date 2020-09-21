package com.App.ui.menu;

import com.App.service.CarsService;
import com.App.service.enums.SortItem;
import com.App.service.enums.StatisticItem;
import com.app.converter.model.enums.CarBodyType;
import com.app.converter.model.enums.EngineType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static com.App.ui.menu.UserDataService.*;


@RequiredArgsConstructor

public class MenuService {

    private final CarsService carsService;

    private int printMenu() {
        System.out.println("1. Get cars collection sorted asc/desc by Engine power / Tyre size / Components quantity ");
        System.out.println("2. Get cars collection with chosen body and price range");
        System.out.println("3. Get sorted cars collection with chosen engine");
        System.out.println("4. Get statistic data for chosen value price/mileage/power");
        System.out.println("5. Get map with Car key and mileage value");
        System.out.println("6. Get map with tyre type key and value as list of cars with that tyre type");
        System.out.println("7. Get cars collection with chosen component list");
        System.out.println("8. Exit");
        return getInt("Choose option:");
    }

    public void showMainMenu() {
        do {
            int choice = printMenu();
            switch (choice) {
                case 1 -> sortByItem();
                case 2 -> sortByCarBody();
                case 3 -> sortCarsByEngine();
                case 4 -> valueStatistic();
                case 5 -> carAndMileageMap();
                case 6 -> tyreTypeAndCarsListMap();
                case 7 -> carsContainComponents();
                case 8 -> {
                    System.out.println("Have a nice day");
                    return;
                }
                default -> System.out.println("No option with number " + choice);
            }
        } while (true);
    }

    private void carsContainComponents() {

        List<String> allComponents = carsService.allComponents();
        List<String> components = UserDataService.chooseComponents(allComponents);

        System.out.println(toJson(carsService.containComponents(components)));
    }

    private void tyreTypeAndCarsListMap() {
        System.out.println(toJson(carsService.toTyreAndCarsMap()));
    }

    private void carAndMileageMap() {
        System.out.println(toJson(carsService.toCarAndMileageMap()));
    }

    private void valueStatistic() {
        StatisticItem statisticItem = UserDataService.getStatisticItem();
        System.out.println(toJson(carsService.statistic(statisticItem)));
    }

    private void sortCarsByEngine() {
        EngineType engineType = getSortEngineType();
        System.out.println(toJson(carsService.carModelsWithChosenEngine(engineType)));
    }

    private void sortByCarBody() {
        CarBodyType carBodyType = UserDataService.getSortCarBody();
        BigDecimal min = UserDataService.getBigDecimal("Insert minimal price");
        BigDecimal max = UserDataService.getBigDecimal("Insert maximal price");

        System.out.println(toJson(carsService.carsWithChosenBody(carBodyType, min, max)));
    }


    private void sortByItem() {
        SortItem sortItem = getSortItem();
        boolean desc = getBoolean("DESC?");
        var s = carsService.sortByItem(sortItem, desc);
        System.out.println(toJson(s));
    }

    private static <T> String toJson(T element) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(element);
    }
}
