package com.mapstructlearning.dto;

import lombok.Data;

/**
 * 驾驶员DTO对象
 * @author wangqiang6
 */
@Data
public class DriverDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 驾驶员的名字
     */
    private String name;
}
