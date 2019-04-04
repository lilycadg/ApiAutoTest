package com.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;

/**
 * Created by lil on 2018/11/23.
 */
@EnableScheduling
//@ComponentScan("com.course")
@SpringBootApplication
public class Application {
    private static ConfigurableApplicationContext context;
    public static void main(String[] args){
        Application.context= SpringApplication.run(Application.class,args);
    }
    @PreDestroy
    public void close(){
        Application.context.close();
    }
}
