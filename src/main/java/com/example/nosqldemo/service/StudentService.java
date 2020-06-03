package com.example.nosqldemo.service;

import com.example.nosqldemo.annotation.NeedSetValueField;
import com.example.nosqldemo.mongodb.Course;
import com.example.nosqldemo.mongodb.Student;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StudentService {

    static HashMap<Integer, String> map = new HashMap<Integer, String>();


    public Course getCourse(Integer id) {
        if (map.size() == 0) {
            for (int i = 0; i < 10; i++) {
                map.put(i, "Course" + i);
            }
        }
        Course course = new Course();
        course.setId(id);
        course.setName(map.get(id));
        return course;
    }

    @NeedSetValueField()
    public List<Student> GetStudent() {
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Student(i));
        }
        return list;
    }

    public List<Student> GetStudentAndCourse() {
        List<Student> list = new ArrayList<>();
        Student student = null;
        for (int i = 0; i < 10; i++) {
            student = new Student(i);
            student.setName(getCourse(i).getName());
            list.add(student);
        }
        return list;
    }
}
