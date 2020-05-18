package com.example.nosqldemo;

import com.example.nosqldemo.mongodb.Course;
import com.example.nosqldemo.mongodb.Student;
import com.example.nosqldemo.mongodb.StudentReps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class MongoDBTests {

    @Autowired
    private StudentReps studentReps;

    @Test
    void InserTest() {
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            student.setId(i + 1);
            student.setName(String.valueOf((char) (65 + i)));
            student.setSex("男");
            List<Course> courses = new ArrayList<Course>();
            Course math = new Course();
            math.setId(1);
            math.setName("数学");
            courses.add(math);
            Course chinese = new Course();
            chinese.setId(2);
            chinese.setName("语文");
            courses.add(chinese);
            Course english = new Course();
            english.setId(3);
            english.setName("英语");
            courses.add(english);
            studentReps.saveStudent(student, courses);
            System.out.println(studentReps.findById(student.getId()));
        }

    }

    @Test
    void UpdateTest() {
        Student student = studentReps.findAll().get(0);
        System.out.println(student);
        Course course = student.getCourseList().get(0);
        course.setName("测试修改：" + new Date());
        studentReps.updateCourseName(student.getId(), course);
        System.out.println(studentReps.findById(student.getId()));
    }

    @Test
    void  SetQueryTest(){
        System.out.println( studentReps.findBySetId(2));
    }
}
