package com.App.ui;

import com.App.service.CarsService;
import com.App.ui.menu.MenuService;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        CarsService carsService = new CarsService("./resources/jsonSamochody02.json");

        MenuService menuService = new MenuService(carsService);
        menuService.showMainMenu();


    }
}
