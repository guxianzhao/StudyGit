package com.example.nosqldemo.service;

import com.example.nosqldemo.annotation.NeedSetValueField;
import com.example.nosqldemo.mongodb.Course;

public class StudentService {

    public Course getCourse(Integer id){
        return new Course();
    }

    @NeedSetValueField()
    public void GetStudent(){

    }
}
