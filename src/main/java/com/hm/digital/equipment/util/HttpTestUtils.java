package com.hm.digital.equipment.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

import com.google.gson.Gson;
import com.hm.digital.equipment.controller.dahua.dto.LoginFirst;
import com.hm.digital.equipment.controller.dahua.dto.LoginSecond;

public class HttpTestUtils {

  public static final String ACTION = "/videoService/accounts/authorize";


  public static String httpRequest(HttpEnum method, String ip, int port, String action, String token, String content) {
    String responJson = null;
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse httpResponse = null;
    InputStream inputStream = null;
    KeyStore trustStore = null;
    SSLContext sslcontext = null;
    try {
      trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
      // 设置信任签名
      sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustStrategy() {
        @Override
        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
          return true;
        }
      }).build();
      X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
        @Override
        public void verify(String s, SSLSocket sslSocket) throws IOException {

        }

        @Override
        public void verify(String s, X509Certificate x509Certificate) throws SSLException {

        }

        @Override
        public void verify(String s, String[] strings, String[] strings1) throws SSLException {

        }

        @Override
        public boolean verify(String s, SSLSession sslSession) {
          return true;
        }
      };
      // 设置协议http和https对应的处理socket链接工厂的对象
      Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
          .register("http", PlainConnectionSocketFactory.INSTANCE)
          .register("https", new SSLConnectionSocketFactory(sslcontext, hostnameVerifier))
          .build();

      PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
          socketFactoryRegistry);

      httpClient = HttpClientBuilder.create()
          .setConnectionManager(connectionManager)
          .setRedirectStrategy(new RestRedirectStrategy()).build();
      String proto = port == 8320 ? "https://" : "https://115.236.17.59";
      String uri = proto + ip + ":" + port + action;
      HttpRequestBase httpReq = getRequestEntity(method, token, uri, content);
      httpResponse = httpClient.execute(httpReq);
      inputStream = httpResponse.getEntity().getContent();
      responJson = convertToString(inputStream);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != httpResponse) {
        try {
          httpResponse.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != httpClient) {
        try {
          httpClient.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return responJson;
  }

  private static HttpRequestBase getRequestEntity(HttpEnum method, String token, String uri, String content)
      throws UnsupportedEncodingException {
    switch (method.getNum()) {
      case 1:
        HttpGet httpGet = new HttpGet(uri + content);
        httpGet.addHeader("Content-type", "application/json");
        httpGet.addHeader("X-Subject-Token", token);
        return httpGet;
      case 2:
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("Content-type", "application/json");
        httpPost.addHeader("X-Subject-Token", token);
        httpPost.setEntity(new StringEntity(content, "UTF-8"));
        return httpPost;
      case 3:
        HttpPut httpPut = new HttpPut(uri);
        httpPut.addHeader("Content-type", "application/json");
        httpPut.addHeader("X-Subject-Token", token);
        httpPut.setEntity(new StringEntity(content, "UTF-8"));
        return httpPut;
      case 4:
        HttpDelete httpDelete = new HttpDelete(uri + content);
        httpDelete.addHeader("Content-type", "application/json");
        httpDelete.addHeader("X-Subject-Token", token);
        return httpDelete;
      default:
        System.out.println("请求方法不对");
    }
    return null;
  }

  private static String convertToString(InputStream is) {
    if (is == null) {
      return null;
    }
    BufferedReader bf = null;
    try {
      StringBuilder sb = new StringBuilder();
      String temp = "";
      bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      while ((temp = bf.readLine()) != null) {
        sb.append(temp);
      }
      return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } finally {
      closeStream(bf);
      closeStream(is);
    }
  }

  private static void closeStream(Closeable closeable) {
    if (null != closeable) {
      try {
        closeable.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }


  //第一次登陆，客户端只传用户名，服务端返回realm、readomKey和encryptType信息。
  private static String firstLogin(String ip, int port, String userName) {
    LoginFirst loginFirst = new LoginFirst();
    loginFirst.setClientType("winpc");
    loginFirst.setUserName(userName);
    String rsp = HttpTestUtils.httpRequest(HttpEnum.POST, ip, port, ACTION, "", new Gson().toJson(loginFirst));
    return rsp;
  }
  //第二次登录，客户端根据返回的信息，按照指定的加密算法计算签名，再带着用户名和签名登陆一次。
  private static String secondLogin(String ip, int port, String userName, String password, String realm,
      String randomKey) throws Exception {
    LoginSecond snd = new LoginSecond();
    snd.setUserName(userName);
    snd.setClientType("winpc");
    snd.setRandomKey(randomKey);
    snd.setEncryptType("MD5");
    String signature = snd.calcSignature(password, realm);
    snd.setSignature(signature);
    Gson gson = new Gson();
    String ctx = gson.toJson(snd);
    String rsp = HttpTestUtils.httpRequest(HttpEnum.POST, ip, port, ACTION, "", ctx);
    return rsp;
  }

  @SuppressWarnings("unchecked")
  public static String login(String ip, int port, String userName, String password) throws Exception {
    String response = firstLogin(ip, port, userName);
    Map<String, String> responseMap = new Gson().fromJson(response, Map.class);
    String random = responseMap.get("randomKey");
    String realm = responseMap.get("realm");
    response = secondLogin(ip, port, userName, password, realm, random);
    return response;
  }


  public static String getToken(String ip, int port, String userName, String password) throws Exception {
    String response = "";
    String token = "";
    response = login(ip, port, userName, password);
    Map<String, Object> rsp = new Gson().fromJson(response, Map.class);
    String message = (String) rsp.get("message");
    if (message != null && !"".equals(message)) {
      System.out.println(message);
      throw new Exception("未获取到token");
    }
    token = (String) rsp.get("token");
    if (token == null || "".equals(token)) {
      System.out.println("获取到的token为空");
      throw new Exception("获取到的token为空");
    }
    return token;
  }

}
