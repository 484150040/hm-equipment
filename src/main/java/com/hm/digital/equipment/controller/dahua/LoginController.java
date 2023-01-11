package com.hm.digital.equipment.controller.dahua;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.digital.equipment.controller.dahua.dto.BaseUserInfo;
import com.hm.digital.equipment.util.HttpTestUtils;

import lombok.SneakyThrows;

@Controller
public class LoginController extends BaseUserInfo {

  /**
   * 登录
   *
   * @return
   */
  @SneakyThrows
  @RequestMapping("/login")
  @ResponseBody
  public String login() {
    token = HttpTestUtils.getToken(ip, Integer.valueOf(port), userName, password);
    return token;
  }

}
