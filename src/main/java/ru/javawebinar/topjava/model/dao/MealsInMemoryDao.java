package ru.javawebinar.topjava.model.dao;

import ru.javawebinar.topjava.model.entity.Meal;
import ru.javawebinar.topjava.model.storage.MealsInMemoryStorage;

import java.util.concurrent.ConcurrentMap;

public class MealsInMemoryDao implements MealsDao{

    private static final MealsInMemoryDao dao = new MealsInMemoryDao();

    private MealsInMemoryDao() { }

    public static MealsInMemoryDao getInstance() {
        return dao;
    }

    @Override
    public ConcurrentMap<Integer, Meal> findAll() {
        return MealsInMemoryStorage.getInstance().list();
    }

    @Override
    public Meal findById(Integer id) {
        return null;
    }

    @Override
    public Integer save(Meal meal) {
        return null;
    }

    @Override
    public void removeAll() {

    }

    @Override
    public void removeById() {

    }
}
