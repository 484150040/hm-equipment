package com.hm.digital.equipment.controller.dahua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.digital.common.utils.ResultData;
import com.hm.digital.equipment.controller.dahua.service.KeepLoginService;

import lombok.SneakyThrows;

@Controller
public class KeepLoginController {

  @Autowired
  private KeepLoginService keepLoginService;

  /**
   * 保活会话
   *
   * @return
   */
  @SneakyThrows
  @RequestMapping("/keepLogin")
  @ResponseBody
  public ResultData keepLogin() {
    return keepLoginService.keepalive();
  }

}
