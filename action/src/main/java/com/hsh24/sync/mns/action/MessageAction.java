package com.hsh24.sync.mns.action;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hsh24.sync.framework.action.BaseAction;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;

/**
 * 
 * @author JiakunXu
 * 
 */
public class MessageAction extends BaseAction {

	private static final long serialVersionUID = -5409620057199615942L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(MessageAction.class);

	public String xml() {
		try {
			handle(this.getServletRequest(), this.getServletResponse(), "XML");
		} catch (Exception e) {
			logger.error(e);
		}

		return SUCCESS;
	}

	public String simplified() {
		try {
			handle(this.getServletRequest(), this.getServletResponse(), "SIMPLIFIED");
		} catch (Exception e) {
			logger.error(e);
		}

		return SUCCESS;
	}

	/**
	 * check if this request comes from MNS Server.
	 * 
	 * @param method
	 *            , http method
	 * @param uri
	 *            , http uri
	 * @param headers
	 *            , http headers
	 * @param cert
	 *            , cert url
	 * @return true if verify pass
	 */
	private Boolean authenticate(String method, String uri, Map<String, String> headers, String cert) {
		// 获取待签名字符串
		String str2sign = getSignStr(method, uri, headers);

		// 对Authorization字段做Base64解码
		String signature = headers.get("authorization");
		byte[] decodedSign = Base64.decodeBase64(signature);

		// 根据URL获取证书，并从证书中获取公钥
		try {
			URL url = new URL(cert);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			DataInputStream in = new DataInputStream(conn.getInputStream());
			CertificateFactory cf = CertificateFactory.getInstance("X.509");

			Certificate c = cf.generateCertificate(in);
			PublicKey pk = c.getPublicKey();

			// 认证
			java.security.Signature signetcheck = java.security.Signature.getInstance("SHA1withRSA");
			signetcheck.initVerify(pk);
			signetcheck.update(str2sign.getBytes());
			return signetcheck.verify(decodedSign);
		} catch (Exception e) {
			logger.error(e);
		}

		return false;
	}

	/**
	 * build string for sign.
	 * 
	 * @param method
	 *            , http method
	 * @param uri
	 *            , http uri
	 * @param headers
	 *            , http headers
	 * @return String fro sign
	 */
	private String getSignStr(String method, String uri, Map<String, String> headers) {
		StringBuilder sb = new StringBuilder();
		sb.append(method);
		sb.append("\n");
		sb.append(safeGetHeader(headers, "content-md5"));
		sb.append("\n");
		sb.append(safeGetHeader(headers, "content-type"));
		sb.append("\n");
		sb.append(safeGetHeader(headers, "date"));
		sb.append("\n");

		List<String> tmp = new ArrayList<String>();
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			if (entry.getKey().startsWith("x-mns-"))
				tmp.add(entry.getKey() + ":" + entry.getValue());
		}
		Collections.sort(tmp);

		for (String kv : tmp) {
			sb.append(kv);
			sb.append("\n");
		}

		sb.append(uri);
		return sb.toString();
	}

	private String safeGetHeader(Map<String, String> headers, String name) {
		if (headers.containsKey(name))
			return headers.get(name);
		else
			return "";
	}

	/**
	 * process method for NSHandler.
	 * 
	 * @param request
	 *            , http request
	 * @param response
	 *            , http responst
	 * @param context
	 *            , http context
	 * @throws HttpException
	 * @throws IOException
	 */
	private void handle(HttpServletRequest request, HttpServletResponse response, String format) throws HttpException,
		IOException {
		String method = request.getMethod().toUpperCase(Locale.ENGLISH);

		if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
			throw new MethodNotSupportedException(method + " method not supported");
		}

		String target = request.getRequestURI();

		Enumeration<?> headers = request.getHeaderNames();
		Map<String, String> hm = new HashMap<String, String>();
		while (headers.hasMoreElements()) {
			String key = (String) headers.nextElement();
			String value = request.getHeader(key);
			hm.put(key, value);
		}

		String cert = request.getHeader("x-mns-signing-cert-url");
		if (StringUtils.isEmpty(cert)) {
			response.setStatus(HttpStatus.SC_BAD_REQUEST);
			return;
		}
		cert = new String(Base64.decodeBase64(cert));

		if (!authenticate(method, target, hm, cert)) {
			response.setStatus(HttpStatus.SC_BAD_REQUEST);
			return;
		}

		if ("XML".equals(format)) {
			// parser xml content
			InputStream content = request.getInputStream();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			Element notify = null;
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.parse(content);
				NodeList nl = document.getElementsByTagName("Notification");
				if (nl == null || nl.getLength() == 0) {
					response.setStatus(HttpStatus.SC_BAD_REQUEST);
					return;
				}
				notify = (Element) nl.item(0);
			} catch (ParserConfigurationException e) {
				logger.error(e);
				response.setStatus(HttpStatus.SC_BAD_REQUEST);
				return;
			} catch (SAXException e) {
				logger.error(e);
				response.setStatus(HttpStatus.SC_BAD_REQUEST);
				return;
			}

			paserContent(notify);
		} else if ("SIMPLIFIED".equals(format)) {
			// parser content of simplified notification
			InputStream is = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			String content = buffer.toString();

			System.out.println("Simplified Notification: \n" + content);
		}
	}

	/**
	 * parser /notifications message content.
	 * 
	 * @param notify
	 *            , xml element
	 */
	private void paserContent(Element notify) {
		try {
			String topicOwner = safeGetElementContent(notify, "TopicOwner");
			System.out.println("TopicOwner:\t" + topicOwner);

			String topicName = safeGetElementContent(notify, "TopicName");
			System.out.println("TopicName:\t" + topicName);

			String subscriber = safeGetElementContent(notify, "Subscriber");
			System.out.println("Subscriber:\t" + subscriber);

			String subscriptionName = safeGetElementContent(notify, "SubscriptionName");
			System.out.println("SubscriptionName:\t" + subscriptionName);

			String msgid = safeGetElementContent(notify, "MessageId");
			System.out.println("MessageId:\t" + msgid);

			// if PublishMessage with base64 message
			// String msg = safeGetElementContent(notify, "Message");
			// System.out.println("Message:\t" + new String(Base64.decodeBase64(msg)));

			// if PublishMessage with string message
			String msg = safeGetElementContent(notify, "Message");
			System.out.println("Message:\t" + msg);

			String msgMD5 = safeGetElementContent(notify, "MessageMD5");
			System.out.println("MessageMD5:\t" + msgMD5);

			String msgPublishTime = safeGetElementContent(notify, "PublishTime");
			Date d = new Date(Long.parseLong(msgPublishTime));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strdate = sdf.format(d);
			System.out.println("PublishTime:\t" + strdate);

			String msgTag = safeGetElementContent(notify, "MessageTag");
			if (msgTag != "") {
				System.out.println("MessageTag:\t" + msgTag);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private String safeGetElementContent(Element element, String tag) {
		NodeList nl = element.getElementsByTagName(tag);
		if (nl != null && nl.getLength() > 0) {
			return nl.item(0).getTextContent();
		} else {
			logger.warn("get " + tag + " from xml fail");
			return "";
		}
	}

}
