package wx.auth.wxAuth.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class AuthUtil {

    public static final String APPID = "wxd9a6ae7fd385178d";
    public static final String APPSECRET = "ba0aa8e4a71adbf58efa0879898ae7bb";

    public static JSONObject doGetJson(String url) throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        HttpResponse httpResponse = client.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        if(entity != null){
            String result = EntityUtils.toString(entity,"utf-8");
            jsonObject = new JSONObject(result);
        }
        return jsonObject;
    }

}
