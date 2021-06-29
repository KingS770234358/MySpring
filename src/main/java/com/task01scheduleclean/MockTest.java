package com.task01scheduleclean;

import com.myspring.Annotation.Component;
import com.task01scheduleclean.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class MockTest {
    /**
     * consumerService
     */
    @Autowired
    private ConsumerService consumerService;

    public int cntDelete(int[] ids) {
        return consumerService.delete(ids);
    }
}
