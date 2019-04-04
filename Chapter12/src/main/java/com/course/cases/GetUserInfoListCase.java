package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListModel;
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
import java.util.List;

/**
 * Created by lil on 2018/11/26.
 */
public class GetUserInfoListCase {
    @Test(dependsOnGroups = "loginTrue",description = "获取性别为0的用户")
    public void getUserListInfo() throws IOException, InterruptedException {
        SqlSession session= DatabaseUtil.getSqlSession();
        GetUserListModel getUserListModel=session.selectOne("getUserList1",1);
        System.out.println(getUserListModel.toString());
        System.out.println(TestConfig.getUserListUrl);
        //发送请求数据
        //数据表中sex=1，在user表中查询sex=0用户
        JSONArray resultJson=getJsonResult(getUserListModel);
        Thread.sleep(200);
        //验证  这一块不太理解//直接从user中取出sex=0的用户，真实数据
        //请求参数是getUserListModel，返回结果是User .getUserListModel.getExpected()=getUserList,与数据库查询中的id对应
        List<User> userList=session.selectList(getUserListModel.getExpected(),getUserListModel);
        for(User u:userList){
            System.out.print("获取的user"+u.toString());
        }
        JSONArray userListJson=new JSONArray(userList);
        Assert.assertEquals(userListJson.length(),resultJson.length());
        for(int i=0;i<resultJson.length();i++){
            JSONObject expect= (JSONObject) resultJson.get(i);
            JSONObject actual= (JSONObject) userListJson.get(i);
            //期望数据与真实数据做对比
            Assert.assertEquals(expect.toString(),actual.toString());
        }
    }

    private JSONArray getJsonResult(GetUserListModel getUserInfoListModel) throws IOException {
        HttpPost post=new HttpPost(TestConfig.getUserListUrl);
        JSONObject param=new JSONObject();
        param.put("userName",getUserInfoListModel.getUserName());
        param.put("sex",getUserInfoListModel.getSex());
        param.put("age",getUserInfoListModel.getAge());
        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.dafaultHttpClient.setCookieStore(TestConfig.store);
        String result;
        HttpResponse response=TestConfig.dafaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
        JSONArray jsonArray=new JSONArray(result);
        return jsonArray;
    }
}
