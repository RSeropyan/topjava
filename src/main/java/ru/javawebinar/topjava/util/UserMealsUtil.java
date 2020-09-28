package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        System.out.println("Filtered by cycles:");
        List<UserMealWithExcess> mealsFilteredByCycles = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsFilteredByCycles.forEach(System.out::println);


        System.out.println("Filtered by streams:");
        List<UserMealWithExcess> mealsFilteredByStreams = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsFilteredByStreams.forEach(System.out::println);

        System.out.println("Filtered by streams - Optional 2:");
        List<UserMealWithExcess> mealsFilteredByStreamsOptional2 = filteredByStreamsOptional2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsFilteredByStreamsOptional2.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesPerDayHistory = new HashMap<>();

        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            caloriesPerDayHistory.merge(mealDate, meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> filteredUserMealsWithExcess = new ArrayList<>();

        for (UserMeal meal : meals) {
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            if (TimeUtil.isBetweenHalfOpen(mealTime, startTime, endTime)) {
                LocalDate mealDate = meal.getDateTime().toLocalDate();
                Integer mealDateCurrentCalories = caloriesPerDayHistory.get(mealDate);
                boolean isExcess = (mealDateCurrentCalories > caloriesPerDay);
                UserMealWithExcess mealWithExcess = new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), isExcess);
                filteredUserMealsWithExcess.add(mealWithExcess);
            }
        }

        return filteredUserMealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesPerDayHistory = meals.stream()
                .collect(Collectors.toMap(
                        meal -> meal.getDateTime().toLocalDate(),
                        UserMeal::getCalories,
                        Integer::sum
                ));

        List<UserMealWithExcess> filteredUserMealsWithExcess = meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> {
                    LocalDate mealDate = meal.getDateTime().toLocalDate();
                    Integer mealDateCurrentCalories = caloriesPerDayHistory.get(mealDate);
                    boolean isExcess = (mealDateCurrentCalories > caloriesPerDay);
                    return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), isExcess);
                })
                .collect(Collectors.toList());

        return filteredUserMealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreamsOptional2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> filteredUserMealsWithExcess = meals.stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate()))
                .values()
                .stream()
                .collect(Collectors.toMap(
                        list -> list.stream().collect(Collectors.summingInt(UserMeal::getCalories)),
                        Function.identity()
                ))
                .entrySet()
                .stream()
                .flatMap(e -> e.getValue()
                        .stream()
                        .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                        .map(userMeal -> {
                            boolean isExcess = (e.getKey() > caloriesPerDay);
                            return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), isExcess);
                        }))
                .collect(Collectors.toList());

        return filteredUserMealsWithExcess;
    }

}
