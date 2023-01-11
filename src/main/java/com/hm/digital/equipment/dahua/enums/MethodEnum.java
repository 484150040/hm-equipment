package com.hm.digital.equipment.dahua.enums;

public enum MethodEnum {
  FN_LOGIN("请求登录", "FN_LOGIN"),
  FN_REBOOT("请求重启系统", "FN_REBOOT"),
  FN_GETNESTINFO("获取机库信息", "FN_GETNESTINFO"),
  FN_GETMQTTCONFIG("获取 MQTT 配置信息", "FN_GETMQTTCONFIG"),
  FN_SETMQTTCONFIG("设置 MQTT 配置信息", "FN_SETMQTTCONFIG"),
  FN_GETALTERNATEPOINT("获取备降点", "FN_GETALTERNATEPOINT"),
  FN_SETALTERNATEPOINT("设置备降点", "FN_SETALTERNATEPOINT"),
  FN_GETUPLOADURLS("获取文件上传地址", "FN_GETUPLOADURLS"),
  FN_SETUPLOADURLS("设置文件上传地址", "FN_SETUPLOADURLS"),
  FN_GETNETWORKCONFIG("获取网络配置", "FN_GETNETWORKCONFIG"),
  FN_SETNETWORKCONFIG("设置网络配置", "FN_SETNETWORKCONFIG"),
  FN_GETVEDIOURLS("获取直播地址", "FN_GETVEDIOURLS"),
  FN_SETVEDIOURLS("设置直播地址", "FN_SETVEDIOURLS"),
  FN_STARTPAIRING("开始机巢配对", "FN_STARTPAIRING"),
  FN_GETPAIRINGSTATUS("获取机巢配对状态", "FN_GETPAIRINGSTATUS")
  ;

  private String name;

  private String value;

  MethodEnum(String name, String value) {
    this.name = name;
    this.value = value;
  }
  public String getName() {
    return name;
  }
  public String getValue() {
    return value;
  }
}
