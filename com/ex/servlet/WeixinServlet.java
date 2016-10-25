package com.ex.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.ex.po.TextMessage;
import com.ex.robot.entity.RequestInfo;
import com.ex.util.CheckUtil;
import com.ex.util.MessageUtil;
import com.ex.util.WeixinUtil;

public class WeixinServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out=resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out=resp.getWriter();
		try {
			Map<String, String> map=MessageUtil.xmlToMap(req);
			String fromUserName=map.get("FromUserName");
			String toUserName=map.get("ToUserName");
			String msgType=map.get("MsgType");
			String content=map.get("Content");
			
			String message=null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}else if("2".equals(content)){
					message=MessageUtil.initNewsMessage(toUserName, fromUserName);
				}else if("?".equals(content)||"？".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else if("3".equals(content)){
					//message=MessageUtil.initImageMessage(toUserName, fromUserName);
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.weatherMenu());
				}else if("4".equals(content)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.scoreMenu());
				}else if("10".equals(content)){
					message=MessageUtil.initMusicMessage(toUserName, fromUserName);
				}else if(content.endsWith("天气")){
					String city=content.replace("天气", "").trim();
					message=MessageUtil.initText(toUserName, fromUserName, WeixinUtil.queryWeather(city));
				}else if(content.startsWith("查询")){
					message=MessageUtil.initText(toUserName, fromUserName, WeixinUtil.getScoreByPython(content));
				}else {
					RequestInfo requestInfo=new RequestInfo();
					requestInfo.setInfo(content);
					requestInfo.setUserId(fromUserName);
					message=MessageUtil.initText(toUserName, fromUserName, WeixinUtil.getRobotResponse(requestInfo));
				}
				
				
			} else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String evenType=map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(evenType)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else if(MessageUtil.MESSAGE_CLICK.equals(evenType)){
					message=MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else if(MessageUtil.MESSAGE_VIEW.equals(evenType)){
					String url=map.get("EventKey");
					message=MessageUtil.initText(toUserName, fromUserName, url);
				}else if(MessageUtil.MESSAGE_SCANCODE.equals(evenType)){
					String key=map.get("EventKey");
					message=MessageUtil.initText(toUserName, fromUserName, key);
				}
			} else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				String label=map.get("Label");
				message=MessageUtil.initText(toUserName, fromUserName, label);
			}
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally{
			out.close();
		}
	}
}
