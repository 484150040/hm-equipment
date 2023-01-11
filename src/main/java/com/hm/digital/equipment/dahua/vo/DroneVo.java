package com.hm.digital.equipment.dahua.vo;

import lombok.Data;

@Data
public class DroneVo<T> {
  private String method;
  private T params;
  public DroneVo() {
  }
  public DroneVo(String method, T params) {
    this.method = method;
    this.params = params;
  }
}
