package com.hm.digital.equipment.dahua;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.digital.common.enums.ConfigEnum;
import com.hm.digital.common.utils.HttpClientUtil;
import com.hm.digital.equipment.dahua.dto.LoginDto;
import com.hm.digital.equipment.dahua.enums.MethodEnum;
import com.hm.digital.equipment.dahua.feign.ConfigsFeignBiz;
import com.hm.digital.equipment.dahua.vo.DroneVo;
import com.hm.digital.inface.entity.Config;

import lombok.SneakyThrows;

import static com.hm.digital.common.utils.HttpClientUtil.objectToMap;

@Controller
public class HangarController {

  private static String host;


  @Autowired
  public ConfigsFeignBiz configsFeignBiz;

  @PostConstruct
  public void init() {
   try {
     host = configsFeignBiz.configList(getCofig(ConfigEnum.HANGAR.getKey())).get(0).getValue();
     if (StringUtils.isEmpty(host)){
       return;
     }
     //登录
     String username = configsFeignBiz.configList(getCofig(ConfigEnum.HANGAR_USERNAME.getKey())).get(0).getValue();
     String password = configsFeignBiz.configList(getCofig(ConfigEnum.HANGAR_PASSWORD.getKey())).get(0).getValue();
     DroneVo droneVo = new DroneVo();
     droneVo.setMethod(MethodEnum.FN_LOGIN.getValue());
     droneVo.setParams(LoginDto.builder().username(username).password(password));
     Map<String, Object> droneVomap = getResult(droneVo);

     if (CollectionUtils.isEmpty(droneVomap) && droneVomap.get("code") != null && Integer
         .valueOf(droneVomap.get("code").toString()).equals(200)) {
       System.out.println("登录成功");
     } else {
       System.out.println("登录失败");
     }

     //获取sn码
     DroneVo drone = new DroneVo();
     drone.setMethod(MethodEnum.FN_GETNESTINFO.getValue());
     Map<String, Object> dronemap = getResult(drone);
     if (!CollectionUtils.isEmpty(dronemap)) {
       Map<String, Object> dataMap = objectToMap(dronemap.get("data"));
       Config config = getCofig(ConfigEnum.HANGAR_SNM.getKey());
       config.setValue(String.valueOf(dataMap.get("devSN")));
       configsFeignBiz.save(config);
     }

     //获取 MQTT 配置信息
     DroneVo droneMqtt = new DroneVo();
     droneMqtt.setMethod(MethodEnum.FN_GETMQTTCONFIG.getValue());
     Map<String, Object> droneMqttmap = getResult(droneMqtt);
     if (!CollectionUtils.isEmpty(droneMqttmap)) {
       if (Integer.valueOf(droneMqttmap.get("code").toString()) == 200) {
         Map<String, Object> dataMap = objectToMap(droneMqttmap.get("data"));
         Config config = getCofig(ConfigEnum.HANGAR_CONFIGURATION.getKey());
         config.setValue(String.valueOf(dataMap));
         Config configs = configsFeignBiz.configList(getCofig(ConfigEnum.HANGAR_CONFIGURATION.getKey())).get(0);
         if (configs!=null){
           return;
         }
         configsFeignBiz.save(config);
       }/*else {
        //    设置 MQTT 配置信息
        DroneVo droneaddMqtt = new DroneVo();
        droneaddMqtt.setMethod(MethodEnum.FN_SETMQTTCONFIG.getValue());
        Map<String, Object> droneaddMqttmap = getResult(droneaddMqtt);
        Map<String, Object> dataMap = objectToMap(droneaddMqttmap.get("data"));
        Config config = getCofig(ConfigEnum.HANGAR_CONFIGURATION.getKey());
        config.setValue(String.valueOf(dataMap));
        Config configs = configsServices.getValue(getCofig(ConfigEnum.HANGAR_CONFIGURATION.getKey())).get(0);
        if (configs!=null){
          configs.setValue(String.valueOf(dataMap));
          configsServices.save(configs);
          return;
        }
        configsServices.save(config);
      }*/
     }
   }catch (Exception e) {
       return;
   }
  }

  /**
   * 结果
   *
   * @param drone
   * @return
   */
  private Map<String, Object> getResult(DroneVo drone) {
    Map<String, Object> parameter = objectToMap(drone);
    try {
      String result = HttpClientUtil.httpPostRequest(host, parameter);
      Map<String, Object> map = objectToMap(result);
      return map;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return null;
    }

  }
  private Config getCofig(String config) {
    Config configVO = new Config();
    configVO.setType(config);
    configVO.setUniverse("1");
    return configVO;
  }
  /**
   * 无人机操作信息
   *
   * @return
   */
  @SneakyThrows
  @RequestMapping("/hangar")
  @ResponseBody
  public String hangar(@RequestBody DroneVo droneVo) {
    Map<String, Object> parameters = objectToMap(droneVo);
    String result = HttpClientUtil.httpPostRequest(host, parameters);
    return result;
  }
}
