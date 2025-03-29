package tests;

import models.BookInfoModel;
import models.UserNameAndPasswordModel;

public class TestData {

    static String userName = System.getProperty("userName", "userName");
    static String password = System.getProperty("password", "password");

    public static UserNameAndPasswordModel credentials = new UserNameAndPasswordModel(userName, password);
    private static String book_isbn = "9781449325862";
    private static String book_title = "Git Pocket Guide";
    public static final BookInfoModel book = new BookInfoModel(book_isbn, book_title);
}