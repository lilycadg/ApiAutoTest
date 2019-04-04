package com.weather.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by lil on 2019/1/9.
 */
public class ConfigFile {
    private static ResourceBundle bundle;
    public int RESPNSE_STATUS_CODE_200 = 200;
    public int RESPNSE_STATUS_CODE_201 = 201;
    public int RESPNSE_STATUS_CODE_404 = 404;
    public int RESPNSE_STATUS_CODE_500 = 500;

    public static String getUrl(){
        bundle= ResourceBundle.getBundle("application", Locale.CHINA);
        String url=bundle.getString("test.uri");
        return url;
    }
    //获取登录主机名
    public static String getLoginHost(){
        bundle= ResourceBundle.getBundle("application", Locale.CHINA);
        String loginhost=bundle.getString("login.url");
        return loginhost;

    }
    public static String getLoginUri(){
        String loginuri=bundle.getString("login.uri");
        return loginuri;
    }
    //得到用户的企业代码
    public static String enterpriseCode(){
        String enterpriseuri=bundle.getString("enterprise.list");
        return enterpriseuri;
    }
    public static String getListUri(){
        String listuri=bundle.getString("user.list");
        return listuri;
    }
    //validate_ticket
    public static String getValidateTicket(){
        String validateTicket=bundle.getString("validate.list");
        return validateTicket;
    }
    //post方法
    public static String getRegisterUri(){
        String registerUri=bundle.getString("register.uri");
        return registerUri;
    }
    //put方法uri
    public static String getUpdateUri(){
        String updateUri=bundle.getString("update.uri");
        return updateUri;
    }
    //delete方法uri
    public static String getDeleteUri(){
        String deleteUri=bundle.getString("delete.uri");
        return deleteUri;
    }
}
