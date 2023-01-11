package com.hm.digital.equipment.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.digital.equipment.controller.dahua.vo.DroneVo;
import com.hm.digital.equipment.util.DhHttpClientUtil;
import com.huaweicloud.sdk.core.auth.AbstractCredentials;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.model.ShowDeviceMessageRequest;
import com.huaweicloud.sdk.iotda.v5.model.ShowDeviceMessageResponse;
import com.huaweicloud.sdk.iotda.v5.region.IoTDARegion;
import com.huaweicloud.sdk.vpc.v3.VpcClient;
import com.huaweicloud.sdk.vpc.v3.model.ListVpcsRequest;
import com.huaweicloud.sdk.vpc.v3.model.ListVpcsResponse;

import lombok.SneakyThrows;

@Controller
public class TestController {


/*
  @Value("${icc.host}")
  private String host;
  @Value("${icc.clientId}")
  private String clientId;
  @Value("${icc.clientSecret}")
  private String clientSecret;
*/

  @Autowired
  private DhHttpClientUtil dewHttpClientUtil;

  @SneakyThrows
  @RequestMapping("/tttt")
  @ResponseBody
  public String tttt(@RequestBody Map<String,Object> param,@RequestParam String url) {
    return dewHttpClientUtil.post(url,param);
  }

  @SneakyThrows
  @RequestMapping("/tttt1/{id}")
  @ResponseBody
  public String tttt1(@PathVariable String id,@RequestParam String url) {
    return dewHttpClientUtil.get(url,id);
  }
  @SneakyThrows
  @RequestMapping("/tttt2")
  @ResponseBody
  public String tttt2(@RequestBody Map<String, Object> param,@RequestParam String url) {
    return dewHttpClientUtil.get(url,param);
  }
  public static void listVpcs(VpcClient client) {
    try {
      // 实例化ListVpcsRequest请求对象，调用listVpcs接口
      ListVpcsResponse listVpcsResponse = client.listVpcs(new ListVpcsRequest().withLimit(1));
      // 输出json格式的字符串响应
    } catch (ServiceResponseException e) {
    }
  }

  public static void main(String[] args) {
    String ak = "VIGDQD9NKO1IKCBQAIYT";
    String sk = "thsgelJyYFXnpaVAAytQgVqnygCFCUsndpy4Wt4Q";

    ICredential auth = new BasicCredentials()
        .withDerivedPredicate(AbstractCredentials.DEFAULT_DERIVED_PREDICATE) // 在衍生ak/sk认证场景下使用
        .withAk(ak)
        .withSk(sk);

    IoTDAClient client = IoTDAClient.newBuilder()
        .withCredential(auth)
        .withRegion(IoTDARegion.valueOf("cn-north-4"))
        .build();
    ShowDeviceMessageRequest request = new ShowDeviceMessageRequest();
    request.withDeviceId("62d50a5a4c7c4e3646bb4a9d_kaifa");
    request.withMessageId("9dc41dd8-a0c0-4cc4-b433-4b019cc47289");
    try {
      ShowDeviceMessageResponse response = client.showDeviceMessage(request);
      System.out.println(response.toString());
    } catch (ConnectionException e) {
      e.printStackTrace();
    } catch (RequestTimeoutException e) {
      e.printStackTrace();
    } catch (ServiceResponseException e) {
      e.printStackTrace();
      System.out.println(e.getHttpStatusCode());
      System.out.println(e.getErrorCode());
      System.out.println(e.getErrorMsg());
    }
  }

}
