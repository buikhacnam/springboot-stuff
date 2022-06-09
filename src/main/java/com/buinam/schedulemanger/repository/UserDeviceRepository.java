package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.model.UserDevice;
import com.buinam.schedulemanger.service.UserDeviceService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Long> {

    UserDevice findByUserNameAndDeviceType(String userName, String deviceType);

    List<UserDevice> findAllByUserName(String to);
}

