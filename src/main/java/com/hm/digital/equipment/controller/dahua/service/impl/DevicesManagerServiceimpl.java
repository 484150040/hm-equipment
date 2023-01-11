package com.hm.digital.equipment.controller.dahua.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.equipment.controller.dahua.dto.BaseUserInfo;
import com.hm.digital.equipment.controller.dahua.service.DevicesManagerService;
import com.hm.digital.equipment.util.HttpEnum;
import com.hm.digital.equipment.util.HttpTestUtils;

import lombok.SneakyThrows;

@Service
public class DevicesManagerServiceimpl extends BaseUserInfo implements DevicesManagerService {

  public static final String ACTION = "/videoService/devicesManager/deviceTree";

  public static int a = -1;//机构层级标志

  public static String mark = "";//打印结果的空格标志

  private String getOrgTree(String nodeType, String typeCode, String page, String pageSize, String orgCode,String ip, int port, String token) throws Exception {
    //先获取根组织Id
    /**
     * id : 类型string ，必填。要查询组织的惟一编码。查询根组织时不需要填值
     * nodeType : 类型int ，必填。固定为1,表示组织
     * typeCode : 类型string ，必填。检索类型，查询组织"01"。
     */
    String content = "?id=" + orgCode +
        "&nodeType=" + nodeType +
        "&typeCode=" + typeCode +
        "&page=" + page +
        "&pageSize=" + pageSize;
    String response = HttpTestUtils.httpRequest(HttpEnum.GET, ip, port, ACTION, token, content);
    System.out.println(response);
    Map<String, Object> rsp = new Gson().fromJson(response, Map.class);
    List<Map<String, Object>> arr = (List<Map<String, Object>>) rsp.get("results");
    if (arr != null && arr.size() > 0) {
      String rootId = (String) arr.get(0).get("id");
      System.out.println("Root Org Code is " + rootId);
      //获取根组织下的组织列表，此处为获取一级组织下的组织的id，需要获取二级组织的话，可以将此处的rootid替换为获取的一级组织的id
      content = "?id=" + rootId + "&nodeType=" + nodeType +"&typeCode=" + typeCode +"&page=" + page +"&pageSize=" + pageSize;
      String response1 = HttpTestUtils.httpRequest(HttpEnum.GET, ip, port, ACTION, token, content);
      rsp = new Gson().fromJson(response1, Map.class);
      arr = (List<Map<String, Object>>) rsp.get("results");
      for (Map<String, Object> node : arr) {
        System.out.println("一级组织的orgCode为:" + node.get("orgName") + "=" + node.get("id"));
      }
    }
    return response;
  }
  //此方法可以获取到当前环境所有的组织机构树，和每一个具体的机构所包含的通道
  private String getSub(String nodeType, String typeCode, String page, String pageSize, String code) throws Exception {
    String content =
        "?id=" + code + "&nodeType=" + nodeType + "&typeCode=" + typeCode + "&page=" + page + "&pageSize=" + pageSize;
    String response = HttpTestUtils.httpRequest(HttpEnum.GET, ip, Integer.valueOf(port), ACTION, token, content);
    List<Map<String, Object>> arr = (List<Map<String, Object>>) new Gson().fromJson(response, Map.class).get("results");
    if (arr != null && arr.size() > 0) {
      for (Map<String, Object> node : arr) {
        a++;
        mark += "   ";
        System.out.println(mark + a + "级组织为:" + node.get("orgName") + "=(组织编码)" + node.get("id"));
        getOrgDevTree(nodeType, typeCode, page, pageSize, (String) node.get("id"), mark);
        getSub(nodeType, typeCode, page, pageSize, (String) node.get("id"));
        mark = mark.substring(0, mark.length() - 3);
        a--;
      }
      return response;
    } else {
      return response;
    }
  }


