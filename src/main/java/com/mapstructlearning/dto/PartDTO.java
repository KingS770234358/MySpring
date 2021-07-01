package com.mapstructlearning.dto;

import lombok.Data;

/**
 * 汽车零件DTO对象
 * @author wangqiang6
 */
@Data
public class PartDTO {
    /**
     * 汽车零件的id
     */
    private Long partId;
    /**
     * 零件的名字，如：方向盘
     */
    private String partName;
}
