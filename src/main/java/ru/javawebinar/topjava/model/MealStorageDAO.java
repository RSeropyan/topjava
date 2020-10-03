package ru.javawebinar.topjava.model;

import java.util.List;

public class MealStorageDAO {

    public static List<Meal> getAllMeals() {
        return MealStorage.getMeals();
    }

}
