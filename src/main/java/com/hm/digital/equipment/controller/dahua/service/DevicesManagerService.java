package com.hm.digital.equipment.controller.dahua.service;

import com.hm.digital.common.utils.ResultData;

public interface DevicesManagerService {
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
  ResultData orgTree(String nodeType, String typeCode, String page, String pageSize, String orgCode);

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
  ResultData getDevTree(String nodeType, String typeCode, String page, String pageSize, String orgCode);

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
  ResultData getOrgTree(String nodeType, String typeCode, String page, String pageSize, String orgCode);
}
