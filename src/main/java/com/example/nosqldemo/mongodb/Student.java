package com.example.nosqldemo.mongodb;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "student")
@Data
public class Student {

    private Integer id;
    private String name;
    private String sex;
    private List<Course> courseList;
}
