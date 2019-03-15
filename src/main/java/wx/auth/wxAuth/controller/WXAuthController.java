package wx.auth.wxAuth.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wx.auth.wxAuth.util.AuthUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@RequestMapping("/auth")
@Controller
public class WXAuthController {


    @GetMapping("/wxLogin")
    public void wxLogin(HttpServletResponse response) throws IOException {
        String backUrl = "http://43084102.nat123.cc/auth/callBack";
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
                + "appid="+ AuthUtil.APPID
                + "&redirect_uri="+ URLEncoder.encode(backUrl)
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";
        response.sendRedirect(url);
    }

    @GetMapping("/callBack")
    public void callBack(HttpServletRequest request) throws IOException {
        String code = request.getParameter("code");

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                + "appid="+AuthUtil.APPID
                + "&secret="+AuthUtil.APPSECRET
                + "&code="+code
                + "&grant_type=authorization_code";
        JSONObject jsonObject = AuthUtil.doGetJson(url);
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");

        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?"
                + "access_token="+token
                + "&openid="+openid
                + "&lang=zh_CN";

        JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
        System.out.println(userInfo);
    }





}
