package helpers;

import api.AuthApi;
import models.LoginResponseModel;
import models.UserNameAndPasswordModel;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        String login = System.getProperty("userName", "userName");
        String password = System.getProperty("password", "password");

        AuthApi authorizationApi = new AuthApi();
        UserNameAndPasswordModel credentials = new UserNameAndPasswordModel(login, password);
        LoginResponseModel loginResponse = authorizationApi.login(credentials);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
    }
}