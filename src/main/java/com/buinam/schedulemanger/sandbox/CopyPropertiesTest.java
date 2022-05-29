package com.buinam.schedulemanger.sandbox;

import com.buinam.schedulemanger.mapper.GenerateMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CopyPropertiesTest {
    @Autowired
    private GenerateMapper mapper;

    @Test
    public void testCopyProperties() {
        Car car = new Car(4, 1, 4);
        Motorbike motorbike = new Motorbike(2, 0);

        System.out.println("before: " + motorbike.toString() + car.toString());
        BeanUtils.copyProperties(car, motorbike, "doors");
        System.out.println("after: " + motorbike.toString() + car.toString());

        Motorbike m2 = mapper.mapMotorbikeFromCar(car);
        System.out.println("m2: "+ m2.toString());

        System.out.println("---------------------------------");

        Car car2 = new Car(4, 1, 4);
        Motorbike motorbike2 = new Motorbike(2, 0);
        System.out.println("before: " + motorbike2.toString() + car2.toString());
        BeanUtils.copyProperties(motorbike2, car2, "doors");
        System.out.println("after: " + motorbike2.toString() + car2.toString());

        Car c2 = mapper.mapCarFromMotorbike(motorbike2);
        System.out.println("c2: "+ c2.toString());

        System.out.println("---------------------------------");
        Car car3 = new Car();

        Motorbike motorbike3 = new Motorbike(2, 0);
        System.out.println("before: " + motorbike3.toString() + car3.toString());
        BeanUtils.copyProperties(motorbike3, car3, "doors");
        System.out.println("after: " + motorbike3.toString() + car3.toString());
    }
}
