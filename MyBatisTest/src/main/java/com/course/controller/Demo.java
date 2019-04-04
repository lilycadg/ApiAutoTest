package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lil on 2018/11/23.
 */
@Log4j
@Api(value="/v1",description = "这是第一个demo")
@RestController
@RequestMapping("v1")
public class Demo {
    @Autowired
    private SqlSessionTemplate template;
    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
    @ApiOperation(value="这是demo",httpMethod="GET")
    private int getUserCount(){
        return template.selectOne("getUserCount");
    }
    @RequestMapping(value="/addUser",method = RequestMethod.POST)
    //@ApiOperation(value = "这是添加用户的接口",httpMethod = "GET")
    public int addUser(@RequestBody User user){
        int result=template.insert("addUser",user);
        return result;

    }
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public int updateUser(@RequestBody User user){
       return template.update("updateUser",user);
    }
    @RequestMapping(value ="/deleteUser",method = RequestMethod.GET)
    public int delUser(@RequestParam int id){
        int resullt=template.delete("deleteUser",id);
        return resullt;
    }

}
