package com.mapstructlearning.vo;

import lombok.Data;

@Data
public class PartVO {
    /**
     * 汽车零件的id
     */
    private Long partId;
    /**
     * 零件的名字，如：方向盘
     */
    private String partName;
}
