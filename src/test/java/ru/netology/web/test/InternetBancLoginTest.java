package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.data.RegistrationDto;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class InternetBancLoginTest {

    static String login1 = DataGenerator.generateLogin("en");
    static String password1 = DataGenerator.generateLogin("en");

    static String login2 = DataGenerator.generateLogin("en");
    static String password2 = DataGenerator.generateLogin("en");

    @BeforeAll
    static void setUpAll() {

        given()
                .spec(requestSpec)
                .body(new RegistrationDto(login1, password1, "active"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @Test
    void userRegisteredStatusActive() {

        open("http://localhost:9999/");
        $("span[data-test-id='login'] input").setValue(login1);
        $("span[data-test-id='password'] input").setValue(password1);
        $("button[data-test-id='action-login']").click();
        $(".heading_theme_alfa-on-white").shouldHave(Condition.text("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void userNotRegistered() {

        open("http://localhost:9999/");
        $("span[data-test-id='login'] input").setValue(DataGenerator.generateLogin("en"));
        $("span[data-test-id='password'] input").setValue(DataGenerator.generatePassword("en"));
        $("button[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void invalidLogin() {

        open("http://localhost:9999/");
        $("span[data-test-id='login'] input").setValue(DataGenerator.generateLogin("en"));
        $("span[data-test-id='password'] input").setValue(password1);
        $("button[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void invalidPassword() {

        open("http://localhost:9999/");
        $("span[data-test-id='login'] input").setValue(login1);
        $("span[data-test-id='password'] input").setValue(DataGenerator.generatePassword("en"));
        $("button[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void userRegisteredStatusBlocked() {

        given()
                .spec(requestSpec)
                .body(new RegistrationDto(login2, password2, "blocked"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

        open("http://localhost:9999/");
        $("span[data-test-id='login'] input").setValue(login2);
        $("span[data-test-id='password'] input").setValue(password2);
        $("button[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
}
