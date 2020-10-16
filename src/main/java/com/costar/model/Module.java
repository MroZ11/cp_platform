package com.costar.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by cloud on 2019/6/14.
 */
@Entity
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
public class Module {
    private String id;
    private String moduleName;
    private String moduleDesc;
    private String moduleType;
    private String moduleIcon;
    /**
     * 最小根节点应为0
     */
    private String parent;
    private String moduleUrl;
    private Integer iLevel;
    private Integer leaf;
    private String application;
    private String controller;
    private Integer enable;
    private Integer priority;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MODULE_NAME")
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Basic
    @Column(name = "MODULE_DESC")
    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    @Basic
    @Column(name = "MODULE_TYPE")
    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    @Basic
    @Column(name = "MODULE_ICON")
    public String getModuleIcon() {
        return moduleIcon;
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon;
    }

    @Basic
    @Column(name = "PARENT")
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = "MODULE_URL")
    public String getModuleUrl() {
        return moduleUrl;
    }

    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl;
    }

    @Basic
    @Column(name = "I_LEVEL")
    public Integer getiLevel() {
        return iLevel;
    }

    public void setiLevel(Integer iLevel) {
        this.iLevel = iLevel;
    }

    @Basic
    @Column(name = "LEAF")
    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

    @Basic
    @Column(name = "APPLICATION")
    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    @Basic
    @Column(name = "CONTROLLER")
    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    @Basic
    @Column(name = "ENABLE")
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    @Basic
    @Column(name = "PRIORITY")
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }


}
