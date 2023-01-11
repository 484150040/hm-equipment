package com.hm.digital.equipment.controller.dahua.service.impl;

import org.springframework.stereotype.Service;

import com.hm.digital.common.utils.ResultData;
import com.hm.digital.equipment.controller.dahua.dto.BaseUserInfo;
import com.hm.digital.equipment.controller.dahua.service.KeepLoginService;
import com.hm.digital.equipment.util.HttpEnum;
import com.hm.digital.equipment.util.HttpTestUtils;

import lombok.SneakyThrows;

@Service
public class KeepLoginServiceimpl extends BaseUserInfo implements KeepLoginService {

  public static final String ACTION = "/videoService/accounts/token/keepalive";

  public  String keepAlive(String ip, int port, String token) throws Exception{
    String content = "{\"token\":\""+token+"\"}";
    String response= HttpTestUtils.httpRequest(HttpEnum.PUT,ip,port,ACTION,token,content);
    return response;
  }
  @SneakyThrows
  @Override
  public ResultData keepalive() {
    while (true) {
      String rsp = keepAlive(ip, Integer.valueOf(port), token);
      System.out.println(rsp);
      Thread.sleep(110000);
      break;
    }
    return ResultData.success();
  }
}
