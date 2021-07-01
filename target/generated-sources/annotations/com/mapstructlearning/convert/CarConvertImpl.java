package com.mapstructlearning.convert;

import com.mapstructlearning.dto.CarDTO;
import com.mapstructlearning.dto.DriverDTO;
import com.mapstructlearning.dto.PartDTO;
import com.mapstructlearning.vo.CarVO;
import com.mapstructlearning.vo.DriverVO;
import com.mapstructlearning.vo.PartVO;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-01T13:32:35+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.6 (JetBrains s.r.o)"
)
@Component
public class CarConvertImpl implements CarConvert {

    @Override
    public CarVO convertCarDTO2CarVO(CarDTO carDTO) {
        if ( carDTO == null ) {
            return null;
        }

        CarVO carVO = new CarVO();

        if ( carDTO.getPublishDate() != null ) {
            carVO.setPublishDate( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ).format( carDTO.getPublishDate() ) );
        }
        carVO.setBrandName( carDTO.getBrand() );
        carVO.setTotalPrice( new DecimalFormat( "#.00" ).format( carDTO.getTotalPrice() ) );
        carVO.setDriverVO( convertDriverDTO2DriverVO( carDTO.getDriverDTO() ) );
        carVO.setId( carDTO.getId() );
        carVO.setVin( carDTO.getVin() );
        carVO.setPrice( carDTO.getPrice() );

        carDto2carVoAfter( carDTO, carVO );

        return carVO;
    }

    @Override
    public DriverVO convertDriverDTO2DriverVO(DriverDTO driverDTO) {
        if ( driverDTO == null ) {
            return null;
        }

        DriverVO driverVO = new DriverVO();

        driverVO.setDriverId( driverDTO.getId() );
        driverVO.setFullName( driverDTO.getName() );

        return driverVO;
    }

    @Override
    public List<CarVO> convertCarDTOs2CarVOs(List<CarDTO> carDTOS) {
        if ( carDTOS == null ) {
            return null;
        }

        List<CarVO> list = new ArrayList<CarVO>( carDTOS.size() );
        for ( CarDTO carDTO : carDTOS ) {
            list.add( convertCarDTO2CarVO( carDTO ) );
        }

        return list;
    }

    @Override
    public PartVO convertPartDTO2PartVOWithoutDefault(PartDTO partDTO) {
        if ( partDTO == null ) {
            return null;
        }

        PartVO partVO = new PartVO();

        partVO.setPartId( partDTO.getPartId() );

        return partVO;
    }

    @Override
    public PartVO convertPartDTO2PartVOWithoutDefault2(PartDTO partDTO) {
        if ( partDTO == null ) {
            return null;
        }

        PartVO partVO = new PartVO();

        partVO.setPartName( partDTO.getPartName() );

        return partVO;
    }

    @Override
    public PartVO convertPartDTO2PartVO(PartDTO partDTO) {
        if ( partDTO == null ) {
            return null;
        }

        PartVO partVO = new PartVO();

        partVO.setPartId( partDTO.getPartId() );
        partVO.setPartName( partDTO.getPartName() );

        return partVO;
    }

    @Override
    public void updatePartVO(PartDTO partDTO, PartVO partVO) {
        if ( partDTO == null ) {
            return;
        }

        partVO.setPartId( partDTO.getPartId() );
        partVO.setPartName( partDTO.getPartName() );
    }

    @Override
    public PartDTO convertPartVO2PartDTO(PartVO partVO) {
        if ( partVO == null ) {
            return null;
        }

        PartDTO partDTO = new PartDTO();

        partDTO.setPartId( partVO.getPartId() );
        partDTO.setPartName( partVO.getPartName() );

        return partDTO;
    }
}
