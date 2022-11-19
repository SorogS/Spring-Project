package com.sorog.carrent.repository;

import com.sorog.carrent.model.Role;
import com.sorog.carrent.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(Role name);
}

