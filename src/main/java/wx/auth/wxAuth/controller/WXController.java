package wx.auth.wxAuth.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import wx.auth.wxAuth.util.WeixinUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WXController {

    /**
     * 微信小程序登录
     *
     * @param code
     * @return
     * @throws Exception
     */
    @PostMapping("/wxLogin")
    public Map<String, Object> wxLogin(String code) throws Exception {
        System.out.println("+++++++++++++++wxlogin-code:" + code);
        String APPID = "wx740c40c88326d26b";
        String SECRET = "24f1970e3b267a3bda4beb9115dbd5b1";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + APPID + "&secret=" + SECRET
                + "&js_code=" + code + "&grant_type=authorization_code";
        JSONObject json = WeixinUtil.doGet(url);
        System.out.println(json);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        return map;
    }


}
