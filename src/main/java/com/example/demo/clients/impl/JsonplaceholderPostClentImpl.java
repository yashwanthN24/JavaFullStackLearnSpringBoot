package com.example.demo.clients.impl;

import com.example.demo.clients.JsonplaceholderPostClient;
import com.example.demo.dto.JsonPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JsonplaceholderPostClentImpl implements JsonplaceholderPostClient {

    private  final RestClient getJsonPlaceHolderPostsClient;


    @Override
    public List<JsonPostDTO> allPosts() {
        List<JsonPostDTO> posts =  getJsonPlaceHolderPostsClient
                .get()
                .uri("posts")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return posts;
    }

    @Override
    public JsonPostDTO getPost(Long postId) {
        return getJsonPlaceHolderPostsClient
                .get()
                .uri("posts/{id}" , postId)
                .retrieve()
                .body(JsonPostDTO.class);
    }

    @Override
    public JsonPostDTO createNewPost(JsonPostDTO post) {
        return getJsonPlaceHolderPostsClient.post().uri("posts").body(post).retrieve().body(JsonPostDTO.class);
    }

    @Override
    public JsonPostDTO updatePost(JsonPostDTO newPost, Long postId) {
        return getJsonPlaceHolderPostsClient
                .patch()
                .uri("posts/{id}" , postId )
                .retrieve()
                .body(JsonPostDTO.class);
    }

    @Override
    public JsonPostDTO deletePost(Long postId) {
        return getJsonPlaceHolderPostsClient
                .delete()
                .uri("posts/{id}" , postId)
                .retrieve()
                .body(JsonPostDTO.class);
    }
}
