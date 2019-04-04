package com.weather.cases;

import com.weather.method.RestClientGet;
import com.weather.utils.ConfigFile;
import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by lil on 2019/1/9.
 */
public class GetApiTest {
   // TestBase testBase;
    private String host;
    //private ResourceBundle bundle;
    RestClientGet restClient;
    ConfigFile configfile;

    @BeforeTest
    public void beforeTest() {
       // bundle= ResourceBundle.getBundle("application", Locale.CHINA);
        configfile=new ConfigFile();
        host=configfile.getUrl();
        //host=bundle.getString("test.uri");
    }

    @Test
    public void getAPITest() throws ClientProtocolException, IOException {

        //String listuri=bundle.getString("user.list");
        String listuri=configfile.getListUri();
        String testUrl=this.host+listuri;
        restClient = new RestClientGet();
        restClient.get(testUrl);
    }
    @Test
    public void postAPITest() throws IOException {
        String registerUri=configfile.getRegisterUri();
        String testUrl=this.host+registerUri;
        restClient = new RestClientGet();
        restClient.post(testUrl);
    }
    @Test
    public void putAPITest() throws IOException {
        String updateUri=configfile.getUpdateUri();
        String testUri=this.host+updateUri;
        restClient=new RestClientGet();
        restClient.put(testUri);
    }
    @Test
    public void deleteAPITest() throws IOException {
        String deleteUri=configfile.getDeleteUri();
        String testUri=this.host+deleteUri;
        restClient=new RestClientGet();
        restClient.delete(testUri);
    }
}
