package com.App.service;

import com.App.service.enums.SortItem;
import com.App.service.enums.StatisticItem;
import com.App.service.exception.CarsServiceException;
import com.App.service.statistic.CarStatistic;
import com.App.service.validator.CarValidator;
import com.app.converter.json.CarsJsonConverter;
import com.app.converter.model.Car;
import com.app.converter.model.enums.CarBodyType;
import com.app.converter.model.enums.EngineType;
import com.app.converter.model.enums.TyreType;
import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarsService {

    private final Set<Car> cars;

    public CarsService(String filename) {
        cars = init(filename);
    }

    private Set<Car> init(String filename) {
        AtomicInteger counter = new AtomicInteger(0);

        return new CarsJsonConverter(filename)
                .fromJson()
                .orElseThrow(() -> new CarsServiceException("json conversion has been failed"))
                .stream()
                .filter(car -> {
                    counter.getAndIncrement();
                    if (!CarValidator.validation(car)) {
                        System.out.println(("--------> VALIDATION ERROR FOR CAR NO. " + counter.get()));
                        return false;
                    }
                    return true;
                }).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return cars
                .stream()
                .map(Car::toString)
                .collect(Collectors.joining("\n"));
    }
    // ------   METODY   -------

    /*
    1. Metoda zwraca kolekcję samochodów posortowaną według kryterium
podanego jako argument. Metoda powinna umożliwiać sortowanie
według ilości komponentów, mocy silnika oraz rozmiaru opony.
Dodatkowo metoda powinna umożliwiać sortowanie rosnąco oraz
malejąco.
     */
    public List<Car> sortByItem(SortItem sortItem, boolean reverse) {
        if (Objects.isNull(sortItem)) {
            throw new IllegalArgumentException("Sort item is null!");
        }

        Stream<Car> sortedCars = switch (sortItem) {
            case COMPONENTS_LENGTH -> cars
                    .stream()
                    .sorted((Comparator.comparing(x -> x.getCarBody().getComponents().size())));
            case ENGINE_POWER -> cars
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getEngine().getPower()));
            case TIRE_SIZE -> cars
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getWheel().getSize()));
        };
        List<Car> carsAfterSorting = sortedCars.collect(Collectors.toList());
        if (reverse) {
            Collections.reverse(carsAfterSorting);
        }
        return carsAfterSorting;
    }

    /*
    2. Metoda zwraca kolekcję samochodów o określonym rodzaju nadwozia
przekazanym jako argument (CarBodyType) oraz o cenie z
przedziału <a, b>, gdzie a oraz b to kolejne argumenty metody
     */
    public List<Car> carsWithChosenBody(CarBodyType type, BigDecimal min, BigDecimal max) {
        if (type == null) throw new CarsServiceException("Car body type is null!");
        if (Objects.isNull(min) || Objects.isNull(max)) throw new CarsServiceException("Price value is null");
        if (min.compareTo(max) > 0) throw new CarsServiceException("Price range is not correct");

        return cars
                .stream()
                .filter(x -> x.doesItMatch(type, min, max))
                .collect(Collectors.toList());
    }

    /*
    3. Metoda zwraca posortowaną alfabetycznie kolekcję modeli
samochodów, które posiadają typ silnika (EngineType) przekazany
jako argument metody.
     */
    public List<String> carModelsWithChosenEngine(EngineType engine) {
        if (engine == null) {
            throw new IllegalArgumentException("Engine type is null!");
        }
        return cars
                .stream()
                .filter(x -> x.getEngine().getEngineType().equals(engine))
                .map(Car::getModel)
                .sorted()
                .collect(Collectors.toList());
    }

    /*
    4. Metoda zwraca dane statystyczne dla podanej jako argument
wielkości. Dopuszczalne wielkości to cena, przebieg oraz moc
silnika. Dane statystyczne powinny zawierać wartość
najmniejszą, wartość największą oraz wartość średnią.
     */
    public CarStatistic statistic(StatisticItem statisticItem) {
        if (statisticItem == null) throw new CarsServiceException("Statistic item is null!");

        switch (statisticItem) {
            case PRICE -> {
                return new CarStatistic(cars
                        .stream()
                        .map(Car::getPrice)
                        .collect(Collectors2.summarizingBigDecimal(price -> price)));
            }
            case POWER -> {
                return new CarStatistic(cars
                        .stream()
                        .map(car -> car.getEngine().getPower())
                        .collect(Collectors2.summarizingBigDecimal(BigDecimal::valueOf)));
            }
            case MILEAGE -> {
                return new CarStatistic(cars
                        .stream()
                        .map(Car::getMileage)
                        .collect(Collectors2.summarizingBigDecimal(BigDecimal::new)));
            }
            default -> throw new CarsServiceException("Wrong statistic Item");
        }
    }

    /*
    5.Metoda zwraca mapę, w której kluczem jest obiekt klasy Car,
natomiast wartością jest liczba kilometrów, które samochód
przejechał. Pary w mapie posortowane są malejąco według
wartości.
     */
    public Map<Car, Integer> toCarAndMileageMap() {
        return cars
                .stream()
                // .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        Car::getMileage))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Car, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum, LinkedHashMap::new));
    }

    /*
    6.Metoda zwraca mapę, w której kluczem jest rodzaj opony
(TyreType), a wartością lista samochodów o takim typie opony.
Mapa posortowana jest malejąco po ilości elementów w kolekcji.
     */
    public Map<TyreType, List<Car>> toTyreAndCarsMap() {

        return cars
                .stream()
                .collect(Collectors.groupingBy(car -> car.getWheel().getTyreType()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    /*
    7. Metoda zwraca kolekcję samochodów, które posiadają wszystkie
komponenty z kolekcji przekazanej jako argument. Kolekcja
posortowana jest alfabetycznie według nazwy modelu samochodu.
     */
    public List<Car> containComponents(List<String> components) {
        if (components == null) {
            throw new IllegalArgumentException("Components list is null!");
        }
        return cars
                .stream()
                .filter(x -> x.getCarBody().getComponents().containsAll(components))
                .sorted(Comparator.comparing(Car::getModel))
                .collect(Collectors.toList());
    }

    public List<String> allComponents() {
        return cars
                .stream()
                .flatMap(x -> x.getCarBody().getComponents().stream())
                .distinct()
                .collect(Collectors.toList());

    }
}
