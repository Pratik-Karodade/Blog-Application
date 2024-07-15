package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // url : http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // url : http://localhost:8080/api/posts/allposts
    // pagination and sorting
    @GetMapping("all")
    public List<PostDto> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return postService.getAllPosts(pageNo,pageSize);
    }

    // url : http://localhost:8080/api/posts/{id}
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable(name = "id") long id){
        return postService.getPostById(id);
    }

    // url : http://localhost:8080/api/posts/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.updatePost(postDto,id),HttpStatus.OK);
    }

    // url : http://localhost:8080/api/posts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable (name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Entity deletes successfully",HttpStatus.OK);
    }
}
