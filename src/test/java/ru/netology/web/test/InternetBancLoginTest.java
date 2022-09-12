package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.web.data.DataGenerator.Registration.getUser;
import static ru.netology.web.data.DataGenerator.getRandomLogin;
import static ru.netology.web.data.DataGenerator.getRandomPassword;

public class InternetBancLoginTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("span[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("span[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button[data-test-id='action-login']").click();
        $(".heading_theme_alfa-on-white").shouldHave(Condition.text("Личный кабинет")).shouldBe(Condition.visible);
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("span[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("span[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("button[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("span[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("span[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Пользователь заблокирован")).shouldBe(Condition.visible);
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("span[data-test-id='login'] input").setValue(wrongLogin);
        $("span[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("span[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("span[data-test-id='password'] input").setValue(wrongPassword);
        $("button[data-test-id='action-login']").click();
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
    }



//    @BeforeAll
//    static void setUpAll() {
//
//        given()
//                .spec(requestSpec)
//                .body(new RegistrationDto(login1, password1, "active"))
//                .when()
//                .post("/api/system/users")
//                .then()
//                .statusCode(200);
//    }
//
//    @Test
//    void userRegisteredStatusActive() {
//
//        open("http://localhost:9999/");
//        $("span[data-test-id='login'] input").setValue(login1);
//        $("span[data-test-id='password'] input").setValue(password1);
//        $("button[data-test-id='action-login']").click();
//        $(".heading_theme_alfa-on-white").shouldHave(Condition.text("Личный кабинет")).shouldBe(Condition.visible);
//    }
//
//    @Test
//    void userNotRegistered() {
//
//        open("http://localhost:9999/");
//        $("span[data-test-id='login'] input").setValue(DataGenerator.generateLogin("en"));
//        $("span[data-test-id='password'] input").setValue(DataGenerator.generatePassword("en"));
//        $("button[data-test-id='action-login']").click();
//        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
//        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
//    }
//
//    @Test
//    void invalidLogin() {
//
//        open("http://localhost:9999/");
//        $("span[data-test-id='login'] input").setValue(DataGenerator.generateLogin("en"));
//        $("span[data-test-id='password'] input").setValue(password1);
//        $("button[data-test-id='action-login']").click();
//        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
//        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
//    }
//
//    @Test
//    void invalidPassword() {
//
//        open("http://localhost:9999/");
//        $("span[data-test-id='login'] input").setValue(login1);
//        $("span[data-test-id='password'] input").setValue(DataGenerator.generatePassword("en"));
//        $("button[data-test-id='action-login']").click();
//        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
//        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
//    }
//
//    @Test
//    void userRegisteredStatusBlocked() {
//
//        given()
//                .spec(requestSpec)
//                .body(new RegistrationDto(login2, password2, "blocked"))
//                .when()
//                .post("/api/system/users")
//                .then()
//                .statusCode(200);
//
//        open("http://localhost:9999/");
//        $("span[data-test-id='login'] input").setValue(login2);
//        $("span[data-test-id='password'] input").setValue(password2);
//        $("button[data-test-id='action-login']").click();
//        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка!")).shouldBe(Condition.visible);
//        $("div[data-test-id='error-notification']").shouldHave(Condition.text("Пользователь заблокирован")).shouldBe(Condition.visible);
//    }
//
//    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
//            .setBaseUri("http://localhost")
//            .setPort(9999)
//            .setAccept(ContentType.JSON)
//            .setContentType(ContentType.JSON)
//            .log(LogDetail.ALL)
//            .build();
}
