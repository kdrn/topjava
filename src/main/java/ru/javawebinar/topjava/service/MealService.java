package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(Meal meal, int userId);

    void delete(int mealId, int userId) throws NotFoundException;

    MealWithExceed get(int mealId, int userId) throws NotFoundException;

    List<MealWithExceed> getAll(int userId);

    void update(Meal meal, int userId);
}
