package com.hm.digital.equipment.biz;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hm.digital.equipment.vo.ConfigVO;
import com.hm.digital.inface.biz.ConfigsService;
import com.hm.digital.inface.entity.Config;
import com.hm.digital.inface.mapper.ConfigMapper;


@Service
@Transactional
public class ConfigsServiceimpl implements ConfigsService {

  @Autowired
  private ConfigMapper configMapper;

  @Override
  public List<Config> getValue(String type, String prisonId) {
    return null;
  }
  @Override
  public List<Config> getValue(Config config) {
    ConfigVO configVO = new ConfigVO();
    BeanUtils.copyProperties(config, configVO);
    return configMapper.findAll(configVO.toSpec());
  }
  @Override
  public Config getId(String id) {
    return configMapper.getOne(id);
  }
  @Override
  public Config save(Config config) {
    return configMapper.save(config);
  }
}
