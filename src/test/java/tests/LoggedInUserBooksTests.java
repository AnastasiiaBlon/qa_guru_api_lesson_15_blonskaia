package tests;

import org.openqa.selenium.Cookie;
import models.AddBooksModel;
import models.IsbnModel;
import models.LoginResponseModel;
import models.TokenGenerationModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static tests.TestData.credentials;

public class LoggedInUserBooksTests extends TestBase {
    @Test
    @DisplayName("Add book in account")
    void addBookToProfileTest() {
        LoginResponseModel loginResponse = authorizationApi.login(credentials);
        booksApi.deleteAllBooks(loginResponse);

        step("Create book object", () -> {
            IsbnModel isbnModel = new IsbnModel(TestData.book.getIsbn());
            List<IsbnModel> isbnList = List.of(isbnModel);
            AddBooksModel booksList = new AddBooksModel(loginResponse.getUserId(), isbnList);
            booksApi.addBook(loginResponse, booksList);
        });

        step("Add auth cookies", () -> {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
            getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        });

        step("Check the book is shown", () -> {
            open("/profile");
            $("[id='see-book-" + TestData.book.getTitle() + "']").shouldBe(visible);
        });
    }

    @Test
    @DisplayName("Delete book from account")
    void deleteBookFromProfileTest() {
        LoginResponseModel loginResponse = authorizationApi.login(credentials);
        booksApi.deleteAllBooks(loginResponse);

        step("Add book in account", () -> {
            IsbnModel isbnModel = new IsbnModel(TestData.book.getIsbn());
            List<IsbnModel> isbnList = List.of(isbnModel);
            AddBooksModel booksList = new AddBooksModel(loginResponse.getUserId(), isbnList);
            booksApi.addBook(loginResponse, booksList);
        });

        step("Add auth cookies", () -> {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
            getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        });

        step("Open account", () -> {
            open("/profile");
        });

        step ("Check the book is shown in account", () -> {
            $("[id='see-book-" + TestData.book.getTitle() + "']").shouldBe(visible);
        });

        step("Delete book from account", () -> {
            booksApi.deleteBook(loginResponse, TestData.book.getIsbn());
        });

        step("Открываем профиль и проверяем, что книга исчезла", () -> {
            open("/profile");
            $("[id='see-book-" + TestData.book.getTitle() + "']").shouldNotBe(visible);
        });
    }
}
