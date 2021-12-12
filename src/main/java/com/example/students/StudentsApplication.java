package com.example.students;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class StudentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentsApplication.class, args);
    }
}
