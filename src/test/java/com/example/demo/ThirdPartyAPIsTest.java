package com.example.demo;


import com.example.demo.clients.JsonplaceholderPostClient;
import com.example.demo.dto.JsonPostDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ThirdPartyAPIsTest {

    @Autowired
    private JsonplaceholderPostClient jsonService;

    Logger log = LoggerFactory.getLogger(ThirdPartyAPIsTest.class);

    @Test
    @Order(2)
    void getAllJsonPosts(){
        log.info("fetching posts");
        List<JsonPostDTO> posts =  jsonService.allPosts();

        log.trace("fetching");
        System.out.println(posts);
//        posts.forEach(System.out::println);
        log.debug("got all json post");

    }

    @Test
    @Order(3)
    void getJsonPost(){
        JsonPostDTO post = jsonService.getPost(101L);
        System.out.println(post);
    }

    @Test
    @Order(1)
    void createJsonPost(){
        JsonPostDTO dto = new JsonPostDTO();
        dto.setTitle("yashwanth");
        dto.setBody("I am yash the rocky bhai of india");

        JsonPostDTO createdPost = jsonService.createNewPost(dto);
        System.out.println(createdPost);
    }

    @Order(4)
    @Test
    void updateJsonPost(){
        JsonPostDTO dto = new JsonPostDTO();
        dto.setTitle("yashwanth");
        dto.setBody("I am yash the rocky bhai of india");

        JsonPostDTO jsonPost = jsonService.updatePost(dto , 2L);
        System.out.println(jsonPost);
    }

    @Test
    @Order(5)
    void deleteJsonPost(){
        JsonPostDTO deletedPost = jsonService.deletePost(3L);
        System.out.println(deletedPost);
    }



}
