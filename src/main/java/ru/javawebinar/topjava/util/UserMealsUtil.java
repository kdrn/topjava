package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                   LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> mapCounter = new HashMap<>(); //МАПа для подсчета калорий за день
        List<UserMealWithExceed> resultList = new ArrayList<>();
        for (UserMeal meal : mealList) { //обходим переданный лист
            /*если в МАПе не содержится дата блюда, добавляем в МАПу дату с калориями из аргумента метода минус калории блюда
            */
            if (!mapCounter.containsKey(meal.getDateTime().toLocalDate())) {
                mapCounter.put(meal.getDateTime().toLocalDate(), caloriesPerDay - meal.getCalories());
            } else  //если содержится, то:
            {
                mapCounter.put(meal.getDateTime().toLocalDate(), //уменьшаем в МАПе-счетчике для даты блюда значение на количество калорий блюда из листа
                        mapCounter.get(meal.getDateTime().toLocalDate()) - meal.getCalories());
            }
        }
        for (UserMeal meal : mealList) //второй раз обходим список блюд
        {
            if (mapCounter.get(meal.getDateTime().toLocalDate()) < 0 && TimeUtil.isBetween(meal.getDateTime().toLocalTime(),
                    startTime, endTime)) //если значение калорий за этот день в счетчике-МАПе меньше 0 и время в интересуемом промежутке
            {// добавляем в результат новый объект UserMealWithExceed со значением true
                resultList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        true));
            } else if (mapCounter.get(meal.getDateTime().toLocalDate()) >= 0 && TimeUtil.isBetween(meal.getDateTime().toLocalTime(),
                    startTime, endTime)) //если значение калорий за этот день в счетчике-МАПе больше или равно 0 и время в интересуемом промежутке
            {// добавляем в результат новый объект UserMealWithExceed со значением false
                resultList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        false));
            }
        }
        return resultList;
    }
}
