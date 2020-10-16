package com.costar.dao;

import com.costar.model.SysMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by cloud on 2019/6/24.
 */
public interface SysMsgDao extends JpaRepository<SysMsg, String>, JpaSpecificationExecutor<SysMsg> {








}
