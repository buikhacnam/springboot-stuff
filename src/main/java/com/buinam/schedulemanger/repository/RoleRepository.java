package com.buinam.schedulemanger.repository;

import com.buinam.schedulemanger.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

