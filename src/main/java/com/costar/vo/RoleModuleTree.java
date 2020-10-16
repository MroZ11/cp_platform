package com.costar.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Created by cloud on 2019/6/20.
 */
@Setter
@Getter
@Builder
public class RoleModuleTree {

    private String roleId;
    private Set<String>  authorizedIds;
    private ModuleTree baseTree;

}
