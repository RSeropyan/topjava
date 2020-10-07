package ru.javawebinar.topjava.model.dao;

import ru.javawebinar.topjava.model.entity.Meal;
import ru.javawebinar.topjava.model.storage.MealsInMemoryStorage;

import java.util.concurrent.ConcurrentMap;

public class MealsInMemoryDao implements MealsDao{

    private static final MealsInMemoryDao dao = new MealsInMemoryDao();
    private MealsInMemoryStorage storage = null;

    private MealsInMemoryDao() {
        storage = MealsInMemoryStorage.getInstance();
    }

    public static MealsInMemoryDao getInstance() {
        return dao;
    }

    @Override
    public ConcurrentMap<Integer, Meal> findAll() {
        return storage.getContainer();
    }

    @Override
    public Meal findById(Integer id) {
        return storage.getContainer().get(id);
    }

    @Override
    public void save(Meal meal) {

        Integer id = meal.getId();
        if (id == null) {
            storage.add(meal);
        }
        else {
            storage.getContainer().replace(id, meal);
        }

    }

    @Override
    public void removeById(Integer id) {
        storage.getContainer().remove(id);
    }
}
