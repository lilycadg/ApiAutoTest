package com.weather.method;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weather.sha1.SHA1;
import com.weather.utils.ConfigFile;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
/**
 * Created by lil on 2019/1/9.
 */
public class RestClientGet {
     ConfigFile configfile=new ConfigFile();;
     SHA1 sha=new SHA1();
     public String ticket;
     public String ts=String.valueOf(Calendar.getInstance().getTimeInMillis());
   //  public String sign=sha.encode(sha.connectStr(ts));

    //1. Get 请求方法
    public void get(String url) throws ClientProtocolException, IOException {

        //HttpGet get = new HttpGet(url);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        //创建一个HttpGet的请求对象
        HttpGet httpget = new HttpGet(url);
        //执行请求,相当于postman上点击发送按钮，然后赋值给HttpResponse对象接收
        HttpResponse httpResponse = httpclient.execute(httpget);

        //拿到Http响应状态码，例如和200,404,500去比较
        int responseStatusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("response status code -->"+responseStatusCode);
        Assert.assertEquals(responseStatusCode,configfile.RESPNSE_STATUS_CODE_200,"不相等");

        //把响应内容存储在字符串对象
        String responseString = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");

        //创建Json对象，把上面字符串序列化成Json对象
        JSONObject responseJson = JSON.parseObject(responseString);
        System.out.println("respon json from API-->" + responseJson);

        //获取响应头信息,返回是一个数组
        Header[] headerArray = httpResponse.getAllHeaders();
        //创建一个hashmap对象，通过postman可以看到请求响应头信息都是Key和value得形式，所以我们想起了HashMap
        HashMap<String, String> hm = new HashMap<String, String>();
        //增强for循环遍历headerArray数组，依次把元素添加到hashmap集合
        for(Header header : headerArray) {
            hm.put(header.getName(), header.getValue());
        }

        //打印hashmap
        System.out.println("response headers -->"+ hm);

    }
    public void post(String url) throws ClientProtocolException, IOException {
        //声明一个Client对象，用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个方法，这个方法就是post方法
        HttpPost post = new HttpPost(url);
        //添加参数
        JSONObject param = new JSONObject();
        param.put("name","morpheus");
        param.put("job","leader");
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = client.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        //JSONObject resultJson = new JSONObject(result);
        JSONObject resultJson = JSON.parseObject(result);
        System.out.println("这是API数据"+resultJson);
        int responseStatusCode = response.getStatusLine().getStatusCode();
        System.out.println("response status code -->"+responseStatusCode);
        Assert.assertEquals(responseStatusCode,configfile.RESPNSE_STATUS_CODE_201,"状态码不一致");
        String name=(String) resultJson.get("name");
        String job=(String) resultJson.get("job");
        Assert.assertEquals(name,"morpheus"," name不一致");
        Assert.assertEquals(job,"leader"," 不一致");
    }
    public void put(String url) throws IOException {
        //声明一个Client对象，用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个put方法
        HttpPut put=new HttpPut(url);
        //添加参数
        JSONObject param = new JSONObject();
        param.put("name","morpheus");
        param.put("job","zion resident");
        //设置请求头信息 设置header
        put.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        put.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = client.execute(put);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        JSONObject resultJson = JSON.parseObject(result);
        System.out.println("这是API数据"+resultJson);
        int responseStatusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(responseStatusCode,configfile.RESPNSE_STATUS_CODE_200,"状态码不一致");
        String name=(String) resultJson.get("name");
        String job=(String) resultJson.get("job");
        Assert.assertEquals(name,"morpheus"," name不一致");
        Assert.assertEquals(job,"zion resident"," 不一致");
    }
    public void delete(String url) throws IOException {
        //声明一个Client对象，用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个put方法
        HttpDelete delete=new HttpDelete(url);
        //发送请求
        HttpResponse response=client.execute(delete);
        //拿到Http响应状态码，例如和200,404,500去比较
        int responseStatusCode = response.getStatusLine().getStatusCode();
        System.out.println("response status code -->"+responseStatusCode);
        Assert.assertEquals(responseStatusCode,configfile.RESPNSE_STATUS_CODE_200,"状态码不一致");

    }
    public String login(String url)throws IOException{
        //声明一个Client对象，用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个方法，这个方法就是post方法
        HttpPost post = new HttpPost(url);
        //添加参数
        JSONObject param = new JSONObject();
        param.put("userName","2017122@cadg.cn");
        param.put("password","li123456");
        param.put("terminal","web");
        param.put("code","litest");
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        post.setHeader("appid","8cg78c041b2b28c734c3e5b7534cbb8p");
        //加密，得到签名
        //String ts=String.valueOf(Calendar.getInstance().getTimeInMillis());

       // String sign=sha.encode(sha.connectStr(ts));
        post.setHeader("ts",ts);
        post.setHeader("sign",sha.encode(sha.connectStr(ts)));
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = client.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        int responseStatusCode = response.getStatusLine().getStatusCode();
        System.out.println("response status code -->"+responseStatusCode);
        Assert.assertEquals(responseStatusCode,configfile.RESPNSE_STATUS_CODE_200,"状态码不一致");
        //JSONObject resultJson = new JSONObject(result);
        JSONObject resultJson = JSON.parseObject(result);
        System.out.println("这是API数据"+resultJson);

        //得到json字符串中的data
        JSONObject data= (JSONObject) resultJson.get("data");
        //从data中取出ticket，供其他接口使用
        ticket= (String) data.get("ticket");
        System.out.println(ticket);
        return ticket;
    }
    public void enterpriseList(String url) throws IOException, InterruptedException {
        //声明一个Client对象，用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个方法，这个方法就是post方法
        HttpPost post = new HttpPost(url);
        //添加参数
        JSONObject param = new JSONObject();
        param.put("userName","cs@litest.com");
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        post.setHeader("appid","8cg78c041b2b28c734c3e5b7534cbb8p");
        //加密，得到签名
        //String ts=String.valueOf(Calendar.getInstance().getTimeInMillis());
        //String sign=sha.encode(sha.connectStr(ts));
        post.setHeader("ts",ts);
        post.setHeader("sign",sha.encode(sha.connectStr(ts)));

        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = client.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        JSONObject resultJson = JSON.parseObject(result);
        System.out.println("这是API数据"+resultJson);
        Object code=resultJson.get("code");
        Assert.assertEquals(code,configfile.RESPNSE_STATUS_CODE_200,"状态码不一致");
    }
    public void valitateTicketGet(String url) throws IOException {
        //String uri=https://devwork.cbim.org.cn/api/client/v2/user/request_ticket;
        //String ticket=login(uri);
        //声明一个Client对象，用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个put方法
        HttpGet get=new HttpGet(url);
        //设置请求头信息 设置header
        get.setHeader("content-type","application/json");
        get.setHeader("appid","8cg78c041b2b28c734c3e5b7534cbb8p");
        //加密，得到签名
        //String ts=String.valueOf(Calendar.getInstance().getTimeInMillis());
        //String sign=sha.encode(sha.connectStr(ts));
        get.setHeader("ts",ts);
        get.setHeader("sign",sha.encode(sha.connectStr(ts)));

       //怎么取到ticket？
        get.setHeader("ticket",ticket);
        //发送请求
        HttpResponse response=client.execute(get);
        //拿到Http响应状态码，例如和200,404,500去比较
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        JSONObject resultJson = JSON.parseObject(result);
        Object code=resultJson.get("code");
        Assert.assertEquals(code,configfile.RESPNSE_STATUS_CODE_200,"状态码不一致");

    }
}
