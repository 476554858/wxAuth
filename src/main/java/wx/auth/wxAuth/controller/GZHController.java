package wx.auth.wxAuth.controller;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.auth.wxAuth.util.CheckUtil;
import wx.auth.wxAuth.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@RequestMapping("/gzh")
@Controller
public class GZHController {

    //43084102.nat123.cc

    /**
     * 公众号接入
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */

    @ResponseBody
    @GetMapping("/gzhjr")
    public String gzhjr(String signature,String timestamp,String nonce,String echostr,HttpServletResponse response) throws IOException {
        if(CheckUtil.checkSignature(signature,timestamp,nonce)){
//             response.getWriter().print(echostr);
            return echostr;
        }
        return "";
    }


    @PostMapping("/gzhjr")
    public void gzhjr(HttpServletResponse response,HttpServletRequest request) throws IOException, DocumentException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        Map<String,String> map = MessageUtil.xmlToMap(request);
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");

        String message = null;
        if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
            if("1".equals(content)){
                message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.firstMenu());
            }else if("2".equals(content)){
                message = MessageUtil.initNewsMessage(toUserName,fromUserName);
            }else if("3".equals(content)){
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.threeMenu());
            }else if ("4".equals(content)) {
                message = MessageUtil.initImageMessage(toUserName,fromUserName);
            }else if("5".equals(content)){
                message = MessageUtil.initMusicMessage(toUserName,fromUserName);
            } else if ("?".equals(content) || "？".equals(content)) {
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
            }
        }else if(MessageUtil.MESSAGE_EVNET.equals(msgType)){
            String eventType = map.get("Event");
            if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
            }else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
            }else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
                String url = map.get("EventKey");
                message = MessageUtil.initText(toUserName, fromUserName, url);
            }else if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                String key = map.get("EventKey");
                message = MessageUtil.initText(toUserName, fromUserName, key);
            }
        }else if(MessageUtil.MESSAGE_LOCATION.equals(message)){
            String label = map.get("Label");
            message = MessageUtil.initText(toUserName, fromUserName, label);
        }
        System.out.println(message);
        out.print(message);
    }

}
