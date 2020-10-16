package com.costar.dao;

import com.costar.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by cloud on 2019/6/14.
 */
public interface ModuleDao extends JpaRepository<Module, String>, JpaSpecificationExecutor<Module> {

    /**
     * 根据父节点找直属子节点 优先级越小越在前
     *
     * @param parent 父节点
     * @param enable 可用状态 0不可用 1可用
     * @return
     */
    List<Module> findModulesByParentAndEnableOrderByPriorityAsc(String parent, int enable);

    /**
     * 根据父节点找直属子节点 优先级越小越在前
     *
     * @param parent 父节点
     * @return
     */
    List<Module> findModulesByParentOrderByPriorityAsc(String parent);


    Set<Module> findModulesByIdInOrderByPriorityAsc(String[] ids);


    @Query("select u.id from #{#entityName} u order by u.priority asc ")
    Set<String> getAllMoudleIds();


    @Query("select MAX(u.priority) from #{#entityName} u where u.parent =:parentId ")
    Integer getChildMaxPriority(@Param("parentId") String parentId);


    @Modifying
    @Transactional
    @Query(" delete from #{#entityName} u where u.id = :moudleId or u.parent =:moudleId ")
    Integer deleteMoudleAndChild(@Param("moudleId") String moudleId);
}
