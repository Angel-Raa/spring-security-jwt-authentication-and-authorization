package com.github.angel.raa.modules.utils.enums;

import java.util.Arrays;
import java.util.List;


public enum Role {
    ROLE_ADMINISTRATOR(Arrays.asList(
            Permission.READ_ONE_PRODUCT,
            Permission.READ_ALL_PRODUCTS,
            Permission.CREATE_ONE_PRODUCT,
            Permission.UPDATE_ONE_PRODUCT,
            Permission.DISABLE_ONE_PRODUCT,
            Permission.DELETE_ONE_PRODUCT,
            Permission.READ_ONE_CATEGORIES,
            Permission.READ_ALL_CATEGORIES,
            Permission.DISABLE_ONE_CATEGORIES,
            Permission.DELETE_ONE_CATEGORIES,
            Permission.CREATE_ONE_CATEGORIES,
            Permission.UPDATE_ONE_CATEGORIES,
            Permission.READ_MY_PROFILE
    )),

    ROLE_ASSISTANT_ADMINISTRATOR(Arrays.asList(
            Permission.READ_ONE_PRODUCT,
            Permission.READ_ALL_PRODUCTS,
            Permission.CREATE_ONE_PRODUCT,
            Permission.UPDATE_ONE_PRODUCT,
            Permission.READ_ALL_CATEGORIES,
            Permission.CREATE_ONE_CATEGORIES,
            Permission.UPDATE_ONE_CATEGORIES,
            Permission.READ_MY_PROFILE
    )),
    ROLE_CUSTOMER(Arrays.asList(
            Permission.READ_MY_PROFILE
    ));

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
