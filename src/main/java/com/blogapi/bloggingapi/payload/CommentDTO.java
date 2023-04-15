package com.blogapi.bloggingapi.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Integer id;

    @NotBlank(message = "Comment should not be blank")
    private String content;

    private UserDTO user;

    private Integer postId;
}
