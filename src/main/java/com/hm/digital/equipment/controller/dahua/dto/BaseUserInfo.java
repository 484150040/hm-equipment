package com.hm.digital.equipment.controller.dahua.dto;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hm.digital.common.enums.ConfigEnum;
import com.hm.digital.inface.biz.ConfigsService;
import com.hm.digital.inface.entity.Config;

import lombok.Data;

public  class BaseUserInfo {
    protected static String ip;
    protected static String port;
    protected static String userName;
    protected static String password;
    protected static String token;

  @Autowired
  public ConfigsService configsServices;

  @PostConstruct
  public void init() {
    ip =  configsServices.getValue(getCofig(ConfigEnum.DH_IP.getKey())).get(0).getValue();
    port =  configsServices.getValue(getCofig(ConfigEnum.DH_PORT.getKey())).get(0).getValue();
    userName =  configsServices.getValue(getCofig(ConfigEnum.DH_USERNAME.getKey())).get(0).getValue();
    password =  configsServices.getValue(getCofig(ConfigEnum.DH_PASSWORD.getKey())).get(0).getValue();
    token =  configsServices.getValue(getCofig(ConfigEnum.DH_TOKEN.getKey())).get(0).getValue();
    if (StringUtils.isEmpty(token)) {
      System.out.println("没有进行登录，请调用Login的main方法进行登录");
    }
  }

  private Config getCofig(String config) {
    Config configVO = new Config();
    configVO.setType(config);
    configVO.setUniverse("1");
    return configVO;
  }
   /* static {
        ResourceBundle bundle = ResourceBundle.getBundle("baseinfo");
        ip = bundle.getString("ip");
        port = bundle.getString("port");
        userName = bundle.getString("userName");
        password = bundle.getString("password");
        //第一次获取到token之后保存到token.properties文件中，之后直接读取properties文件中token即可，防止重复申请token
        ResourceBundle tokenbundle = ResourceBundle.getBundle("token");
        if (!("").equals(tokenbundle.getString("token"))) {
            token = tokenbundle.getString("token");
        }else {
            System.out.println("没有进行登录，请调用Login的main方法进行登录");
        }
    }*/
}
