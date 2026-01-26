package com.example.demo.utils;

import com.example.demo.dto.PostDTO;
import com.example.demo.entities.User;
import com.example.demo.services.PostService;
import com.example.demo.services.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private  final PostService postService;

    public boolean isOwnerOfPost(Long postId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }

}
