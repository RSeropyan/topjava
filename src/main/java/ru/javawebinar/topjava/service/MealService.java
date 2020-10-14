package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal, int userId) throws NotFoundException {
        Meal newMeal = repository.save(meal, userId);
        if (newMeal != null) {
            return newMeal;
        }
        else {
            throw new NotFoundException("Not found entity with id=" + meal.getId());
        }
    }

    public void deleteById(int id, int userId) throws NotFoundException {

        boolean isDeleted = repository.delete(id, userId);
        if (!isDeleted) {
            throw new NotFoundException("Not found entity with id=" + id);
        }

    }

    public Meal getById(int id, int userId) throws NotFoundException {

        Meal meal = repository.get(id, userId);
        if (meal != null) {
            return meal;
        }
        else {
            throw new NotFoundException("Not found entity with id=" + id);
        }

    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

}