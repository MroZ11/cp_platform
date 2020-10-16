package com.costar.dao;


import com.costar.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

/**
 * Created by cloud on 2019/6/28.
 */
public interface UserAppDao extends JpaRepository<UserApp, String>, JpaSpecificationExecutor<UserApp>, QuerydslPredicateExecutor {

   int countByAppIdAndOwnerUserName(String appId,String ownerUserName);

}
