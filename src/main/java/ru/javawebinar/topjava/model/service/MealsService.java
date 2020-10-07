package ru.javawebinar.topjava.model.service;

import ru.javawebinar.topjava.model.dao.MealsDao;
import ru.javawebinar.topjava.model.dao.MealsInMemoryDao;
import ru.javawebinar.topjava.model.dto.MealTo;
import ru.javawebinar.topjava.model.entity.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MealsService {

    private static final MealsService service = new MealsService();

    private MealsDao dao = null;

    private MealsService() {
        dao = MealsInMemoryDao.getInstance();
    }

    public static MealsService getInstance() {
        return service;
    }

    private static final int CALORIES_DAILY_LIMIT = 2000;

    public List<MealTo> findAll() {

        List<Meal> mealsList = new ArrayList<>(dao.findAll().values());
        return MealsUtil.convertToDtoList(mealsList, CALORIES_DAILY_LIMIT);

    }

    public Meal findById(Integer id) {
        return dao.findById(id);
    }

    public void removeById(Integer id) {
        dao.removeById(id);
    };

    public void save(Integer id, LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(id, dateTime, description, calories);
        dao.save(meal);
    }

}
