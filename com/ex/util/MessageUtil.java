package com.ex.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ex.po.Image;
import com.ex.po.ImageMessage;
import com.ex.po.Music;
import com.ex.po.MusicMessage;
import com.ex.po.News;
import com.ex.po.NewsMessage;
import com.ex.po.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_SCANCODE = "scancode_push";

	/*
	 * ��xmlתΪmap����
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request)
			throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();

		InputStream is = request.getInputStream();
		Document doc = reader.read(is);

		Element root = doc.getRootElement();

		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		is.close();
		return map;
	}

	/*
	 * ���ı���Ϣת��Ϊ��Ϣ����
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xStream = new XStream();
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}

	public static String initText(String toUserName, String fromUserName,
			String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		System.out.println(textMessageToXml(text));
		return textMessageToXml(text);
	}

	/*
	 * ���˵�
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("��ӭ���Ĺ�ע���밴�ղ˵���ʾ���в�����\n\n");
		sb.append("1���γ̽���\n");
		sb.append("2��Ľ��������\n");
		sb.append("3��������ѯ\n");
		sb.append("4.�ɼ���ѯ\n\n");
		sb.append("�ظ�?�����˲˵���");
		return sb.toString();
	}

	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("���׿γ̽���΢�Ź��ںſ�������Ҫ�漰���ںŽ��ܡ��༭ģʽ���ܡ�����ģʽ���ܵ�");
		return sb.toString();
	}

	public static String secondMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("haaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahha");
		return sb.toString();
	}
	
	public static String weatherMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("������ѯʾ��"+"\n");
		sb.append("��������"+"\n");
		sb.append("changzhou����"+"\n\n");
		sb.append("�ظ�?��ʾ�˵���");
		return sb.toString();
	}
	
	public static String scoreMenu(){
		StringBuffer sb=new StringBuffer();
		sb.append("�ɼ���ѯʾ��"+"\n");
		sb.append("121101501040700 *******"+"\n\n");
		sb.append("�ظ�?��ʾ�˵���");
		return sb.toString();
	}

	/*
	 * ͼ����ϢתΪxml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		XStream xStream = new XStream();
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", new News().getClass());
		return xStream.toXML(newsMessage);
	}

	/*
	 * ��ͼƬ��ϢתΪxml
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}

	/*
	 * ��������ϢתΪxml
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/*
	 * ͼ����Ϣ����װ
	 */
	public static String initNewsMessage(String toUserName, String fromUserName) {
		String message = null;
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		News news = new News();
		news.setTitle("Ľ��������");
		;
		news.setDescription("111111111111111111111111111111111");
		news.setPicUrl("http://xxcc.tunnel.2bdata.com/Weixin/image/ali.jpg");
		news.setUrl("www.baidu.com");

		newsList.add(news);

		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());

		message = newsMessageToXml(newsMessage);
		return message;
	}

	/*
	 * ��װͼƬ��Ϣ
	 */
	public static String initImageMessage(String toUserName, String fromUserName) {
		String message = null;
		Image image = new Image();
		image.setMediaId("4n5mGLtbphZRUt3I4FneP0zmeaERIbBXmPXjRDn-iafj-5EiZ4g_cCmvYgu5yE9S");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}

	/*
	 * ��װ������Ϣ
	 */
	public static String initMusicMessage(String toUserName, String fromUserName) {
		String message = null;
		Music music = new Music();
		music.setThumbMediaId("MB7AwtSKv83gQneV8Z15FpvOB7O_botTFFlO1kpMQFhX4mLkguIaLw6XsdlYlYUT");
		music.setTitle("see you again");
		music.setDescription("�ٶ��뼤��7������");
		music.setMusicUrl("http://xxcc.tunnel.2bdata.com/Weixin/resource/SeeYouAgain.mp3");
		music.setHQMusicUrl("http://xxcc.tunnel.2bdata.com/Weixin/resource/SeeYouAgain.mp3");

		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(toUserName);
		musicMessage.setToUserName(fromUserName);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMusic(music);
		message = musicMessageToXml(musicMessage);
		return message;
	}

}
