package com.example.nosqldemo;

import Service.DemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NosqldemoApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void Test(){
		System.out.println(Math.pow(1.07,30));
		System.out.println(Math.pow(1.1,30));
		DemoService service=new DemoService();
		service.Show();
	}
}
