package com.example.nosqldemo.mongodb;

import com.example.nosqldemo.annotation.NeedSetValue;
import com.example.nosqldemo.service.StudentService;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "student")
@Data
public class Student {
    public Student() {

    }

    public Student(Integer id) {
        this.id = id;
    }

    private Integer id;
    @NeedSetValue(beanClass = StudentService.class, param = "id", method = "getCourse", targetField = "name")
    private String name;
    private String sex;
    private List<Course> courseList;
}
