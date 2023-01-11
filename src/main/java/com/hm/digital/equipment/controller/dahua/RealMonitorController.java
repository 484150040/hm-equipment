package com.hm.digital.equipment.controller.dahua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.digital.common.utils.ResultData;
import com.hm.digital.equipment.controller.dahua.service.RealMonitorService;

import lombok.SneakyThrows;

@Controller
public class RealMonitorController {

  @Autowired
  private RealMonitorService realMonitorService;

  /**
   * 获取实时监控视频URI
   *
   * @param channelId
   * @return
   */
  @SneakyThrows
  @RequestMapping("/realMonitor")
  @ResponseBody
  public ResultData realMonitor(String channelId) {
    return realMonitorService.realMonitor(channelId);
  }

  /**
   * 获取回放URI
   *
   *  注意：使用前请根据实际情况修改请求的参数，修改完后调用main方法即可
   * 	需要修改的地方为content中channelCode，resource，beginTime等参数
   * 	本demo的content中，只是给出了一个例子，可以根据参数选填的情况，自由组合content中参数内容
   */
  @SneakyThrows
  @RequestMapping("/playback")
  @ResponseBody
  public ResultData playback(String channelCode, String beginTime, String endTime, String scheme, String location,
      String resource) {
    return realMonitorService.playback(channelCode, beginTime, endTime, scheme, location, resource);
  }
}
