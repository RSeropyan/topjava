package ru.javawebinar.topjava.model.dao;

import ru.javawebinar.topjava.model.entity.Meal;

import java.util.List;

public interface MealsDao {

    List<Meal> findAll();

    Meal findById(Integer id);

    Integer save(Meal meal);

    void removeAll();

    void removeById();
}
