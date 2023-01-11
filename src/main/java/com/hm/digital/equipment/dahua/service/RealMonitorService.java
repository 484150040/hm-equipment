package com.hm.digital.equipment.dahua.service;

import com.hm.digital.common.utils.ResultData;

public interface RealMonitorService {
  ResultData realMonitor(String channelId);

  ResultData playback(String channelCode, String beginTime, String endTime, String scheme, String location,
      String resource);
}
