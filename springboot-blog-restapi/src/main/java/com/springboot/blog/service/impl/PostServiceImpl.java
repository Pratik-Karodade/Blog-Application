package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository posRepository) {
        this.postRepository = posRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //converting postdto to post
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());

        Post newPost = postRepository.save(post);

        // convert post to postdto
        PostDto postResponse = new PostDto();
        postResponse.setContent(newPost.getContent());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        return postResponse;
    }
}