  private String getDevTree(String nodeType, String typeCode, String page, String pageSize, String orgCode, String ip,
      int port, String token) throws Exception {
        /*此处的orgCode为调用分级获取组织接口得到的orgCode
            id : 类型string ，必填。要查询组织的惟一编码。
            nodeType : 类型int ，必填。固定为1,表示组织
            typeCode : 类型string ，必填。检索类型，值不同返回的内容不同。 查询设备"01;1;ALL"。查询设备和通
            道"01;1;ALL;ALL"。仅查询通道"01;0;ALL;ALL"。
        */
    String content = "?id=" + orgCode +
        "&nodeType=" + nodeType +
        "&typeCode=" + typeCode +
        "&page=" + page +
        "&pageSize=" + pageSize;
    String response = HttpTestUtils.httpRequest(HttpEnum.GET, ip, port, ACTION, token, content);
    System.out.println(response);
    Map<String, Object> rsp = new Gson().fromJson(response, Map.class);
    List<Map<String, Object>> arr = (List<Map<String, Object>>) rsp.get("results");
    if (arr != null && arr.size() > 0) {
      for (Map<String, Object> node : arr) {
        //枚举所有类型是通道的编码值
        DecimalFormat df = new DecimalFormat("######0");
        if (3 == Integer.valueOf(df.format(node.get("nodeType")))) {
          System.out.println(node.get("channelName") + "=:" + node.get("channelId"));
        }
      }
    }
    return response;
  }

  //此方法为获取组织和设备树专用方法
  public String getOrgDevTree(String nodeType, String typeCode, String page, String pageSize, String orgCode,
      String mark) throws Exception {
        /*此处的orgCode为调用分级获取组织接口得到的orgCode
        nodeType的取值：查询设备"01;1;ALL"。查询设备和通道"01;1;ALL;ALL"。仅查询通道"01;0;ALL;ALL"。
        */
    String content =
        "?id=" + orgCode + "&nodeType=" + nodeType + "&typeCode=" + typeCode + "&page=" + page + "&pageSize="
            + pageSize;
    String response = HttpTestUtils.httpRequest(HttpEnum.GET, ip, Integer.valueOf(port), ACTION, token, content);
    Map<String, Object> rsp = new Gson().fromJson(response, Map.class);
    List<Map<String, Object>> arr = (List<Map<String, Object>>) rsp.get("results");
    int start = 0;
    int total = 0;
    if (arr != null && arr.size() > 0) {
      for (Map<String, Object> node : arr) {
        //枚举所有类型是通道的编码值
        DecimalFormat df = new DecimalFormat("######0");
        if (3 == Integer.valueOf(df.format(node.get("nodeType")))) {
          start++;
        }
      }
      if (start > 0) {
        System.out.print("            " + mark);
      }
      for (Map<String, Object> node : arr) {
        //枚举所有类型是通道的编码值
        DecimalFormat df = new DecimalFormat("######0");
        if (3 == Integer.valueOf(df.format(node.get("nodeType")))) {
          total++;
          System.out.print("（通道名称）" + node.get("channelName") + "=（通道编码）" + node.get("channelId") + "; ");
        }
      }
      if (total > 0) {
        System.out.println();
      }
    }
    return response;
  }

  @SneakyThrows
  @Override
  public ResultData orgTree(String nodeType, String typeCode, String page, String pageSize, String orgCode) {
    return ResultData.success(getSub(nodeType, typeCode, page, pageSize, orgCode));
  }

  @SneakyThrows
  @Override
  public ResultData getDevTree(String nodeType, String typeCode, String page, String pageSize, String orgCode) {
    return ResultData
        .success(getDevTree(nodeType, typeCode, page, pageSize, orgCode, ip, Integer.valueOf(port), token));
  }
  @SneakyThrows
  @Override
  public ResultData getOrgTree(String nodeType, String typeCode, String page, String pageSize, String orgCode) {
    return ResultData
        .success(getOrgTree(nodeType, typeCode, page, pageSize, orgCode, ip, Integer.valueOf(port), token));
  }

}
