package com.course.model;

import lombok.Data;

/**
 * Created by lil on 2018/11/27.
 */
@Data
public class User {
    private int id;
    private String userName;
    private String password;
    private int age;
    private String sex;
    private String permission;
    private String isDelete;

}