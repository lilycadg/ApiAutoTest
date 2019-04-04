package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoModel;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lil on 2018/11/26.
 */
public class GetUserInfoCase {
    @Test(dependsOnGroups = "loginTrue",description = "获取userId为1的用户信息")
    public  void getUserInfo() throws IOException {
        SqlSession session= DatabaseUtil.getSqlSession();
        //getUserInfoModel  为什么是这个字符串？
        GetUserInfoModel getUserInfoModel=session.selectOne("getUserInfo1",1);
        System.out.println(getUserInfoModel.toString());
        System.out.println(TestConfig.getUserInfoUrl);
        JSONArray resultJson=getJsonResult(getUserInfoModel);
        User user=session.selectOne(getUserInfoModel.getExpected(),getUserInfoModel);
        List userList=new ArrayList();
        userList.add(user);
        JSONArray jsonArray=new JSONArray(userList);
        //字段没有对应
        JSONArray jsonArray1=new JSONArray(resultJson.getString(0));
        Assert.assertEquals(jsonArray.toString(),jsonArray1.toString());
    }

    private JSONArray getJsonResult(GetUserInfoModel getUserInfoModel) throws IOException {
        HttpPost post=new HttpPost(TestConfig.getUserListUrl);
        JSONObject param=new JSONObject();
        param.put("id",getUserInfoModel.getUserId());
        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.dafaultHttpClient.setCookieStore(TestConfig.store);
        String result;
        HttpResponse response=TestConfig.dafaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        List resultList= Arrays.asList(result);
        JSONArray array=new JSONArray(resultList);
        return array;
    }


}
