package com.costar.dao;

import com.costar.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Created by cloud on 2019/6/14.
 */
public interface RoleDao extends JpaRepository<Role, String>,
        JpaSpecificationExecutor<Role>, QuerydslPredicateExecutor {







}
