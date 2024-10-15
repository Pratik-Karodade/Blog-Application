package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(
        name = "CRUD operations for POST operations"
)
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // url : http://localhost:8080/api/posts
    @Operation(summary = "Create post REST API",description = "Create post rest api is used to save post to database")
    @ApiResponse(responseCode = "201",description = "Http status 201 created")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // url : http://localhost:8080/api/posts/allposts
    // pagination and sorting
    @GetMapping("all")
    @Operation(summary = "Get all posts REST API",
            description = "Get all posts rest api is used to get all posts")
    @ApiResponse(responseCode = "200",description = "Http status 200")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO,required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam( value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam( value = "sortDir", defaultValue = AppConstants.DEFAULF_SORT_DIR,required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    // url : http://localhost:8080/api/posts/{id}
    @Operation(summary = "Get post by id REST API",
            description = "get post by id rest api is used to get post to database")
    @ApiResponse(responseCode = "200",description = "Http status 200")
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable(name = "id") long id){
        return postService.getPostById(id);
    }

    // url : http://localhost:8080/api/posts/{id}
    @Operation(summary = "Update post REST API",
            description = "Update post by id rest api is used to update post")
    @ApiResponse(responseCode = "200",description = "Http status 200")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.updatePost(postDto,id),HttpStatus.OK);
    }

    // url : http://localhost:8080/api/posts/{id}
    @Operation(summary = "Delete post REST API",
            description = "Delete post by id rest api is used to delete post")
    @ApiResponse(responseCode = "200",description = "Http status 200")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable (name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Entity deletes successfully",HttpStatus.OK);
    }


    // http://localhost:8080/api/posts/category/{id}
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("id") Long categoryId){
        List<PostDto> postDtos = postService.getPostByCategory(categoryId);
        return ResponseEntity.ok(postDtos);
    }
}
