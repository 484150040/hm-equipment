package com.hm.digital.equipment.dahua.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hm.digital.inface.entity.Config;

@FeignClient(value = "nacos", path = "/digital")
public interface ConfigsFeignBiz {

  @GetMapping(value = "/save")
  void save(@RequestBody Config config);


  @GetMapping("/configList")
  List<Config> configList(Config config);
}
