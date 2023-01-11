package com.hm.digital.equipment.controller.dahua.service;

import com.hm.digital.common.utils.ResultData;

public interface RealMonitorService {
  ResultData realMonitor(String channelId);

  ResultData playback(String channelCode, String beginTime, String endTime, String scheme, String location,
      String resource);
}
