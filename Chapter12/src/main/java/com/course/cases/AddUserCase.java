package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserModel;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by lil on 2018/11/26.
 */
public class AddUserCase {
    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser() throws IOException, InterruptedException {
        SqlSession session= DatabaseUtil.getSqlSession();
        AddUserModel addUserModel=session.selectOne("addUserModel",1);
        System.out.println(addUserModel.toString());
        System.out.println(TestConfig.addUserUrl);
        //发请求，获取结果
        String result=getResult(addUserModel);
        //验证返回结果
        Thread.sleep(2000);
        //addUser   向user表中添加数据之后，取不到
        SqlSession session1= DatabaseUtil.getSqlSession();
        User user=session1.selectOne("addUser",addUserModel);
        System.out.println(user.toString());
        Assert.assertEquals(addUserModel.getExpected(),result);

    }

    private String getResult(AddUserModel addUserModel) throws IOException {
        HttpPost post=new HttpPost(TestConfig.addUserUrl);
        JSONObject param=new JSONObject();
        param.put("userName",addUserModel.getUserName());
        param.put("password",addUserModel.getPassword());
        param.put("sex",addUserModel.getSex());
        param.put("age",addUserModel.getAge());
        param.put("permission",addUserModel.getPermission());
        param.put("isDelete",addUserModel.getIsDelete());
        //设置请求头信息
        post.setHeader("Content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookies
        TestConfig.dafaultHttpClient.setCookieStore(TestConfig.store);
        String result;//存放返回结果
        HttpResponse response=TestConfig.dafaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        return result;
    }
}
