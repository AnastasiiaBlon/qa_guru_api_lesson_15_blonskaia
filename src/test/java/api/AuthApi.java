package api;
import models.TokenGenerationModel;
import specs.ApiSpec;
import models.UserNameAndPasswordModel;
import models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class AuthApi {

    public LoginResponseModel login(UserNameAndPasswordModel credentials){

        return given()
                .body(credentials)
                .contentType(JSON)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(ApiSpec.successResponseSpec)
                .extract().as(LoginResponseModel.class);
    }
    public TokenGenerationModel generateToken (UserNameAndPasswordModel credentials){

        return given()
                .body(credentials)
                .contentType(JSON)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(ApiSpec.successResponseSpec)
                .extract().as(TokenGenerationModel.class);
    }
}