package com.costar.dao;

import com.costar.model.RolesMoudles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * Created by cloud on 2019/6/14.
 */
public interface RolesMoudlesDao extends JpaRepository<RolesMoudles, String>, JpaSpecificationExecutor<RolesMoudles> {


    @Query("select u.moduleId from #{#entityName} u where u.roleId = ?1")
    Set<String> getCurrentRoleMoudleIds(String roleId);

    @Modifying
    @Query("delete from #{#entityName} u where u.roleId = ?1")
    void deleteInBulkByRoleId(String roleId);

}
