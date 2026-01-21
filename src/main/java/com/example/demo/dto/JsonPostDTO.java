package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JsonPostDTO {

    private Long id;
    private String title;
    private String body;
}
