package com.ex.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Properties;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.python.antlr.PythonParser.return_stmt_return;

import com.ex.menu.Button;
import com.ex.menu.ClickButton;
import com.ex.menu.Menu;
import com.ex.menu.ViewButton;
import com.ex.po.AccessToken;
import com.ex.robot.entity.RequestInfo;
import com.ex.weather.util.WeatherUtil;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

public class WeixinUtil {
	private static final String APPID = "wx64e7576f83b9c0f6";
	private static final String APPSECRET = "3b66dd1846925dba8f84bcca7eb2c0d7";

	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	private static final String CREATE_MENU_URL_STRING = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	private static final String WEATHER_QUERY_URL ="https://api.thinkpage.cn/v3/weather/now.json?key=goprgwiqefufagah&location=LOCATION&language=zh-Hans&unit=c";
	
	private static final String ROBOT_KEY="e257e62d95b642d7bb70b781d512164a";//�����˵���Key
	
	private static final String ROBOT_URL = "http://www.tuling123.com/openapi/api";//�����˵���url
	/*
	 * get����
	 */
	public static JSONObject doGetStr(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resault = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.fromObject(resault);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	/*
	 * post����
	 */
	public static JSONObject doPostStr(String url, String outStr) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		try {
			httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String resault = EntityUtils
					.toString(response.getEntity(), "UTF-8");
			jsonObject = JSONObject.fromObject(resault);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

	/*
	 * ��ȡtoken�����������һ�����󳬹�7200�룬�����´�΢�ŷ�������ȡ��δ������ӱ����ļ���ȡ
	 */
	public static AccessToken getAccessToken() {
		AccessToken token = new AccessToken();

		Properties prop = new Properties();
		try {
			InputStream is = WeixinUtil.class
					.getResourceAsStream("/com/ex/util/token.properties");
			prop.load(is);
			long now = new Date().getTime();
			long time = Long.parseLong(prop.getProperty("createTime"));
			System.out.println(time);
			if (now - time > 7200000) {
				String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace(
						"APPSECRET", APPSECRET);
				JSONObject jsonObject = doGetStr(url);
				if (jsonObject != null) {
					token.setToken(jsonObject.getString("access_token"));
					token.setExpiresIn(jsonObject.getInt("expires_in"));
				}
				prop.setProperty("token", jsonObject.getString("access_token"));
				prop.setProperty("expiresIn",
						jsonObject.getString("expires_in"));
				prop.setProperty("createTime", String.valueOf(now));
				FileOutputStream out = new FileOutputStream(
						"src/com/ex/util/token.properties", true);
				prop.store(out, "");
				out.close();
			} else {
				token.setToken(prop.getProperty("token"));
				token.setExpiresIn(Integer.parseInt(prop
						.getProperty("expiresIn")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return token;
	}

	/*
	 * ��װ�˵�
	 */
	public static Menu initMenu() {
		Menu menu = new Menu();
		ClickButton button11 = new ClickButton();
		button11.setName("click�˵�");
		button11.setType("click");
		button11.setKey("11");// ��ȡ��Ψһƾ֤

		ViewButton button21 = new ViewButton();
		button21.setName("view�˵�");
		button21.setType("view");
		button21.setUrl("https://www.baidu.com");

		ClickButton button31 = new ClickButton();
		button31.setName("ɨ���¼�");
		button31.setType("scancode_push");
		button31.setKey("31");

		ClickButton button32 = new ClickButton();
		button32.setName("����λ��");
		button32.setType("location_select");
		button32.setKey("32");

		Button button = new Button();
		button.setName("�˵�");
		button.setSub_button(new Button[] { button31, button32 });

		menu.setButton(new Button[] { button11, button21, button });

		return menu;
	}

	/*
	 * �ļ��ϴ�
	 */
	public static String upload(String filePath, String accessToken, String type)
			throws IOException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("�ļ�������");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace(
				"TYPE", type);
		URL urlObj = new URL(url);
		// ����
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		// ��������ͷ��Ϣ
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// ���ñ߽�
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-type", "multipart/form-data;boundary="
				+ BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition:form-data;name=\"file\";filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("UTF-8");

		// ��������
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// �����ͷ
		out.write(head);

		// �ļ����Ĳ���
		// ���ļ������ļ��ķ�ʽ���뵽url��
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// ��β����
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// ����������ݷָ���

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;

		try {
			// ����BufferedReader����������ȡURL��Ӧ
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObject = JSONObject.fromObject(result);
		System.out.println(jsonObject);
		String typeName = "media_id";
		if (!"image".equals(type)) {
			typeName = type + "_media_id";
		}
		String mediaId = jsonObject.getString(typeName);
		return mediaId;
	}

	public static int createMenu(String token, String menu) {
		int result = -1;
		String url = CREATE_MENU_URL_STRING.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if (jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	/*
	 * ��ѯ�˵�
	 */
	public static JSONObject queryMenu(String token){
		String url=QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doGetStr(url);
		return jsonObject;
	}
	/*
	 * ɾ���˵�
	 */
	public static int deleteMenu(String token){
		int result=-1;
		String url=DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject=doGetStr(url);
		if(jsonObject !=null){
			result=jsonObject.getInt("errcode");
		}
		return result;
	}
	/*
	 * ������ѯ,�����ѯ���Ի᷵�أ�{"status":"The location can not be found."}
	 */
	public static String queryWeather(String location){
		String url=WEATHER_QUERY_URL.replace("LOCATION", location);
		String info;
		JSONObject jsonObject=doGetStr(url);
		if(jsonObject.get("status")==null){
			info=WeatherUtil.getInfo(jsonObject);
		} else{
			info="�͹٣�����ѯ�����Ʋ��ԣ������²�ѯ~~";
		}
		return info;
	}
	/*
	 * ���������죻��ª�棬Ӧ��Ҫ��post��ʽ�����ص��������Ϳ���Ҫ��װʵ����
	 */
	public static String getRobotResponse(RequestInfo requestInfo){
		requestInfo.setKey(ROBOT_KEY);
		JSONObject jsonObject=JSONObject.fromObject(requestInfo);
		JSONObject json=doPostStr(ROBOT_URL, jsonObject.toString());
		String result=json.getString("text");
		return result;
	}
	
	/*
	 * ����python��óɼ����ַ���
	 */
	public static String getScoreByPython(String content){
		String[] strArr=content.trim().replace("��ѯ", "").trim().split("(\\s+)");
		String result=null;
		if(strArr.length==2){
			String name=strArr[0];
			String password=strArr[1];
			String str="";
			try {
				Process p = Runtime.getRuntime().exec("python C:\\Users\\jm\\Desktop\\PaChong.py "+name+" "+password);
				InputStream is = p.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is,Charset.forName("utf-8")));
				String line;
				while((line = reader.readLine())!= null){
					str=str+line+"\n";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result=str.replaceAll("[\\s{}\"]", "").replace(",", "\n");
		} else {
			result="�͹٣�����ѯ�����Ʋ��ԣ������²�ѯ~~";
		}
		
		return result;
	}
	
	
}
