package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealsInMemoryStorageService {

    private static final int CALORIES_DAILY_LIMIT = 2000;

    public static List<MealTo> getAllMeals() {
        List<Meal> meals = MealsInMemoryStorage.getInstance().findAll();
        return MealsUtil.convertToDtoList(meals, CALORIES_DAILY_LIMIT);

    }

}
