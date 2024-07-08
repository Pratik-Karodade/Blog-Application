package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository posRepository) {
        this.postRepository = posRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //converting postdto to post
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        // convert post to postdto
        PostDto postResponse = mapToDto(post);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
       return  posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //convert post to postdto
    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        return post;
    }
}
