package ru.javawebinar.topjava.model.storage;

import ru.javawebinar.topjava.model.entity.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class MealsInMemoryStorage {

    private static final MealsInMemoryStorage storage = new MealsInMemoryStorage();

    public ConcurrentSkipListMap<Integer, Meal> meals = null;

    private MealsInMemoryStorage() {

        meals = new ConcurrentSkipListMap<>();

        meals.put(0, new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(1, new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(2, new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(3, new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(4, new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(5, new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(6, new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

    }

    public static MealsInMemoryStorage getInstance() {
        return storage;
    }

    public ConcurrentMap<Integer, Meal> getContainer() {
        return meals;
    }

    public void add(Meal meal) {

        Integer availableKey = meals.lastKey() + 1;
        meal.setId(availableKey);
        meals.put(availableKey, meal);

    }

}
