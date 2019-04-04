package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoModel;
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
public class UpdateUserInfoCase {
    @Test(dependsOnGroups = "loginTrue",description = "这是更新接口测试")
    public void updateUserInfo() throws IOException, InterruptedException {
        SqlSession session= DatabaseUtil.getSqlSession();
        UpdateUserInfoModel updateUserInfo1=session.selectOne("updateUserInfoModel",1);
        System.out.println(updateUserInfo1.toString());
        System.out.println(TestConfig.updateUserInfoUrl);
        int result=getResult(updateUserInfo1);
        Thread.sleep(2000);
        //上一个session中存在缓存，启用新的缓存
        SqlSession session1= DatabaseUtil.getSqlSession();
        User user=session1.selectOne(updateUserInfo1.getExpected(),updateUserInfo1);
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }

    private int getResult(UpdateUserInfoModel updateUserInfo1) throws IOException {
        HttpPost post=new HttpPost(TestConfig.updateUserInfoUrl);
        JSONObject param=new JSONObject();
        param.put("id",updateUserInfo1.getUserId());
        param.put("userName",updateUserInfo1.getUserName());
        param.put("sex",updateUserInfo1.getSex());
        param.put("age",updateUserInfo1.getAge());
        param.put("permission",updateUserInfo1.getPermission());
        param.put("isDelete",updateUserInfo1.getIsDelete());
        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.dafaultHttpClient.setCookieStore(TestConfig.store);
        String result;
        HttpResponse response=TestConfig.dafaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        return Integer.parseInt(result);
    }

    @Test(dependsOnGroups = "loginTrue",description = "这是删除用户信息")
    public void deleteUser() throws IOException, InterruptedException {
        SqlSession session= DatabaseUtil.getSqlSession();
        UpdateUserInfoModel updateUserInfo1=session.selectOne("updateUserInfoModel",2);
        System.out.println(updateUserInfo1.toString());
        System.out.println(TestConfig.updateUserInfoUrl);
        int result=getResult(updateUserInfo1);
        Thread.sleep(2000);
        SqlSession session1= DatabaseUtil.getSqlSession();
        User user=session1.selectOne(updateUserInfo1.getExpected(),updateUserInfo1);
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }
}
