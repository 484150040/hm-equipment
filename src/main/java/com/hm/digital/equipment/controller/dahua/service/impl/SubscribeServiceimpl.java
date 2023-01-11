package com.hm.digital.equipment.controller.dahua.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hm.digital.common.utils.ResultData;
import com.hm.digital.equipment.controller.dahua.dto.BaseUserInfo;
import com.hm.digital.equipment.controller.dahua.service.SubscribeService;
import com.hm.digital.equipment.util.HttpEnum;
import com.hm.digital.equipment.util.HttpTestUtils;

import lombok.SneakyThrows;

@Service
public class SubscribeServiceimpl extends BaseUserInfo implements SubscribeService {

  public static final String ACTION = "/videoService/eventCenter/messages/subscribe";
  public static final String SUBSCRIBEADDRESS = "/videoService/eventCenter/messages/subscribeAddress";

  /*
     获取消息之前需要调用一次该接口，msgId=-1，来获取最新的msgId。第一次请求填-1，
     得到最新的msgId(此时不会返回消息内容)。接下来用返回的最新的msgId作为入参，获取对应的消息内容。
   */
  private static String getMessage(String msg,String msgNum,String type,String ip, int port, String token) throws Exception{
    //第一次调用获取最新的msgId
    /**
     * type: 类型string 。必填。获取消息的类型,参考消息类型。 此参数可以组合,如果要2和4的消息,那么type=2,4
     * msgId: 类型long 。必填。消息id。第一次请求填-1，得到最新的msgId(此时不会返回消息内容)。接下来用返回的最
     * 新的msgId作为入参，获取对应的消息内容。
     * msgNum: 类型int 。选填。每次最多获取的消息条数,不超过256,不填写默认值为64。
     * waitSec: 类型int 。选填。最大等待时间(单位：秒)，范围 [0, 60] 之间。不填或0表示不等待，大于0小于等于60
     * 表示在没有数据时进行等待，直到查询到数据立即返回。
     */
    String content="?msgId=" + msg+
        "&msgNum=" + msgNum+
        "&type="+type;
    String response= HttpTestUtils.httpRequest(HttpEnum.GET,ip,port,ACTION,token,content);
    System.out.println(response);
    Map<String, Object> rsp = new Gson().fromJson(response, Map.class);
    Object msgId = rsp.get("nextMsgId");
    String content1="?msgId="+(msgId==null?0:msgId)+"&msgNum=64&type=1,2";
    //将最新的msgId放入参数中进行调用，获取最新的message
    String response1=HttpTestUtils.httpRequest(HttpEnum.GET,ip,port,ACTION,token,content1);
    System.out.println(response1);
    return response1;
  }

  /**
   * 消息订阅
   *
   * @param ip
   * @param port
   * @param token
   * @return
   * @throws Exception
   */
  private static String getAddress(String ip, int port, String token) throws Exception{
    String content="";
    String response=HttpTestUtils.httpRequest(HttpEnum.GET,ip,port,SUBSCRIBEADDRESS,token,content);
    System.out.println(response);
    return response;
  }
  @SneakyThrows
  @Override
  public ResultData subscribe(String ipa,Integer porta,String msg,String msgNum,String type) {
//    String ip1="10.31.36.1";
//    int port1=8306;
    String rsp = getMessage(msg,msgNum,type,ipa, porta, token);
    return ResultData.success(rsp);
  }
}
