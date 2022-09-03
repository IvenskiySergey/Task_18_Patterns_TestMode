package ru.netology.web;

import com.codeborne.selenide.Condition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class InternetBancLoginTest {

    @BeforeAll
    static void setUpAll() {

        given()
                .spec(requestSpec)
                .body(new RegistrationDto("vasya", "password", "active"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @Test
    void userRegisteredStatusActive() {

        open("http://localhost:9999/");
        $("span[data-test-id='login'] input").setValue("vasya");
        $("span[data-test-id='password'] input").setValue("password");
        $("button[data-test-id='action-login']").click();
        $(".heading_theme_alfa-on-white").shouldHave(Condition.text("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void userNotRegisteredStatusActive() {

        open("http://localhost:9999/");
        $("span[data-test-id='login'] input").setValue("nina");
        $("span[data-test-id='password'] input").setValue("password123");
        $("button[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void invalidLogin() {

        open("http://localhost:9999/");
        $("span[data-test-id='login'] input").setValue("vosya");
        $("span[data-test-id='password'] input").setValue("password");
        $("button[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void invalidPassword() {

        open("http://localhost:9999/");
        $("span[data-test-id='login'] input").setValue("vasya");
        $("span[data-test-id='password'] input").setValue("pasword");
        $("button[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    void userRegisteredStatusBlocked() {

        given()
                .spec(requestSpec)
                .body(new RegistrationDto("vasya", "password", "blocked"))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);

        open("http://localhost:9999/");
        $("span[data-test-id='login'] input").setValue("vasya");
        $("span[data-test-id='password'] input").setValue("password");
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
