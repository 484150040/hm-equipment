package com.hm.digital.equipment.dahua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.digital.common.utils.ResultData;
import com.hm.digital.equipment.dahua.service.DevicesManagerService;

import lombok.SneakyThrows;

@Controller
public class DevicesManagerController {

  @Autowired
  private DevicesManagerService devicesManagerService;

  /**
   * 此方法只能查询根组织以及一级组织的组织名称和组织编号
   *
   * @param nodeType
   * @param typeCode
   * @param page
   * @param pageSize
   * @param orgCode
   * @return
   */
  @SneakyThrows
  @RequestMapping("/deviceTree")
  @ResponseBody
  public ResultData deviceTree(String nodeType, String typeCode, String page, String pageSize, String orgCode) {
    return devicesManagerService.orgTree(nodeType, typeCode, page, pageSize, orgCode);
  }


  /**
   * 分级获取设备和通道接口
   *
   * @param nodeType
   * @param typeCode
   * @param page
   * @param pageSize
   * @param orgCode
   * @return
   */
  @SneakyThrows
  @RequestMapping("/getDevTree")
  @ResponseBody
  public ResultData getDevTree(String nodeType, String typeCode, String page, String pageSize, String orgCode) {
    return devicesManagerService.getDevTree(nodeType, typeCode, page, pageSize, orgCode);
  }

  /**
   * 分级获得组织
   *
   * @param nodeType
   * @param typeCode
   * @param page
   * @param pageSize
   * @param orgCode
   * @return
   */
  @SneakyThrows
  @RequestMapping("/getOrgTree")
  @ResponseBody
  public ResultData getOrgTree(String nodeType, String typeCode, String page, String pageSize, String orgCode) {
    return devicesManagerService.getOrgTree(nodeType, typeCode, page, pageSize, orgCode);
  }

}
