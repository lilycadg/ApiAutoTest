package com.weather.cases;

import com.weather.method.RestClientGet;
import com.weather.utils.ConfigFile;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by lil on 2019/1/11.
 */
public class LoginCase {
    private String host;
    //private ResourceBundle bundle;
    RestClientGet restClient;
    ConfigFile configfile;

    @BeforeTest
    public void beforeTest() {
        configfile=new ConfigFile();
        host=configfile.getLoginHost();
    }
    @Test
    public void loginAPITest() throws IOException {
        String loginUri=configfile.getLoginUri();
        String testUri=this.host+loginUri;
        restClient=new RestClientGet();
        restClient.login(testUri);
    }
    @Test
    public void enterpriseAPITest() throws IOException, InterruptedException {
        String enterpriseUri=configfile.enterpriseCode();
        String testUri=this.host+enterpriseUri;
        restClient=new RestClientGet();
        restClient.enterpriseList(testUri);
    }
    @Test
    public void validateAPITest() throws IOException {
        String validateUri=configfile.getValidateTicket();
        String testUri=this.host+validateUri;
        restClient=new RestClientGet();
        restClient.valitateTicketGet(testUri);
    }
}
