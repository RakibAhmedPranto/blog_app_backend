package com.blog.app.blog.role.repository;

import com.blog.app.blog.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
