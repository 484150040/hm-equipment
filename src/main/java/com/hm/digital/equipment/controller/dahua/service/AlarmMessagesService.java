package com.hm.digital.equipment.controller.dahua.service;

import com.hm.digital.common.utils.ResultData;
import com.hm.digital.equipment.controller.dahua.vo.AlarmMessagesVo;

public interface AlarmMessagesService {
  ResultData alarmMessages(AlarmMessagesVo alarmMessagesVo);
}
