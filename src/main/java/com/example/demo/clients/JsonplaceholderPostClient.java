package com.example.demo.clients;

import com.example.demo.dto.JsonPostDTO;
import com.example.demo.dto.PostDTO;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;

import java.util.List;

public interface JsonplaceholderPostClient {

    List<JsonPostDTO> allPosts();

    JsonPostDTO getPost(Long postId);

    JsonPostDTO createNewPost(JsonPostDTO post);

    JsonPostDTO updatePost(JsonPostDTO newPost , Long postId);

    JsonPostDTO deletePost(Long postId);
}
