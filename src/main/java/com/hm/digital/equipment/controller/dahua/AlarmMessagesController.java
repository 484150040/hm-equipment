package com.hm.digital.equipment.controller.dahua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.digital.common.utils.ResultData;
import com.hm.digital.equipment.controller.dahua.service.AlarmMessagesService;
import com.hm.digital.equipment.controller.dahua.service.SubscribeService;
import com.hm.digital.equipment.controller.dahua.vo.AlarmMessagesVo;

import lombok.SneakyThrows;

@Controller
public class AlarmMessagesController {

  @Autowired
  private AlarmMessagesService alarmMessagesService;

  /**
   * 分页获取报警消息的列表
   *
   * @param alarmMessagesVo
   * @return
   */
  @SneakyThrows
  @RequestMapping("/alarmMessages")
  @ResponseBody
  public ResultData alarmMessages(@RequestBody AlarmMessagesVo alarmMessagesVo) {
    return alarmMessagesService.alarmMessages(alarmMessagesVo);
  }
}
