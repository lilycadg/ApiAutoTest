package com.course.model;

import lombok.Data;

/**
 * Created by lil on 2018/11/26.
 */
@Data
public class User {
    private int id;
    private String userName;
    private String password;
    private String permission;
    private int age;
    private String sex;
    private String isDelete;
    public String toString(){
        return("{id:"+id+","+
                "userName:"+userName+","+
                "password:"+password+","+
                "permission:"+permission+","+
                "age:"+age+","+
                "sex:"+sex+","+
                "isDelete:"+isDelete+",}"
        );
    }

}
