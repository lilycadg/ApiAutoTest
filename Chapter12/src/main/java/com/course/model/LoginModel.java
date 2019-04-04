package com.course.model;

import lombok.Data;

/**
 * Created by lil on 2018/11/26.
 */
@Data
public class LoginModel {
    private int id;
    private String userName;
    private String password;
    private String expected;
}
