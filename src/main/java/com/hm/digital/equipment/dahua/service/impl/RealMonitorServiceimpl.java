package com.hm.digital.equipment.dahua.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.equipment.dahua.dto.BaseUserInfo;
import com.hm.digital.equipment.dahua.service.RealMonitorService;
import com.hm.digital.equipment.util.HttpEnum;
import com.hm.digital.equipment.util.HttpTestUtils;

import lombok.SneakyThrows;

@Service
public class RealMonitorServiceimpl extends BaseUserInfo implements RealMonitorService {
  public static final String ACTION = "/videoService/realmonitor/uri";

  public static final String PLAYBACK = "/videoService/playback/uri";

  //获取实时监控视频URI
  private static String getRealMonitor(String channelId, String ip, int port, String token) throws Exception {
    /**
     * channelId : 类型string ，必填。通道编码。channelID为之前调用分级获取设备通道的channelid，scheme为获取视频类型
     * subType : 类型int ，选填。码流类型，0:主码流、1:辅流1、2:辅流2。默认为0主码流。
     * scheme : 类型string ，选填。协议类型，支持RTSP、FLV_HTTP、HLS三种，默认RTSP。
     */
//    String channelId="JfV2Zni8B1CO6L1PLN87MJ";
    String scheme = "RTSP";
    String content = "?channelId=" + channelId +
        "&scheme=" + scheme;
    String response = HttpTestUtils.httpRequest(HttpEnum.GET, ip, port, ACTION, token, content);
    Map<String, Object> rsp = new Gson().fromJson(response, Map.class);
    Object url = rsp.get("url");
    System.out.println("url:  " + url);

    return response;
  }
  @SneakyThrows
  @Override
  public ResultData realMonitor(String channelId) {
    return ResultData.success(getRealMonitor(channelId, ip, Integer.valueOf(port), token));
  }
  @Override
  public ResultData playback(String channelCode, String beginTime, String endTime, String scheme, String location,
      String resource) {
    /**
     * channelCode:类型string ，必填。视频通道编码。
     * resource:类型string ，选填。按文件回放需要，视频文件资源路径，可以通过录像查询的file字段获得，或者手动上
     * 传视频文件。
     * beginTime:类型string ，必填。按文件和按时间回放都需要，开始回放时间。时间格式:YYYYMMDDTHHmmssZ。
     * endTime:类型string ，必填。按文件和按时间回放都需要，结束回放时间。时间格式:YYYYMMDDTHHmmssZ。
     * location: 类型string ，必填。录像存储位置。
     * 协议类型描述
     * cloud 平台录像
     * device 设备录像
     * scheme:类型string ，选填。协议类型，支持RTSP、HLS两种。默认RTSP。
     * duration:类型int ，选填。有效时间，单位为秒，最长不超过10分钟，默认10分钟。
     */
    String content = "?channelCode=" + channelCode +
        "&resource=" + resource +
        "&beginTime=" + beginTime +
        "&endTime=" + endTime +
        "&scheme=" + scheme +
        "&location=" + location;
    String response = HttpTestUtils.httpRequest(HttpEnum.GET, ip, Integer.valueOf(port), PLAYBACK, token, content);
    return ResultData.success(response);
  }
}
