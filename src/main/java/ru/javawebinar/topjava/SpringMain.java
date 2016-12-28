package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            MealRestController mealController = appCtx.getBean(MealRestController.class);
            System.out.println("***********************************************");
            System.out.println(mealController.getAll());

            System.out.println("***********************************************");
            mealController.update(new Meal(LocalDateTime.now(), "Жрачка", 111));
            mealController.update(new Meal(LocalDateTime.now(), "Жрачка2", 1111));
            mealController.update(new Meal(LocalDateTime.now(), "Жрачка3", 1111));
            System.out.println(mealController.getAll());

            System.out.println("***********************************************");
            mealController.update(new Meal(1, LocalDateTime.now(), "Новая жрачка", 777));
            System.out.println(mealController.getAll());
            System.out.println("***********************************************");
            System.out.println(mealController.get(1));
            mealController.delete(1);


//            adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN));
//            adminUserController.create(new User(2, "userName4", "email4", "password4", Role.ROLE_ADMIN));
//            adminUserController.create(new User(3, "userName3", "email3", "password3", Role.ROLE_ADMIN));
//            adminUserController.create(new User(4, "userName2", "email2", "password2", Role.ROLE_ADMIN));
        }
    }
}
