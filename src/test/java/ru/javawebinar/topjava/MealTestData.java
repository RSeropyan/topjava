package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    // USER meal
    public static Meal userMeal1;
    public static Meal userMeal2;
    public static Meal userMeal3;

    // ADMIN meal
    public static Meal adminMeal1;
    public static Meal adminMeal2;
    public static Meal adminMeal3;
    public static Meal adminMeal4;

    static {
        initializeTestData();
    }

    public static void initializeTestData() {
        // USER meal initializing
        userMeal1 = new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        userMeal2 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        userMeal3 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);

        // ADMIN meal initializing
        adminMeal1 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        adminMeal2 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        adminMeal3 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        adminMeal4 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    }

}
