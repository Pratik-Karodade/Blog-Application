package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;

    //title should not be empty min =2 characters
    @NotEmpty
    @Size(min = 2,message = "title should not be empty min =2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10,message = "description should not be empty min = 2 characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
    private Long categoryId;
}
