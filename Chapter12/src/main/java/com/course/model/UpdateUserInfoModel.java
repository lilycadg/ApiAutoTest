package com.course.model;

import lombok.Data;

/**
 * Created by lil on 2018/11/26.
 */
@Data
public class UpdateUserInfoModel {
    private int id;
    private int userId;
    private String userName;
    private String sex;
    private int age;
    private String permission;
    private String isDelete;
    private String expected;
}

