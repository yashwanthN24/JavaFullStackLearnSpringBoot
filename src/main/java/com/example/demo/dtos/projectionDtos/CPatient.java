package com.example.demo.dtos.projectionDtos;

import lombok.*;


//public record CPatient(Long id, String name) {
//}

//or class

//@Getter
//@Setter
//@AllArgsConstructor
//@ToString

public  class CPatient{
    private Long Id;
    private String name;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CPatient(Long id, String name) {
        Id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "CPatient{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }
}