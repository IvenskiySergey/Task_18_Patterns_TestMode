package ru.netology.web.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateLogin(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String login = faker.name().firstName();
        return login;
    }

    public static String generatePassword(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String password = faker.internet().password();
        return password;
    }
}
