package com.example.nosqldemo;

import Service.DemoService;
import com.example.nosqldemo.mongodb.Student;
import com.example.nosqldemo.mysql.entity.User;
import com.example.nosqldemo.mysql.service.UserService;
import com.example.nosqldemo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NosqldemoApplicationTests {

    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void Test() {
        System.out.println(Math.pow(1.07, 30));
        System.out.println(Math.pow(1.1, 30));
        DemoService service = new DemoService();
        service.Show();
    }

    @Test
    void testApo() {
        Integer count = 1000000;

        Long now = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            List<Student> list = studentService.GetStudent();
            // System.out.print(list);
        }
        System.out.println("反射结束");
        Long then = System.currentTimeMillis();
        System.out.println("反射的使用时间：" + (then - now));

        for (int i = 0; i < count; i++) {
            List<Student> list = studentService.GetStudentAndCourse();
            // System.out.print(list);
        }
        System.out.println("不反射结束");
        Long end = System.currentTimeMillis();
        System.out.println("不反射的使用时间：" + (end - then));
    }

    @Test
    void testMySqlApo() {
        Integer count = 100;

        Long now = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            List<User> list = userService.getUserList();
            System.out.print(list.getClass());
        }
        System.out.println("反射结束");
        Long then = System.currentTimeMillis();
        System.out.println("反射的使用时间：" + (then - now));

        for (int i = 0; i < count; i++) {
            List<User> list = userService.getUserListAndRole();
            System.out.print(list.getClass());
        }
        System.out.println("直接赋值结束");
        Long end = System.currentTimeMillis();
        System.out.println("直接赋值的使用时间：" + (end - then));

    }
}
