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
    }
}
