package com.ex.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;



import java.util.HashMap;
import java.util.Map;

import org.python.antlr.PythonParser.funcdef_return;
import org.python.antlr.PythonParser.return_stmt_return;

import net.sf.json.JSONObject;

import com.ex.po.AccessToken;
import com.ex.util.WeixinUtil;

public class WeixinTest {
	public static void main(String[] args) {
		/*AccessToken token=WeixinUtil.getAccessToken();
		System.out.println("Ʊ��"+token.getToken());
		System.out.println("��Чʱ��"+token.getExpiresIn());*/
		
		/*String path="C:\\Users\\jm\\Downloads\\test.png";
		try {
			String medidId=WeixinUtil.upload(path, token.getToken(), "thumb");
			System.out.println(medidId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*String menu=JSONObject.fromObject(WeixinUtil.initMenu()).toString();
		int result=WeixinUtil.createMenu(token.getToken(), menu);
		if(result==0){
			System.err.println("�����˵��ɹ�");
		} else {
			System.out.println("�����룺"+result);
		}*/
		/*JSONObject jsonObject=WeixinUtil.queryMenu(token.getToken());
		System.out.println(jsonObject);*/
		
		/*int result=WeixinUtil.deleteMenu(token.getToken());
		if(result==0){
			System.out.println("ɾ���˵��ɹ�");
		} else {
			System.out.println("�����룺"+result);
		}*/
		
		/*String info=WeixinUtil.queryWeather("����");
		System.out.println(info);*/
		/*String str="��������";
		if(str.endsWith("����")){
			String city=str.replace("����", "").trim();
			System.out.println(city);
		}*/
		String str=null;
		try {
			//Process p1 = Runtime.getRuntime().exec("ping 127.0.0.1");
			Process p = Runtime.getRuntime().exec("python C:\\Users\\jm\\Desktop\\PaChong.py 121101501040700 240417");
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
		System.out.println(str.replaceAll("[\\s{}\"]", "").replace(",", "\n"));
		
	}
	
}
