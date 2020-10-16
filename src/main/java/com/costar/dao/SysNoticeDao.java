package com.costar.dao;

import com.costar.model.SysNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * Created by cloud on 2019/7/23.
 */
public interface SysNoticeDao extends JpaRepository<SysNotice, String>, QuerydslPredicateExecutor {
}
