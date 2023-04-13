package com.blogapi.bloggingapi.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    @NotBlank
    @Size(min = 4, message = "Name should be more than 3 characters")
    private String categoryName;

    @NotBlank
    @Size(min = 4, message = "Description should be more than 3 characters")
    private String categoryDescription;
}
