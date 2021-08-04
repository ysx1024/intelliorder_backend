package com.equations.intelliorder;

import com.equations.intelliorder.order.service.IOrderlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IntelliorderApplicationTests {

    @Autowired
    private IOrderlistService orderlistService;

    @Test
    void contextLoads() {
    }


}
