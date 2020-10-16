package com.costar.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by cloud on 2019/6/18.
 */
@Entity
@Table(name = "roles_moudles")
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
public class RolesMoudles {
    private String id;
    private String moduleId;
    private String roleId;

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "MODULE_ID")
    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    @Basic
    @Column(name = "ROLE_ID")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


}
