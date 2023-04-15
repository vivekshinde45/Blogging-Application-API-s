package com.blogapi.bloggingapi.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Integer id;

    @NotBlank(message = "Please provide title")
    @Size(min = 2, message = "Title should be more than 1 character")
    private String title;

    @NotBlank(message = "Please provide content")
    @Size(min = 5, message = "content should be more than 5 characters")
    private String content;

    private Date addedDate;

    private String imgName;

    private CategoryDTO category;

    private UserDTO user;

    private Set<CommentDTO> comments = new HashSet<>();
}
