package com.hm.digital.equipment.vo;

import org.springframework.data.jpa.domain.Specification;

import com.hm.digital.common.config.QueryCondition;
import com.hm.digital.common.enums.MatchType;
import com.hm.digital.common.query.BaseQuery;
import com.hm.digital.inface.entity.Config;

import lombok.Data;

@Data
public class ConfigVO extends BaseQuery<Config> {

  /**
   * 类型
   */
  @QueryCondition(func = MatchType.equal)
  private String type;

  /**
   * 监所编号
   */
  @QueryCondition(func = MatchType.equal)
  private String prisonId;

  /**
   * 配置名称
   */
  @QueryCondition(func = MatchType.equal)
  private String name;

  /**
   * 配置数据
   */
  @QueryCondition(func = MatchType.equal)
  private String value;

  /**
   * 设备
   */
  @QueryCondition(func = MatchType.equal)
  private String equipment;

  /**
   * 前端、后端标识
   */
  @QueryCondition(func = MatchType.equal)
  private String universe;

  @Override
  public Specification<Config> toSpec() {
    return super.toSpecWithAnd();
  }

}
