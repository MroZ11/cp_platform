package com.costar.dao;

import com.costar.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Created by cloud on 2019/6/14.
 */
public interface UserInfoDao extends JpaRepository<UserInfo, String>, JpaSpecificationExecutor<UserInfo> {


    <T> Optional<T>  getByUserName(String userName, Class<T> type);

    UserInfo getByClientId(String clientId);
}
