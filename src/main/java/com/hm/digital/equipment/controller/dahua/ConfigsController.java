package com.hm.digital.equipment.controller.dahua;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.hm.digital.common.enums.ErrorCode;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.inface.biz.ConfigsService;
import com.hm.digital.inface.entity.Config;

import lombok.SneakyThrows;

@Controller
public class ConfigsController {

  @Autowired
  private ConfigsService configsService;


  /**
   * 查询配置信息
   *
   * @param config
   * @return
   */
  @SneakyThrows
  @RequestMapping("/configList")
  @ResponseBody
  public ResultData configList(Config config) {
    List<Config> configs = configsService.getValue(config);
    if (CollectionUtils.isEmpty(configs)) {
      return ResultData.error(ErrorCode.NULL_OBJ.getValue(), ErrorCode.NULL_OBJ.getDesc());
    }
    return ResultData.success(configs);
  }
}
