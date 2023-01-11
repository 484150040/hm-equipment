package com.hm.digital.equipment.dahua.service;

import com.hm.digital.common.utils.ResultData;

public interface SubscribeService {
  ResultData subscribe(String ipa,Integer porta,String msg,String msgNum,String type);
}
