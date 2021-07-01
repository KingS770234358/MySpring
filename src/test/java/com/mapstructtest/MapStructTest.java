package com.mapstructtest;

import com.mapstructlearning.MapStructApplication;
import com.mapstructlearning.convert.CarConvert;
import com.mapstructlearning.dto.CarDTO;
import com.mapstructlearning.dto.DriverDTO;
import com.mapstructlearning.dto.PartDTO;
import com.mapstructlearning.vo.CarVO;
import com.mapstructlearning.vo.DriverVO;
import com.mapstructlearning.vo.PartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MapStructApplication.class})
public class MapStructTest {

    /**
     * 在SpringBoot中使用MapStruct
     *
     */
    @Autowired
    private CarConvert carConvert;
    @Test
    public void useMapStructInSpring(){
        PartVO partVO = new PartVO();
        partVO.setPartId(2L);
        partVO.setPartName("空调");
        System.out.println(partVO);
        PartDTO partDTO = carConvert.convertPartVO2PartDTO(partVO);
        System.out.println(partDTO);
    }

    /**
     * 继承配置的逆向转换
     */
    @Test
    public void mapStructInheritInverseConfiguration(){
        PartVO partVO = new PartVO();
        partVO.setPartId(2L);
        partVO.setPartName("空调");
        System.out.println(partVO);
        PartDTO partDTO = CarConvert.INSTANCE.convertPartVO2PartDTO(partVO);
        System.out.println(partDTO);
    }
    /**
     * 用 原类的所有属性 更新 目标类的属性
     */
    @Test
    public void mapStructInheritConfiguration(){
        PartDTO partDTO = new PartDTO();
        partDTO.setPartId(1L);
        partDTO.setPartName("轮胎");
        System.out.println(partDTO);
        PartVO partVO = CarConvert.INSTANCE.convertPartDTO2PartVOWithoutDefault2(partDTO);
        System.out.println(partVO);

        PartDTO partDTO2 = new PartDTO();
        partDTO2.setPartId(2L);
        partDTO2.setPartName("车门");
        CarConvert.INSTANCE.updatePartVO(partDTO2, partVO);
        System.out.println(partVO);
    }

    /**
     * 使MapStruct默认的映射行为失效
     */
    @Test
    public void convertCarDTO2CarVOWithoutDefault(){
        PartDTO partDTO = new PartDTO();
        partDTO.setPartId(1L);
        partDTO.setPartName("轮胎");
        System.out.println(partDTO);
        PartVO partVO = CarConvert.INSTANCE.convertPartDTO2PartVOWithoutDefault2(partDTO);
        System.out.println(partVO);
    }

    /**
     * MapStruct批量映射
     * List<CarDTO> -> List<CarVO>
     */
    @Test
    public void mapStructBatchMappings2(){
        CarDTO carDTO = buildCarDTO();
        List<CarDTO> carDTOS = new ArrayList<>();
        carDTOS.add(carDTO); // mapstruct 的映射源 SOURCE
        List<CarVO> carVOS = CarConvert.INSTANCE.convertCarDTOs2CarVOs(carDTOS);
        System.out.println(carVOS);
    }
    @Test
    public void mapStructBatchMappings1(){
        CarDTO carDTO = buildCarDTO();
        List<CarDTO> carDTOS = new ArrayList<>();
        carDTOS.add(carDTO); // mapstruct 的映射源 SOURCE
        List<CarVO> carVOS = new ArrayList<>();
        for(CarDTO cd : carDTOS){
            carVOS.add(CarConvert.INSTANCE.convertCarDTO2CarVO(cd));
        }
        System.out.println(carVOS);
    }

    /**
     * MapStruct自定义属性映射规则
     * 完成 double 保留两位小数 转成String
     * 完成 Date 格式化 转成String
     */
    @Test
    public void mapStructMappings(){
        CarDTO carDTO = buildCarDTO();
        System.out.println(carDTO);
        CarVO carVO = CarConvert.INSTANCE.convertCarDTO2CarVO(carDTO);
        // CarVO(id=330, vin=vin123456789, price=123789.126,
        // totalPrice=143789.13, publishDate=2021-07-01 10:39:36, brand=大众, hasPart=null, driverVO=null)
        System.out.println(carVO);
    }

    /**
     * MapStruct默认的数据转换
     * 默认规则：
     * ·属性如果同类型、同名 自动映射复制
     * ·属性如果同名，会自动进行类型转换：
     *     1.8种基本类型和他们对应的包装类型之间
     *     2.8种基本类型（和他们对应的包装类）和String之间
     *     3.日期类型和String之间
     *     参考官网...
     */
    @Test
    public void firstMapStruct(){
        CarDTO carDTO = buildCarDTO();
        CarVO carVO = CarConvert.INSTANCE.convertCarDTO2CarVO(carDTO);
        // CarVO(id=330, vin=vin123456789, price=123789.126, totalPrice=143789.126,
        // publishDate=2021/7/1 上午10:22, brand=大众, hasPart=null, driverVO=null)
        // 有默认的数据转换配置
        System.out.println(carVO);
    }

    /**
     * 测试传统的通过getter setter赋值完成pojo之间的转换
     * CarDto --> CarVo
     * 代码冗杂，不能突出业务逻辑重点
     * 复用率低
     */
    @Test
    public void withoutFramework(){
        // 模拟业务构造出的CarDTO对象
        CarDTO carDTO = buildCarDTO();
        // 开始转换dto->vo
        CarVO carVO = new CarVO();
        carVO.setId(carDTO.getId());
        carVO.setVin(carDTO.getVin());
        carVO.setPrice(carDTO.getPrice()); // double Double jdk自动装箱、拆箱不需要手动转换
        double totalPrice = carDTO.getTotalPrice();
        DecimalFormat df = new DecimalFormat("#.00");// 保留两位小数
        carVO.setTotalPrice(df.format(totalPrice));
        Date publishDate = carDTO.getPublishDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        carVO.setPublishDate(sdf.format(publishDate));
        carVO.setBrandName(carDTO.getBrand());
        List<PartDTO> partDTOS = carDTO.getPartDTOs();
        carVO.setHasPart((partDTOS != null && !partDTOS.isEmpty()) ? true : false);
        DriverVO driverVO = new DriverVO();
        driverVO.setDriverId(carDTO.getDriverDTO().getId());
        driverVO.setFullName(carDTO.getDriverDTO().getName());
        carVO.setDriverVO(driverVO);
        System.out.println(carVO);
    }

    /**
     * 模拟业务构造出的CarDTO对象
     * @return
     */
    private CarDTO buildCarDTO(){
        CarDTO carDTO = new CarDTO();
        carDTO.setId(330L);
        carDTO.setVin("vin123456789");
        carDTO.setPrice(123789.126d);
        carDTO.setTotalPrice(143789.126d);
        carDTO.setColor("白色");
        carDTO.setPublishDate(new Date());
        carDTO.setBrand("大众");
        // 零件
        PartDTO partDTO1 = new PartDTO();
        partDTO1.setPartId(1L);
        partDTO1.setPartName("多功能方向盘");
        PartDTO partDTO2 = new PartDTO();
        partDTO2.setPartId(2L);
        partDTO2.setPartName("智能车门");
        List<PartDTO> partDTOSList = new ArrayList<>();
        partDTOSList.add(partDTO1);
        partDTOSList.add(partDTO2);
        carDTO.setPartDTOs(partDTOSList);
        // 驾驶员
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setId(88L);
        driverDTO.setName("小明");
        carDTO.setDriverDTO(driverDTO);
        return carDTO;
    }
}
