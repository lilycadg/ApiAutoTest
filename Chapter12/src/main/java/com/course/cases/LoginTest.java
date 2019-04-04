package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginModel;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by lil on 2018/11/26.
 */
public class LoginTest {
    @BeforeTest(groups="loginTrue",description ="测试准备工作，获取HttpClient对象" )
    public void beforeTest(){
        TestConfig.getUserInfoUrl= ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl=ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl=ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.addUserUrl=ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.updateUserInfoUrl=ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.dafaultHttpClient=new DefaultHttpClient();

    }
    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession session= DatabaseUtil.getSqlSession();
        //从数据库中取出第一行的数据
        LoginModel loginModel= session.selectOne("loginModel",1);
        //输出第一行的数据 loginModel.toString
        System.out.println(loginModel.toString());
        System.out.println(TestConfig.loginUrl);
        //发送请求
        String result=getResult(loginModel);//请求发送后得到的response
        //验证结果
        Assert.assertEquals(loginModel.getExpected(),result);
    }

    private String getResult(LoginModel loginModel) throws IOException {
        HttpPost post=new HttpPost(TestConfig.loginUrl);
        JSONObject param=new JSONObject();
        //把第一行数据中的用户名和密码加入到json
        param.put("userName",loginModel.getUserName());
        param.put("password",loginModel.getPassword());
        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        String result;
        HttpResponse response=TestConfig.dafaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        TestConfig.store=TestConfig.dafaultHttpClient.getCookieStore();
        System.out.println(result);
        return result;
    }

    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession session= DatabaseUtil.getSqlSession();
        LoginModel loginModel= session.selectOne("loginModel",2);
        System.out.println(loginModel.toString());
        System.out.println(TestConfig.loginUrl);
        //发送请求
        String result=getResult(loginModel);
        //验证结果
        Assert.assertEquals(loginModel.getExpected(),result);
    }
}
