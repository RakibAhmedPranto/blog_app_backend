package com.blog.app.blog.category.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer id;
    @NotEmpty
    @Size(min = 4, message = "Category Title Must be min 4 character long")
    private String title;
    @NotEmpty
    private String description;
}
