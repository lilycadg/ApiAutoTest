package com.weather.sha1;

import java.security.MessageDigest;

/**
 * Created by lil on 2019/1/11.
 */
public class SHA1 {
    String sign;
    public String ts;
    //拼接字符串
    public  String connectStr(String ts){
        String str;
        String appkey="b774cfe9fc8776b961a650df3efb3mf3";
        String uri="/api/client/v2/user/request_ticket";
        //ts= String.valueOf(Calendar.getInstance().getTimeInMillis());
       // String ts="1464942570531";
        str=appkey+","+uri+","+ts;
        return str;
    }
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        sign=buf.toString();
        return buf.toString();
    }

    public String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
   /* public void main(String[] args) {
        sign=encode(connectStr());
        System.out.println(sign);
    }*/

}
