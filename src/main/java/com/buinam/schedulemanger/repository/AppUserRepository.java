package com.buinam.schedulemanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.buinam.schedulemanger.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
