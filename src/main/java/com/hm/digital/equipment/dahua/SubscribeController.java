package com.hm.digital.equipment.dahua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.digital.common.utils.ResultData;
import com.hm.digital.equipment.dahua.service.SubscribeService;

import lombok.SneakyThrows;

@Controller
public class SubscribeController {

  @Autowired
  private SubscribeService subscribeService;

  /**
   *  获取消息之前需要调用一次该接口，msgId=-1，来获取最新的msgId。第一次请求填-1，
   *      得到最新的msgId(此时不会返回消息内容)。接下来用返回的最新的msgId作为入参，获取对应的消息内容。
   * @param ipa
   * @param porta
   * @param msg
   * @param msgNum
   * @param type
   * @return
   */
  @SneakyThrows
  @RequestMapping("/subscribe")
  @ResponseBody
  public ResultData subscribe(String ipa,Integer porta,String msg,String msgNum,String type) {
    return subscribeService.subscribe(ipa, porta, msg, msgNum, type);
  }
}
