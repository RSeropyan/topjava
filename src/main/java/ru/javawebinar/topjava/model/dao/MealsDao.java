package ru.javawebinar.topjava.model.dao;

import ru.javawebinar.topjava.model.entity.Meal;

import java.util.concurrent.ConcurrentMap;

public interface MealsDao {

    ConcurrentMap<Integer, Meal> findAll();

    Meal findById(Integer id);

    Integer save(Meal meal);

    void removeAll();

    void removeById();
}
