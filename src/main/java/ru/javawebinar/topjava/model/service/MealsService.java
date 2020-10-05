package ru.javawebinar.topjava.model.service;

import ru.javawebinar.topjava.model.dao.MealsDao;
import ru.javawebinar.topjava.model.dao.MealsInMemoryDao;
import ru.javawebinar.topjava.model.dto.MealTo;
import ru.javawebinar.topjava.model.entity.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealsService {

    private static final MealsService service = new MealsService();

    private MealsService() { }

    public static MealsService getInstance() {
        return service;
    }

    private static final int CALORIES_DAILY_LIMIT = 2000;

    public List<MealTo> getAllMeals() {
        MealsDao mealsDao = MealsInMemoryDao.getInstance();
        List<Meal> meals = mealsDao.findAll();
        return MealsUtil.convertToDtoList(meals, CALORIES_DAILY_LIMIT);

    }

}
