package com.hm.digital.equipment.dahua.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.equipment.dahua.dto.BaseUserInfo;
import com.hm.digital.equipment.dahua.service.AlarmMessagesService;
import com.hm.digital.equipment.dahua.vo.AlarmMessagesVo;
import com.hm.digital.equipment.util.HttpEnum;
import com.hm.digital.equipment.util.HttpTestUtils;

import lombok.SneakyThrows;

@Service
public class AlarmMessagesServiceimpl extends BaseUserInfo implements AlarmMessagesService {

  public static final String ACTION = "/videoService/eventCenterxia/alarm/messages";

  //获取实时监控视频URI
  private static String getAlarmMessages(AlarmMessagesVo alarmMessagesVo,String ip, int port, String token) throws Exception{
    /**
     * channelId : 类型string ，必填。通道编码。channelID为之前调用分级获取设备通道的channelid，scheme为获取视频类型
     * subType : 类型int ，选填。码流类型，0:主码流、1:辅流1、2:辅流2。默认为0主码流。
     * scheme : 类型string ，选填。协议类型，支持RTSP、FLV_HTTP、HLS三种，默认RTSP。
     */
//    String content="{\n"
//        + "    \"baseInfo\":{\n"
//        + "        \"page\" : 1,\n"
//        + "        \"pageSize\" : 50,\n"
//        + "        \"orderKey\": \"alarmDate\",\n"
//        + "        \"direction\": \"asc\"\n"
//        + "    },\n"
//        + "    \"searchInfo\":{\n"
//        + "        \"deviceCode\" : \"GRM3DSDSDDDAFDSAFFDSFD\",\n"
//        + "        \"deviceCodes\" : [\"GRM3DSDSDDDAFDSAFFDSFD\",\"GRM3DSDSDDDAFDSBBBBBBB\"],\n"
//        + "        \"channelCode\" : \"XMMDSD2333SEFDSAFFDSFD\",\n"
//        + "        \"channelCodes\" : [\"XMMDSD2333SEFDSAFFDSFD\",\"XMMDSD2333SEFDCCCCCCC\"],\n"
//        + "        \"alarmStatus\" : [1,2],\n"
//        + "        \"alarmType\" : [2,3],\n"
//        + "        \"startAlarmDate\" : \"20191010T103020Z\",\n"
//        + "        \"endAlarmDate”: \"20191011T103020Z\",\n"
//        + "        \"alarmGrade\" : [1,2,3],\n"
//        + "        \"startHandleDate\" : \"20191010T103020Z\",\n"
//        + "        \"endHandleDate\" : \"20191011T103020Z\",\n"
//        + "        \"handleStatus\" : [1,2,3]\n"
//        + "    }\n"
//        + "}";
    String content = JSON.toJSONString(alarmMessagesVo);
    String response= HttpTestUtils.httpRequest(HttpEnum.POST,ip,port,ACTION,token,content);
    Map<String, Object> rsp = new Gson().fromJson(response, Map.class);

    System.out.println("url:  "+rsp);

    return response;
  }
  @SneakyThrows
  @Override
  public ResultData alarmMessages(AlarmMessagesVo alarmMessagesVo) {
    return ResultData.success(getAlarmMessages(alarmMessagesVo,ip, Integer.valueOf(port), token));
  }
}
