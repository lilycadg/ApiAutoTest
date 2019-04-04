package com.course.model;

import lombok.Data;

/**
 * Created by lil on 2018/11/26.
 */
@Data
public class AddUserModel {
    private String userName;
    private String password;
    private String sex;
    private int age;
    private String permission;
    private String isDelete;
    private String expected;
}
