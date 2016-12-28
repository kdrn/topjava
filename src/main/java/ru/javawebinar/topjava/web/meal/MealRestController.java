package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public MealWithExceed get(int mealId)
    {
        return service.get(mealId, AuthorizedUser.id());
    }

    public void delete(int mealId)
    {
        service.delete(mealId, AuthorizedUser.id());
    }

    public void update(Meal meal)
    {
        if (meal.getDateTime() == null)
        {
            meal = new Meal(meal.getId(), LocalDateTime.now(), meal.getDescription(), meal.getCalories());
        }
        service.update(meal, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAll()
    {
        return service.getAll(AuthorizedUser.id());
    }

}
