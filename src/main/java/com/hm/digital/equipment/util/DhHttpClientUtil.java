package com.hm.digital.equipment.util;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.hutool.json.JSONUtil;
import com.dahuatech.icc.oauth.http.DefaultClient;
import com.dahuatech.icc.oauth.http.IClient;
import com.dahuatech.icc.oauth.model.v202010.GeneralRequest;
import com.dahuatech.icc.oauth.model.v202010.GeneralResponse;
import com.hm.digital.common.enums.ConfigEnum;
import com.hm.digital.equipment.dahua.feign.ConfigsFeignBiz;
import com.hm.digital.inface.biz.ConfigsService;
import com.hm.digital.inface.entity.Config;

import lombok.SneakyThrows;

@Component
public class DhHttpClientUtil {

  //  @Value("${icc.host}")
  private static String host;
  //  @Value("${icc.clientId}")
  private static String clientId;
  //  @Value("${icc.clientSecret}")
  private static String clientSecret;

  @Autowired
  public ConfigsFeignBiz configsFeignBiz;

  @PostConstruct
  public void init() {
    try {
      host = configsFeignBiz.configList(getCofig(ConfigEnum.ICC_HOST.getKey())).get(0).getValue();
      clientId = configsFeignBiz.configList(getCofig(ConfigEnum.ICC_CLIENTID.getKey())).get(0).getValue();
      clientSecret = configsFeignBiz.configList(getCofig(ConfigEnum.ICC_CLIENTSECRET.getKey())).get(0).getValue();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

  }

  private Config getCofig(String config) {
    Config configVO = new Config();
    configVO.setType(config);
    configVO.setUniverse("1");
    return configVO;
  }

  /**
   * 大华post请求
   *
   * @param url
   * @param param
   * @return
   */
  @SneakyThrows
  public String post(String url, Map<String, Object> param) {
    IClient iClient = new DefaultClient(host, clientId, clientSecret);
    GeneralRequest generalRequest =
        new GeneralRequest(url, Method.POST);
    generalRequest.body(JSONUtil.toJsonStr(JSONUtil.parseObj(param)));
    GeneralResponse subscribeResponse =
        iClient.doAction(generalRequest, generalRequest.getResponseClass());
    return subscribeResponse.toString();
  }

  /**
   * 大华get请求地址
   *
   * @param url
   * @param param
   * @return
   */
  @SneakyThrows
  public String get(String url, Map<String, Object> param) {
    IClient iClient = new DefaultClient(host, clientId, clientSecret);
    GeneralRequest generalRequest =
        new GeneralRequest(url, Method.GET);
    generalRequest.form(param);
    GeneralResponse subscribeResponse =
        iClient.doAction(generalRequest, generalRequest.getResponseClass());
    return subscribeResponse.toString();
  }

  /**
   * 大华get请求地址
   *
   * @param url
   * @param param
   * @return
   */
  @SneakyThrows
  public String get(String url, String param) {
    IClient iClient = new DefaultClient(host, clientId, clientSecret);
    GeneralRequest generalRequest =
        new GeneralRequest(url, Method.GET);
    generalRequest.get(generalRequest.getUrl() + "/" + param);
    GeneralResponse subscribeResponse =
        iClient.doAction(generalRequest, generalRequest.getResponseClass());
    return subscribeResponse.toString();
  }
}
