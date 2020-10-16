package com.costar.vo;


import com.costar.model.Module;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by cloud on 2019/6/14.
 */
@Setter
@Getter
@Builder
public class ModuleTree {

    private String id;
    private boolean open;
    private boolean isParent;
    private boolean isUsable;
    private String ownerId;
    private String authorized;
    private String name;
    private List<ModuleTree> children;
    private Module moduleInfo;

}
