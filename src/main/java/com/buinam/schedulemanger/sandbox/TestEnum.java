package com.buinam.schedulemanger.sandbox;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestEnum {
    @Test
    public void testEnum() {
        StatusTestEnum statusActive = StatusTestEnum.ACTIVE;
        System.out.println(statusActive.getStatus());

        StatusTestEnum statusDeleted = StatusTestEnum.DELETED;
        System.out.println(statusDeleted.getStatus());


    }
}
