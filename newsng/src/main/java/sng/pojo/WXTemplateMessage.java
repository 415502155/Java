package sng.pojo;

import java.io.Serializable;
import java.util.Map;

public class WXTemplateMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1824513249723582060L;

	/**
	 * 接收者openid
	 */
	private String touser;

	/**
	 * 模板ID
	 */
	private String template_id;

	/**
	 * <pre>
	   * 跳小程序所需数据，不需跳小程序可不用传该数据
	   * url和miniprogram都是非必填字段，若都不传则模板无跳转；若都传，会优先跳转至小程序。
	   * 开发者可根据实际需要选择其中一种跳转方式即可。当用户的微信客户端版本不支持跳小程序时，将会跳转至url。
	   * </pre>
	 */
	private String url;

	private MiniProgram miniprogram;

	private String topcolor;// 消息头部颜色

	private Map<String, WXTemplateMessageData> data;// 消息内容

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MiniProgram getMiniprogram() {
		return miniprogram;
	}

	public void setMiniprogram(MiniProgram miniprogram) {
		this.miniprogram = miniprogram;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Map<String, WXTemplateMessageData> getData() {
		return data;
	}

	public void setData(Map<String, WXTemplateMessageData> data) {
		this.data = data;
	}

	public WXTemplateMessage() {
		super();
	}

	public WXTemplateMessage(String touser, String template_id, String url, Map<String, WXTemplateMessageData> data) {
		super();
		this.touser = touser;
		this.template_id = template_id;
		this.url = url;
		this.data = data;
	}

	public WXTemplateMessage(String touser, String template_id, String url, MiniProgram miniprogram, String topcolor,
			Map<String, WXTemplateMessageData> data) {
		super();
		this.touser = touser;
		this.template_id = template_id;
		this.url = url;
		this.miniprogram = miniprogram;
		this.topcolor = topcolor;
		this.data = data;
	}
}
