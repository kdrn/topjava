package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by kdrn on 19.02.2017.
 */

@Controller
@RequestMapping(value = "/meals")
public class MealsController {

    @Autowired
    private MealService mealService;

    private static final Logger LOG = LoggerFactory.getLogger(MealsController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String getMeals(Model model) {
        LOG.info("getAll");
        List<MealWithExceed> meals = MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()),
                MealsUtil.DEFAULT_CALORIES_PER_DAY);
        model.addAttribute("meals", meals);
        return "meals";
    }

    @RequestMapping(method = RequestMethod.GET, params = "action=create")
    public String createMeals(HttpServletRequest request, Model model) {
        LOG.info("createMeals (redirect to meal.jsp)");
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(method = RequestMethod.GET, params = "action=update")
    public String updateMeals(HttpServletRequest request, Model model) {
        LOG.info("updateMeals (redirect to meal.jsp)");
        final Meal meal = mealService.get(getId(request), AuthorizedUser.id());
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(method = RequestMethod.GET, params = "action=delete")
    public String deleteMeals(HttpServletRequest request, Model model) {
        LOG.info("deleteMeals");
        int id = getId(request);
        LOG.info("Delete {}", id);
        mealService.delete(id, AuthorizedUser.id());
        return getMeals(model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "action=filter")
    public String filter(HttpServletRequest request, Model model) {
        LOG.info("filterMeals");
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        int uId = AuthorizedUser.id();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
//        Collection<Meal> meals = mealService.getBetweenDateTimes(startDateTime, endDateTime, uId);
//        Collection<MealWithExceed> mealsTO = MealsUtil.getWithExceeded(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        List<MealWithExceed> meals = MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()),
                MealsUtil.DEFAULT_CALORIES_PER_DAY);
        List<MealWithExceed> mealsTO = meals.stream().filter(m -> DateTimeUtil.isBetween(m.getDateTime(), startDateTime, endDateTime)).collect(Collectors.toList());
        model.addAttribute("meals", mealsTO);
        return "meals";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveMeal(HttpServletRequest request, Model model) {
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            LOG.info("Create {}", meal);
            mealService.save(meal, AuthorizedUser.id());
        } else {
            LOG.info("Update {}", meal);
            meal.setId(Integer.parseInt(request.getParameter("id")));
            mealService.update(meal, AuthorizedUser.id());
        }
        return getMeals(model);
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

}
