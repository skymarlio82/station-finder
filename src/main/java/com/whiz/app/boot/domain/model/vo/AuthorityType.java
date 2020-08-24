package com.whiz.app.boot.domain.model.vo;

public enum AuthorityType {
    ROLE_ADMIN(1, "ROLE_ADMIN"),
    ROLE_USER(2, "ROLE_USER"),
    ROLE_ANONYMOUS(3, "ROLE_ANONYMOUS"),
    ;

    AuthorityType(int roleCode, String roleDescription) {
        this.roleCode = roleCode;
        this.roleDescription = roleDescription;
    }

    private int roleCode;
    private String roleDescription;

    public int getRoleCode() {
        return roleCode;
    }

    public String getRoleDescription() {
        return roleDescription;
    }
}