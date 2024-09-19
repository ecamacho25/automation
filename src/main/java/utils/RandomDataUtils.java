package utils;

import com.github.javafaker.Faker;

public class RandomDataUtils {
	
    private static final Faker faker = new Faker();

    public static String generateRandomString(int length) {
        return faker.lorem().characters(length);
    }

    public static int generateRandomInt(int bound) {
        return faker.number().numberBetween(0, bound);
    }

    public static String generateRandomFirstName() {
        return faker.name().firstName();
    }

    public static String generateRandomLastName() {
        return faker.name().lastName();
    }

    public static String generateRandomEmail() {
        return faker.internet().emailAddress();
    }

    public static String generateRandomPhoneNumber() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String generateRandomAddress() {
        return faker.address().fullAddress();
    }

}