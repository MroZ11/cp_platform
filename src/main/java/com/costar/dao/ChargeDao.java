package com.costar.dao;

import com.costar.model.AppPayChannel;
import com.costar.model.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by cloud on 2020/10/20.
 */
public interface ChargeDao extends JpaRepository<Charge, String> {
}
