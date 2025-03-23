package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookInfoModel {
    private String isbn;
    private String title;
}