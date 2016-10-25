package com.ex.weather.util;

import com.ex.weather.entity.AllResults;
import com.ex.weather.entity.Location;
import com.ex.weather.entity.Now;
import com.ex.weather.entity.Result;

import net.sf.json.JSONObject;

public class WeatherUtil {
	public static String getInfo(JSONObject jsonObject){
		StringBuffer sb=new StringBuffer();
		AllResults allResults=(AllResults) JSONObject.toBean(jsonObject, AllResults.class);
		Result result=allResults.getResults()[0];
		Location location=result.getLocation();
		Now now=result.getNow();
		String lastUpdate=result.getLast_update();
		String city=location.getName();
		String text=now.getText();
		String temperature=now.getTemperature();
		sb.append(city+"ʵʱ������"+"\n");
		sb.append(text+"  "+"�¶ȣ�"+temperature+"��"+"\n");
		sb.append("��������"+lastUpdate);
		return sb.toString();
	}
}
