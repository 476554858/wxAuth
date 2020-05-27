package wx.auth.wxAuth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wx.auth.wxAuth.entity.AccessToken;
import wx.auth.wxAuth.util.WeixinUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxAuthApplicationTests {

	@Test
	public void contextLoads() throws Exception{
		String path = "http://api.map.baidu.com/routematrix/v2/riding?origins=40.45,116.34|40.54,116.35&destinations=40.34,116.45|40.35,116.46&ak=BFvk7std1NNdQ7U8A4noxgiZYwxHqCeO";
		//Map<String, String> map = WxAuthApplicationTests.getLatitude("",path);

		AccessToken token = WeixinUtil.getAccessToken();
		System.out.println("票据"+token.getToken());
		System.out.println("有效时间"+token.getExpiresIn());

//		String myPath = "C:/dog.jpg";
//		String mediaId = WeixinUtil.upload(myPath, token.getToken(), "image");
//		System.out.println("mediaId:"+mediaId);
		//image   3HY-tHyErsoijKX0buBeo476BTZeUV7pnl5FdOFtSqpA5SZwgxdCZt5OWmMxaVxz
		//thumb   3HY-tHyErsoijKX0buBeozG_LZEefw8K7AKcrpLm6-tTiG4mfU017cQFoahKLQHo

		String menu = net.sf.json.JSONObject.fromObject(WeixinUtil.initMenu()).toString();
		int result = WeixinUtil.createMenu(token.getToken(), menu);
		if(result==0){
			System.out.println("创建菜单成功");
		}else{
			System.out.println("错误码："+result);
		}
	}


	public static Map<String, String> getLatitude(String address,String url) {
		try {
// 将地址转换成utf-8的16进制
			address = URLEncoder.encode(address, "UTF-8");
// 如果有代理，要设置代理，没代理可注释
// System.setProperty("http.proxyHost","192.168.172.23");
// System.setProperty("http.proxyPort","3209");
			URL resjson = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					resjson.openStream()));
			String res;
			StringBuilder sb = new StringBuilder("");
			while ((res = in.readLine()) != null) {
				sb.append(res.trim());
			}
			in.close();
			String str = sb.toString();
			System.out.println("return json+++++++++++++++++++:" + str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*public static void main(String args[]) {
		Map<String, String> map = WxAuthApplicationTests.getLatitude("成都 高新西区西区大道1398号");
		if (null != map) {
			System.out.println(map.get("lng"));
			System.out.println(map.get("lat"));
		}
	}*/

}
