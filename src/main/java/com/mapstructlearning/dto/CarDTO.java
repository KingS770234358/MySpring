package com.mapstructlearning.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CarDTO {
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
    private double price;
    /**
     * 上路的价格
     */
    private double totalPrice;
    /**
     * 车的颜色
     */
    private String color;
    /**
     * 生产日期
     */
    private Date publishDate;
    /**
     * 品牌名字
     */
    private String brand;
    /**
     * 车的零件列表
     */
    private List<PartDTO> partDTOs;
    /**
     * 车的司机
     */
    private DriverDTO driverDTO;
}
