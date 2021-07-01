package com.mapstructlearning.vo;

import lombok.Data;

@Data
public class CarVO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 车辆的编号
     */
    private String vin;
    /**
     * 裸车的价格
     */
    private Double price;
    /**
     * 上路的价格 String类型，要求保留两位小数...
     */
    private String totalPrice;
    /**
     * 车的颜色
     */
    private String color;
    /**
     * 生产日期，格式：yyyy-MM-dd HH:mm:ss
     */
    private String publishDate;
    /**
     * 品牌名字
     */
    private String brandName;
    /**
     * 是否配置了零件
     */
    private Boolean hasPart;
    /**
     * 车的司机
     */
    private DriverVO driverVO;
}
