package com.mapstructlearning.convert;

import com.mapstructlearning.dto.CarDTO;
import com.mapstructlearning.dto.DriverDTO;
import com.mapstructlearning.dto.PartDTO;
import com.mapstructlearning.vo.CarVO;
import com.mapstructlearning.vo.DriverVO;
import com.mapstructlearning.vo.PartVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * car相关的pojo之间的转化
 */
@Mapper(componentModel = "spring") // 这里是MapStruct的Mapper 实质上就是给该interface加了@Component注解
public interface CarConvert {
    /**
     * CarConvert转换对象
     */
    // 实际的开发中可以注册到容器中 不写
    CarConvert INSTANCE = Mappers.getMapper(CarConvert.class);
    /**
     * CarDto --> CarVo
     */
    @Mappings(value = {
            // source是待转换类中的属性        target是目标类中的属性
            @Mapping(source = "totalPrice", target = "totalPrice", numberFormat = "#.00"),
            @Mapping(source = "publishDate", target = "publishDate", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            // 忽略对目标类中的指定属性的映射
            @Mapping(target = "color", ignore = true),
            @Mapping(source = "brand", target = "brandName"),
            // 会自动搜索 convertDriverDTO2DriverVO 方法
            @Mapping(source = "driverDTO", target = "driverVO")
    })
    CarVO convertCarDTO2CarVO(CarDTO carDTO);

    @Mapping(source = "id", target = "driverId")
    @Mapping(source = "name", target = "fullName")
    DriverVO convertDriverDTO2DriverVO(DriverDTO driverDTO);

    /**
     * 自定义映射处理
     * 完成上述@Mapping无法完成的一些映射
     */
    @AfterMapping // 让@Mapper方法调用完之后 调用该方法
    default void carDto2carVoAfter(CarDTO carDTO, @MappingTarget CarVO carVO) {
        // @MappingTarget 表示传来的CarVO是已经被赋值过的
        List<PartDTO> partDTOS = carDTO.getPartDTOs();
        carVO.setHasPart((partDTOS != null && !partDTOS.isEmpty()) ? true : false);
    }

    /**
     * 批量映射处理
     */
    List<CarVO> convertCarDTOs2CarVOs(List<CarDTO> carDTOS);

    /**
     * 使MapStruct默认的映射行为失效
     * 场景：
     * 当xxxVO -> yyyVO时，有许多的属性不希望按照默认映射规则直接映射，此时
     * 需要写多个ignore=true。
     * 如有10个属性，9个属性不希望按照默认映射规则直接映射，则需要写9个ignore=true
     * 为了防止这种情况，可以使用@BeanMapping注解，ignoreByDefault = true使默认的映射规则失效，
     * 而只映射那些配置了@Mappings/@Mapping的属性
     */
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "partId", target = "partId")
    PartVO convertPartDTO2PartVOWithoutDefault(PartDTO partDTO);

    @BeanMapping(ignoreByDefault = true)
    @Mappings(value = {
            @Mapping(source = "partName", target = "partName")
    })
    PartVO convertPartDTO2PartVOWithoutDefault2(PartDTO partDTO);

    PartVO convertPartDTO2PartVO(PartDTO partDTO);

    /**
     * @InheritConfiguration 继承@Mappings映射配置，当有多个相同的类型转换时，
     * 使用name属性指定特定的方法对应的映射配置 避免同样的配置写多份
     * 场景： 写一个 用 原类的所有属性 更新 目标类的属性 的方法
     */
    @InheritConfiguration(name = "convertPartDTO2PartVO")
    void updatePartVO(PartDTO partDTO, @MappingTarget PartVO partVO);

    @InheritInverseConfiguration(name = "convertPartDTO2PartVO")
    PartDTO convertPartVO2PartDTO(PartVO partVO);

}
