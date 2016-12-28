package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int mealId, int userId) throws NotFoundException {
        ValidationUtil.checkNotFound(repository.delete(mealId, userId), "Not found for user" + userId);
    }

    @Override
    public MealWithExceed get(int mealId, int userId) throws NotFoundException {
        MealWithExceed result = null;
        for (MealWithExceed m : this.getAll(userId))
        {
            if(m.getId() == mealId)
            {
                result = m;
            }
        }
//        Meal current = ValidationUtil.checkNotFound(this.getAll(userId), "UserID: " + userId );
////        List<MealWithExceed> exceedList = this.getAll(userId);
        return ValidationUtil.checkNotFound(result, "UserID: " + userId);
    }

    @Override
    public List<MealWithExceed> getAll(int userId) {
        return MealsUtil.getWithExceeded(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public void update(Meal meal, int userId) {
        repository.save(meal, userId);
    }
}
