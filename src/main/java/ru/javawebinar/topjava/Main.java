package ru.javawebinar.topjava;

import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    public static void main(String[] args) {
//        System.out.format("Hello Topjava Enterprise!");

        MealRepository rep = new InMemoryMealRepositoryImpl();
        System.out.println(rep.getAll(1));
        System.out.println("******************");
        System.out.println(rep.delete(3, 1));
        System.out.println("******************");
        System.out.println(rep.get(6, 1));
        System.out.println("******************");
        System.out.println(rep.getAll(1));
    }
}
