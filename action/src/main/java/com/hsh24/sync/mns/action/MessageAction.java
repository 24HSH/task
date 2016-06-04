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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.protocol.ExecutionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hsh24.sync.framework.action.BaseAction;
import com.hsh24.sync.framework.log.Logger4jCollection;
import com.hsh24.sync.framework.log.Logger4jExtend;
import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author JiakunXu
 * 
 */
public class MessageAction extends BaseAction {

	private static final long serialVersionUID = -5409620057199615942L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(MessageAction.class);

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws HttpException
	 */
	public String mnsNotify() throws HttpException, IOException {
		ActionContext ctx = ActionContext.getContext();
		handle1((HttpRequest) ctx.get(ExecutionContext.HTTP_REQUEST),
			(HttpResponse) ctx.get(ExecutionContext.HTTP_RESPONSE));

		return SUCCESS;
	}

	/**
	 * check if this request comes from MNS Server
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
		String str2sign = getSignStr(method, uri, headers);
		// System.out.println(str2sign);
		String signature = headers.get("Authorization");
		byte[] decodedSign = Base64.decodeBase64(signature);
		// get cert, and verify this request with this cert
		try {
			// String cert =
			// "http://mnstest.oss-cn-hangzhou.aliyuncs.com/x509_public_certificate.pem";
			URL url = new URL(cert);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			DataInputStream in = new DataInputStream(conn.getInputStream());
			CertificateFactory cf = CertificateFactory.getInstance("X.509");

			Certificate c = cf.generateCertificate(in);
			PublicKey pk = c.getPublicKey();

			java.security.Signature signetcheck = java.security.Signature.getInstance("SHA1withRSA");
			signetcheck.initVerify(pk);
			signetcheck.update(str2sign.getBytes());
			Boolean res = signetcheck.verify(decodedSign);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("authenticate fail, " + e.getMessage());
			return false;
		}
	}

	/**
	 * build string for sign
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
		sb.append(safeGetHeader(headers, "Content-md5"));
		sb.append("\n");
		sb.append(safeGetHeader(headers, "Content-Type"));
		sb.append("\n");
		sb.append(safeGetHeader(headers, "Date"));
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
	 * process method for NSHandler
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
	private void handle1(HttpRequest request, HttpResponse response) throws HttpException, IOException {
		String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);

		if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
			throw new MethodNotSupportedException(method + " method not supported");
		}

		Header[] headers = request.getAllHeaders();
		Map<String, String> hm = new HashMap<String, String>();
		for (Header h : headers) {
			System.out.println(h.getName() + ":" + h.getValue());
			hm.put(h.getName(), h.getValue());
		}

		String target = request.getRequestLine().getUri();
		System.out.println(target);

		if (request instanceof HttpEntityEnclosingRequest) {
			HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();

			// verify request
			Header certHeader = request.getFirstHeader("x-mns-signing-cert-url");
			if (certHeader == null) {
				System.out.println("SigningCerURL Header not found");
				response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
				return;
			}

			String cert = certHeader.getValue();
			if (cert.isEmpty()) {
				System.out.println("SigningCertURL empty");
				response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
				return;
			}
			cert = new String(Base64.decodeBase64(cert));
			System.out.println("SigningCertURL:\t" + cert);
			logger.debug("SigningCertURL:\t" + cert);

			if (!authenticate(method, target, hm, cert)) {
				System.out.println("authenticate fail");
				logger.warn("authenticate fail");
				response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
				return;
			}

			// parser content of simplified notification
			InputStream is = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			String content = buffer.toString();

			System.out.println("Simplified Notification: \n" + content);
		}

		response.setStatusCode(HttpStatus.SC_NO_CONTENT);
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

	/**
	 * parser /notifications message content
	 * 
	 * @param notify
	 *            , xml element
	 */
	private void paserContent(Element notify) {
		try {
			String topicOwner = safeGetElementContent(notify, "TopicOwner");
			System.out.println("TopicOwner:\t" + topicOwner);
			logger.debug("TopicOwner:\t" + topicOwner);

			String topicName = safeGetElementContent(notify, "TopicName");
			System.out.println("TopicName:\t" + topicName);
			logger.debug("TopicName:\t" + topicName);

			String subscriber = safeGetElementContent(notify, "Subscriber");
			System.out.println("Subscriber:\t" + subscriber);
			logger.debug("Subscriber:\t" + subscriber);

			String subscriptionName = safeGetElementContent(notify, "SubscriptionName");
			System.out.println("SubscriptionName:\t" + subscriptionName);
			logger.debug("SubscriptionName:\t" + subscriptionName);

			String msgid = safeGetElementContent(notify, "MessageId");
			System.out.println("MessageId:\t" + msgid);
			logger.debug("MessageId:\t" + msgid);

			// if PublishMessage with base64 message
			String msg = safeGetElementContent(notify, "Message");
			System.out.println("Message:\t" + new String(Base64.decodeBase64(msg)));
			logger.debug("Message:\t" + new String(Base64.decodeBase64(msg)));

			// if PublishMessage with string message
			// String msg = safeGetElementContent(notify, "Message");
			// System.out.println("Message:\t" + msg);
			// logger.debug("Message:\t" + msg);

			String msgMD5 = safeGetElementContent(notify, "MessageMD5");
			System.out.println("MessageMD5:\t" + msgMD5);
			logger.debug("MessageMD5:\t" + msgMD5);

			String msgPublishTime = safeGetElementContent(notify, "PublishTime");
			Date d = new Date(Long.parseLong(msgPublishTime));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String strdate = sdf.format(d);
			System.out.println("PublishTime:\t" + strdate);
			logger.debug("MessagePublishTime:\t" + strdate);

			String msgTag = safeGetElementContent(notify, "MessageTag");
			if (msgTag != "") {
				System.out.println("MessageTag:\t" + msgTag);
				logger.debug("MessageTag:\t" + msgTag);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			logger.warn(e.getMessage());
		}

	}

	/**
	 * process method for NSHandler
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
	private void handle(final HttpRequest request, final HttpResponse response) throws HttpException, IOException {
		String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);

		if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
			throw new MethodNotSupportedException(method + " method not supported");
		}

		Header[] headers = request.getAllHeaders();
		Map<String, String> hm = new HashMap<String, String>();
		for (Header h : headers) {
			System.out.println(h.getName() + ":" + h.getValue());
			hm.put(h.getName(), h.getValue());
		}

		String target = request.getRequestLine().getUri();
		System.out.println(target);

		if (request instanceof HttpEntityEnclosingRequest) {
			HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();

			// parser xml content
			InputStream content = entity.getContent();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			Element notify = null;
			try {
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.parse(content);
				NodeList nl = document.getElementsByTagName("Notification");
				if (nl == null || nl.getLength() == 0) {
					System.out.println("xml tag error");
					logger.warn("xml tag error");
					response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
					return;
				}
				notify = (Element) nl.item(0);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				logger.warn("xml parser fail! " + e.getMessage());
				response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
				return;
			} catch (SAXException e) {
				e.printStackTrace();
				logger.warn("xml parser fail! " + e.getMessage());
				response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
				return;
			}

			// verify request
			Header certHeader = request.getFirstHeader("x-mns-signing-cert-url");
			if (certHeader == null) {
				System.out.println("SigningCerURL Header not found");
				response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
				return;
			}

			String cert = certHeader.getValue();
			if (cert.isEmpty()) {
				System.out.println("SigningCertURL empty");
				response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
				return;
			}
			cert = new String(Base64.decodeBase64(cert));
			System.out.println("SigningCertURL:\t" + cert);
			logger.debug("SigningCertURL:\t" + cert);

			if (!authenticate(method, target, hm, cert)) {
				System.out.println("authenticate fail");
				logger.warn("authenticate fail");
				response.setStatusCode(HttpStatus.SC_BAD_REQUEST);
				return;
			}
			paserContent(notify);

		}

		response.setStatusCode(HttpStatus.SC_NO_CONTENT);
	}

}
