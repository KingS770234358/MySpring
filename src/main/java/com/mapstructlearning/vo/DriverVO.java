package com.mapstructlearning.vo;

import lombok.Data;

/**
 * 驾驶员VO对象
 * @author wangqiang6
 */
@Data
public class DriverVO {
    /**
     * 驾驶员id
     */
    private Long driverId;
    /**
     * 驾驶员的名字
     */
    private String fullName;
}
