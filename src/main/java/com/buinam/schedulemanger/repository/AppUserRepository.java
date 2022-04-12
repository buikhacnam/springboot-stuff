package com.buinam.schedulemanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.buinam.schedulemanger.model.AppUser;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    @Query(value = "SELECT * FROM app_user WHERE username = ?1", nativeQuery = true)
    List<AppUser> findAllByName(String name);
}
